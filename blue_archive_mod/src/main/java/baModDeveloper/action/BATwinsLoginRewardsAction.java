package baModDeveloper.action;

import baModDeveloper.cards.BATwinsLoginRewards;
import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.character.BATwinsCharacter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsLoginRewardsAction extends AbstractGameAction {
    private boolean upgraded;
    private AbstractCard.CardColor color;
    private BATwinsLoginRewards card;

    public BATwinsLoginRewardsAction(boolean upgraded, AbstractCard.CardColor color) {
        this.upgraded = upgraded;
        this.color = color;
        this.card = new BATwinsLoginRewards();
        if (this.upgraded) {
            card.upgrade();
        }
        this.target = AbstractDungeon.player;
    }

    @Override
    public void update() {
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.color == this.color && c instanceof BATwinsModCustomCard) {
                if (!((BATwinsModCustomCard) c).hasCardInBringOutCards(this.card)) {
                    tmp.addToTop(c);
                }
            }
        }
        if (tmp.isEmpty()) {
            this.isDone = true;
            return;
        }
        AbstractCard c = tmp.getRandomCard(AbstractDungeon.cardRandomRng);
        if (c instanceof BATwinsModCustomCard) {
            ((BATwinsModCustomCard) c).addBringOutCard(this.card);
            c.flash(BATwinsCharacter.getColorWithCardColor(c.color));
        }
        this.isDone = true;

    }
}
