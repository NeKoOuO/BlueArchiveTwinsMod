package baModDeveloper.action;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class BATwinsAccelerateAction extends BATwinsSelectDrawPileCardToPlayAction {
    AbstractPlayer p;
    //    private ArrayList<AbstractCard> canNotSelected;
    private boolean freeToPlay;
    private int enegyOnUse = -1;
    private boolean upgraded;

    public BATwinsAccelerateAction(int numOfConnection, int energyOnUse, boolean freeToPlay, boolean upgraded) {
        super(false, (AbstractMonster) null, numOfConnection);
//        canNotSelected=new ArrayList<>();
        this.freeToPlay = freeToPlay;
        this.enegyOnUse = energyOnUse;
        this.p = AbstractDungeon.player;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int energy = EnergyPanel.totalCount;
            if (this.enegyOnUse != -1) {
                energy = this.enegyOnUse;
            }
            if (this.upgraded) {
                energy += 1;
            }
            if (this.p.hasRelic("Chemical X")) {
                energy += 2;
                this.p.getRelic("Chemical X").flash();
            }
            if (energy < 0) {
                this.isDone = true;
                return;
            }
            for (AbstractCard c : this.p.drawPile.group) {
                if (c.cost > energy) {
                    this.canNotSelect.add(c);
                }
            }
            if (!this.canNotSelect.isEmpty()) {
                this.p.drawPile.group.removeAll(this.canNotSelect);
            }

            if (!this.freeToPlay) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        super.update();


    }
}
