package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.ShaderHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.Objects;
import java.util.Optional;

public class BATwinsSelfConnectivity extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("SelfConnectivity");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "SelfConnectivity");
    private static final String IMG_PATH2 = ModHelper.makeImgPath("cards", "SelfConnectivity2");
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;
    private AbstractCard cardToCopy;
    private static AbstractCard EasterEggCard=new BATwinsMomoiStrick();
    private static ShaderProgram shaderProgram;

    public BATwinsSelfConnectivity() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        shaderProgram=ShaderHelper.getShaderProgram("selfConnectivity");
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        updateCardPreview();
        if (cardToCopy == null) {
            return;
        }
        if (cardToCopy instanceof BATwinsModCustomCard) {
            ((BATwinsModCustomCard) cardToCopy).numberOfConnections = this.numberOfConnections;
            ((BATwinsModCustomCard) cardToCopy).useMOMOI(abstractPlayer, abstractMonster);
            ((BATwinsModCustomCard) cardToCopy).numberOfConnections = 0;
        } else {
            cardToCopy.use(abstractPlayer, abstractMonster);
        }
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        updateCardPreview();
        if (this.cardToCopy == null) {
            return;
        }
        if (cardToCopy instanceof BATwinsModCustomCard) {
            ((BATwinsModCustomCard) cardToCopy).numberOfConnections = this.numberOfConnections;
            ((BATwinsModCustomCard) cardToCopy).useMIDORI(abstractPlayer, abstractMonster);
            ((BATwinsModCustomCard) cardToCopy).numberOfConnections = 0;
        } else {
            cardToCopy.use(abstractPlayer, abstractMonster);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        updateCardPreview();
    }


    @Override
    public void render(SpriteBatch sb) {
//        if (this.cardsToPreview == null) {
//            updateCardPreview();
//        }
        super.render(sb);

    }

    @Override
    public void triggerOnConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.cardToCopy == null) {
            return;
        }
        if (cardToCopy instanceof BATwinsModCustomCard) {
            ((BATwinsModCustomCard) cardToCopy).numberOfConnections = this.numberOfConnections;
            ((BATwinsModCustomCard) cardToCopy).triggerOnConnectPlayed(abstractPlayer, abstractMonster);
            ((BATwinsModCustomCard) cardToCopy).numberOfConnections = 0;
        }
    }

    @Override
    public void triggerOnSuperConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.cardToCopy == null) {
            return;
        }
        if (cardToCopy instanceof BATwinsModCustomCard) {
            ((BATwinsModCustomCard) cardToCopy).numberOfConnections = this.numberOfConnections;
            ((BATwinsModCustomCard) cardToCopy).triggerOnSuperConnectPlayed(abstractPlayer, abstractMonster);
            ((BATwinsModCustomCard) cardToCopy).numberOfConnections = 0;
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.originRawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;

            initializeDescription();
        }
    }

    private void updateCardPreview() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()) {
            Optional<AbstractCard> preCard;
            if (this.upgraded) {
                preCard = AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(card -> !Objects.equals(card.cardID, this.cardID)).reduce((first, second) -> second);
            } else {
                preCard = AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(card -> !Objects.equals(card.cardID, this.cardID) && card.color == this.color).reduce((first, second) -> second);
            }
            if (preCard.isPresent()) {
                this.cardsToPreview = preCard.get().makeStatEquivalentCopy();
                this.cardToCopy = preCard.get();
                this.cardToCopy.applyPowers();
            }
        }
//        if (this.cardsToPreview == null) {
//            this.cardsToPreview = this.makeCopy();
//            this.cardToCopy = null;
//        }
    }

    @Override
    public void conversionColor(boolean flash) {
        super.conversionColor(flash);
        this.updateCardPreview();
    }

    @SpireOverride
    protected void renderPortrait(SpriteBatch sb) {
        if (this.cardsToPreview != null && !Objects.equals(this.cardsToPreview.cardID, this.cardID)) {
            float tempScale = this.drawScale * 0.4F;
            sb.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            this.cardsToPreview.angle = this.angle;
            this.cardsToPreview.drawScale = tempScale;
            this.cardsToPreview.current_x = this.current_x;
            this.cardsToPreview.current_y = this.current_y + AbstractCard.IMG_HEIGHT * 0.18F * this.drawScale;
            this.cardsToPreview.render(sb);
        }else if(this.cardsToPreview==null&&AbstractDungeon.getCurrRoom().phase== AbstractRoom.RoomPhase.COMBAT){
            shaderProgram.setUniformf("iResolution", Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            ShaderHelper.renderShader(shaderProgram,sb,this::renderEasterEggCard);
        }


    }

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        this.updateCardPreview();
    }

    @Override
    public void renderCardPreview(SpriteBatch sb) {
        if (!Objects.equals(this.cardsToPreview.cardID, this.cardID)) {
            super.renderCardPreview(sb);
        }
    }

    private void renderEasterEggCard(SpriteBatch sb){
        sb.setColor(Color.WHITE);
        EasterEggCard.current_x= Settings.WIDTH/2.0F;
        EasterEggCard.current_y=Settings.HEIGHT/2.0F;
        EasterEggCard.drawScale=1.0F;
        EasterEggCard.render(sb);
    }
}
