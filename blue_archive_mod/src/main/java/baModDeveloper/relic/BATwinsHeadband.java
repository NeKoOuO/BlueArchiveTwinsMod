package baModDeveloper.relic;

import baModDeveloper.action.BATwinsGainEnergyAction;
import baModDeveloper.action.BATwinsPlayTempCardAction;
import baModDeveloper.cards.BATwinsHeadbandChice;
import baModDeveloper.cards.BATwinsMaidForm;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class BATwinsHeadband extends CustomRelic implements CustomSavable<BATwinsEnergyPanel.EnergyType> {
    public static final String ID = ModHelper.makePath("Headband");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "Headband"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "Headband_p"));
    private static final RelicTier type = RelicTier.BOSS;
    private BATwinsEnergyPanel.EnergyType energyType;

    public BATwinsHeadband() {
        super(ID, texture, outline, type, LandingSound.CLINK);
    }

    public void setEnergyType(BATwinsEnergyPanel.EnergyType energyType) {
        this.energyType = energyType;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        BATwinsHeadbandChice chice1 = new BATwinsHeadbandChice();
        BATwinsHeadbandChice chice2 = new BATwinsHeadbandChice();
        chice2.conversionColor(false);
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(chice1);
        list.add(chice2);
        AbstractDungeon.cardRewardScreen.chooseOneOpen(list);
    }

    @Override
    public void atTurnStart() {
        this.flash();
        if (this.energyType != null)
            addToBot(new BATwinsGainEnergyAction(1, this.energyType));
    }

    @Override
    public BATwinsEnergyPanel.EnergyType onSave() {
        return this.energyType;
    }

    @Override
    public void onLoad(BATwinsEnergyPanel.EnergyType energyType) {
        this.energyType = energyType;
    }

    @Override
    public void atBattleStartPreDraw() {
        boolean hasAttire = false;
        boolean hasBroom = false;
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (!hasAttire && r.relicId.equals(BATwinsMaidAttire.ID)) {
                hasAttire = true;
            } else if (!hasBroom && r.relicId.equals(BATwinsBroom.ID)) {
                hasBroom = true;
            }
            if (hasAttire && hasBroom) {
                this.flash();
                addToBot(new BATwinsPlayTempCardAction(new BATwinsMaidForm(), 1));
                return;
            }
        }
    }

}
