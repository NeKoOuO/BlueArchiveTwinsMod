package baModDeveloper.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class BATwinsCampfireReopenEffect extends CampfireSleepEffect {

    private final Color screenColor = AbstractDungeon.fadeColor.cpy();

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.updateBlackScreenColor();
//        if (this.duration < this.startingDuration - 0.5F) {
//            this.playSleepJingle();
//        }

        if (this.duration < this.startingDuration / 2.0F) {

            this.isDone = true;
            ((RestRoom) AbstractDungeon.getCurrRoom()).fadeIn();
            AbstractRoom.waitTimer = 0.0F;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;


        }
        if (this.isDone && AbstractDungeon.getCurrRoom() instanceof RestRoom) {
            try {
                Method method = CampfireUI.class.getDeclaredMethod("initializeButtons");
                method.setAccessible(true);
                Field field = CampfireUI.class.getDeclaredField("buttons");
                field.setAccessible(true);
                ArrayList<AbstractCampfireOption> buttons = (ArrayList<AbstractCampfireOption>) field.get(((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI);
                buttons.clear();
                method.invoke(((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI);
                ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI.reopen();
//                field.setAccessible(false);
//                method.setAccessible(false);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                     NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateBlackScreenColor() {
        if (this.duration > this.startingDuration - 0.5F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - (this.startingDuration - 0.5F)) * 2.0F);
        } else if (this.duration < 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration);
        } else {
            this.screenColor.a = 1.0F;
        }

    }

    private void playSleepJingle() {
        int roll = MathUtils.random(0, 2);
        switch (AbstractDungeon.id) {
            case "Exordium":
                if (roll == 0) {
                    CardCrawlGame.sound.play("SLEEP_1-1");
                } else if (roll == 1) {
                    CardCrawlGame.sound.play("SLEEP_1-2");
                } else {
                    CardCrawlGame.sound.play("SLEEP_1-3");
                }
                break;
            case "TheCity":
                if (roll == 0) {
                    CardCrawlGame.sound.play("SLEEP_2-1");
                } else if (roll == 1) {
                    CardCrawlGame.sound.play("SLEEP_2-2");
                } else {
                    CardCrawlGame.sound.play("SLEEP_2-3");
                }
                break;
            case "TheBeyond":
                if (roll == 0) {
                    CardCrawlGame.sound.play("SLEEP_3-1");
                } else if (roll == 1) {
                    CardCrawlGame.sound.play("SLEEP_3-2");
                } else {
                    CardCrawlGame.sound.play("SLEEP_3-3");
                }
        }

    }
}
