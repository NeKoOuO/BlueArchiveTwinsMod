package baModDeveloper.action;

import baModDeveloper.cards.BATwinsAdventureBegins;
import baModDeveloper.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class BATwinsAdventureBeginsAction extends AbstractGameAction {
    private ArrayList<AbstractCard.CardType> cardTypes;
    private int amount;
    private boolean anyNum;
    private AbstractPlayer p;
    private BATwinsAdventureBegins card;
    private ArrayList<AbstractCard> canNotSelect;
    private UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    public BATwinsAdventureBeginsAction(ArrayList<AbstractCard.CardType> cardTypes, int amount, boolean anyNum, BATwinsAdventureBegins card) {
        this.cardTypes = cardTypes;
        this.amount = amount;
        this.p = AbstractDungeon.player;
        this.anyNum = anyNum;
        this.card = card;
        this.canNotSelect = new ArrayList<>();
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            }
            this.p.hand.group.stream().filter(c -> cardTypes.contains(c.type) || c == card).forEach(c -> canNotSelect.add(c));
            if (this.p.hand.size() == this.canNotSelect.size()) {
                this.isDone = true;
                return;
            }
            this.p.hand.group.removeAll(this.canNotSelect);
            if (this.p.hand.size() == 1) {
                cardToDo(this.p.hand.getTopCard());
                this.p.hand.removeCard(this.p.hand.getTopCard());
                if (!this.canNotSelect.isEmpty()) {
                    this.p.hand.group.addAll(this.canNotSelect);
                }
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(uiStrings.TEXT[4], this.amount, this.anyNum, this.anyNum, false, false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.cardToDo(c);
            }
            if (!this.canNotSelect.isEmpty()) {
                this.p.hand.group.addAll(this.canNotSelect);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        }
        tickDuration();
    }

    private void cardToDo(AbstractCard card) {
        this.card.addBringOutCard(card);
    }
}
