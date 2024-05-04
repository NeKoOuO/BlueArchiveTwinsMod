package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

public class BATwinsRetroGame extends CustomRelic {
    public static final String ID= ModHelper.makePath("RetroGame");
    private static final Texture texture= TextureLoader.getTexture(ModHelper.makeImgPath("relic","RetroGame"));
    private static final Texture outline=TextureLoader.getTexture(ModHelper.makeImgPath("relic","RetroGame_p"));
    private static final RelicTier type=RelicTier.COMMON;
    public BATwinsRetroGame() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }
}
