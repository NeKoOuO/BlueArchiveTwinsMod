package baModDeveloper.effect;

import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BATwinsSlashEffect extends AbstractGameEffect {
    Texture img;
    private float x, y;

    public BATwinsSlashEffect(Color color) {
        this.color = color.cpy();
        this.color.a = 1.0F;
        this.img = ImageMaster.loadImage(ModHelper.makeImgPath("effect", "vfx"));
        this.startingDuration = 0.2F;
        this.duration = 0.2F;
        this.x = Settings.WIDTH / 2.0F - 150 * Settings.scale;
        this.y = AbstractDungeon.floorY;
        this.y += MathUtils.random(0, 300 * Settings.scale);
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration <= 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setColor(this.color);
        spriteBatch.draw(this.img, this.x, this.y, Settings.WIDTH / 2.0F, this.img.getHeight() / 2.0F);
    }

    @Override
    public void dispose() {
        this.img.dispose();
    }
}
