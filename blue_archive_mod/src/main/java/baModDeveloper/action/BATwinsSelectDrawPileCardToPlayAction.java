package baModDeveloper.action;

import baModDeveloper.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BATwinsSelectDrawPileCardToPlayAction extends AbstractGameAction {
    private boolean isRandom;
    private AbstractPlayer p;
    private int numberOfConnections;
    private AbstractCard.CardColor color;
    protected ArrayList<AbstractCard> canNotSelect;
    private boolean isBlockOrigin = false;
    private boolean removePower = false;
    private UIStrings UISTRINGS = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom) {
        this(isRandom, null);
    }

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractMonster target, int numberOfConnections, AbstractCard.CardColor color) {
        this.isRandom = isRandom;
        this.target = target;
        this.p = AbstractDungeon.player;
        this.numberOfConnections = numberOfConnections;
        this.color = color;
        this.canNotSelect = new ArrayList<>();
        this.amount = 1;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractMonster target, int numberOfConnections) {
        this(isRandom, target, numberOfConnections, null);
        this.amount = 1;
    }

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractMonster target) {
        this(isRandom, target, 1);
    }

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, int numberOfConnections) {
        this(isRandom, (AbstractMonster) null, numberOfConnections);
    }

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractCard.CardColor color, int numberOfConnections) {
        this(isRandom, null, numberOfConnections, color);
    }

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractMonster target, int amount, int numberOfConnections, boolean isBlockOrigin, boolean removePower) {
        this(isRandom, target, numberOfConnections);
        this.amount = amount;
        this.isBlockOrigin = isBlockOrigin;
        this.removePower = removePower;
    }

    @Override
    public void update() {
        if (this.p.drawPile.isEmpty()) {
            if(!this.canNotSelect.isEmpty()){
                this.p.drawPile.group.addAll(canNotSelect);
            }
            this.isDone = true;
            return;
        }
        if (this.isRandom) {
            removeCards();
            if (!this.p.drawPile.group.isEmpty()) {
                WhatTheCardDo(this.p.drawPile.getRandomCard(AbstractDungeon.cardRandomRng));
            }
            if (!this.canNotSelect.isEmpty()) {
                this.p.drawPile.group.addAll(this.canNotSelect);
            }
            this.isDone = true;
            tickDuration();
            return;
        } else if (this.duration == Settings.ACTION_DUR_FAST) {
            removeCards();
            if (this.p.drawPile.isEmpty()) {
                this.p.drawPile.group.addAll(this.canNotSelect);
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(this.p.drawPile, Math.min(this.amount, this.p.drawPile.size()), UISTRINGS.TEXT[0], false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                WhatTheCardDo(c);
            }
            if (!this.canNotSelect.isEmpty()) {
                this.p.drawPile.group.addAll(this.canNotSelect);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.isDone = true;
            return;
        }
    }

    private void removeCards() {
        for(AbstractCard card: AbstractDungeon.player.drawPile.group){
            if(card.color!=this.color||(this.removePower&&card.type== AbstractCard.CardType.POWER)){
                this.canNotSelect.add(card);
            }
        }

        this.p.drawPile.group.removeAll(this.canNotSelect);
    }

    private void WhatTheCardDo(AbstractCard c) {
//        if (this.target == null) {
//            this.target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
//        }
        addToTop(new BATwinsPlayDrawPailCardAction(c, this.target, false, this.numberOfConnections, this.isBlockOrigin));

    }

}
