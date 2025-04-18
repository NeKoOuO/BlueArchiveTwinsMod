package baModDeveloper.cards;

import baModDeveloper.helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Objects;

public class BATwinsEasterEgg extends CustomCard {
    public static final String ID = ModHelper.makePath("EasterEgg");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "defaultSkill");
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static String dungeon;

    public BATwinsEasterEgg() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
//        AbstractDungeon.effectList.add(new BATwinsEasterEggEffect(BATwinsCharacterOptionPatch.updateHitboxPatch.random.nextBoolean(), this.upgraded));
        addToBot(new DrawCardAction(this.magicNumber));
        if (dungeon == null || !Objects.equals(AbstractDungeon.id, dungeon)) {
            dungeon = AbstractDungeon.id;
            CardCrawlGame.music.dispose();
            CardCrawlGame.music.changeBGM(ModHelper.makePath("pixelTime"));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new BATwinsEasterEgg();
    }
}
