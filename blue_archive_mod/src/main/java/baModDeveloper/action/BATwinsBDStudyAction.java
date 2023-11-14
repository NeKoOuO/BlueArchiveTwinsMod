package baModDeveloper.action;

import baModDeveloper.cards.BATwinsBDStudy;
import baModDeveloper.power.BATwinsExperiencePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsBDStudyAction extends AbstractGameAction {
    private int amount;
    private AbstractPlayer p;
    public BATwinsBDStudyAction(int amount){
        this.p= AbstractDungeon.player;
        this.amount=amount;
        this.duration= Settings.ACTION_DUR_FAST;
    }
    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_FAST){
            if(this.p.hand.isEmpty()){
                this.isDone=true;
                return;
            }
            if(this.p.hand.size()==1){
                WhatToDoWithCard(this.p.hand.getTopCard());
                this.isDone=true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open("",this.amount,false,false,false,false);
            tickDuration();
            return;
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            for(AbstractCard c:AbstractDungeon.handCardSelectScreen.selectedCards.group){
                WhatToDoWithCard(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved=true;
            this.isDone=true;
            tickDuration();
        }
    }
    private void WhatToDoWithCard(AbstractCard card){
        addToTop(new ApplyPowerAction(this.p,this.p,new BATwinsExperiencePower(this.p,card.costForTurn*2)));
        this.p.hand.moveToExhaustPile(card);
    }
}
