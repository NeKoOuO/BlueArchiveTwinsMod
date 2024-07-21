package baModDeveloper.cards;

import baModDeveloper.action.BATwinsClearBringOutCardAction;
import baModDeveloper.cards.bullets.BATwinsCustomBulletCard;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsContinuousShooting extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("ContinuousShooting");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "ContinuousShooting");
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;

    public BATwinsContinuousShooting() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseMagicNumber = this.magicNumber = 1;
        this.selfRetain = true;
        this.tags.add(BATwinsCardTags.Shooting);
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new BATwinsClearBringOutCardAction(this));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer, abstractMonster);
    }

    @Override
    public void upgrade() {
        this.upgraded = true;
        this.timesUpgraded++;
        this.name = NAME + "+" + Integer.toString(this.timesUpgraded);
        this.initializeTitle();
        this.upgradeMagicNumber(this.timesUpgraded);
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void onRetained() {
        this.flash(BATwinsCharacter.getColorWithCardColor(this.color));
        for (int i = 0; i < this.magicNumber; i++) {
            this.addBringOutCard(BATwinsCustomBulletCard.getRandomBullet());
        }
    }
}
