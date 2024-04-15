package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsFundOverdraft extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("FundOverdraft");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "FundOverdraft");
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;
    private int playedCount = 0;

    public BATwinsFundOverdraft() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber - playedCount;

    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        CardCrawlGame.sound.play(ModHelper.makePath("coin"));
        addToBot(new DrawCardAction(this.magicNumber));
        this.playedCount++;
        this.applyPowers();
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer, abstractMonster);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.magicNumber = this.baseMagicNumber - this.playedCount;
        this.magicNumber = Math.max(this.magicNumber, 0);
        if (this.magicNumber != this.baseMagicNumber) {
            this.isMagicNumberModified = true;
        } else {
            this.isMagicNumberModified = false;
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void triggerOnConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        this.playedCount = 0;
        this.applyPowers();
    }

//    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
//        addToBot(new DrawCardAction(this.magicNumber));
//        if(this.magicNumber-1>=0){
//            this.magicNTakeActionsSeparatelyumber--;
//            this.isMagicNumberModified=true;
//        }
//        this.initializeDescription();
//    }
}
