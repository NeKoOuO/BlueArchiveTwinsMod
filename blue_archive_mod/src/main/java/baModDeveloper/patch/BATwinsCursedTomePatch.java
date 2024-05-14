package baModDeveloper.patch;

import baModDeveloper.relic.BATwinsBookOfProhibitions;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.CursedTome;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class BATwinsCursedTomePatch {
    @SpirePatch(clz = CursedTome.class, method = "randomBook")
    public static class randomBookPatch {
        @SpireInsertPatch(rloc = 2, localvars = {"possibleBooks"})
        public static void insertPatch(CursedTome _instance, @ByRef ArrayList<AbstractRelic>[] ___possibleBooks) {
            if (!AbstractDungeon.player.hasRelic(BATwinsBookOfProhibitions.ID)) {
                ___possibleBooks[0].add(RelicLibrary.getRelic(BATwinsBookOfProhibitions.ID).makeCopy());
            }
        }
    }

}
