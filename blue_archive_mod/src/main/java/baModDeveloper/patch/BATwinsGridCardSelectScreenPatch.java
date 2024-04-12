package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BATwinsGridCardSelectScreenPatch {
    @SpirePatch(clz = GridCardSelectScreen.class, method = SpirePatch.CLASS)
    public static class FiledPatch {
        public static SpireField<Boolean> forExchange = new SpireField<>(() -> false);
        public static UIStrings CANCELBUTTON = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("CancelButton"));
        public static UIStrings TIPMSG = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("TipMsg"));
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "update")
    public static class updatePatch {
        @SpireInsertPatch(rloc = 136)
        public static SpireReturn<Void> updatePatch(GridCardSelectScreen __instance, @ByRef AbstractCard[] ___hoveredCard, String ___lastTip, String ___tipMsg) {
            if (FiledPatch.forExchange.get(__instance)) {
                if (___hoveredCard[0] instanceof BATwinsModCustomCard) {
                    ___hoveredCard[0].untip();
                    __instance.confirmScreenUp = true;
                    __instance.upgradePreviewCard = ___hoveredCard[0].makeStatEquivalentCopy();
                    ((BATwinsModCustomCard) __instance.upgradePreviewCard).conversionColor(false);
                    __instance.upgradePreviewCard.displayUpgrades();
                    __instance.upgradePreviewCard.drawScale = 0.875F;
                    ___hoveredCard[0].stopGlowing();
                    __instance.selectedCards.clear();
                    AbstractDungeon.overlayMenu.cancelButton.show(FiledPatch.CANCELBUTTON.TEXT[0]);
                    __instance.confirmButton.show();
                    __instance.confirmButton.isDisabled = false;
                    ___lastTip = ___tipMsg;
                    ___tipMsg = FiledPatch.TIPMSG.TEXT[0];
                    return SpireReturn.Return();
                }


            } else {
                return SpireReturn.Continue();
            }
            return SpireReturn.Continue();
        }

        @SpireInsertPatch(rloc = 202)
        public static void updatePatch2(GridCardSelectScreen __instance) {
            if (FiledPatch.forExchange.get(__instance)) {
                __instance.upgradePreviewCard.update();
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "cancelUpgrade")
    public static class cancelUpgradePatch {
        @SpireInsertPatch(rloc = 18)
        public static void cancelUpgradePatch(GridCardSelectScreen __instance) {
            if (FiledPatch.forExchange.get(__instance)) {
                AbstractDungeon.overlayMenu.cancelButton.show(FiledPatch.CANCELBUTTON.TEXT[0]);
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "render")
    public static class renderPatch {
        @SpireInsertPatch(rloc = 89)
        public static void renderPatch(GridCardSelectScreen __instance, SpriteBatch sb, AbstractCard ___hoveredCard) {
            if (FiledPatch.forExchange.get(__instance)) {
                try {
                    Method privateMethod = __instance.getClass().getDeclaredMethod("renderArrows", SpriteBatch.class);
                    privateMethod.setAccessible(true);
                    privateMethod.invoke(__instance, sb);

                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                ___hoveredCard.current_x = Settings.WIDTH * 0.36F;
                ___hoveredCard.current_y = Settings.HEIGHT / 2.0F;
                ___hoveredCard.target_x = Settings.WIDTH * 0.36F;
                ___hoveredCard.target_y = Settings.HEIGHT / 2.0F;
                ___hoveredCard.render(sb);
                ___hoveredCard.updateHoverLogic();
                ___hoveredCard.renderCardTip(sb);


                __instance.upgradePreviewCard.current_x = Settings.WIDTH * 0.63F;
                __instance.upgradePreviewCard.current_y = Settings.HEIGHT / 2.0F;
                __instance.upgradePreviewCard.target_x = Settings.WIDTH * 0.63F;
                __instance.upgradePreviewCard.target_y = Settings.HEIGHT / 2.0F;
                __instance.upgradePreviewCard.render(sb);
                __instance.upgradePreviewCard.updateHoverLogic();
                __instance.upgradePreviewCard.renderCardTip(sb);
            }
        }

        @SpireInsertPatch(rloc = 133)
        public static void renderPatch2(GridCardSelectScreen __instance, SpriteBatch sb) {
            if (!PeekButton.isPeeking && FiledPatch.forExchange.get(__instance)) {
                __instance.confirmButton.render(sb);
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "open", paramtypez = {CardGroup.class, int.class, String.class, boolean.class, boolean.class, boolean.class, boolean.class})
    public static class openPatch {
        @SpireInsertPatch(rloc = 16)
        public static void openPatch(GridCardSelectScreen __instance, CardGroup group, int numCards, String tipMsg, boolean forUpgrade, boolean forTransform, boolean canCancel, boolean forPurge) {
            if (FiledPatch.forExchange.get(__instance)) {
                AbstractDungeon.overlayMenu.cancelButton.show(FiledPatch.CANCELBUTTON.TEXT[0]);
            }
        }
    }
}
