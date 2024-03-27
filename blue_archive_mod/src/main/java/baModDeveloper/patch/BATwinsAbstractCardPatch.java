package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.power.BATwinsFlatFallPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsAbstractCardPatch {
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class FieldPatch {
        public static SpireField<Boolean> blockTheOriginalEffect = new SpireField<>(() -> false);
        public static SpireField<Integer> numberOfConnections = new SpireField<>(() -> 0);
    }

    @SpirePatch(clz = AbstractCard.class, method = "hover")
    public static class hoverPatch {
        @SpireInsertPatch(loc = 3)
        public static void hoverPatch(AbstractCard __instance, boolean ___hovered) {
            if (__instance instanceof BATwinsModCustomCard && ___hovered && !((BATwinsModCustomCard) __instance).justHovered) {
                if (AbstractDungeon.player != null) {
                    ((BATwinsModCustomCard) __instance).triggerOnHovered();
                }
                ((BATwinsModCustomCard) __instance).justHovered = true;
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "unhover")
    public static class unhoverPatch {
        @SpireInsertPatch(loc = 3)
        public static void unhoverPatch(AbstractCard __instance) {
            if (__instance instanceof BATwinsModCustomCard) {
                ((BATwinsModCustomCard) __instance).justHovered = false;
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "hasEnoughEnergy")
    public static class hasEnoughEnergyPatch {
        @SpireInsertPatch(rloc = 16)
        public static SpireReturn<Boolean> hasEnoughEnergyPatch(AbstractCard _instance) {
            if (AbstractDungeon.player.hasPower(BATwinsFlatFallPower.POWER_ID) && _instance.type == AbstractCard.CardType.ATTACK) {
                if (AbstractDungeon.player.getPower(BATwinsFlatFallPower.POWER_ID).amount == 0) {
                    _instance.cantUseMessage = BATwinsModCustomCard.flatFallMsg.TEXT[0];
                    BATwinsAbstractCardPatch.FieldPatch.blockTheOriginalEffect.set(_instance, false);
                    return SpireReturn.Return(false);
                }
            }
            return SpireReturn.Continue();
        }

        @SpireInsertPatch(rloc = 13)
        public static void hasEnoughEnergyPatch2(AbstractCard _instance) {
            BATwinsAbstractCardPatch.FieldPatch.blockTheOriginalEffect.set(_instance, false);
        }
    }

//    @SpirePatch(clz = AbstractCard.class,method = "use")
//    public static class usePatch{
//        @SpirePostfixPatch
//        public static void usePatch(AbstractCard _instance){
//            FieldPatch.numberOfConnections.set(_instance,0);
//        }
//    }
}
