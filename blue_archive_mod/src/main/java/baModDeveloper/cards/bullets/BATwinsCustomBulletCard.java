package baModDeveloper.cards.bullets;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public abstract class BATwinsCustomBulletCard extends CustomCard {
    public BATwinsCustomBulletCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    private static BATwinsCustomBulletCard[] BULLETS=null;

    private static void initializationBullets(){
        if(BULLETS==null){
            BULLETS=new BATwinsCustomBulletCard[5];
            BULLETS[0]=new BATwinsPoisonBullet();
            BULLETS[1]=new BATwinsArmorPiercingBullet();
            BULLETS[2]=new BATwinsSniperBullet();
            BULLETS[3]=new BATwinsIncendiaryBullet();
            BULLETS[4]=new BATwinsHeavyBullets();
        }

    }

    public static BATwinsCustomBulletCard getRandomBullet(){
        initializationBullets();
        BATwinsCustomBulletCard c=BULLETS[AbstractDungeon.cardRandomRng.random(BULLETS.length-1)];
        if(!UnlockTracker.isCardSeen(c.cardID)){
            UnlockTracker.markCardAsSeen(c.cardID);
        }
        return c;
    }
}
