package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.character.BATwinsCharacter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsRandomShootingAction extends AbstractGameAction {
    private AbstractCard card;
    private boolean clear;
    public BATwinsRandomShootingAction(AbstractCard card,boolean clear){
        this.card=card;
        this.clear=clear;
    }
    @Override
    public void update() {
        CardGroup temp=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractDungeon.player.hand.group.stream().filter(card1 -> card1.hasTag(BATwinsModCustomCard.BATwinsCardTags.Shooting)).forEach(temp::addToBottom);
        if(temp.isEmpty()){
            this.isDone=true;
            return;
        }
        AbstractCard target=temp.getRandomCard(AbstractDungeon.cardRandomRng);
        target.flash(BATwinsCharacter.getColorWithCardColor(target.color));
        addToTop(new BATwinsCopyBulletsAction(this.card,target,this.clear));
        this.isDone=true;
    }
}
