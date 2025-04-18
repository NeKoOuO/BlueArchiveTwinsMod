package baModDeveloper.power;

import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BATwinsDoubleExperiencePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("DoubleExperiencePower");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_84 = ModHelper.makeImgPath("power", "DoubleExperience84");
    private static final String IMG_32 = ModHelper.makeImgPath("power", "DoubleExperience32");
    private static TextureAtlas.AtlasRegion REGION128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84), 0, 0, 84, 84);
    private static TextureAtlas.AtlasRegion REGION48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32), 0, 0, 32, 32);

    public BATwinsDoubleExperiencePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = TYPE;
        this.amount = -1;
        this.region128 = REGION128;
        this.region48 = REGION48;

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

//    @Override
//    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
//        if(power.ID.equals(BATwinsExperiencePower.POWER_ID)){
//            power.amount*=2;
//        }
//    }
}
