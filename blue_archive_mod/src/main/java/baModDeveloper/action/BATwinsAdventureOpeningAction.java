package baModDeveloper.action;

import baModDeveloper.cards.BATwinsAdventureOpening;
import baModDeveloper.cards.BATwinsModCustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BATwinsAdventureOpeningAction extends AbstractGameAction {
    ArrayList<AbstractCard> storageCards;
    AbstractCard card;

    public BATwinsAdventureOpeningAction(ArrayList<AbstractCard> StorageCards,AbstractCard card){
        this.storageCards=StorageCards;
        this.card=card;
        this.duration= Settings.ACTION_DUR_LONG;
    }
    @Override
    public void update() {
        for(AbstractCard c:storageCards){
            AbstractCard temp=c.makeSameInstanceOf();
            AbstractMonster m= AbstractDungeon.getRandomMonster();

            AbstractDungeon.player.limbo.addToBottom(temp);
            temp.current_x=card.current_x;
            temp.current_y=card.current_y;
            temp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            temp.target_y = Settings.HEIGHT / 2.0F;

            if(m!=null){
                temp.calculateCardDamage(m);
            }

            temp.purgeOnUse=true;

            if(temp instanceof BATwinsModCustomCard){
                ((BATwinsModCustomCard) temp).numberOfConnections=1;
            }
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(temp,m,card.energyOnUse,true,true),true);

        }
        addToBot(new BATwinsSelectAdventureCardAction(1, (BATwinsAdventureOpening) this.card));
        this.isDone=true;
        tickDuration();
    }
}
