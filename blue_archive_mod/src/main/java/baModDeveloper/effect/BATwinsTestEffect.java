package baModDeveloper.effect;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.Character3DHelper;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BATwinsTestEffect extends AbstractGameEffect {
    public BATwinsTestEffect(){

    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(AbstractDungeon.player instanceof BATwinsCharacter){
            ((BATwinsCharacter) AbstractDungeon.player).setCharAnimation(Character3DHelper.MomoiActionList.ATTACK);
        }
        this.isDone=true;
    }

    @Override
    public void dispose() {

    }
}
