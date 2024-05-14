package baModDeveloper.action;

import baModDeveloper.helpers.ModHelper;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;

public class BATwinsMaidFormAction extends AbstractGameAction {
    private final int costMax;
    private final AbstractMonster target;
    private final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    public BATwinsMaidFormAction(int costMax, AbstractMonster target) {
        this.costMax = costMax;
        this.target = target;
    }

    @Override
    public void update() {
        addToTop(new SelectCardsAction(AbstractDungeon.player.drawPile.group, 1, String.format(uiStrings.TEXT[10]+uiStrings.TEXT[0],1), false, this::filter, this::callback));
        this.isDone = true;
    }

    private void callback(List<AbstractCard> cards) {
        for (AbstractCard card : cards) {
            addToTop(new BATwinsPlayDrawPailCardAction(card, target, false, 1));
        }
    }

    private boolean filter(AbstractCard card) {
        return card.cost <= this.costMax&&card.cost!=-2;
    }
}
