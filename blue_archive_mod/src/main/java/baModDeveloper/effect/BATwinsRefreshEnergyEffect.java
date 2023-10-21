package baModDeveloper.effect;

import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.RefreshEnergyEffect;


public class BATwinsRefreshEnergyEffect extends RefreshEnergyEffect {
    public static final float EFFECT_FUR=0.4f;
    private float scale;
    private Color color;
    private TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private BATwinsEnergyPanel.EnergyType type;
    public BATwinsRefreshEnergyEffect(BATwinsEnergyPanel.EnergyType type){
        this.scale = Settings.scale / 1.2F;
        this.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.img = ImageMaster.WHITE_RING;
        this.x = 198.0F * Settings.scale - (float)this.img.packedWidth / 2.0F;
        this.y = 190.0F * Settings.scale - (float)this.img.packedHeight / 2.0F;
        this.duration = 0.4F;
        this.type=type;
    }
    @Override
    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();
        this.scale *= 1.0F + Gdx.graphics.getDeltaTime() * 2.5F;
        this.color.a = Interpolation.fade.apply(0.0F, 0.75F, this.duration / 0.4F);
        if (this.color.a < 0.0F) {
            this.color.a = 0.0F;
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb){
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        if(type== BATwinsEnergyPanel.EnergyType.MOMOI){
            sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 1.5F, this.scale * 1.5F, this.rotation);
        } else if (type== BATwinsEnergyPanel.EnergyType.MIDORI) {
            sb.draw(this.img, this.x-64.0F, this.y+64.0F, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 1.5F, this.scale * 1.5F, this.rotation);
        } else if (type== BATwinsEnergyPanel.EnergyType.ALL) {
            sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 1.5F, this.scale * 1.5F, this.rotation);
            sb.draw(this.img, this.x-64.0F, this.y+64.0F, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 1.5F, this.scale * 1.5F, this.rotation);
        }
        sb.setBlendFunction(770, 771);

    }
}
