package baModDeveloper.action;

import baModDeveloper.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BATwinsSelectDrawPileCardToPlayAction extends AbstractGameAction {
    private boolean isRandom;
    private AbstractPlayer p;
    private int numberOfConnections;
    private AbstractCard.CardColor color;
    private ArrayList<AbstractCard> canNotSelect;
    private UIStrings UISTRINGS= CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom){
        this(isRandom,null);
    }
    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractMonster target, int numberOfConnections, AbstractCard.CardColor color){
        this.isRandom=isRandom;
        this.target=target;
        this.p=AbstractDungeon.player;
        this.numberOfConnections=numberOfConnections;
        this.color=color;
        this.canNotSelect=new ArrayList<>();
        this.duration= Settings.ACTION_DUR_FAST;
    }
    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractMonster target, int numberOfConnections){
        this(isRandom,target,numberOfConnections,null);
    }
    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractMonster target){
        this(isRandom,target,1);
    }
    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom,int numberOfConnections){
        this(isRandom, (AbstractMonster) null,numberOfConnections);
    }
    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractCard.CardColor color,int numberOfConnections){
        this(isRandom,null,numberOfConnections,color);
    }
    @Override
    public void update() {
        if(this.p.drawPile.isEmpty()){
            this.isDone=true;
            return;
        }
        if(this.isRandom){
            if(this.color!=null){
                this.p.drawPile.group.stream().filter(card->card.color!=BATwinsSelectDrawPileCardToPlayAction.this.color).forEach(card->BATwinsSelectDrawPileCardToPlayAction.this.canNotSelect.add(card));
            }
            this.p.drawPile.group.removeAll(this.canNotSelect);
            if(!this.p.drawPile.group.isEmpty()){
                WhatTheCardDo(this.p.drawPile.getRandomCard(AbstractDungeon.cardRandomRng));
            }
            this.isDone=true;
            tickDuration();
            return;
        }else if (this.duration==Settings.ACTION_DUR_FAST){
            if(this.color!=null){
                this.p.drawPile.group.stream().filter(card->card.color!=BATwinsSelectDrawPileCardToPlayAction.this.color||card.isInAutoplay).forEach(card->BATwinsSelectDrawPileCardToPlayAction.this.canNotSelect.add(card));
            }
            this.p.drawPile.group.removeAll(this.canNotSelect);
            AbstractDungeon.gridSelectScreen.open(this.p.drawPile,1,false,UISTRINGS.TEXT[0]);
            tickDuration();
            return;
        }
        if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
            for(AbstractCard c:AbstractDungeon.gridSelectScreen.selectedCards){
                WhatTheCardDo(c);
            }
            this.isDone=true;
            return;
        }
    }

    private void WhatTheCardDo(AbstractCard c){
        if(this.target==null){
            this.target=AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        }
        addToTop(new BATwinsPlayDrawPailCardAction(c,this.target,false,this.numberOfConnections));
        if(!this.canNotSelect.isEmpty()){
            this.p.drawPile.group.addAll(this.canNotSelect);
        }
    }

}
