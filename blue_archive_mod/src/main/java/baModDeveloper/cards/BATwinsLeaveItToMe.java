package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsLeaveItToMe extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("LeaveItToMe");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "LeaveItToMe");
    private static final int COST = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsLeaveItToMe() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage = 16;
        this.damage = this.baseDamage;
//        this.isMultiDamage=true;
        this.baseBlock = 5;
        this.block = this.baseBlock;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
//        addToBot(new DamageAllEnemiesAction(abstractPlayer,this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL,true));
        triggerSpecialEffects();
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer, abstractMonster);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeDamage(6);
            this.upgradeBlock(2);
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hand.group.stream().allMatch(card -> card.color == this.color)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    private void triggerSpecialEffects() {
        addToBot(new AbstractGameAction() {
            private final CardColor color;
            private final int numOfConnections;

            {
                color = BATwinsLeaveItToMe.this.color;
                amount = BATwinsLeaveItToMe.this.block;
                numOfConnections = BATwinsLeaveItToMe.this.numberOfConnections;
            }

            @Override
            public void update() {
                if (numOfConnections > 0 || AbstractDungeon.player.hand.group.stream().allMatch(card -> card.color == color)) {
                    int count = 0;
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!m.isDeadOrEscaped()) {
                            count++;
                        }
                    }
                    addToBot(new GainBlockAction(AbstractDungeon.player, this.amount * count));
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void triggerOnConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        triggerSpecialEffects();
    }
}
