package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsCoolingTimePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.Collections;
import java.util.Iterator;

public class BATwinsCoolingTimeAction extends AbstractGameAction {
    private int amount;
    private AbstractPlayer p;
    private boolean exchange;
    private UIStrings UISTRINGS = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    public BATwinsCoolingTimeAction(int amount, boolean exchange) {
        this.amount = amount;
        this.p = AbstractDungeon.player;
        this.source = this.p;
        this.target = this.p;
        this.exchange = exchange;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
            for (AbstractCard c : this.p.discardPile.group) {
                if (c instanceof BATwinsModCustomCard) {
                    if (this.exchange) {
                        if (c.color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD) {
                            temp.addToTop(c);
                        }
                    } else {
                        if (c.color == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
                            temp.addToTop(c);
                        }
                    }
                }
            }
            if (temp.isEmpty()) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(temp, this.amount, false, String.format(UISTRINGS.TEXT[10]+UISTRINGS.TEXT[3],this.amount));
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
//                addToTop(new ApplyPowerAction(this.p,this.p,new BATwinsCoolingTimePower()));
                if (this.p != null && !this.p.isDeadOrEscaped()) {
                    AbstractPower powToApply = new BATwinsCoolingTimePower(this.p, c);
                    if (this.source != null) {
                        Iterator iter1 = this.source.powers.iterator();
                        while (iter1.hasNext()) {
                            AbstractPower pow = (AbstractPower) iter1.next();
                            pow.onApplyPower(powToApply, this.target, this.source);
                        }
                    }
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
                    boolean hasBuffAlready = false;
                    for (AbstractPower p : this.p.powers) {
                        if (p instanceof BATwinsCoolingTimePower) {
                            ((BATwinsCoolingTimePower) p).stackPower(c);
                            p.flash();
                            p.updateDescription();
                            hasBuffAlready = true;
                            AbstractDungeon.onModifyPower();
                        }
                    }

                    if (!hasBuffAlready) {
                        this.p.powers.add(powToApply);
                        Collections.sort(this.target.powers);
                        powToApply.onInitialApplication();
                        powToApply.flash();
                        AbstractDungeon.onModifyPower();
                    }

                }
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.isDone = true;
        }
    }
}
