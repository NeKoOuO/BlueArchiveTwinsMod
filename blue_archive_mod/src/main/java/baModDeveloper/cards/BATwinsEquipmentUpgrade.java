package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsEquipmentUpgrade extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("EquipmentUpgrade");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","defaultSkill");
    private static final int COST=1;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.SKILL;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET=CardTarget.SELF;
    private static final CardRarity RARITY=CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsEquipmentUpgrade() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseBlock=5;
        this.block=this.baseBlock;
        this.baseMagicNumber=2;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void upgrade() {

    }
}
