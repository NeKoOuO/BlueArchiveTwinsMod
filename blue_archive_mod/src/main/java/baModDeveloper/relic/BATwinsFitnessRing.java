package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BATwinsFitnessRing extends CustomRelic {
    public static final String ID = ModHelper.makePath("FitnessRing");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "FitnessRing"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "FitnessRing_p"));
    private static final RelicTier type = RelicTier.RARE;
    private int count = 0;

    public BATwinsFitnessRing() {
        super(ID, texture, outline, type, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (!this.grayscale) {
            this.flash();
            drawnCard.upgrade();
        }
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
        this.count = 0;
    }

    @Override
    public void atTurnStart() {
        count++;
        if (count > 1) {
            this.grayscale = true;
        }
    }
}
