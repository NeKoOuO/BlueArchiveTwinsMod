package baModDeveloper.power;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BATwinsSeeYouHaveASharenPower extends AbstractPower {
    public static final String POWER_ID= ModHelper.makePath("SeeYouHaveASharenPower");
    private static final AbstractPower.PowerType TYPE=PowerType.BUFF;
    private static final PowerStrings powerStrings= CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME=powerStrings.NAME;
    private static final String[] DESCRIPTIONS=powerStrings.DESCRIPTIONS;
    private static final String IMG_84=ModHelper.makeImgPath("power","SeeYouHaveASharen84");
    private static final String IMG_32=ModHelper.makeImgPath("power","SeeYouHaveASharen32");

    public BATwinsSeeYouHaveASharenPower(AbstractCreature owner,int amount){
        this.name=NAME;
        this.ID=POWER_ID;
        this.owner=owner;
        this.type=TYPE;
        this.amount=amount;
        this.region128=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84),0,0,84,84);
        this.region48=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32),0,0,32,32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description=DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(!card.purgeOnUse&&card.type==AbstractCard.CardType.ATTACK&&(card.target== AbstractCard.CardTarget.ENEMY||card.target==AbstractCard.CardTarget.SELF_AND_ENEMY)&&this.amount>0){
            flash();
            AbstractMonster m=null;
            if(action.target!=null){
                m= (AbstractMonster) action.target;
            }

            for(AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters){
                if(mo!=m&&!mo.isDeadOrEscaped()){
                    AbstractCard temp=card.makeSameInstanceOf();
                    AbstractDungeon.player.limbo.addToBottom(temp);
                    temp.current_x=card.current_x;
                    temp.current_y=card.current_y;
                    temp.target_x= Settings.WIDTH/2.0F-300.0F*Settings.scale;
                    temp.target_y=Settings.HEIGHT/2.0F;

                    if(mo!=null){
                        temp.calculateCardDamage(mo);
                    }
                    temp.purgeOnUse=true;
                    if(temp instanceof BATwinsModCustomCard){
                        ((BATwinsModCustomCard) temp).numberOfConnections=1;
                    }
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(temp,mo,card.energyOnUse,true,true),true);

                }
            }
            this.amount--;
            if(this.amount==0){
                addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this.ID));
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer){
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this.ID));
        }
    }
}
