package baModDeveloper.action;

import baModDeveloper.character.BATwinsCharacter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BATwinsAlternatingAttackAction extends AbstractGameAction {
    private final AbstractCard.CardColor color;
    private final AbstractPlayer p;
    private final ArrayList<AbstractCard> canNotSelectCards=new ArrayList<>();
    public BATwinsAlternatingAttackAction(AbstractCard.CardColor color, AbstractMonster target){
        this.color=color;
        this.p= AbstractDungeon.player;
        this.target=target;
        this.duration= Settings.ACTION_DUR_FAST;
    }
    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_FAST){
            for(AbstractCard c:this.p.hand.group){
                if(c.color!=this.color||c.type!= AbstractCard.CardType.ATTACK){
                    this.canNotSelectCards.add(c);
                }
            }
            if(this.canNotSelectCards.size()==this.p.hand.group.size()){
                this.isDone=true;
                return;
            }
            if(this.p.hand.group.size()-this.canNotSelectCards.size()==1){
                for(AbstractCard c:this.p.hand.group){
                    if(c.color==this.color&&c.type== AbstractCard.CardType.ATTACK){
                        wantToUseCard(c);
                        this.isDone=true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.canNotSelectCards);
            if(this.p.hand.group.size()>1){
                AbstractDungeon.handCardSelectScreen.open("",1,false,false,false);
                tickDuration();
                return;
            }
            if(this.p.hand.group.size()==1){
                wantToUseCard(this.p.hand.getTopCard());
                this.isDone=true;
                return;
            }

        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            for(AbstractCard c:AbstractDungeon.handCardSelectScreen.selectedCards.group){
                wantToUseCard(c);

            }
            for(AbstractCard c:this.canNotSelectCards){
                this.p.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved=true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone=true;
        }
        tickDuration();
    }

    private void wantToUseCard(AbstractCard card){
        this.p.hand.addToTop(card);
        addToTop(new BATwinsPlayHandCardAction(card,this.target));
//        card.applyPowers();
//        card.calculateCardDamage((AbstractMonster) this.target);
//        addToTop((AbstractGameAction)new NewQueueCardAction(card, this.target, false, true));
    }
}
