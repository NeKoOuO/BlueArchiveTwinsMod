package baModDeveloper.cards.colorless;

import baModDeveloper.action.BATwinsAccelerateAction;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.patch.BATwinsAbstractCardPatch;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsAccelerate extends CustomCard {
    public static final String ID = ModHelper.makePath("Accelerate");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "Accelerate");
    private static final int COST = -1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardRarity RARITY = CardRarity.RARE;

    public int connectionCost = -1;
//    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsAccelerate() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.connectionCost != -1) {
            this.energyOnUse = this.connectionCost;
            this.connectionCost = -1;
        }
        addToBot(new BATwinsAccelerateAction(BATwinsAbstractCardPatch.FieldPatch.numberOfConnections.get(this) + 1, this.energyOnUse, this.freeToPlayOnce, this.upgraded));
//        AbstractDungeon.player.energy.use(AbstractDungeon.player.energy.energy);
        BATwinsAbstractCardPatch.FieldPatch.numberOfConnections.set(this, 0);
    }
}
