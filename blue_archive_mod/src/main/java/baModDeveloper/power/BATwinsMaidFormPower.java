package baModDeveloper.power;

import baModDeveloper.action.BATwinsPlayTempCardAction;
import baModDeveloper.action.BATwinsPropCollectionAction;
import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.cards.colorless.BATwinsAccelerate;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

public class BATwinsMaidFormPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("MaidFormPower");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_84 = ModHelper.makeImgPath("power", "MaidForm84");
    private static final String IMG_32 = ModHelper.makeImgPath("power", "MaidForm32");
//    private static final Random rng = new Random();
    private int cardsToPlayThisTurn=0;

    public BATwinsMaidFormPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = TYPE;
        this.owner = owner;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32), 0, 0, 32, 32);
        this.amount = amount;

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
//        addToBot(new BATwinsPropCollectionAction(this.amount));

    }

    @Override
    public void atStartOfTurn() {
        this.cardsToPlayThisTurn=0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(!card.purgeOnUse&&this.amount>0&& AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - this.cardsToPlayThisTurn <= this.amount){
            this.cardsToPlayThisTurn++;
            flash();
            AbstractMonster m= (AbstractMonster) action.target;

            BATwinsAccelerate temp=new BATwinsAccelerate();
            temp.purgeOnUse=true;
            temp.freeToPlayOnce=true;
            temp.energyOnUse=card.cost;
            temp.connectionCost=card.cost;

            AbstractDungeon.player.limbo.addToBottom(temp);
            temp.current_x = card.current_x;
            temp.current_y = card.current_y;
            temp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            temp.target_y = Settings.HEIGHT / 2.0F;

            if(m!=null){
                temp.calculateCardDamage(m);
            }

            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(temp,m,card.energyOnUse,true,true),true);
        }
    }
}
