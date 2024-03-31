package baModDeveloper.cards;

import baModDeveloper.action.BATwinsForceDetonationAction;
import baModDeveloper.action.BATwinsHalfPowerAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsBurnPower;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class BATwinsForceDetonation extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("ForceDetonation");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "ForceDetonation");
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;

    public BATwinsForceDetonation() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseMagicNumber = this.magicNumber = 2;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMIDORI(abstractPlayer, abstractMonster);
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for(int i=0;i<this.magicNumber;i++){
            addToBot(new BATwinsForceDetonationAction(1, BATwinsBurnPower.POWER_ID));
            addToBot(new BATwinsForceDetonationAction(1, PoisonPower.POWER_ID));
        }
        for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new BATwinsHalfPowerAction(BATwinsBurnPower.POWER_ID,m));
            addToBot(new BATwinsHalfPowerAction(PoisonPower.POWER_ID,m));

        }

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
}
