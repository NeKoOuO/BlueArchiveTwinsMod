package baModDeveloper.effect;

import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;

public class BATwinsColorfulAboveTextEffect extends TextAboveCreatureEffect {
    private float changeColorDuration;
    private Color orginCOlor;
    private float y;
    private float offsetY;

    public BATwinsColorfulAboveTextEffect(float x, float y, String msg, Color targetColor) {
        super(x, y, msg, targetColor);
        this.changeColorDuration=0.5F;
        this.orginCOlor=targetColor.cpy();
    }

    @Override
    public void update() {
        super.update();
        this.changeColorDuration-= Gdx.graphics.getDeltaTime();
        this.color=this.orginCOlor.cpy();
        if(this.changeColorDuration<0.0F){
            this.color= ModHelper.getBATwinsOtherColor(this.orginCOlor).cpy();
            this.changeColorDuration=0.5F;
            this.orginCOlor=this.color.cpy();
        }
    }

}
