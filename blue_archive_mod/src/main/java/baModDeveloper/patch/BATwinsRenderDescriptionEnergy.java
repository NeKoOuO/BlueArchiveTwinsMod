package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsMidoriStrick;
import baModDeveloper.cards.BATwinsMomoiStrick;
import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.RenderDescriptionEnergy;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.ShrinkLongDescription;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@SpirePatch(
        clz=AbstractCard.class,
        method="renderDescription"
)
@SpirePatch(
        clz=AbstractCard.class,
        method="renderDescriptionCN"
)
public class BATwinsRenderDescriptionEnergy {
    private static final BATwinsMomoiStrick MOMOICARD=new BATwinsMomoiStrick();
    private static final BATwinsMidoriStrick MIDORICARD=new BATwinsMidoriStrick();
    private static final Pattern r = Pattern.compile("\\[(TE|LE)]");
    private static final float CARD_ENERGY_IMG_WIDTH = 24.0f * Settings.scale;

    @SpireInsertPatch(
            locator= Locator.class,
            localvars={"spacing", "i", "start_x", "draw_y", "font", "textColor", "tmp", "gl"}
    )
    public static void Insert(AbstractCard __instance, SpriteBatch sb, float spacing, int i, @ByRef float[] start_x, float draw_y,
                              BitmapFont font, Color textColor, @ByRef String[] tmp, GlyphLayout gl)
    {
        Matcher m = r.matcher(tmp[0]);
        if (tmp[0].equals("[TE]")) {
            __instance.renderSmallEnergy(sb, BaseMod.getCardSmallEnergy(MOMOICARD),
                    (start_x[0] - __instance.current_x) / Settings.scale / __instance.drawScale,
                    (draw_y + i * 1.45f * -font.getCapHeight() - 6f - __instance.current_y + font.getCapHeight()) / Settings.scale / __instance.drawScale);

//            if (!tmp[0].equals("[E]") && m.group(2).equals(".")) {
//                FontHelper.renderRotatedText(sb, font, LocalizedStrings.PERIOD,
//                        __instance.current_x, __instance.current_y,
//                        start_x[0] - __instance.current_x + CARD_ENERGY_IMG_WIDTH * __instance.drawScale * ShrinkLongDescription.Scale.descriptionScale.get(__instance),
//                        i * 1.45f * -font.getCapHeight() + draw_y - __instance.current_y - 6.0f,
//                        __instance.angle, true, textColor);
//                gl.setText(font, RenderDescriptionEnergy.AdjustEnergyWidth.PERIOD_SPACE);
//                gl.width += CARD_ENERGY_IMG_WIDTH * __instance.drawScale * ShrinkLongDescription.Scale.descriptionScale.get(__instance);
//            }
//            else {
//                gl.width = CARD_ENERGY_IMG_WIDTH * __instance.drawScale * ShrinkLongDescription.Scale.descriptionScale.get(__instance);
//            }

            start_x[0] += gl.width;
            tmp[0] = "";
        } else if (tmp[0].equals("[LE]")) {
            __instance.renderSmallEnergy(sb, BaseMod.getCardSmallEnergy(MIDORICARD),
                    (start_x[0] - __instance.current_x) / Settings.scale / __instance.drawScale,
                    (draw_y + i * 1.45f * -font.getCapHeight() - 6f - __instance.current_y + font.getCapHeight()) / Settings.scale / __instance.drawScale);


            start_x[0] += gl.width;
            tmp[0] = "";
        }
    } private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            com.evacipated.cardcrawl.modthespire.lib.Matcher matcher = new com.evacipated.cardcrawl.modthespire.lib.Matcher.MethodCallMatcher(GlyphLayout.class, "setText");
            int[] lines = LineFinder.findAllInOrder(ctBehavior, new ArrayList<>(), matcher);
            return new int[]{lines[lines.length-1]}; // Only last occurrence
        }
    }
    @SpirePatch(
            clz=AbstractCard.class,
            method="initializeDescriptionCN"
    )
    public static class FixEForChinese
    {
        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"word", "currentWidth", "sbuilder", "numLines", "CARD_ENERGY_IMG_WIDTH", "CN_DESC_BOX_WIDTH"}
        )
        public static void Insert(AbstractCard __instance, @ByRef String[] word, @ByRef float[] currentWidth,
                                  @ByRef StringBuilder[] currentLine, @ByRef int[] numLines,
                                  float CARD_ENERGY_IMG_WIDTH, float CN_DESC_BOX_WIDTH)
        {
            if (word[0].equals("[TE]")||word[0].equals("[LE]")) {
                if (currentWidth[0] + CARD_ENERGY_IMG_WIDTH > CN_DESC_BOX_WIDTH) {
                    ++numLines[0];
                    __instance.description.add(new DescriptionLine(currentLine[0].toString(), currentWidth[0]));
                    currentLine[0] = new StringBuilder();
                    currentWidth[0] = CARD_ENERGY_IMG_WIDTH;
                    currentLine[0].append(" ").append(word[0]).append(" ");
                } else {
                    currentLine[0].append(" ").append(word[0]).append(" ");
                    currentWidth[0] += CARD_ENERGY_IMG_WIDTH;
                }
                word[0] = "";
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
            {
                com.evacipated.cardcrawl.modthespire.lib.Matcher finalMatcher = new com.evacipated.cardcrawl.modthespire.lib.Matcher.MethodCallMatcher(
                        String.class, "toCharArray");

                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }


    }
    @SpirePatch(
            clz=AbstractCard.class,
            method="initializeDescription"
    )
    @SpirePatch(
            clz=AbstractCard.class,
            method="initializeDescriptionCN"
    )
    public static class InitializeDescriptionPatches
    {
        //Adjust width calculation
//        @SpireInstrumentPatch
//        public static ExprEditor adjustParams()
//        {
//            return new ExprEditor() {
//                @Override
//                public void edit(FieldAccess f) throws CannotCompileException {
//                    if (f.isReader() && "CARD_ENERGY_IMG_WIDTH".equals(f.getFieldName()) && AbstractCard.class.getName().equals(f.getClassName())) {
//                        f.replace("$_ = $proceed()" +
//                                " * ((Float)" + ShrinkLongDescription.Scale.class.getName() + ".descriptionScale.get(this)).floatValue();");
//                    }
//                }
//            };
//        }

        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"word"}
        )
        public static void AlterEnergyKeyword(AbstractCard __instance, String word)
        {
            if ("[TE]".equals(word) && !__instance.keywords.contains("[TE]")) {
                __instance.keywords.add("[TE]");
            } else if ("[LE]".equals(word) && !__instance.keywords.contains("[LE]")) {
                __instance.keywords.add("[LE]");
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
            {
                com.evacipated.cardcrawl.modthespire.lib.Matcher finalMatcher = new com.evacipated.cardcrawl.modthespire.lib.Matcher.MethodCallMatcher(
                        String.class, "toLowerCase");

                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

}
