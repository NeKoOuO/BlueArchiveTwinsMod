package baModDeveloper.relic;

import baModDeveloper.BATwinsMod;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import java.awt.*;
import java.lang.reflect.Field;

public class BATwinsTelescope extends CustomRelic {
    public static final String ID= ModHelper.makePath("Telescope");
    private static final Texture texture=TextureLoader.getTexture(ModHelper.makeImgPath("relic","MidorisGameConsole"));
    private static final Texture outline= TextureLoader.getTexture(ModHelper.makeImgPath("relic","MidorisGameConsole_p"));
    private static final RelicTier type=RelicTier.BOSS;
    private static ShaderProgram shaderProgram;
    private static Color startColor=Color.WHITE.cpy();
    private static Color endColor=Color.BLUE.cpy();
    private static Color color=startColor;
    private static float rotate=0.0F;
    private static int rgb=0;
    private Field field;

    public BATwinsTelescope() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);
//        if (shaderProgram == null) {
//            shaderProgram = new ShaderProgram(Gdx.files.internal("baModResources/shader/telescope/vertex.glsl"), Gdx.files.internal("baModResources/shader/telescope/fragment.glsl"));
//            if (!shaderProgram.isCompiled()) {
//                throw new RuntimeException(shaderProgram.getLog());
//            }
//        }
        Class<AbstractCard> abstractCardClass = AbstractCard.class;
        try {
            field=abstractCardClass.getDeclaredField("textColor");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {

    }

    @Override
    public void update() {
        super.update();

        if(AbstractDungeon.player!=null){
            rotate+=0.01F;
            color=blendColors(startColor,endColor,rotate);
            AbstractDungeon.player.hand.group.forEach(card -> {
                try {
                    field.set(card,color);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            if(rotate>=1.0F){
                startColor=color;
                rgb++;
                rotate=0.0F;
                endColor=new Color(rgb%2,rgb%3,rgb%5,1.0F);
            }
        }
    }

    private Color blendColors(Color c1, Color c2, float ratio) {
        float r = (c1.r * (1 - ratio) + c2.r * ratio);
        float g = (c1.g * (1 - ratio) + c2.g * ratio);
        float b = (c1.b * (1 - ratio) + c2.b * ratio);
        return new Color(r, g, b,1.0F);
    }

}
