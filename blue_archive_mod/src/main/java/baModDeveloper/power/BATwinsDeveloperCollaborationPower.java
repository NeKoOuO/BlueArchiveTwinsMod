package baModDeveloper.power;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BATwinsDeveloperCollaborationPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("DeveloperCollaborationPower");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_84 = ModHelper.makeImgPath("power", "DeveloperCollaboration84");
    private static final String IMG_32 = ModHelper.makeImgPath("power", "DeveloperCollaboration32");
    private static final String IMG_MOMOI_84 = ModHelper.makeImgPath("power", "DeveloperCollaboration_momoi84");
    private static final String IMG_MOMOI_32 = ModHelper.makeImgPath("power", "DeveloperCollaboration_momoi32");
    private static final String IMG_MIDORI_84 = ModHelper.makeImgPath("power", "DeveloperCollaboration_midori84");
    private static final String IMG_MIDORI_32 = ModHelper.makeImgPath("power", "DeveloperCollaboration_midori32");
    private final TextureAtlas.AtlasRegion momoi128, momoi48, midori128, midori48;
    private AbstractCard.CardColor lastColor;

    private int count;

    public BATwinsDeveloperCollaborationPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = TYPE;
        this.owner = owner;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32), 0, 0, 32, 32);
        this.momoi128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_MOMOI_84), 0, 0, 84, 84);
        this.momoi48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_MOMOI_32), 0, 0, 32, 32);
        this.midori128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_MIDORI_84), 0, 0, 84, 84);
        this.midori48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_MIDORI_32), 0, 0, 32, 32);

        this.count = 0;
        this.updateDescription();

    }

    @Override
    public void updateDescription() {
        String lastcard = "";
        if (this.lastColor == null) {
            lastcard = DESCRIPTIONS[9];
        } else if (this.lastColor == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD) {
            lastcard = DESCRIPTIONS[4];
        } else if (this.lastColor == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
            lastcard = DESCRIPTIONS[5];
        } else if (this.lastColor == AbstractCard.CardColor.COLORLESS) {
            lastcard = DESCRIPTIONS[6];
        } else if (this.lastColor == AbstractCard.CardColor.CURSE) {
            lastcard = DESCRIPTIONS[7];
        } else {
            lastcard = DESCRIPTIONS[8];
        }
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + lastcard + DESCRIPTIONS[3] + DESCRIPTIONS[10] + Integer.toString(this.count * this.amount) + DESCRIPTIONS[1];
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if (this.lastColor != null) {
            if (this.lastColor == BATwinsCharacter.getOtherColor(usedCard.color)) {
                this.flash();
                this.count++;
            } else {
                this.flash();
                this.count = 0;
            }
        }
        this.lastColor = usedCard.color;
//        else if(AbstractDungeon.actionManager.cardsPlayedThisTurn.size()>=2){
//            if((this.lastColor== BATwinsCharacter.Enums.BATWINS_MOMOI_CARD&&usedCard.color==BATwinsCharacter.Enums.BATWINS_MIDORI_CARD)||(this.lastColor==BATwinsCharacter.Enums.BATWINS_MIDORI_CARD&&usedCard.color==BATwinsCharacter.Enums.BATWINS_MOMOI_CARD)){
//                addToBot(new DrawCardAction(this.amount));
//                this.flash();
//                this.lastColor=usedCard.color;
//            }
//        }
        addToTop(new TextAboveCreatureAction(this.owner, this.name+":"+this.amount*this.count+"%"));
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
//        this.lastColor = null;
        this.count = 0;
        addToTop(new TextAboveCreatureAction(this.owner, this.name+":"+ 0 +"%"));
        this.updateDescription();
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, c);
        if (this.img != null) {
            return;
        } else {
            sb.setColor(c);
            if (Settings.isMobile) {
                sb.draw(this.getIconImage(false), x - (float) this.region48.packedWidth / 2.0F, y - (float) this.region48.packedHeight / 2.0F, (float) this.region48.packedWidth / 2.0F, (float) this.region48.packedHeight / 2.0F, (float) this.region48.packedWidth, (float) this.region48.packedHeight, Settings.scale * 1.17F, Settings.scale * 1.17F, 0.0F);
            } else {
                sb.draw(this.getIconImage(false), x - (float) this.region48.packedWidth / 2.0F, y - (float) this.region48.packedHeight / 2.0F, (float) this.region48.packedWidth / 2.0F, (float) this.region48.packedHeight / 2.0F, (float) this.region48.packedWidth, (float) this.region48.packedHeight, Settings.scale, Settings.scale, 0.0F);
            }
        }

    }

    private TextureAtlas.AtlasRegion getIconImage(boolean bigImage) {
        if (this.lastColor == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD) {
            if (bigImage) {
                return this.momoi128;
            } else {
                return this.momoi48;
            }
        } else if (this.lastColor == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
            if (bigImage) {
                return this.midori128;
            } else {
                return this.midori48;
            }
        }
        if (bigImage) {
            return this.region128;
        } else {
            return this.region48;
        }
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * (1.0F + (float) this.count * this.amount / 100);
        }
        return damage;
    }

    @Override
    public float modifyBlockLast(float blockAmount) {
        return blockAmount * (1.0F + (float) this.count * this.amount / 100);
    }
}
