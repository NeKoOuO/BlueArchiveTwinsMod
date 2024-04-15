package baModDeveloper.cards;

import baModDeveloper.action.BATwinsPlayHandCardAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.patch.BATwinsAbstractCardPatch;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BATwinsTakeABreak extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("TakeABreak");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "TakeABreak");
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;
    private static final UIStrings UISTRING = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));

    public BATwinsTakeABreak() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMIDORI(abstractPlayer, abstractMonster);
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (!this.isInAutoplay) {
            addToBot(new DrawCardAction(this.magicNumber, new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractCard c : DrawCardAction.drawnCards) {
                        if (!c.isEthereal)
                            c.retain = true;
                    }
                    this.isDone = true;
                }
            }));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void triggerOnConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int drawCards = this.magicNumber;
        if (BATwinsAbstractCardPatch.FieldPatch.blockTheOriginalEffect.get(this)) {
            drawCards = 1;
        } else {
            drawCards += 1;
        }
        addToBot(new DrawCardAction(drawCards, new AbstractGameAction() {
            ArrayList<AbstractCard> canNotSelect = new ArrayList<>();

            {
                this.duration = Settings.ACTION_DUR_FAST;
            }

            @Override
            public void update() {
                if (DrawCardAction.drawnCards.isEmpty()) {
                    this.isDone = true;
                    return;
                }
                if (this.duration == Settings.ACTION_DUR_FAST) {
                    for (AbstractCard c : DrawCardAction.drawnCards) {
                        if (!c.isEthereal) {
                            c.retain = true;
                        }
                    }

                    for (AbstractCard c : AbstractDungeon.player.hand.group) {
                        if (!DrawCardAction.drawnCards.contains(c)) {
                            canNotSelect.add(c);
                        }
                    }
                    AbstractDungeon.player.hand.group.removeAll(canNotSelect);
                    AbstractDungeon.handCardSelectScreen.open(String.format(UISTRING.TEXT[0], 1), 1, false, false, false, false);
                    tickDuration();
                    return;
                }
                if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                    for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
//                        AbstractDungeon.player.hand.addToTop(c);
                        addToTop(new BATwinsPlayHandCardAction(c, null, BATwinsTakeABreak.this.numberOfConnections + 1));
                    }
                    AbstractDungeon.player.hand.group.addAll(canNotSelect);
                    AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                    AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                    this.isDone = true;
                    return;
                }

            }
        }));
    }
}
