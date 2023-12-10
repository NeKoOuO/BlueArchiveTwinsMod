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
            addToTop(new BATwinsPlayTempCardAction(cardToPlay,1));

        }
        this.isDone=true;
    }
}
