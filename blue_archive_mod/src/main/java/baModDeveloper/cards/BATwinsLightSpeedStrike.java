package baModDeveloper.cards;

import baModDeveloper.action.BATwinsPlayHandCardAction;
import baModDeveloper.action.BATwinsSelectHandCardToPlayAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsFlatFallPower;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EntanglePower;

import java.util.ArrayList;

public class BATwinsLightSpeedStrike extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("LightSpeedStrike");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","LightSpeedStrike");
    private static final int COST=2;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.ATTACK;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET=CardTarget.ENEMY;
    private static final CardRarity RARITY=CardRarity.COMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsLightSpeedStrike() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage=6;
        this.damage=this.baseDamage;
        this.tags.add(CardTags.STRIKE);

    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage), AbstractGameAction.AttackEffect.LIGHTNING));
        if(playedAttack(true)){
            addToBot(new BATwinsSelectHandCardToPlayAction(null,abstractMonster,CardType.ATTACK));
            addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BATwinsFlatFallPower(abstractPlayer)));
        }

//        abstractPlayer.hand.group.removeAll(strikeCards);
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer,abstractMonster);

    }

    @Override
    public void upgrade() {
        if(!upgraded){
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    @Override
    public void triggerOnHovered() {
        if(playedAttack(false)){
            AbstractDungeon.player.hand.group.stream().filter(card -> card.type==CardType.ATTACK&&card!=this).forEach(card -> card.flash(BATwinsCharacter.getColorWithCardColor(card.color)));
        }
    }

    private boolean playedAttack(boolean playing){
        int a=0;
        if(playing){
            a=1;
        }
        for(int i=0;i<AbstractDungeon.actionManager.cardsPlayedThisTurn.size()-a;i++){
            if(AbstractDungeon.actionManager.cardsPlayedThisTurn.get(i).type==CardType.ATTACK){
                return false;
            }
        }
        return true;
    }

    @Override
    public void triggerOnGlowCheck() {
        if(playedAttack(false)){
            this.glowColor=AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }else{
            this.glowColor=AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
}
