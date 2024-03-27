package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.relic.BATwinsHeadband;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BATwinsHeadbandChice extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("HeadbandChice");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","HeadbandChice");
    private static final int COST=-2;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.POWER;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET=CardTarget.NONE;
    private static final CardRarity RARITY=CardRarity.SPECIAL;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsHeadbandChice() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onChoseThisOption();
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onChoseThisOption();
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void onChoseThisOption() {
        for(AbstractRelic r:AbstractDungeon.player.relics){
            if(r instanceof BATwinsHeadband){
                if(this.color==BATwinsCharacter.Enums.BATWINS_MOMOI_CARD){
                    ((BATwinsHeadband) r).setEnergyType(BATwinsEnergyPanel.EnergyType.MOMOI);
                }else{
                    ((BATwinsHeadband) r).setEnergyType(BATwinsEnergyPanel.EnergyType.MIDORI);
                }
            }
        }
    }

    @Override
    public void conversionColor(boolean flash) {
        super.conversionColor(flash);
        this.exchangeName();
    }

    protected void exchangeName() {
        if (this.color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD) {
            this.name = CARD_STRINGS.NAME;
        } else {
            this.name = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        }
        if (this.upgraded) {
            this.upgradeName();
        }
    }
}
