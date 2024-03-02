package baModDeveloper.cards;

import baModDeveloper.BATwinsMod;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsExcellentDesigner extends CustomCard {
    public static final String ID = ModHelper.makePath("ExcellentDesigner");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "ExcellentDesigner");
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardRarity RARITY = CardRarity.SPECIAL;

    public BATwinsExcellentDesigner() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 0;
        this.damage = this.baseDamage;
        this.baseBlock = 0;
        this.block = this.baseBlock;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.isEthereal = true;
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.isEthereal = false;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage), AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        this.baseDamage = 0;
        this.baseBlock = 0;
        this.isDamageModified = false;
        this.isBlockModified = false;
        this.applyPowers();
        this.initializeDescription();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        this.superFlash(BATwinsMod.MOMOIColor);
//        this.flash(BATwinsMod.MOMOIColor);
        if (c.baseDamage >= 0) {
            this.baseDamage += c.baseDamage;
            this.isDamageModified = true;
        }
        if (c.baseBlock >= 0) {
            this.baseBlock += c.baseBlock;
            this.isBlockModified = true;
        }
        this.applyPowers();
        this.initializeDescription();
    }

    @Override
    public void triggerWhenDrawn() {
        this.superFlash(BATwinsMod.MIDORIColor);
//        this.flash(BATwinsMod.MIDORIColor);
        addToBot(new DrawCardAction(this.magicNumber));
//        addToBot(new DiscardPileToHandAction(1));
    }

}
