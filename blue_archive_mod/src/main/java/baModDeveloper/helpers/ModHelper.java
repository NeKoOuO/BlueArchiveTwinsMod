package baModDeveloper.helpers;

import baModDeveloper.character.BATwinsCharacter;

public class ModHelper {
    public static String makePath(String id){
        return "BATwinsMod:"+id;
    }
    public static String makeImgPath(String floder,String imgName){
        return "baModResources/img/"+floder+"/"+imgName+".png";
    }

    public static String makeGifPath(String floder,String imgName){
        return "baModResources/img/"+floder+"/"+imgName+".gif";
    }

    public static <T> T checkBATwinPlayer(T player){
        if(player instanceof BATwinsCharacter){
            return (T)player;
        }else{
            return player;
        }
    }
}
