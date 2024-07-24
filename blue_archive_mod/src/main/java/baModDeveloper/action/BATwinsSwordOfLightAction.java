package baModDeveloper.action;

import baModDeveloper.BATwinsMod;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class BATwinsSwordOfLightAction extends AbstractGameAction {
    private static ShaderProgram shaderProgram;
    private boolean playVfx;

    public BATwinsSwordOfLightAction() {
        this.duration = 3.3F;
        if (shaderProgram == null) {
            shaderProgram = new ShaderProgram(Gdx.files.internal("baModResources/shader/laser/vertex.glsl"), Gdx.files.internal("baModResources/shader/laser/fragment.glsl"));
            if (!shaderProgram.isCompiled()) {
                ModHelper.getLogger().warn("Shader Program not compiled!");
            }
        }
        this.playVfx = false;
    }

    @Override
    public void update() {
        if(!shaderProgram.isCompiled()){
            this.isDone=true;
            return;
        }
        if (this.duration == 3.3F) {
            CardCrawlGame.sound.play(ModHelper.makePath("Alice"));
        }
        if (this.duration < 1.0F) {
            if (!this.playVfx) {
                CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT");
                this.playVfx = true;
            }
            BATwinsMod.postProcessQueue.add(((spriteBatch, textureRegion) -> {
                spriteBatch.setShader(shaderProgram);
                shaderProgram.setUniformf("iResolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                shaderProgram.setUniformf("iTime", 1.0F - this.duration);
                spriteBatch.draw(textureRegion, 0.0F, 0.0F);
                spriteBatch.setShader(null);
            }));
        }
        tickDuration();

    }
}
