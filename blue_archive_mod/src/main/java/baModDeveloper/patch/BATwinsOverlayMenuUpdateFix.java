package baModDeveloper.patch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;

@SpirePatch(clz = OverlayMenu.class,method = "update")
public class BATwinsOverlayMenuUpdateFix {
    
    @SpirePrefixPatch
    public static void OverlayMenuPostFix(OverlayMenu _instance){
        if(AbstractDungeon.player instanceof BATwinsCharacter){
            if(!(_instance.energyPanel instanceof BATwinsEnergyPanel)) {
                _instance.energyPanel = new BATwinsEnergyPanel();
            }
        }
    }
}


