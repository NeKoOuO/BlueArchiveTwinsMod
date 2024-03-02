package baModDeveloper.action;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Optional;

import static basemod.BaseMod.logger;

public class BATwinsDrawCardByColor extends AbstractGameAction {
    private AbstractCard.CardColor color;
    private int amount;
    private boolean shuffleCheck = false;

    public BATwinsDrawCardByColor(AbstractCard.CardColor color, int amount, boolean shuffleCheck) {
        this.color = color;
        this.amount = amount;
        this.shuffleCheck = shuffleCheck;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public BATwinsDrawCardByColor(AbstractCard.CardColor color, int amount) {
        this(color, amount, false);
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            this.isDone = true;
            return;
        }
        if (this.amount <= 0) {
            this.isDone = true;
            return;
        }

        int deckColorSize = (int) AbstractDungeon.player.drawPile.group.stream().filter(s -> s.color == this.color).count();
        int disColorSize = (int) AbstractDungeon.player.discardPile.group.stream().filter(s -> s.color == this.color).count();
        if (this.amount > deckColorSize + disColorSize) {
            this.amount = deckColorSize + disColorSize;
        }

        if (SoulGroup.isActive()) {
            return;
        }
        if (deckColorSize + disColorSize == 0) {
            this.isDone = true;
            return;
        }
        if (AbstractDungeon.player.hand.size() == 10) {
            AbstractDungeon.player.createHandIsFullDialog();
            this.isDone = true;
            return;
        }
        if (!this.shuffleCheck) {
            if (this.amount + AbstractDungeon.player.hand.size() > 10) {
                int handSizeAndDraw = 10 - this.amount - AbstractDungeon.player.hand.size();
                this.amount += handSizeAndDraw;
                AbstractDungeon.player.createHandIsFullDialog();

            }
            if (this.amount > deckColorSize) {
                int tmp = this.amount - deckColorSize;
                addToTop(new BATwinsDrawCardByColor(this.color, tmp, true));
                addToTop(new EmptyDeckShuffleAction());
                if (deckColorSize != 0) {
                    addToTop(new BATwinsDrawCardByColor(this.color, deckColorSize));
                }
                this.amount = 0;
                this.isDone = true;
                return;
            }
            this.shuffleCheck = true;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.amount != 0 && this.duration < 0.0F) {
            if (Settings.FAST_MODE) {
                this.duration = Settings.ACTION_DUR_XFAST;
            } else {
                this.duration = Settings.ACTION_DUR_FASTER;
            }
            this.amount--;
            if (deckColorSize != 0) {
                Optional<AbstractCard> card = AbstractDungeon.player.drawPile.group.stream().filter(s -> s.color == this.color).findFirst();
                if (card.isPresent()) {
                    AbstractCard c = card.get();
                    AbstractDungeon.player.drawPile.moveToHand(c);
                    AbstractDungeon.player.hand.refreshHandLayout();
                }
            } else {
                logger.warn("BATwinsMod:Draw Card By Color But DrawPile Has Not Cards");
                this.isDone = true;
            }
            if (this.amount == 0) {
                this.isDone = true;
            }
        }
    }

}
