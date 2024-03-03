package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

public class BATwinsSingleCardViewPopupPatch {
    @SpirePatch(clz = SingleCardViewPopup.class, method = SpirePatch.CLASS)
    public static class FieldPatch {
        public static String exchangeBtn = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("CardViewBtn")).TEXT[0];
        public static SpireField<Boolean> isViewingExchange = new SpireField<>(() -> false);
        public static SpireField<Hitbox> exchangeHb = new SpireField<>(() -> new Hitbox(200.0F * Settings.scale, 80.0F * Settings.scale));
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = SpirePatch.CONSTRUCTOR)
    public static class constructorPatch {
        @SpirePostfixPatch
        public static void constructorPatch(SingleCardViewPopup _instance) {
            FieldPatch.exchangeHb.set(_instance, new Hitbox(200.0F * Settings.scale, 80.0F * Settings.scale));
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "open", paramtypez = {AbstractCard.class, CardGroup.class})
    public static class openPatch {
        @SpirePostfixPatch
        public static void openPatch(SingleCardViewPopup _instance, AbstractCard card, CardGroup group) {
            if (card instanceof BATwinsModCustomCard) {
                FieldPatch.exchangeHb.get(_instance).move(Settings.WIDTH / 2.0F - 400.0F * Settings.scale, 150.0F * Settings.scale);
            } else {
                FieldPatch.exchangeHb.get(_instance).move(-1000.0F * Settings.scale, -1000.0F * Settings.scale);
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "open", paramtypez = {AbstractCard.class})
    public static class openPatch2 {
        @SpirePostfixPatch
        public static void openPatch(SingleCardViewPopup _instance, AbstractCard card) {
            if (card instanceof BATwinsModCustomCard) {
                FieldPatch.exchangeHb.get(_instance).move(Settings.WIDTH / 2.0F - 400.0F * Settings.scale, 150.0F * Settings.scale);
            } else {
                FieldPatch.exchangeHb.get(_instance).move(-1000.0F * Settings.scale, -1000.0F * Settings.scale);
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "update")
    public static class updatePatch {
        @SpirePostfixPatch
        public static void updatePatch(SingleCardViewPopup _instance) {
            updateExchangePreview(_instance);
        }

        private static void updateExchangePreview(SingleCardViewPopup _instance) {
            FieldPatch.exchangeHb.get(_instance).update();
            if (FieldPatch.exchangeHb.get(_instance).hovered && InputHelper.justClickedLeft) {
                FieldPatch.exchangeHb.get(_instance).clickStarted = true;
            }
            if (FieldPatch.exchangeHb.get(_instance).clicked || CInputActionSet.proceed.isJustPressed()) {
                CInputActionSet.proceed.unpress();
                FieldPatch.exchangeHb.get(_instance).clicked = false;
                FieldPatch.isViewingExchange.set(_instance, !FieldPatch.isViewingExchange.get(_instance));
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "updateInput")
    public static class updateInputPatch {
        private static boolean hovered = false;

        @SpireInsertPatch(rloc = 12)
        public static void updateInputPatch1(SingleCardViewPopup _instance, Hitbox ___cardHb) {
            hovered = FieldPatch.exchangeHb.get(_instance).hovered;
            ___cardHb.hovered = hovered;
        }

        @SpireInsertPatch(rloc = 24)
        public static void updateInputPatch2(SingleCardViewPopup _instance, Hitbox ___cardHb) {
            if (hovered) {
                ___cardHb.hovered = false;
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "render")
    public static class renderPatch {
        @SpireInsertPatch(rloc = 4, localvars = {"copy"})
        public static void renderPatch2(SingleCardViewPopup _instance, SpriteBatch sb, boolean ___isViewingUpgrade, AbstractCard ___card, @ByRef AbstractCard[] copy) {
            if (FieldPatch.isViewingExchange.get(_instance) && ___card instanceof BATwinsModCustomCard) {
                ((BATwinsModCustomCard) ___card).conversionColor(false);
            }
        }

        @SpireInsertPatch(rloc = 5, localvars = {"copy"})
        public static void renderPatch3(SingleCardViewPopup _instance, SpriteBatch sb, boolean ___isViewingUpgrade, AbstractCard ___card, @ByRef AbstractCard[] copy) {
            if (!___isViewingUpgrade && FieldPatch.isViewingExchange.get(_instance) && ___card instanceof BATwinsModCustomCard) {
                copy[0] = ___card.makeStatEquivalentCopy();
                ((BATwinsModCustomCard) ___card).conversionColor(false);
            }
        }

        @SpirePostfixPatch
        public static void renderPatch(SingleCardViewPopup _instance, SpriteBatch sb) {
            renderExchangeViewToggle(_instance, sb);
        }

        private static void renderExchangeViewToggle(SingleCardViewPopup _instance, SpriteBatch sb) {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.CHECKBOX, FieldPatch.exchangeHb.get(_instance).cX - 80.0F * Settings.scale - 32.0F, FieldPatch.exchangeHb.get(_instance).cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);

            if (FieldPatch.exchangeHb.get(_instance).hovered) {
                FontHelper.renderFont(sb, FontHelper.cardTitleFont, FieldPatch.exchangeBtn, FieldPatch.exchangeHb.get(_instance).cX - 45.0F * Settings.scale, FieldPatch.exchangeHb.get(_instance).cY + 10.0F * Settings.scale, Settings.BLUE_TEXT_COLOR);
            } else {
                FontHelper.renderFont(sb, FontHelper.cardTitleFont, FieldPatch.exchangeBtn, FieldPatch.exchangeHb.get(_instance).cX - 45.0F * Settings.scale, FieldPatch.exchangeHb.get(_instance).cY + 10.0F * Settings.scale, Settings.GOLD_COLOR);
            }
            if (FieldPatch.isViewingExchange.get(_instance)) {
                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.TICK, FieldPatch.exchangeHb.get(_instance).cX - 80.0F * Settings.scale - 32.0F, FieldPatch.exchangeHb.get(_instance).cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            }
            FieldPatch.exchangeHb.get(_instance).render(sb);
        }
    }
}
