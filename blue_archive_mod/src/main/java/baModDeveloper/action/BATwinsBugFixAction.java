package baModDeveloper.action;

import baModDeveloper.cards.BATwinsBugCard;
import baModDeveloper.power.BATwinsBugFixPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Objects;

public class BATwinsBugFixAction extends AbstractGameAction {
     private int amount;
     private AbstractPlayer p;
     public BATwinsBugFixAction(int amount){
         this.amount=amount;
         this.p= AbstractDungeon.player;
         this.duration= Settings.ACTION_DUR_FAST;
     }
    @Override
    public void update() {
        CardGroup temp=new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for(AbstractCard c:this.p.drawPile.group){
            if(c.type== AbstractCard.CardType.STATUS||c.type== AbstractCard.CardType.CURSE){
                temp.addToTop(c);
            }
        }
        for(int i = 0; i<this.amount&& !temp.isEmpty(); i++){
            AbstractCard exhuastCard=temp.getRandomCard(AbstractDungeon.cardRandomRng);
            if(Objects.equals(exhuastCard.cardID, BATwinsBugCard.ID)){
                addToTop(new ApplyPowerAction(this.p,this.p,new StrengthPower(this.p,1)));
            }
            addToTop(new ExhaustSpecificCardAction(exhuastCard,this.p.drawPile));
            temp.removeCard(exhuastCard);
        }
        this.isDone=true;
    }
}
