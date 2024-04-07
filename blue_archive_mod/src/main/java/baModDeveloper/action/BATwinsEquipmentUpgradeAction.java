package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BATwinsEquipmentUpgradeAction extends SelectCardsInHandAction {
    private static final Predicate<AbstractCard> cardFilter = card -> {
        return true;
    };
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));
    private static final Consumer<List<AbstractCard>> callback = cardList -> {
        for (AbstractCard card : cardList) {
            card.upgrade();
            if (!card.name.contains("◆")) {
                card.name += "◆";
            }
            if (card instanceof BATwinsModCustomCard) {
                ((BATwinsModCustomCard) card).modifyEnergyType = BATwinsEnergyPanel.EnergyType.SHARE;
            }
            card.selfRetain = true;
//            card.exhaust = false;
//            card.isEthereal = false;
        }
    };

    public BATwinsEquipmentUpgradeAction(int amount) {
        super(amount, uiStrings.TEXT[6], false, false, cardFilter, callback);
    }
}
