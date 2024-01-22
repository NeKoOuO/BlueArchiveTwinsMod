package baModDeveloper.patch;

import baModDeveloper.power.BATwinsMaidFormPower;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsDamageActionPatch {
    @SpirePatch(clz = DamageAction.class,method = "update")
    public static class updatePatch{
        @SpireInsertPatch(rloc = 29)
        public static void updatePatch(DamageAction _instance, @ByRef DamageInfo[] ___info){
            if(AbstractDungeon.player.hasPower(BATwinsMaidFormPower.POWER_ID)){
                BATwinsMaidFormPower power= (BATwinsMaidFormPower) AbstractDungeon.player.getPower(BATwinsMaidFormPower.POWER_ID);
                power.douleDamage(___info[0]);
            }
        }
    }
}
