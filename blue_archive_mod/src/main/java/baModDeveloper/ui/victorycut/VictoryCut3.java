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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class VictoryCut3 extends AbstractBATwinsVictoryCut implements Disposable {

    private Texture background;
    private Character3DHelper character3DHelper;
    private FrameBuffer buffer;
    private FrameBuffer charBuffer;

    private boolean setAnima=false;

    public VictoryCut3(){
        this.background= ImageMaster.loadImage(ModHelper.makeImgPath("UI/scence","Cut3background"));
        if(AbstractDungeon.player instanceof BATwinsCharacter){
            this.character3DHelper=((BATwinsCharacter) AbstractDungeon.player).get3DHelper();
        }
        this.buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false,false);
        this.charBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false,false);
        this.duration=3.0F;
        this.isDone=false;
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
        if(!this.setAnima){
            this.character3DHelper.clearCountdown();
            this.character3DHelper.setMomoiAnimation(Character3DHelper.MomoiActionList.PANIC);
            this.character3DHelper.setMidoriAnimation(Character3DHelper.MidoriActionList.PANIC);
            this.setAnima=true;
        }
        this.character3DHelper.setPosition(this.character3DHelper.current_x+200.F* Settings.scale,this.character3DHelper.current_y);
        if(this.duration<0.0F){
            this.isDone=true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(endTexture!=null){
            sb.setColor(Color.ORANGE.cpy());
            sb.draw(endTexture, Settings.WIDTH/2.0F, 0.0F, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT, 1.0F, 1.0F, 0.0F, 0, 0, Settings.WIDTH, Settings.HEIGHT, false, true);
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
        sb.draw(this.background,0.0F,0.0F,Settings.WIDTH/2.0F,Settings.HEIGHT);
        sb.draw(region,0,0,Settings.WIDTH/2.0F,Settings.HEIGHT/2.0F,Settings.WIDTH,Settings.HEIGHT,2.0F,2.0F,0.0F);
        sb.end();
        buffer.end();

        sb.begin();
        Texture texture1=buffer.getColorBufferTexture();
        if(this.isDone){
            endTexture=texture1;
        }
        sb.setColor(Color.WHITE.cpy());
        sb.draw(texture1, Settings.WIDTH/2.0F, 0.0F, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT, 1.0F, 1.0F, 0.0F, 0, 0, Settings.WIDTH, Settings.HEIGHT, false, true);

    }

    @Override
    public void dispose() {
        this.background.dispose();
        this.charBuffer.dispose();
        this.buffer.dispose();
    }
}
