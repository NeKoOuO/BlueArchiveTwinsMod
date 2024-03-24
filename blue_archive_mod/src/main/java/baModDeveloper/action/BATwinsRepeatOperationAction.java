package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsMasterCraftsmanshipPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class BATwinsRepeatOperationAction extends AbstractGameAction {
    private int amount;
    private ArrayList<AbstractCard> canNotSelect;
    private AbstractPlayer p;
    private UIStrings UISTRINGS = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    public BATwinsRepeatOperationAction(int amount) {
        this.amount = amount;
        this.canNotSelect = new ArrayList<>();
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }
            for (AbstractCard card : this.p.hand.group) {
                if (!(card instanceof BATwinsModCustomCard) || card.isInAutoplay || card.type == AbstractCard.CardType.POWER) {
                    canNotSelect.add(card);
                }
            }
            if (this.canNotSelect.size() == this.p.hand.size()) {
                this.isDone = true;
                return;
            }
            this.p.hand.group.removeAll(this.canNotSelect);
            this.amount = Math.min(this.amount, this.p.hand.size());
            if (!this.p.hand.group.isEmpty()) {
                AbstractDungeon.handCardSelectScreen.open(String.format(UISTRINGS.TEXT[2], this.amount), this.amount, true, true, false);
                tickDuration();
                return;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (c instanceof BATwinsModCustomCard) {
                    BATwinsModCustomCard temp = (BATwinsModCustomCard) c.makeSameInstanceOf();
                    temp.conversionColor();
                    //如果有技艺大师buff则升级
                    if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(BATwinsMasterCraftsmanshipPower.POWER_ID)) {
                        temp.upgrade();
                        AbstractDungeon.player.getPower(BATwinsMasterCraftsmanshipPower.POWER_ID).flash();
                    }
                    addToTop(new BATwinsMakeTempCardInHandAction(temp, true, temp.exhaust, false, temp.isEthereal, temp.selfRetain));
                    this.p.hand.addToTop(c);
                }
            }
            if (!this.canNotSelect.isEmpty()) {
                this.p.hand.group.addAll(this.canNotSelect);
            }
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            this.isDone = true;
        }
    }
}
