//package baModDeveloper.patch;
//
//import baModDeveloper.character.BATwinsCharacter;
//import baModDeveloper.helpers.ModHelper;
//import com.evacipated.cardcrawl.modthespire.lib.ByRef;
//import com.evacipated.cardcrawl.modthespire.lib.SpireField;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.cutscenes.NeowNarrationScreen;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.localization.CharacterStrings;
//import com.sun.org.apache.xpath.internal.operations.Bool;
//
//public class BATwinsNeowNarrationScreenPatch {
//    private static CharacterStrings characterStrings= CardCrawlGame.languagePack.getCharacterString(ModHelper.makePath("PostCreditsNeow"));
//    private static CharacterStrings originNeowString=CardCrawlGame.languagePack.getCharacterString("PostCreditsNeow");
//    @SpirePatch(clz = NeowNarrationScreen.class,method = "open")
//    public static class openPatch{
//        public static boolean isBATwins=false;
//        @SpirePrefixPatch
//        public static void openPatch(NeowNarrationScreen _instance, @ByRef CharacterStrings[] ___charStrings){
//            if(isBATwins){
//                ___charStrings[0]=characterStrings;
//                isBATwins=false;
//            }else{
//                ___charStrings[0]=originNeowString;
//            }
//        }
//    }
//}
