//package baModDeveloper.patch;
//
//import com.evacipated.cardcrawl.modthespire.lib.SpireField;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//
//public class BATwinsAbstractPlayerPatch {
//    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
//    public static class FieldPatch {
//        public static SpireField<Boolean> blockTheOriginalEffect = new SpireField<>(() -> false);
//    }
//}
