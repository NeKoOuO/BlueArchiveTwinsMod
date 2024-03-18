package baModDeveloper.effect;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.Character3DHelper;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BATwinsCharAttackEffect extends AbstractGameEffect {
    private AbstractCard.CardColor color;

    public BATwinsCharAttackEffect(AbstractCard.CardColor color) {
        this.color = color;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (AbstractDungeon.player instanceof BATwinsCharacter) {
            if (color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD) {
                ((BATwinsCharacter) AbstractDungeon.player).setMomoiAnimation(Character3DHelper.MomoiActionList.ATTACK);
            } else if (color == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
                ((BATwinsCharacter) AbstractDungeon.player).setMidoriAnimation(Character3DHelper.MidoriActionList.ATTACK);
            }
        }
        this.isDone = true;

    }

    @Override
    public void dispose() {

    }
}
