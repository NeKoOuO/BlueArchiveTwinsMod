package baModDeveloper.action;

import baModDeveloper.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class BATwinsPropCollectionAction extends AbstractGameAction {
    private int amount;
    private AbstractPlayer p;
    private UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    public BATwinsPropCollectionAction(int amount) {
        this.amount = amount;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
            temp.group.addAll(this.p.drawPile.group);
            temp.group.addAll(this.p.discardPile.group);
            if (temp.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (temp.size() <= this.amount) {
                for (AbstractCard card : temp.group) {
                    cardToDo(card);
                }
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(temp, this.amount, String.format(uiStrings.TEXT[10] + uiStrings.TEXT[5], this.amount), false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                cardToDo(card);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.isDone = true;
            tickDuration();
        }
    }

    public void cardToDo(AbstractCard card) {
        addToTop(new BATwinsDrawOrDisCardToHandAction(card));
    }
}
