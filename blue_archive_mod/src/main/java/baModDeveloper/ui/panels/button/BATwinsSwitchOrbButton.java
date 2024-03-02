package baModDeveloper.ui.panels.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;

public class BATwinsSwitchOrbButton {

    private Hitbox hb = new Hitbox(0.0F, 0.0F, 230.F * Settings.scale, 110F * Settings.scale);
    public boolean isHidden = true;
    public boolean enable = false;
    private boolean isDisable = false;
    private static final float SHOW_X = 198.0F * Settings.xScale;
    private static final float SHOW_Y = 190.0F * Settings.yScale;

    public BATwinsSwitchOrbButton() {
    }

    public int getKeyCode() {
        return 0;
    }

    public void render(SpriteBatch sb) {

    }

}
