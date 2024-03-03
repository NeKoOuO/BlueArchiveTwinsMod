package baModDeveloper.patch;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = OverlayMenu.class, method = "update")
public class BATwinsOverlayMenuUpdateFix {

    @SpirePrefixPatch
    public static void OverlayMenuPostFix(OverlayMenu _instance) {
        if (AbstractDungeon.player instanceof BATwinsCharacter) {
            if (!(_instance.energyPanel instanceof BATwinsEnergyPanel)) {
                _instance.energyPanel = new BATwinsEnergyPanel();
            }
        }
    }
}


