package baModDeveloper.cards;

import baModDeveloper.action.BATwinsAdventureBeginsAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Optional;

public class BATwinsAdventureBegins extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("AdventureBegins");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "AdventureBegins");
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;
    //    private ArrayList<AbstractCard> cardsToBringOut;
    public static CardType[] TYPES = {CardType.ATTACK, CardType.SKILL, CardType.POWER, CardType.STATUS, CardType.CURSE};

    public BATwinsAdventureBegins() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
//        cardsToBringOut=new ArrayList<>();
        initializeDescription();
        initializationFlavor();
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
//        for(AbstractCard card:cardsToBringOut){
//            addToBot(new BATwinsPlayTempCardAction(card,this.numberOfConnections+1));
//        }
        ArrayList<CardType> types = new ArrayList<>();
        for (AbstractCard c : this.cardToBringOut) {
            types.add(c.type);
        }
        addToBot(new BATwinsAdventureBeginsAction(types, 1, this.upgraded, this));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer, abstractMonster);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.originRawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
            initializationFlavor();
        }
    }


    @Override
    public void addBringOutCard(AbstractCard card) {
        super.addBringOutCard(card);
        initializationFlavor();

    }

    public void initializationFlavor() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < CARD_STRINGS.EXTENDED_DESCRIPTION.length; i++) {
            int finalI = i;
            if (this.cardToBringOut != null && this.cardToBringOut.stream().anyMatch(c -> BATwinsAdventureBegins.TYPES[finalI] == c.type)) {
                stringBuilder.append(" #r");
                Optional<AbstractCard> card = this.cardToBringOut.stream().filter(c -> c.type == BATwinsAdventureBegins.TYPES[finalI]).findFirst();
                card.ifPresent(abstractCard -> stringBuilder.append(abstractCard.name));
            } else {
                stringBuilder.append(" #g");
                stringBuilder.append(CARD_STRINGS.EXTENDED_DESCRIPTION[i]);
            }
            if (i != CARD_STRINGS.EXTENDED_DESCRIPTION.length - 1) {
                stringBuilder.append(",");
            }
        }
        FlavorText.AbstractCardFlavorFields.flavor.set(this, stringBuilder.toString());
    }
}
