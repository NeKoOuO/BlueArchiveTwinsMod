package baModDeveloper.ui.panels;

import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;

public class BATwinsExperencePanel extends AbstractPanel {
    private static final String expPanelEmptyPath= ModHelper.makeImgPath("UI","experencePanel_empty");

    private static final Texture expPanelEmpty= ImageMaster.loadImage(expPanelEmptyPath);
    public BATwinsExperencePanel(float show_x, float show_y) {
        super(show_x, show_y, -480* Settings.scale, 200* Settings.scale, 200.0F * Settings.yScale, 12.0F * Settings.scale, null, true);
    }

    public void update(){

    }

    public void render(SpriteBatch sb){
//        Gdx.gl.glClearColor(0,0,0,1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.draw(expPanelEmpty,this.current_x,this.current_y,137,64);

    }
}
