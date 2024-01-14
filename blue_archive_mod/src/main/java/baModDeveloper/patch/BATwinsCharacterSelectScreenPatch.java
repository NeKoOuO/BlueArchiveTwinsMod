//package baModDeveloper.patch;
//
//import baModDeveloper.character.BATwinsCharacter;
//import baModDeveloper.helpers.ModHelper;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.video.VideoPlayer;
//import com.badlogic.gdx.video.VideoPlayerDesktop;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.localization.CharacterStrings;
//import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
//import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class BATwinsCharacterSelectScreenPatch {
//    @SpirePatch(clz = CharacterSelectScreen.class,method = "render")
//    public static class renderPatch{
//        private static int TWINSINDEX=-1;
//
//        private static final CharacterStrings characterStrings= CardCrawlGame.languagePack.getCharacterString(ModHelper.makePath("Twins"));
//
//        private static int ini=0;
//        private static boolean videoPlay=false;
//        private static Texture a;
//        @SpirePostfixPatch
//        public static void renderPatch(CharacterSelectScreen _instance,SpriteBatch sb,boolean __anySelected){
//            if(__anySelected){
//                if(TWINSINDEX==-1){
//                    for(int i=0;i<_instance.options.size();i++){
//                        if(_instance.options.get(i).name.equals(characterStrings.NAMES[0])){
//                            TWINSINDEX=i;
//                            break;
//                        }
//                    }
//                }
//                if(TWINSINDEX==-1){
//                    return;
//                }
//                if(_instance.options.get(TWINSINDEX).selected){
////                    if(!videoPlay){
////                        play();
////                    }
////                    if(ini<=100){
////                        if(a!=null){
////                            a.dispose();
////                        }
////                        a=new Texture()
////                    }
//                }
//            }
//
//        }
//
//        public static void play(){
//            Timer t=new Timer();
//            t.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    videoPlay=true;
//                    ini+=1;
//                }
//            },0,66);
//
//        }
//    }
//}
