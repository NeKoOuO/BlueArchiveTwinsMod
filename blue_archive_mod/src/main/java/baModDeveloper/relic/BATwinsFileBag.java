package baModDeveloper.relic;

import baModDeveloper.effect.BATwinsFileBagEffect;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.CardRewardSkipButtonRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CardRewardScreen;

public class BATwinsFileBag extends CustomRelic implements CardRewardSkipButtonRelic {
    public static final String ID = ModHelper.makePath("FileBag");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "FileBag"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "FileBag_p"));
    private static final RelicTier type = RelicTier.RARE;
    public BATwinsFileBag() {
        super(ID, texture, outline, type,LandingSound.CLINK);
        this.counter=0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public void onClickedButton() {
        ModHelper.logger.info(BATwinsFileBag.class.getName()+":onClickedButton");
        this.flash();
        for(AbstractCard card: AbstractDungeon.cardRewardScreen.rewardGroup){
            switch (card.rarity){
                case COMMON:
                    this.counter+=5;
                    break;
                case UNCOMMON:
                    this.counter+=10;
                    break;
                case RARE:
                    this.counter+=30;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public String getButtonLabel() {
        return DESCRIPTIONS[1];
    }

    @Override
    public void onTrigger() {
        this.flash();
        AbstractDungeon.topLevelEffectsQueue.add(new BATwinsFileBagEffect());
        this.counter-=200;
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.floorY<=40;
    }

    @SuppressWarnings("unused")
    @SpirePatch(clz = CardRewardScreen.class,method = "onClose")
    public static class onClosePatch{
        @SpirePostfixPatch
        public static void postfixPatch(CardRewardScreen _instance){
            if(AbstractDungeon.player.hasRelic(BATwinsFileBag.ID)){
                AbstractRelic relic=AbstractDungeon.player.getRelic(BATwinsFileBag.ID);
                if(relic.counter>=200&&AbstractDungeon.getCurrRoom().phase!= AbstractRoom.RoomPhase.COMBAT){
                    relic.onTrigger();
                }
            }
        }
    }
}
