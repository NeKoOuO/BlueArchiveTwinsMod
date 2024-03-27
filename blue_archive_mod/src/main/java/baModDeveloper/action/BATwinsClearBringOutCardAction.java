package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class BATwinsClearBringOutCardAction extends AbstractGameAction {
    private AbstractCard card;

    public BATwinsClearBringOutCardAction(AbstractCard card) {
        this.card = card;

    }

    @Override
    public void update() {
        if (card instanceof BATwinsModCustomCard) {
            ((BATwinsModCustomCard) card).clearBringOutCards();
        }
        this.isDone = true;
    }
}
