package baModDeveloper.action;

import baModDeveloper.cards.BATwinsMidoriStrick;
import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.cards.BATwinsMomoiStrick;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class BATwinsSkillCombinationAction extends AbstractGameAction {
    private int amount;
    private ArrayList<AbstractCard> canNotSelect;
    private AbstractPlayer p;
    private AbstractCard.CardColor color;
    private UIStrings UISTRINGS= CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    public BATwinsSkillCombinationAction(int amount, AbstractCard.CardColor color){
        this.amount=amount;
        this.canNotSelect=new ArrayList<>();
        this.p= AbstractDungeon.player;
        this.color=color;
        this.duration= Settings.ACTION_DUR_FAST;
    }
    @Override
    public void update() {
        if(this.duration==Settings.ACTION_DUR_FAST){
            if(this.p.drawPile.isEmpty()){
                this.isDone=true;
                return;
            }
            this.p.drawPile.group.stream().filter(card -> card.type!= AbstractCard.CardType.SKILL).forEach(card -> this.canNotSelect.add(card));
            if(this.canNotSelect.size()==this.p.drawPile.size()){
                this.isDone=true;
                return;
            }
            this.p.drawPile.group.removeAll(this.canNotSelect);
            AbstractDungeon.gridSelectScreen.open(this.p.drawPile,1,UISTRINGS.TEXT[1],false,false);
            tickDuration();
            return;
        }
        if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
            for(AbstractCard c:AbstractDungeon.gridSelectScreen.selectedCards){
                BATwinsModCustomCard card;
                if(this.color== BATwinsCharacter.Enums.BATWINS_MOMOI_CARD){
                    card=new BATwinsMomoiStrick();
                } else if (this.color==BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
                    card=new BATwinsMidoriStrick();
                }else{
                    card=new BATwinsMomoiStrick();
                }
                card.addBringOutCard(c);
                addToTop(new MakeTempCardInDrawPileAction(card,this.amount,true,true,false));
                addToTop(new ExhaustSpecificCardAction(c,this.p.drawPile,true));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            if(!this.canNotSelect.isEmpty()){
                this.p.drawPile.group.addAll(this.canNotSelect);
            }
            this.isDone=true;
            return;
        }
    }
}
