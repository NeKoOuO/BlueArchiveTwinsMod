package baModDeveloper.event;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import baModDeveloper.relic.BATwinsPackage;
import baModDeveloper.ui.BATwinsSoraShopItem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.cards.curses.Shame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Random;

public class BATwinsSoraShop extends AbstractImageEvent {
    public static final String ID = ModHelper.makePath("SoraShop");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String imgUrl = ModHelper.makeImgPath("event", "SoraShop");
    private static Texture DIALOG = TextureLoader.getTexture(ModHelper.makeImgPath("UI/soraShop", "dialog"));

    private static Texture shopBackground = TextureLoader.getTexture(ModHelper.makeImgPath("UI/soraShop", "shopBackGround"));

    private static UIStrings soraMessage=CardCrawlGame.languagePack.getUIString(ModHelper.makePath("SoraMessage"));
    private enum CurrentScreen {START, SHOPPING,SHOPEND, END}

    private static int lostHp=7;

    private CurrentScreen currentScreen = CurrentScreen.START;

    private TextureAtlas atlas;
    private SkeletonJson json;
    private SkeletonData data;
    private AnimationStateData stateData;
    private Skeleton skeleton;
    private AnimationState state;
    private ArrayList<BATwinsSoraShopItem> items;
    private Vector2 eyePosition;

    private float dialogDuration = 0;
    private float dialogFadeDuration = 0;
    private boolean showDialog = false;
    private String dialogMsg = "";
    private Color dialogColor = Color.WHITE.cpy();

    private Random random=new Random();
    private Hitbox soraHb;
    private Hitbox leaveButtonHb;

    public BATwinsSoraShop() {
        super(title, DESCRIPTIONS[0], imgUrl);

        this.imageEventText.setDialogOption(OPTIONS[0]);

        this.initAnimation();
        this.items = new ArrayList<>();
        int index = 0;
        addShopItem(index, BATwinsSoraShopItem.ShopItem.LIFE);
        index++;
        addShopItem(index, BATwinsSoraShopItem.ShopItem.SMITH);
        index++;
        addShopItem(index, BATwinsSoraShopItem.ShopItem.CRYSTALHANIWA);
        index++;
        if(!Settings.hasRubyKey||!Settings.hasEmeraldKey||!Settings.hasSapphireKey){
            addShopItem(index, BATwinsSoraShopItem.ShopItem.KEY);
            index++;
        }
        addShopItem(index, BATwinsSoraShopItem.ShopItem.CHALLENGE);
        index++;
        addShopItem(index, BATwinsSoraShopItem.ShopItem.INITIALRELIC);
        index++;
        addShopItem(index, BATwinsSoraShopItem.ShopItem.POTIONSLOT);
        index++;
        addShopItem(index, BATwinsSoraShopItem.ShopItem.TELEPHONE);
        index++;
        while (index < 8) {
            addShopItem(index, BATwinsSoraShopItem.ShopItem.LIFE);
            index++;
        }

        this.eyePosition = new Vector2(622.0F * Settings.scale, 700.0F * Settings.yScale);

        this.soraHb=new Hitbox(Settings.WIDTH*0.2F,Settings.HEIGHT*0.5F);
        soraHb.move(this.eyePosition.x,Settings.HEIGHT*0.5F);

        this.leaveButtonHb=new Hitbox(BATwinsSoraShopItem.ITEMBUTTON.getWidth()*2*BATwinsSoraShopItem.BGSCALE,
                BATwinsSoraShopItem.ITEMBUTTON.getHeight()*2*BATwinsSoraShopItem.BGSCALE);
        this.leaveButtonHb.move(Settings.WIDTH / 2.0F + 460.0F * Settings.xScale,Settings.HEIGHT/2.0F-420.0F*Settings.scale);
    }

    private void addShopItem(int index, BATwinsSoraShopItem.ShopItem item){
        float x = Settings.WIDTH / 2.0F + 60.0F * Settings.xScale;
        float y = Settings.HEIGHT / 2.0F + 20.0F * Settings.yScale;
        x += (index % 4) * 200.0F * Settings.xScale;
        y -= (index / 4) * 300.0F * Settings.yScale;
        this.items.add(new BATwinsSoraShopItem(false, new Vector2(x, y), item,this));

    }

