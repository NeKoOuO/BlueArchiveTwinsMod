package baModDeveloper.ui.victorycut;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractBATwinsVictoryCut {

    public float duration;
    public boolean isDone;
    public Texture endTexture;
    public boolean started;


    public abstract void update();

    public abstract void render(SpriteBatch sb);
}
