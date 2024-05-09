package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BATwinsBookOfProhibitions extends CustomRelic {
    public static final String ID= ModHelper.makePath("BookOfProhibitions");
    private static final Texture texture= TextureLoader.getTexture(ModHelper.makeImgPath("relic","BookOfProhibitions"));
    private static final Texture outline=TextureLoader.getTexture(ModHelper.makeImgPath("relic","BookOfProhibitions_p"));
    private static final RelicTier type=RelicTier.SPECIAL;
    private final Texture koharu;
    private float duration;
    private float fadeout=0.5F;
    private Color color;
    public BATwinsBookOfProhibitions() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);
        koharu= ImageMaster.loadImage(ModHelper.makeImgPath("UI","koharu"));
        this.duration= 0.0F;
        this.color=Color.WHITE.cpy();
        this.color.a=1.0F;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(!this.grayscale){
            if(info.type== DamageInfo.DamageType.NORMAL){
                this.flash();
                if(MathUtils.randomBoolean(0.1F)){
                    this.duration=3.0F;
                }
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
                this.grayscale=true;
                addToBot(new HealAction(AbstractDungeon.player,AbstractDungeon.player,damageAmount/3));
            }
        }
    }

    @Override
    public void atTurnStart() {
        this.grayscale=false;
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        this.grayscale=false;
    }

    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        super.renderInTopPanel(sb);
        if(this.duration>0){
            renderKoharu(sb);
        }
    }

    private void renderKoharu(SpriteBatch sb) {
        TextureRegion background = new TextureRegion(koharu, 0, 0, koharu.getWidth() / 2, koharu.getHeight());
        TextureRegion character = new TextureRegion(koharu, koharu.getWidth() / 2, 0, koharu.getWidth() / 2, koharu.getHeight());

//        if(this.duration<=this.fadeout){
//            this.color=
//        }
        sb.setColor(this.color);

        sb.draw(background,this.currentX-background.getRegionWidth()/4.0F,this.currentY-200.0F*Settings.scale,0.0F,0.0F,character.getRegionWidth(),character.getRegionHeight(),0.5F,0.5F,0.0F);

        sb.draw(character,this.currentX-character.getRegionWidth()/4.0F-character.getRegionWidth()/4.0F,this.currentY-200.0F*Settings.scale-character.getRegionHeight()/4.0F,character.getRegionWidth()/2.0F,character.getRegionHeight()/2.0F,character.getRegionWidth(),character.getRegionHeight(),0.5F,0.5F,getRotation(this.duration));
        this.duration-= Gdx.graphics.getDeltaTime();
    }

    private float getRotation(float duration){
        int angle=(int)duration%2;
        if(angle==0){
            return -15;
        }else{
            return 15;
        }
    }
}
