package baModDeveloper.patch;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.Character3DHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.SmokeBomb;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BATwinsSmokeBombPatch {
    @SpirePatch(clz = SmokeBomb.class,method = "use")
    public static class usePatch{
        @SpirePostfixPatch
        public static void postFixPatch(SmokeBomb __instance, AbstractCreature target){
            if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT&&AbstractDungeon.player instanceof BATwinsCharacter) {
                ((BATwinsCharacter) AbstractDungeon.player).setMomoiAnimation(Character3DHelper.MomoiActionList.ESCAPE);
                ((BATwinsCharacter) AbstractDungeon.player).setMidoriAnimation(Character3DHelper.MidoriActionList.ESCAPE);
            }
        }
    }
}
