package baModDeveloper.potion;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsBurnPower;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BATwinsBurnPotion extends CustomPotion {
    public static final String ID = ModHelper.makePath("BurnPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    private static final PotionRarity rarity = PotionRarity.COMMON;
    private static final PotionSize size = PotionSize.M;
    private static final PotionColor color = PotionColor.BLUE;
    public static Color liquidColor = new Color(255.0F / 255.0F, 1.0F / 255.0F, 19.0F / 255.0F, 1.0F);
    public static Color hybridColor = new Color(253.0F / 255.0F, 2.0F / 255.0F, 22.0F / 255.0F, 1.0F);
    public static Color spotsColor = new Color(254.0F / 255.0F, 1.0F / 255.0F, 19.0F / 255.0F, 1.0F);

    public BATwinsBurnPotion() {
        super(potionStrings.NAME, ID, rarity, size, color);
        this.labOutlineColor = Settings.RED_RELIC_COLOR;
        this.isThrown = true;
        this.targetRequired = true;
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {
                addToBot(new ApplyPowerAction(abstractCreature, AbstractDungeon.player, new BATwinsBurnPower(abstractCreature, AbstractDungeon.player, 16)));
            } else {
                addToBot(new ApplyPowerAction(abstractCreature, AbstractDungeon.player, new BATwinsBurnPower(abstractCreature, AbstractDungeon.player, 8)));
            }

        }
    }

    @Override
    public int getPotency(int i) {
        return 8;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BATwinsBurnPotion();
    }

    @Override
    public void initializeData() {
        this.potency = getPotency();
        this.tips.clear();

        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {
            this.description = potionStrings.DESCRIPTIONS[1];
            this.tips.add(new PowerTip(this.name, this.description));

        } else {
            this.description = potionStrings.DESCRIPTIONS[0];
            this.tips.add(new PowerTip(this.name, this.description));

        }
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[2], GameDictionary.keywords.get("batwinsmod:" + potionStrings.DESCRIPTIONS[2])));

    }
}
