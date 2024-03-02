package baModDeveloper.ui.panels.icons;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;

public class BATwinsMidoriEnergyOrbSmall extends AbstractCustomIcon {
    public static String NAME= ModHelper.makePath("midoriorb");
    private static Texture TEXTRUE= TextureLoader.getTexture(ModHelper.makeImgPath("UI/orb_small","midorienergy"));
    private static BATwinsMidoriEnergyOrbSmall smallorb;
    public BATwinsMidoriEnergyOrbSmall() {
        super(NAME, TEXTRUE);
    }

    public static BATwinsMidoriEnergyOrbSmall get(){
        if(smallorb==null){
            smallorb=new BATwinsMidoriEnergyOrbSmall();
        }
        return smallorb;
    }
    public static Texture getTEXTRUE(){
        return TEXTRUE;
    }
}
