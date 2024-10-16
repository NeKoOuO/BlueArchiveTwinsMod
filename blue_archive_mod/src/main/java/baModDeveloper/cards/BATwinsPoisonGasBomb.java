package baModDeveloper.cards;

import baModDeveloper.action.BATwinsOneMoreAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsBurnPower;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class BATwinsPoisonGasBomb extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("PoisonGasBomb");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "PoisonGasBomb");
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;

    public BATwinsPoisonGasBomb() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new BATwinsBurnPower(abstractMonster, abstractPlayer, this.magicNumber)));
        addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new PoisonPower(abstractMonster, abstractPlayer, this.magicNumber)));
        addToBot(new BATwinsOneMoreAction(BATwinsBurnPower.POWER_ID, abstractMonster));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new PoisonPower(abstractMonster, abstractPlayer, this.magicNumber)));
        addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new BATwinsBurnPower(abstractMonster, abstractPlayer, this.magicNumber)));
        addToBot(new BATwinsOneMoreAction(PoisonPower.POWER_ID, abstractMonster));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);

        }
    }

    @Override
    public void triggerOnConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD) {
            addToBot(new BATwinsOneMoreAction(PoisonPower.POWER_ID, abstractMonster));
        } else {
            addToBot(new BATwinsOneMoreAction(BATwinsBurnPower.POWER_ID, abstractMonster));
        }
    }
}
