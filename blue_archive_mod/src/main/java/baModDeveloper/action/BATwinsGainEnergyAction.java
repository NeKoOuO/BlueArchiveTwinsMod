package baModDeveloper.action;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import javax.swing.*;
import java.util.Iterator;

public class BATwinsGainEnergyAction extends AbstractGameAction {
    private int energyGain;
    private BATwinsEnergyPanel.EnergyType type;

    public BATwinsGainEnergyAction(int amount, BATwinsEnergyPanel.EnergyType type) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, 0);
        this.energyGain = amount;
        this.type = type;
        this.duration = Settings.ACTION_DUR_FAST;

    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player instanceof BATwinsCharacter) {
                ((BATwinsCharacter) AbstractDungeon.player).gainEnergy(this.energyGain, this.type);
                AbstractDungeon.actionManager.updateEnergyGain(this.energyGain);
                Iterator var1 = AbstractDungeon.player.hand.group.iterator();

                while (var1.hasNext()) {
                    AbstractCard c = (AbstractCard) var1.next();
                    c.triggerOnGainEnergy(this.energyGain, true);
                }

            }

        }
        this.tickDuration();
    }
}
