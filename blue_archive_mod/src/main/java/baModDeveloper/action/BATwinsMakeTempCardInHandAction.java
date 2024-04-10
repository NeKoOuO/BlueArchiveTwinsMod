//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.modifier.BATwinsEtherealModifier;
import baModDeveloper.modifier.BATwinsExhaustModifier;
import baModDeveloper.modifier.BATwinsRetainModifier;
import baModDeveloper.modifier.BATwinsSharedModifier;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class BATwinsMakeTempCardInHandAction extends AbstractGameAction {
    private static final float PADDING;

    static {
        PADDING = 25.0F * Settings.scale;
    }

    private AbstractCard c;
    private boolean isOtherCardInCenter;
    private boolean sameUUID;
    private boolean exhaust;
    private boolean isEthereal;
    private boolean exhaustOnUseOnce;
    private boolean selfRetain;

    public BATwinsMakeTempCardInHandAction(AbstractCard card, boolean isOtherCardInCenter) {
        this.isOtherCardInCenter = true;
        this.sameUUID = false;
        UnlockTracker.markCardAsSeen(card.cardID);
        this.amount = 1;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.c = card;
        if (this.c.type != CardType.CURSE && this.c.type != CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
            this.c.upgrade();
        }

        this.isOtherCardInCenter = isOtherCardInCenter;
    }

    public BATwinsMakeTempCardInHandAction(AbstractCard card) {
        this(card, 1);
    }

    public BATwinsMakeTempCardInHandAction(AbstractCard card, int amount) {
        this.isOtherCardInCenter = true;
        this.sameUUID = false;
        UnlockTracker.markCardAsSeen(card.cardID);
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.c = card;
        if (this.c.type != CardType.CURSE && this.c.type != CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
            this.c.upgrade();
        }

    }

    public BATwinsMakeTempCardInHandAction(AbstractCard card, int amount, boolean isOtherCardInCenter) {
        this(card, amount);
        this.isOtherCardInCenter = isOtherCardInCenter;
    }

    public BATwinsMakeTempCardInHandAction(AbstractCard card, boolean isOtherCardInCenter, boolean sameUUID) {
        this(card, 1);
        this.isOtherCardInCenter = isOtherCardInCenter;
        this.sameUUID = sameUUID;
    }

    public BATwinsMakeTempCardInHandAction(AbstractCard card, boolean isOtherCardInCenter, boolean exhuast, boolean exhaustOnUseOnce, boolean isEthereal, boolean selfRetain) {
        this(card, isOtherCardInCenter);
        this.exhaust = exhuast;
        this.exhaustOnUseOnce = exhaustOnUseOnce;
        this.isEthereal = isEthereal;
        this.selfRetain = selfRetain;
    }

    public void update() {
        if (this.amount == 0) {
            this.isDone = true;
        } else {
            int discardAmount = 0;
            int handAmount = this.amount;
            if (this.amount + AbstractDungeon.player.hand.size() > 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                discardAmount = this.amount + AbstractDungeon.player.hand.size() - 10;
                handAmount -= discardAmount;
            }

            this.addToHand(handAmount);
            this.addToDiscard(discardAmount);
            if (this.amount > 0) {
                this.addToTop(new WaitAction(0.8F));
            }

            this.isDone = true;
        }
    }

    private void addToHand(int handAmt) {
        int i;
        switch (this.amount) {
            case 0:
                break;
            case 1:
                if (handAmt == 1) {
                    if (this.isOtherCardInCenter) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), (float) Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), (float) Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard()));
                    }
                }
                break;
            case 2:
                if (handAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), (float) Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), (float) Settings.HEIGHT / 2.0F));
                } else if (handAmt == 2) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), (float) Settings.WIDTH / 2.0F + PADDING + AbstractCard.IMG_WIDTH, (float) Settings.HEIGHT / 2.0F));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), (float) Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), (float) Settings.HEIGHT / 2.0F));
                }
                break;
            case 3:
                if (handAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), (float) Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), (float) Settings.HEIGHT / 2.0F));
                } else if (handAmt == 2) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), (float) Settings.WIDTH / 2.0F + PADDING + AbstractCard.IMG_WIDTH, (float) Settings.HEIGHT / 2.0F));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), (float) Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), (float) Settings.HEIGHT / 2.0F));
                } else if (handAmt == 3) {
                    for (i = 0; i < this.amount; ++i) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard()));
                    }
                }
                break;
            default:
                for (i = 0; i < handAmt; ++i) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), MathUtils.random((float) Settings.WIDTH * 0.2F, (float) Settings.WIDTH * 0.8F), MathUtils.random((float) Settings.HEIGHT * 0.3F, (float) Settings.HEIGHT * 0.7F)));
                }
        }

    }

    private void addToDiscard(int discardAmt) {
        switch (this.amount) {
            case 0:
                break;
            case 1:
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), (float) Settings.WIDTH / 2.0F + PADDING + AbstractCard.IMG_WIDTH, (float) Settings.HEIGHT / 2.0F));
                }
                break;
            case 2:
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), (float) Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), (float) Settings.HEIGHT * 0.5F));
                } else if (discardAmt == 2) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), (float) Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), (float) Settings.HEIGHT * 0.5F));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), (float) Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH * 0.5F, (float) Settings.HEIGHT * 0.5F));
                }
                break;
            case 3:
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), (float) Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH, (float) Settings.HEIGHT * 0.5F));
                } else if (discardAmt == 2) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), (float) Settings.WIDTH * 0.5F, (float) Settings.HEIGHT * 0.5F));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), (float) Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH, (float) Settings.HEIGHT * 0.5F));
                } else if (discardAmt == 3) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), (float) Settings.WIDTH * 0.5F, (float) Settings.HEIGHT * 0.5F));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), (float) Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH), (float) Settings.HEIGHT * 0.5F));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), (float) Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH, (float) Settings.HEIGHT * 0.5F));
                }
                break;
            default:
                for (int i = 0; i < discardAmt; ++i) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), MathUtils.random((float) Settings.WIDTH * 0.2F, (float) Settings.WIDTH * 0.8F), MathUtils.random((float) Settings.HEIGHT * 0.3F, (float) Settings.HEIGHT * 0.7F)));
                }
        }

    }

    private AbstractCard makeNewCard() {
        AbstractCard card;
        if (this.sameUUID) {
            card = this.c.makeSameInstanceOf();
        } else {
            card = this.c.makeStatEquivalentCopy();
        }
        if(this.exhaust&&!c.exhaust&&!CardModifierManager.hasModifier(card,BATwinsExhaustModifier.ID)){
            CardModifierManager.addModifier(card,new BATwinsExhaustModifier());
        }
        if(this.isEthereal&&!c.isEthereal&&!CardModifierManager.hasModifier(card,BATwinsEtherealModifier.ID)){
            CardModifierManager.addModifier(card,new BATwinsEtherealModifier());
        }
        if(this.selfRetain&&!c.selfRetain&&!CardModifierManager.hasModifier(card,BATwinsRetainModifier.ID)){
            CardModifierManager.addModifier(card,new BATwinsRetainModifier());
        }
        if(card instanceof BATwinsModCustomCard&&c instanceof BATwinsModCustomCard){
            if(((BATwinsModCustomCard) card).modifyEnergyType!= BATwinsEnergyPanel.EnergyType.SHARE&&((BATwinsModCustomCard) c).modifyEnergyType== BATwinsEnergyPanel.EnergyType.SHARE&&!CardModifierManager.hasModifier(card, BATwinsSharedModifier.ID)){
                CardModifierManager.addModifier(card,new BATwinsSharedModifier());
            }
        }


//        if(this.exhaust && !card.exhaust){
//            card.exhaust= true;
//            CardModifierManager.addModifier(card,new BATwinsExhaustModifier());
//        }
////        card.exhaust = this.exhaust;
////        card.exhaustOnUseOnce = this.exhaustOnUseOnce;
//        if(this.isEthereal&&!card.isEthereal){
//            card.isEthereal = true;
//            CardModifierManager.addModifier(card,new BATwinsEtherealModifier());
//        }
//        if(this.selfRetain&&!card.selfRetain){
//            card.selfRetain=true;
//            CardModifierManager.addModifier(card,new BATwinsRetainModifier());
//        }
//        card.initializeDescription();
//        card.selfRetain = selfRetain;
        return card;
    }
}
