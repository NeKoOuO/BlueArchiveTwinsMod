package baModDeveloper.cards;

import baModDeveloper.cards.bullets.BATwinsCustomBulletCard;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BATwinsBreechLoading extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("BreechLoading");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "BreechLoading");
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MIDORI;
    private static final UIStrings SELECTTEXT = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));
    private final Predicate<AbstractCard> filter = card -> {
        return card.hasTag(BATwinsCardTags.Shooting);
    };
    private final Consumer<List<AbstractCard>> callback = cards -> {
        for (AbstractCard c : cards) {
            if (c instanceof BATwinsModCustomCard) {
                for (int i = 0; i < BATwinsBreechLoading.this.magicNumber; i++){
                    c.superFlash();
                    ((BATwinsModCustomCard) c).addBringOutCard(BATwinsCustomBulletCard.getRandomBullet());
                }

            }
        }
    };

    public BATwinsBreechLoading() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseBlock = this.block = 5;
        this.baseMagicNumber = this.magicNumber = 3;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractCard> cards=new ArrayList<>();
                for(AbstractCard card:AbstractDungeon.player.hand.group){
                    if(filter.test(card)){
                        cards.add(card);
                    }
                }
                callback.accept(cards);
                this.isDone=true;
            }
        });
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer, abstractMonster);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
//            this.upgradeMagicNumber(1);
            this.selfRetain = true;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.originRawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void triggerOnHovered() {
        AbstractDungeon.player.hand.group.stream().filter(card -> card.hasTag(BATwinsCardTags.Shooting)).forEach(card -> card.flash(BATwinsCharacter.getColorWithCardColor(card.color)));
    }
}
