package baModDeveloper.modifier;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.Keyword;

public class BATwinsExhaustModifier extends AbstractCardModifier {
    public static final String ID = "BATwinsExhaustModifier";
    private static final Keyword EXHAUST = GameDictionary.EXHAUST;
    private static final String LineBreak = "NL ";

    @Override
    public AbstractCardModifier makeCopy() {
        return new BATwinsExhaustModifier();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.charAt(rawDescription.length() - 1) != ' ') {
            rawDescription += " ";
        }
        rawDescription += (LineBreak + EXHAUST.NAMES[0] + " ");
        card.exhaust = true;
        return rawDescription;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
