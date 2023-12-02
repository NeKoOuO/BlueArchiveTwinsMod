package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.core.BATwinsEnergyManager;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class BATwinsCheatingCodeEnabledAction extends AbstractGameAction {
    private AbstractCard.CardColor color;
    private boolean gainEnergy;
    public BATwinsCheatingCodeEnabledAction(AbstractCard.CardColor color,boolean gainEnergy){
        this.color=color;
        this.gainEnergy=gainEnergy;
        this.target= AbstractDungeon.player;
        this.actionType= ActionType.WAIT;
        this.duration= Settings.ACTION_DUR_FAST;

    }
    @Override
    public void update() {
        addToTop(new BATwinsDrawCardByColor(this.color,10));
        addToTop(new WaitAction(0.5F));
        addToTop(new BATwinsDisCardByColorAction(BATwinsCharacter.getOtherColor(this.color)));

        if(this.gainEnergy){
            if(AbstractDungeon.player.energy instanceof BATwinsEnergyManager){
                if(this.color==BATwinsCharacter.Enums.BATWINS_MOMOI_CARD){
                    int MIDORICount=BATwinsEnergyPanel.MidoriCount;
                    BATwinsEnergyPanel.useEnergy(MIDORICount, BATwinsEnergyPanel.EnergyType.MIDORI);
                    BATwinsEnergyPanel.addEnergy(MIDORICount, BATwinsEnergyPanel.EnergyType.MOMOI);
                } else if (this.color==BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
                    int MOMOICount=BATwinsEnergyPanel.MomoiCount;
                    BATwinsEnergyPanel.useEnergy(MOMOICount, BATwinsEnergyPanel.EnergyType.MOMOI);
                    BATwinsEnergyPanel.addEnergy(MOMOICount, BATwinsEnergyPanel.EnergyType.MIDORI);
                }
            }
        }
        this.isDone=true;
        tickDuration();
    }
}
