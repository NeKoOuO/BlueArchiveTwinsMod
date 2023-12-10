package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsModCustomCard;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class BATwinsAbstractCardPatch{
    @SpirePatch(clz = AbstractCard.class,method = SpirePatch.CLASS)
    public static class FieldPatch {
        public static SpireField<Boolean> blockTheOriginalEffect=new SpireField<>(()->false);
    }
    @SpirePatch(clz = AbstractCard.class,method = "hover")
    public static class hoverPatch{
        @SpireInsertPatch(loc = 3)
        public static void hoverPatch(AbstractCard __instance,boolean ___hovered){
            if(__instance instanceof BATwinsModCustomCard&&___hovered&&!((BATwinsModCustomCard) __instance).justHovered){
                ((BATwinsModCustomCard) __instance).triggerOnHovered();
                ((BATwinsModCustomCard) __instance).justHovered= true;
            }
        }
    }
    @SpirePatch(clz = AbstractCard.class,method = "unhover")
    public static class unhoverPatch{
        @SpireInsertPatch(loc = 3)
        public static void unhoverPatch(AbstractCard __instance){
            if(__instance instanceof BATwinsModCustomCard){
                ((BATwinsModCustomCard) __instance).justHovered=false;
            }
        }
    }
}
