package baModDeveloper.power;

import baModDeveloper.BATwinsMod;
import baModDeveloper.action.BATwinsGainEnergyAction;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BATwinsGainTENextTurnPower extends AbstractPower {
    public static final String POWER_ID= ModHelper.makePath("GainTENextTurnPower");
    private static final AbstractPower.PowerType TYPE=PowerType.BUFF;
    private static final PowerStrings powerStrings= CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME=powerStrings.NAME;
    private static final String[] DESCRIPTIONS=powerStrings.DESCRIPTIONS;
    private static final String IMG_84=ModHelper.makeImgPath("power","GainTENextTurn84");
    private static final String IMG_32=ModHelper.makeImgPath("power","GainTENextTurn32");
    public BATwinsGainTENextTurnPower(AbstractCreature owner, int amount){
        this.ID=POWER_ID;
        this.name=NAME;
        this.type=TYPE;
        this.owner=owner;
        this.amount=amount;
        this.region128=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84),0,0,84,84);
        this.region48=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32),0,0,32,32);
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description=DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
    }



    @Override
    public void atStartOfTurn() {
        this.flash();
        if(this.amount>0){
            addToBot(new BATwinsGainEnergyAction(this.amount, BATwinsEnergyPanel.EnergyType.MOMOI));
        }
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,POWER_ID));
    }
}
