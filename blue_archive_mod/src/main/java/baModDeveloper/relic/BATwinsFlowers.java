package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Field;

public class BATwinsFlowers extends CustomRelic {
    public static final String ID= ModHelper.makePath("Flowers");
    private static final Texture texture=TextureLoader.getTexture(ModHelper.makeImgPath("relic","Flowers"));
    private static final Texture outline= TextureLoader.getTexture(ModHelper.makeImgPath("relic","Flowers_p"));
    private static final RelicTier type=RelicTier.SHOP;
    private static Color startColor=Color.WHITE.cpy();
    private static Color endColor=Color.BLUE.cpy();
    private static Color color=startColor;
    private static float rotate=0.0F;
    private static int rgb=0;
    private Field field;

    public BATwinsFlowers() {
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
        return DESCRIPTIONS[0];
    }



    @Override
    public void atTurnStart() {
        boolean gainEnergy=AbstractDungeon.player.drawPile.group.stream().allMatch(card -> {
            try {
                return !field.get(card).equals(Settings.CREAM_COLOR);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        gainEnergy=gainEnergy&&AbstractDungeon.player.discardPile.group.stream().allMatch(card -> {
            try {
                return !field.get(card).equals(Settings.CREAM_COLOR);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        if(gainEnergy){
            this.flash();
            addToBot(new DrawCardAction(1));
        }
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
