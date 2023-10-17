package baModDeveloper.core;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import baModDeveloper.ui.panels.BATwinsEnergyPanel.EnergyType;

public class BATwinsEnergyManager extends EnergyManager{

    public BATwinsEnergyManager(int e) {
        super(e);
    }

    @Override
    public void prep(){
        super.prep();
        BATwinsEnergyPanel.MomoiCount=0;
        BATwinsEnergyPanel.MidoriCount=0;
        BATwinsEnergyPanel.selectedEnergySlot=EnergyType.MOMOI;
    }

    @Override
    public void recharge(){
        if (AbstractDungeon.player.hasRelic("Ice Cream")) {
            if (EnergyPanel.totalCount > 0) {
                AbstractDungeon.player.getRelic("Ice Cream").flash();
                AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, AbstractDungeon.player
                    .getRelic("Ice Cream")));
            } 
            BATwinsEnergyPanel.addEnergy(this.energy,EnergyType.ALL);
        } else if (AbstractDungeon.player.hasPower("Conserve")) {
            if (EnergyPanel.totalCount > 0)
                AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ReducePowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, "Conserve", 1)); 
            BATwinsEnergyPanel.addEnergy(this.energy,EnergyType.ALL);
        } else {
            BATwinsEnergyPanel.setEnergy(this.energy,EnergyType.ALL);
        } 
        AbstractDungeon.actionManager.updateEnergyGain(this.energy);
    }


    @Override
    public void use(int e){
        BATwinsEnergyPanel.useEnergy(e,EnergyType.SPEIFY);
    }

    public void use(int e,EnergyType energyType){
        BATwinsEnergyPanel.useEnergy(e,energyType);
    }
}
