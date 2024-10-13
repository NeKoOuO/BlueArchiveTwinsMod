package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsPackage extends CustomRelic {
    public static final String ID = ModHelper.makePath("Package");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "Package"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "Package_p"));
    private static final RelicTier type = RelicTier.SPECIAL;

    public BATwinsPackage() {
        super(ID, texture, outline, type, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new DamageAllEnemiesAction(null,
                DamageInfo.createDamageMatrix(5, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}
