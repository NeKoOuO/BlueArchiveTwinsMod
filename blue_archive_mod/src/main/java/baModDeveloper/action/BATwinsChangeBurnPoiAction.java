package baModDeveloper.action;

import baModDeveloper.power.BATwinsBurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class BATwinsChangeBurnPoiAction extends AbstractGameAction {
    private boolean gainBlock;
    private AbstractPlayer p;
    public BATwinsChangeBurnPoiAction(AbstractMonster target,boolean gainBlock){
        this.target=target;
        this.gainBlock=gainBlock;
        this.p=AbstractDungeon.player;
    }
    @Override
    public void update() {
        if(!this.target.isDeadOrEscaped()){
            AbstractPower burnPower=this.target.getPower(BATwinsBurnPower.POWER_ID);
            AbstractPower poiPower=this.target.getPower(PoisonPower.POWER_ID);
            int burnAmount=0,poiAmount=0;
            if(burnPower!=null){
                burnAmount=burnPower.amount;
            }
            if(poiPower!=null){
                poiAmount=poiPower.amount;
            }
            if(gainBlock&&Math.abs(burnAmount-poiAmount)>0){
                addToTop(new GainBlockAction(this.p,this.p,Math.abs(burnAmount-poiAmount)));
            }
            if(burnPower==null){
                if(poiAmount>0)
                    addToTop(new ApplyPowerAction(this.target,this.p,new BATwinsBurnPower(this.target,this.p,poiAmount)));
            }else if(poiAmount==0){
                addToTop(new RemoveSpecificPowerAction(this.target,this.p,burnPower));
            }else{
                burnPower.amount=poiAmount;
                burnPower.updateDescription();
            }

            if(poiPower==null){
                if(burnAmount>0)
                    addToTop(new ApplyPowerAction(this.target,this.p,new PoisonPower(this.target,this.p,burnAmount)));
            }else if(burnAmount==0){
                addToTop(new RemoveSpecificPowerAction(this.target,this.p,poiPower));
            }else{
                poiPower.amount=burnAmount;
                poiPower.updateDescription();
            }


        }
        this.isDone=true;

    }

}
