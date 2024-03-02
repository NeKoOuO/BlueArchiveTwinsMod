package baModDeveloper.patch;

import baModDeveloper.helpers.ModHelper;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.runHistory.RunPathElement;
import com.megacrit.cardcrawl.screens.stats.CampfireChoice;

public class BATwinsRunPathElementPatch {
    @SpirePatch(clz = RunPathElement.class, method = "getTipDescriptionText")
    public static class getTipHeaderWithRoomTypeTextPatch {
        @SpireInsertPatch(rloc = 244, localvars = {"sb"})
        public static void getTipDescriptionTextPatch(RunPathElement __instance, CampfireChoice ___campfireChoice, String ___TEXT_MISSING_INFO, @ByRef StringBuilder[] sb) {
            if (___campfireChoice != null) {
                if (___campfireChoice.key.equals(ModHelper.makePath("EXCHANGE"))) {
                    String TEXT_EXCHANGE_OPTION = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("Exchange_Option")).TEXT[0];
                    int index = sb[0].lastIndexOf(___TEXT_MISSING_INFO);
                    sb[0].replace(index, sb[0].length(), String.format(TEXT_EXCHANGE_OPTION, new Object[]{CardLibrary.getCardNameFromMetricID(___campfireChoice.data)}));
                }
            }

        }
    }
}
