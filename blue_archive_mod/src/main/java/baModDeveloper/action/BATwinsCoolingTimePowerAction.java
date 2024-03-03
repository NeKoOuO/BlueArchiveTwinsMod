package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class BATwinsCoolingTimePowerAction extends AbstractGameAction {
    private ArrayList<AbstractCard> strogedCards;

    public BATwinsCoolingTimePowerAction(ArrayList<AbstractCard> strogedCards) {
        this.strogedCards = strogedCards;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        for (AbstractCard c : this.strogedCards) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                continue;
            }
            if (AbstractDungeon.player.drawPile.contains(c)) {
                AbstractDungeon.player.drawPile.moveToHand(c);
                c.lighten(false);
//                c.unhover();
                c.applyPowers();
            } else if (AbstractDungeon.player.discardPile.contains(c)) {
                //此处在修改，将牌从弃牌堆的位置划回手卡，而不是凭空出现
                AbstractDungeon.player.hand.addToHand(c);
                AbstractDungeon.player.discardPile.removeCard(c);
                c.lighten(false);
//                c.unhover();
                c.applyPowers();
            }
        }
        this.isDone = true;

    }
}
