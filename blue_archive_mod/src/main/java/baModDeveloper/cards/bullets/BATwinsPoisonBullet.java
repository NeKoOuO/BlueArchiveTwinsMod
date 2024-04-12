package baModDeveloper.cards.bullets;

import baModDeveloper.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class BATwinsPoisonBullet extends BATwinsCustomBulletCard {
    public static final String ID = ModHelper.makePath("PoisonBullet");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "PoisonBullet");
    private static final int COST = -2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardRarity RARITY = CardRarity.SPECIAL;

    public BATwinsPoisonBullet() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 3;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(3);
        this.upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage), AbstractGameAction.AttackEffect.POISON));
        addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new PoisonPower(abstractMonster, abstractPlayer, this.magicNumber)));
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = this.timesUpgraded + 1 + "X" + NAME;
        this.initializeTitle();
    }
}
