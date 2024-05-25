package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsModCustomCard;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsUseCardActionPatch {
    @SpirePatch(clz = UseCardAction.class, method = "update")
    public static class updatePatch {
        @SpireInsertPatch(rloc = 67)
        public static void insertPatch(UseCardAction _instance, AbstractCard ___targetCard) {
            if (AbstractDungeon.player.limbo.contains(___targetCard)) {
                AbstractDungeon.player.limbo.removeCard(___targetCard);
            }
        }

//        private static class UnlimboLocator extends SpireInsertLocator{
//
//            @Override
//            public int[] Locate(CtBehavior ctBehavior) throws Exception {
//                Matcher.FieldAccessMatcher fieldAccessMatcher=new Matcher.FieldAccessMatcher(AbstractCard.class,"targetCard");
//                return LineFinder.findInOrder(ctBehavior,fieldAccessMatcher);
//            }
//        }
    }
}
