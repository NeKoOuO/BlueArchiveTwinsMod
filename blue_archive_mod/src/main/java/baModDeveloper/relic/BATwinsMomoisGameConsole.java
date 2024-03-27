package baModDeveloper.relic;

import baModDeveloper.action.BATwinsAddHandAccordColorAction;
import baModDeveloper.action.BATwinsGainEnergyAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BATwinsMomoisGameConsole extends CustomRelic {
    public static final String ID = ModHelper.makePath("MomoisGameConsole");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "MomoisGameConsole"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "MomoisGameConsole_p"));
    private static final RelicTier type = RelicTier.COMMON;

    public BATwinsMomoisGameConsole() {
        super(ID, texture, outline, type, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.
                addToBot(new BATwinsGainEnergyAction(1, BATwinsEnergyPanel.EnergyType.MOMOI));
        addToBot(new BATwinsAddHandAccordColorAction(BATwinsCharacter.Enums.BATWINS_MOMOI_CARD, true, false));
        this.grayscale = true;
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BATwinsMomoisGameConsole();
    }
}
