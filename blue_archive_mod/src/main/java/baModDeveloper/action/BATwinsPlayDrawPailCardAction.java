package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsPlayDrawPailCardAction extends AbstractGameAction {
    private AbstractCard card;
    private boolean exhaust;
    public BATwinsPlayDrawPailCardAction(AbstractCard card,AbstractCreature target,boolean exhaust){
        this.card=card;
        this.exhaust=exhaust;
        this.target=target;
        this.duration= Settings.ACTION_DUR_FAST;
        this.actionType= ActionType.WAIT;
    }
    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_FAST){
            if(AbstractDungeon.player.drawPile.contains(card)){
                AbstractDungeon.player.drawPile.group.remove(this.card);
                AbstractDungeon.getCurrRoom().souls.remove(this.card);
                card.exhaust=this.exhaust;
                AbstractDungeon.player.limbo.group.add(this.card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                card.target_y = Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale=0.12F;
                card.targetDrawScale=0.75F;

                card.applyPowers();
                addToTop(new NewQueueCardAction(card,this.target,false,true));
                addToTop(new UnlimboAction(card));
                if(Settings.FAST_MODE){
                    addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                }else{
                    addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }
        }
        this.isDone=true;
    }
}
