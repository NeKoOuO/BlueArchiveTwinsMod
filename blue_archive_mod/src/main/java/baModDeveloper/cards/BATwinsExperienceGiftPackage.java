package baModDeveloper.cards;

import baModDeveloper.action.BATwinsPlayHandCardAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsExperiencePower;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsExperienceGiftPackage extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("ExperienceGiftPackage");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "ExperienceGiftPackage");
    private static final int COST = 8;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.SHARE;

    public BATwinsExperienceGiftPackage() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMIDORI(abstractPlayer, abstractMonster);
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
//        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BATwinsExperiencePower(abstractPlayer,this.costForTurn)));
        addToBot(new DrawCardAction(this.magicNumber));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hand.contains(this) && AbstractDungeon.player.hasPower(BATwinsExperiencePower.POWER_ID)) {
            if (this.costForTurn!=0&&this.costForTurn <= AbstractDungeon.player.getPower(BATwinsExperiencePower.POWER_ID).amount) {
                addToBot(new BATwinsPlayHandCardAction(this, null));
            }
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        super.use(abstractPlayer, abstractMonster);
        this.modifyCostForCombat(-1);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.upgradeBaseCost(8);
            this.upgradeMagicNumber(1);
        }
    }
}
