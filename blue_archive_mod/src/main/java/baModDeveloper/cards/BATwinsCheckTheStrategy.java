package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;

public class BATwinsCheckTheStrategy extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("CheckTheStrategy");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "CheckTheStrategy");
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;
    private static final UIStrings uiString = CardCrawlGame.languagePack.getUIString("DiscardAction");

    public BATwinsCheckTheStrategy() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

        addToBot(new SelectCardsInHandAction(this.magicNumber, uiString.TEXT[0], false, false, this::filter, this::callback));
        addToBot(new DrawCardAction(this.magicNumber));
    }

    private void callback(List<AbstractCard> cards) {
        for (AbstractCard c : cards) {
            AbstractDungeon.player.hand.moveToDiscardPile(c);
            c.triggerOnManualDiscard();
            GameActionManager.incrementDiscard(false);
        }
        if (!cards.isEmpty()) {
            boolean allSameColor = true;
            AbstractCard.CardColor color = cards.get(0).color;
            for (int i = 1; i < cards.size(); i++) {
                if (cards.get(i).color != color) {
                    allSameColor = false;
                    break;
                }
            }
            if (allSameColor) {
                addToBot(new DrawCardAction(1));
            }
        }
        cards.clear();

    }

    private boolean filter(AbstractCard card) {
        return true;
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer, abstractMonster);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
//            this.upgradeMagicNumber(1);
            this.originRawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.exhaust = false;
        }
    }

}
