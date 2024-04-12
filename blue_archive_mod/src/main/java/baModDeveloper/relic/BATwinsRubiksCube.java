package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BATwinsRubiksCube extends CustomRelic {
    public static final String ID = ModHelper.makePath("RubiksCube");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "RubiksCube"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "RubiksCube_p"));
    private static final RelicTier type = RelicTier.RARE;

    public BATwinsRubiksCube() {
        super(ID, texture, outline, type, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onTrigger() {
        this.counter++;
        if (this.counter == 2) {
            this.triggerOnConnection();
            this.counter = 0;
        }
    }

    public void triggerOnConnection() {
        this.flash();
        addToBot(new DrawCardAction(1));
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        this.counter = 0;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }
}
