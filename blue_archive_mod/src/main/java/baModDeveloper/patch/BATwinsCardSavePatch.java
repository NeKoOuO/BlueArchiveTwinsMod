package baModDeveloper.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;

public class BATwinsCardSavePatch {
    @SpirePatch(clz = CardSave.class,method = SpirePatch.CLASS)
    public static class FiledPatch{
        public static SpireField<Boolean> isExchange=new SpireField<>(()->false);
    }
}
