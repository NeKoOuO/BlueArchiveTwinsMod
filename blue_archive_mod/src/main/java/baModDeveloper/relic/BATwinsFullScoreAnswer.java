package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BATwinsFullScoreAnswer extends CustomRelic {
    public static final String ID= ModHelper.makePath("FullScoreAnswer");
    private static final Texture texture= TextureLoader.getTexture(ModHelper.makeImgPath("relic","FullScoreAnswer"));
    private static final Texture outline=TextureLoader.getTexture(ModHelper.makeImgPath("relic","FullScoreAnswer_p"));
    private static final RelicTier type=RelicTier.UNCOMMON;
    public BATwinsFullScoreAnswer() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,1)));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,1)));
    }


    @Override
    public void wasHPLost(int damageAmount) {
        if(!this.grayscale){
            this.flash();
            addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, StrengthPower.POWER_ID, 1));
            addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, DexterityPower.POWER_ID, 1));
            this.grayscale=true;
        }

    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        super.onEnterRoom(room);
        this.grayscale=false;
    }
}
