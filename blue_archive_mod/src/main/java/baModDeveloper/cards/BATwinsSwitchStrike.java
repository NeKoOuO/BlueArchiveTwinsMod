package baModDeveloper.cards;

import baModDeveloper.action.BATwinsSwitchStrikeAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsSwitchStrike extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("SwitchStrike");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "SwitchStrike");
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsSwitchStrike() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage = 10;
        this.damage = this.baseDamage;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage), AbstractGameAction.AttackEffect.LIGHTNING));
//        BATwinsSwitchStrike temp= (BATwinsSwitchStrike) this.makeStatEquivalentCopy();
//        temp.conversionColor();
//        addToBot(new BATwinsMakeTempCardInHandAction(temp));
//        addToBot(new Co);
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer, abstractMonster);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    @Override
    public void triggerOnConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD)
            addToBot(new BATwinsSwitchStrikeAction(BATwinsCharacter.Enums.BATWINS_MIDORI_CARD, abstractMonster, this.numberOfConnections + 1));
        else if (this.color == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
            addToBot(new BATwinsSwitchStrikeAction(BATwinsCharacter.Enums.BATWINS_MOMOI_CARD, abstractMonster, this.numberOfConnections + 1));
        }
    }
}
