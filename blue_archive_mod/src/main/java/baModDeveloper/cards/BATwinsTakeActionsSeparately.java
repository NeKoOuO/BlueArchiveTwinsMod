package baModDeveloper.cards;

import baModDeveloper.action.BATwinsSelectHandCardToPlayAction;
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

public class BATwinsTakeActionsSeparately extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("TakeActionsSeparately");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "TakeActionsSeparately");
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;

    public BATwinsTakeActionsSeparately() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseBlock = 7;
        this.block = this.baseBlock;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new BATwinsSelectHandCardToPlayAction(BATwinsCharacter.Enums.BATWINS_MIDORI_CARD, abstractMonster, null, 1, this.numberOfConnections + 1,false,true));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new BATwinsSelectHandCardToPlayAction(BATwinsCharacter.Enums.BATWINS_MOMOI_CARD, abstractMonster, null, 1, this.numberOfConnections + 1,false,true));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
        }
    }

    @Override
    public void triggerOnHovered() {
        if (AbstractDungeon.player != null) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof BATwinsModCustomCard && c.color == BATwinsCharacter.getOtherColor(this.color)) {
                    c.flash(BATwinsCharacter.getColorWithCardColor(c.color));
                }
            }
        }

    }
}
