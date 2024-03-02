package baModDeveloper.ui.panels;

import baModDeveloper.effect.BATwinsRefreshEnergyEffect;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.RefreshEnergyEffect;

import baModDeveloper.character.BATwinsCharacter;

public class BATwinsEnergyPanel extends EnergyPanel {
    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack
            .getTutorialString("Energy Panel Tip");
    private static final int RAW_W = 256;
    private static final Color ENERGY_TEXT_COLOR = new Color(1.0F, 1.0F, 0.86F, 1.0F);
    private Hitbox tipHitbox_MOMOI = new Hitbox(0.0F, 0.0F, 120.0F * Settings.scale, 120.0F * Settings.scale);
    private Hitbox tipHitbox_MIDORI=new Hitbox(-128.0F,64.0F,120.0F*Settings.scale,120.0F*Settings.scale);
    private Texture gainEnergyImg;
    private Texture[] gainEnergyImgs;
    private float energyVfxAngle = 0.0F;
    private float energyVfxScale = Settings.scale;
    private Color energyVfxColor = Color.WHITE.cpy();
    private static final float VFX_ROTATE_SPEED = -30.0F;
    private static EnergyType energyVfxType=EnergyType.ALL;
    private static UIStrings orbLabel=CardCrawlGame.languagePack.getUIString(ModHelper.makePath("EnergyOrbLabel"));
    private static UIStrings orbMsg=CardCrawlGame.languagePack.getUIString(ModHelper.makePath("EnergyOrbMsg"));
    public static int MomoiCount = 0;
    public static int MidoriCount = 0;

    public static EnergyType selectedEnergySlot=EnergyType.MOMOI;
    public enum EnergyType {
        MOMOI, MIDORI, ALL,SPEIFY,SHARE
    }

    public BATwinsEnergyPanel() {
        this.img = null;
        this.show_x = 198.0F * Settings.xScale;
        this.show_y = 190.0F * Settings.yScale;
        this.hide_x = -480.0F * Settings.scale;
        this.hide_y = 200.0F * Settings.yScale;
        // if (true) {
        this.current_x = hide_x;
        this.current_y = hide_y;
        this.target_x = hide_x;
        this.target_y = hide_y;
        this.isHidden = true;
        // }
        // else {
        // this.current_x = show_x;
        // this.current_y = show_y;
        // this.target_x = show_x;
        // this.target_y = show_y;
        // this.isHidden = false;
        // }
        if(AbstractDungeon.player instanceof BATwinsCharacter){
            this.gainEnergyImgs=((BATwinsCharacter)AbstractDungeon.player).getEnergyImages();
        }
        this.gainEnergyImg = AbstractDungeon.player.getEnergyImage();
    }

