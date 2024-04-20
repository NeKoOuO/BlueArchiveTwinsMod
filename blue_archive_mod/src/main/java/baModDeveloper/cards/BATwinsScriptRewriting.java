package baModDeveloper.cards;

import baModDeveloper.action.BATwinsScriptRewritingAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsBurnPower;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class BATwinsScriptRewriting extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("ScriptRewriting");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "ScriptRewriting");
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;

    public BATwinsScriptRewriting() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.exhaust = true;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMIDORI(abstractPlayer, abstractMonster);
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
//        addToBot(new BATwinsChangeBurnPoiAction(abstractMonster, true));
        addToBot(new BATwinsScriptRewritingAction(this.upgraded, this.color, abstractMonster));
        if (upgraded) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD) {
                        if (abstractMonster.hasPower(PoisonPower.POWER_ID)) {
                            addToTop(new ApplyPowerAction(abstractMonster, AbstractDungeon.player, new BATwinsBurnPower(abstractMonster, AbstractDungeon.player, abstractMonster.getPower(PoisonPower.POWER_ID).amount)));
                        }
                    } else {
                        if (abstractMonster.hasPower(BATwinsBurnPower.POWER_ID)) {
                            addToTop(new ApplyPowerAction(abstractMonster, AbstractDungeon.player, new PoisonPower(abstractMonster, AbstractDungeon.player, abstractMonster.getPower(BATwinsBurnPower.POWER_ID).amount)));
                        }
                    }
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.selfRetain = true;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.originRawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
