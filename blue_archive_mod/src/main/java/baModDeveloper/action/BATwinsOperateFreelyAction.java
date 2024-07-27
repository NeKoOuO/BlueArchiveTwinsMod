package baModDeveloper.action;

import baModDeveloper.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class BATwinsOperateFreelyAction extends AbstractGameAction {
    private final AbstractCard.CardColor color;
    private final int numberOfConnect;
    public UIStrings GridSelectTitle = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    public BATwinsOperateFreelyAction(int amount, AbstractCard.CardColor color,int numberOfConnect){
        this.amount=amount;
        this.color=color;
        this.numberOfConnect=numberOfConnect;
        this.duration= Settings.ACTION_DUR_FAST;

    }
    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_FAST){
            ArrayList<AbstractCard> cards=generateCards();
            if(cards.isEmpty()){
                this.isDone=true;
                return;
            }
            AbstractDungeon.cardRewardScreen.customCombatOpen(cards, GridSelectTitle.TEXT[0],false);
            tickDuration();
            return;
        }
        if(AbstractDungeon.cardRewardScreen.discoveryCard!=null){
            AbstractCard card=AbstractDungeon.cardRewardScreen.discoveryCard;
            addToTop(new BATwinsPlayDrawPailCardAction(card,null,this.numberOfConnect));
            this.isDone=true;
            AbstractDungeon.cardRewardScreen.discoveryCard=null;
        }
    }

    private ArrayList<AbstractCard> generateCards(){
        ArrayList<AbstractCard> cards=new ArrayList();
        AbstractDungeon.player.drawPile.group.stream().filter(card -> card.color==this.color).forEach(cards::add);
        if(cards.size()<=this.amount){
            return cards;
        }
        ArrayList<AbstractCard> result=new ArrayList<>();
        for(int i=0;i<this.amount;i++){
            int index=AbstractDungeon.cardRandomRng.random(0,cards.size()-1);
            result.add(cards.get(index));
            cards.remove(index);
        }
        return result;
    }
}
