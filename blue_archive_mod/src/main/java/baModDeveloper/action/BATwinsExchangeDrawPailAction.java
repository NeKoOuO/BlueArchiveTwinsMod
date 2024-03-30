package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.helpers.ModHelper;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BATwinsExchangeDrawPailAction extends SelectCardsAction {
    private static final Predicate<AbstractCard> cardFilter = card -> {
        return card instanceof BATwinsModCustomCard;
    };
    private static final Consumer<List<AbstractCard>> callback = cardList -> {
        for (AbstractCard c : cardList) {
            AbstractDungeon.actionManager.addToBottom(new BATwinsCoversionColorAction((BATwinsModCustomCard) c, false));
        }
    };
    private static final UIStrings uistrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    public BATwinsExchangeDrawPailAction(int amount) {
        super(AbstractDungeon.player.drawPile.group, amount, String.format(uistrings.TEXT[10]+uistrings.TEXT[7],amount), false, cardFilter, callback);
    }
}
