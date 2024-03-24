package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;

public class BATwinsBroom extends CustomRelic {
    public static final String ID= ModHelper.makePath("Broom");
    private static final Texture texture= TextureLoader.getTexture(ModHelper.makeImgPath("relic","Broom"));
    private static final Texture outline=TextureLoader.getTexture(ModHelper.makeImgPath("relic","Broom_p"));
    private static final RelicTier type=RelicTier.COMMON;
    public BATwinsBroom() {
        super(ID,texture,outline,type,LandingSound.FLAT);
        this.counter=0;
    }

    @Override
    public void atTurnStart() {
        this.counter++;
        if(counter==3){
            this.flash();
            addToBot(new BetterDiscardPileToHandAction(1));
            this.counter=0;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


}
