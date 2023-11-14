package baModDeveloper.action;

import baModDeveloper.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class BATwinsAddHandAccordColorAction extends AbstractGameAction {
    private AbstractCard.CardColor color;
    private UIStrings strings= CardCrawlGame.languagePack.getUIString(ModHelper.makePath("AddHandAccordColorAction"));
    private String[] TEXT=strings.TEXT;
    private ArrayList<AbstractCard> deckcards=new ArrayList<>();
    private AbstractPlayer p;
    public BATwinsAddHandAccordColorAction(AbstractCard.CardColor color){
        this.color=color;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration=this.startDuration= Settings.ACTION_DUR_FAST;
        this.p=AbstractDungeon.player;
    }

    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_FAST){
            if(this.p.drawPile.isEmpty()){
                this.isDone=true;
                return;
            }
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractCard c:this.p.drawPile.group){
                if(c.color!=this.color){
                    temp.addToTop(c);
                }
            }
            if(temp.isEmpty()){
                this.isDone=true;
                return;
            }
            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);
            AbstractDungeon.gridSelectScreen.open(temp,1,false,TEXT[0]);
            tickDuration();
            return;
        }
        if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
            for(AbstractCard c:AbstractDungeon.gridSelectScreen.selectedCards){
                if(this.p.hand.size()==10){
                    this.p.drawPile.moveToDiscardPile(c);
                    this.p.createHandIsFullDialog();
                    continue;
                }
                this.p.drawPile.moveToHand(c,this.p.drawPile);
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
        this.isDone=true;
    }
}
