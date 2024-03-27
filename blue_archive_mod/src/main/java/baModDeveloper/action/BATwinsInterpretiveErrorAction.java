package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class BATwinsInterpretiveErrorAction extends AbstractGameAction {
    private boolean upgraded;
    private int amount;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotexchange;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("InterpretiveErrorAction"));
    public static final String[] TEXT = uiStrings.TEXT;

    public BATwinsInterpretiveErrorAction(boolean upgraded, int amount) {
        this.upgraded = upgraded;
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.cannotexchange = new ArrayList<>();
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (c.color != BATwinsCharacter.Enums.BATWINS_MOMOI_CARD && c.color != BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
                    this.cannotexchange.add(c);
                } else if (c.isInAutoplay) {
                    this.cannotexchange.add(c);
                }
            }
            if (this.cannotexchange.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() - this.cannotexchange.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (c.color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD || c.color == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
                        ((BATwinsModCustomCard) c).conversionColor();
                        c.freeToPlayOnce = true;
                        if (this.upgraded) {
                            c.upgrade();
                        }
                        c.superFlash();
                        c.applyPowers();
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.cannotexchange);
            if (this.p.hand.group.size() > 1) {
//                String title=TEXT[0];
//                if(this.amount==1){
//                    title+=EXTRETEXT[0];
//                }else {
//                    title+=EXTRETEXT[1];
//                }
//                title+=TEXT[1];
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, true, false, false);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
                AbstractCard card = this.p.hand.getTopCard();
                ((BATwinsModCustomCard) card).conversionColor();
                if (this.upgraded) {
                    card.upgrade();
                }
                card.freeToPlayOnce = true;
                this.p.hand.getTopCard().superFlash();
                this.p.hand.getTopCard().applyPowers();
                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                ((BATwinsModCustomCard) c).conversionColor();
                if (this.upgraded) {
                    c.upgrade();
                }
                c.freeToPlayOnce = true;
                c.superFlash();
                c.applyPowers();
                this.p.hand.addToTop(c);
            }
            for (AbstractCard c : this.cannotexchange) {
                this.p.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
    }
}
