package baModDeveloper.cards;

import baModDeveloper.action.BATwinsDisCardByColorAction;
import baModDeveloper.action.BATwinsDisOtherCardByColorAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsExperiencePower;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Consumer;

public class BATwinsDontSayIt extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("DontSayIt");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "DontSayIt");
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;
    private final Consumer<Integer> callback = integer -> {
        if (integer == 0) {
//            BATwinsDontSayIt.this.addToBot(new BATwinsLevelUpAction(BATwinsDontSayIt.this.magicNumber, true));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BATwinsExperiencePower(AbstractDungeon.player, BATwinsDontSayIt.this.magicNumber)));
        }
    };

    public BATwinsDontSayIt() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseBlock = 6;
        this.block = this.baseBlock;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new BATwinsDisCardByColorAction(BATwinsCharacter.Enums.BATWINS_MIDORI_CARD, this.callback));

    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new BATwinsDisCardByColorAction(BATwinsCharacter.Enums.BATWINS_MOMOI_CARD, this.callback));


    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }
    }

    protected void exchangeName() {
        if (this.color == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
            this.name = CARD_STRINGS.NAME;
        } else {
            this.name = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        }
        if (this.upgraded) {
            this.upgradeName();
        }
    }

    @Override
    public void conversionColor(boolean flash) {
        super.conversionColor(flash);
        this.exchangeName();
    }

    @Override
    public void triggerOnHovered() {
        if (AbstractDungeon.player != null) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.color ==BATwinsCharacter.getOtherColor(this.color)) {
                    c.flash(BATwinsCharacter.getColorWithCardColor(c.color));
                }
            }
        }

    }
}
