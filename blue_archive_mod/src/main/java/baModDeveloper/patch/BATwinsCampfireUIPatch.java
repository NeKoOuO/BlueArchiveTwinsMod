package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.ui.panels.button.BATwinsCamfireExchangeButton;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;

public class BATwinsCampfireUIPatch {
    @SpirePatch(clz = CampfireUI.class,method = "initializeButtons")
    public static class initializeButtonsPatch{
        @SpireInsertPatch(rloc= 4)
        public static void initializeButtonsPatch(CampfireUI __instance, ArrayList<AbstractCampfireOption> ___buttons){
            boolean hasBATwinsCard=false;
            for(AbstractCard c:AbstractDungeon.player.masterDeck.group){
                if(c instanceof BATwinsModCustomCard){
                    hasBATwinsCard=true;
                    break;
                }
            }
            if(hasBATwinsCard){
                ___buttons.add(new BATwinsCamfireExchangeButton());
            }
        }
    }
}
