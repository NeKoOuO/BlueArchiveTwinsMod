package baModDeveloper.cards;

import baModDeveloper.action.BATwinsAdventureOpeningAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BATwinsAdventureOpening extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("AdventureOpening");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "defaultSkill");
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;

    private ArrayList<AbstractCard> StorageCards;

    public BATwinsAdventureOpening() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.StorageCards = new ArrayList<>();
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(BATwinsCardTags.Adventure);
        this.selfRetain = true;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new BATwinsAdventureOpeningAction(this.StorageCards, this));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer, abstractMonster);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.modifyEnergyType = BATwinsEnergyPanel.EnergyType.SHARE;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.originRawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void addStorageCard(AbstractCard card) {
        this.StorageCards.add(card);
        this.initializeDescription();
    }

    @Override
    public void initializeDescription() {
        this.appendCardDescription();
        super.initializeDescription();
    }

    private void appendCardDescription() {
        if (this.StorageCards == null || this.StorageCards.isEmpty()) {
            return;
        }
        if (this.upgraded)
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        else
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        this.rawDescription += " NL ";
        StringBuilder strBuilder = new StringBuilder(this.rawDescription);
        for (int i = 0; i < this.StorageCards.size(); i++) {
            strBuilder.append(this.StorageCards.get(i).name.replace("冒险-", ""));
            if (i != this.StorageCards.size() - 1) {
                strBuilder.append("→");
            }
        }
        this.rawDescription = strBuilder.toString();
    }

    public static boolean PreviousCardIsAdventrue() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty()) {
            AbstractCard previousCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1);
            return previousCard.hasTag(BATwinsCardTags.Adventure);
        }
        return false;
    }
}
