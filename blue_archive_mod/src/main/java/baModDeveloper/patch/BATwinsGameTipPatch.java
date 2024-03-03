package baModDeveloper.patch;

import baModDeveloper.helpers.ModHelper;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameTips;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Collections;

public class BATwinsGameTipPatch {
    private static final UIStrings tips = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("Tips"));

    @SpirePatch(clz = GameTips.class, method = "initialize")
    public static class initializePatch {
        @SpirePostfixPatch
        public static void initializePatch(GameTips _instance, @ByRef ArrayList<String>[] ___tips) {
//            if(AbstractDungeon.player instanceof BATwinsCharacter){
            for (String s : tips.TEXT) {
                ___tips[0].add(s);
            }
            Collections.shuffle(___tips[0]);
//            }

        }
    }
}
