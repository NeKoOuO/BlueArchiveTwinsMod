package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.cards.BATwinsUniversalBullet;
import baModDeveloper.cards.bullets.BATwinsCustomBulletCard;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.effect.BATwinsUniversalBulletEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.List;

public class BATwinsUniversalBulletAction extends AbstractGameAction {
    private final AbstractCard.CardColor color;
    private final BATwinsCustomBulletCard bullet;

    public BATwinsUniversalBulletAction(AbstractCard.CardColor color, BATwinsCustomBulletCard bullet) {
        this.color = color;
        this.bullet = bullet;
        if (this.bullet.color != this.color) {
            this.bullet.conversionColor();
        }
    }

    @Override
    public void update() {
        CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractDungeon.player.hand.group.stream().filter(this::filter).forEach(temp::addToBottom);
        if (!temp.isEmpty()) {
            AbstractCard card = temp.getRandomCard(AbstractDungeon.cardRandomRng);
            if (card instanceof BATwinsModCustomCard) {
                ((BATwinsModCustomCard) card).addBringOutCard(this.bullet);
                card.flash(BATwinsCharacter.getColorWithCardColor(card.color));
                AbstractDungeon.topLevelEffectsQueue.add(new BATwinsUniversalBulletEffect((BATwinsUniversalBullet) bullet,card));
            }
        }
        this.isDone = true;
    }

    private void callback(List<AbstractCard> cards) {
        for (AbstractCard c : cards) {
            if (c instanceof BATwinsModCustomCard) {
                ((BATwinsModCustomCard) c).addBringOutCard(this.bullet);
            }
        }
    }

    private boolean filter(AbstractCard card) {
        return card.color == this.color;
    }

}
