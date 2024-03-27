package baModDeveloper.event;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.relic.BATwinsFoldingShield;
import baModDeveloper.relic.BATwinsNekoHowitzer;
import baModDeveloper.relic.BATwinsSwordOfLight;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BATwinsCommunication extends PhasedEvent {
    public static final String ID = ModHelper.makePath("Communication");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String imgUrl = ModHelper.makeImgPath("event", "Communication");
    private final int gold=100;
    public BATwinsCommunication() {
        super(ID, title, imgUrl);

        TextPhase.OptionInfo info=new TextPhase.OptionInfo(String.format(OPTIONS[0],this.gold));
        info.setOptionResult(this::getReward);
        info.enabledCondition(()->{
            return AbstractDungeon.player.gold>=gold;
        });

        registerPhase("start",new TextPhase(DESCRIPTIONS[0]).addOption(info).addOption(OPTIONS[1],integer -> transitionKey("leave")));
        registerPhase("leave",new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[2],integer -> {openMap();}));
        registerPhase("afterReward",new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[2],integer -> {openMap();}));

        transitionKey("start");

        this.noCardsInRewards=true;
    }

    private void getReward(int i){
        AbstractDungeon.player.loseGold(this.gold);

        ArrayList<AbstractRelic> rewards=new ArrayList<>();
        rewards.add(new BATwinsFoldingShield());
        rewards.add(new BATwinsSwordOfLight());
        rewards.add(new BATwinsNekoHowitzer());

        int rng= AbstractDungeon.miscRng.random(0,rewards.size()-1);
        AbstractDungeon.getCurrRoom().rewards.clear();
        AbstractDungeon.getCurrRoom().addRelicToRewards(rewards.get(rng));
        AbstractDungeon.combatRewardScreen.open();
        transitionKey("afterReward");

    }


}
