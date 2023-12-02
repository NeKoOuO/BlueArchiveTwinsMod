package baModDeveloper.action;

import baModDeveloper.cards.BATwinsMidoriStrick;
import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.cards.BATwinsMomoiStrick;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Set;

public class BATwinsDefensiveCounterattackAction extends AbstractGameAction {
    private final boolean exchange;
    public BATwinsDefensiveCounterattackAction(AbstractMonster m,boolean exchange){
        this.target=m;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.exchange=exchange;
    }
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            BATwinsModCustomCard cardToPlay;
            if (exchange) {
                cardToPlay = new BATwinsMomoiStrick();
            } else {
                cardToPlay = new BATwinsMidoriStrick();
            }
            cardToPlay.exhaustOnUseOnce = true;
            AbstractDungeon.player.limbo.group.add(cardToPlay);
            cardToPlay.current_y = 0.0F * Settings.scale;
            cardToPlay.target_x = (float) Settings.WIDTH / 2.0F - 200.0F * Settings.xScale;
            cardToPlay.target_y = (float) Settings.HEIGHT / 2.0F;
            cardToPlay.targetAngle = 0.0F;
            cardToPlay.lighten(false);
            cardToPlay.drawScale = 0.12F;
            cardToPlay.targetDrawScale = 0.75F;
            cardToPlay.applyPowers();
//            addToTop(new NewQueueCardAction(cardToPlay, this.target, false, true));
            cardToPlay.purgeOnUse=true;
            cardToPlay.numberOfConnections=1;
//            cardToPlay.playedByOtherCard = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(cardToPlay, (AbstractMonster) this.target,cardToPlay.energyOnUse,true,true),true);
//            addToTop(new UnlimboAction(cardToPlay));
            if (!Settings.FAST_MODE) {
                addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }
        }
        this.isDone=true;
    }
}
