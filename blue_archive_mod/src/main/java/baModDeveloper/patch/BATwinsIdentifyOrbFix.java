//package baModDeveloper.patch;
//
//import baModDeveloper.character.BATwinsCharacter;
//import baModDeveloper.helpers.ImageHelper;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.evacipated.cardcrawl.modthespire.lib.ByRef;
//import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.helpers.FontHelper;
//
//@SpirePatch(clz = FontHelper.class,method = "renderSmartText",paramtypez = {SpriteBatch.class,BitmapFont.class,String.class,float.class,float.class,float.class,float.class,Color.class})
//public class BATwinsIdentifyOrbFix {
//    @SpireInsertPatch(rloc = 26,localvars = {"word"})
//    public static void IdentifyOrbFix(SpriteBatch sb, BitmapFont font, String msg, float x, float y, float lineWidth, float lineSpacing, Color baseColor,  @ByRef TextureAtlas.AtlasRegion[] ___orb,String word){
//        if(word.equals("[TE]")){
//            ___orb[0]= ImageHelper.MOMOISMALLORB;
//        } else if (word.equals("[LE]")) {
//            ___orb[0]=ImageHelper.MIDORISMALLORB;
//        }
//    }
//}
