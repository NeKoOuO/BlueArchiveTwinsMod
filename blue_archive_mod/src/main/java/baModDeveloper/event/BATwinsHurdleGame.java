package baModDeveloper.event;

import baModDeveloper.BATwinsMod;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.Character3DHelper;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class BATwinsHurdleGame extends AbstractImageEvent {
    public static final String ID = ModHelper.makePath("HurdleGame");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String imgUrl = ModHelper.makeImgPath("event", "HurdleGame");

    private CurrentScreen currentScreen = CurrentScreen.MAIN;

    private Color color;
    private int gameState = 0;

    private ArrayList<Float> ObstacleLocation = new ArrayList<>();

    private Texture obstacleImg;
    private Texture pressButton;
    private Texture backGround;
    private Hitbox buttonHb;
    private float imgX;
    private float imgY;

    private boolean canPress = true;
    private float waiting = 0.0F;

    private float speed = 150.0F;

    private int nextPos = 0;
    private int sorce = 0;
    private int gold = 50;

    private float startingPoint = Settings.WIDTH * 0.2F;

    private Character3DHelper character3DHelper;

    private enum CurrentScreen {MAIN, GAMING, DONE, LEAVE, LOOK}

    public BATwinsHurdleGame() {
        super(title, DESCRIPTIONS[0], imgUrl);
        if (AbstractDungeon.player instanceof BATwinsCharacter && BATwinsMod.Enable3D) {
            this.imageEventText.setDialogOption(OPTIONS[0]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[1], true);
        }
        this.imageEventText.setDialogOption(OPTIONS[4]);
        this.imageEventText.setDialogOption(OPTIONS[2]);
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        for (int i = 0; i < 10; i++) {
            ObstacleLocation.add(Settings.WIDTH + (float) (i * 450) * Settings.scale);
        }

        obstacleImg = ImageMaster.loadImage(ModHelper.makeImgPath("UI", "Hurdle"));
        pressButton = ImageMaster.loadImage(ModHelper.makeImgPath("UI", "PressButtton_A"));
        backGround = ImageMaster.loadImage(ModHelper.makeImgPath("UI", "Playground"));
        this.buttonHb = new Hitbox(pressButton.getWidth(), pressButton.getHeight());
        this.buttonHb.move(Settings.WIDTH / 2.0F, Settings.HEIGHT / 5.0F);
        this.imgX = 0;
        this.imgY = Settings.HEIGHT / 3.0F;
        if (BATwinsMod.Enable3D) {
            this.character3DHelper = BATwinsCharacter.get3DHelper();
            this.character3DHelper.resetModelPosition(-1000.0F, -1000.0F, BATwinsCharacter.Enums.BATWINS_MIDORI_CARD);
        }

        this.noCardsInRewards = true;

    }

    @Override
    protected void buttonEffect(int i) {
        switch (this.currentScreen) {
            case MAIN:
                if (i == 0 && BATwinsMod.Enable3D) {
                    this.currentScreen = CurrentScreen.GAMING;
                    this.imageEventText.removeDialogOption(0);
                    GenericEventDialog.hide();
                    this.color.a = Interpolation.fade.apply(0.0F, 1.0F, 1.0F);
                } else if (i == 2) {
                    this.currentScreen = CurrentScreen.LEAVE;
                    this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(OPTIONS[3]);
                } else if (i == 1) {
                    this.currentScreen = CurrentScreen.LOOK;
                    this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(OPTIONS[3]);
                    getReward();
                }
                break;
            case GAMING:
                break;
            case DONE:
            case LEAVE:
            case LOOK:
                if (BATwinsMod.Enable3D) {
                    this.character3DHelper.setStandAnima(Character3DHelper.AnimationName.NORMAL_IDLE, BATwinsCharacter.Enums.BATWINS_MOMOI_CARD);
                    this.character3DHelper.resetModelPosition(-150 * Settings.scale, 0, BATwinsCharacter.Enums.BATWINS_MIDORI_CARD);
                }


                openMap();
                break;
        }
    }

    @Override
    public void update() {
        switch (currentScreen) {
            case MAIN:
                super.update();
                break;
            case GAMING:
                this.buttonHb.update();
                if (this.gameState == 1 && this.canPress && this.buttonHb.hovered && InputHelper.justClickedLeft || CInputActionSet.proceed.isJustPressed()) {
                    character3DHelper.setMomoiAnimation(Character3DHelper.MomoiActionList.JUMP);
                    this.canPress = false;
                    this.waiting = 1.5F;
                }
                this.waiting = Math.max(waiting - Gdx.graphics.getDeltaTime(), 0.0F);
                if (this.waiting == 0.0F) {
                    this.canPress = true;
                }
                if (this.gameState == 0 && this.buttonHb.hovered && InputHelper.justClickedLeft || CInputActionSet.proceed.isJustPressed()) {
                    this.gameState = 1;
                    this.character3DHelper.setStandAnima(Character3DHelper.AnimationName.MOVING, BATwinsCharacter.Enums.BATWINS_MOMOI_CARD);
                }
                if (this.gameState == 1)
                    this.ObstacleLocation.replaceAll(aFloat -> aFloat - Gdx.graphics.getDeltaTime() * this.speed);


                if (this.nextPos >= this.ObstacleLocation.size()) {
                    this.gameState = 3;
                    this.canPress = false;
                    this.waiting = 100.0F;
                } else if (this.gameState == 1 && this.ObstacleLocation.get(this.nextPos) <= startingPoint) {
                    if (!this.character3DHelper.getCurrentAnima(BATwinsCharacter.Enums.BATWINS_MOMOI_CARD).equals(Character3DHelper.getAnimationString(Character3DHelper.AnimationName.MOVE_JUMP, BATwinsCharacter.Enums.BATWINS_MOMOI_CARD))) {
                        this.gameState = 2;
                        this.character3DHelper.setMomoiAnimation(Character3DHelper.MomoiActionList.DEATH);
                        this.canPress = false;
                        this.waiting = 100.0F;
                    } else {
                        this.sorce++;
                    }
                    this.nextPos++;
                }
                if (this.gameState == 2 && !this.character3DHelper.getCurrentAnima(BATwinsCharacter.Enums.BATWINS_MOMOI_CARD).equals(Character3DHelper.getAnimationString(Character3DHelper.AnimationName.DEATH, BATwinsCharacter.Enums.BATWINS_MOMOI_CARD))) {
                    this.currentScreen = CurrentScreen.DONE;
                    GenericEventDialog.show();
                    this.imageEventText.clearAllDialogs();
                    if(this.sorce==0){
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                    }else{
                        this.imageEventText.updateBodyText(String.format(DESCRIPTIONS[4],this.sorce));
                    }
                    this.imageEventText.setDialogOption(OPTIONS[3]);
                    getRewardWithGaming();
                }
                if (this.gameState == 3 && !this.character3DHelper.getCurrentAnima(BATwinsCharacter.Enums.BATWINS_MOMOI_CARD).equals(Character3DHelper.getAnimationString(Character3DHelper.AnimationName.MOVE_JUMP, BATwinsCharacter.Enums.BATWINS_MOMOI_CARD))) {
                    this.currentScreen = CurrentScreen.DONE;
                    GenericEventDialog.show();
                    this.imageEventText.clearAllDialogs();
                    if (this.sorce == 10) {
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                    } else if(this.sorce==0){
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                    }else{
                        this.imageEventText.updateBodyText(String.format(DESCRIPTIONS[4],this.sorce));
                    }
                    this.imageEventText.setDialogOption(OPTIONS[3]);
                    getRewardWithGaming();
                }
//                this.character3DHelper.resetModelPosition();
                break;
            case DONE:
            case LEAVE:
            case LOOK:
                this.color.a = Interpolation.fade.apply(1.0F, 0.0F, 1.0F);
                super.update();
                break;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (this.currentScreen != CurrentScreen.GAMING) {
            return;
        }
        sb.setColor(this.color);

        sb.draw(this.backGround, imgX, Settings.HEIGHT * 0.25F, Settings.WIDTH, this.backGround.getHeight() * Settings.scale);
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontBlue, this.sorce + "/" + this.ObstacleLocation.size(), Settings.WIDTH / 2.0F, Settings.HEIGHT * 0.7F, Color.WHITE);
        for (Float aFloat : this.ObstacleLocation) {
            sb.draw(obstacleImg, this.imgX + aFloat, this.imgY, this.obstacleImg.getWidth() * Settings.scale * 1.2F, this.obstacleImg.getHeight() * Settings.scale * 1.2F);
        }
        this.character3DHelper.render(sb,false);

//        sb.draw(this.pressButton,Settings.WIDTH/2.0F,Settings.HEIGHT/5.0F);

        sb.setBlendFunction(770, 1);
        if (!this.canPress) {
            sb.setColor(0.0F, 1.0F, 1.0F, 0.1F);
        } else if (this.buttonHb.hovered) {
            sb.setColor(1.0F, 1.0F, 1.0F, 0.25F);
        } else {
            sb.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
        if (this.buttonHb.hovered) {
            sb.draw(this.pressButton, Settings.WIDTH / 2.0F - this.pressButton.getWidth() / 2.0F, Settings.HEIGHT / 5.0F - this.pressButton.getHeight() / 2.0F);
        } else {
            sb.draw(this.pressButton, Settings.WIDTH / 2.0F - this.pressButton.getWidth() / 2.0F, Settings.HEIGHT / 5.0F - this.pressButton.getHeight() / 2.0F);
        }
        sb.setBlendFunction(770, 771);
        this.buttonHb.render(sb);

    }

    private void getReward() {
        int rng = AbstractDungeon.miscRng.random(0, 100);
        AbstractDungeon.getCurrRoom().rewards.clear();
        if (rng >= 0 && rng < 50) {
            AbstractDungeon.getCurrRoom().addGoldToRewards(gold);
        } else if (rng >= 50 && rng < 80) {
            AbstractDungeon.getCurrRoom().addPotionToRewards();
            AbstractDungeon.getCurrRoom().addPotionToRewards();
        } else {
            AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);
        }
        AbstractDungeon.combatRewardScreen.open();
    }

    private void getRewardWithGaming() {
        AbstractDungeon.getCurrRoom().rewards.clear();
        if (this.sorce == 0) {
            return;
        } else if (this.sorce > 0 && this.sorce < 5) {
            AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);
        } else if (this.sorce >= 5 && this.sorce < 10) {
            AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.UNCOMMON);
        } else if (this.sorce == 10) {
            AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.RARE);
        }
        AbstractDungeon.combatRewardScreen.open();
    }
}
