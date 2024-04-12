package baModDeveloper.relic;

import baModDeveloper.action.BATwinsDoublePowerAction;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import baModDeveloper.power.BATwinsBurnPower;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsNekoHowitzer extends CustomRelic {
    public static final String ID = ModHelper.makePath("NekoHowitzer");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "NekoHowitzer"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "NekoHowitzer_p"));
    private static final RelicTier type = RelicTier.SPECIAL;
    private final int count = 2;

    public BATwinsNekoHowitzer() {
        super(ID, texture, outline, type, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], this.count);
    }

    @Override
    public void atTurnStart() {
        addToBot(new AbstractGameAction() {
            final int count = BATwinsNekoHowitzer.this.count;

            @Override
            public void update() {
                AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
                int rng = AbstractDungeon.miscRng.random(1, 100);
                if (rng == 1) {
                    addToTop(new BATwinsDoublePowerAction(BATwinsBurnPower.POWER_ID, m));
                }
                addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new BATwinsBurnPower(m, AbstractDungeon.player, this.count)));
                this.isDone = true;
            }
        });
    }
}
