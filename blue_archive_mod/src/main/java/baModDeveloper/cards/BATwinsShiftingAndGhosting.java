package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsShiftingAndGhosting extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("ShiftingAndGhosting");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "ShiftingAndGhosting");
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;

    public BATwinsShiftingAndGhosting() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseBlock = 12;
        this.block = this.baseBlock;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        BATwinsModCustomCard card=new BATwinsExchange();
        card.conversionColor(false);
        this.cardsToPreview=card;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMIDORI(abstractPlayer, abstractMonster);
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, this.block));
//        addToBot(new BATwinsExchangeDrawPailAction(this.magicNumber));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview,this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
//            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void conversionColor(boolean flash) {
        super.conversionColor(flash);
        if(this.cardsToPreview instanceof BATwinsModCustomCard){
            ((BATwinsModCustomCard) this.cardsToPreview).conversionColor(false);
        }
    }
}
