package baModDeveloper.action;

import baModDeveloper.core.BATwinsEnergyManager;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class BATwinsCoverChargeAction extends AbstractGameAction {
    private final int damage;
    private final int block;
    private boolean freeToPlayOnce = false;
    private final AbstractPlayer p;

    public int energyOnUseMomoi=-1;
    public int energyOnUseMidori=-1;

    public BATwinsCoverChargeAction(AbstractPlayer p, int damage, int block, boolean freeToPlayOnce, AbstractMonster target,int energyOnUseMomoi,int energyOnUseMidori) {
        this.damage = damage;
        this.block = block;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.target = target;
        this.energyOnUseMomoi=energyOnUseMomoi;
        this.energyOnUseMidori=energyOnUseMidori;
    }

    @Override
    public void update() {
        int MOMOICount = 0;
        int MIDORICount = 0;
        if (this.p.energy instanceof BATwinsEnergyManager) {
            MOMOICount = BATwinsEnergyPanel.getMomoiCount();
            MIDORICount = BATwinsEnergyPanel.getMidoriCount();
        } else {
            int effect = EnergyPanel.totalCount;
            MOMOICount = effect / 2;
            MIDORICount = effect - MOMOICount;
        }
        if(this.energyOnUseMomoi!=-1){
            MOMOICount=energyOnUseMomoi;
        }
        if(this.energyOnUseMidori!=-1){
            MIDORICount=energyOnUseMidori;
        }

        if (this.p.hasRelic("Chemical X")) {
            MOMOICount += 2;
            MIDORICount += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (MOMOICount > 0) {
            for (int i = 0; i < MOMOICount; i++) {
                addToBot(new DamageAction(this.target, new DamageInfo(this.p, this.damage), AttackEffect.LIGHTNING));
            }
        }
        if (MIDORICount > 0) {
            for (int i = 0; i < MIDORICount; i++) {
                addToBot(new GainBlockAction(this.p, this.block));
            }
        }
        if (!this.freeToPlayOnce) {
            if (this.p.energy instanceof BATwinsEnergyManager) {
                ((BATwinsEnergyManager) this.p.energy).use(MOMOICount, BATwinsEnergyPanel.EnergyType.MOMOI);
                ((BATwinsEnergyManager) this.p.energy).use(MIDORICount, BATwinsEnergyPanel.EnergyType.MIDORI);
            } else {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}
