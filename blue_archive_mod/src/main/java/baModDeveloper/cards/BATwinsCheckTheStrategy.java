package baModDeveloper.cards;

import baModDeveloper.action.BATwinsDisOtherCardByColorAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsCheckTheStrategy extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("CheckTheStrategy");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "defaultSkill");
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;

    public BATwinsCheckTheStrategy() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new WaitAction(1.0F));
        addToBot(new BATwinsDisOtherCardByColorAction(BATwinsCharacter.Enums.BATWINS_MOMOI_CARD, integer -> {
            if (integer > 0)
                addToTop(new DrawCardAction(integer));
        }));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new WaitAction(1.0F));
        addToBot(new BATwinsDisOtherCardByColorAction(BATwinsCharacter.Enums.BATWINS_MIDORI_CARD, integer -> {
            if (integer > 0) {
                addToTop(new DrawCardAction(integer));
            } else if (integer == 0) {
//                if(BATwinsCheckTheStrategy.this.upgraded){
//                    addToTop(new ArmamentsAction(true));
//                }
                addToTop(new SelectCardsAction(AbstractDungeon.player.drawPile.group, 1, "", false, card -> {
                    return true;
                }, cards -> {
                    for (AbstractCard c : cards) {
                        if (AbstractDungeon.player.hand.size() == 10) {
                            AbstractDungeon.player.createHandIsFullDialog();
                            return;
                        }
//                        if(BATwinsCheckTheStrategy.this.upgraded){
//                            c.upgrade();
//                        }
                        AbstractDungeon.player.drawPile.removeCard(c);
                        AbstractDungeon.player.drawPile.moveToHand(c);
                    }
                }));

            }
        }));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void triggerOnHovered() {
        if (AbstractDungeon.player != null) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.color != this.color) {
                    c.flash(BATwinsCharacter.getColorWithCardColor(c.color));
                }
            }
        }

    }
}
