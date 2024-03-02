package baModDeveloper.action;

import baModDeveloper.cards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class BATwinsBulletWarehouseAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private boolean upgraded;

    public BATwinsBulletWarehouseAction(boolean upgraded) {
        this.amount = 1;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_FAST;

    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new BATwinsContinuousShooting());
        cards.add(new BATwinsFocusShooting());
        cards.add(new BATwinsStableShooting());
        cards.add(new BATwinsRandomShooting());
        if (upgraded) {
            cards.forEach(AbstractCard::upgrade);
        }

        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(cards, CardRewardScreen.TEXT[1], true);
            tickDuration();
            return;
        }
        if (!this.retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard disCard1 = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
//                AbstractCard disCard2=AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                if (AbstractDungeon.player.hasPower(MasterRealityPower.POWER_ID)) {
                    disCard1.upgrade();
//                    disCard2.upgrade();
                }
                disCard1.current_x = -1000.0F * Settings.xScale;
//                disCard2.current_x=-1000.0F*Settings.xScale+AbstractCard.IMG_HEIGHT;
                if (AbstractDungeon.player.hand.size() < 10) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard1, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard1, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                }
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
                this.retrieveCard = true;
            }
        }
        tickDuration();

    }
}
