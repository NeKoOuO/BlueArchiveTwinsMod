package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsExchangeAllAction extends AbstractGameAction {
    private final AbstractCard.CardColor color;
    public BATwinsExchangeAllAction(AbstractCard.CardColor color){
        this.color=color;
        this.duration= Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        AbstractDungeon.player.hand.group.forEach(this::exchange);
        this.isDone=true;
    }

    private void exchange(AbstractCard card){
        if(card instanceof BATwinsModCustomCard &&card.color!=this.color){
            addToTop(new BATwinsCoversionColorAction((BATwinsModCustomCard) card,true));
        }
    }
}
