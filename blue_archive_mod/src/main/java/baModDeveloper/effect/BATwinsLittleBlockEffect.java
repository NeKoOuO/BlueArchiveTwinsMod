package baModDeveloper.effect;

import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BATwinsLittleBlockEffect extends AbstractGameEffect {
    private static String texturePath= ModHelper.makeImgPath("effect","block");
    public static TextureRegion littleBlock= new TextureRegion(ImageMaster.loadImage(texturePath));

    private Color startColor;
    private float speed;
    private float size;
    private float x;
    private float y;
    private float totolDuration;
    private boolean horizon;
    private float originSize;
    public BATwinsLittleBlockEffect(Color startColor,float x,float y,float size,float speed,boolean horizon){
        this.startColor=startColor;
        this.startColor.a=0.6F;
        this.x=x;
        this.y=y;
        this.size=this.originSize=size;
        this.speed=speed;
        this.horizon=horizon;
        this.duration=0.8F;
        this.totolDuration=this.duration;
        this.isDone=false;
    }

    @Override
    public void update() {
        this.duration-= Gdx.graphics.getDeltaTime();
        this.size=this.originSize*this.duration/this.totolDuration;
        if(this.duration<0.0F){
            this.isDone=true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setColor(this.startColor);
        spriteBatch.draw(littleBlock,this.x,this.y,littleBlock.getRegionWidth()/2.0F,littleBlock.getRegionHeight()/2.0F,littleBlock.getRegionWidth(),littleBlock.getRegionHeight(),this.size,this.size,0.0F);

    }

    @Override
    public void dispose() {

    }
}
