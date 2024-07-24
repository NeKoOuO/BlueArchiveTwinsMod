package baModDeveloper.power;

import baModDeveloper.action.BATwinsNormalAttackMethodsAction;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BATwinsNormalAttackMethodsPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("NormalAttackMethodsPower");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_84 = ModHelper.makeImgPath("power", "NormalAttackMethods84");
    private static final String IMG_32 = ModHelper.makeImgPath("power", "NormalAttackMethods32");
    private static TextureAtlas.AtlasRegion REGION128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84), 0, 0, 84, 84);
    private static TextureAtlas.AtlasRegion REGION48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32), 0, 0, 32, 32);

    public BATwinsNormalAttackMethodsPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = TYPE;
        this.amount = amount;
        this.region128 = REGION128;
        this.region48 = REGION48;

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new WaitAction(0.5F));
        addToBot(new BATwinsNormalAttackMethodsAction(this.amount, false));
    }

//    @Override
//    public void atStartOfTurnPostDraw() {
//        flash();
//        addToBot(new WaitAction(0.5F));
//        addToBot(new BATwinsNormalAttackMethodsAction(this.amount,false));
//    }

//    @Override
//    public void onUseCard(AbstractCard card, UseCardAction action) {
//        if(card.hasTag(AbstractCard.CardTags.STRIKE)&&card instanceof BATwinsModCustomCard){
//            ((BATwinsModCustomCard) card).modifyEnergyType= BATwinsEnergyPanel.EnergyType.SHARE;
//        }
//    }
}
