package baModDeveloper.effect;

import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BATwinsEasterEggEffect extends AbstractGameEffect {
    private static Texture momoi1;
    private static Texture midori1 = ImageMaster.loadImage(ModHelper.makeImgPath("UI", "EasterEgg"));
    private static Texture momoi2, midori2;
    private boolean character;
    private boolean HSence;
    //    private boolean reverse;
    private float FadeInOut;

    public BATwinsEasterEggEffect(boolean character, boolean HSence) {
        this.character = character;
        this.HSence = HSence;
//        midori1= ImageMaster.loadImage(ModHelper.makeImgPath("UI","EasterEgg"));
//        this.reverse=false;
        this.duration = 2.0F;
        this.FadeInOut = 0.2F;
        this.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.color.a = 0.0F;

//        BATwinsNeowNarrationScreenPatch.openPatch.isBATwins=true;
    }

    @Override
    public void update() {
//        this.duration -= Gdx.graphics.getDeltaTime();
        if (2.0F - this.duration < this.FadeInOut) {
            this.color.a = MathHelper.scaleLerpSnap(this.color.a, 1.0F);
        } else if (this.duration < this.FadeInOut) {
            this.color.a = MathHelper.scaleLerpSnap(this.color.a, 0.0F);
        }
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
//        if(this.duration>0.0F){
        if (!this.isDone) {
//            spriteBatch.setColor(this.color);
            spriteBatch.setColor(Color.WHITE.cpy());
            spriteBatch.draw(midori1, 0, 0, midori1.getWidth() * Settings.scale, midori1.getHeight() * Settings.scale);
            spriteBatch.setBlendFunction(770, 771);
        }
//        }
    }

    @Override
    public void dispose() {

    }
}
