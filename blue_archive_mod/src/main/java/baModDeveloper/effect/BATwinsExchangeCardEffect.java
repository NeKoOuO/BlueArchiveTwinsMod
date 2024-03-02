package baModDeveloper.effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BATwinsExchangeCardEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private AbstractCard.CardColor color;

    public BATwinsExchangeCardEffect(float x, float y, AbstractCard.CardColor color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.duration = 0.0F;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
