package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class BATwinsDisCardByColorAction extends AbstractGameAction {
    private AbstractCard.CardColor color;
    public BATwinsDisCardByColorAction(AbstractCard.CardColor color){
        this.color=color;
    }
    @Override
    public void update() {
        ArrayList<AbstractCard> disCards=new ArrayList<>();
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c.color==this.color){
                disCards.add(c);
            }
        }
        for(AbstractCard c:disCards){
            AbstractDungeon.player.hand.moveToDiscardPile(c);
            c.triggerOnManualDiscard();
        }
        this.isDone=true;
    }
}
