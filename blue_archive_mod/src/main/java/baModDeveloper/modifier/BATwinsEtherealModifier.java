package baModDeveloper.modifier;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.Keyword;

public class BATwinsEtherealModifier extends AbstractCardModifier {
    public static final String ID = "BATwinsEtherealModifier";
    private static final Keyword ETHEREAL = GameDictionary.ETHEREAL;
    private static final String LineBreak = "NL ";

    @Override
    public AbstractCardModifier makeCopy() {
        return new BATwinsEtherealModifier();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {

        if (rawDescription.charAt(rawDescription.length() - 1) != ' ') {
            rawDescription += " ";
        }
        rawDescription += (LineBreak + ETHEREAL.NAMES[0] + " ");
        card.isEthereal = true;
        if (card.selfRetain) {
            card.selfRetain = false;
        }
        if (card.retain) {
            card.retain = false;
        }
        return rawDescription;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
