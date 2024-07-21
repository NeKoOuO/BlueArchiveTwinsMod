package baModDeveloper.relic;

import baModDeveloper.action.BATwinsMakeTempCardInHandAction;
import baModDeveloper.cards.BATwinsExchange;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BATwinsGameGuide extends CustomRelic {
    public static final String ID = ModHelper.makePath("GameGuide");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "GameGuide"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "GameGuide_p"));
    private static final RelicTier type = RelicTier.BOSS;
    private AbstractCard card;

    public BATwinsGameGuide() {
        super(ID, texture, type, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        this.flash();
        addToBot(new BATwinsMakeTempCardInHandAction(CardLibrary.getCard(BATwinsExchange.ID), true, true, true, true, false));
//        addToBot(new DrawCardAction(1));
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(BATwinsGameMagazine.ID);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BATwinsGameGuide();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(BATwinsGameMagazine.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(BATwinsGameMagazine.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    return;
                }
            }
        } else {
            super.obtain();
        }
    }
}
