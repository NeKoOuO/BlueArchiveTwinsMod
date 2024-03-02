package baModDeveloper.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;

public class BATwinsCancelButtonPatch {
    @SpirePatch(clz = CancelButton.class, method = "update")
    public static class updatePatch {
        @SpireInsertPatch(rloc = 44)
        public static void updatePatch(CancelButton __instance) {
            if (BATwinsGridCardSelectScreenPatch.FiledPatch.forExchange.get(AbstractDungeon.gridSelectScreen)) {
                AbstractDungeon.gridSelectScreen.forUpgrade = true;
            }
        }

        @SpireInsertPatch(rloc = 46)
        public static void updatePatch2(CancelButton __instance) {
            if (BATwinsGridCardSelectScreenPatch.FiledPatch.forExchange.get(AbstractDungeon.gridSelectScreen)) {
                AbstractDungeon.gridSelectScreen.forUpgrade = false;
            }
        }
    }
}
