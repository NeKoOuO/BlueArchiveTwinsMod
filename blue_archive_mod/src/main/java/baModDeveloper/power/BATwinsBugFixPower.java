package baModDeveloper.power;

import baModDeveloper.action.BATwinsBugCardAction;
import baModDeveloper.action.BATwinsBugFixAction;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BATwinsBugFixPower extends AbstractPower {
    public static final String POWER_ID= ModHelper.makePath("BugFixPower");
    private static final AbstractPower.PowerType TYPE=PowerType.BUFF;
    private static final PowerStrings powerStrings= CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME=powerStrings.NAME;
    private static final String[] DESCRIPTIONS=powerStrings.DESCRIPTIONS;
    private static final String IMG_84=ModHelper.makeImgPath("power","BugFix84");
    private static final String IMG_32=ModHelper.makeImgPath("power","BugFix32");

    public BATwinsBugFixPower(AbstractCreature onwer,int amount){
        this.ID=POWER_ID;
        this.type=TYPE;
        this.name=NAME;
        this.owner=onwer;
        this.region128=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84),0,0,84,84);
        this.region48=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32),0,0,32,32);
        this.amount=amount;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description=DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new BATwinsBugFixAction(this.amount));
    }
}
