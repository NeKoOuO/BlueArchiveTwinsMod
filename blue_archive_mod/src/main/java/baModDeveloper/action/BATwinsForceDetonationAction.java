package baModDeveloper.action;

import baModDeveloper.power.BATwinsBurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class BATwinsForceDetonationAction extends AbstractGameAction {
    private int amount;
    private String powerID;

    public BATwinsForceDetonationAction(int amount, String powerID) {
        this.amount = amount;
        this.powerID = powerID;
    }

    @Override
    public void update() {
        for (int j = AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1; j >= 0; j--) {
            AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.monsters.get(j);
            if (!m.isDeadOrEscaped()) {
                if (m.hasPower(powerID)) {
                    AbstractPower power = m.getPower(powerID);
                    for (int i = 0; i < this.amount; i++) {
                        if (power instanceof PoisonPower) {
                            power.flashWithoutSound();
                            addToTop(new PoisonLoseHpAction(power.owner, AbstractDungeon.player, power.amount, AttackEffect.POISON));
                        }
                        if (power instanceof BATwinsBurnPower) {
                            power.flashWithoutSound();
                            addToTop(new DamageAction(power.owner, new DamageInfo(power.owner, power.amount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
                            power.amount--;
                        }
                    }
                }
            }
        }
        this.isDone = true;

    }
}
