package baModDeveloper.ui.panels.icons;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;

public class BATwinsMomoiEnergyOrbSmall extends AbstractCustomIcon {
    public static String NAME= ModHelper.makePath("momoiorb");
    private static Texture TEXTRUE= TextureLoader.getTexture(ModHelper.makeImgPath("UI/orb_small","momoienergy"));
    private static BATwinsMomoiEnergyOrbSmall smallorb;
    public BATwinsMomoiEnergyOrbSmall() {
        super(NAME, TEXTRUE);
    }

    public static BATwinsMomoiEnergyOrbSmall get(){
        if(smallorb==null){
            smallorb=new BATwinsMomoiEnergyOrbSmall();
        }
        return smallorb;
    }
}
