package baModDeveloper.action;

import baModDeveloper.cards.BATwinsBugCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Objects;

public class BATwinsPlotRepairAction extends AbstractGameAction {
    private boolean upgraded;
    private AbstractPlayer p;

    public BATwinsPlotRepairAction(boolean upgraded) {
        this.upgraded = upgraded;
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;

    }

    @Override
    public void update() {
        addToTop(new MakeTempCardInDiscardAction(new BATwinsBugCard(), 1));
        for (AbstractCard c : this.p.drawPile.group) {
            if (Objects.equals(c.cardID, BATwinsBugCard.ID)) {
                addToTop(new ExhaustSpecificCardAction(c, this.p.drawPile));
                if (!this.upgraded) {
                    break;
                }
            }
        }
        tickDuration();
        this.isDone = true;
    }
}
