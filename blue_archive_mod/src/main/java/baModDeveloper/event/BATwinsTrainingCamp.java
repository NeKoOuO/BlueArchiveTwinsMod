package baModDeveloper.event;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.relic.BATwinsByProving;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;

import java.util.function.Consumer;

public class BATwinsTrainingCamp extends PhasedEvent {
    public static final String ID = ModHelper.makePath("TrainingCamp");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String imgUrl = ModHelper.makeImgPath("event", "TrainingCamp");
    private boolean pickCard = false;

    private boolean hasCard = false;
    private boolean hasAttackCard = false;
    private boolean hasSkillCard = false;
    private boolean hasPowerCard = false;

    public BATwinsTrainingCamp() {
        super(ID, title, imgUrl);
        int loseHp = AbstractDungeon.player.currentHealth / 2;
        if (!AbstractDungeon.player.masterDeck.isEmpty()) {
            this.hasCard = true;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                switch (c.type) {
                    case ATTACK:
                        this.hasAttackCard = true;
                        break;
                    case SKILL:
                        this.hasSkillCard = true;
                        break;
                    case POWER:
                        this.hasPowerCard = true;
                        break;
                }
            }
        }

        Consumer<Integer> battle = integer -> {
            int ran = AbstractDungeon.miscRng.random(10);
            if (ran > 7) {
                transitionKey("AfterTheBattle");
            } else {
                AbstractDungeon.player.damage(new DamageInfo(null, AbstractDungeon.miscRng.random(1, loseHp), DamageInfo.DamageType.HP_LOSS));
                transitionKey("AfterTheBattleWithDamage");
            }
        };
        TextPhase.OptionInfo opinfo1 = new TextPhase.OptionInfo(String.format(this.hasCard ? OPTIONS[0] : OPTIONS[9], Integer.toString(loseHp)));
        opinfo1.enabledCondition(() -> {
            return hasCard;
        });
        opinfo1.setOptionResult(battle);
        registerPhase("Start", new TextPhase(DESCRIPTIONS[0]).addOption(opinfo1).addOption(OPTIONS[1], integer -> transitionKey("Leave")));

        //        CardGroup temp=new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        //        for(AbstractCard card: AbstractDungeon.player.masterDeck.group){
        //            if(card.type== AbstractCard.CardType.ATTACK){
        //
        //            }
        //        }
        Consumer<Integer> getReward = integer -> {
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
            AbstractCard.CardType type;
            switch (integer) {
                case 0:
                    type = AbstractCard.CardType.ATTACK;
                    break;
                case 1:
                    type = AbstractCard.CardType.SKILL;
                    break;
                case 2:
                    type = AbstractCard.CardType.POWER;
                    break;
                default:
                    return;
            }
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                if (card.type == type) {
                    temp.addToTop(card);
                }
            }
            BATwinsTrainingCamp.this.pickCard = true;
            AbstractDungeon.gridSelectScreen.open(temp, 1, "", false, false);


        };
        TextPhase.OptionInfo choose1 = new TextPhase.OptionInfo(this.hasAttackCard ? OPTIONS[2] : OPTIONS[6]);
        choose1.enabledCondition(() -> {
            return hasAttackCard;
        });
        choose1.setOptionResult(getReward);
        TextPhase.OptionInfo choose2 = new TextPhase.OptionInfo(this.hasSkillCard ? OPTIONS[3] : OPTIONS[7]);
        choose2.enabledCondition(() -> {
            return hasSkillCard;
        });
        choose2.setOptionResult(getReward);
        TextPhase.OptionInfo choose3 = new TextPhase.OptionInfo(this.hasPowerCard ? OPTIONS[4] : OPTIONS[8]);
        choose3.enabledCondition(() -> {
            return hasPowerCard;
        });
        choose3.setOptionResult(getReward);
        registerPhase("AfterTheBattle", new TextPhase(DESCRIPTIONS[2]).addOption(choose1).addOption(choose2).addOption(choose3));
        registerPhase("AfterTheBattleWithDamage", new TextPhase(DESCRIPTIONS[1]).addOption(choose1).addOption(choose2));
        registerPhase("Leave", new TextPhase(DESCRIPTIONS[3]).addOption(OPTIONS[5], integer -> openMap()));
        registerPhase("AfterRewards", new TextPhase(DESCRIPTIONS[4]).addOption(OPTIONS[1], integer -> openMap()));

        transitionKey("Start");

    }

    @Override
    public void update() {
        super.update();

        if (this.pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, new BATwinsByProving(c));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            transitionKey("AfterRewards");
            this.pickCard = false;
        }
    }
}
