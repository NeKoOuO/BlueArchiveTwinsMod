package baModDeveloper.action;

import baModDeveloper.cards.BATwinsAdventureOpening;
import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class BATwinsSelectAdventureCardAction extends AbstractGameAction {
    private int amount;
    private static final UIStrings uiStrings= CardCrawlGame.languagePack.getUIString(ModHelper.makePath("SelectAdventureCard"));
    public static final String[] TEXT=uiStrings.TEXT;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> isNotAdvCards=new ArrayList<>();
    BATwinsAdventureOpening card;
    public BATwinsSelectAdventureCardAction(int amount, BATwinsAdventureOpening card){
        this.amount=amount;
        this.card=card;
        this.p=AbstractDungeon.player;
        this.duration= Settings.ACTION_DUR_FAST;
    }
    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_FAST){
            for(AbstractCard c:this.p.hand.group){
                if(!c.hasTag(BATwinsModCustomCard.BATwinsCardTags.Adventure)||c.isInAutoplay){
                    this.isNotAdvCards.add(c);
                }
            }
            if(this.isNotAdvCards.size()==this.p.hand.group.size()){
                this.isDone=true;
                return;
            }
            if(this.p.hand.group.size()-this.isNotAdvCards.size()==1){
                for(AbstractCard c:this.p.hand.group){
                    if(c.hasTag(BATwinsModCustomCard.BATwinsCardTags.Adventure)){
                        this.card.addStorageCard(c);
                        addToBot(new ExhaustSpecificCardAction(c,this.p.hand));
                        this.isDone=true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.isNotAdvCards);
            if(this.p.hand.group.size()>1){
                String title=TEXT[0];
                AbstractDungeon.handCardSelectScreen.open(title,this.amount,true,true,false,false);
                tickDuration();
                return;
            }
            if(this.p.hand.group.size()==1){
                this.card.addStorageCard(this.p.hand.getTopCard());
                addToBot(new ExhaustSpecificCardAction(this.p.hand.getTopCard(),this.p.hand));
                this.isDone=true;
            }
        }

        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            for(AbstractCard c:AbstractDungeon.handCardSelectScreen.selectedCards.group){
                this.card.addStorageCard(c);
                addToBot(new ExhaustSpecificCardAction(c,this.p.hand));
                this.p.hand.addToTop(c);
            }
            for(AbstractCard c:this.isNotAdvCards){
                this.p.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved=true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone=true;
        }
        tickDuration();
    }
}
