package baModDeveloper.action;

import baModDeveloper.power.BATwinsBurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.Objects;

public class BATwinsDoublePowerAction extends AbstractGameAction {
    private String powerId;
    private float multiple;

    public BATwinsDoublePowerAction(String powerId, AbstractCreature target) {
        this.powerId = powerId;
        this.target = target;
        this.multiple=1.0F;
    }
    public BATwinsDoublePowerAction(String powerId,AbstractCreature target,float multiple){
        this(powerId,target);
        this.multiple=multiple;
    }

    @Override
    public void update() {
        if (!target.isDeadOrEscaped()) {
            AbstractPower p = this.target.getPower(this.powerId);
            if (p != null && p.amount != -1) {
                if(Objects.equals(this.powerId, PoisonPower.POWER_ID)){
                    addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player, new PoisonPower(this.target, AbstractDungeon.player, (int) (p.amount*this.multiple))));
                } else if (Objects.equals(this.powerId, BATwinsBurnPower.POWER_ID)) {
                    addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player, new BATwinsBurnPower(this.target, AbstractDungeon.player, (int) (p.amount*this.multiple))));
                }
            }
        }
        this.isDone = true;
    }
}
