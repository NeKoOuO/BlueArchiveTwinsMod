package baModDeveloper.effect;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BATwinsPaintingArtEffect extends AbstractGameEffect {
    private Texture img;
    private AbstractMonster target;
    private float x,y;
    private TextureRegion textureRegion;
    private float width,height;
    private float stepX,stepY;
    private boolean playSound=false;
    public BATwinsPaintingArtEffect(Color color, AbstractMonster target){
        this.target=target;
        this.color=color;
        this.color.a=1.0F;
        this.img= ImageMaster.loadImage(ModHelper.makeImgPath("effect","vfx"));
        textureRegion= new TextureRegion(this.img);
        this.x= AbstractDungeon.player.hb.cX+150* Settings.scale;
        this.y= AbstractDungeon.floorY+120*Settings.scale;
//        this.rotation= MathUtils.random(-20,20);
        width=Math.min(150*Settings.scale,target.hb.cX-this.x);
        height=0.0F;
        this.duration=0.5F;

        this.stepX=(target.hb.cX-this.x)/this.duration;
        this.stepY=(target.hb.cY-this.y)/this.duration;
    }

    @Override
    public void update() {
        this.duration-= Gdx.graphics.getDeltaTime();
        if(this.duration<=0.0F){
            this.isDone=true;
        }else{
//            this.y+=Math.signum(this.y,AbstractDungeon.floorY+)
            if(!playSound){
                CardCrawlGame.sound.play(ModHelper.makePath("Midori_Ex"));
                this.playSound=true;
            }
            this.width+=this.stepX*Gdx.graphics.getDeltaTime();
//            this.height+=this.stepY*Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setColor(this.color);
        spriteBatch.draw(this.textureRegion,this.x,this.y,width,this.textureRegion.getRegionHeight()/3.0F);
//        public void draw(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)

    }

    @Override
    public void dispose() {
        this.img.dispose();
    }
}
