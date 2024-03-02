package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.cards.bullets.BATwinsCustomBulletCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class BATwinsCopyBulletsAction extends AbstractGameAction {
    private final AbstractCard source;
    private final AbstractCard target;
    private final boolean clear;
    public BATwinsCopyBulletsAction(AbstractCard source,AbstractCard target,boolean clear){
        this.source=source;
        this.target=target;
        this.clear=clear;
        this.duration= Settings.ACTION_DUR_FAST;
    }
    @Override
    public void update() {
        if(source instanceof BATwinsModCustomCard&&target instanceof BATwinsModCustomCard){
            for(AbstractCard c:((BATwinsModCustomCard) source).getBringOutCards()){
                if(c instanceof BATwinsCustomBulletCard){
                    ((BATwinsModCustomCard) target).addBringOutCard(c);
                }
            }
        }
        if(clear&&source instanceof BATwinsModCustomCard){
            ((BATwinsModCustomCard) source).clearBringOutCards();
        }
        this.isDone=true;
    }
}
