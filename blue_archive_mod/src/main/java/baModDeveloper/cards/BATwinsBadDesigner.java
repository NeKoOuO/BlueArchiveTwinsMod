package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsBadDesigner extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("BadDesigner");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","defaultSkill");
    private static final int COST=1;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.SKILL;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET=CardTarget.NONE;
    private static final CardRarity RARITY=CardRarity.RARE;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.SHARE;

    public BATwinsBadDesigner() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.misc=5;
        this.baseMagicNumber=this.misc;
        this.magicNumber=this.baseMagicNumber;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(this.magicNumber<=1){
            this.exhaust=true;
            for(AbstractCard c:abstractPlayer.masterDeck.group) {
                if (c.uuid == this.uuid) {
                    abstractPlayer.masterDeck.removeCard(c);
                }
            }
        }else{
            addToBot(new IncreaseMiscAction(this.uuid,this.misc,-1));
            this.upgradeMagicNumber(-1);
        }
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer,abstractMonster);
    }

    @Override
    public void triggerOnExhaust() {
        AbstractDungeon.player.exhaustPile.removeCard(this);
    }

    @Override
    public void onRemoveFromMasterDeck() {
        addToBot(new AddCardToDeckAction(new BATwinsExcellentDesigner()));
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        addToBot(new DiscardAction(AbstractDungeon.player,AbstractDungeon.player,1,false));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            this.upgradeName();
            this.misc-=2;
            if(this.misc<=1){
                this.misc=1;
            }
            this.magicNumber=this.misc;
            this.initializeDescription();
        }
    }
    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber=this.misc;
        this.magicNumber=this.misc;
        this.initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(!super.canUse(p,m)){
            return false;
        }
        return p.hand.size() == 1 && p.hand.getTopCard() == this;
    }
}
