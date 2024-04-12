package baModDeveloper.cards;

import baModDeveloper.action.BATwinsCoverChargeAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsCoverCharge extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("CoverCharge");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "CoverCharge");
    private static final int COST = -1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;

    public int energyOnUseMomoi = -1;
    public int energyOnUseMidori = -1;
    public boolean ignoredEnergyOnUse = true;

    public BATwinsCoverCharge() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage = 5;
        this.damage = this.baseDamage;
        this.block = 5;
        this.baseBlock = 5;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMIDORI(abstractPlayer, abstractMonster);
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (!this.isInAutoplay) {
            this.energyOnUseMomoi = BATwinsEnergyPanel.getMomoiCount();
            this.energyOnUseMidori = BATwinsEnergyPanel.getMidoriCount();
        }
        addToBot(new BATwinsCoverChargeAction(abstractPlayer, this.damage, this.block, this.freeToPlayOnce, abstractMonster, this.ignoredEnergyOnUse ? -1 : this.energyOnUseMomoi, this.ignoredEnergyOnUse ? -1 : this.energyOnUseMidori));
        this.ignoredEnergyOnUse = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeBlock(2);
        }
    }


}
