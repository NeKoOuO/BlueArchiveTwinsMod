package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class BATwinsRepeatOperationAction extends AbstractGameAction {
    private int amount;
    private ArrayList<AbstractCard> canNotSelect;
    private AbstractPlayer p;
    public BATwinsRepeatOperationAction(int amount){
        this.amount=amount;
        this.canNotSelect=new ArrayList<>();
        this.p= AbstractDungeon.player;
        this.actionType=ActionType.CARD_MANIPULATION;
        this.duration= Settings.ACTION_DUR_FAST;
    }
    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_FAST){
            if(this.p.hand.isEmpty()){
                this.isDone=true;
                return;
            }
            for(AbstractCard card:this.p.hand.group){
                if(!(card instanceof BATwinsModCustomCard)){
                    canNotSelect.add(card);
                }
            }
            if(this.canNotSelect.size()==this.p.hand.size()){
                this.isDone=true;
                return;
            }
            this.p.hand.group.removeAll(this.canNotSelect);
            if(!this.p.hand.group.isEmpty()){
                AbstractDungeon.handCardSelectScreen.open("",this.amount,true,true,false);
                tickDuration();
                return;
            }
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            for(AbstractCard c:AbstractDungeon.handCardSelectScreen.selectedCards.group){
                if(c instanceof BATwinsModCustomCard){
                    BATwinsModCustomCard temp= (BATwinsModCustomCard) c.makeSameInstanceOf();
                    temp.conversionColor();
                    addToTop(new BATwinsMakeTempCardInHandAction(c,true,false,false,true,false));

                }
            }
            this.isDone=true;
        }
    }
}
