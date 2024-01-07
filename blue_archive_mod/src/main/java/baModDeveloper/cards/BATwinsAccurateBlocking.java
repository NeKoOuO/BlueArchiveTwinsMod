package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.lang.reflect.Field;

public class BATwinsAccurateBlocking extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("AccurateBlocking");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","defaultSkill");
    private static final int COST=1;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.SKILL;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET=CardTarget.SELF_AND_ENEMY;
    private static final CardRarity RARITY=CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MIDORI;

    public BATwinsAccurateBlocking() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseBlock=8;
        this.block=this.baseBlock;
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            this.upgradeName();
            this.upgradeBlock(3);
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(!(super.canUse(p,m))){
            return false;
        }
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c.color==this.color&&c!=this){
                return false;
            }
        }
        return true;
    }


    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMIDORI(abstractPlayer,abstractMonster);
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer,this.block));
        try{
            Field multiAmt=AbstractMonster.class.getDeclaredField("intentMultiAmt");
            multiAmt.setAccessible(true);
            int amt=multiAmt.getInt(abstractMonster);
            int dmg = abstractMonster.getIntentDmg();
            amt=amt==-1?1:amt;
            if(abstractMonster.intent== AbstractMonster.Intent.ATTACK&&abstractPlayer.currentBlock+this.block<amt*dmg){
                addToBot(new GainBlockAction(abstractPlayer,this.block));
            }
            multiAmt.setAccessible(false);
        }catch (NoSuchFieldException | IllegalAccessException ignored){
            ignored.printStackTrace();
        }

    }
}
