package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsBurnPower;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsAlreadyAngry extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("AlreadyAngry");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","defaultAttack");
    private static final int COST=2;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.ATTACK;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET=CardTarget.ENEMY;
    private static final CardRarity RARITY=CardRarity.BASIC;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsAlreadyAngry() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage=9;
        this.damage=this.baseDamage;
        this.baseMagicNumber=3;
        this.magicNumber=this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            this.upgradeName();
            this.upgradeDamage(4);
            this.magicNumber+=2;
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new BATwinsBurnPower(abstractMonster,this.magicNumber)));
    }
}
