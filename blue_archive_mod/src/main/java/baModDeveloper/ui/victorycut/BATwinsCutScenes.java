package baModDeveloper.ui.victorycut;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.VictoryScreen;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BATwinsCutScenes extends Cutscene {
    private List<AbstractBATwinsVictoryCut> cuts;
    private Field bgImgField;
    private Field bgColorField;
    private Texture blackBg;
    private Texture twinsBg;
    private boolean isDone=false;
    private Color bgColor;
    private float duration;
    public BATwinsCutScenes() {
        super(BATwinsCharacter.Enums.BATwins);
        cuts=new ArrayList<>();
        cuts.add(new VictoryCut1());
        cuts.add(new VictoryCut2());
        cuts.add(new VictoryCut3());
        try {
            this.bgImgField=Cutscene.class.getDeclaredField("bgImg");
            this.bgImgField.setAccessible(true);
            this.bgColorField=Cutscene.class.getDeclaredField("bgColor");
            this.bgColorField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            ModHelper.getLogger().warn("Get bgImg Error!");
        }

        this.blackBg= ImageMaster.loadImage(ModHelper.makeImgPath("UI/scence","BlackBg"));
        this.twinsBg=ImageMaster.loadImage(ModHelper.makeImgPath("UI/scence","TwinsBg"));
        this.bgColor=Color.WHITE.cpy();

        this.duration=10.0F;
    }

    @Override
    public void update() {
        this.duration-= Gdx.graphics.getDeltaTime();
        for(AbstractBATwinsVictoryCut cut:this.cuts){
            cut.update();
            if(!cut.isDone){
                break;
            }
        }
        if(this.duration<0.0F){
            this.isDone=true;
        }
        this.updateIfDone();
        this.updateSceneChange();
    }

    @Override
    public void render(SpriteBatch sb) {
//        for(AbstractBATwinsVictoryCut cut:this.cuts){
//            cut.render(sb);
//            if(!cut.isDone){
//                break;
//            }
//        }
    }


    @Override
    public void renderAbove(SpriteBatch sb) {
        sb.setColor(this.bgColor);
        sb.draw(this.blackBg,0.0F,0.0F,Settings.WIDTH,Settings.HEIGHT);
        if(!this.isDone){
            for(AbstractBATwinsVictoryCut cut:this.cuts){
                cut.render(sb);
                if(!cut.isDone){
                    break;
                }
            }
        }

        sb.setColor(this.bgColor);
        sb.draw(this.twinsBg,0.0F,0.0F,Settings.WIDTH,Settings.HEIGHT);
        //        if(this.bgImgField!=null&&this.bgColorField!=null){
//            try {
//                if (this.bgImgField.get(this) != null) {
//                    sb.setColor((Color) bgColorField.get(this));
//                    this.renderImg(sb, (Texture) this.bgImgField.get(this));
//                }
//            } catch (IllegalAccessException ignore) {
//            }
//        }

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void renderImg(SpriteBatch sb, Texture img) {
        if (Settings.isSixteenByTen) {
            sb.draw(img, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        } else {
            sb.draw(img, 0.0F, -50.0F * Settings.scale, (float)Settings.WIDTH, (float)Settings.HEIGHT + 110.0F * Settings.scale);
        }

    }

    private void updateIfDone(){
        if(this.isDone){
            this.dispose();
            this.bgColor.a = 0.0F;
            GameCursor.hidden = false;
            AbstractDungeon.victoryScreen = new VictoryScreen((MonsterGroup)null);
        }
    }

    private void updateSceneChange() {
        if ((InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed())) {
            for(AbstractBATwinsVictoryCut cut:cuts){
                if(!cut.isDone){
                    cut.isDone=true;
                    this.duration-=cut.duration;
                    break;
                }
            }
        }
    }
}
