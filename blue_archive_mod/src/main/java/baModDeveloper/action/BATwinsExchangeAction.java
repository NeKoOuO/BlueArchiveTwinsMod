package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsMasterCraftsmanshipPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class BATwinsExchangeAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));
    public static final String[] TEXT = uiStrings.TEXT;
    public static final String[] EXTRETEXT = uiStrings.EXTRA_TEXT;
    private int amount;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotexchange = new ArrayList<>();
    private boolean playBackOriginColor;

    public BATwinsExchangeAction(int amount, boolean playBackOriginColor) {
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.playBackOriginColor = playBackOriginColor;
    }

    public BATwinsExchangeAction(int amount) {
        this(amount, false);
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
//            if(this.p.hand.group.size()-this.cannotexchange.size()==1){
//                for(AbstractCard c:this.p.hand.group){
//                    if(c.color== BATwinsCharacter.Enums.BATWINS_MOMOI_CARD||c.color==BATwinsCharacter.Enums.BATWINS_MIDORI_CARD){
//                        ((BATwinsModCustomCard)c).conversionColor();
//                        ((BATwinsModCustomCard) c).playBackOriginalColor=this.playBackOriginColor;
//                        c.applyPowers();
//                        this.isDone=true;
//                        return;
//                    }
//                }
//            }
            this.p.hand.group.removeAll(this.cannotexchange);
            if (!this.p.hand.group.isEmpty()) {
//                String title=TEXT[0];
//                if(this.amount==1){
//                    title+=EXTRETEXT[0];
//                }else {
//                    title+=EXTRETEXT[1];
//                }
//                title+=TEXT[1];
                AbstractDungeon.handCardSelectScreen.open(uiStrings.TEXT[7], this.amount, true, true, false, false);
                tickDuration();
                return;
            } else {
                this.p.hand.group.addAll(this.cannotexchange);
                this.isDone = true;
                return;
            }
//            if(this.p.hand.group.size()==1){
//                ((BATwinsModCustomCard)this.p.hand.getTopCard()).conversionColor();
//                ((BATwinsModCustomCard)this.p.hand.getTopCard()).playBackOriginalColor=this.playBackOriginColor;
//                this.p.hand.getTopCard().applyPowers();
//                this.isDone=true;
//            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                ((BATwinsModCustomCard) c).conversionColor();
                //如果有技艺大师buff则升级
                if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(BATwinsMasterCraftsmanshipPower.POWER_ID)) {
                    c.upgrade();
                    AbstractDungeon.player.getPower(BATwinsMasterCraftsmanshipPower.POWER_ID).flash();
                }
                ((BATwinsModCustomCard) c).playBackOriginalColor = this.playBackOriginColor;
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
