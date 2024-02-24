package baModDeveloper.action;

import baModDeveloper.power.BATwinsBurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsForceDetonationAction extends AbstractGameAction {
    private  int amount;
    private String powerID;
    public BATwinsForceDetonationAction(int amount,String powerID){
        this.amount=amount;
        this.powerID=powerID;
    }
    @Override
    public void update() {
        for(AbstractMonster m:AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!m.isDeadOrEscaped()){
                if(m.hasPower(powerID)){
                    for(int i=0;i<this.amount;i++){
                        m.getPower(powerID).atStartOfTurn();
                        m.getPower(powerID).atEndOfTurn(false);
                    }
                    m.getPower(powerID).amount/=2;
                }
            }
        }
        this.isDone=true;

    }
}
