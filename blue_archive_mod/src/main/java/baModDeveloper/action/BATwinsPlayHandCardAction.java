package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.patch.BATwinsAbstractCardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
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
    private int numberOfConnections;
    private boolean blockTheOriginalEffect;
    public BATwinsPlayHandCardAction(AbstractCard card, AbstractCreature target,boolean randomTarget,boolean ignoreDeath,int numberOfConnections,boolean blockTheOriginalEffect){
        this.card=card;
        this.p= AbstractDungeon.player;
        this.target=target;
        this.ignoreDeath=ignoreDeath;
        this.numberOfConnections=numberOfConnections;
        this.blockTheOriginalEffect=blockTheOriginalEffect;
        this.duration= Settings.ACTION_DUR_FAST;
    }
    BATwinsPlayHandCardAction(AbstractCard card, AbstractCreature target,boolean randomTarget,boolean ignoreDeath){
        this(card,target,randomTarget,ignoreDeath,0,false);
    }
    public BATwinsPlayHandCardAction(AbstractCard card,AbstractCreature target){
        this(card,target,false,true);
    }
    public BATwinsPlayHandCardAction(AbstractCard card,AbstractCreature target,boolean ignoreDeath){
        this(card,target,false,ignoreDeath);
    }
    public BATwinsPlayHandCardAction(AbstractCard card,AbstractCreature target,int numberOfConnections){
        this(card,target,false,true,1,false);
    }
    public BATwinsPlayHandCardAction(AbstractCard card,AbstractCreature target,int numberOfConnections,boolean blockTheOriginalEffect){
        this(card,target,false,true,1,blockTheOriginalEffect);
    }

    @Override
    public void update() {
        if(this.target==null){
            this.target=AbstractDungeon.getCurrRoom().monsters.getRandomMonster();
        }
        if(!this.target.isDead&&!card.isInAutoplay){
            this.card.applyPowers();
            this.card.calculateCardDamage((AbstractMonster) this.target);
//            this.p.hand.removeCard(this.card);
            AbstractDungeon.getCurrRoom().souls.remove(this.card);
//            card.current_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
//            card.current_y = 0.0F * Settings.scale;
//            card.target_x = (float) Settings.WIDTH / 2.0F - 200.0F * Settings.xScale;
//            card.target_y = (float) Settings.HEIGHT / 2.0F;
//            AbstractDungeon.player.limbo.group.add(this.card);
            if(card instanceof BATwinsModCustomCard){
                ((BATwinsModCustomCard) card).numberOfConnections=this.numberOfConnections;
                ((BATwinsModCustomCard) card).blockTheOriginalEffect=this.blockTheOriginalEffect;
            }else{
                BATwinsAbstractCardPatch.FieldPatch.blockTheOriginalEffect.set(card,true);
            }
            card.isInAutoplay=true;
//            this.card.isInAutoplay=true;
//            addToTop(new ShowCardAction(this.card));
//            this.p.limbo.ad
//            if(card instanceof BATwinsModCustomCard){
//                ((BATwinsModCustomCard) card).playedByOtherCard=true;
//            }
//            addToTop(new UnlimboAction(this.card));
            addToTop((AbstractGameAction)new NewQueueCardAction(card, this.target, false, true));
//            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(this.card, (AbstractMonster) this.target,card.energyOnUse,true,true),true);
        }
        this.isDone=true;
    }
}
