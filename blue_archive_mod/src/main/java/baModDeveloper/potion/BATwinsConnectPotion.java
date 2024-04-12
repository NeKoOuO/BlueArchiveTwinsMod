package baModDeveloper.potion;

import baModDeveloper.action.BATwinsSelectHandCardToPlayAction;
import baModDeveloper.helpers.ModHelper;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BATwinsConnectPotion extends CustomPotion {
    public static final String ID = ModHelper.makePath("ConnectPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    private static final PotionRarity rarity = PotionRarity.UNCOMMON;
    private static final PotionSize size = PotionSize.H;
    private static final PotionColor color = PotionColor.BLUE;
    public static Color liquidColor = new Color(14.0F / 255.0F, 233.0F / 255.0F, 168.0F / 255.0F, 0.8F);
    public static Color hybridColor = new Color(254.0F / 255.0F, 168.0F / 255.0F, 198.0F / 255.0F, 1.0F);
    public static Color spotsColor = new Color(14.0F / 255.0F, 233.0F / 255.0F, 168.0F / 255.0F, 0.8F);

    public BATwinsConnectPotion() {
        super(potionStrings.NAME, ID, rarity, size, color);
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {
                addToBot(new BATwinsSelectHandCardToPlayAction(null, null, null, 2, 1));
            } else {
                addToBot(new BATwinsSelectHandCardToPlayAction(null, null, null, 1, 1));
            }

        }
    }

    @Override
    public int getPotency(int i) {
        return 2;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BATwinsConnectPotion();
    }

    @Override
    public void initializeData() {
        this.potency = getPotency();
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {
            this.description = potionStrings.DESCRIPTIONS[1];
        } else {
            this.description = potionStrings.DESCRIPTIONS[0];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
}
