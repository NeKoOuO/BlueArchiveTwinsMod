package baModDeveloper.patch;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.core.BATwinsEnergyManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;

@SpirePatch(clz = CardCrawlGame.class, method = "loadPlayerSave")
public class BATwinsLoadLayerSaveFix {

    @SpirePostfixPatch
    public static void LoadLayerSaveFix(CardCrawlGame _instance, AbstractPlayer p) {
        if (p instanceof BATwinsCharacter) {
            p.energy = new BATwinsEnergyManager(CardCrawlGame.saveFile.red + CardCrawlGame.saveFile.green + CardCrawlGame.saveFile.blue);
        }
    }

}
