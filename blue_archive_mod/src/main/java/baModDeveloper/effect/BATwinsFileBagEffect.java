package baModDeveloper.effect;

import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class BATwinsFileBagEffect extends AbstractGameEffect {
    public BATwinsFileBagEffect() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.srcRareCardPool, 1, false,
                    CardCrawlGame.languagePack.getRelicStrings(ModHelper.makePath("FileBag")).DESCRIPTIONS[2]);
            this.duration -= Gdx.graphics.getDeltaTime();
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                card.untip();
                card.unhover();
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                ModHelper.logger.info("FileBag:" + card.name);
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
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
