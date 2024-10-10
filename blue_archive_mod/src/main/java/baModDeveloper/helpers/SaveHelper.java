package baModDeveloper.helpers;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import com.google.gson.JsonElement;

public class SaveHelper implements CustomSavable, ISubscriber {

    public static class SaveValue {
        public boolean challengeCoupons = false;
        public int challengeCouponsFloor = -1;

        public boolean hasSoraPhone=false;
    }

    public SaveValue values = new SaveValue();

    public SaveHelper() {
        BaseMod.subscribe(this);
    }

    @Override
    public Object onSave() {
        return null;
    }

    @Override
    public void onLoad(Object o) {

    }

    @Override
    public JsonElement onSaveRaw() {
        return ModHelper.gson.toJsonTree(this.values);
    }

    @Override
    public void onLoadRaw(JsonElement value) {
        if (value != null) {
            this.values = ModHelper.gson.fromJson(value, SaveValue.class);
        }
    }
}
