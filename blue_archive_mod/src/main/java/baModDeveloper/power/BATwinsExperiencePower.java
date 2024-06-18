package baModDeveloper.power;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.BATwinsLevelUpInterface;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsExperiencePanel;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BATwinsExperiencePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("ExperiencePower");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_84 = ModHelper.makeImgPath("power", "Experience84");
    private static final String IMG_32 = ModHelper.makeImgPath("power", "Experience32");
    public static int MAX = 10;
    public int LEVEL = 0;

    public BATwinsExperiencePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.type = TYPE;
        this.name = NAME;
        this.owner = owner;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32), 0, 0, 32, 32);
        this.amount = amount;
//        MAX=((LEVEL/5)+1)*10;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + LEVEL + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + MAX + DESCRIPTIONS[3];
    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        while (true) {
            if (this.amount >= MAX) {
                this.levelup(1, false);
            } else {
                break;
            }
        }
        this.updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage += LEVEL;
        }
        return damage;
    }

    public float modifyBlock(float blockAmount) {
        if ((blockAmount += LEVEL) < 0.0F) {
            return 0.0F;
        }
        return blockAmount;
    }

    public void levelup(int amount, boolean clearExp) {
        this.flash();
        this.LEVEL += amount;
        if (clearExp) {
            this.amount = 0;
        } else {
            this.amount = this.amount - MAX;
        }
        addToTop(new TextAboveCreatureAction(this.owner, DESCRIPTIONS[4]));

        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof BATwinsLevelUpInterface) {
                ((BATwinsLevelUpInterface) r).triggerOnLevelUp();
            }
        }
        updateDescription();
        if (AbstractDungeon.player instanceof BATwinsCharacter) {
            BATwinsExperiencePanel.LevelUp();
        }
    }
}
