package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsColorEgg;
import baModDeveloper.character.BATwinsCharacter;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import org.lwjgl.Sys;

public class BATwinsCharacterOptionPatch {
    @SpirePatch(clz = CharacterOption.class,method = "updateHitbox")
    public static class updateHitboxPatch{
        private static int PressCount=0;
        public static boolean FindColorEgg=false;
        @SpireInsertPatch(rloc = 35)
        public static void updateHitboxPatch(CharacterOption _instance){
            if(_instance.c instanceof BATwinsCharacter){
                PressCount++;
                if(PressCount>9){
                    FindColorEgg=true;
                }
            }else{
                PressCount=0;
                FindColorEgg=false;
            }
            System.out.println("pressCount:"+PressCount);
        }
    }
}
