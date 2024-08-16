package baModDeveloper.ui.victorycut;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.Cutscene;

import java.lang.reflect.Field;

public class BATwinsCutScenes extends Cutscene {
    private AbstractBATwinsVictoryCut cut;

    private Field bgImgField;
    private Field bgColorField;
    public BATwinsCutScenes() {
        super(BATwinsCharacter.Enums.BATwins);
        this.cut=new VictoryCut1();
        try {
            this.bgImgField=Cutscene.class.getDeclaredField("bgImg");
            this.bgImgField.setAccessible(true);
            this.bgColorField=Cutscene.class.getDeclaredField("bgColor");
            this.bgColorField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            ModHelper.getLogger().warn("Get bgImg Error!");
        }
    }

    @Override
    public void update() {
        super.update();
        this.cut.update();
    }

    @Override
    public void render(SpriteBatch sb) {
    }


    @Override
    public void renderAbove(SpriteBatch sb) {
        this.cut.render(sb);
//        if(this.bgImgField!=null&&this.bgColorField!=null){
//            try {
//                if (this.bgImgField.get(this) != null) {
//                    sb.setColor((Color) bgColorField.get(this));
//                    this.renderImg(sb, (Texture) this.bgImgField.get(this));
//                }
//            } catch (IllegalAccessException ignore) {
//            }
//        }

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void renderImg(SpriteBatch sb, Texture img) {
        if (Settings.isSixteenByTen) {
            sb.draw(img, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        } else {
            sb.draw(img, 0.0F, -50.0F * Settings.scale, (float)Settings.WIDTH, (float)Settings.HEIGHT + 110.0F * Settings.scale);
        }

    }
}
