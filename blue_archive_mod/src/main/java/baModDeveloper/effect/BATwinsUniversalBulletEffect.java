package baModDeveloper.effect;

import baModDeveloper.cards.BATwinsUniversalBullet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.CardTrailEffect;

import java.util.ArrayList;

public class BATwinsUniversalBulletEffect extends AbstractGameEffect {
    private BATwinsUniversalBullet bullet;
    private AbstractCard targetCard;
    ArrayList<CardTrailEffect> effects;
//    private CardTrailEffect effect;
    private static TextureAtlas.AtlasRegion img = null;
    private float x,y;
    private float stepX,stepY;
    public BATwinsUniversalBulletEffect(BATwinsUniversalBullet bullet, AbstractCard targetCard){
        this.bullet=bullet;
        this.targetCard=targetCard;
        this.x=Settings.WIDTH/2.0F;
        this.y=Settings.HEIGHT/2.0F;

        effects=new ArrayList<>();

        this.duration=0.5F;
        this.stepX=(targetCard.current_x-this.x)/this.duration;
        this.stepY=(targetCard.current_y-this.y)/this.duration;
    }

    @Override
    public void update() {
        this.duration-= Gdx.graphics.getDeltaTime();
        this.stepX=(targetCard.current_x-this.x)/this.duration;
        this.stepY=(targetCard.current_y-this.y)/this.duration;
        this.x+=stepX*Gdx.graphics.getDeltaTime();
        this.y+=stepY*Gdx.graphics.getDeltaTime();



        for(int i=0;i<30;i++){
            CardTrailEffect effect=new CardTrailEffect();
            effect.init(this.x,this.y);
            this.effects.add(effect);

        }

        for(CardTrailEffect effect1:this.effects){
            if(effect1.duration>0.25F)
                effect1.update();
        }
        if(this.duration<0.0F){
            this.isDone=true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        for(CardTrailEffect effect:this.effects){
            if(effect.duration>0.25F)
                effect.render(sb);
        }
    }

    @Override
    public void dispose() {

    }

}
