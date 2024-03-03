//package baModDeveloper.power;
//
//import baModDeveloper.helpers.ModHelper;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.megacrit.cardcrawl.core.AbstractCreature;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.helpers.ImageMaster;
//import com.megacrit.cardcrawl.localization.PowerStrings;
//import com.megacrit.cardcrawl.powers.AbstractPower;
//
//public class BATwinsReduceDrawPower extends AbstractPower {
//    public static final String POWER_ID= ModHelper.makePath("ReduceDrawPower");
//    private static final AbstractPower.PowerType TYPE=PowerType.DEBUFF;
//    private static final PowerStrings powerStrings= CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
//    private static final String NAME=powerStrings.NAME;
//    private static final String[] DESCRIPTIONS=powerStrings.DESCRIPTIONS;
//    private static final String IMG_84=ModHelper.makeImgPath("power","ReduceDraw84");
//    private static final String IMG_32=ModHelper.makeImgPath("power","ReduceDraw32");
//
//    public BATwinsReduceDrawPower(AbstractCreature owner,int amount){
//        this.name=NAME;
//        this.ID=POWER_ID;
//        this.type=TYPE;
//        this.owner=owner;
//        this.region128=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84),0,0,84,84);
//        this.region48=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32),0,0,32,32);
//        this.amount=amount;
//    }
//
//
//}