    public static void setEnergy(int energy, EnergyType type) {
        switch (type) {
            case MOMOI:
                MomoiCount = energy;
                break;
            case MIDORI:
                MidoriCount = energy;
                break;
            default:
                MomoiCount = energy;
                MidoriCount = energy;
                break;
        }
        totalCount = MomoiCount + MidoriCount;
        AbstractDungeon.effectsQueue.add(new BATwinsRefreshEnergyEffect(type));
        energyVfxTimer = 2.0F;
        fontScale = 2.0F;
        energyVfxType=type;
    }
    public static void addEnergy(int e){
        addEnergy(e,EnergyType.SPEIFY);
    }
    public static void addEnergy(int e, EnergyType type) {
        if(type==EnergyType.SPEIFY){
            type=BATwinsEnergyPanel.selectedEnergySlot;
        }
        switch (type) {
            case MOMOI:
                MomoiCount += e;
                break;
            case MIDORI:
                MidoriCount += e;
                break;
            default:
                MomoiCount += e;
                MidoriCount += e;
                break;
        }
        if (MomoiCount > 999) {
            MomoiCount = 999;
        }
        if (MidoriCount > 999) {
            MidoriCount = 999;
        }
        totalCount = MomoiCount + MidoriCount;
        AbstractDungeon.effectsQueue.add(new BATwinsRefreshEnergyEffect(type));
        energyVfxTimer = 2.0F;
        fontScale = 2.0F;
        energyVfxType=type;
    }
    public static void useEnergy(int e){
        useEnergy(e,EnergyType.ALL);
    }
    public static void useEnergy(int e, EnergyType type) {
        switch (type) {
            case MOMOI:
                if(e<=MomoiCount){
                    MomoiCount -= e;
                }else {
                    e=e-MomoiCount;
                    MomoiCount=0;
                    MidoriCount-=2*e;
                }
                break;
            case MIDORI:
                if(e<=MidoriCount){
                    MidoriCount -= e;
                }else {
                    e=e-MidoriCount;
                    MidoriCount=0;
                    MomoiCount-=2*e;
                }
                break;
            case ALL:
                MomoiCount-=e;
                MidoriCount-=e;
                break;
            case SPEIFY:
                if (selectedEnergySlot==EnergyType.MOMOI){
                    if(e<=MomoiCount){
                        MomoiCount -= e;
                    }else {
                        e=e-MomoiCount;
                        MomoiCount=0;
                        MidoriCount-=2*e;
                    }
                }
                else{
                    if(e<=MidoriCount){
                        MidoriCount -= e;
                    }else {
                        e=e-MidoriCount;
                        MidoriCount=0;
                        MomoiCount-=2*e;
                    }
                }
                break;
            case SHARE:
                if (selectedEnergySlot==EnergyType.MOMOI){
                    if(e<=MomoiCount){
                        MomoiCount -= e;
                    }else {
                        e=e-MomoiCount;
                        MomoiCount=0;
                        MidoriCount-=e;
                    }
                }
                else{
                    if(e<=MidoriCount){
                        MidoriCount -= e;
                    }else {
                        e=e-MidoriCount;
                        MidoriCount=0;
                        MomoiCount-=e;
                    }
                }
                break;
        }
        if (MomoiCount < 0) {
            MomoiCount = 0;
        }
        if (MidoriCount < 0) {
            MidoriCount = 0;
        }
        totalCount = MomoiCount + MidoriCount;
        if (e != 0) {
            fontScale = 2.0F;
        }
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player instanceof BATwinsCharacter) {
            BATwinsCharacter BAplayer = (BATwinsCharacter) player;
            BAplayer.updateOrb(MomoiCount, MidoriCount);
        } else {
            player.updateOrb(totalCount);
        }
        updateVfx();
        if (fontScale != 1.0F)
            fontScale = MathHelper.scaleLerpSnap(fontScale, 1.0F);
        this.tipHitbox_MOMOI.update();
        this.tipHitbox_MIDORI.update();
        if(!Settings.USE_LONG_PRESS&&InputHelper.justClickedLeft&&this.tipHitbox_MOMOI.hovered&&!AbstractDungeon.isScreenUp){
            this.tipHitbox_MOMOI.clickStarted=true;
        }
        if(!Settings.USE_LONG_PRESS&&InputHelper.justClickedLeft&&this.tipHitbox_MIDORI.hovered&&!AbstractDungeon.isScreenUp){
            this.tipHitbox_MIDORI.clickStarted=true;
        }
        if (this.tipHitbox_MOMOI.hovered && !AbstractDungeon.isScreenUp)
            AbstractDungeon.overlayMenu.hoveredTip = true;
        if (this.tipHitbox_MIDORI.hovered && !AbstractDungeon.isScreenUp)
            AbstractDungeon.overlayMenu.hoveredTip = true;
        if(this.tipHitbox_MOMOI.clicked){
            this.tipHitbox_MOMOI.clicked=false;
            this.tipHitbox_MOMOI.clickStarted=false;
            if(BATwinsEnergyPanel.selectedEnergySlot!=EnergyType.MOMOI){
                BATwinsEnergyPanel.selectedEnergySlot=EnergyType.MOMOI;
                updateSelectedEnergyIcon();
            }
//            updateSelectedEnergyIcon();
        }else if(this.tipHitbox_MIDORI.clicked){

            this.tipHitbox_MIDORI.clicked=false;
            this.tipHitbox_MIDORI.clickStarted=false;
            if(BATwinsEnergyPanel.selectedEnergySlot!=EnergyType.MIDORI){
                BATwinsEnergyPanel.selectedEnergySlot=EnergyType.MIDORI;
                updateSelectedEnergyIcon();

            }
//            updateSelectedEnergyIcon();
        }

