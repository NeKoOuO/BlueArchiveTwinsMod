package baModDeveloper.helpers;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;

import java.util.HashMap;
import java.util.Map;

import static baModDeveloper.helpers.Character3DHelper.AnimationName.*;

public class CharacterMaidHelper extends Character3DHelper {
    protected static Map<AnimationName, String[]> AnimationNames = new HashMap<>();

    static {
        AnimationNames.put(NORMAL_IDLE, new String[]{"bone_root|CH0201_Normal_Idle", "bone_root|CH0202_Normal_Idle"});
        AnimationNames.put(STAND_ATTACK_DELAY, new String[]{"bone_root|CH0201_Normal_Attack_Delay", "bone_root|CH0202_Normal_Attack_Delay"});
        AnimationNames.put(MOVING, new String[]{"bone_root|CH0201_Move_Ing", "bone_root|CH0202_Move_Ing"});
        AnimationNames.put(MOVING_END, new String[]{"bone_root|CH0201_Move_End_Normal", "bone_root|CH0202_Move_End_Normal"});
        AnimationNames.put(ATTACK_START, new String[]{"bone_root|CH0201_Normal_Attack_Start", "bone_root|CH0202_Normal_Attack_Start"});
        AnimationNames.put(ATTACKING, new String[]{"bone_root|CH0201_Normal_Attack_Ing", "bone_root|CH0202_Normal_Attack_Ing"});
        AnimationNames.put(ATTACK_END, new String[]{"bone_root|CH0201_Normal_Attack_End", "bone_root|CH0202_Normal_Attack_End"});
        AnimationNames.put(MOVE_JUMP, new String[]{"bone_root|CH0201_Move_Jump", "bone_root|CH0202_Move_Jump"});
        AnimationNames.put(DEATH, new String[]{"bone_root|CH0201_Vital_Death", "bone_root|CH0202_Vital_Death"});
        AnimationNames.put(DYING, new String[]{"bone_root|CH0201_Vital_Dying_Ing", "bone_root|CH0202_Vital_Dying_Ing"});
        AnimationNames.put(RELOAD, new String[]{"bone_root|CH0201_Normal_Reload", "bone_root|CH0202_Normal_Reload"});
        AnimationNames.put(PANIC, new String[]{"bone_root|CH0201_Vital_Panic", "bone_root|CH0202_Vital_Panic"});
        AnimationNames.put(REACTION, new String[]{"bone_root|CH0201_Victory_Start", "bone_root|CH0202_Victory_Start"});
    }

    @Override
    public void init() {
        this.momoiController = new ModelController("baModResources/img/char/model/momoi_maid.g3dj", 0, 0, -500, AnimationNames.get(NORMAL_IDLE)[0]);
        this.midoriController = new ModelController("baModResources/img/char/model/midori_maid.g3dj", 0, 0, -500, AnimationNames.get(NORMAL_IDLE)[1]);
        if(this.momoiController.inited){
            this.momoiController.setAttribute("Month", new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
            this.momoiController.setAttribute("CH0201_Body", new IntAttribute(IntAttribute.CullFace, 0));

        }
        if(this.midoriController.inited){
            this.midoriController.setAttribute("Month", new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
            this.midoriController.setAttribute("CH0202_Body", new IntAttribute(IntAttribute.CullFace, 0));

        }
        super.init();

    }
}
