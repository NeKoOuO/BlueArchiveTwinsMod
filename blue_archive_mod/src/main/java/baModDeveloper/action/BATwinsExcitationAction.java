package baModDeveloper.action;

import baModDeveloper.helpers.ColorComparer;
import baModDeveloper.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BATwinsExcitationAction extends AbstractGameAction {
    private UIStrings UISTRINGS = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    public BATwinsExcitationAction(int amount) {
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            ArrayList<AbstractCard> powercards = CardLibrary.getAllCards().stream().filter(card -> card.type == AbstractCard.CardType.POWER && card.color != AbstractCard.CardColor.COLORLESS).collect(Collectors.toCollection(ArrayList::new));
            cardGroup.group.addAll(powercards);
            cardGroup.group.sort(new ColorComparer());
            this.amount = Math.min(this.amount, cardGroup.size());
            AbstractDungeon.gridSelectScreen.open(cardGroup, this.amount, String.format(UISTRINGS.TEXT[10] + UISTRINGS.TEXT[0], this.amount), false);
            tickDuration();
            return;
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    addToTop(new BATwinsPlayTempCardAction(c, 1));
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.isDone = true;
                return;
            }
        }


    }
}
