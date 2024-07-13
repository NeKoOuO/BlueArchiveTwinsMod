package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.stats.RunData;

import java.lang.reflect.Field;

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
    }

    @Override
    public String getUpdatedDescription() {
        if(this.rank==0){
            return DESCRIPTIONS[0];
        }
        if(this.score==-1){
            return DESCRIPTIONS[0];
        }
        return String.format(relicStrings[this.rank-1].DESCRIPTIONS[0],this.score);
    }

    private int checkScore(){
        RunData runData = null;
        String timestamp = null;
        try {
            FileHandle[] fileHandler= Gdx.files.local("runs/BATwins").list();
            for (FileHandle fileHandle : fileHandler) {
                RunData temp = ModHelper.gson.fromJson(fileHandle.readString(), RunData.class);
                if (timestamp == null || temp.timestamp.compareTo(timestamp) > 0) {
                    runData = temp;
                    timestamp = runData.timestamp;
                }
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
        if(score==-1){
            return 1;
        }
        if(score<500){
            return 4;
        }else if(score<1000){
            return 3;
        }else if(score<1500){
            return 2;
        }else{
            return 1;
        }
    }


}
