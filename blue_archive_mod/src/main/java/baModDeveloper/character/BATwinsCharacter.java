package baModDeveloper.character;

import baModDeveloper.BATwinsMod;
import baModDeveloper.animation.AbstractAnimation;
import baModDeveloper.cards.*;
import baModDeveloper.core.BATwinsEnergyManager;
import baModDeveloper.helpers.Character3DHelper;
import baModDeveloper.helpers.ColorComparer;
import baModDeveloper.helpers.ImageHelper;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.patch.BATwinsAbstractCardPatch;
import baModDeveloper.patch.BATwinsCharacterOptionPatch;
import baModDeveloper.power.BATwinsBorrowMePower;
import baModDeveloper.relic.BATwinsGameMagazine;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import baModDeveloper.ui.panels.BATwinsExperencePanel;
import baModDeveloper.ui.panels.energyorb.BATwinsEnergyMidoriOrb;
import baModDeveloper.ui.panels.energyorb.BATwinsEnergyMomoiOrb;
import baModDeveloper.ui.panels.icons.BATwinsMidoriEnergyOrbSmall;
import baModDeveloper.ui.panels.icons.BATwinsMomoiEnergyOrbSmall;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class BATwinsCharacter extends CustomPlayer {
    private static final String BATWINS_CHARACTER_SHOULDER_1 = ModHelper.makeImgPath("char", "shoulder");
    private static final String BATWINS_CHARACTER_SHOULDER_2 = ModHelper.makeImgPath("char", "shoulder2");
    private static final String BATWINS_CHARACTER_CORPSE = ModHelper.makeImgPath("char", "corpse");
    private static final String[] MOMOI_ORB_TEXTURES = new String[]{
            ModHelper.makeImgPath("UI/orb", "layer1_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer2_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer3_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer4_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer5_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer6_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer1d_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer2d_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer3d_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer4d_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer5d_momoi")
    };
    private static final String[] MIDORI_ORB_TEXTURES = new String[]{
            ModHelper.makeImgPath("UI/orb", "layer1_midori"),
            ModHelper.makeImgPath("UI/orb", "layer2_midori"),
            ModHelper.makeImgPath("UI/orb", "layer3_midori"),
            ModHelper.makeImgPath("UI/orb", "layer4_midori"),
            ModHelper.makeImgPath("UI/orb", "layer5_midori"),
            ModHelper.makeImgPath("UI/orb", "layer6_midori"),
            ModHelper.makeImgPath("UI/orb", "layer1d_midori"),
            ModHelper.makeImgPath("UI/orb", "layer2d_midori"),
            ModHelper.makeImgPath("UI/orb", "layer3d_midori"),
            ModHelper.makeImgPath("UI/orb", "layer4d_midori"),
            ModHelper.makeImgPath("UI/orb", "layer5d_midori")
    };
    private static final String MOMOI_ORB_VFX = ModHelper.makeImgPath("UI/orb", "vfx_momoi");
    private static final String MIDORI_ORB_VFX = ModHelper.makeImgPath("UI/orb", "vfx_midori");
    private static final String MOMOI_ORB_MARK = ModHelper.makeImgPath("UI/orb", "orbMark_momoi");
    private static final String MIDORI_ORB_MARK = ModHelper.makeImgPath("UI/orb", "orbMark_midori");
    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F,
            -5.0F, 0.0F};
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack
            .getCharacterString(ModHelper.makePath("Twins"));

    private EnergyOrbInterface energyOrbMomoi;
    private EnergyOrbInterface energyOrbMidori;


    //GIF相关
