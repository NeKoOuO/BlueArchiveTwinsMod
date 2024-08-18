package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;

public class BATwinsCollaboration extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("Collaboration");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "Collaboration");
    private static final int COST = 0;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsCollaboration() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage = 2;
        this.damage = this.baseDamage;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new AbstractGameAction() {
            final int damage=BATwinsCollaboration.this.damage;
            @Override
            public void update() {
                for(int i=AbstractDungeon.player.hand.size()-1;i>=0;i--){
                    AbstractCard c=AbstractDungeon.player.hand.group.get(i);
                    if (c.color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD) {
                        addToTop(new VFXAction(new CardFlashVfx(c,true)));
                        addToTop(new DamageRandomEnemyAction(new DamageInfo(abstractPlayer, this.damage), AbstractGameAction.AttackEffect.LIGHTNING));
                    }
                }
                this.isDone=true;
            }
        });
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new AbstractGameAction() {
            final int damage=BATwinsCollaboration.this.damage;
            @Override
            public void update() {
                for(int i=AbstractDungeon.player.hand.size()-1;i>=0;i--){
                    AbstractCard c=AbstractDungeon.player.hand.group.get(i);
                    if (c.color == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
                        addToTop(new VFXAction(new CardFlashVfx(c,true)));
                        addToTop(new DamageRandomEnemyAction(new DamageInfo(abstractPlayer, this.damage), AbstractGameAction.AttackEffect.LIGHTNING));
                    }
                }
                this.isDone=true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
        }
    }


}
