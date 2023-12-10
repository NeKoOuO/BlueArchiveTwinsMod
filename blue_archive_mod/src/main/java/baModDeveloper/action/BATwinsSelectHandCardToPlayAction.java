package baModDeveloper.action;

import baModDeveloper.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
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
    private int amount;
    private int numberOfConnections;
    private boolean blockTheOriginalEffect;
    private UIStrings UISTRINGS= CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    public BATwinsSelectHandCardToPlayAction(boolean isAllColor,AbstractCard.CardColor color, AbstractMonster target,boolean isAllType, AbstractCard.CardType type,int amount,int numberOfConnections,boolean blockTheOriginalEffect){
        this.color=color;
        this.p= AbstractDungeon.player;
        this.target=target;
        this.type=type;
        this.duration= Settings.ACTION_DUR_FAST;
        this.isAllColor=isAllColor;
        this.isAllType=isAllType;
        this.amount=amount;
        this.numberOfConnections=numberOfConnections;
        this.blockTheOriginalEffect=blockTheOriginalEffect;
    }
    public BATwinsSelectHandCardToPlayAction(AbstractCard.CardColor color, AbstractMonster target, AbstractCard.CardType type,int amount,int numberOfConnections){
        this(false,color,target,false,type,amount,numberOfConnections,false);
    }

    public BATwinsSelectHandCardToPlayAction(boolean isAllColor,boolean isAllType){
        this(null,null,null);
        this.isAllColor=isAllColor;
        this.isAllType=isAllType;
    }
    public BATwinsSelectHandCardToPlayAction(AbstractCard.CardColor color, AbstractMonster target, AbstractCard.CardType type){
        this(color,target,type,1,1);
    }
    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_FAST){
            for(AbstractCard c:this.p.hand.group){
                if(!this.isAllColor&&c.color!=this.color){
                    this.canNotSelectCards.add(c);
                } else if (!this.isAllType&&c.type!=this.type) {
                    this.canNotSelectCards.add(c);
                }else if(c.isInAutoplay){
                    this.canNotSelectCards.add(c);
                }
            }
            if(this.canNotSelectCards.size()==this.p.hand.group.size()){
                this.isDone=true;
                return;
            }
            this.p.hand.group.removeAll(this.canNotSelectCards);
            this.amount= Math.min(this.amount, this.p.hand.size());
            if(!this.p.hand.group.isEmpty()){
                AbstractDungeon.handCardSelectScreen.open(UISTRINGS.TEXT[0],this.amount,false,false,false);
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
//        this.p.hand.addToTop(card);
        if(this.target==null){
            this.target=AbstractDungeon.getCurrRoom().monsters.getRandomMonster();
        }
        addToTop(new BATwinsPlayHandCardAction(card,this.target,this.numberOfConnections,this.blockTheOriginalEffect));
//        card.applyPowers();
//        card.calculateCardDamage((AbstractMonster) this.target);
//        addToTop((AbstractGameAction)new NewQueueCardAction(card, this.target, false, true));
    }
}
