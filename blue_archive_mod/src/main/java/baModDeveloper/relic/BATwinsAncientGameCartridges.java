package baModDeveloper.relic;

import baModDeveloper.action.BATwinsPlayDrawPailCardAction;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BATwinsAncientGameCartridges extends CustomRelic {
    public static final String ID = ModHelper.makePath("AncientGameCartridges");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "AncientGameCartridges"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "AncientGameCartridges"));
    private static final RelicTier type = RelicTier.RARE;

    public BATwinsAncientGameCartridges() {
        super(ID, texture, outline, type, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
//        this.flash();
//        CardGroup powerCards=new CardGroup(CardGroup.CardGroupType.CARD_POOL);
//        AbstractDungeon.player.drawPile.group.stream().filter(card -> card.type== AbstractCard.CardType.POWER).forEach(powerCards::addToBottom);
//        AbstractCard card=powerCards.getRandomCard(AbstractDungeon.cardRandomRng);
//        addToTop(new BATwinsPlayDrawPailCardAction(card,null,false));
//        this.grayscale=true;
    }


    @Override
    public void atTurnStartPostDraw() {
        if (!this.grayscale) {
            this.flash();
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    CardGroup powerCards = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
                    AbstractDungeon.player.drawPile.group.stream().filter(card -> card.type == AbstractCard.CardType.POWER).forEach(powerCards::addToBottom);
                    if (!powerCards.isEmpty()) {
                        AbstractCard card = powerCards.getRandomCard(AbstractDungeon.cardRandomRng);
                        addToTop(new BATwinsPlayDrawPailCardAction(card, null, false));
                    }
                    this.isDone=true;
                }
            });

            this.grayscale = true;

        }

    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BATwinsAncientGameCartridges();
    }
}
