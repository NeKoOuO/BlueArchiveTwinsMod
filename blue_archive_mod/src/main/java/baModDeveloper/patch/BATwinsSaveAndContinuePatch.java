package baModDeveloper.patch;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import java.util.Map;
import java.util.Set;

public class BATwinsSaveAndContinuePatch {
    @SpirePatch(clz = SaveAndContinue.class, method = "loadSaveFile", paramtypez = {String.class})
    public static class loadSaveFilePatch {
        @SpireInsertPatch(rloc = 20, localvars = {"saveFile", "savestr"})
        public static void loadSaveFilePatch(String filePath, @ByRef SaveFile[] saveFile, @ByRef String[] savestr) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(savestr[0], JsonObject.class);
            JsonArray jsonArray = jsonObject.getAsJsonArray("cards");
            int i = 0;
            String keyName = "";
            JsonObject obj = jsonArray.get(0).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
            for (Map.Entry<String, JsonElement> map : entrySet) {
                if (map.getKey().contains("isExchange")) {
                    keyName = map.getKey();
                    break;
                }
            }
            if (keyName.isEmpty()) {
                System.out.println("can not read BATwins saveFile isExchange!");
                return;
            }
            for (JsonElement cardElement : jsonArray) {
                JsonObject cardObject = cardElement.getAsJsonObject();
                boolean isExchange = cardObject.get(keyName).getAsBoolean();
                BATwinsCardSavePatch.FiledPatch.isExchange.set(saveFile[0].cards.get(i), isExchange);
                i++;
                System.out.println(keyName + isExchange);
            }
        }
    }
}
