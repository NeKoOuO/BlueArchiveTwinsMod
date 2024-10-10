package baModDeveloper.ui;

import baModDeveloper.BATwinsMod;
import baModDeveloper.event.BATwinsSoraShop;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import baModDeveloper.localization.SoraItemStrings;
import baModDeveloper.relic.BATwinsCrystalHaniwa;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BATwinsSoraShopItem {

    public static Texture ITEMBG = TextureLoader.getTexture(ModHelper.makeImgPath("UI/soraShop", "ShopItemBg"));
    public static Texture ITEMBUTTON = TextureLoader.getTexture(ModHelper.makeImgPath("UI/soraShop", "ShopItemButton"));


    private boolean isRare;
    private Vector2 position;
    private ShopItem item;
    private Hitbox hitbox;
    private Hitbox buttonHb;
    public static float BGSCALE = 0.6F * Settings.scale;
    private static Vector2 HITBOXOFFSET = new Vector2(ITEMBG.getWidth() * BGSCALE / 2.0F, ITEMBG.getHeight() * BGSCALE / 2.0F);

    private SoraItemStrings soraItemStrings;
    private PowerTip tip;
    private float buttonScale;
    private int count;
    private boolean enable;
    private Texture img;
    BATwinsSoraShop shop;
    Random random;


    public BATwinsSoraShopItem(boolean isRare, Vector2 position, ShopItem item,BATwinsSoraShop shop) {
        this.isRare = isRare;
        this.position = position;
        this.item = item;
        this.hitbox = new Hitbox(ITEMBG.getWidth() * BGSCALE, ITEMBG.getHeight() * BGSCALE);
        this.hitbox.move(position.x + HITBOXOFFSET.x, position.y + HITBOXOFFSET.y);
        this.soraItemStrings = ModHelper.soraItemStringsMap.get(item.getId());

        this.buttonHb = new Hitbox(ITEMBUTTON.getWidth() * BGSCALE, ITEMBUTTON.getHeight() * BGSCALE);
        this.buttonHb.move(position.x + HITBOXOFFSET.x, position.y + ITEMBUTTON.getHeight() * BGSCALE / 2.0F);

        this.tip = new PowerTip();
        this.tip.header = this.soraItemStrings.name;
        this.tip.body = this.soraItemStrings.description;
        this.buttonScale = BGSCALE;
        this.count = this.soraItemStrings.count;
        this.enable = true;
        this.shop=shop;
        this.random=new Random(AbstractDungeon.miscRng.randomLong());

        this.img = getItemImg(this.item);

    }

    public void update() {
        this.hitbox.update();
        this.buttonHb.update();

        if (this.buttonHb.hovered && InputHelper.justClickedLeft && this.enable && AbstractDungeon.player.gold >= this.soraItemStrings.price) {
            InputHelper.justClickedLeft = false;
            this.activeEffect();
            this.count--;
            if (this.count <= 0) {
                this.enable = false;
            }
        }
    }

    private void activeEffect() {
        boolean isActivated=true;
        switch (this.item) {
            case LIFE:
                AbstractDungeon.player.heal((int) (AbstractDungeon.player.maxHealth * 0.2F));
                break;
            case SMITH:
                ArrayList<AbstractCard> canUpgraded = new ArrayList<>();
                AbstractDungeon.player.masterDeck.group.stream().filter(AbstractCard::canUpgrade).forEach(canUpgraded::add);
                if (!canUpgraded.isEmpty()) {
                    Collections.shuffle(canUpgraded, this.random);
                    AbstractCard card = canUpgraded.get(0);
                    card.upgrade();
                    AbstractDungeon.player.bottledCardUpgradeCheck(canUpgraded.get(0));
                    AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(canUpgraded
                            .get(0).makeStatEquivalentCopy()));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                }else{
                    shop.showDialog(this.soraItemStrings.message,2.0F,null);
                    isActivated=false;
                }
                break;
            case CRYSTALHANIWA:
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(((float) Settings.WIDTH / 2), ((float) Settings.HEIGHT / 2), new BATwinsCrystalHaniwa());
                break;
            case KEY:
                if(this.img==ImageMaster.RUBY_KEY)
                    AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.RED));
                if(this.img==ImageMaster.EMERALD_KEY)
                    AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.GREEN));
                if(this.img==ImageMaster.SAPPHIRE_KEY)
                    AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.BLUE));
                break;
            case CHALLENGE:
                BATwinsMod.saveHelper.values.challengeCoupons=true;
                break;
            case INITIALRELIC:
                ArrayList<String> relics=AbstractDungeon.player.getStartingRelics();
                String relicId=relics.get(relics.size()-1);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(((float) Settings.WIDTH / 2), ((float) Settings.HEIGHT / 2),RelicLibrary.getRelic(relicId));
                break;
            case POTIONSLOT:
                AbstractDungeon.player.potionSlots += 1;
                AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
                AbstractDungeon.player.obtainPotion(AbstractDungeon.returnRandomPotion());
                break;
            case TELEPHONE:
                BATwinsMod.saveHelper.values.hasSoraPhone=true;
                break;
            default:
                break;
        }
        if(isActivated){
            AbstractDungeon.player.loseGold(this.soraItemStrings.price);
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(ITEMBG, this.position.x, this.position.y, 0, 0, ITEMBG.getWidth(), ITEMBG.getHeight(),
                BGSCALE, BGSCALE, 0, 0, 0, ITEMBG.getWidth(), ITEMBG.getHeight(), false, false);
        if (this.buttonHb.hovered) {
            sb.setColor(1.0F, 1.0F, 1.0F, 0.25F);
        } else {
            sb.setColor(Color.WHITE);
        }
        if (!this.enable) {
            sb.setColor(Color.GRAY);
        }
        sb.draw(ITEMBUTTON,
                this.position.x,
                this.position.y,
                0,
                0,
                ITEMBUTTON.getWidth(),
                ITEMBUTTON.getHeight(),
                buttonScale, buttonScale,
                0,
                0, 0,
                ITEMBUTTON.getWidth(),
                ITEMBUTTON.getHeight(),
                false, false);
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UI_GOLD, this.position.x + ITEMBUTTON.getWidth() * BGSCALE * 0.1F, this.position.y + ITEMBUTTON.getHeight() * BGSCALE * 0.2F,
                0, 0, ImageMaster.UI_GOLD.getWidth(), ImageMaster.UI_GOLD.getHeight(),
                0.8F * Settings.scale, 0.8F * Settings.scale,
                0, 0, 0, ImageMaster.UI_GOLD.getWidth(), ImageMaster.UI_GOLD.getHeight(), false, false);
        Color color;
        if (AbstractDungeon.player.gold >= this.soraItemStrings.price) {
            color = Color.WHITE.cpy();
        } else {
            color = Color.RED.cpy();
        }
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont,
                Integer.toString(this.soraItemStrings.price), this.position.x + ITEMBUTTON.getWidth() * BGSCALE * 0.5F, this.position.y + ITEMBUTTON.getHeight() * BGSCALE * 0.2F + FontHelper.tipHeaderFont.getCapHeight() * 2, color);

        this.renderTitle(sb);
        this.renderItemImage(sb);
        if (this.hitbox.hovered) {
            this.renderTips(sb);
        }

        this.hitbox.render(sb);
        this.buttonHb.render(sb);
    }

    private void renderTips(SpriteBatch sb) {
        TipHelper.renderGenericTip(InputHelper.mX, InputHelper.mY - 60.0F * Settings.yScale, this.soraItemStrings.name, this.soraItemStrings.description);
    }

    private void renderItemImage(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if(this.item==ShopItem.INITIALRELIC){
            sb.draw(this.img, this.position.x + HITBOXOFFSET.x - 32.0F, this.position.y + HITBOXOFFSET.y - 32.0F,
                    32.0F, 32.0F, 64.0F, 64.0F, Settings.scale * 2F, Settings.scale * 2F, 0.0F, 0, 0, 128, 128, false, false);
        }else{
            sb.draw(this.img, this.position.x + HITBOXOFFSET.x - 32.0F, this.position.y + HITBOXOFFSET.y - 32.0F,
                    32.0F, 32.0F, 64.0F, 64.0F, Settings.scale * 2F, Settings.scale * 2F, 0.0F, 0, 0, 64, 64, false, false);
        }

    }

    private void renderTitle(SpriteBatch sb) {
        float x = this.position.x + HITBOXOFFSET.x;
        float y = this.position.y + ITEMBG.getHeight() * BGSCALE * 0.9F;
        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, this.soraItemStrings.name, x, y, Color.BLACK, 0.7F);
    }


    public enum ShopItem {
        LIFE("Life"),
        SMITH("Smith"),
        MEDICINE("Medicine"),
        CRYSTALHANIWA("CrystalHaniwa"),
        KEY("Key"),
        CHALLENGE("Challenge"),
        INITIALRELIC("InitialRelics"),
        POTIONSLOT("PotionSlot"),
        TELEPHONE("Telephone");

        private String id;

        private ShopItem(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }


    public static Texture getItemImg(ShopItem item) {
        switch (item) {
            case LIFE:
                return ImageMaster.TP_HP;
            case SMITH:
                return TextureLoader.getTexture(ModHelper.makeImgPath("UI/soraShop", "smithStone"));
            case MEDICINE:
                return TextureLoader.getTexture(ModHelper.makeImgPath("UI/soraShop","medicine"));
            case CRYSTALHANIWA:
                return TextureLoader.getTexture(ModHelper.makeImgPath("UI/soraShop","CrystalHaniwa"));
            case KEY:
                List<Texture> keyList=new ArrayList<>();
                if(!Settings.hasRubyKey){
                    keyList.add(ImageMaster.RUBY_KEY);
                }
                if(!Settings.hasEmeraldKey){
                    keyList.add(ImageMaster.EMERALD_KEY);
                }
                if(!Settings.hasSapphireKey){
                    keyList.add(ImageMaster.SAPPHIRE_KEY);
                }
                if(!keyList.isEmpty()){
                    Collections.shuffle(keyList,new Random(AbstractDungeon.miscRng.randomLong()));
                    return keyList.get(0);
                }
                return ImageMaster.RUBY_KEY;
            case CHALLENGE:
                return TextureLoader.getTexture(ModHelper.makeImgPath("UI/soraShop","challengeCoupons"));
            case INITIALRELIC:
                ArrayList<String> relics=AbstractDungeon.player.getStartingRelics();
                String relicId=relics.get(relics.size()-1);
                return RelicLibrary.getRelic(relicId).img;
            case POTIONSLOT:
                return ImageMaster.POTION_T_CONTAINER;
            case TELEPHONE:
                return TextureLoader.getTexture(ModHelper.makeImgPath("UI/soraShop","Telephone"));
            default:
                return ImageMaster.TP_HP;
        }
    }


}
