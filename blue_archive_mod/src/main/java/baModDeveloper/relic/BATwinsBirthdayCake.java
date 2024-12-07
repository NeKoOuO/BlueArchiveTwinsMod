package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import baModDeveloper.modifier.BATwinsWishModifier;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Wish;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class BATwinsBirthdayCake extends CustomRelic {
    public static final String ID = ModHelper.makePath("BirthdayCake");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "BirthdayCake"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "BirthdayCake_p"));
    private static final RelicTier type = RelicTier.SPECIAL;

    public BATwinsBirthdayCake() {
        super(ID, texture, outline, type, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        super.onEquip();
        AbstractCard wish=CardLibrary.getCopy(Wish.ID);
        CardModifierManager.addModifier(wish,new BATwinsWishModifier());
        AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(wish, Settings.WIDTH/2.0F,Settings.HEIGHT/2.0F));
    }
}
