package baModDeveloper.cards;

import baModDeveloper.action.BATwinsPlayDisPileCardAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Optional;

public class BATwinsSelfConnectivity extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("SelfConnectivity");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","defaultSkill");
    private static final int COST=1;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.SKILL;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET=CardTarget.SELF_AND_ENEMY;
    private static final CardRarity RARITY=CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MOMOI;
    private AbstractCard cardToCopy;

    public BATwinsSelfConnectivity() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        updateCardPreview();
        if(cardToCopy==null){
            return;
        }
        if(cardToCopy instanceof BATwinsModCustomCard){
            ((BATwinsModCustomCard) cardToCopy).useMOMOI(abstractPlayer,abstractMonster);
        }else{
            cardToCopy.use(abstractPlayer,abstractMonster);
        }
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        updateCardPreview();
        if(this.cardToCopy==null){
            return;
        }
        if(cardToCopy instanceof BATwinsModCustomCard){
            abstractMonster=AbstractDungeon.getRandomMonster();
            ((BATwinsModCustomCard) cardToCopy).useMIDORI(abstractPlayer,abstractMonster);
        }else{
            cardToCopy.use(abstractPlayer,abstractMonster);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        updateCardPreview();
    }

    @Override
    public void renderCardPreview(SpriteBatch sb) {
        updateCardPreview();
        if(this.cardsToPreview.cardID!=this.cardID){
            super.renderCardPreview(sb);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(this.cardsToPreview==null){
            updateCardPreview();
        }
        super.render(sb);

    }

    @Override
    public void triggerOnConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(this.upgraded){
            if(this.cardToCopy==null){
                return;
            }
            updateCardPreview();
            if(this.cardToCopy instanceof BATwinsModCustomCard){
                ((BATwinsModCustomCard) this.cardToCopy).triggerOnConnectPlayed(abstractPlayer,abstractMonster);
            }
        }
        addToBot(new BATwinsPlayDisPileCardAction(this.cardToCopy,abstractMonster,false,this.numberOfConnections+1));
    }

    @Override
    public void triggerOnSuperConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(this.upgraded){
            if(this.cardToCopy==null){
                return;
            }
            updateCardPreview();
            if(this.cardToCopy instanceof BATwinsModCustomCard){
                ((BATwinsModCustomCard) this.cardToCopy).triggerOnSuperConnectPlayed(abstractPlayer,abstractMonster);
            }
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.rawDescription=CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.originRawDescription=CARD_STRINGS.UPGRADE_DESCRIPTION;

            initializeDescription();
        }
    }

    private void updateCardPreview(){
        if(!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()){
            Optional<AbstractCard> preCard= AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(card -> card.cardID!=this.cardID).reduce((first, second)->second);
            if(preCard.isPresent()){
                this.cardsToPreview=preCard.get().makeCopy();
                this.cardToCopy=preCard.get();
            }
        }
        if(this.cardsToPreview==null){
            this.cardsToPreview=this.makeCopy();
            this.cardToCopy=null;
        }
    }
}
