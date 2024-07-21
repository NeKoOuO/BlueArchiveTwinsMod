package baModDeveloper.patch;

import baModDeveloper.helpers.ModHelper;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import com.megacrit.cardcrawl.trials.CustomTrial;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class BATwinsCustomModeScreenPatch implements CustomSavable , ISubscriber {
    public static boolean NoMomoiCardModEnable=false;
    public static boolean NoMidoriCardModEnable=false;

//    public BATwinsCustomModeScreenPatch(){
//        BaseMod.subscribe(this);
//    }
    @Override
    public Object onSave() {
        return new boolean[]{NoMomoiCardModEnable,NoMidoriCardModEnable};
    }

    @Override
    public void onLoad(Object o) {
        boolean[] mods= (boolean[]) o;
        NoMomoiCardModEnable=mods[0];
        NoMidoriCardModEnable=mods[1];
    }

    @Override
    public JsonElement onSaveRaw() {
        JsonObject object=new JsonObject();
        object.addProperty("NoMomoiCardMod",NoMomoiCardModEnable);
        object.addProperty("NoMidoriCardMod",NoMidoriCardModEnable);
        return object;
    }

    @Override
    public void onLoadRaw(JsonElement value) {
        ModHelper.getLogger().info(value);
        if(value!=null){
            NoMomoiCardModEnable=value.getAsJsonObject().get("NoMomoiCardMod").getAsBoolean();
            NoMidoriCardModEnable=value.getAsJsonObject().get("NoMidoriCardMod").getAsBoolean();
        }

    }

    @SpirePatch(clz = CustomModeScreen.class,method = "initializeMods")
    public static class initializeModsPatch{
        @SpirePostfixPatch
        public static void postFixPatch(CustomModeScreen __instance){
            try {
                Method method=CustomModeScreen.class.getDeclaredMethod("addMod",String.class,String.class,boolean.class);
                method.setAccessible(true);
                CustomMod noMomoiCardMod= (CustomMod) method.invoke(__instance,ModHelper.makePath("NoMomoiCard"),"r",false);
                CustomMod noMidoriCardMod= (CustomMod) method.invoke(__instance,ModHelper.makePath("NoMidoriCard"),"r",false);
                noMomoiCardMod.setMutualExclusionPair(noMidoriCardMod);
                noMidoriCardMod.setMutualExclusionPair(noMomoiCardMod);
            } catch (NoSuchMethodException|InvocationTargetException | IllegalAccessException e) {
                ModHelper.getLogger().error(e.getMessage());
                return;
            }


        }
    }

    @SpirePatch(clz = CustomModeScreen.class,method = "addNonDailyMods")
    public static class addNonDailyModsPatch{
        @SpirePostfixPatch
        public static void postFixPatch(CustomModeScreen __instance, CustomTrial trial, ArrayList<String> modIds){
            NoMomoiCardModEnable=false;
            NoMidoriCardModEnable=false;
            for(String s:modIds){
                if(s.equals(ModHelper.makePath("NoMomoiCard"))){
                    NoMomoiCardModEnable=true;
                    NoMidoriCardModEnable=false;
                }else if(s.equals(ModHelper.makePath("NoMidoriCard"))){
                    NoMidoriCardModEnable=true;
                    NoMomoiCardModEnable=false;
                }
            }
        }
    }
}
