package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsFoldingShield extends CustomRelic {
    public static final String ID = ModHelper.makePath("FoldingShield");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "FoldingShield"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "FoldingShield_p"));
    private static final RelicTier type = RelicTier.SPECIAL;
    private final int amount = 2;

    public BATwinsFoldingShield() {
        super(ID, texture, outline, type, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onPlayerEndTurn() {
        if (!AbstractDungeon.player.hand.isEmpty()&&AbstractDungeon.player.currentBlock>0) {
            this.flash();
            addToBot(new GainBlockAction(AbstractDungeon.player, (int) (AbstractDungeon.player.currentBlock*0.2F)));
        }

    }
}
