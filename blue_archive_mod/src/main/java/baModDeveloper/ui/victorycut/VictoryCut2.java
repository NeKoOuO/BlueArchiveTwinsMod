package baModDeveloper.ui.victorycut;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.Character3DHelper;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class VictoryCut2 extends AbstractBATwinsVictoryCut{
    private TextureAtlas.AtlasRegion backImg;
    private TextureRegion fire;
    private Character3DHelper character3DHelper;
    private FrameBuffer buffer;
    private FrameBuffer charBuffer;
    private TextureAtlas heartAtlas;
    private Skeleton heartSkeleton;
    private AnimationState heartState;
    private AnimationStateData heartStateData;
    private SkeletonMeshRenderer sr;

    private boolean setAnima=false;

    public VictoryCut2(){
        TextureAtlas atlas=new TextureAtlas(Gdx.files.internal("endingScene/scene.atlas"));
        this.backImg = atlas.findRegion("bg");
        this.loadAnimation("images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", 1.0F);
        sr=new SkeletonMeshRenderer();
        this.heartSkeleton.setPosition(Settings.WIDTH*0.75F, AbstractDungeon.floorY);
        this.heartState.setAnimation(0, "idle", true);
        this.duration=3.0F;
        this.isDone=false;
        if(AbstractDungeon.player instanceof BATwinsCharacter){
            this.character3DHelper=((BATwinsCharacter) AbstractDungeon.player).get3DHelper();
        }
        this.buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false,false);
        this.charBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false,false);

        fire=new TextureRegion(ImageMaster.loadImage(ModHelper.makeImgPath("effect","fire")));
        fire.flip(false,true);
        this.started=false;
    }
    @Override
    public void update() {
        if(this.isDone){
            return;
        }
        if(!this.started){
            this.started=true;
            return;
        }
        this.duration-=Gdx.graphics.getDeltaTime();
        this.heartState.update(Gdx.graphics.getDeltaTime());
        this.heartState.apply(this.heartSkeleton);
        this.heartSkeleton.updateWorldTransform();
        if(!this.setAnima){
            this.character3DHelper.setMomoiAnimation(Character3DHelper.MomoiActionList.REACTION);
            this.character3DHelper.setMidoriAnimation(Character3DHelper.MidoriActionList.REACTION);
            this.setAnima=true;
        }
        this.character3DHelper.setPosition(this.character3DHelper.current_x+200.F*Settings.scale,this.character3DHelper.current_y);


        if(this.duration<0.0F){
            this.isDone=true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(endTexture!=null){
            sb.setColor(Color.ORANGE.cpy());
            sb.draw(endTexture, 0.0F, 0.0F, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT, 0.5F, 0.5F, 0.0F, 0, 0, Settings.WIDTH, Settings.HEIGHT, false, true);
            return;
        }

        sb.end();

        charBuffer.begin();
        Gdx.gl.glClearColor(0.0F,0.0F,0.0F,0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        character3DHelper.render(sb);
        sb.end();
        charBuffer.end();
        Texture texture=charBuffer.getColorBufferTexture();
        TextureRegion region=new TextureRegion(texture);
        region.flip(false,true);

        buffer.begin();
        Gdx.gl.glClearColor(0.0F,0.0F,0.0F,0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.setBlendFunction(770, 771);
        sb.setColor(Color.WHITE.cpy());
        sb.draw(this.backImg.getTexture(), this.backImg.offsetX * Settings.scale, this.backImg.offsetY * Settings.yScale,
                0.0F, 0.0F, this.backImg.packedWidth, this.backImg.packedHeight, Settings.scale, Settings.yScale, 0.0F,
                this.backImg.getRegionX(),this.backImg.getRegionY(),
                this.backImg.getRegionWidth(),this.backImg.getRegionHeight(),false,false);
        sb.draw(region,0,0,Settings.WIDTH/2.0F,Settings.HEIGHT/2.0F,Settings.WIDTH,Settings.HEIGHT,2.0F,2.0F,0.0F);
        if(this.isDone)
            sb.draw(fire,Settings.WIDTH*0.75F-fire.getRegionWidth()/2.0F,AbstractDungeon.floorY,fire.getRegionWidth(),fire.getRegionHeight(),fire.getRegionWidth(),fire.getRegionHeight(),1.0F,1.0F,0.0F);

        sb.end();
        CardCrawlGame.psb.begin();
        sr.draw(CardCrawlGame.psb,this.heartSkeleton);
        CardCrawlGame.psb.end();
        sb.begin();

        buffer.end();

        Texture texture1=buffer.getColorBufferTexture();
        if(this.isDone){
            endTexture=texture1;
        }
        sb.setColor(Color.WHITE.cpy());
        sb.draw(texture1, 0.0F, 0.0F, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT, 0.5F, 0.5F, 0.0F, 0, 0, Settings.WIDTH, Settings.HEIGHT, false, true);

    }

    protected void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        this.heartAtlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(this.heartAtlas);

        json.setScale((Settings.renderScale / scale)/5.0F);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.heartSkeleton = new Skeleton(skeletonData);
        this.heartSkeleton.setColor(Color.WHITE);
        this.heartStateData = new AnimationStateData(skeletonData);
        this.heartState = new AnimationState(this.heartStateData);
    }
}
