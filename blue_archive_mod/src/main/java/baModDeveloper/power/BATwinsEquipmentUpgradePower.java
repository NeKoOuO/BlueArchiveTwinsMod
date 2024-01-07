//package baModDeveloper.power;
//
//import baModDeveloper.helpers.ModHelper;
//import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
//import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.core.AbstractCreature;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.localization.PowerStrings;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.powers.AbstractPower;
//
//import java.util.ArrayList;
//
//public class BATwinsEquipmentUpgradePower extends AbstractPower implements InvisiblePower , NonStackablePower {
//    public static final String POWER_ID= ModHelper.makePath("EquipmentUpgradePower");
//    private static final AbstractPower.PowerType TYPE=PowerType.BUFF;
//    private static final PowerStrings powerStrings= CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
//    private static final String NAME=powerStrings.NAME;
//    private static final String[] DESCRIPTIONS=powerStrings.DESCRIPTIONS;
////    private static final String IMG_84=ModHelper.makeImgPath("power","EquipmentUpgrade84");
////    private static final String IMG_32=ModHelper.makeImgPath("power","EquipmentUpgrade32");
//
//    private ArrayList<AbstractCard> triggeredCards=new ArrayList<>();
//    public BATwinsEquipmentUpgradePower(AbstractCreature owner,ArrayList<AbstractCard> card){
//        this.owner=owner;
//        this.triggeredCards=card;
//    }
//
//    @Override
//    public void onPlayCard(AbstractCard card, AbstractMonster m) {
//        super.onPlayCard(card, m);
//    }
//
//}
