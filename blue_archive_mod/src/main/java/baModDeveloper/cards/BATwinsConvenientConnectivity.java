package baModDeveloper.cards;

import baModDeveloper.action.BATwinsSelectDrawPileCardToPlayAction;
import baModDeveloper.action.BATwinsSelectHandCardToPlayAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsConvenientConnectivity extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("ConvenientConnectivity");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "ConvenientConnectivity");
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;

    public BATwinsConvenientConnectivity() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new BATwinsSelectHandCardToPlayAction(null, null, this.numberOfConnections + 1, false));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer, abstractMonster);
    }

    @Override
    public void triggerOnConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.upgraded) {
            addToBot(new BATwinsSelectDrawPileCardToPlayAction(false, null, 1, this.numberOfConnections + 1, false, false));
        } else {
            addToBot(new BATwinsSelectDrawPileCardToPlayAction(true, null, 1, this.numberOfConnections + 1, false, false));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.originRawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;

            this.initializeDescription();
        }
    }
}
