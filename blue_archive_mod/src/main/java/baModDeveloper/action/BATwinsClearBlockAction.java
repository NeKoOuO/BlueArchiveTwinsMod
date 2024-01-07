package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class BATwinsClearBlockAction extends AbstractGameAction {
    public BATwinsClearBlockAction(AbstractCreature target){
        this.target=target;
        this.duration= Settings.ACTION_DUR_FAST;
    }
    @Override
    public void update() {
        if(!target.isDeadOrEscaped()){
            if(target.currentBlock>0)
                target.loseBlock();
        }
        this.isDone=true;

    }
}
