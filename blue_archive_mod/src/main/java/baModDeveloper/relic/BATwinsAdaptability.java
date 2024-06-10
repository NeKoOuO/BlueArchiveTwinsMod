package baModDeveloper.relic;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.BATwinsLevelUpInterface;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import baModDeveloper.power.BATwinsExperiencePower;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsAdaptability extends CustomRelic implements BATwinsLevelUpInterface {
    public static final String ID= ModHelper.makePath("Adaptability");
    private static final Texture texture=TextureLoader.getTexture(ModHelper.makeImgPath("relic","Adaptability"));
    private static final Texture outline= TextureLoader.getTexture(ModHelper.makeImgPath("relic","Adaptability_p"));
    private static final RelicTier type=RelicTier.UNCOMMON;
    public BATwinsAdaptability() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void triggerOnLevelUp() {
        this.flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new BATwinsExperiencePower(AbstractDungeon.player,2)));
    }
}
