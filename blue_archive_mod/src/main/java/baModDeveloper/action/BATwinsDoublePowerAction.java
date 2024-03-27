package baModDeveloper.action;

import baModDeveloper.power.BATwinsBurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BATwinsDoublePowerAction extends AbstractGameAction {
    private String powerId;
    public BATwinsDoublePowerAction(String powerId, AbstractCreature target){
        this.powerId=powerId;
        this.target=target;
    }
    @Override
    public void update() {
        if(!target.isDeadOrEscaped()){
            AbstractPower p=this.target.getPower(this.powerId);
            if(p!=null&&p.amount!=-1){
                addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player,new BATwinsBurnPower(this.target,AbstractDungeon.player,p.amount)));
            }
        }
        this.isDone=true;
    }
}
