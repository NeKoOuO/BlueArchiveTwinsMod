package baModDeveloper.patch;

import baModDeveloper.BATwinsMod;
import baModDeveloper.character.BATwinsCharacter;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

public class BATwinsAbstractDungeonPatch {
    @SpirePatch(clz = AbstractDungeon.class, method = "nextRoomTransition", paramtypez = SaveFile.class)
    public static class nextRoomTransitionPatch {
        @SpireInsertPatch(rloc = 164)
        public static void nextRoomTransitionPatch(AbstractDungeon _instance, SaveFile saveFile) {
            if (AbstractDungeon.player instanceof BATwinsCharacter) {
                ((BATwinsCharacter) AbstractDungeon.player).onEnterRoom();
            }
        }

        @SpirePrefixPatch
        public static void prefixPatch(AbstractDungeon _instance, SaveFile saveFile, @ByRef MapRoomNode[] ___nextRoom){
            if(BATwinsMod.saveHelper.values.challengeCoupons){
//                BATwinsMod.saveHelper.values.challengeCoupons=false;
//                BATwinsMod.saveHelper.values.ChallengeCouponsActivated=true;
                if(BATwinsMod.saveHelper.values.challengeCouponsFloor!=-1&&AbstractDungeon.floorNum!=BATwinsMod.saveHelper.values.challengeCouponsFloor){
                    BATwinsMod.saveHelper.values.challengeCoupons=false;
                    return;
                }
                if(!(___nextRoom[0].room instanceof MonsterRoomBoss)){
                    ___nextRoom[0].room=new MonsterRoomElite();
                    BATwinsMod.saveHelper.values.challengeCouponsFloor=AbstractDungeon.floorNum;
                }
            }
        }
    }
}
