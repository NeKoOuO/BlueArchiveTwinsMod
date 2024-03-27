package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.effect.BATwinsShowCardAndFlashEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsLearnedAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractCard card;
    private AbstractCard.CardColor color;
    private AbstractCard thisCard;

    public BATwinsLearnedAction(AbstractCreature target, DamageInfo info, AbstractCard card, AbstractCard.CardColor color) {
        this.info = info;
        this.card = card;
        this.color = color;
        setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    @Override
    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            this.target.damage(this.info);
            if (this.card == null) {
                this.isDone = true;
                return;
            }
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c.uuid.equals(card.uuid)) {
                        if (c instanceof BATwinsModCustomCard) {
                            if (c.color != this.color)
                                ((BATwinsModCustomCard) c).conversionColor(false);
                            this.thisCard = c;
                        }
                    }
                }
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
            }
        }
        tickDuration();
        if (this.isDone && this.thisCard != null) {
            AbstractDungeon.topLevelEffectsQueue.add(new BATwinsShowCardAndFlashEffect(this.thisCard.makeStatEquivalentCopy()));
            addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        }
    }
}
