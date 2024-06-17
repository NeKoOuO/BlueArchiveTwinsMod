package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsEasterEgg;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.Iterator;

public class BATwinsGameActionManagerPatch {
    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class getNextActionPatch {
        @SpireInsertPatch(rloc = 158, localvars = {"e", "i"})
        public static void getNextActionPatch(GameActionManager __instance, @ByRef AbstractCard[] e, Iterator<AbstractCard> i) {
            if (BATwinsAbstractCardPatch.FieldPatch.dontFadeOutInLimbo.get(e[0])) {
                BATwinsAbstractCardPatch.FieldPatch.dontFadeOutInLimbo.set(e[0], false);
                if (i.hasNext()) {
                    e[0] = i.next();
                } else {
                    e[0] = new BATwinsEasterEgg();
                }
            }
        }
    }
}
