package baModDeveloper.power;

import baModDeveloper.action.BATwinsOneMoreAction;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class BATwinsOnceMoreExchangePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("OnceMoreExchangePower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_84 = ModHelper.makeImgPath("power", "OnceMore84");
    private static final String IMG_32 = ModHelper.makeImgPath("power", "OnceMore32");
    private static TextureAtlas.AtlasRegion REGION128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84), 0, 0, 84, 84);
    private static TextureAtlas.AtlasRegion REGION48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32), 0, 0, 32, 32);

    public BATwinsOnceMoreExchangePower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.type = TYPE;
        this.region128 = REGION128;
        this.region48 = REGION48;

        this.owner = owner;
        this.amount = -1;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.flash();

            addToBot(new BATwinsOneMoreAction(PoisonPower.POWER_ID, true));
        }
    }
}
