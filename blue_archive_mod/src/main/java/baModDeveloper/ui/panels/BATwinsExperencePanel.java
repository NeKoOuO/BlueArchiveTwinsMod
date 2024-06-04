package baModDeveloper.ui.panels;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsExperiencePower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;

import static com.megacrit.cardcrawl.helpers.FontHelper.prepFont;

public class BATwinsExperencePanel extends AbstractPanel {
    private static final String expPanelEmptyPath = ModHelper.makeImgPath("UI", "experencePanel_empty");
    private static final String expPanelFullPath = ModHelper.makeImgPath("UI", "experencePanel_full");
    private static final Texture expPanelEmpty = ImageMaster.loadImage(expPanelEmptyPath);
    private static final Texture expPanelFull = ImageMaster.loadImage(expPanelFullPath);
    private static final float FontSize = 34.0F;
    private static final Color FONT_COLOR = new Color(1.0F, 1.0F, 0.86F, 1.0F);
    public static boolean LEVELUP = false;
    private static float ChangedFontSize = FontSize;
    private static BitmapFont expPanelFont = prepFont(FontSize, true);
    private static float WIDTH = 138.0F / 3 * Settings.scale;
    private static float HEIGHT = 64.0F / 3 * Settings.scale;
    private static float FontScale = 1.0F;
    private int expAmount;
    private int expLevel;
    private Color color;
    private Color fontColor;

    public BATwinsExperencePanel(float show_x, float show_y) {
        super(show_x, show_y, -480 * Settings.scale, 200 * Settings.scale, 200.0F * Settings.yScale, 12.0F * Settings.scale, null, true);

        this.expAmount = 0;
        this.expLevel = 0;

        this.color=Color.WHITE.cpy();
        this.color.a=1.0F;
        this.fontColor=FONT_COLOR;
    }

    public static void LevelUp() {
        LEVELUP = true;
        FontScale = 2.0F;
    }

    public void update(float hbAlpha) {
        if (this.target_x != this.current_x) {
            this.current_x = this.target_x;
        }
        if (this.target_y != this.current_y) {
            this.current_y = this.target_y;
        }
        if (AbstractDungeon.player instanceof BATwinsCharacter) {
            if (AbstractDungeon.player.hasPower(BATwinsExperiencePower.POWER_ID)) {
                BATwinsExperiencePower power = (BATwinsExperiencePower) AbstractDungeon.player.getPower(BATwinsExperiencePower.POWER_ID);
                this.expAmount = power.amount;
                this.expLevel = power.LEVEL;
            } else {
                this.expAmount = 0;
                this.expLevel = 0;
            }
        }
        if (FontScale != 1.0F) {
            FontScale = MathHelper.scaleLerpSnap(FontScale, 1.0F);
        }

        this.color.a=hbAlpha;
        this.fontColor.a=hbAlpha;
    }

    public void render(SpriteBatch sb) {
//        Gdx.gl.glClearColor(0,0,0,1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setColor(this.color);
        for (int i = 0; i < this.expAmount; i++) {
            sb.draw(expPanelFull, this.current_x, this.current_y + i * HEIGHT, WIDTH, HEIGHT);
        }
        for (int i = this.expAmount; i < 10; i++) {
            sb.draw(expPanelEmpty, this.current_x, this.current_y + i * HEIGHT, WIDTH, HEIGHT);
        }
        FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString(this.expAmount), this.current_x, this.current_y + 10 * HEIGHT, fontColor);
        expPanelFont.getData().setScale(FontScale);
        FontHelper.renderFontCentered(sb, expPanelFont, "LV:" + this.expLevel, this.current_x + 20.0F * Settings.scale, this.current_y + 11 * HEIGHT, fontColor);
    }

    @Override
    public void show() {
        super.show();
    }

    public void updatePosition(float x, float y) {
        this.target_x = x;
        this.target_y = y;
    }


}
