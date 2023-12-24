package baModDeveloper.patch;

import baModDeveloper.character.BATwinsCharacter;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

public class BATwinsAbstractDungeonPatch {
    @SpirePatch(clz = AbstractDungeon.class,method = "nextRoomTransition",paramtypez = SaveFile.class)
    public static class nextRoomTransitionPatch{
        @SpireInsertPatch(rloc = 164)
        public static void nextRoomTransitionPatch(AbstractDungeon _instance, SaveFile saveFile){
            if(AbstractDungeon.player instanceof BATwinsCharacter){
                ((BATwinsCharacter) AbstractDungeon.player).onEnterRoom();
            }
        }
    }
}
