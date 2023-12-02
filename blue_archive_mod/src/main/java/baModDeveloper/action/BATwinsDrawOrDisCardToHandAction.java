package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class BATwinsDrawOrDisCardToHandAction extends AbstractGameAction {

    private AbstractCard card;
    private boolean randomCard;
    private AbstractPlayer p;
    public enum Pile{
        DrawPile,DiscardPile,ALL
    }
    private Pile pile;
    public BATwinsDrawOrDisCardToHandAction(boolean randomCard,AbstractCard card,Pile pile){
        this.card=card;
        this.p=AbstractDungeon.player;
        this.randomCard=randomCard;
        this.pile=pile;
        this.actionType= ActionType.CARD_MANIPULATION;
        this.duration= Settings.ACTION_DUR_MED;
    }
    public BATwinsDrawOrDisCardToHandAction(AbstractCard card){
        this(false,card,Pile.ALL);
    }
    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_MED){
            if(this.pile==Pile.DrawPile){
                if(this.p.drawPile.isEmpty()){
                    this.isDone=true;
                    return;
                }
                if(randomCard){
                    this.card=this.p.drawPile.getRandomCard(AbstractDungeon.cardRandomRng);
                }
                card.unhover();
                card.lighten(true);
                this.p.drawPile.addToHand(card);
                this.p.hand.refreshHandLayout();
                this.p.hand.applyPowers();
            } else if (this.pile==Pile.DiscardPile) {
                if(this.p.discardPile.isEmpty()){
                    this.isDone=true;
                    return;
                }
                if(randomCard){
                    this.card=this.p.discardPile.getRandomCard(AbstractDungeon.cardRandomRng);
                }
                card.unhover();
                card.lighten(true);
                this.p.discardPile.addToHand(card);
                this.p.hand.refreshHandLayout();
                this.p.hand.applyPowers();
            } else if (this.pile==Pile.ALL) {
                if(this.card==null){
                    this.isDone=true;
                    return;
                }
                if(this.p.drawPile.contains(this.card)){
                    addToTop(new BATwinsDrawOrDisCardToHandAction(false,this.card,Pile.DrawPile));
                } else if (this.p.discardPile.contains(this.card)) {
                    addToTop(new BATwinsDrawOrDisCardToHandAction(false,this.card,Pile.DiscardPile));
                }
                this.isDone=true;
                return;
            }

        }
        this.isDone=true;
        tickDuration();
    }
}
