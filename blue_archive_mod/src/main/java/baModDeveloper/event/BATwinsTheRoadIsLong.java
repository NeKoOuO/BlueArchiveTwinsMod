package baModDeveloper.event;

import baModDeveloper.cards.colorless.BATwinsExcitation;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;

public class BATwinsTheRoadIsLong extends PhasedEvent {
    public static final String ID = ModHelper.makePath("TheRoadIsLong");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String imgUrl = ModHelper.makeImgPath("event", "TheRoadIsLong");


    private int loseHp;
    private int loseMaxHp;
    private int recoveryHp;

    public BATwinsTheRoadIsLong() {
        super(ID, title, imgUrl);
        this.loseHp = 30;
        this.loseMaxHp = 12;
        this.recoveryHp = AbstractDungeon.player.maxHealth;
        registerPhase("EnterRoom", new TextPhase(DESCRIPTIONS[0] + DESCRIPTIONS[1]).addOption(OPTIONS[5], integer -> transitionKey("Start")));
        registerPhase("Start", new TextPhase(DESCRIPTIONS[2] + DESCRIPTIONS[3]).addOption(OPTIONS[0], this::startOptionResult).addOption(String.format(OPTIONS[1], this.loseHp), this::startOptionResult).addOption(String.format(OPTIONS[2], this.recoveryHp, this.loseMaxHp), this::startOptionResult));
        registerPhase("Forward", new TextPhase(DESCRIPTIONS[7] + DESCRIPTIONS[12] + DESCRIPTIONS[13]).addOption(OPTIONS[5], integer -> transitionKey("ForwardEnd")));
        registerPhase("ForwardEnd", new TextPhase(DESCRIPTIONS[8]).addOption(OPTIONS[6], this::endResult));
        registerPhase("Pathfinding", new TextPhase(DESCRIPTIONS[4] + DESCRIPTIONS[5] + DESCRIPTIONS[6]).addOption(OPTIONS[3], this::pathFindingOptionResult).addOption(OPTIONS[4], this::pathFindingOptionResult));
        registerPhase("PathFindingEnd", new TextPhase(DESCRIPTIONS[9]).addOption(OPTIONS[6], this::endResult));
        registerPhase("Rest", new TextPhase(DESCRIPTIONS[11]).addOption(OPTIONS[6], this::endResult));

        transitionKey("EnterRoom");
    }

    private void startOptionResult(int i) {
        switch (i) {
            case 0:
                transitionKey("Forward");
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCard(BATwinsExcitation.ID).makeCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCard(Pain.ID).makeCopy(), Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH, Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCard(Pain.ID).makeCopy(), Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH, Settings.HEIGHT / 2.0F));

                break;
            case 1:
                AbstractDungeon.player.damage(new DamageInfo(null, this.loseHp, DamageInfo.DamageType.HP_LOSS));
                transitionKey("Pathfinding");
                break;
            case 2:
                AbstractDungeon.player.heal(this.recoveryHp);
                AbstractDungeon.player.decreaseMaxHealth(this.loseMaxHp);
                transitionKey("Rest");
                break;
        }
    }

    private void pathFindingOptionResult(int i) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        int effectCount = 0;
        switch (i) {
            case 0:
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c.color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD) {
                        cards.add(c);
                    }
                }
                break;
            case 1:
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c.color == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
                        cards.add(c);
                    }
                }
                break;
        }
        for (AbstractCard c : cards) {
            if (c.canUpgrade()) {
                effectCount++;
                if (effectCount <= 20) {
                    float x = MathUtils.random(0.1F, 0.9F) * Settings.WIDTH;
                    float y = MathUtils.random(0.2F, 0.8F) * Settings.HEIGHT;
                    AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
                    c.upgrade();
                    AbstractDungeon.player.bottledCardUpgradeCheck(c);
                }
            }
        }
        transitionKey("PathFindingEnd");
    }

    private void endResult(int i) {
        openMap();
    }
}
