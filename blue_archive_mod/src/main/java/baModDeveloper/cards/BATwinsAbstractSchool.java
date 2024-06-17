package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.patch.BATwinsAbstractMonsterPatch;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BATwinsAbstractSchool extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("AbstractSchool");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "AbstractSchool");
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;


    public BATwinsAbstractSchool() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage = 0;
        this.damage = this.baseDamage;
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = this.block = 5;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMIDORI(abstractPlayer, abstractMonster);
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage), AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot(new DrawCardAction(this.magicNumber));
        if (abstractMonster != null)
            BATwinsAbstractMonsterPatch.addPixelMonster(abstractMonster);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
        }
    }


    private int calColorNum() {
        ArrayList<CardColor> colors = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (!colors.contains(c.color) && c != this) {
                colors.add(c.color);
            }
        }
        return colors.size();
    }

    @Override
    public void applyPowers() {
        int colorNum = calColorNum();
        int baseBaseDamage = this.baseDamage;
        this.baseDamage = this.baseBlock * colorNum;
        int baseBaseMagicNumber = this.baseMagicNumber;
        this.baseMagicNumber = colorNum;
        this.magicNumber = this.baseMagicNumber;
        super.applyPowers();
        this.isDamageModified = this.damage != colorNum;
        this.isMagicNumberModified = this.magicNumber != baseBaseMagicNumber;
        this.baseDamage = baseBaseDamage;
        this.baseMagicNumber = baseBaseMagicNumber;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int colorNum = calColorNum();
        int baseBaseDamage = this.baseDamage;
        this.baseDamage = this.baseBlock * colorNum;
        int baseBaseMagicNumber = this.baseMagicNumber;
        this.baseMagicNumber = colorNum;
        this.magicNumber = this.baseMagicNumber;
        super.calculateCardDamage(mo);
        this.isDamageModified = this.damage != colorNum;
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
        this.baseDamage = baseBaseDamage;
        this.baseMagicNumber = baseBaseMagicNumber;
    }

    @Override
    protected void applyPowersToBlock() {

    }
}
