package baModDeveloper.power;

import baModDeveloper.action.BATwinsDefensiveCounterattackAction;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class BATwinsDefensiveCounterattackPower extends AbstractPower {
    public static final String POWER_ID= ModHelper.makePath("DefensiveCounterattackPower");
    public static final String EXCHANGE_POWER_ID= ModHelper.makePath("DefensiveCounterattackExchangePower");

    private static final AbstractPower.PowerType TYPE=PowerType.BUFF;
    private static final PowerStrings powerStrings= CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerStrings exchangePowerStrings=CardCrawlGame.languagePack.getPowerStrings(EXCHANGE_POWER_ID);
    private static final String NAME=powerStrings.NAME;
    private static final String EXCHANGE_NAME= exchangePowerStrings.NAME;
    private static final String[] DESCRIPTIONS=powerStrings.DESCRIPTIONS;
    private static final String[] EXCHANGE_DESCRIPTION=exchangePowerStrings.DESCRIPTIONS;
    private static final String IMG_84=ModHelper.makeImgPath("power","DefensiveCounterattack84");
    private static final String IMG_32=ModHelper.makeImgPath("power","DefensiveCounterattack32");
    private boolean exchange;
    private ArrayList<DamageInfo> damageInfos;
    public BATwinsDefensiveCounterattackPower(AbstractCreature owner,int amount,boolean exchange){
        if(exchange){
            this.name=EXCHANGE_NAME;
            this.ID=EXCHANGE_POWER_ID;
        }else{
            this.name=NAME;
            this.ID=POWER_ID;
        }
        this.owner=owner;
        this.type=TYPE;
        this.amount=amount;
        this.region128=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84),0,0,84,84);
        this.region48=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32),0,0,32,32);
        this.damageInfos=new ArrayList<>();
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if(exchange){
            this.description=EXCHANGE_DESCRIPTION[0]+this.amount+EXCHANGE_DESCRIPTION[1];
        }else {
            this.description=DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        this.damageInfos.add(info);
        this.flash();
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        for(DamageInfo info:damageInfos){
            for(int i=0;i<this.amount;i++){
                if(info.owner instanceof AbstractMonster)
                    addToBot(new BATwinsDefensiveCounterattackAction((AbstractMonster) info.owner,this.exchange));
            }
        }
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this.ID));
    }
}
