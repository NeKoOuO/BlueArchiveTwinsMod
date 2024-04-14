package baModDeveloper.modifier;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.Keyword;

public class BATwinsRetainModifier extends AbstractCardModifier {
    public static final String ID = "BATwinsRetainModifier";
    private static final Keyword RETAIN = GameDictionary.RETAIN;
    private static final String LineBreak = "NL ";

    @Override
    public AbstractCardModifier makeCopy() {
        return new BATwinsRetainModifier();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.charAt(rawDescription.length() - 1) != ' ') {
            rawDescription += " ";
        }
        rawDescription += (LineBreak + RETAIN.NAMES[0] + " ");
        if (!card.isEthereal) {
            card.selfRetain = true;
        }
        return rawDescription;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
