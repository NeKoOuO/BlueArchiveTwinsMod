package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class BATwinsDrawOrDisCardToHandAction extends AbstractGameAction {

    private final AbstractCard card;
    private final CardGroup group;
    public BATwinsDrawOrDisCardToHandAction(AbstractCard card,CardGroup group){
        this.card=card;
        this.group=group;
        this.actionType= ActionType.CARD_MANIPULATION;
        this.duration= Settings.ACTION_DUR_MED;
    }
    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_MED){
            if(this.group.isEmpty()){
                this.isDone=true;
                return;
            }
            card.unhover();
            card.lighten(true);
            group.removeCard(card);
            AbstractDungeon.player.hand.addToTop(card);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();

        }
        this.isDone=true;
        tickDuration();
    }
}
