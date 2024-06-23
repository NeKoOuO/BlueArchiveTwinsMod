package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.power.BATwinsBorrowMePower;
import baModDeveloper.power.BATwinsFlatFallPower;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
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
        public static SpireField<Boolean> dontFadeOutInLimbo = new SpireField<>(() -> false);
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
        @SpireInsertPatch(rloc = 34)
        public static SpireReturn<Boolean> hasEnoughEnergyPatch(AbstractCard _instance) {
            if(AbstractDungeon.overlayMenu.energyPanel instanceof BATwinsEnergyPanel){
                if(_instance.freeToPlay()||_instance.isInAutoplay){
                    SpireReturn.Return(true);
                }
                boolean hasEnoughEnergy=true;
                if(_instance instanceof BATwinsModCustomCard){
                    if(((BATwinsModCustomCard) _instance).modifyEnergyType== BATwinsEnergyPanel.EnergyType.SHARE||AbstractDungeon.player.hasPower(BATwinsBorrowMePower.POWER_ID)){
                        if (BATwinsEnergyPanel.MomoiCount + BATwinsEnergyPanel.MidoriCount >= _instance.costForTurn) {
                            _instance.cantUseMessage = AbstractCard.TEXT[11];
                            return SpireReturn.Return(true);
                        }
                    }else if(((BATwinsModCustomCard) _instance).modifyEnergyType== BATwinsEnergyPanel.EnergyType.MOMOI){
                        if (BATwinsEnergyPanel.MomoiCount + BATwinsEnergyPanel.MidoriCount / 2 >= _instance.costForTurn) {
                            _instance.cantUseMessage = AbstractCard.TEXT[11];
                            return SpireReturn.Return(true);
                        }
                    }else if(((BATwinsModCustomCard) _instance).modifyEnergyType== BATwinsEnergyPanel.EnergyType.MIDORI){
                        if (BATwinsEnergyPanel.MidoriCount + BATwinsEnergyPanel.MomoiCount / 2 >= _instance.costForTurn) {
                            _instance.cantUseMessage = AbstractCard.TEXT[11];
                            return SpireReturn.Return(true);
                        }
                    }
                }else{
                    if (BATwinsEnergyPanel.MomoiCount + BATwinsEnergyPanel.MidoriCount >= _instance.costForTurn) {
                        _instance.cantUseMessage = AbstractCard.TEXT[11];
                        return SpireReturn.Return(true);
                    }
                }

                _instance.cantUseMessage=AbstractCard.TEXT[11];
                return SpireReturn.Return(false);
            }
            return SpireReturn.Continue();
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
