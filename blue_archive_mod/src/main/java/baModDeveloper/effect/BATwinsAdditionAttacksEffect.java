package baModDeveloper.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AdditiveSlashImpactEffect;

public class BATwinsAdditionAttacksEffect extends AbstractGameEffect {
    private float sX,sY;
    private float tX,tY;
    private float x,y;
    private float vX,vY;
    private TextureAtlas.AtlasRegion img;
    private boolean activated;

    public BATwinsAdditionAttacksEffect(float sX, float sY, float tX, float tY, Color color){
        this.img= ImageMaster.GLOW_SPARK_2;
        this.sX=sX;
        this.sY=sY+MathUtils.random(-90.0F, 90.0F) * Settings.scale;
        this.tX=tX;
        this.tY=tY;
        this.x=this.sX;
        this.y=this.sY;
        this.scale=0.01F;
        this.startingDuration=0.3F;
        this.duration=0.3F;
        this.renderBehind= MathUtils.randomBoolean(0.2F);
        this.color=color;
        this.activated=false;
    }

    @Override
    public void update() {
        this.duration-= Gdx.graphics.getDeltaTime();
        this.scale = Interpolation.pow3Out.apply(2.0F, 2.5F, this.duration / this.startingDuration) * Settings.scale/3.0F;
        this.x = Interpolation.swingOut.apply(this.tX, this.sX, this.duration / this.startingDuration );
        this.y = Interpolation.swingOut.apply(this.tY, this.sY, this.duration / this.startingDuration );

        if(this.duration<0.0F){
            AbstractDungeon.effectsQueue.add(new AdditiveSlashImpactEffect(this.tX, this.tY, this.color.cpy()));
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, MathUtils.randomBoolean());
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setColor(this.color);
        spriteBatch.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 2.0F, this.scale * 2.0F, this.rotation);
        spriteBatch.setColor(this.color);
        spriteBatch.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);

    }

    @Override
    public void dispose() {

    }
}
