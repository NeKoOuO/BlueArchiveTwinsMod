package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsGameLaunchAction extends AbstractGameAction {
   AbstractCard.CardColor color;

    public BATwinsGameLaunchAction(AbstractCard.CardColor color) {
        this.color=color;
    }

    @Override
    public void update() {
        if (!this.isDone) {
            for(AbstractCard card:AbstractDungeon.player.hand.group){
                if(card.color==this.color&&DrawCardAction.drawnCards.contains(card)){
                    card.setCostForTurn(0);
                }
            }
            this.isDone = true;
        }
    }
}
