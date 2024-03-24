package baModDeveloper.cards;

import baModDeveloper.action.BATwinsPaintingArtAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsPaintingArt extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("PaintingArt");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "PaintingArt");
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;

    public BATwinsPaintingArt() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage = 2;
        this.damage = this.baseDamage;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }


    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new BATwinsPaintingArtAction(this, 2, true, this.damageTypeForTurn));
        }
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new BATwinsPaintingArtAction(this, 2, false, this.damageTypeForTurn));
        }
    }
}
