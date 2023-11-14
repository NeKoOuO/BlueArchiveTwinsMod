package baModDeveloper.power;

import baModDeveloper.cards.BATwinsSinglePlayerGame;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class BATwinsSinglePlayerGamePower extends AbstractPower {
    public static final String POWER_ID= ModHelper.makePath("SinglePlayerGamePower");
    private static final AbstractPower.PowerType TYPE=PowerType.BUFF;
    private static final PowerStrings powerStrings= CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME=powerStrings.NAME;
    private static final String[] DESCRIPTIONS=powerStrings.DESCRIPTIONS;
    private static final String IMG_84=ModHelper.makeImgPath("power","SinglePlayerGame84");
    private static final String IMG_32=ModHelper.makeImgPath("power","SinglePlayerGame32");

    public BATwinsSinglePlayerGamePower(AbstractCreature owner,int amount){
        this.ID=POWER_ID;
        this.name=NAME;
        this.type=TYPE;
        this.owner=owner;
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
    public void onAfterCardPlayed(AbstractCard usedCard) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(AbstractDungeon.player.hand.size()<=0){
                    this.isDone=true;
                    return;
                }
                boolean same=true;
                ArrayList<AbstractCard> temp = new ArrayList<>(AbstractDungeon.player.hand.group);
                temp.remove(usedCard);
                AbstractCard.CardColor firstColor = temp.get(0).color;
                if(temp.stream().allMatch(obj -> (obj.color.equals(firstColor)))){
                    BATwinsSinglePlayerGamePower.this.flash();
                    addToTop(new GainBlockAction(BATwinsSinglePlayerGamePower.this.owner,BATwinsSinglePlayerGamePower.this.amount));
                }
                this.isDone=true;
            }
        });


    }

}
