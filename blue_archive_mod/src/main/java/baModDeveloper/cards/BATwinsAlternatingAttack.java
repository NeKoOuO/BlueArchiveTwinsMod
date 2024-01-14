package baModDeveloper.cards;

import baModDeveloper.action.BATwinsSelectHandCardToPlayAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsAlternatingAttack extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("AlternatingAttack");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","AlternatingAttack");
    private static final int COST=2;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.ATTACK;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET=CardTarget.ENEMY;
    private static final CardRarity RARITY=CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsAlternatingAttack() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage=10;
        this.damage=this.baseDamage;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage), AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot(new BATwinsSelectHandCardToPlayAction(BATwinsCharacter.Enums.BATWINS_MIDORI_CARD,abstractMonster,CardType.ATTACK,1,this.numberOfConnections+1));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage), AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot(new BATwinsSelectHandCardToPlayAction(BATwinsCharacter.Enums.BATWINS_MOMOI_CARD,abstractMonster,CardType.ATTACK,1,this.numberOfConnections+1));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }

    @Override
    public void triggerOnHovered() {
        if(AbstractDungeon.player!=null){
            AbstractDungeon.player.hand.group.stream().filter(card -> card instanceof BATwinsModCustomCard&&card.color==BATwinsCharacter.getOtherColor(this.color)&&card.type==CardType.ATTACK).forEach(card -> card.flash(BATwinsCharacter.getColorWithCardColor(card.color)));
        }
    }
}
