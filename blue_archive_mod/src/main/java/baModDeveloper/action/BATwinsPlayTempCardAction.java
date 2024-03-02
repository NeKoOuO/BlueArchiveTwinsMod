package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsPlayTempCardAction extends AbstractGameAction {
    private AbstractCard cardToPlay;
    private int numberOfConnections;

    public BATwinsPlayTempCardAction(AbstractCard card, int numberOfConnections, AbstractCreature target) {
        this.cardToPlay = card;
        this.numberOfConnections = numberOfConnections;
        this.target = target;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public BATwinsPlayTempCardAction(AbstractCard card, int numberOfConnections) {
        this(card, numberOfConnections, null);
    }

    @Override
    public void update() {
        AbstractDungeon.player.limbo.group.add(cardToPlay);
        cardToPlay.current_y = 0.0F * Settings.scale;
        cardToPlay.target_x = (float) Settings.WIDTH / 2.0F - 200.0F * Settings.xScale;
        cardToPlay.target_y = (float) Settings.HEIGHT / 2.0F;
        cardToPlay.targetAngle = 0.0F;
        cardToPlay.lighten(false);
        cardToPlay.drawScale = 0.12F;
        cardToPlay.targetDrawScale = 0.75F;
        cardToPlay.purgeOnUse = true;
        cardToPlay.exhaustOnUseOnce = true;

        cardToPlay.applyPowers();
        if (cardToPlay instanceof BATwinsModCustomCard) {
            ((BATwinsModCustomCard) cardToPlay).numberOfConnections = this.numberOfConnections;
        }
        if (this.target == null) {
            this.target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        }
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(cardToPlay, (AbstractMonster) this.target, cardToPlay.energyOnUse, true, true), true);
//            addToTop(new UnlimboAction(cardToPlay));
        if (!Settings.FAST_MODE) {
            addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        } else {
            addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }
        this.isDone = true;
        return;
    }
}
