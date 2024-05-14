package baModDeveloper.cards.bullets;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.relic.BATwinsSpecialAmmunition;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public abstract class BATwinsCustomBulletCard extends BATwinsModCustomCard {
    private static BATwinsCustomBulletCard[] BULLETS = null;

    public BATwinsCustomBulletCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target, BATwinsEnergyPanel.EnergyType.SHARE);
    }

    public BATwinsCustomBulletCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, BATwinsEnergyPanel.EnergyType energyType) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target, energyType);
    }

    private static void initializationBullets() {
        if (BULLETS == null) {
            BULLETS = new BATwinsCustomBulletCard[5];
            BULLETS[0] = new BATwinsPoisonBullet();
            BULLETS[1] = new BATwinsArmorPiercingBullet();
            BULLETS[2] = new BATwinsSniperBullet();
            BULLETS[3] = new BATwinsIncendiaryBullet();
            BULLETS[4] = new BATwinsHeavyBullets();
        }

    }

    public static BATwinsCustomBulletCard getRandomBullet() {
        initializationBullets();
        BATwinsCustomBulletCard c = BULLETS[AbstractDungeon.cardRandomRng.random(BULLETS.length - 1)];
        if (!UnlockTracker.isCardSeen(c.cardID)) {
            UnlockTracker.markCardAsSeen(c.cardID);
        }
        return c;
    }

    @Override
    public void applyPowers() {
        int baseBaseDamage = this.baseDamage;
        if (AbstractDungeon.player.hasRelic(BATwinsSpecialAmmunition.ID)) {
            this.baseDamage += 3;
            this.baseDamage += this.timesUpgraded * 3;
        }
        super.applyPowers();
        this.isDamageModified = this.damage != baseBaseDamage;
        this.baseDamage = baseBaseDamage;

    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
