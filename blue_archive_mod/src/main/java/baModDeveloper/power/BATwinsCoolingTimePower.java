package baModDeveloper.power;

import baModDeveloper.action.BATwinsCoolingTimePowerAction;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.DeckToHandAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class BATwinsCoolingTimePower extends AbstractPower {
    public static final String POWER_ID= ModHelper.makePath("CoolingTimePower");
    private static final AbstractPower.PowerType TYPE=PowerType.BUFF;
    private static final PowerStrings powerStrings= CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME=powerStrings.NAME;
    private static final String[] DESCRIPTIONS=powerStrings.DESCRIPTIONS;
    private static final String IMG_84=ModHelper.makeImgPath("power","CoolingTime84");
    private static final String IMG_32=ModHelper.makeImgPath("power","CoolingTime32");
    private ArrayList<AbstractCard> strogedCards=new ArrayList<>();

    public BATwinsCoolingTimePower(AbstractCreature owner, AbstractCard card){
        this.name=NAME;
        this.ID=POWER_ID;
        this.owner=owner;
        this.type=TYPE;
        this.amount=-1;
        this.strogedCards.add(card);
        this.region128=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84),0,0,84,84);
        this.region48=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32),0,0,32,32);
        this.updateDescription();
    }


    public void stackPower(AbstractCard card){
        this.strogedCards.add(card);
    }

    @Override
    public void updateDescription() {
        this.description=DESCRIPTIONS[0];
        StringBuilder stringBuilder=new StringBuilder(this.description);
        stringBuilder.append("\n");
        for(AbstractCard c:this.strogedCards){
            stringBuilder.append(c.name);
            stringBuilder.append("\n");
        }
        stringBuilder.append(DESCRIPTIONS[1]);
        this.description=stringBuilder.toString();
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        addToBot(new BATwinsCoolingTimePowerAction(this.strogedCards));
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,this.ID));
    }
}
