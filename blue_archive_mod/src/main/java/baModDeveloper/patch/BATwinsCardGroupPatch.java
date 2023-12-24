package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsModCustomCard;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardSave;

import java.util.ArrayList;
import java.util.Iterator;

public class BATwinsCardGroupPatch {
    @SpirePatch(clz = CardGroup.class,method = "getCardDeck")
    public static class getCardDeckPatch{
        @SpireInsertPatch(rloc = 4,localvars = {"retVal"})
        public static void getCardGroupPatch(CardGroup _instance, @ByRef ArrayList<CardSave>[] retVal){
            for(int i=0;i<_instance.group.size();i++){
                AbstractCard card=_instance.group.get(i);
                if(card instanceof BATwinsModCustomCard){
                    if(((BATwinsModCustomCard) card).exchanged()){
                        BATwinsCardSavePatch.FiledPatch.isExchange.set(retVal[0].get(i),true);
                    }
                }
            }
        }
    }
}
