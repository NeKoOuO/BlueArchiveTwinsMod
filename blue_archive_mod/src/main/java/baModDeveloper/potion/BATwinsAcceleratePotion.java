package baModDeveloper.potion;

import baModDeveloper.cards.colorless.BATwinsAccelerate;
import baModDeveloper.helpers.ModHelper;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.CunningPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.sun.org.apache.xpath.internal.operations.Mod;

public class BATwinsAcceleratePotion extends CustomPotion {
    public static final String ID=ModHelper.makePath("AcceleratePotion");
    private static final PotionStrings potionStrings= CardCrawlGame.languagePack.getPotionString(ID);
    private static final PotionRarity rarity=PotionRarity.RARE;
    private static final PotionSize size=PotionSize.CARD;
    private static final PotionColor color=PotionColor.BLUE;
    public static Color liquidColor=new Color(159.0F / 255.0F, 165.0F / 255.0F, 290.0F / 255.0F, 1.0F);
    public static Color hybridColor=new Color(233.0F / 255.0F, 233.0F / 255.0F, 233.0F / 255.0F, 1.0F);
    public static Color spotsColor=new Color(254.0F / 255.0F, 168.0F / 255.0F, 198.0F / 255.0F, 1.0F);
    public BATwinsAcceleratePotion(){
        super(potionStrings.NAME,ID,rarity,size,color);

    }

    @Override
    public void initializeData() {
//        /* 27 */     this.potency = getPotency();
//        /* 28 */     this.description = potionStrings.DESCRIPTIONS[0];
//        /* 29 */     this.tips.clear();
//        /* 30 */     this.tips.add(new PowerTip(this.name, this.description));
//        /* 31 */     this.tips.add(new PowerTip(
//                /*    */
//                /* 33 */           TipHelper.capitalize(GameDictionary.STANCE.NAMES[0]), (String)GameDictionary.keywords
///* 34 */           .get(GameDictionary.STANCE.NAMES[0])));
        this.potency=getPotency();
        this.tips.clear();

        if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic("SacredBark")){
            this.description=potionStrings.DESCRIPTIONS[1];
            this.tips.add(new PowerTip(this.name,this.description));
            this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[3],GameDictionary.keywords.get("batwinsmod:"+potionStrings.DESCRIPTIONS[3])));

        }else {
            this.description=potionStrings.DESCRIPTIONS[0];
            this.tips.add(new PowerTip(this.name,this.description));
            this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[2],GameDictionary.keywords.get("batwinsmod:"+potionStrings.DESCRIPTIONS[2])));

        }

    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            BATwinsAccelerate temp=new BATwinsAccelerate();
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic("SacredBark")){
                temp.upgrade();
            }
            addToBot(new MakeTempCardInHandAction(temp));

        }
    }

    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BATwinsAcceleratePotion();
    }
}
