package baModDeveloper.potion;

import baModDeveloper.effect.BATwinsCampfireReopenEffect;
import baModDeveloper.helpers.ModHelper;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepScreenCoverEffect;

public class BATwinsStaminaPotion extends AbstractPotion {
    public static final String ID= ModHelper.makePath("StaminaPotion");
    private static final PotionStrings potionStrings= CardCrawlGame.languagePack.getPotionString(ID);
    private static final PotionRarity rarity=PotionRarity.RARE;
    private static final PotionSize size=PotionSize.M;
    private static final PotionColor color=PotionColor.BLUE;
    public static Color liquidColor=new Color(255.0F / 255.0F, 1.0F / 255.0F, 19.0F / 255.0F, 1.0F);
    public static Color hybridColor=new Color(253.0F / 255.0F, 2.0F / 255.0F, 22.0F / 255.0F, 1.0F);
    public static Color spotsColor=new Color(254.0F / 255.0F, 1.0F / 255.0F, 19.0F / 255.0F, 1.0F);
    private static final String imgUrl=ModHelper.makeImgPath("potion","StaminaPotion");
    private static final Texture img= ImageMaster.loadImage(imgUrl);
    public BATwinsStaminaPotion() {
        super(potionStrings.NAME, ID, rarity, size, color);
        this.isThrown=false;
        this.targetRequired=false;
        this.canUse=false;

    }

    @Override
    public void initializeData() {
        this.potency=getPotency();
        this.tips.clear();

        this.description=potionStrings.DESCRIPTIONS[0];
        this.tips.add(new PowerTip(this.name,this.description));

    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if(AbstractDungeon.getCurrRoom() instanceof RestRoom){
            AbstractDungeon.effectList.add(new BATwinsCampfireReopenEffect());
            for(int i=0;i<30;i++){
                AbstractDungeon.topLevelEffects.add(new CampfireSleepScreenCoverEffect());
            }

//            ((RestRoom)AbstractDungeon.getCurrRoom()).campfireUI.reopen();
        }
    }

    @Override
    public int getPotency(int i) {
        return 0;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BATwinsStaminaPotion();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1.0F,1.0F,1.0F,1.0F);
        sb.draw(img,this.posX-32.0F,this.posY-32.0F,32.0F,32.0F,64.0F,64.0F,this.scale,this.scale,0.0F,0,0,64,64,false,false);
    }

    @Override
    public void renderOutline(SpriteBatch sb, Color c) {

    }

    @Override
    public void renderOutline(SpriteBatch sb) {

    }

    @Override
    public void renderLightOutline(SpriteBatch sb) {

    }

    @Override
    public void renderShiny(SpriteBatch sb) {

    }

    @Override
    public boolean canUse() {
        return AbstractDungeon.getCurrRoom() instanceof RestRoom&&AbstractDungeon.getCurrRoom().phase== AbstractRoom.RoomPhase.COMPLETE;
    }

    @Override
    public void labRender(SpriteBatch sb) {
        this.render(sb);
        if (this.hb.hovered) {
            TipHelper.queuePowerTips(150.0F * Settings.scale, 800.0F * Settings.scale, this.tips);
            this.scale = 1.5F * Settings.scale;
        } else {
            this.scale = MathHelper.scaleLerpSnap(this.scale, 1.2F * Settings.scale);
        }
        if (this.hb != null) {
            this.hb.render(sb);
        }
    }

    @Override
    public void shopRender(SpriteBatch sb) {
//        super.shopRender(sb);
        this.generateSparkles(0.0F, 0.0F, false);
        if (this.hb.hovered) {
            TipHelper.queuePowerTips((float) InputHelper.mX + 50.0F * Settings.scale, (float)InputHelper.mY + 50.0F * Settings.scale, this.tips);
            this.scale = 1.5F * Settings.scale;
        } else {
            this.scale = MathHelper.scaleLerpSnap(this.scale, 1.2F * Settings.scale);
        }
        this.render(sb);
        if (this.hb != null) {
            this.hb.render(sb);
        }

    }
}
