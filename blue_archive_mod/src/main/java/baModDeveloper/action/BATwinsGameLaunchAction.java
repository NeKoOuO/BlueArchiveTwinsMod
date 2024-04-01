package baModDeveloper.action;

import baModDeveloper.character.BATwinsCharacter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsGameLaunchAction extends AbstractGameAction {
    boolean upgraded;

    public BATwinsGameLaunchAction(boolean upgraded) {
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        if (!this.isDone) {
            if (!upgraded) {
                CardGroup MOMOICards = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
                CardGroup MIDORICards = new CardGroup(CardGroup.CardGroupType.CARD_POOL);

                for (AbstractCard c : DrawCardAction.drawnCards) {
                    if (c.color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD) {
                        MOMOICards.addToTop(c);
                    } else if (c.color == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
                        MIDORICards.addToTop(c);
                    }
                }
                if (!MOMOICards.isEmpty()) {
                    MOMOICards.getRandomCard(AbstractDungeon.cardRandomRng).setCostForTurn(0);
                }
                if (!MIDORICards.isEmpty()) {
                    MIDORICards.getRandomCard(AbstractDungeon.cardRandomRng).setCostForTurn(0);
                }
            } else {
                CardGroup handcards = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
                for (AbstractCard c : DrawCardAction.drawnCards) {
                    handcards.addToTop(c);
                }
                if (!handcards.isEmpty()) {
                    AbstractCard firstcard = handcards.getRandomCard(AbstractDungeon.cardRandomRng);
                    firstcard.setCostForTurn(0);
                    handcards.removeCard(firstcard);
                    if(!handcards.isEmpty()){
                        AbstractCard secondcard = handcards.getRandomCard(AbstractDungeon.cardRandomRng);
                        secondcard.setCostForTurn(0);
                    }

                }
            }
        this.isDone=true;
        }
    }
}
