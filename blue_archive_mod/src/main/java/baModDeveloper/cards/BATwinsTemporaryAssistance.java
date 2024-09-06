package baModDeveloper.cards;

import baModDeveloper.action.BATwinsDrawOrDisCardToHandAction;
import baModDeveloper.action.BATwinsExchangeAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsTemporaryAssistance extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("TemporaryAssistance");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "TemporaryAssistance");
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;
    private static int priviousEnengyAmount = 0;

    public BATwinsTemporaryAssistance() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMIDORI(abstractPlayer, abstractMonster);
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new BATwinsExchangeAction(1, true));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.originRawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void triggerOnEnergyExhausted(BATwinsEnergyPanel.EnergyType energyType) {
        boolean trigger = false;
        if (this.color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD && energyType == BATwinsEnergyPanel.EnergyType.MOMOI) {
            trigger = true;
        } else if (this.color == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD && energyType == BATwinsEnergyPanel.EnergyType.MIDORI) {
            trigger = true;
        }
        if (!trigger || AbstractDungeon.player.discardPile.contains(this) && !this.upgraded) {
            return;
        }
        addToBot(new BATwinsDrawOrDisCardToHandAction(this));
    }
}
