package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BATwinsFileBag extends CustomRelic {
    public static final String ID = ModHelper.makePath("FileBag");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "FileBag"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "FileBag_p"));
    private static final RelicTier type = RelicTier.RARE;
    public BATwinsFileBag() {
        super(ID, texture, outline, type,LandingSound.CLINK);

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        ArrayList< AbstractCard> cards=this.initSelectCards();
        addToBot(new SelectCardsAction(cards,1,"",false,c->true,this::callback));
    }

    private void callback(List<AbstractCard> cards) {
        for(AbstractCard card:cards){
            card.freeToPlayOnce=true;
            addToTop(new MakeTempCardInDrawPileAction(card,1,true,true));
        }
    }

    private ArrayList<AbstractCard> initSelectCards() {
        ArrayList<AbstractCard> cards=new ArrayList<>();
        ArrayList<AbstractCard> commonPool=AbstractDungeon.srcCommonCardPool.group.stream().filter(c->{
            return !c.hasTag(AbstractCard.CardTags.HEALING);
        }).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<AbstractCard> uncommonPool=AbstractDungeon.srcUncommonCardPool.group.stream().filter(c->{
            return !c.hasTag(AbstractCard.CardTags.HEALING);
        }).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<AbstractCard> rarePool=AbstractDungeon.srcRareCardPool.group.stream().filter(c->{
            return !c.hasTag(AbstractCard.CardTags.HEALING);
        }).collect(Collectors.toCollection(ArrayList::new));
        Random random=new Random(AbstractDungeon.miscRng.randomLong());
        for(int i=0;i<9;i++){
            float rare=random.nextFloat();
            AbstractCard card;
            if(rare<0.85F){
                card=commonPool.get(random.nextInt(commonPool.size()));
            }else if(rare<0.985F){
                card=uncommonPool.get(random.nextInt(uncommonPool.size()));
            }else{
                card=rarePool.get(random.nextInt(rarePool.size()));
            }
            cards.add(card.makeCopy());
        }
        float rare=random.nextFloat();
        if(rare<0.96F){
            cards.add(uncommonPool.get(random.nextInt(uncommonPool.size())).makeCopy());
        }else{
            cards.add(rarePool.get(random.nextInt(rarePool.size())).makeCopy());
        }
        return cards;
    }
}
