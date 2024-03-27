package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.power.BATwinsMasterCraftsmanshipPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsCoversionColorAction extends AbstractGameAction {
    private BATwinsModCustomCard card;
    private boolean flash;

    public BATwinsCoversionColorAction(BATwinsModCustomCard card, boolean flash) {
        this.card = card;
        this.flash = flash;
    }

    public BATwinsCoversionColorAction(BATwinsModCustomCard card) {
        this(card, true);
    }

    @Override
    public void update() {

        card.conversionColor(flash);
        //如果有技艺大师buff则升级
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(BATwinsMasterCraftsmanshipPower.POWER_ID)) {
            card.upgrade();
            AbstractDungeon.player.getPower(BATwinsMasterCraftsmanshipPower.POWER_ID).flash();
        }
        this.isDone = true;
    }
}
