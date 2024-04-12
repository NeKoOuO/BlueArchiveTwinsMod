package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsAccumulatedStrike extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("AccumulatedStrike");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "AccumulatedStrike");
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;
    private int realBaseDamage;

    public BATwinsAccumulatedStrike() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage = 6;
        this.realBaseDamage = this.baseDamage;
        this.damage = this.baseDamage;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.STRIKE);
    }

    public static int strickCount() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.hasTag(CardTags.STRIKE)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMIDORI(abstractPlayer, abstractMonster);
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot((AbstractGameAction) new DamageAction((AbstractCreature) abstractMonster, new DamageInfo((AbstractCreature) abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void applyPowers() {
        this.realBaseDamage = this.baseDamage;
        this.baseDamage = this.baseDamage + strickCount() * this.magicNumber;
        super.applyPowers();
        this.baseDamage = this.realBaseDamage;
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        this.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.realBaseDamage = this.baseDamage;
        this.baseDamage = this.baseDamage + strickCount() * this.magicNumber;
        super.calculateCardDamage(mo);
        super.applyPowers();
        this.baseDamage = this.realBaseDamage;
    }
}
