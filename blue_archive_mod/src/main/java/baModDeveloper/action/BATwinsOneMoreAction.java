package baModDeveloper.action;

import baModDeveloper.power.BATwinsBurnPower;
import baModDeveloper.power.BATwinsOnceMorePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsOneMoreAction extends AbstractGameAction {
    private String powerid;
    private boolean allEnemy=false;
    public BATwinsOneMoreAction(String powerid,boolean allEnemy){
        this.powerid=powerid;
        this.allEnemy=allEnemy;
    }
    public BATwinsOneMoreAction(String powerid,AbstractMonster target){
        this.powerid=powerid;
        this.target=target;
    }
    @Override
    public void update() {
        if(allEnemy){
            for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters){
                if(m.hasPower(this.powerid)){
                    m.getPower(this.powerid).atStartOfTurn();
                    m.getPower(this.powerid).atEndOfTurn(false);
                }
            }
        }else{
            if(this.target!=null){
                if(target.hasPower(this.powerid)){
                    target.getPower(this.powerid).atStartOfTurn();
                    target.getPower(this.powerid).atEndOfTurn(false);
                }
            }
        }
        this.isDone=true;
    }
}
