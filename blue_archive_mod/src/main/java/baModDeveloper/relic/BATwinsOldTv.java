package baModDeveloper.relic;

import baModDeveloper.BATwinsMod;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsOldTv extends CustomRelic {
    public static final String ID= ModHelper.makePath("OldTv");
    private static final Texture texture=TextureLoader.getTexture(ModHelper.makeImgPath("relic","OldTv"));
    private static final Texture outline= TextureLoader.getTexture(ModHelper.makeImgPath("relic","OldTv_p"));
    private static final RelicTier type=RelicTier.BOSS;
    private static ShaderProgram shaderProgram;
    private static float shaderTime=0.0F;
    public BATwinsOldTv() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);
        if(shaderProgram==null){
            shaderProgram=new ShaderProgram(Gdx.files.internal("baModResources/shader/oldtv/vertex.glsl"),Gdx.files.internal("baModResources/shader/oldtv/fragment.glsl"));
            if(!shaderProgram.isCompiled()){
                throw new RuntimeException(shaderProgram.getLog());
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void update() {
        super.update();
        if(AbstractDungeon.player!=null&&AbstractDungeon.player.relics.contains(this)){
            shaderTime+=Gdx.graphics.getDeltaTime();
            BATwinsMod.postProcessQueue.add(((spriteBatch, textureRegion) -> {
                spriteBatch.setShader(shaderProgram);
                shaderProgram.setUniformf("iResolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                shaderProgram.setUniformf("iTime",shaderTime );
                spriteBatch.draw(textureRegion, 0.0F, 0.0F);
                spriteBatch.setShader(null);
            }));
        }

    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }
}
