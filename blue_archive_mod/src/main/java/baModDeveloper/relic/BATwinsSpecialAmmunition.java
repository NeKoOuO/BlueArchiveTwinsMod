package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

public class BATwinsSpecialAmmunition extends CustomRelic {
    public static final String ID= ModHelper.makePath("SpecialAmmunition");
    private static final Texture texture= TextureLoader.getTexture(ModHelper.makeImgPath("relic","SpecialAmmunition"));
    private static final Texture outline=TextureLoader.getTexture(ModHelper.makeImgPath("relic","SpecialAmmunition"));
    private static final RelicTier type=RelicTier.COMMON;
    public BATwinsSpecialAmmunition() {
        super(ID, texture, outline,type, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


}
