package baModDeveloper.event;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.function.Consumer;

public class BATwinsDirtShowdown extends PhasedEvent {
    public static final String ID = ModHelper.makePath("DirtShowdown");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String imgUrl = ModHelper.makeImgPath("event", "DirtShowdown");
    private boolean pickCard = false;

    public BATwinsDirtShowdown() {
        super(ID, title, imgUrl);
        Consumer<Integer> getReward1 = integer -> {
            getReward(BATwinsCharacter.Enums.BATWINS_MOMOI_CARD);
        };
        Consumer<Integer> getReward2 = integer -> {
            getReward(BATwinsCharacter.Enums.BATWINS_MIDORI_CARD);
        };

        registerPhase("start", new TextPhase(DESCRIPTIONS[0]).addOption(OPTIONS[1], integer -> transitionKey("gaming")).addOption(OPTIONS[0], integer -> transitionKey("leave")));
        registerPhase("level", new TextPhase(DESCRIPTIONS[5]).addOption(OPTIONS[7], integer -> openMap()));
        registerPhase("gaming", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[2], integer -> transitionKey("gameover1")).addOption(OPTIONS[3], integer -> transitionKey("gameover2")));
        registerPhase("gameover1", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[4] + OPTIONS[5], getReward1));
        registerPhase("gameover2", new TextPhase(DESCRIPTIONS[3]).addOption(OPTIONS[4] + OPTIONS[6], getReward2));
        registerPhase("levelaftergame", new TextPhase(DESCRIPTIONS[4]).addOption(OPTIONS[7], integer -> openMap()));

        transitionKey("start");
    }

    private void getReward(AbstractCard.CardColor color) {
        if (AbstractDungeon.player != null) {
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            AbstractDungeon.player.masterDeck.group.stream().filter(card -> card.color == color && card.canUpgrade()).forEach(temp::addToBottom);
            if (temp.isEmpty()) {
                transitionKey("levelaftergame");
                return;
            }
            if (temp.size() == 1) {
                AbstractCard card = temp.getTopCard();
                card.upgrade();
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                transitionKey("levelaftergame");
                return;
            }

            AbstractCard card1 = temp.getRandomCard(AbstractDungeon.cardRandomRng);
            temp.removeCard(card1);
            AbstractCard card2 = temp.getRandomCard(AbstractDungeon.cardRandomRng);
            if (card1 != null) {
                card1.upgrade();
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card1.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, Settings.HEIGHT / 2.0F));
            }
            if (card2 != null) {
                card2.upgrade();
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card2.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 20.0F * Settings.scale, Settings.HEIGHT / 2.0F));
            }
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            transitionKey("levelaftergame");

        }
    }
}
