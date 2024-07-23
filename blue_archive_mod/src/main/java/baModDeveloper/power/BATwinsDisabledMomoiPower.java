package baModDeveloper.power;

import baModDeveloper.BATwinsMod;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.Character3DHelper;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BATwinsDisabledMomoiPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("DisabledMomoiPower");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_84 = ModHelper.makeImgPath("power", "DisabledMomoi84");
    private static final String IMG_32 = ModHelper.makeImgPath("power", "DisabledMomoi32");
    private static TextureAtlas.AtlasRegion REGION128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84), 0, 0, 84, 84);
    private static TextureAtlas.AtlasRegion REGION48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32), 0, 0, 32, 32);

    public BATwinsDisabledMomoiPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = TYPE;
        this.owner = owner;
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
    public boolean canPlayCard(AbstractCard card) {
        return card.color != BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        if (AbstractDungeon.player instanceof BATwinsCharacter && BATwinsMod.Enable3D) {
            ((BATwinsCharacter) AbstractDungeon.player).get3DHelper().setMomoiAnimation(Character3DHelper.MomoiActionList.PANIC);
        }
    }

    @Override
    public void onRemove() {
        super.onRemove();
        if (AbstractDungeon.player instanceof BATwinsCharacter && BATwinsMod.Enable3D) {
            ((BATwinsCharacter) AbstractDungeon.player).get3DHelper().clearMomoiAnima();
        }
    }
}