//    private static final String[] GIFPATH_MOMOI=new String[]{
//            ModHelper.makeGifPath("char",ModHelper.MOMOI_FLODER,"shooting_stand"),
//            ModHelper.makeGifPath("char",ModHelper.MOMOI_FLODER,"run")
//    };
//    private static final String[] GIFPATH_MIDORI=new String[]{
//            ModHelper.makeGifPath("char",ModHelper.MIDORI_FLODER,"shooting_stand"),
//            ModHelper.makeGifPath("char",ModHelper.MIDORI_FLODER,"run")
//
//    };
//    private ArrayList<AbstractAnimation> anima_momoi;
//    private ArrayList<AbstractAnimation> anima_midori;
//    private int anim_time_momoi = 0;
//    private int anim_len_momoi = 0;
//    private int anim_time_midori = 0;
//    private int anim_len_midori = 0;
//    private AbstractAnimation rendered_anima_momoi;
//    private AbstractAnimation rendered_anima_midori;
    //3D相关
    private static final Character3DHelper character3DHelper=new Character3DHelper();

    BATwinsExperencePanel expPanel;

    //排序手牌
    ColorComparer colorComparer;
    //角色立绘，先暂时使用图片代替，之后使用3d模型替换
    private static final String stand_Img = BATwinsMod.Enable3D?ModHelper.makeImgPath("char","p"):ModHelper.makeImgPath("char", "standup");

    //    public static GifAnimation character=new GifAnimation(ModHelper.makeGifPath("char","character"));
    public BATwinsCharacter(String name) {
        super(name, Enums.BATwins, MOMOI_ORB_TEXTURES, MOMOI_ORB_VFX, LAYER_SPEED, null, null);
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 0.0F * Settings.scale);

        this.energyOrbMomoi = new BATwinsEnergyMomoiOrb(MOMOI_ORB_TEXTURES, MOMOI_ORB_VFX, LAYER_SPEED, MOMOI_ORB_MARK);
        this.energyOrbMidori = new BATwinsEnergyMidoriOrb(MIDORI_ORB_TEXTURES, MIDORI_ORB_VFX, LAYER_SPEED, MIDORI_ORB_MARK);

        this.initializeClass(stand_Img, BATWINS_CHARACTER_SHOULDER_2,
                BATWINS_CHARACTER_SHOULDER_1, BATWINS_CHARACTER_CORPSE, getLoadout(), 0.0F, 0.0F, 200.0F, 200.0F,
                new BATwinsEnergyManager(2));

        //3D相关
