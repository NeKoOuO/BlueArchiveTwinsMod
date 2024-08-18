package baModDeveloper.ui.victorycut;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.Character3DHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactCurvyEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VictoryCut1 extends AbstractBATwinsVictoryCut implements Disposable {
    private TextureAtlas.AtlasRegion backImg;
    private Character3DHelper character3DHelper;
    private FrameBuffer buffer;
    private FrameBuffer charBuffer;

    private TextureAtlas heartAtlas;
    private Skeleton heartSkeleton;
    private AnimationState heartState;
    private AnimationStateData heartStateData;
    private int effectCount;
    private SkeletonMeshRenderer sr;

    private boolean setAnima=false;

    private List<AbstractGameEffect> effectList;
    private Random random;
    public VictoryCut1(){
        TextureAtlas atlas=new TextureAtlas(Gdx.files.internal("endingScene/scene.atlas"));
        this.backImg = atlas.findRegion("bg");

        this.loadAnimation("images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", 1.0F);
        sr=new SkeletonMeshRenderer();
        this.heartSkeleton.setPosition(Settings.WIDTH*0.75F,AbstractDungeon.floorY);
        this.heartState.setAnimation(0, "idle", true);


        this.duration=3.0F;
        this.isDone=false;
        if(AbstractDungeon.player instanceof BATwinsCharacter){
            this.character3DHelper=((BATwinsCharacter) AbstractDungeon.player).get3DHelper();
        }
        this.buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false,false);
        this.charBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false,false);

        effectList=new ArrayList<>();
        this.effectCount=0;
        this.random=new Random();
        this.started=false;
    }

    @Override
    public void dispose() {
        this.buffer.dispose();
        this.charBuffer.dispose();
        this.heartAtlas.dispose();
        for(AbstractGameEffect effect:effectList){
            effect.dispose();
        }
        effectList.clear();
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
            this.character3DHelper.setMomoiAnimation(Character3DHelper.MomoiActionList.CONTINUEATTACK);
            this.character3DHelper.setMidoriAnimation(Character3DHelper.MidoriActionList.CONTINUEATTACK);
            this.setAnima=true;
        }

        float effectOffsetX=(this.random.nextFloat()*2.0F-1.0F)*Settings.WIDTH*0.1F;
        float effectOffsetY=(this.random.nextFloat()*2.0F)*Settings.HEIGHT*0.2F;
        if(effectCount%3==0){
            effectList.add(new FlashAtkImgEffect(this.heartSkeleton.getX()+effectOffsetX,this.heartSkeleton.getY()+effectOffsetY, AbstractGameAction.AttackEffect.BLUNT_LIGHT,true));
        }else{
            effectList.add(new DamageImpactLineEffect(this.heartSkeleton.getX()+effectOffsetX,this.heartSkeleton.getY()+effectOffsetY));
            effectList.add(new DamageImpactCurvyEffect(this.heartSkeleton.getX()+effectOffsetX,this.heartSkeleton.getY()+effectOffsetY));
        }
        effectCount++;
        for(AbstractGameEffect effect:effectList){
            effect.update();
        }
        if(this.duration<0.0F){
            this.isDone=true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(endTexture!=null){
            sb.setColor(Color.ORANGE.cpy());
            sb.draw(endTexture, 0.0F, Settings.HEIGHT / 2.0F, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT, 0.5F, 0.5F, 0.0F, 0, 0, Settings.WIDTH, Settings.HEIGHT, false, true);
            return;
        }
        sb.end();

        buffer.begin();
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        creatFrameBuffer(sb);
        buffer.end();


        sb.begin();
        Texture texture = buffer.getColorBufferTexture();
        if(this.isDone){
            endTexture=texture;
        }
        sb.setColor(Color.WHITE.cpy());
        sb.draw(texture, 0.0F, Settings.HEIGHT / 2.0F, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT, 0.5F, 0.5F, 0.0F, 0, 0, Settings.WIDTH, Settings.HEIGHT, false, true);
    }

    public void creatFrameBuffer(SpriteBatch sb) {
        sb.begin();
        sb.setBlendFunction(770, 771);
        sb.setColor(Color.WHITE.cpy());
        sb.draw(this.backImg.getTexture(), this.backImg.offsetX * Settings.scale, this.backImg.offsetY * Settings.yScale,
                0.0F, 0.0F, this.backImg.packedWidth, this.backImg.packedHeight, Settings.scale, Settings.yScale, 0.0F,
                this.backImg.getRegionX(),this.backImg.getRegionY(),
                this.backImg.getRegionWidth(),this.backImg.getRegionHeight(),false,false);
        this.character3DHelper.render(sb);
        sb.end();
        CardCrawlGame.psb.begin();
        sr.draw(CardCrawlGame.psb, this.heartSkeleton);
        CardCrawlGame.psb.end();
        sb.begin();

        for(AbstractGameEffect effect:effectList){
            if(!effect.isDone)
                effect.render(sb);
        }
        sb.end();


//        sb.begin();
    }





    protected void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        this.heartAtlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(this.heartAtlas);

        json.setScale(Settings.renderScale / scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.heartSkeleton = new Skeleton(skeletonData);
        this.heartSkeleton.setColor(Color.WHITE);
        this.heartStateData = new AnimationStateData(skeletonData);
        this.heartState = new AnimationState(this.heartStateData);
    }
}
