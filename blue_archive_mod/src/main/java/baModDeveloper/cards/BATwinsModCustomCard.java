package baModDeveloper.cards;

import baModDeveloper.BATwinsMod;
import baModDeveloper.action.BATwinsPlayTempCardAction;
import baModDeveloper.cards.bullets.BATwinsCustomBulletCard;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.effect.BATwinsCharAttackEffect;
import baModDeveloper.helpers.ImageHelper;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.patch.BATwinsAbstractCardPatch;
import baModDeveloper.power.*;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public abstract class BATwinsModCustomCard extends CustomCard {
    public static UIStrings flatFallMsg = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("FlatFall"));
    private static UIStrings ExchangeKeywords = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("ExchangeKeyowrds"));
    public BATwinsEnergyPanel.EnergyType modifyEnergyType;
    public CardColor OriginalColor;
    public boolean playBackOriginalColor = false;
    public ArrayList<Color> GradientColor = new ArrayList<>();
    public int numberOfConnections = 0;
    public boolean justHovered = false;
    protected boolean bringOutCard = false;
    protected ArrayList<AbstractCard> cardToBringOut = new ArrayList<>();
    protected String originRawDescription;
    private int startColor = 0;
    private float gradientDuration = 0.0F;

    //    public boolean playedByOtherCard=false;
    public BATwinsModCustomCard(String ID, String NAME, String IMG_PATH, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET, BATwinsEnergyPanel.EnergyType ENERGYTYPE) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originRawDescription = DESCRIPTION;
        this.OriginalColor = COLOR;
        this.modifyEnergyType = ENERGYTYPE;
        this.GradientColor.add(BATwinsMod.MOMOIColor.cpy());
        this.GradientColor.add(BATwinsMod.MIDORIColor.cpy());

    }

    public CardColor getCardColor() {
        return this.color;
    }

    public void conversionColor(boolean flash) {
        if (this.color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD) {
            this.color = BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
            if (this.modifyEnergyType != BATwinsEnergyPanel.EnergyType.SHARE)
                this.modifyEnergyType = BATwinsEnergyPanel.EnergyType.MIDORI;
        } else {
            this.color = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
            if (this.modifyEnergyType != BATwinsEnergyPanel.EnergyType.SHARE)
                this.modifyEnergyType = BATwinsEnergyPanel.EnergyType.MOMOI;
        }
        if (flash)
            this.superFlash(BATwinsCharacter.getColorWithCardColor(this.color));


        this.initializeDescription();
    }

    public void conversionColor() {
        this.conversionColor(true);
    }

    @Override
    public AbstractCard makeCopy() {
        BATwinsModCustomCard temp = (BATwinsModCustomCard) super.makeCopy();
        temp.OriginalColor = this.OriginalColor;
        if (this.exchanged()) {
            temp.conversionColor(false);
        }
        if (this.bringOutCard) {
            temp.bringOutCard = true;
            temp.GradientColor = this.GradientColor;
            temp.glowColor = this.glowColor.cpy();
            this.cardToBringOut.forEach(card -> temp.addBringOutCard(card.makeStatEquivalentCopy()));
//            temp.cardToBringOut.add this.cardToBringOut;
//            temp.cardsToPreview = this.cardsToPreview.makeStatEquivalentCopy();
        }
//        temp.initializeDescription();
        return temp;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        BATwinsModCustomCard temp = (BATwinsModCustomCard) super.makeStatEquivalentCopy();

//        temp.color=this.color;
//        temp.modifyEnergyType=this.modifyEnergyType;
//        temp.GradientColor.addAll(this.GradientColor);
//        if(this.bringOutCard){
//            temp.addBringOutCard(this.cardToBringOut);
////        }
//        if(temp.exchanged()){
//            initializeDescription();
//        }
        return temp;
    }

    @Override
    public void initializeDescription() {
        if (this.originRawDescription != null) {
            this.rawDescription = this.originRawDescription;
        }
        if (this.OriginalColor != null && this.OriginalColor != this.color) {
            this.rawDescription = replaceDescription(this.rawDescription);
        }
        super.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (!BATwinsAbstractCardPatch.FieldPatch.blockTheOriginalEffect.get(this)) {
            if (this.color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD) {
                useMOMOI(abstractPlayer, abstractMonster);
            } else if (this.color == BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
                useMIDORI(abstractPlayer, abstractMonster);
            }
            if (this.type == CardType.ATTACK)
                AbstractDungeon.effectsQueue.add(new BATwinsCharAttackEffect(this.color));

        }

        if (this.isInAutoplay) {
            if (this.numberOfConnections < 1) {
                this.numberOfConnections = 1;
            }
            triggerOnConnectPlayed(abstractPlayer, abstractMonster);
            if (this.numberOfConnections > 1) {
                triggerOnSuperConnectPlayed(abstractPlayer, abstractMonster);
            }
//            this.playedByOtherCard=false;
        }
        if (this.bringOutCard) {
            this.bringOutCard(abstractMonster);
        }
        this.numberOfConnections = 0;
        BATwinsAbstractCardPatch.FieldPatch.blockTheOriginalEffect.set(this, false);
        if (AbstractDungeon.player instanceof BATwinsCharacter && !this.freeToPlay() && !this.freeToPlayOnce) {
            if (this.costForTurn > 0) {
                if (AbstractDungeon.player.hasPower(BATwinsDoubleExperiencePower.POWER_ID)) {
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BATwinsExperiencePower(abstractPlayer, this.costForTurn * 2)));
                } else {
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BATwinsExperiencePower(abstractPlayer, this.costForTurn)));
                }
            } else if (this.costForTurn == -1) {
                if (AbstractDungeon.player.hasPower(BATwinsDoubleExperiencePower.POWER_ID)) {
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BATwinsExperiencePower(abstractPlayer, this.energyOnUse * 2)));
                } else {
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BATwinsExperiencePower(abstractPlayer, this.energyOnUse)));
                }
            }
        }
        if (this.playBackOriginalColor && this.exchanged()) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    BATwinsModCustomCard.this.conversionColor();
                    this.isDone = true;
                    tickDuration();
                }
            });
        }
    }


    abstract public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster);

    abstract public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster);

    public String replaceDescription(String description) {
//        description = exchangeStr(description, "中毒", "batwinsmod:灼伤");
//        description = exchangeStr(description, "[batwinsmod:midoriorbicon]", "[batwinsmod:momoiorbicon]");
//        description = exchangeStr(description, "batwinsmod:桃牌", "batwinsmod:绿牌");
//        description = exchangeStr(description, "虚弱", "易伤");

        for (int i = 0; i < ExchangeKeywords.TEXT.length; i += 2) {
            description = exchangeStr(description, ExchangeKeywords.TEXT[i], ExchangeKeywords.TEXT[i + 1]);
        }
        return description;
    }

    private String exchangeStr(String description, String str1, String str2) {
        String temp = "#######";
        description = description.replace(str1, temp);
        description = description.replace(str2, str1);
        description = description.replace(temp, str2);
        return description;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (this.modifyEnergyType == BATwinsEnergyPanel.EnergyType.SHARE) {
            if (gradientDuration > Settings.MAX_FPS) {
                startColor = (startColor + 1) % GradientColor.size();
                this.gradientDuration = 0.0F;
            }
            this.glowColor = this.GradientColor.get(startColor);
            this.gradientDuration += Settings.ACTION_DUR_MED;
        }

        super.render(sb);
    }

    public void triggerOnHovered() {
        return;
    }

    public void triggerOnEnergyUse(int amount, BATwinsEnergyPanel.EnergyType energyType) {
        return;
    }

    public void triggerOnEnergyExhausted(BATwinsEnergyPanel.EnergyType energyType) {
        return;
    }

    public void triggerOnConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        return;
    }

    public void triggerOnSuperConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        return;
    }

    public boolean exchanged() {
        return this.color != this.OriginalColor;
    }

    public void addBringOutCard(AbstractCard card) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(BATwinsExpansionMagazinePower.POWER_ID) && card instanceof BATwinsCustomBulletCard) {
            AbstractDungeon.player.getPower(BATwinsExpansionMagazinePower.POWER_ID).flash();
            card.upgrade();
        }
        this.bringOutCard = true;

        if (card instanceof BATwinsCustomBulletCard) {
            Optional<AbstractCard> c = this.hasCardInBringOutCards(card);
            if (c.isPresent()) {
                c.get().upgrade();
                if (card.upgraded) {
                    //修复了存储升级后子弹却没升级的bug
                    for (int i = 0; i < card.timesUpgraded; i++) {
                        c.get().upgrade();
                    }
                }
                return;
            }
        }
        this.cardToBringOut.add(card.makeSameInstanceOf());
        if (this.cardsToPreview == null) {
            this.cardsToPreview = this.cardToBringOut.get(0);
        }
