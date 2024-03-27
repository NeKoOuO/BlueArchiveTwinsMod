package baModDeveloper.cards;

import baModDeveloper.action.BATwinsDisAllColorCards;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsKeepItToTheEnd extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("KeepItToTheEnd");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "KeepItToTheEnd");
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsKeepItToTheEnd() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseBlock = 7;
        this.block = this.baseBlock;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
        }
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int count = 0;
        for (AbstractCard c : abstractPlayer.hand.group) {
            if (c instanceof BATwinsModCustomCard) {
                if (((BATwinsModCustomCard) c).getCardColor() == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD && c != this) {
                    count++;
                }
            }
        }
        addToBot(new BATwinsDisAllColorCards(BATwinsCharacter.Enums.BATWINS_MOMOI_CARD));
        for (int i = 0; i < count; i++) {
            addToBot(new GainBlockAction(abstractPlayer, this.block));
        }
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int count = 0;
        for (AbstractCard c : abstractPlayer.hand.group) {
            if (c instanceof BATwinsModCustomCard) {
                if (((BATwinsModCustomCard) c).getCardColor() == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD && c != this) {
                    count++;
                }
            }
        }
        addToBot(new BATwinsDisAllColorCards(BATwinsCharacter.Enums.BATWINS_MIDORI_CARD));
        for (int i = 0; i < count; i++) {
            addToBot(new GainBlockAction(abstractPlayer, this.block));
        }
    }

    @Override
    public void triggerOnHovered() {
        if (AbstractDungeon.player != null) {
            AbstractDungeon.player.hand.group.stream().filter(card -> card.color == this.color && card != this).forEach(card -> card.flash(BATwinsCharacter.getColorWithCardColor(card.color)));
        }
    }
}
