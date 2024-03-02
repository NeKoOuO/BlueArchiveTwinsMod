package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.function.Consumer;

public class BATwinsDisCardByColorAction extends AbstractGameAction {
    private AbstractCard.CardColor color;
    private Consumer<Integer> callback;

    public BATwinsDisCardByColorAction(AbstractCard.CardColor color) {
        this.color = color;
    }

    public BATwinsDisCardByColorAction(AbstractCard.CardColor color, Consumer<Integer> callback) {
        this.color = color;
        this.callback = callback;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> disCards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.color == this.color) {
                disCards.add(c);
            }
        }

        for (AbstractCard c : disCards) {
            AbstractDungeon.player.hand.moveToDiscardPile(c);
            c.triggerOnManualDiscard();
        }
        if (this.callback != null) {
            this.callback.accept(disCards.size());
        }
        this.isDone = true;
    }
}