        if (Settings.isDebug) {
            if (InputHelper.scrolledDown) {
                addEnergy(1, EnergyType.ALL);
            } else if (InputHelper.scrolledUp && totalCount > 0) {
                useEnergy(1, EnergyType.ALL);
            }
        }
    }

    private void updateVfx() {
        if (energyVfxTimer != 0.0F) {
            this.energyVfxColor.a = Interpolation.exp10In.apply(0.5F, 0.0F, 1.0F - energyVfxTimer / 2.0F);
            this.energyVfxAngle += Gdx.graphics.getDeltaTime() * -30.0F;
            this.energyVfxScale = Settings.scale
                    * Interpolation.exp10In.apply(1.0F, 0.1F, 1.0F - energyVfxTimer / 2.0F);
            energyVfxTimer -= Gdx.graphics.getDeltaTime();
            if (energyVfxTimer < 0.0F) {
                energyVfxTimer = 0.0F;
                this.energyVfxColor.a = 0.0F;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {

        this.tipHitbox_MOMOI.move(this.current_x, this.current_y);
        this.tipHitbox_MIDORI.move(this.current_x-100.0F*Settings.scale,this.current_y+100.0F*Settings.scale);
        renderOrb(sb);
        renderVfx(sb);
        AbstractPlayer player = AbstractDungeon.player;
        if (player instanceof BATwinsCharacter) {
            BATwinsCharacter BAplayer = (BATwinsCharacter) player;
            String energyMsgMomoi = MomoiCount + "/" + BAplayer.energy.energy;
            String energyMsgMidori = MidoriCount + "/" + BAplayer.energy.energy;
            AbstractDungeon.player.getEnergyNumFont().getData().setScale(fontScale);
            FontHelper.renderFontCentered(sb, BAplayer.getEnergyNumFont(), energyMsgMomoi, current_x, current_y,
                    ENERGY_TEXT_COLOR);
            FontHelper.renderFontCentered(sb, BAplayer.getEnergyNumFont(), energyMsgMidori, current_x - 100.0F*Settings.scale,
                    current_y + 100.0F*Settings.scale, ENERGY_TEXT_COLOR);
            this.tipHitbox_MOMOI.render(sb);
            this.tipHitbox_MIDORI.render(sb);
            if (this.tipHitbox_MOMOI.hovered && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT
                    && !AbstractDungeon.isScreenUp)
                TipHelper.renderGenericTip(50.0F * Settings.scale, 380.0F * Settings.scale, orbLabel.TEXT[0], orbMsg.TEXT[0]);
            if (this.tipHitbox_MIDORI.hovered && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT
                    && !AbstractDungeon.isScreenUp)
                TipHelper.renderGenericTip(-14.0F * Settings.scale, 444.0F * Settings.scale, orbLabel.TEXT[1], orbMsg.TEXT[1]);
        }
    }

    private void renderOrb(SpriteBatch sb) {
        AbstractPlayer player = AbstractDungeon.player;
        if (player instanceof BATwinsCharacter) {
            BATwinsCharacter BAplayer = (BATwinsCharacter) player;
            if (MomoiCount == 0) {
                BAplayer.renderOrb(sb, false, current_x, current_y);
            } else {
                BAplayer.renderOrb(sb, true, current_x, current_y);
            }
        }
    }

    private void renderVfx(SpriteBatch sb) {
        if (energyVfxTimer != 0.0F) {
            sb.setBlendFunction(770, 1);
            sb.setColor(this.energyVfxColor);
            if(energyVfxType==EnergyType.MOMOI||energyVfxType==EnergyType.ALL)
                sb.draw(this.gainEnergyImgs[0], this.current_x - 128.0F, this.current_y - 128.0F, 128.0F, 128.0F, 256.0F,
                    256.0F, this.energyVfxScale, this.energyVfxScale, -this.energyVfxAngle + 50.0F, 0, 0, 256, 256,
                    true, false);
            if(energyVfxType==EnergyType.MIDORI||energyVfxType==EnergyType.ALL)
                sb.draw(this.gainEnergyImgs[1], this.current_x -128.0F-100.0F*Settings.scale, this.current_y -128.0F+100.0F*Settings.scale, 128.0F, 128.0F, 256.0F,
                    256.0F, this.energyVfxScale, this.energyVfxScale, this.energyVfxAngle, 0, 0, 256, 256, false,
                    false);
            sb.setBlendFunction(770, 771);
        }
    }

    public static int getCurrentEnergy() {
        if (AbstractDungeon.player == null)
            return 0;
        return totalCount;
    }

    public static int getMomoiCount() {
        if (AbstractDungeon.player == null)
            return 0;
        return MomoiCount;
    }

    public static int getMidoriCount() {
        if (AbstractDungeon.player == null)
            return 0;
        return MidoriCount;
    }

    public static EnergyType getOtherEnergyType(EnergyType type){
        if(type==EnergyType.MOMOI){
            return EnergyType.MIDORI;
        } else if (type==EnergyType.MIDORI) {
            return EnergyType.MOMOI;
        }
        return type;
    }

    private void updateSelectedEnergyIcon(){
        if(AbstractDungeon.player!=null){
            AbstractDungeon.player.relics.forEach(abstractRelic -> abstractRelic.updateDescription(AbstractDungeon.player.chosenClass));
        }
    }
}