//        character3DHelper.init();
        if(BATwinsMod.Enable3D){
            if(!character3DHelper.inited()){
                character3DHelper.init();
            }
            character3DHelper.setPosition(Settings.WIDTH*0.04F,Settings.HEIGHT*0.07F);
        }

    }

    @Override
    protected void initializeClass(String imgUrl, String shoulder2ImgUrl, String shouldImgUrl, String corpseImgUrl, CharSelectInfo info, float hb_x, float hb_y, float hb_w, float hb_h, EnergyManager energy) {
        super.initializeClass(imgUrl, shoulder2ImgUrl, shouldImgUrl, corpseImgUrl, info, hb_x, hb_y, hb_w, hb_h, energy);

        //gif相关
//        this.anima_momoi=new ArrayList<>();
//        for(String s:GIFPATH_MOMOI){
//            AbstractAnimation temp=new AbstractAnimation(s,this.drawX,this.drawY,200,200,0.5F);
//            temp.setMovable(false);
//            this.anima_momoi.add(temp);
//        }
//
//        this.anima_midori=new ArrayList<>();
//        for(String s:GIFPATH_MIDORI){
//            AbstractAnimation temp=new AbstractAnimation(s,this.drawX,this.drawY,200,200,0.5F);
//            temp.setMovable(false);
//            this.anima_midori.add(temp);
//        }
//
//        AbstractAnimation.addAnimation(null);
//        AbstractAnimation.addAnimation(null);


        colorComparer = new ColorComparer();

        expPanel = new BATwinsExperencePanel(this.drawX - 230 * Settings.scale, this.drawY);
        expPanel.show();
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
        if(MathUtils.randomBoolean()){
            CardCrawlGame.sound.play(ModHelper.makePath("charSelect_momoi"));
        }else {
            CardCrawlGame.sound.play(ModHelper.makePath("charSelect_midori"));
        }
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public CardColor getCardColor() {
        return Enums.BATWINS_MOMOI_CARD;
    }

    @Override
    public Color getCardRenderColor() {
        return BATwinsMod.BATwinsColor;
    }

    @Override
    public Color getCardTrailColor() {
        return BATwinsMod.BATwinsColor;
    }

    public Color getCardTrailColor(BATwinsEnergyPanel.EnergyType cardType) {
        if (cardType == BATwinsEnergyPanel.EnergyType.MOMOI) {
            return BATwinsMod.MOMOIColor;
        } else {
            return BATwinsMod.MIDORIColor;
        }
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(characterStrings.NAMES[0], characterStrings.TEXT[0], 75, 75, 0, 99, 5, this,
                this.getStartingRelics(), this.getStartingDeck(), false);
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public Color getSlashAttackColor() {
        return BATwinsMod.BATwinsColor;
    }

    @Override
    public AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new BATwinsMomoiStrick();
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(BATwinsMomoiStrick.ID);
        retVal.add(BATwinsMomoiStrick.ID);
        retVal.add(BATwinsMidoriStrick.ID);
        retVal.add(BATwinsMidoriStrick.ID);
        retVal.add(BATwinsMomoiDefend.ID);
        retVal.add(BATwinsMomoiDefend.ID);
        retVal.add(BATwinsMidoriDefend.ID);
        retVal.add(BATwinsMidoriDefend.ID);
        retVal.add(BATwinsAlreadyAngry.ID);
        retVal.add(BATwinsPaintingConception.ID);

        if (BATwinsCharacterOptionPatch.updateHitboxPatch.FindEasterEgg) {
            this.masterDeck.addToBottom(new BATwinsEasterEgg());
            BATwinsCharacterOptionPatch.updateHitboxPatch.PressCount = 0;
            BATwinsCharacterOptionPatch.updateHitboxPatch.FindEasterEgg = false;
        }
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(BATwinsGameMagazine.ID);
        return retVal;
    }

    @Override
    public String getTitle(PlayerClass arg0) {
        return characterStrings.NAMES[0];
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new BATwinsCharacter(this.name);
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        this.energyOrbMomoi.renderOrb(sb, enabled, current_x, current_y);
        this.energyOrbMidori.renderOrb(sb, enabled, current_x, current_y);
    }

    public static class Enums {
        @SpireEnum
        public static PlayerClass BATwins;

        @SpireEnum(name = "BATWINSMOMOICARD")
        public static AbstractCard.CardColor BATWINS_MOMOI_CARD;
        @SpireEnum(name = "BATWINSMIDORICARD")
        public static AbstractCard.CardColor BATWINS_MIDORI_CARD;

        @SpireEnum(name = "BATWINSMOMOICARD")
        public static CardLibrary.LibraryType BATWINS_MOMOI_LIBRARY;
        @SpireEnum(name = "BATWINSMIDORICARD")
        public static CardLibrary.LibraryType BATWINS_MIDORI_LIBRARY;

    }

    @Override
    public Texture getEnergyImage() {
        if (Objects.requireNonNull(BATwinsEnergyPanel.selectedEnergySlot) == BATwinsEnergyPanel.EnergyType.MIDORI) {
            return BATwinsMidoriEnergyOrbSmall.getTEXTRUE();
        }
        return BATwinsMomoiEnergyOrbSmall.getTEXTRUE();
    }

    public Texture[] getEnergyImages() {
        return new Texture[]{((CustomEnergyOrb) this.energyOrbMomoi).getEnergyImage(), ((CustomEnergyOrb) energyOrbMidori).getEnergyImage()};
    }

    @Override
    public void updateOrb(int totalCount) {
        this.energyOrbMomoi.updateOrb(totalCount);
        this.energyOrbMidori.updateOrb(totalCount);
    }

    public void updateOrb(int MomoiCount, int MidoriCount) {
        this.energyOrbMomoi.updateOrb(MomoiCount);
        this.energyOrbMidori.updateOrb(MidoriCount);
    }

    @Override
    public void gainEnergy(int e) {
        BATwinsEnergyPanel.addEnergy(e, BATwinsEnergyPanel.EnergyType.SPEIFY);
        this.hand.glowCheck();
    }

    public void gainEnergy(int e, BATwinsEnergyPanel.EnergyType type) {
        BATwinsEnergyPanel.addEnergy(e, type);
        this.hand.glowCheck();
    }

    @Override
    public void loseEnergy(int e) {
        BATwinsEnergyPanel.useEnergy(e);
    }

    @Override
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool) {
        AbstractCard.CardColor MomoiColor = Enums.BATWINS_MOMOI_CARD;
        AbstractCard.CardColor MidoriColor = Enums.BATWINS_MIDORI_CARD;
        Iterator var3 = CardLibrary.cards.entrySet().iterator();

        while (true) {
            Map.Entry c;
            AbstractCard card;
            do {
                do {
                    do {
                        if (!var3.hasNext()) {
                            return tmpPool;
                        }

                        c = (Map.Entry) var3.next();
                        card = (AbstractCard) c.getValue();
                    } while (!card.color.equals(MomoiColor) && !card.color.equals(MidoriColor));
                } while (card.rarity == AbstractCard.CardRarity.BASIC);
            } while (UnlockTracker.isCardLocked((String) c.getKey()) && !Settings.isDailyRun);

            tmpPool.add(card);
        }
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        if (c.type == AbstractCard.CardType.ATTACK) {
            this.useFastAttackAnimation();
        }

        c.calculateCardDamage(monster);
        if (c.cost == -1 && EnergyPanel.totalCount < energyOnUse && !c.ignoreEnergyOnUse) {
            c.energyOnUse = EnergyPanel.totalCount;
        }

        if (c.cost == -1 && c.isInAutoplay) {
            c.freeToPlayOnce = true;
        }
        if (!(c instanceof BATwinsModCustomCard)) {
            if (!BATwinsAbstractCardPatch.FieldPatch.blockTheOriginalEffect.get(c)) {
                c.use(this, monster);
            } else {
                BATwinsAbstractCardPatch.FieldPatch.blockTheOriginalEffect.set(c, false);
            }
        } else {
            c.use(this, monster);
        }
        AbstractDungeon.actionManager.addToBottom(new UseCardAction(c, monster));
        if (!c.dontTriggerOnUseCard) {
            this.hand.triggerOnOtherCardPlayed(c);
        }

        this.hand.removeCard(c);
        this.cardInUse = c;
        c.target_x = (float) (Settings.WIDTH / 2);
        c.target_y = (float) (Settings.HEIGHT / 2);
        if (c.costForTurn > 0 && !c.freeToPlay() && !c.isInAutoplay && (!this.hasPower("Corruption") || c.type != AbstractCard.CardType.SKILL)) {
            if (c instanceof BATwinsModCustomCard && this.energy instanceof BATwinsEnergyManager) {
                if (AbstractDungeon.player.hasPower(BATwinsBorrowMePower.POWER_ID)) {
                    ((BATwinsEnergyManager) this.energy).use(c.costForTurn, BATwinsEnergyPanel.EnergyType.SHARE);
                } else {
                    ((BATwinsEnergyManager) this.energy).use(c.costForTurn, ((BATwinsModCustomCard) c).modifyEnergyType);
                }
            } else {
                this.energy.use(c.costForTurn);
            }
        }

        if (!this.hand.canUseAnyCard() && !this.endTurnQueued) {
            AbstractDungeon.overlayMenu.endTurnButton.isGlowing = true;
        }

    }

    public TextureAtlas.AtlasRegion getOrb(String word) {
        if (word.equals("[TE]")) {
            return ImageHelper.MOMOISMALLORB;
        } else {
            return ImageHelper.MIDORISMALLORB;
        }
    }

    public static CardColor getOtherColor(CardColor color) {
        if (color == Enums.BATWINS_MOMOI_CARD) {
            return Enums.BATWINS_MIDORI_CARD;
        } else if (color == Enums.BATWINS_MIDORI_CARD) {
            return Enums.BATWINS_MOMOI_CARD;
        }
        return color;
    }

    @Override
    public void update() {
        super.update();

        //排序手牌
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (BATwinsMod.AutoSort)
                this.hand.group.sort(colorComparer);
            if (BATwinsMod.ShowExpBar)
                this.expPanel.update();
//            if(BATwinsMod.Enable3D)
//                character3DHelper.update();

        }
        if(BATwinsMod.Enable3D&&!(AbstractDungeon.getCurrRoom() instanceof RestRoom)){
            character3DHelper.update();

        }
    }

    private static final Color BLUE_BORDER_GLOW_COLOR = new Color(0.2F, 0.9F, 1.0F, 0.25F);

    public static Color getColorWithCardColor(CardColor color) {
        if (color == Enums.BATWINS_MOMOI_CARD) {
            return BATwinsMod.MOMOIColor.cpy();
        } else if (color == Enums.BATWINS_MIDORI_CARD) {
            return BATwinsMod.MIDORIColor.cpy();
        }
        return BLUE_BORDER_GLOW_COLOR.cpy();
    }


    @Override
    public void render(SpriteBatch sb) {
//        if(this.rendered_anima_midori!=null){
//            this.rendered_anima_midori.render(sb);
//        }
//        if(this.rendered_anima_momoi!=null){
//            this.rendered_anima_momoi.render(sb);
//        }
        super.render(sb);

        if (BATwinsMod.ShowExpBar && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            this.expPanel.render(sb);
        if(BATwinsMod.Enable3D&& !(AbstractDungeon.getCurrRoom() instanceof RestRoom)) {
            character3DHelper.render(sb);
        }
    }
//    public enum AnimationChar{
//        MOMOI,
//        MIDORI
//    }
//    public enum Animation{
//        SHOOTING_STAND(0,100),
//        RUN(1,100);
//
//        private int index;
//        private int len;
//        private Animation(int index,int len){
//            this.index=index;
//            this.len=len;
//        }
//        public int getIndex(){
//            return index;
//        }
//        public int getLen(){
//            return len;
//        }
//    }
//    public void setAnimation(AnimationChar character,Animation anima){
//        switch (character){
//            case MOMOI:
//                this.rendered_anima_momoi=this.anima_momoi.get(anima.getIndex());
//                this.anim_len_momoi=anima.getLen();
//                if(anima==Animation.RUN){
//                    this.rendered_anima_momoi.setPosition(this.rendered_anima_momoi.drawX-200,this.rendered_anima_momoi.drawY);
//                }
//                break;
//            case MIDORI:
//                this.rendered_anima_midori=this.anima_midori.get(anima.getIndex());
//                this.anim_len_midori=anima.getLen();
//                if(anima==Animation.RUN){
//                    this.rendered_anima_midori.setPosition(this.rendered_anima_midori.drawX-200,this.rendered_anima_momoi.drawY);
//                }
//                break;
//            default:
//                return;
//        }
//    }

    public void onEnterRoom() {
        //设置进入房间时的动画
        if(BATwinsMod.Enable3D){
            character3DHelper.setMomoiAnimation(Character3DHelper.MomoiActionList.MOVING);
            character3DHelper.setMidoriAnimation(Character3DHelper.MidoriActionList.MOVING);
        }
    }

    public void setMomoiAnimation(Character3DHelper.MomoiActionList anima){
        if(BATwinsMod.Enable3D){
            character3DHelper.setMomoiAnimation(anima);
        }
    }
    public void setMidoriAnimation(Character3DHelper.MidoriActionList anima){
        if(BATwinsMod.Enable3D){
            character3DHelper.setMidoriAnimation(anima);
        }
    }

    @Override
    public boolean saveFileExists() {
        return super.saveFileExists();
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
//        if(this.currentBlock==0&&info.output>0&&this.currentHealth>0){
//            CardCrawlGame.sound.play(ModHelper.makeAudioPath("affected_momoi"));
//        }
    }
}
