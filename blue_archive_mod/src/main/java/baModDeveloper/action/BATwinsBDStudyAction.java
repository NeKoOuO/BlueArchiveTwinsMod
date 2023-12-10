package baModDeveloper.action;

import baModDeveloper.cards.BATwinsBDStudy;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsExperiencePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class BATwinsBDStudyAction extends AbstractGameAction {
    private int amount;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> canNotSelect;
    private UIStrings UISTRING= CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));
    public BATwinsBDStudyAction(int amount){
        this.p= AbstractDungeon.player;
        this.canNotSelect=new ArrayList<>();
        this.amount=amount;
        this.duration= Settings.ACTION_DUR_FAST;
    }
    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_FAST){
            this.p.hand.group.stream().filter(card -> card.isInAutoplay).forEach(card -> this.canNotSelect.add(card));
            this.p.hand.group.removeAll(this.canNotSelect);
            if(this.p.hand.isEmpty()){
                this.isDone=true;
                return;
            }
            if(this.p.hand.size()==1){
                WhatToDoWithCard(this.p.hand.getTopCard());
                this.isDone=true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(String.format(UISTRING.TEXT[1],this.amount),this.amount,false,false,false,false);
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
        if(card.costForTurn>0){
            addToTop(new ApplyPowerAction(this.p,this.p,new BATwinsExperiencePower(this.p,card.costForTurn*2)));
            this.p.hand.moveToExhaustPile(card);
        }
        if(!this.canNotSelect.isEmpty()){
            this.p.hand.group.addAll(this.canNotSelect);
        }
    }
}
