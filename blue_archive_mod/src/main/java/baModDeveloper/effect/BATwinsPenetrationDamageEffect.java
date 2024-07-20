package baModDeveloper.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactCurvyEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;

public class BATwinsPenetrationDamageEffect extends AbstractGameEffect {
    private float startX;
    private float endX;
    private float y;
    TextureAtlas.AtlasRegion img;
    private float currentX;
    private float stepX;
    private boolean playVfx;
    public BATwinsPenetrationDamageEffect(){
        this.startX=Float.MAX_VALUE;
        this.endX=Float.MIN_VALUE;
        this.img= ImageMaster.ATK_SLASH_H;
        this.y=Settings.HEIGHT/2.0F;

        for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!m.isDeadOrEscaped()){
                if(m.hb.x>endX){
                    endX=m.hb.x+this.img.getRegionWidth()/2.0F;
                }
                if(m.hb.x<startX){
                    startX=m.hb.x-this.img.getRegionWidth()/2.0F;
                }
                this.y=(m.hb.y+m.hb.height/2.0F+this.y)/2.0F;
            }
        }
        this.stepX=2000.0F;
        this.duration=(this.endX-this.startX)/this.stepX;
        this.currentX=this.startX;
        this.playVfx=false;
        this.scale = Settings.scale;
        this.rotation=0.0F;
    }

    @Override
    public void update() {
        this.duration-= Gdx.graphics.getDeltaTime();

        this.currentX+=this.stepX*Gdx.graphics.getDeltaTime();

        if(!this.playVfx){
            CardCrawlGame.sound.play("ATTACK_FAST");
            this.playVfx=true;
        }
        for(int i = 0; i < 10; ++i) {
            AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(this.currentX, y));
        }

        for(int i = 0; i < 3; ++i) {
            AbstractDungeon.effectsQueue.add(new DamageImpactCurvyEffect(this.currentX, y));
        }
        if(this.duration<0.0F){
            this.isDone=true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setColor(Color.WHITE.cpy());
        spriteBatch.draw(this.img,this.currentX,this.y,this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F,this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);

    }

    @Override
    public void dispose() {

    }
}
