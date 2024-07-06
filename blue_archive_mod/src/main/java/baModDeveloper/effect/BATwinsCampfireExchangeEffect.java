package baModDeveloper.effect;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.patch.BATwinsGridCardSelectScreenPatch;
import baModDeveloper.patch.BATwinsMetricDataPatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BATwinsCampfireExchangeEffect extends AbstractGameEffect {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("ExchangeEffect"));
    private static final String[] TEXT = uiStrings.TEXT;

    private static final float DUR = 1.5F;
    private boolean openedScreen = false;
    private Color screenColor = AbstractDungeon.fadeColor.cpy();
    private boolean momoiSound = false, midoriSound = false;

    public BATwinsCampfireExchangeEffect() {
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    @Override
    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            updateBlackScreenColor();
        }
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
//                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH/2.0F,Settings.HEIGHT/2.0F));
                BATwinsMetricDataPatch.FiledPatch.campfire_exchange.set(CardCrawlGame.metricData, BATwinsMetricDataPatch.FiledPatch.campfire_exchange.get(CardCrawlGame.metricData) + 1);
                CardCrawlGame.metricData.addCampfireChoiceData(ModHelper.makePath("EXCHANGE"), c.getMetricID());
                if (c instanceof BATwinsModCustomCard) {
                    ((BATwinsModCustomCard) c).conversionColor(false);
                }
                if (c.color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD && !momoiSound) {
                    CardCrawlGame.sound.play(ModHelper.makePath("campfire_momoi"));
                    momoiSound = true;
                } else if (c.color == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD && !midoriSound) {
                    CardCrawlGame.sound.play(ModHelper.makePath("campfire_midori"));
                    midoriSound = true;
                }
                AbstractDungeon.effectsQueue.add(new BATwinsShowCardAndFlashEffect(c.makeStatEquivalentCopy()));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
//            BATwinsGridCardSelectScreenPatch.FiledPatch.forExchange.set(AbstractDungeon.gridSelectScreen,false);
            ((RestRoom) AbstractDungeon.getCurrRoom()).fadeIn();
        }
        if (!AbstractDungeon.isScreenUp && BATwinsGridCardSelectScreenPatch.FiledPatch.forExchange.get(AbstractDungeon.gridSelectScreen)) {
            BATwinsGridCardSelectScreenPatch.FiledPatch.forExchange.set(AbstractDungeon.gridSelectScreen, false);
        }
        if (this.duration < 1.0F && !this.openedScreen) {
            this.openedScreen = true;
            CardGroup group = getExchangeCards();
            BATwinsGridCardSelectScreenPatch.FiledPatch.forExchange.set(AbstractDungeon.gridSelectScreen, true);
            AbstractDungeon.gridSelectScreen.open(group, 1, TEXT[0], false, false, true, false);
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                //添加接口
            }
        }
        if (this.duration < 0.0F) {
            this.isDone = true;
            if (CampfireUI.hidden) {
                AbstractRoom.waitTimer = 0.0F;
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
            }
        }
    }

    private CardGroup getExchangeCards() {
        CardGroup group = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        AbstractDungeon.player.masterDeck.group.stream().filter(card -> card instanceof BATwinsModCustomCard).forEach(group::addToBottom);
        return group;
    }

    private void updateBlackScreenColor() {
        if (this.duration > 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / 1.5F);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setColor(this.screenColor);
//        spriteBatch.draw();
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            AbstractDungeon.gridSelectScreen.render(spriteBatch);
        }
    }

    @Override
    public void dispose() {

    }
}
