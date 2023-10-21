package baModDeveloper.power;

import baModDeveloper.BATwinsMod;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static baModDeveloper.character.BATwinsCharacter.Enums.BATwins;

public class BATwinsDeveloperCollaborationPower extends AbstractPower {
    public static final String POWER_ID= ModHelper.makePath("DeveloperCollaborationPower");
    private static final AbstractPower.PowerType TYPE=PowerType.BUFF;
    private static final PowerStrings powerStrings= CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME=powerStrings.NAME;
    private static final String[] DESCRIPTIONS=powerStrings.DESCRIPTIONS;
    private static final String IMG_84=ModHelper.makeImgPath("power","DeveloperCollaboration84");
    private static final String IMG_32=ModHelper.makeImgPath("power","DeveloperCollaboration32");

    private AbstractCard.CardColor lastColor;
    public BATwinsDeveloperCollaborationPower(AbstractCreature owner,int amount){
        this.name=NAME;
        this.ID=POWER_ID;
        this.type=TYPE;
        this.owner=owner;
        this.amount=amount;
        this.region128=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84),0,0,84,84);
        this.region48=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32),0,0,32,32);
        this.updateDescription();

    }
    @Override
    public void updateDescription(){
        String lastcard="";
        if(this.lastColor==null){
            lastcard=DESCRIPTIONS[9];
        }
        else if (this.lastColor==BATwinsCharacter.Enums.BATWINS_MOMOI_CARD){
            lastcard=DESCRIPTIONS[4];
        } else if (this.lastColor==BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
            lastcard=DESCRIPTIONS[5];
        } else if (this.lastColor== AbstractCard.CardColor.COLORLESS) {
            lastcard=DESCRIPTIONS[6];
        }else if (this.lastColor==AbstractCard.CardColor.CURSE){
            lastcard=DESCRIPTIONS[7];
        }else{
            lastcard=DESCRIPTIONS[8];
        }
        this.description=DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1]+DESCRIPTIONS[2]+lastcard+DESCRIPTIONS[3];
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if(this.lastColor==null){
            this.lastColor=usedCard.color;
        }
        else if(AbstractDungeon.actionManager.cardsPlayedThisTurn.size()>=2){
            if((this.lastColor== BATwinsCharacter.Enums.BATWINS_MOMOI_CARD&&usedCard.color==BATwinsCharacter.Enums.BATWINS_MIDORI_CARD)||(this.lastColor==BATwinsCharacter.Enums.BATWINS_MIDORI_CARD&&usedCard.color==BATwinsCharacter.Enums.BATWINS_MOMOI_CARD)){
                addToBot(new DrawCardAction(this.amount));
                this.flash();
                this.lastColor=usedCard.color;
            }
        }
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.lastColor=null;
        this.updateDescription();
    }
}
