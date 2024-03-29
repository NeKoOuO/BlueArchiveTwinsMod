package baModDeveloper.effect;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class BATwinsCampfireReopenEffect extends CampfireSleepEffect {

    @Override
    public void update() {
        super.update();
        if(this.isDone&& AbstractDungeon.getCurrRoom() instanceof RestRoom){
            try {
                Method method= CampfireUI.class.getDeclaredMethod("initializeButtons");
                method.setAccessible(true);
                Field field=CampfireUI.class.getDeclaredField("buttons");
                field.setAccessible(true);
                ArrayList<AbstractCampfireOption> buttons= (ArrayList<AbstractCampfireOption>) field.get(((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI);
                buttons.clear();
                method.invoke(((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI);
                ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI.reopen();
//                field.setAccessible(false);
//                method.setAccessible(false);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
