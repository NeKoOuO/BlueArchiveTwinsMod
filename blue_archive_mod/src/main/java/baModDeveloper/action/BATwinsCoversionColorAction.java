package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class BATwinsCoversionColorAction extends AbstractGameAction {
    private BATwinsModCustomCard card;
    public BATwinsCoversionColorAction(BATwinsModCustomCard card){
        this.card=card;
    }
    @Override
    public void update() {
        card.conversionColor();
        this.isDone=true;
    }
}
