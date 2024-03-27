package baModDeveloper.patch;

import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;

import java.util.Objects;

public class BATwinsMainMusicPatch {
    @SpirePatch(clz = MainMusic.class,method = "getSong")
    public static class getSongPatch{
        @SpirePrefixPatch
        public static SpireReturn<Music> getSongPatch(MainMusic _instance, String key){
            if(Objects.equals(key, ModHelper.makePath("pixelTime"))){
                return SpireReturn.Return(MainMusic.newMusic(ModHelper.makeAudioPath("PixelTime")));
            }
            return SpireReturn.Continue();
        }
    }
}
