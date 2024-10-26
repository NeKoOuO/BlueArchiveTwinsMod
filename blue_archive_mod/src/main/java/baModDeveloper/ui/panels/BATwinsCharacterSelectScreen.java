package baModDeveloper.ui.panels;

import baModDeveloper.BATwinsMod;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.SkeletonRendererDebug;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.io.IOException;
import java.util.ArrayList;

public class BATwinsCharacterSelectScreen implements ISubscriber {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("ModeSelectionPrompt"));
    private static final UIStrings skinSelector = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("Skins"));
    public static BATwinsCharacterSelectScreen instance;
    private Hitbox checkbox;
    private boolean checked;
    private boolean hovered;
    private boolean shown;
    private float current_x, current_y;
    private SpireConfig spireConfig;

    private Hitbox leftArrow;
    private Hitbox rightArrow;

    private static final UIStrings backgroundSelector = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("Backgrounds"));

    private Hitbox bg_leftArrow;
    private Hitbox bg_rightArrow;
    private float bgSelectorOffset = 50.0F * Settings.yScale;
    private static final ArrayList<String> bgList = new ArrayList<>();

    static {
        String floder = "char/selectscreen";
        bgList.add("");
        bgList.add(ModHelper.makeFilePath(floder+"/momoi_normal", "Momoi_home", ""));
        bgList.add(ModHelper.makeFilePath(floder+"/midori_normal","Midori_home",""));
        bgList.add(ModHelper.makeFilePath(floder+"/momoi_maid","CH0201_home",""));
        bgList.add(ModHelper.makeFilePath(floder+"/midori_maid","CH0202_home",""));
//        bgList.add(ModHelper.makeFilePath(floder))
    }

    private TextureAtlas atlas;
    private com.esotericsoftware.spine38.SkeletonJson json;
    private com.esotericsoftware.spine38.SkeletonData data;
    private com.esotericsoftware.spine38.AnimationStateData stateData;
    private com.esotericsoftware.spine38.Skeleton skeleton;
    private com.esotericsoftware.spine38.AnimationState state;
    private SkeletonRendererDebug debug;
    private com.esotericsoftware.spine38.SkeletonRenderer renderer;

    public BATwinsCharacterSelectScreen() throws IOException {
        this.checkbox = new Hitbox(300.0F * Settings.scale, 50.0F * Settings.scale);
        this.checked = BATwinsMod.Enable3D;
        this.shown = false;
        this.hovered = false;
        this.current_x = Settings.WIDTH / 6.0F;
        this.current_y = Settings.HEIGHT / 3.0F + 50.0F * Settings.yScale;
        this.checkbox.move(this.current_x, this.current_y);

        spireConfig = new SpireConfig("BATwinsMod", "Common");

        initSkinSelector();

        initBgSelector();

        debug=new SkeletonRendererDebug();
        renderer=new com.esotericsoftware.spine38.SkeletonRenderer();
        renderer.setPremultipliedAlpha(true);
    }

    private void initBgSelector() {
        this.bg_leftArrow = new Hitbox(50 * Settings.scale, 50 * Settings.scale);
        this.bg_rightArrow = new Hitbox(50 * Settings.scale, 50 * Settings.scale);
        this.bg_leftArrow.move(this.current_x + 200 * Settings.scale, this.current_y - this.bgSelectorOffset);
        this.bg_rightArrow.move(this.current_x + 400 * Settings.scale, this.current_y - this.bgSelectorOffset);
    }

    public static BATwinsCharacterSelectScreen getInstance() {
        if (instance == null) {
            try {
                instance = new BATwinsCharacterSelectScreen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    private void initSkinSelector() {
        this.leftArrow = new Hitbox(50 * Settings.scale, 50 * Settings.scale);
        this.rightArrow = new Hitbox(50 * Settings.scale, 50 * Settings.scale);
        this.leftArrow.move(this.current_x + 200 * Settings.scale, this.current_y);
        this.rightArrow.move(this.current_x + 400 * Settings.scale, this.current_y);
    }

    public void update() {
        if (CardCrawlGame.chosenCharacter == BATwinsCharacter.Enums.BATwins) {
            this.shown = true;
            this.updateSkeleton();
            this.checkbox.update();
            this.leftArrow.update();
            this.rightArrow.update();
            this.bg_leftArrow.update();
            this.bg_rightArrow.update();
            if (InputHelper.justClickedLeft) {
                if (this.leftArrow.hovered) {
                    this.leftArrow.clickStarted = true;
                } else if (this.rightArrow.hovered) {
                    this.rightArrow.clickStarted = true;
                }else if(this.bg_leftArrow.hovered){
                    this.bg_leftArrow.clickStarted=true;
                }else if(this.bg_rightArrow.hovered){
                    this.bg_rightArrow.clickStarted=true;
                }
            }

            if (this.checkbox.clicked) {
                this.checkbox.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.checked = !checked;
                BATwinsMod.Enable3D = this.checked;
                spireConfig.setBool(ModHelper.makePath("Enable3D"), BATwinsMod.Enable3D);
                SaveConfig();

            }
            if (this.leftArrow.clicked || this.rightArrow.clicked) {
                int add = this.leftArrow.clicked ? -1 : 1;
                this.leftArrow.clicked = false;
                this.rightArrow.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                BATwinsMod.SelectedSkin = Math.abs((BATwinsMod.SelectedSkin + add) % skinSelector.TEXT.length);
                spireConfig.setInt(ModHelper.makePath("SelectedSkin"), BATwinsMod.SelectedSkin);
                SaveConfig();
            }
            if (this.bg_leftArrow.clicked || this.bg_rightArrow.clicked) {
                int add = this.bg_leftArrow.clicked ? -1 : 1;
                this.bg_leftArrow.clicked = false;
                this.bg_rightArrow.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                BATwinsMod.SelectedBg = (BATwinsMod.SelectedBg + add) % backgroundSelector.TEXT.length;
                if(BATwinsMod.SelectedBg<0){
                    BATwinsMod.SelectedBg+=backgroundSelector.TEXT.length;
                }
                spireConfig.setInt(ModHelper.makePath("SelectedBg"), BATwinsMod.SelectedBg);
                this.ChangeBg();
                SaveConfig();
            }

            if (InputHelper.justClickedLeft) {
                if (this.checkbox.hovered) {
                    this.checkbox.clickStarted = true;
                }
            }
            this.hovered = this.checkbox.hovered;
        } else {
            this.shown = false;
        }
    }

    private void updateSkeleton() {
        if (BATwinsMod.SelectedBg != 0 && this.skeleton != null) {
            this.state.update(Gdx.graphics.getDeltaTime());
            this.state.apply(this.skeleton);
            this.skeleton.updateWorldTransform();
            this.skeleton.setPosition(Settings.WIDTH / 2.0F, 0);
        }

    }

    private void ChangeBg() {
        if (BATwinsMod.SelectedBg != 0) {
            if (BATwinsMod.SelectedBg < bgList.size()) {
                String path = bgList.get(BATwinsMod.SelectedBg);
                if (this.atlas != null) {
                    this.atlas.dispose();
                }
                this.atlas = new TextureAtlas(path + "atlas");
                this.json = new com.esotericsoftware.spine38.SkeletonJson(this.atlas);
                this.json.setScale(Settings.renderScale*0.58F);
                this.data = json.readSkeletonData(Gdx.files.internal(path + "json"));
                this.skeleton = new com.esotericsoftware.spine38.Skeleton(data);
                this.skeleton.setColor(Color.WHITE.cpy());
                this.stateData = new com.esotericsoftware.spine38.AnimationStateData(data);
                this.state = new com.esotericsoftware.spine38.AnimationState(this.stateData);
                this.state.addAnimation(0, "Start_Idle_01", false, 0);
                this.state.addAnimation(0, "Idle_01", true, 0);
            } else {
                BATwinsMod.SelectedBg = 0;
            }
        }
    }

    private void SaveConfig() {
        try {
            spireConfig.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void render(SpriteBatch sb) {
        if (this.shown) {
            this.renderBg(sb);

            sb.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            sb.draw(ImageMaster.OPTION_TOGGLE, this.current_x - 150.0F * Settings.scale, this.current_y - ImageMaster.OPTION_TOGGLE.getHeight() / 2.0F);
            if (this.checked) {
                sb.draw(ImageMaster.OPTION_TOGGLE_ON, this.current_x - 150.0F * Settings.scale, this.current_y - ImageMaster.OPTION_TOGGLE.getHeight() / 2.0F);
            }
            FontHelper.renderFontCentered(sb, FontHelper.menuBannerFont, uiStrings.TEXT[0], this.current_x + 30 * Settings.scale, this.current_y);
            FontHelper.renderFontCentered(sb, FontHelper.menuBannerFont, uiStrings.TEXT[3], this.current_x + 30 * Settings.scale, this.current_y - this.bgSelectorOffset);
            if (this.hovered) {
                this.renderTip(sb);
            }
            this.checkbox.render(sb);

            if (BATwinsMod.Enable3D) {
                renderSkinSelector(sb);
            }

            this.renderBgSelector(sb);
        }
    }

    private void renderBg(SpriteBatch sb) {
        if (BATwinsMod.SelectedBg != 0 && this.skeleton != null) {
            sb.end();
            CardCrawlGame.psb.begin();
//            AbstractCreature.sr.draw(CardCrawlGame.psb, this.skeleton);

            renderer.draw(CardCrawlGame.psb,this.skeleton);
            CardCrawlGame.psb.end();
//            debug.draw(skeleton);
            sb.begin();
        }
    }

    private void renderBgSelector(SpriteBatch sb) {
        if (!this.bg_leftArrow.hovered && !Settings.isControllerMode) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, this.bg_leftArrow.cX - 24.0F, this.bg_leftArrow.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        if (!this.bg_rightArrow.hovered && !Settings.isControllerMode) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, this.bg_rightArrow.cX - 24.0F, this.bg_rightArrow.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, true, false);
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, backgroundSelector.TEXT[BATwinsMod.SelectedBg], this.current_x + 300 * Settings.scale, this.current_y - this.bgSelectorOffset);
        this.bg_leftArrow.render(sb);
        this.bg_rightArrow.render(sb);
    }

    private void renderSkinSelector(SpriteBatch sb) {
        if (!this.leftArrow.hovered && !Settings.isControllerMode) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, this.leftArrow.cX - 24.0F, this.leftArrow.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        if (!this.rightArrow.hovered && !Settings.isControllerMode) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, this.rightArrow.cX - 24.0F, this.rightArrow.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, true, false);
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, skinSelector.TEXT[BATwinsMod.SelectedSkin], this.current_x + 300 * Settings.scale, this.current_y);
        this.leftArrow.render(sb);
        this.rightArrow.render(sb);

    }

    private void renderTip(SpriteBatch sb) {
        TipHelper.renderGenericTip(this.current_x, this.current_y, uiStrings.TEXT[1], uiStrings.TEXT[2]);
    }
}
