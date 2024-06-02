package baModDeveloper.action;

import baModDeveloper.BATwinsMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class BATwinsSwordOfLightAction extends AbstractGameAction {
    private static ShaderProgram shaderProgram;
    private boolean playVfx;
    public BATwinsSwordOfLightAction(){
        this.duration=1.0F;
        if(shaderProgram==null){
            shaderProgram=new ShaderProgram(Gdx.files.internal("baModResources/shader/laser/vertex.glsl"),Gdx.files.internal("baModResources/shader/laser/fragment.glsl"));
            if(!shaderProgram.isCompiled()){
                throw new RuntimeException(shaderProgram.getLog());
            }
        }
        this.playVfx=false;
    }
    @Override
    public void update() {
        tickDuration();
        if(!this.playVfx){
            CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT");
            this.playVfx=true;
        }
        BATwinsMod.postProcessQueue.add(((spriteBatch, textureRegion) -> {
            spriteBatch.setShader(shaderProgram);
            shaderProgram.setUniformf("iResolution", Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            shaderProgram.setUniformf("iTime",1.0F-this.duration);
            spriteBatch.draw(textureRegion,0.0F,0.0F);
            spriteBatch.setShader(null);
        }));
    }
}
