package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsSelectDrawPileCardToPlayAction extends AbstractGameAction {
    private boolean isRandom;
    private AbstractPlayer p;
    private int numberOfConnections;
    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom){
        this(isRandom,null);
    }
    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractMonster target,int numberOfConnections){
        this.isRandom=isRandom;
        this.target=target;
        this.p=AbstractDungeon.player;
        this.numberOfConnections=numberOfConnections;
        this.duration= Settings.ACTION_DUR_FAST;
    }
    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractMonster target){
        this(isRandom,target,1);
    }
    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom,int numberOfConnections){
        this(isRandom,null,numberOfConnections);
    }
    @Override
    public void update() {

        if(this.isRandom){
            WhatTheCardDo(this.p.drawPile.getRandomCard(AbstractDungeon.cardRandomRng));
            this.isDone=true;
            tickDuration();
            return;
        }else if (this.duration==Settings.ACTION_DUR_FAST){
            AbstractDungeon.gridSelectScreen.open(this.p.drawPile,1,false,"");
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
            this.target=AbstractDungeon.getCurrRoom().monsters.getRandomMonster();
        }
        addToTop(new BATwinsPlayDrawPailCardAction(c,this.target,false,this.numberOfConnections));
    }

}