//        this.cardsToPreview=card.makeCopy();
        if (this.modifyEnergyType == BATwinsEnergyPanel.EnergyType.SHARE) {
            this.GradientColor.add(Color.ORANGE);
        } else {
            this.glowColor = Color.ORANGE;
        }

    }

    public Optional<AbstractCard> hasCardInBringOutCards(AbstractCard card) {
        if (this.bringOutCard) {
            return this.cardToBringOut.stream().filter(card1 -> card1.cardID.equals(card.cardID)).findFirst();
        } else {
            return Optional.empty();
        }
    }

    public ArrayList<AbstractCard> getBringOutCards() {
        return this.cardToBringOut;
    }

    public void clearBringOutCards() {
        if (this.cardToBringOut.isEmpty()) {
            return;
        }
        if (this.cardsToPreview == this.cardToBringOut.get(0)) {
            this.cardsToPreview = null;
        }
        this.cardToBringOut.clear();
        this.bringOutCard = false;
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    public void bringOutCard(AbstractMonster m) {
        if (this.bringOutCard) {
            for (AbstractCard c : this.cardToBringOut)
                addToBot(new BATwinsPlayTempCardAction(c.makeStatEquivalentCopy(), this.numberOfConnections + 1, m));
        }
    }

    @Override
    public void renderCardPreview(SpriteBatch sb) {
        if (this.cardToBringOut.size() <= 1) {
            super.renderCardPreview(sb);
            if (!this.cardToBringOut.isEmpty() && this.cardToBringOut.get(0) == this.cardsToPreview) {
                this.renderCardChain(sb, this.cardsToPreview);
            }
            return;
        }
        int renderCardsNum = this.cardToBringOut.size();
        float tmpScale;
        tmpScale = (Settings.HEIGHT * 0.9F) / (renderCardsNum * AbstractCard.IMG_HEIGHT);
        tmpScale = Math.min(tmpScale, 0.8F);
        if (AbstractDungeon.player == null || !AbstractDungeon.player.isDraggingCard) {
            if (this.current_x > (float) Settings.WIDTH * 0.75F) {
                this.cardsToPreview.current_x = this.current_x + (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * tmpScale + 16.0F) * this.drawScale;
            } else {
                this.cardsToPreview.current_x = this.current_x - (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * tmpScale + 16.0F) * this.drawScale;
            }
            this.cardsToPreview.current_y = IMG_HEIGHT / 2.0F * tmpScale * this.drawScale;
            this.cardsToPreview.drawScale = tmpScale;
            this.cardsToPreview.render(sb);
        }
        int count = 2;
        if (this.bringOutCard) {
            for (AbstractCard c : this.cardToBringOut) {
                if (this.cardsToPreview != c) {
                    if (this.current_x > (float) Settings.WIDTH * 0.75F) {
                        c.current_x = this.current_x + (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * tmpScale + 16.0F) * this.drawScale;
                    } else {
                        c.current_x = this.current_x - (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * tmpScale + 16.0F) * this.drawScale;
                    }
                    c.current_y = (IMG_HEIGHT / 2.0F * tmpScale) * this.drawScale + IMG_HEIGHT * tmpScale * this.drawScale * (count - 1);
//                    c.current_y=c.current_y-IMG_HEIGHT*0.15F*count;
                    c.drawScale = tmpScale;
                    c.render(sb);
                    count++;
                }
                this.renderCardChain(sb, c);

            }
        }
    }

    private void renderCardChain(SpriteBatch sb, AbstractCard card) {
//        card.current_x=108.0F;
        sb.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        sb.draw(ImageHelper.CHAIN, card.current_x, card.current_y, (float) (ImageHelper.CHAIN.getWidth() * card.drawScale * Settings.scale), (float) (ImageHelper.CHAIN.getHeight() * card.drawScale * Settings.scale));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (this.bringOutCard) {
            for (AbstractCard c : this.cardToBringOut) {
                c.applyPowers();
            }
        }
    }

    public static class BATwinsAttackEffect {
        @SpireEnum
        public static AbstractGameAction.AttackEffect BATwinsShooting;
    }

    public static class BATwinsCardTags {
        @SpireEnum
        public static CardTags Adventure;
        @SpireEnum
        public static CardTags Shooting;
    }
}
