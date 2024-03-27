package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsBugCardAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard bugCard;

    public BATwinsBugCardAction(AbstractCard bugCard) {
        this.p = AbstractDungeon.player;
        this.bugCard = bugCard;
    }

    @Override
    public void update() {
        CardGroup temp = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        temp.group.addAll(this.p.hand.group);
        temp.removeCard(this.bugCard);
        AbstractCard c = temp.getRandomCard(AbstractDungeon.cardRandomRng);
//        this.exhaustedCard =c;
        addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand, false));
        this.isDone = true;
    }
}
