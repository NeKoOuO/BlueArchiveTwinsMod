package baModDeveloper.ui.panels;

import baModDeveloper.BATwinsMod;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.io.IOException;

public class BATwinsCharacterSelectScreen implements ISubscriber {

    public static BATwinsCharacterSelectScreen instance;

    private Hitbox checkbox;
    private boolean checked;
    private boolean hovered;
    private boolean shown;
    private float current_x,current_y;
    private static final UIStrings uiStrings= CardCrawlGame.languagePack.getUIString(ModHelper.makePath("ModeSelectionPrompt"));
    private SpireConfig spireConfig;

    public BATwinsCharacterSelectScreen() throws IOException {
        this.checkbox=new Hitbox(300.0F* Settings.scale,50.0F*Settings.scale);
        this.checked= BATwinsMod.Enable3D;
        this.shown=false;
        this.hovered=false;
        this.current_x=Settings.WIDTH/6.0F;
        this.current_y=Settings.HEIGHT/3.0F;
        this.checkbox.move(this.current_x,this.current_y);

        spireConfig= new SpireConfig("BATwinsMod", "Common");

    }

    public void update() {
        if(CardCrawlGame.chosenCharacter== BATwinsCharacter.Enums.BATwins){
            this.shown=true;
            this.checkbox.update();
            if(this.checkbox.clicked){
                this.checkbox.clicked=false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.checked=!checked;
                BATwinsMod.Enable3D=this.checked;
                spireConfig.setBool(ModHelper.makePath("Enable3D"),BATwinsMod.Enable3D);
                try{
                    spireConfig.save();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            if(InputHelper.justClickedLeft){
                if(this.checkbox.hovered){
                    this.checkbox.clickStarted=true;
                }
            }
            this.hovered=this.checkbox.hovered;
        }else {
            this.shown=false;
        }
    }

    public void render(SpriteBatch sb){
        if(this.shown){
            sb.setColor(1.0F,1.0F,1.0F,1.0F);
            sb.draw(ImageMaster.OPTION_TOGGLE,this.current_x-150.0F*Settings.scale,this.current_y-ImageMaster.OPTION_TOGGLE.getHeight()/2.0F);
            if(this.checked){
                sb.draw(ImageMaster.OPTION_TOGGLE_ON,this.current_x-150.0F*Settings.scale,this.current_y-ImageMaster.OPTION_TOGGLE.getHeight()/2.0F);
            }
            FontHelper.renderFontCentered(sb, FontHelper.menuBannerFont,uiStrings.TEXT[0],this.current_x+30*Settings.scale,this.current_y);
            if(this.hovered){
                this.renderTip(sb);
            }
            this.checkbox.render(sb);
        }
    }

    private void renderTip(SpriteBatch sb) {
        TipHelper.renderGenericTip(this.current_x,this.current_y,uiStrings.TEXT[1],uiStrings.TEXT[2]);
    }

    public static BATwinsCharacterSelectScreen getInstance(){
        if(instance==null){
            try{
                instance=new BATwinsCharacterSelectScreen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
}
