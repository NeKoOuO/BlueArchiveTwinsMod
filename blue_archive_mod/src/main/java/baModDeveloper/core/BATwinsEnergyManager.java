package baModDeveloper.core;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import baModDeveloper.ui.panels.BATwinsEnergyPanel.EnergyType;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class BATwinsEnergyManager extends EnergyManager {

    public BATwinsEnergyManager(int e) {
        super(e);
    }

    @Override
    public void prep() {
        super.prep();
        BATwinsEnergyPanel.MomoiCount = 0;
        BATwinsEnergyPanel.MidoriCount = 0;
//        BATwinsEnergyPanel.selectedEnergySlot = EnergyType.MOMOI;
    }

    @Override
    public void recharge() {
        if (AbstractDungeon.player.hasRelic("Ice Cream")) {
            if (EnergyPanel.totalCount > 0) {
                AbstractDungeon.player.getRelic("Ice Cream").flash();
                AbstractDungeon.actionManager.addToTop((AbstractGameAction) new RelicAboveCreatureAction((AbstractCreature) AbstractDungeon.player, AbstractDungeon.player
                        .getRelic("Ice Cream")));
            }
            BATwinsEnergyPanel.addEnergy(this.energy, EnergyType.ALL);
        } else if (AbstractDungeon.player.hasPower("Conserve")) {
            if (EnergyPanel.totalCount > 0)
                AbstractDungeon.actionManager.addToTop((AbstractGameAction) new ReducePowerAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player, "Conserve", 1));
            BATwinsEnergyPanel.addEnergy(this.energy, EnergyType.ALL);
        } else {
            BATwinsEnergyPanel.setEnergy(this.energy, EnergyType.ALL);
        }
        AbstractDungeon.actionManager.updateEnergyGain(this.energy);
    }


    @Override
    public void use(int e) {
        BATwinsEnergyPanel.useEnergy(e, EnergyType.SHARE);
    }

    public void use(int e, EnergyType energyType) {
        int priviousMOMOICount = BATwinsEnergyPanel.getMomoiCount();
        int priviousMIDORICount = BATwinsEnergyPanel.getMidoriCount();
        BATwinsEnergyPanel.useEnergy(e, energyType);
//        triggerEnengyUseEffect(e,energyType);
        if (BATwinsEnergyPanel.getMomoiCount() == 0 && priviousMOMOICount != 0) {
            triggerOnEnengyExhausted(EnergyType.MOMOI);
        } else if (BATwinsEnergyPanel.getMidoriCount() == 0 && priviousMIDORICount != 0) {
            triggerOnEnengyExhausted(EnergyType.MIDORI);
        }
    }

    //    private void triggerEnengyUseEffect(int e,EnergyType energyType){
//        AbstractPlayer p=AbstractDungeon.player;
//        for(AbstractCard card:p.hand.group){
//            if(card instanceof BATwinsModCustomCard){
//                ((BATwinsModCustomCard) card).triggerOnEnergyUse(e,energyType);
//            }
//        }
//        for(AbstractCard card:p.drawPile.group){
//            if(card instanceof BATwinsModCustomCard){
//                ((BATwinsModCustomCard) card).triggerOnEnergyUse(e,energyType);
//            }
//        }
//        for(AbstractCard card:p.discardPile.group){
//            if(card instanceof BATwinsModCustomCard){
//                ((BATwinsModCustomCard) card).triggerOnEnergyUse(e,energyType);
//            }
//        }
//
//    }
    private void triggerOnEnengyExhausted(EnergyType energyType) {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard card : p.hand.group) {
            if (card instanceof BATwinsModCustomCard) {
                ((BATwinsModCustomCard) card).triggerOnEnergyExhausted(energyType);
            }
        }
        for (AbstractCard card : p.drawPile.group) {
            if (card instanceof BATwinsModCustomCard) {
                ((BATwinsModCustomCard) card).triggerOnEnergyExhausted(energyType);
            }
        }
        for (AbstractCard card : p.discardPile.group) {
            if (card instanceof BATwinsModCustomCard) {
                ((BATwinsModCustomCard) card).triggerOnEnergyExhausted(energyType);
            }
        }
    }

}
