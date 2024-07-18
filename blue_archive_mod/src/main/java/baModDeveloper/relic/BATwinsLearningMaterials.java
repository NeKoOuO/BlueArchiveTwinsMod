package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnSmithRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class BATwinsLearningMaterials extends CustomRelic implements BetterOnSmithRelic {
    public static final String ID = ModHelper.makePath("LearningMaterial");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "LearningMaterials"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "LearningMaterials_p"));
    private static final RelicTier type = RelicTier.COMMON;

    public BATwinsLearningMaterials() {
        super(ID, texture, outline, type, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void betterOnSmith(AbstractCard abstractCard) {
        CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        if (AbstractDungeon.player != null) {
            AbstractDungeon.player.masterDeck.group.stream().filter(card -> {
                return (card.rarity == AbstractCard.CardRarity.BASIC || card.rarity == AbstractCard.CardRarity.COMMON) && card.canUpgrade();
            }).forEach(temp::addToBottom);
            if (!temp.isEmpty()) {
                AbstractCard c = temp.getRandomCard(AbstractDungeon.miscRng);
                c.upgrade();
                this.flash();
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F + (AbstractCard.IMG_WIDTH * 2.0F) * Settings.scale, Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + (AbstractCard.IMG_WIDTH * 2.0F) * Settings.scale, Settings.HEIGHT / 2.0F));
            }


        }
    }

    @Override
    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48);
    }
}
