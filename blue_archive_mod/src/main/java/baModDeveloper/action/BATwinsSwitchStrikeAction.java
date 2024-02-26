package baModDeveloper.action;

import baModDeveloper.cards.BATwinsSwitchStrike;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsSwitchStrikeAction extends AbstractGameAction {
    private AbstractCard.CardColor color;
    private int numberOfConnections;
    public BATwinsSwitchStrikeAction(AbstractCard.CardColor color, AbstractMonster target,int numberOfConnections){
        this.color=color;
        this.target=target;
        this.numberOfConnections=numberOfConnections;
    }
    @Override
    public void update() {
        CardGroup g=new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c.cardID.equals(BATwinsSwitchStrike.ID) &&c.color==this.color){
                g.addToTop(c);
            }
        }
        if(!g.isEmpty()){
            AbstractCard temp=g.getRandomCard(AbstractDungeon.cardRandomRng);
            if(this.target.isDeadOrEscaped()){
                this.target=AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            }
            addToTop(new BATwinsPlayHandCardAction(temp,target,this.numberOfConnections));
        }
        this.isDone=true;
    }
}
