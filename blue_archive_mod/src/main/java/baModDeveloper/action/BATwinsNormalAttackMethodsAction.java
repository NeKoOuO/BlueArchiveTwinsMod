package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.power.BATwinsNoramlAttackAuxiliary;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsNormalAttackMethodsAction extends AbstractGameAction {
    private int amount;
    private AbstractPlayer p;
    private boolean ignoreShuffle;

    public BATwinsNormalAttackMethodsAction(int amount, boolean ignoreShuffle) {
        this.amount = amount;
        this.p = AbstractDungeon.player;
        this.ignoreShuffle = ignoreShuffle;
    }

    @Override
    public void update() {
        CardGroup g = this.p.drawPile;
        CardGroup strickCards = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for (AbstractCard c : g.group) {
            if (c.hasTag(AbstractCard.CardTags.STRIKE)) {
                strickCards.addToTop(c);
            }
        }
        if (!this.ignoreShuffle && strickCards.size() < this.amount && this.p.hasPower(BATwinsNoramlAttackAuxiliary.POWER_ID) && !this.p.discardPile.isEmpty()) {
            this.p.getPower(BATwinsNoramlAttackAuxiliary.POWER_ID).flash();
            addToTop(new BATwinsNormalAttackMethodsAction(this.amount, true));
            addToTop(new ShuffleAction(this.p.drawPile, false));
            addToTop(new EmptyDeckShuffleAction());
            this.isDone = true;
            return;
        }
        for (int i = 0; i < this.amount && !strickCards.isEmpty(); i++) {
            AbstractCard selectedCard = strickCards.getRandomCard(AbstractDungeon.cardRandomRng);
            if (selectedCard instanceof BATwinsModCustomCard) {
                ((BATwinsModCustomCard) selectedCard).modifyEnergyType = BATwinsEnergyPanel.EnergyType.SHARE;
            }
            this.p.drawPile.moveToHand(selectedCard);
            strickCards.removeCard(selectedCard);
        }
        this.isDone = true;
        tickDuration();
    }
}
