package baModDeveloper.cards;

import baModDeveloper.action.BATwinsPlayHandCardAction;
import baModDeveloper.action.BATwinsSelectHandCardToPlayAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.Collectors;

public class BATwinsPaintingConception extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("PaintingConception");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "PaintingConception");
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;

    public BATwinsPaintingConception() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseBlock = 3;
        this.block = this.baseBlock;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
//            this.upgradeBlock(3);
//            this.upgradeMagicNumber(1);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.originRawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, this.block));
//        addToBot(new BATwinsGainEnergyAction(1, BATwinsEnergyPanel.EnergyType.MIDORI));

        if (this.upgraded) {
            addToBot(new BATwinsSelectHandCardToPlayAction(this.color, null, this.numberOfConnections + 1, true));
        } else {
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            temp.group.addAll(abstractPlayer.hand.group.stream().filter(c -> c.color == this.color && c != this && c.type != CardType.POWER&&c.cost!=-2).collect(Collectors.toList()));
            for (int i = 0; i < this.magicNumber; i++) {
                if (temp.isEmpty()) {
                    return;
                }
                AbstractCard card = temp.getRandomCard(AbstractDungeon.cardRandomRng);
                addToBot(new BATwinsPlayHandCardAction(card, null, this.numberOfConnections + 1));
                temp.removeCard(card);
//                abstractPlayer.hand.removeCard(card);
            }
        }


    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
//        addToBot(new GainBlockAction(abstractPlayer, this.block));
//        addToBot(new BATwinsGainEnergyAction(1, BATwinsEnergyPanel.EnergyType.MOMOI));
        useMOMOI(abstractPlayer, abstractMonster);
    }
}
