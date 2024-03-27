package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsEasterEgg;
import baModDeveloper.cards.BATwinsModCustomCard;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

public class BATwinsCardCrawlGamePatch {
    @SpirePatch(clz = CardCrawlGame.class, method = "loadPlayerSave")
    public static class loadPlayerSavePatch {
        @SpireInsertPatch(rloc = 68, localvars = {"p"})
        public static void loadPlayerSavePatch(CardCrawlGame _instance, AbstractPlayer ___p) {
            ArrayList<CardSave> cards = CardCrawlGame.saveFile.cards;
            for (int i = 0; i < cards.size(); i++) {
                if (BATwinsCardSavePatch.FiledPatch.isExchange.get(cards.get(i)) != null && BATwinsCardSavePatch.FiledPatch.isExchange.get(cards.get(i))) {
                    if (___p.masterDeck.group.get(i) instanceof BATwinsModCustomCard) {
                        ((BATwinsModCustomCard) ___p.masterDeck.group.get(i)).conversionColor(false);
                    }
                }
                if (cards.get(i).id.equals(BATwinsEasterEgg.ID)) {
                    BATwinsEasterEgg c = new BATwinsEasterEgg();
                    if (cards.get(i).upgrades > 0) {
                        c.upgrade();
                    }
                    ___p.masterDeck.group.set(i, new BATwinsEasterEgg());
                }
            }

//            if(___p instanceof BATwinsCharacter){
//                if(BATwinsCardSavePatch.FiledPatch.isExchange.get(___s)!=null&&BATwinsCardSavePatch.FiledPatch.isExchange.get(___s)){
//                    if(___p.masterDeck.getTopCard() instanceof BATwinsModCustomCard){
//                        ((BATwinsModCustomCard) ___p.masterDeck.getTopCard()).conversionColor(false);
//                    }
//                }
//            }
        }
    }

    @SpirePatch(clz = CardCrawlGame.class, method = "create")
    public static class createPatch {
        public static OrthographicCamera camera;

        @SpirePostfixPatch
        public static void createPatch(CardCrawlGame _instance, OrthographicCamera ___camera) {
            if (___camera != null) {
                camera = ___camera;
            }
        }
    }
}
