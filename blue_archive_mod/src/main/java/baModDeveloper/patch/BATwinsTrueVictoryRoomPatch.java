package baModDeveloper.patch;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.ui.victorycut.BATwinsCutScenes;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;

public class BATwinsTrueVictoryRoomPatch {
    @SpirePatch(clz = TrueVictoryRoom.class,method = SpirePatch.CONSTRUCTOR)
    public static class ConstructorPatch{
        @SpirePostfixPatch
        public static void postFix(TrueVictoryRoom __instance, @ByRef Cutscene[] ___cutscene){
            if(AbstractDungeon.player.chosenClass== BATwinsCharacter.Enums.BATwins){
                ___cutscene[0]=new BATwinsCutScenes();
            }
        }
    }
}
