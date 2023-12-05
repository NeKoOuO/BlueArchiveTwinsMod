package baModDeveloper.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.sun.org.apache.xpath.internal.operations.Bool;

@SpirePatch(clz = AbstractCard.class,method = SpirePatch.CLASS)
public class BATwinsAbstractCardPatch {
    public static SpireField<Boolean> blockTheOriginalEffect=new SpireField<>(()->false);
}
