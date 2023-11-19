package baModDeveloper.cards;

import baModDeveloper.action.BATwinsGainEnergyAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsAdventureExperience extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("AdventureExperience");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","defaultSkill");
    private static final int COST=2;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.SKILL;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET=CardTarget.NONE;
    private static final CardRarity RARITY=CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsAdventureExperience() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseBlock=2;
        this.block=this.baseBlock;
        this.baseMagicNumber=1;
        this.magicNumber=this.baseMagicNumber;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new BATwinsGainEnergyAction(this.block, BATwinsEnergyPanel.EnergyType.MOMOI));
        addToBot(new DrawCardAction(this.magicNumber));
        if(BATwinsAdventureOpening.PreviousCardIsAdventrue()){
            addToBot(new BATwinsGainEnergyAction(2, BATwinsEnergyPanel.EnergyType.MIDORI));
        }
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new BATwinsGainEnergyAction(this.block, BATwinsEnergyPanel.EnergyType.MIDORI));
        addToBot(new DrawCardAction(this.magicNumber));
        if(BATwinsAdventureOpening.PreviousCardIsAdventrue()){
            addToBot(new BATwinsGainEnergyAction(2, BATwinsEnergyPanel.EnergyType.MOMOI));
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeBlock(1);
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    protected void applyPowersToBlock() {
        this.block=this.baseBlock;
    }
}
