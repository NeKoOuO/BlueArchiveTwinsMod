package baModDeveloper.patch;

import baModDeveloper.power.BATwinsMaidFormPower;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsGainBlockActionPatch {
    @SpirePatch(clz = GainBlockAction.class, method = "update")
    public static class updatePatch {
        @SpireInsertPatch(rloc = 2)
        public static void updatePatch(GainBlockAction _instance, @ByRef int[] ___amount, AbstractCreature ___target) {
            if (AbstractDungeon.player.hasPower(BATwinsMaidFormPower.POWER_ID)) {
                BATwinsMaidFormPower power = (BATwinsMaidFormPower) AbstractDungeon.player.getPower(BATwinsMaidFormPower.POWER_ID);
                ___amount[0] = power.doubleBlock(___target, ___amount[0]);
            }
        }
    }
}
