package baModDeveloper.patch;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

import java.util.Random;

public class BATwinsCharacterOptionPatch {
    @SpirePatch(clz = CharacterOption.class,method = "updateHitbox")
    public static class updateHitboxPatch{
        public static int PressCount=0;
        public static boolean FindEasterEgg=false;
        public static Random random=new Random();
        @SpireInsertPatch(rloc = 35)
        public static void updateHitboxPatch(CharacterOption _instance){
            if(_instance.c instanceof BATwinsCharacter){
                PressCount++;
                if(PressCount>9&&!FindEasterEgg){
                    FindEasterEgg=true;
                    if(random.nextBoolean()){
                        CardCrawlGame.sound.playV(ModHelper.makePath("colorEgg1"),2.0F);
                    }else{
                        CardCrawlGame.sound.playV(ModHelper.makePath("colorEgg2"),2.0F);

                    }
                }
            }else{
                PressCount=0;
                FindEasterEgg=false;
            }
            System.out.println("pressCount:"+PressCount);
        }
    }
}
