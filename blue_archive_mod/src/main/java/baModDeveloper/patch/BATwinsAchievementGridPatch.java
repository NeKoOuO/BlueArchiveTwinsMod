package baModDeveloper.patch;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.icons.BATwinsAchievementItem;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;

public class BATwinsAchievementGridPatch {
    @SpirePatch(clz = AchievementGrid.class,method = SpirePatch.CONSTRUCTOR)
    public static class constructorPatch{
        private static UIStrings uiStrings= CardCrawlGame.languagePack.getUIString(ModHelper.makePath("Achieve"));
        @SpirePostfixPatch
        public static void postfixPatch(AchievementGrid _instance){
            _instance.items.add(new BATwinsAchievementItem(uiStrings.TEXT[0],uiStrings.TEXT[1],
                    ModHelper.makeImgPath("UI","achieveGridLock"),ModHelper.makeImgPath("UI","achieveGridUnlock")));
        }
    }
}
