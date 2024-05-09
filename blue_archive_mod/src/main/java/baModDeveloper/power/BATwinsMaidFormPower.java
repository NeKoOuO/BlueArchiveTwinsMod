package baModDeveloper.power;

import baModDeveloper.action.BATwinsAccelerateAction;
import baModDeveloper.action.BATwinsMaidFormAction;
import baModDeveloper.cards.colorless.BATwinsAccelerate;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.patch.BATwinsAbstractCardPatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
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

import java.util.List;

public class BATwinsMaidFormPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("MaidFormPower");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_84 = ModHelper.makeImgPath("power", "MaidForm84");
    private static final String IMG_32 = ModHelper.makeImgPath("power", "MaidForm32");
    //    private static final Random rng = new Random();
    private int cardsToPlayThisTurn = 0;

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
        this.cardsToPlayThisTurn = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && this.amount > 0 && this.cardsToPlayThisTurn < this.amount) {
            this.cardsToPlayThisTurn++;
            flash();
            addToBot(new BATwinsMaidFormAction(card.costForTurn, (AbstractMonster) action.target));
        }
    }
}
