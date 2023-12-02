package baModDeveloper.cards;

import baModDeveloper.action.BATwinsCoversionColorAction;
import baModDeveloper.action.BATwinsMakeTempCardInHandAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsMysteriousChest extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("MysteriousChest");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","defaultSkill");
    private static final int COST=1;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.SKILL;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET=CardTarget.NONE;
    private static final CardRarity RARITY=CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MIDORI;

    public BATwinsMysteriousChest() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);

    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractCard c= ModHelper.returnTrulyRandomCardInCombatByColor(this.color);
        c.cost=0;
        c.costForTurn=0;
        c.isCostModified=true;
//        c.exhaustOnUseOnce=true;
        if(this.upgraded){
            addToBot(new BATwinsMakeTempCardInHandAction(c,false,false,true,false,true));
        }else{
            addToBot(new BATwinsMakeTempCardInHandAction(c,false,false,true,true,false));
        }
        addToBot(new BATwinsCoversionColorAction(this));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractCard c= ModHelper.returnTrulyRandomCardInCombatByColor(this.color);
        c.cost=0;
        c.costForTurn=0;
        c.isCostModified=true;
//        c.exhaustOnUseOnce=true;
        if(this.upgraded){
            addToBot(new BATwinsMakeTempCardInHandAction(c,false,false,true,false,true));
        }else{
            addToBot(new BATwinsMakeTempCardInHandAction(c,false,false,true,true,false));
        }
        addToBot(new BATwinsCoversionColorAction(this));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            this.upgradeName();
            this.rawDescription=CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
