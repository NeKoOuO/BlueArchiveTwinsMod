package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class BATwinsCoversionColorAction extends AbstractGameAction {
    private BATwinsModCustomCard card;
    private boolean flash;

    public BATwinsCoversionColorAction(BATwinsModCustomCard card, boolean flash) {
        this.card = card;
        this.flash = flash;
    }

    public BATwinsCoversionColorAction(BATwinsModCustomCard card) {
        this(card, true);
    }

    @Override
    public void update() {

        card.conversionColor(flash);
        this.isDone = true;
    }
}
