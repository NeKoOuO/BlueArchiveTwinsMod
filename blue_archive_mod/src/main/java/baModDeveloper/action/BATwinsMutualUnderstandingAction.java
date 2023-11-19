package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsMutualUnderstandingAction extends AbstractGameAction {
    private AbstractCard.CardColor color;
    public BATwinsMutualUnderstandingAction(AbstractCard.CardColor color){
        this.color=color;
    }
    @Override
    public void update() {
        AbstractCard c=DrawCardAction.drawnCards.get(0);
        if(c.color.equals(this.color)){
            AbstractMonster m= AbstractDungeon.getRandomMonster();
            addToTop(new BATwinsPlayHandCardAction(c,m));
        }
        addToTop(new WaitAction(0.4F));
        this.isDone=true;
    }
}
