package baModDeveloper.patch;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.ui.panels.BATwinsCharacterSelectScreen;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

public class BATwinsCharacterSelectScreenPatch {

    public static boolean isBATwinsSelected() {
        return CardCrawlGame.chosenCharacter == BATwinsCharacter.Enums.BATwins && (Boolean) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected");
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class updatePatch {
        @SpirePostfixPatch
        public static void updatePatch(CharacterSelectScreen _instance) {
            if (isBATwinsSelected()) {
                BATwinsCharacterSelectScreen.getInstance().update();
            }
        }


    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class renderPatch {
        @SpirePostfixPatch
        public static void renderPatch(CharacterSelectScreen _instance, SpriteBatch sb) {
            if (isBATwinsSelected()) {
                BATwinsCharacterSelectScreen.getInstance().render(sb);
            }
        }
    }
}