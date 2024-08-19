package baModDeveloper.effect;

import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BATwinsLevelUpEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float distance;
    private float num;
    private Color startColor;
    private float originDuration;
    public BATwinsLevelUpEffect(float x,float y,float num,Color startColor){
        this.x=x;
        this.y=y;
        this.distance=20.0F* Settings.scale;
        this.num=num;
        this.startColor=startColor;
        this.originDuration=0.03F;
        this.duration=this.originDuration;
    }

    @Override
    public void update() {
        this.duration-= Gdx.graphics.getDeltaTime();
        if(this.duration<0.0F){
            if(this.num>=0){
                AbstractDungeon.topLevelEffectsQueue.add(new BATwinsLittleBlockEffect(this.startColor.cpy(),this.x,this.y,0.25F*Settings.scale,62.5F*Settings.scale,false));
                this.startColor= ModHelper.getBATwinsOtherColor(this.startColor.cpy());
                this.y+=this.distance;
                this.num--;
                this.duration=this.originDuration;
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
