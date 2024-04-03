package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.patch.BATwinsAbstractCardPatch;
import baModDeveloper.relic.BATwinsRubiksCube;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsPlayDrawPailCardAction extends AbstractGameAction {
    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("LoopBreak"));
    private AbstractCard card;
    private boolean exhaust;
    private int numberOfConnections;
    private boolean isBlockOrigin = false;

    public BATwinsPlayDrawPailCardAction(AbstractCard card, AbstractCreature target, boolean exhaust, int numberOfConnections) {
        this.card = card;
        this.exhaust = exhaust;
        this.target = target;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.numberOfConnections = numberOfConnections;
    }

    public BATwinsPlayDrawPailCardAction(AbstractCard card, AbstractCreature target, boolean exhaust) {
        this(card, target, exhaust, 1);
    }

    public BATwinsPlayDrawPailCardAction(AbstractCard card, AbstractCreature target, boolean exhaust, int numberOfConnections, boolean isBlockOrigin) {
        this(card, target, exhaust, numberOfConnections);
        this.isBlockOrigin = isBlockOrigin;
    }

    @Override
    public void update() {
        if (this.numberOfConnections > 10) {
            for (int i = uiStrings.TEXT.length - 1; i >= 0; i--) {
                addToTop(new TalkAction(true, uiStrings.TEXT[i], 3.0F, 3.0F));
            }
            this.isDone = true;
            return;
        }
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.drawPile.contains(card)) {
                if (AbstractDungeon.player.hasRelic(BATwinsRubiksCube.ID)) {
                    AbstractDungeon.player.getRelic(BATwinsRubiksCube.ID).onTrigger();
                }
                AbstractDungeon.player.drawPile.group.remove(this.card);
                AbstractDungeon.getCurrRoom().souls.remove(this.card);
                card.exhaust = this.exhaust;
                if (card instanceof BATwinsModCustomCard) {
                    ((BATwinsModCustomCard) card).numberOfConnections = this.numberOfConnections;
                } else {
                    BATwinsAbstractCardPatch.FieldPatch.numberOfConnections.set(card, this.numberOfConnections);
                }
                AbstractDungeon.player.limbo.group.add(this.card);
                card.current_y = 0.0F * Settings.scale;
                card.target_x = (float) Settings.WIDTH / 2.0F - 200.0F * Settings.xScale;
                card.target_y = (float) Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;

                BATwinsAbstractCardPatch.FieldPatch.blockTheOriginalEffect.set(card, this.isBlockOrigin);
                card.applyPowers();
                card.calculateCardDamage((AbstractMonster) this.target);
                card.isInAutoplay = true;
                if (this.target == null) {
                    addToTop(new NewQueueCardAction(card, true, false, true));
                } else {
                    addToTop(new NewQueueCardAction(card, this.target, false, true));
                }
                addToTop(new UnlimboAction(card));
//                if (Settings.FAST_MODE) {
//                    addToTop(new WaitAction(Settings.ACTION_DUR_MED));
//                } else {
//                    addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
//                }
            }
        }
        this.isDone = true;
    }
}
