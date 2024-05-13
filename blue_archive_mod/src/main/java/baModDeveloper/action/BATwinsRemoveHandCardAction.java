package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsRemoveHandCardAction extends AbstractGameAction {
    AbstractCard card;

    public BATwinsRemoveHandCardAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hand.contains(this.card)) {
            AbstractDungeon.player.hand.removeCard(this.card);
        }
        this.isDone = true;
    }
}
