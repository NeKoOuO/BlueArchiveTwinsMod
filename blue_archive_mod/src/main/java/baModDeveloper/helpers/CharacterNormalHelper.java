package baModDeveloper.helpers;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;

import java.util.HashMap;
import java.util.Map;

import static baModDeveloper.helpers.Character3DHelper.AnimationName.*;

public class CharacterNormalHelper extends Character3DHelper {
    protected static Map<AnimationName, String[]> AnimationNames = new HashMap<>();

    static {
        AnimationNames.put(NORMAL_IDLE, new String[]{"Armature|Momoi_Original_Normal_Idle", "Armature|Midori_Original_Normal_Idle"});
        AnimationNames.put(STAND_ATTACK_DELAY, new String[]{"Armature|Momoi_Original_Stand_Attack_Delay", "Armature|Midori_Original_Stand_Attack_Delay"});
        AnimationNames.put(MOVING, new String[]{"Armature|Momoi_Original_Move_Ing", "Armature|Midori_Original_Move_Ing"});
        AnimationNames.put(MOVING_END, new String[]{"Armature|Momoi_Original_Move_End_Normal", "Armature|Midori_Original_Move_End_Normal"});
        AnimationNames.put(ATTACK_START, new String[]{"Armature|Momoi_Original_Normal_Attack_Start", "Armature|Midori_Original_Normal_Attack_Start"});
        AnimationNames.put(ATTACKING, new String[]{"Armature|Momoi_Original_Normal_Attack_Ing", "Armature|Midori_Original_Normal_Attack_Ing"});
        AnimationNames.put(ATTACK_END, new String[]{"Armature|Momoi_Original_Normal_Attack_End", "Armature|Midori_Original_Normal_Attack_End"});
        AnimationNames.put(MOVE_JUMP, new String[]{"Armature|Momoi_Original_Move_Jump", "Armature|Midori_Original_Move_Jump"});
        AnimationNames.put(DEATH, new String[]{"Armature|Momoi_Original_Vital_Death", "Armature|Midori_Original_Vital_Death"});
        AnimationNames.put(DYING, new String[]{"Armature|Momoi_Original_Vital_Dying_ing", "Armature|Midori_Original_Vital_Dying_ing"});
        AnimationNames.put(RELOAD, new String[]{"Armature|Momoi_Original_Normal_Reload", "Armature|Midori_Original_Normal_Reload"});
        AnimationNames.put(PANIC, new String[]{"Armature|Momoi_Original_Vital_Panic", "Armature|Midori_Original_Vital_Panic"});
        AnimationNames.put(REACTION, new String[]{"Armature|Momoi_Original_Victory_Start", "Armature|Midori_Original_Victory_Start"});
    }

    @Override
    public void init() {
        this.momoiController = new ModelController("baModResources/img/char/model/momoi.g3dj", 0, 0, -500, AnimationNames.get(NORMAL_IDLE)[0]);
        this.midoriController = new ModelController("baModResources/img/char/model/midori.g3dj", 0, 0, -500, AnimationNames.get(NORMAL_IDLE)[1]);
        if(this.midoriController.inited){
            this.midoriController.setAttribute("Month", new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));

        }
        if(this.momoiController.inited){
            this.momoiController.setAttribute("Month", new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));

        }
        super.init();

    }
}
