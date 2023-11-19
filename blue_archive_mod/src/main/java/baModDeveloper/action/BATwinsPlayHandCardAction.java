package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsPlayHandCardAction extends AbstractGameAction {
    public AbstractCard card;
    private AbstractPlayer p;
    private boolean ignoreDeath;
    public BATwinsPlayHandCardAction(AbstractCard card, AbstractCreature target,boolean randomTarget,boolean ignoreDeath){
        this.card=card;
        this.p= AbstractDungeon.player;
        this.target=target;
        this.ignoreDeath=ignoreDeath;
        this.duration= Settings.ACTION_DUR_FAST;
    }
    public BATwinsPlayHandCardAction(AbstractCard card,AbstractCreature target){
        this(card,target,false,true);
    }
    public BATwinsPlayHandCardAction(AbstractCard card,AbstractCreature target,boolean ignoreDeath){
        this(card,target,false,ignoreDeath);
    }
    @Override
    public void update() {
        if(this.p.hand.contains(card)&&!this.target.isDead){
            this.card.applyPowers();
            this.card.calculateCardDamage((AbstractMonster) this.target);
//            this.p.hand.removeCard(this.card);
            AbstractDungeon.getCurrRoom().souls.remove(this.card);
//            this.card.isInAutoplay=true;
//            addToTop(new ShowCardAction(this.card));
//            this.p.limbo.ad
//            if(card instanceof BATwinsModCustomCard){
//                ((BATwinsModCustomCard) card).playedByOtherCard=true;
//            }
            addToTop((AbstractGameAction)new NewQueueCardAction(card, this.target, false, true));

//            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(this.card, (AbstractMonster) this.target,card.energyOnUse,true,true),true);
        }
        this.isDone=true;
    }
}
