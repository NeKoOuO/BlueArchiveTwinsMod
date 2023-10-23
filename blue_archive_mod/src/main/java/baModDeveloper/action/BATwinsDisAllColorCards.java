package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.character.BATwinsCharacter;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsDisAllColorCards extends AbstractGameAction {
    private AbstractCard.CardColor color;
    public BATwinsDisAllColorCards(AbstractCard.CardColor color){
        this.color=color;
        this.duration= Settings.ACTION_DUR_FAST;
    }
    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_FAST){
            for(AbstractCard c: AbstractDungeon.player.hand.group){
                if(c instanceof BATwinsModCustomCard&&((BATwinsModCustomCard)c).getCardColor()==this.color){
                    addToTop(new DiscardSpecificCardAction(c));
                }
            }
        }
        this.isDone=true;
    }
}
