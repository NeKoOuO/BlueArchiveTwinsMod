package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.cards.bullets.BATwinsCustomBulletCard;
import baModDeveloper.character.BATwinsCharacter;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.List;

public class BATwinsUniversalBulletAction extends AbstractGameAction {
    private final AbstractCard.CardColor color;
    private final BATwinsCustomBulletCard bullet;
    public BATwinsUniversalBulletAction(AbstractCard.CardColor color,BATwinsCustomBulletCard bullet){
        this.color=color;
        this.bullet=bullet;
    }
    @Override
    public void update() {
        CardGroup temp=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractDungeon.player.hand.group.stream().filter(this::filter).forEach(temp::addToBottom);
        if(!temp.isEmpty()){
            AbstractCard card=temp.getRandomCard(true);
            if(card instanceof BATwinsModCustomCard){
                ((BATwinsModCustomCard) card).addBringOutCard(this.bullet);
                card.flash(BATwinsCharacter.getColorWithCardColor(card.color));
            }
        }
        this.isDone=true;
    }

    private void callback(List<AbstractCard> cards) {
        for(AbstractCard c:cards){
            if(c instanceof BATwinsModCustomCard){
                ((BATwinsModCustomCard) c).addBringOutCard(this.bullet);
            }
        }
    }

    private boolean filter(AbstractCard card){
        return card.color==this.color;
    }

}
