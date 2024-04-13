package baModDeveloper.effect;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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
    private float width;
    public BATwinsPaintingArtEffect(Color color, AbstractMonster target){
        this.target=target;
        this.color=color;
        this.color.a=1.0F;
        this.img= ImageMaster.loadImage(ModHelper.makeImgPath("effect","vfx"));
        textureRegion= new TextureRegion(this.img);
        this.x= AbstractDungeon.player.hb.cX+150* Settings.scale;
        this.y= AbstractDungeon.floorY+50*Settings.scale;
        this.rotation= MathUtils.random(-20,20);
        width=Math.min(150*Settings.scale,target.hb.cX-this.x);
        this.duration=0.5F;
    }

    @Override
    public void update() {
        this.duration-= Gdx.graphics.getDeltaTime();
        if(this.duration<=0.0F){
            this.isDone=true;
        }else{
//            this.y+=Math.signum(this.y,AbstractDungeon.floorY+)
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setColor(this.color);
        spriteBatch.draw(this.textureRegion,this.x,this.y,0,0,target.hb.cX-this.x,this.img.getHeight()/2.0F,1.0F,1.0F,this.rotation);
//        public void draw(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)

    }

    @Override
    public void dispose() {
        this.img.dispose();
    }
}
