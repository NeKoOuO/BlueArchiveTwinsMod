package baModDeveloper.patch;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.relic.BATwinsBirthdayCake;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SuppressWarnings("unused")
public class BATwinsNeowRewardPatch {
    @SpirePatch(clz = NeowReward.class, method = "activate")
    public static class activatePatch {
        @SpireInstrumentPatch
        public static ExprEditor instrumentPatch() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(AbstractDungeon.class.getName()) && m.getMethodName().equals("returnRandomRelic")) {
                        m.replace(String.format("if(%s.replaceCondition($1)){" +
                                "$_=new %s();" +
                                "}else{" +
                                "$_ = $proceed($$);" +
                                "}", BATwinsNeowRewardPatch.class.getName(), BATwinsBirthdayCake.class.getName()));
                    }
                }
            };
        }
    }

    public static boolean replaceCondition(AbstractRelic.RelicTier tier) {
        return ModHelper.BIRTH_DAY &&
                tier == AbstractRelic.RelicTier.BOSS &&
                AbstractDungeon.player.chosenClass == BATwinsCharacter.Enums.BATwins;
    }

    @SpirePatch(clz = NeowEvent.class, method = "talk")
    public static class talkPatch {
        private static final UIStrings neowBirthDay = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("NeowBirthDay"));

        @SpirePrefixPatch
        public static void prefixPatch(NeowEvent _instance, @ByRef String[] msg, String[] ___TEXT) {
            if (ModHelper.BIRTH_DAY && AbstractDungeon.player.chosenClass == BATwinsCharacter.Enums.BATwins) {
                if (___TEXT[8].equals(msg[0])) {
                    msg[0] = neowBirthDay.TEXT[0];
                } else if (___TEXT[9].equals(msg[0])) {
                    msg[0] = neowBirthDay.TEXT[1];
                }
            }
        }
    }

    @SpirePatch(clz = NeowEvent.class, method = SpirePatch.CONSTRUCTOR,paramtypez = {boolean.class})
    public static class constructorPatch {
        @SpirePostfixPatch
        public static void postfixPatch(NeowEvent _instance, boolean isDone, @ByRef AnimatedNpc[] ___npc) {
            if (ModHelper.BIRTH_DAY && AbstractDungeon.player.chosenClass == BATwinsCharacter.Enums.BATwins) {
                ___npc[0].dispose();
                ___npc[0] = new AnimatedNpc(1534.0F * Settings.xScale, AbstractDungeon.floorY - 60.0F * Settings.yScale,
                        "baModResources/img/UI/birthNeow/skeleton.atlas",
                        "baModResources/img/UI/birthNeow/skeleton.json",
                        "idle");
            }
        }
    }
}
