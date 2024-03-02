package baModDeveloper.action;

import baModDeveloper.cards.BATwinsBattleCommand;
import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class BATwinsBattleCommandAction extends AbstractGameAction {
    private ArrayList<AbstractCard> cannotSelect = new ArrayList<>();
    private AbstractPlayer p = AbstractDungeon.player;
    private UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("BattleCommandAction"));
    private String[] TEXT = uiStrings.TEXT;

    public BATwinsBattleCommandAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : p.hand.group) {
                if (!(c instanceof BATwinsModCustomCard) || c.isInAutoplay) {
                    cannotSelect.add(c);
                }
            }
            if (cannotSelect.size() == p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            if (p.hand.group.size() - cannotSelect.size() == 1) {
                for (AbstractCard c : p.hand.group) {
                    if (c instanceof BATwinsModCustomCard) {
                        this.p.hand.moveToDeck(c, true);
                        addToTop(new BATwinsAddHandAccordColorAction(c.color, true));
                        break;
                    }
                }
                this.isDone = true;
                tickDuration();
                return;
            } else {
                this.p.hand.group.removeAll(cannotSelect);
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard.CardColor drawColor = null;
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.p.hand.moveToDeck(c, true);
                drawColor = c.color;
            }
            this.p.hand.group.addAll(this.cannotSelect);
            this.p.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            addToTop(new BATwinsAddHandAccordColorAction(drawColor, true));
        }
        tickDuration();
    }
}
