package baModDeveloper.cards;

import baModDeveloper.action.BATwinsLearnedAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BATwinsLearned extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("Learned");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "Learned");
    private static final int COST = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;
    private static final UIStrings uistrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    private final Predicate<AbstractCard> filter = (card) -> {
        return card.type != CardType.POWER && card != BATwinsLearned.this;
    };
    private final Consumer<List<AbstractCard>> callback = (cards) -> {
        for (AbstractCard card : cards) {
            if (card != null) {
                addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                BATwinsLearned.this.addBringOutCard(card);
            }
        }

    };

    public BATwinsLearned() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage = 10;
        this.damage = this.baseDamage;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new BATwinsLearnedAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, DamageInfo.DamageType.NORMAL), this.bringOutCard ? this.cardToBringOut.get(0) : null, this.color));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new BATwinsLearnedAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, DamageInfo.DamageType.NORMAL), this.bringOutCard ? this.cardToBringOut.get(0) : null, this.color));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        if (!this.bringOutCard) {
            addToBot(new SelectCardsInHandAction(1, String.format(uistrings.TEXT[8], this.name), false, false, filter, callback));
        }
    }

}
