package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BATwinsSelectHandCardToPlayAction extends AbstractGameAction {
    private final AbstractCard.CardColor color;
    private final AbstractPlayer p;
    private final ArrayList<AbstractCard> canNotSelectCards=new ArrayList<>();
    private final AbstractCard.CardType type;
    private boolean isRandom;
    private boolean isRandomTarget;
    private boolean isAllColor;
    private boolean isAllType;
    private int numberOfConnections;
    public BATwinsSelectHandCardToPlayAction(AbstractCard.CardColor color, AbstractMonster target, AbstractCard.CardType type,int numberOfConnections){
        this.color=color;
        this.p= AbstractDungeon.player;
        this.target=target;
        this.type=type;
        this.duration= Settings.ACTION_DUR_FAST;
        this.isAllColor=false;
        this.isAllType=false;
        this.numberOfConnections=numberOfConnections;
    }
    public BATwinsSelectHandCardToPlayAction(boolean isAllColor,boolean isAllType){
        this(null,null,null);
        this.isAllColor=isAllColor;
        this.isAllType=isAllType;
    }
    public BATwinsSelectHandCardToPlayAction(AbstractCard.CardColor color, AbstractMonster target, AbstractCard.CardType type){
        this(color,target,type,1);
    }
    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_FAST){
            for(AbstractCard c:this.p.hand.group){
                if(!this.isAllColor&&c.color!=this.color){
                    this.canNotSelectCards.add(c);
                } else if (!this.isAllType&&c.type!=this.type) {
                    this.canNotSelectCards.add(c);
                }
            }
            if(this.canNotSelectCards.size()==this.p.hand.group.size()){
                this.isDone=true;
                return;
            }
            this.p.hand.group.removeAll(this.canNotSelectCards);
            if(!this.p.hand.group.isEmpty()){
                AbstractDungeon.handCardSelectScreen.open("",1,false,false,false);
                tickDuration();
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
        if(this.target==null){
            this.target=AbstractDungeon.getCurrRoom().monsters.getRandomMonster();
        }
        addToTop(new BATwinsPlayHandCardAction(card,this.target,this.numberOfConnections));
//        card.applyPowers();
//        card.calculateCardDamage((AbstractMonster) this.target);
//        addToTop((AbstractGameAction)new NewQueueCardAction(card, this.target, false, true));
    }
}
