package baModDeveloper.effect;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;

public class BATwinsParoxysmalPainEffect extends AbstractGameEffect {
    private static float IntervalTime = 0.1F;
    Texture img;
    float x, y;
    AbstractCard.CardColor color;
    private int count;
    private boolean playSound = false;

    public BATwinsParoxysmalPainEffect(AbstractCard.CardColor color) {
        this.duration = IntervalTime;
        this.count = 7;
        this.color = color;
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration <= 0.0F) {
            if (!playSound) {
                CardCrawlGame.sound.play(ModHelper.makePath("Momoi_Ex"));
                playSound = true;
            }
            this.duration = IntervalTime;
            this.count--;
            AbstractDungeon.effectsQueue.add(new BATwinsSlashEffect(BATwinsCharacter.getColorWithCardColor(this.color)));
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped()) {
                    AbstractDungeon.effectsQueue.add(new StarBounceEffect(m.hb.cX, m.hb.cY));
                }
            }
            if (this.count <= 0) {
                this.isDone = true;
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
