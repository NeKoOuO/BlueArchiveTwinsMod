package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class BATwinsMutualUnderstandingAction extends AbstractGameAction {
    private AbstractCard.CardColor color;
    private int numberOfConnections;

    public BATwinsMutualUnderstandingAction(AbstractCard.CardColor color, int numberOfConnections) {
        this.color = color;
        this.numberOfConnections = numberOfConnections;
    }

    @Override
    public void update() {
        if (DrawCardAction.drawnCards.isEmpty()) {
            this.isDone = true;
            return;
        }
        AbstractCard c = DrawCardAction.drawnCards.get(0);
        if (c.color.equals(this.color) && c.cost != -2) {
//            AbstractMonster m = AbstractDungeon.getRandomMonster();
            addToTop(new BATwinsPlayHandCardAction(c, null, numberOfConnections));
        }
        addToTop(new WaitAction(0.4F));
        this.isDone = true;
    }
}
