package baModDeveloper.patch;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

public class BATwinsMainMenuPatch {
    private static BATwinsSpecialAchievement achievement=new BATwinsSpecialAchievement();
    @SuppressWarnings("unused")
    @SpirePatch(clz = MainMenuScreen.class,method = "update")
    public static class updatePatch{
        @SpirePostfixPatch
        public static void postfixPatch(MainMenuScreen _instance){
            if(ModHelper.ENABLE_DLC){
                if(!achievement.shown){
                    achievement.delayShow(3.0F);
                }
                achievement.update();
            }

        }
    }
    @SuppressWarnings("unused")
    @SpirePatch(clz = MainMenuScreen.class,method = "render")
    public static class renderPatch{
        @SpirePostfixPatch
        public static void postfixPatch(MainMenuScreen _instance,SpriteBatch sb){
            if(ModHelper.ENABLE_DLC){
                achievement.render(sb);
            }
        }
    }


    //成就弹框
    public static class BATwinsSpecialAchievement{
        private static Texture bgImg= TextureLoader.getTexture(ModHelper.makeImgPath("UI","achievement"));
        private static UIStrings achieveStrings= CardCrawlGame.languagePack.getUIString(ModHelper.makePath("Achieve"));
        private float currentX,currentY;
        private boolean show;
        private float showX,showY;
        private float hideY;
        private float speed=40.0F;
        private float waitDuration =1.0F;
        private boolean shown=false;
        private float duration=4.0F;
        public BATwinsSpecialAchievement(){
            this.show=false;
            this.currentX=Settings.WIDTH-bgImg.getWidth();
            this.currentY=-bgImg.getHeight();

            this.showX=this.currentX;
            this.showY=0;
            this.hideY=this.currentY;
        }
        public void update(){
            if(this.show){
                if(this.currentY<this.showY){
                    this.currentY+=this.speed* Gdx.graphics.getDeltaTime();
                }else{
                    this.currentY=this.showY;
                    this.duration-=Gdx.graphics.getDeltaTime();
                    if(this.duration<0.0F){
                        this.hide();
                    }
                }
            }else{
                if(this.currentY>this.hideY){
                    this.currentY-=this.speed*Gdx.graphics.getDeltaTime();
                }else{
                    this.currentY=this.hideY;
                }
            }
            if(this.waitDuration >0.0F){
                this.waitDuration -=Gdx.graphics.getDeltaTime();
                if(this.waitDuration <0.0F){
                    this.show();
                }
            }
        }

        private void hide() {
            this.show=false;
        }

        public void render(SpriteBatch sb){
            sb.setColor(Color.WHITE.cpy());
            sb.draw(bgImg, Settings.WIDTH-bgImg.getWidth(),this.currentY,bgImg.getWidth(),bgImg.getHeight());
            FontHelper.renderFontLeft(sb,FontHelper.topPanelInfoFont,achieveStrings.TEXT[0],
                    this.currentX+bgImg.getWidth()*0.25F,this.currentY+bgImg.getHeight()*0.7F,Color.WHITE.cpy());
            FontHelper.renderFontLeft(sb,FontHelper.cardDescFont_L,achieveStrings.TEXT[1],
                    this.currentX+bgImg.getWidth()*0.25F,this.currentY+bgImg.getHeight()*0.3F,Color.WHITE.cpy());
        }

        public void show(){
            this.duration=2.0F;
            this.show=true;
            CardCrawlGame.sound.play(ModHelper.makePath("achievement"));
        }
        public void delayShow(float duration){
            this.waitDuration =duration;
            this.shown=true;
        }
    }
}
