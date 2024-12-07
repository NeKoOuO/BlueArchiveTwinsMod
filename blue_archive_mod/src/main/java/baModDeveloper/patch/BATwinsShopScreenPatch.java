package baModDeveloper.patch;

import baModDeveloper.helpers.ModHelper;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.ui.DialogWord;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SpeechTextEffect;
import javassist.CtBehavior;

import java.util.ArrayList;

public class BATwinsShopScreenPatch {
    @SpirePatch(clz = ShopScreen.class, method = SpirePatch.CLASS)
    public static class filedPatch {
        public static SpireField<Boolean> GIVEN_GIFT = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = ShopScreen.class, method = "init")
    public static class initPatch {
        @SpirePostfixPatch
        public static void postfixPatch(ShopScreen _instance, ArrayList<AbstractCard> coloredCards, ArrayList<AbstractCard> colorlessCards) {
            filedPatch.GIVEN_GIFT.set(_instance, false);
        }
    }

    @SpirePatch(clz = ShopScreen.class, method = "purchaseCard")
    public static class purchaseCardPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void insertPatch(ShopScreen _instance, AbstractCard hoveredCard, @ByRef SpeechTextEffect[] ___dialogTextEffect) {
            if (!filedPatch.GIVEN_GIFT.get(_instance)) {
                AbstractDungeon.topLevelEffectsQueue.add(new HappyBirthDaySpeedEffect(_instance, hoveredCard, ___dialogTextEffect));
            }

        }
    }

    private static class Locator extends SpireInsertLocator {

        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ShopScreen.class, "getCantBuyMsg");
            return LineFinder.findAllInOrder(ctBehavior, matcher);
        }
    }

    public static class HappyBirthDaySpeedEffect extends AbstractGameEffect {
        ShopScreen _instance;
        AbstractCard hoveredCard;
        SpeechTextEffect[] ___dialogTextEffect;

        public HappyBirthDaySpeedEffect(ShopScreen _instance, AbstractCard hoveredCard, SpeechTextEffect[] ___dialogTextEffect) {
            this._instance = _instance;
            this.hoveredCard = hoveredCard;
            this.___dialogTextEffect = ___dialogTextEffect;
        }

        @Override
        public void update() {
            SpeechTextEffect effect = ReflectionHacks.getPrivate(_instance, SpeechTextEffect.class, "dialogTextEffect");
            if (effect == null || effect.isDone) {
                UIStrings shopBirthDay = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("ShopBirthDay"));
                _instance.createSpeech(shopBirthDay.TEXT[0]);
//                            AbstractDungeon.topLevelEffectsQueue.add(new SpeechTextEffect(Settings.WIDTH/2.0F,Settings.HEIGHT-380.0F*Settings.scale,
//                                    4.0F,shopBirthDay.TEXT[0], DialogWord.AppearEffect.BUMP_IN));
                if (hoveredCard != null) {
                    hoveredCard.price = AbstractDungeon.player.gold;
                }
                ___dialogTextEffect[0] = new SpeechTextEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT - 380.0F * Settings.scale,
                        4.0F, shopBirthDay.TEXT[0], DialogWord.AppearEffect.BUMP_IN);
                this.isDone = true;
                filedPatch.GIVEN_GIFT.set(_instance, true);
            }
        }

        @Override
        public void render(SpriteBatch spriteBatch) {

        }

        @Override
        public void dispose() {

        }
    }
}
