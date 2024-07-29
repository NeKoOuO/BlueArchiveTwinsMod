package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.stats.RunData;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class BATwinsRankIcon extends CustomRelic {
    public static final String ID= ModHelper.makePath("RankIcon");
    private static final Texture[] textures = new Texture[4];
    private static final RelicStrings[] relicStrings=new RelicStrings[4];
    static {
        for(int i=0;i<textures.length;i++){
            int index=i+1;
            textures[i]=TextureLoader.getTexture(ModHelper.makeImgPath("relic","RankIcon"+index));
            textures[i].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        for(int i=0;i<relicStrings.length;i++){
            int index=i+1;
            relicStrings[i]=CardCrawlGame.languagePack.getRelicStrings(ModHelper.makePath("RankIcon"+index));
        }
    }
//    private static final Texture texture=TextureLoader.getTexture(ModHelper.makeImgPath("relic","RankIcon1"));
    private static final Texture outline= TextureLoader.getTexture(ModHelper.makeImgPath("relic","RankIcon_p"));
    private static final RelicTier type=RelicTier.RARE;
    private int score;
    private int rank;
    public BATwinsRankIcon() {
        super(ID, textures[0],outline,type, LandingSound.SOLID);
        this.score=checkScore();
        this.rank=getRank(this.score);
//        this.DESCRIPTIONS=relicStrings[this.rank].DESCRIPTIONS;
        this.description=getUpdatedDescription();
        this.setTextureOutline(textures[this.rank-1],outline);
        this.flavorText=relicStrings[this.rank-1].FLAVOR;
        try {
            Field field= AbstractRelic.class.getDeclaredField("name");
            field.setAccessible(true);
            field.set(this,relicStrings[this.rank-1].NAME);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if(this.rank<=2){
            this.counter=0;
        }
    }

    @Override
    public String getUpdatedDescription() {
        if(this.rank==0){
            return DESCRIPTIONS[0];
        }
        if(this.score==-1){
            return relicStrings[0].DESCRIPTIONS[0];
        }
        return String.format(relicStrings[this.rank-1].DESCRIPTIONS[0],this.score);
    }

    private int checkScore(){
        RunData runData = null;
        int saveSlot=CardCrawlGame.saveSlot;
        String folderPath="runs/";
//        String timestamp = null;
        try {
            if(saveSlot!=0){
                folderPath=folderPath+saveSlot+"_";
            }
            folderPath+=AbstractDungeon.player.chosenClass.name();
            FileHandle[] fileHandler= Gdx.files.local(folderPath).list();
            Optional<FileHandle> fileHandle= Arrays.stream(fileHandler).max(Comparator.comparing(FileHandle::name));
//            for (FileHandle fileHandle : fileHandler) {
//                RunData temp = ModHelper.gson.fromJson(fileHandle.readString(), RunData.class);
//                if (timestamp == null || temp.timestamp.compareTo(timestamp) > 0) {
//                    runData = temp;
//                    timestamp = runData.timestamp;
//                }
//            }
            if(fileHandle.isPresent()){
                runData= ModHelper.gson.fromJson(fileHandle.get().readString(), RunData.class);
            }
        }catch (Exception e){
            ModHelper.getLogger().error("Can not find last run!");
            return -1;
        }
        if(runData==null){
            return -1;
        }else{
            return runData.score;
        }
    }

    private int getRank(int score){
        if(score<0){
            return 1;
        }
        if(score<1000){
            return 4;
        }else if(score<2000){
            return 3;
        }else if(score<3000){
            return 2;
        }else{
            return 1;
        }
    }

    @Override
    public void onVictory() {
        this.flash();
        switch (this.rank){
            case 1:
            case 2:
                AbstractDungeon.player.gainGold(15);
                this.counter++;
                break;
            case 3:
                AbstractDungeon.player.gainGold(15);
                break;
            case 4:
                AbstractDungeon.player.gainGold(10);
                break;
            default:
                break;
        }

        if((this.counter==4&&this.rank==1)||(this.counter==5&&this.rank==2)){
            AbstractCard card=getCard();
            if(card!=null){
                card.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(card);
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
            }
            this.counter=0;
        }
    }

    private AbstractCard getCard(){
        ArrayList<AbstractCard> cards=new ArrayList<>();
        AbstractCard res = null;
        for(AbstractCard card:AbstractDungeon.player.masterDeck.group){
            if(card.canUpgrade()){
                cards.add(card);
            }
        }
        if(!cards.isEmpty()){
            res = cards.get(AbstractDungeon.miscRng.random(0, cards.size() - 1));

        }
        return res;
    }

    @Override
    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 40);
    }
}