    private void initAnimation() {
        this.atlas = new TextureAtlas(ModHelper.makeFilePath("event/soraShop", "sora_shop", "atlas"));
        this.json = new SkeletonJson(this.atlas);
        this.json.setScale(Settings.renderScale * 0.6F);

        this.data = json.readSkeletonData(Gdx.files.internal(ModHelper.makeFilePath("event/soraShop", "sora_shop37", "json")));
        this.skeleton = new Skeleton(data);
        this.skeleton.setColor(Color.WHITE.cpy());
        this.stateData = new AnimationStateData(data);
        this.state = new AnimationState(this.stateData);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (this.currentScreen) {
            case START:
                this.currentScreen = CurrentScreen.SHOPPING;
                GenericEventDialog.hide();
                this.state.addAnimation(0, "Start01_Idle_01", false, 0);
                this.state.addAnimation(0, "Idle_01", true, 0);
                this.showDialog(soraMessage.TEXT[0], 5.0F, ModHelper.makePath(soraMessage.EXTRA_TEXT[0]));
                //播放bgm
                CardCrawlGame.music.unsilenceBGM();
                CardCrawlGame.music.playTempBgmInstantly(ModHelper.makePath("soraShop"));
                break;
            case SHOPEND:
                switch (i){
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                        this.imageEventText.setDialogOption(eventStrings.OPTIONS[1]);
                        this.currentScreen=CurrentScreen.END;
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(((float) Settings.WIDTH / 2), ((float) Settings.HEIGHT / 2), new BATwinsPackage());
                        break;
                    case 1:
                        try{
                            this.items.get(0).activeEffect();
                            this.items.get(0).activeEffect();
                            this.items.get(1).activeEffect();
                        }catch (Exception e){
                            ModHelper.logger.warn("What happened?");
                        }
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[3]);
                        this.imageEventText.setDialogOption(eventStrings.OPTIONS[1]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Shame(), ((float) Settings.WIDTH / 2), ((float) Settings.HEIGHT / 2)));
                        this.currentScreen=CurrentScreen.END;
                        break;
                    case 2:
                        openMap();
                        break;
                    default:
                        openMap();
                }
                break;
            case END:
                openMap();
                break;
        }
    }

    @Override
    public void update() {
        super.update();
        if (this.currentScreen == CurrentScreen.SHOPPING) {
            this.updateAnimation();
            this.updateShopItems();
            this.updateSoraDialog();

            this.soraHb.update();
            if(this.soraHb.hovered&&InputHelper.justClickedLeft){
                InputHelper.justClickedLeft=false;
                if(!this.showDialog){
                    this.randomSoraAudio();
                }
            }

            this.leaveButtonHb.update();
            if(this.leaveButtonHb.hovered&&InputHelper.justClickedLeft){
                InputHelper.justClickedLeft=false;
                this.currentScreen=CurrentScreen.SHOPEND;
                this.imageEventText.clearAllDialogs();
                this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[1]);
                this.imageEventText.setDialogOption(String.format(eventStrings.OPTIONS[2],lostHp),new BATwinsPackage());
                this.imageEventText.setDialogOption(eventStrings.OPTIONS[3],new Shame());
                this.imageEventText.setDialogOption(eventStrings.OPTIONS[1]);
                GenericEventDialog.show();
                CardCrawlGame.music.silenceTempBgmInstantly();
            }
        }
    }

    private void randomSoraAudio() {
        int r=random.nextInt(soraMessage.TEXT.length-1)+1;
        this.showDialog(soraMessage.TEXT[r],2.0F,ModHelper.makePath(soraMessage.EXTRA_TEXT[r]));
        switch (r){
            case 1:
                this.state.setAnimation(1,"Talk_06_A",false);
                break;
            case 2:
                this.state.setAnimation(1,"Talk_05_A",false);
                break;
            case 3:
                this.state.setAnimation(1,"Talk_05_A",false);
                this.state.getCurrent(1).setEndTime(0.5F);
                break;
            default:
                break;

        }
    }

    private void updateSoraDialog() {
        if (this.showDialog) {
            this.dialogDuration -= Gdx.graphics.getDeltaTime();
            this.dialogFadeDuration = Math.min(this.dialogFadeDuration + Gdx.graphics.getDeltaTime(), 1.0F);
            this.dialogColor.a = dialogFadeDuration;
            if (this.dialogDuration < 0.0F) {
                this.showDialog = false;
            }
        } else if (this.dialogFadeDuration > 0.0F) {
            this.dialogFadeDuration = Math.max(this.dialogFadeDuration - Gdx.graphics.getDeltaTime(), 0.0F);
            this.dialogColor.a = dialogFadeDuration;
        }
    }

    private void updateShopItems() {
        this.items.forEach(BATwinsSoraShopItem::update);
    }

    private void updateAnimation() {
        this.state.update(Gdx.graphics.getDeltaTime());
        this.state.apply(this.skeleton);
        if (this.state.getCurrent(0).getAnimation().getName().equals("Idle_01")) {
            MoveEyes();
        }
        this.skeleton.updateWorldTransform();
        this.skeleton.setPosition(Settings.WIDTH / 2.0F, 0);

    }

    private void MoveEyes() {
        Bone bone = this.skeleton.findBone("Touch_Eye_Key");
        Bone bone1 = this.skeleton.findBone("Touch_Point_Key");
        Vector2 target = new Vector2(InputHelper.mX, InputHelper.mY);
        Vector2 temp = target.sub(this.eyePosition);
        temp.scl(Settings.scale * 0.05F);
        bone.setPosition(temp.y, -temp.x);
        bone1.setPosition(temp.y, -temp.x);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (this.currentScreen == CurrentScreen.SHOPPING) {
            sb.end();
            CardCrawlGame.psb.begin();
            AbstractCreature.sr.draw(CardCrawlGame.psb, this.skeleton);
            CardCrawlGame.psb.end();
            sb.begin();
            sb.setBlendFunction(770, 771);

            this.renderShopBackGround(sb);
            this.renderShopItems(sb);

            this.renderLeaveButton(sb);

            this.renderSoraDialog(sb);

            this.soraHb.render(sb);

            this.leaveButtonHb.render(sb);
        }
    }

    private void renderLeaveButton(SpriteBatch sb) {
        Color color=Color.RED.cpy();
        if(this.leaveButtonHb.hovered){
            color.g=0.3F;
        }
        sb.setColor(color);
        sb.draw(BATwinsSoraShopItem.ITEMBUTTON,leaveButtonHb.x,leaveButtonHb.y,
                this.leaveButtonHb.width,this.leaveButtonHb.height);
        FontHelper.renderFontCentered(sb,FontHelper.SCP_cardTitleFont_small,eventStrings.OPTIONS[1],this.leaveButtonHb.cX,this.leaveButtonHb.cY,Color.WHITE);
    }

    private void renderSoraDialog(SpriteBatch sb) {
        sb.setColor(this.dialogColor);
        sb.draw(DIALOG, Settings.WIDTH * 0.2F, Settings.HEIGHT * 0.1F, 500.0F * Settings.scale, 300.0F * Settings.scale);

        int rawFont = 10;
        if(Settings.language!=Settings.GameLanguage.ZHS){
            rawFont=15;
        }
        String[] splitStrings = splitStringIntoGroups(this.dialogMsg, rawFont);
        float y = Settings.HEIGHT * 0.1F + 180.0F * Settings.scale;
        for (String s : splitStrings) {
            FontHelper.renderFontLeft(sb, FontHelper.SCP_cardTitleFont_small, s, Settings.WIDTH * 0.2F + 50.0F * Settings.scale, y, this.dialogColor);
            y -= FontHelper.SCP_cardTitleFont_small.getLineHeight();
        }
    }

    public static String[] splitStringIntoGroups(String input, int groupSize) {
        int inputLength = input.length();
        int numGroups = (int) Math.ceil((double) inputLength / groupSize);  // 计算需要的分组数
        String[] result = new String[numGroups];  // 用于存放结果的数组

        for (int i = 0; i < numGroups; i++) {
            int start = i * groupSize;  // 每组的起始索引
            int end = Math.min(start + groupSize, inputLength);  // 确保不会超出字符串长度
            result[i] = input.substring(start, end);  // 提取子字符串
        }

        return result;
    }

    private void renderShopBackGround(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(shopBackground, Settings.WIDTH / 2.0F, 0, Settings.WIDTH / 2.0F, Settings.HEIGHT * 0.8F);

    }

    private void renderShopItems(SpriteBatch sb) {
        this.items.forEach(i -> i.render(sb));
    }

    public void showDialog(String msg, float duration, String audioKey) {
        this.dialogDuration = duration;
        this.dialogFadeDuration = 0.0F;
        this.showDialog = true;
        this.dialogMsg = msg;
        if (!StringUtils.isEmpty(audioKey)) {
            CardCrawlGame.sound.play(audioKey);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        this.atlas.dispose();
    }
}
