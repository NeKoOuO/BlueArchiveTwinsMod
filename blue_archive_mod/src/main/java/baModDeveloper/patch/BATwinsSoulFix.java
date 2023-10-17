package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.Soul;

@SpirePatch(clz = Soul.class,method = "updateMovement",paramtypez = {})
public class BATwinsSoulFix {
    @SpireInsertPatch(rloc = 82)
    public static void updateMovementFix(Soul _instance){
        if(_instance.card instanceof BATwinsModCustomCard){
            BATwinsCardTrailEffectFix.cardType=((BATwinsModCustomCard)_instance.card).energyType;
        }
    }

}
