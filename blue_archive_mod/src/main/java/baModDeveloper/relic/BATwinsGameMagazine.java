package baModDeveloper.relic;

import baModDeveloper.cards.BATwinsExchange;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BATwinsGameMagazine extends CustomRelic {
    public static final String ID= ModHelper.makePath("GameMagazine");
    private static final Texture texture= TextureLoader.getTexture(ModHelper.makeImgPath("relic","GameMagazine"));
    private static final Texture outline=TextureLoader.getTexture(ModHelper.makeImgPath("relic","GameMagazine"));
    private static final RelicTier type=RelicTier.STARTER;
    public BATwinsGameMagazine() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.grayscale=true;
        addToBot(new MakeTempCardInHandAction(new BATwinsExchange(),1));
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale=false;
    }
}
