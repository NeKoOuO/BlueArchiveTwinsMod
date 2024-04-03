package baModDeveloper.patch;

import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar;
import javassist.CtBehavior;

public class BATwinsExtCardColorTabNamePatch {
    @SpirePatch(optional = true, cls = "basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix$Render", method = "Insert")
    public static class TabBarNameFix {
        private static final UIStrings uistrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("tabBarName"));

        @SpireInsertPatch(locator = TabNameLocator.class, localvars = {"tabName", "color"})
        public static void InsertPatch(ColorTabBar _instance, SpriteBatch sb, float y, ColorTabBar.CurrentTab curTab, @ByRef String[] tabName, Color color) {
            if (tabName[0].equals(uistrings.TEXT[0])) {
                tabName[0] = uistrings.TEXT[1];
            } else if (tabName[0].equals(uistrings.TEXT[2])) {
                tabName[0] = uistrings.TEXT[3];
            }
        }
    }

    private static class TabNameLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered");
            return LineFinder.findInOrder(ctBehavior, methodCallMatcher);

        }
    }
}
