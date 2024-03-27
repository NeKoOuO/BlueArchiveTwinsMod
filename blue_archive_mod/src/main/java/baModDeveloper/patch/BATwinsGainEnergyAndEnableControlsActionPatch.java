package baModDeveloper.patch;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.GainEnergyAndEnableControlsAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsGainEnergyAndEnableControlsActionPatch {
    @SpirePatch(clz = GainEnergyAndEnableControlsAction.class, method = "update")
    public static class updatePatch {
        @SpireInsertPatch(rloc = 3)
        public static void updatePatch(GainEnergyAndEnableControlsAction _instance, int ___energyGain) {
            if (AbstractDungeon.player instanceof BATwinsCharacter) {
                ((BATwinsCharacter) AbstractDungeon.player).loseEnergy(___energyGain);
                ((BATwinsCharacter) AbstractDungeon.player).gainEnergy(___energyGain, BATwinsEnergyPanel.EnergyType.ALL);
            }
        }
    }
}
