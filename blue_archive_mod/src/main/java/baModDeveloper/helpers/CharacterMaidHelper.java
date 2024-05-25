package baModDeveloper.helpers;

import java.util.HashMap;
import java.util.Map;

import static baModDeveloper.character.BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
import static baModDeveloper.character.BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
import static baModDeveloper.helpers.Character3DHelper.AnimationName.*;
import static baModDeveloper.helpers.Character3DHelper.AnimationName.PANIC;

public class CharacterMaidHelper extends Character3DHelper{
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
    }

    @Override
    public void init() {
        this.momoiController = new ModelController("baModResources/img/char/model/momoi_maid.g3dj", 0, 0, -500, AnimationNames.get(NORMAL_IDLE)[0]);
        this.midoriController = new ModelController("baModResources/img/char/model/midori_maid.g3dj", 0, 0, -500, AnimationNames.get(NORMAL_IDLE)[1]);
        super.init();

    }
}
