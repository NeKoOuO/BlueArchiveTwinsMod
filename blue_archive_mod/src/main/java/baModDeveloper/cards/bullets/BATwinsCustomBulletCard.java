package baModDeveloper.cards.bullets;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.List;

public abstract class BATwinsCustomBulletCard extends CustomCard {
    public BATwinsCustomBulletCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    private static BATwinsCustomBulletCard[] BULLETS=null;

    private static void initlizationBullets(){
        if(BULLETS==null){
            BULLETS=new BATwinsCustomBulletCard[4];
            BULLETS[0]=new BATwinsPoisonBullet();
            BULLETS[1]=new BATwinsArmorPiercingBullet();
            BULLETS[2]=new BATwinsSniperBullet();
            BULLETS[3]=new BATwinsIncendiaryBullet();
        }

    }

    public static BATwinsCustomBulletCard getRandomBullet(){
        initlizationBullets();
        BATwinsCustomBulletCard c=BULLETS[AbstractDungeon.cardRandomRng.random(BULLETS.length-1)];
        if(!UnlockTracker.isCardSeen(c.cardID)){
            UnlockTracker.markCardAsSeen(c.cardID);
        }
        return c;
    }
}
