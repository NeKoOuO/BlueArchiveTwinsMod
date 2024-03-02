package baModDeveloper.cards;

import baModDeveloper.action.BATwinsClearBringOutCardAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsFocusShooting extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("FocusShooting");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "defaultAttack");
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsFocusShooting() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage = this.damage = 0;
        this.baseMagicNumber = this.magicNumber = 1;
        this.tags.add(BATwinsCardTags.Shooting);
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new BATwinsClearBringOutCardAction(this));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer, abstractMonster);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void applyPowers() {
        int BaseBaseDamage = this.baseDamage;
        this.baseDamage += this.cardToBringOut.size() * this.magicNumber;
        super.applyPowers();
        this.isDamageModified = this.damage != BaseBaseDamage;
        this.baseDamage = BaseBaseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int BaseBaseDamage = this.baseDamage;
        this.baseDamage += this.cardToBringOut.size() * this.magicNumber;
        super.calculateCardDamage(mo);
        this.isDamageModified = this.damage != BaseBaseDamage;
        this.baseDamage = BaseBaseDamage;
    }

    @Override
    public void addBringOutCard(AbstractCard card) {
        super.addBringOutCard(card);
        this.cardToBringOut.add(card.makeSameInstanceOf());
    }
}
