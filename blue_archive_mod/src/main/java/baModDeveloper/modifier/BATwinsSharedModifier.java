package baModDeveloper.modifier;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class BATwinsSharedModifier extends AbstractCardModifier {
    public static final String ID = "BATwinsSharedModifier";
    //    private static final Keyword SHARED= CardCrawlGame.languagePack.getKeywordString(ModHelper.makePath(""));
    private static final String LineBreak = "NL ";
    private static final String[] SHARED_NAME = new String[]{
            "batwinsmod:共有",
            "batwinsmod:share",
            "batwinsmod:共有"
    };
    private static int strPos = 0;

//    private static String SHARED;

    static {
        switch (Settings.language) {
            case ZHS:
            case ZHT:
                strPos = 0;
                break;
            case JPN:
                strPos=2;
            default:
                strPos = 1;
                break;
        }
//        SHARED=GameDictionary.keywords.get(ModHelper.makePath(SHARED_NAME[strPos]));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BATwinsSharedModifier();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (card instanceof BATwinsModCustomCard) {
            if (rawDescription.charAt(rawDescription.length() - 1) != ' ') {
                rawDescription += " ";
            }
            rawDescription += (LineBreak + SHARED_NAME[strPos] + " ");
            ((BATwinsModCustomCard) card).modifyEnergyType = BATwinsEnergyPanel.EnergyType.SHARE;
        }
        return rawDescription;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
