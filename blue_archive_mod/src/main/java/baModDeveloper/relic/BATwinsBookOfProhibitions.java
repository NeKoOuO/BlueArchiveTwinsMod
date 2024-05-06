package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BATwinsBookOfProhibitions extends CustomRelic {
    public static final String ID= ModHelper.makePath("BookOfProhibitions");
    private static final Texture texture= TextureLoader.getTexture(ModHelper.makeImgPath("relic","BookOfProhibitions"));
    private static final Texture outline=TextureLoader.getTexture(ModHelper.makeImgPath("relic","BookOfProhibitions_p"));
    private static final RelicTier type=RelicTier.SPECIAL;
    public BATwinsBookOfProhibitions() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(!this.grayscale){
            if(info.type== DamageInfo.DamageType.NORMAL){
                this.flash();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
                this.grayscale=true;
                addToBot(new HealAction(AbstractDungeon.player,AbstractDungeon.player,damageAmount/3));
            }
        }
    }

    @Override
    public void atTurnStart() {
        this.grayscale=false;
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        this.grayscale=false;
    }
}
