package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class BATwinsHalfPowerAction extends AbstractGameAction {
    private String powerId;
    public BATwinsHalfPowerAction(String powerId, AbstractCreature target){
        this.powerId=powerId;
        this.target=target;
    }
    @Override
    public void update() {
        if(!target.isDeadOrEscaped()&&this.target.hasPower(powerId)){
            addToBot(new ReducePowerAction(this.target,this.target,powerId,this.target.getPower(powerId).amount/2));
        }
        this.isDone=true;
    }
}
