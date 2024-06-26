package baModDeveloper.relic;

import baModDeveloper.BATwinsMod;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class BATwinsTelescope extends CustomRelic {
    public static final String ID= ModHelper.makePath("Telescope");
    private static final Texture texture=TextureLoader.getTexture(ModHelper.makeImgPath("relic","MidorisGameConsole"));
    private static final Texture outline= TextureLoader.getTexture(ModHelper.makeImgPath("relic","MidorisGameConsole_p"));
    private static final RelicTier type=RelicTier.BOSS;
    private static ShaderProgram shaderProgram;

    public BATwinsTelescope() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);
        if (shaderProgram == null) {
            shaderProgram = new ShaderProgram(Gdx.files.internal("baModResources/shader/telescope/vertex.glsl"), Gdx.files.internal("baModResources/shader/telescope/fragment.glsl"));
            if (!shaderProgram.isCompiled()) {
                throw new RuntimeException(shaderProgram.getLog());
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {

    }

    @Override
    public void update() {
        super.update();
        BATwinsMod.postProcessQueue.add(this::updateShader);
    }

    private void updateShader(SpriteBatch spriteBatch, TextureRegion textureRegion) {
        spriteBatch.setShader(shaderProgram);
        shaderProgram.setUniformf("iResolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shaderProgram.setUniformf("iMouse", InputHelper.mX,InputHelper.mY);
        shaderProgram.setUniformf("redius",200.0F);
        shaderProgram.setUniformf("strength",2.0F);
        spriteBatch.draw(textureRegion, 0.0F, 0.0F);
        spriteBatch.setShader(null);
    }
}
