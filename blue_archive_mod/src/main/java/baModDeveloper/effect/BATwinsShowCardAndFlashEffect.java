package baModDeveloper.effect;

import baModDeveloper.character.BATwinsCharacter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class BATwinsShowCardAndFlashEffect extends ShowCardBrieflyEffect {
    private boolean flashed = false;
    private AbstractCard card;

    public BATwinsShowCardAndFlashEffect(AbstractCard card) {
        super(card);
        this.card = card;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!flashed) {
            card.superFlash(BATwinsCharacter.getColorWithCardColor(card.color));

            this.flashed = true;
        }
        super.render(sb);
    }
}
