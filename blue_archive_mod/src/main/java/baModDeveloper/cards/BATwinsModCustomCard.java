package baModDeveloper.cards;

import baModDeveloper.BATwinsMod;
import baModDeveloper.action.BATwinsPlayTempCardAction;
import baModDeveloper.cards.bullets.BATwinsCustomBulletCard;
import baModDeveloper.character.BATwinsCharacter;
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

public abstract class BATwinsModCustomCard extends CustomCard {
    public BATwinsEnergyPanel.EnergyType modifyEnergyType;
    public CardColor OriginalColor;
    public boolean playBackOriginalColor = false;
    public ArrayList<Color> GradientColor = new ArrayList<>();
    private int startColor = 0;
    private float gradientDuration = 0.0F;

    public int numberOfConnections = 0;
    public boolean justHovered = false;
    protected boolean bringOutCard = false;
    protected ArrayList<AbstractCard> cardToBringOut = new ArrayList<>();
    protected String originRawDescription;

    public static UIStrings flatFallMsg = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("FlatFall"));

    //    public boolean playedByOtherCard=false;
    public BATwinsModCustomCard(String ID, String NAME, String IMG_PATH, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET, BATwinsEnergyPanel.EnergyType ENERGYTYPE) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originRawDescription = DESCRIPTION;
        this.OriginalColor = COLOR;
        this.modifyEnergyType = ENERGYTYPE;
        this.GradientColor.add(BATwinsMod.MOMOIColor);
        this.GradientColor.add(BATwinsMod.MIDORIColor);

    }

    @Override
    public boolean hasEnoughEnergy() {
        if (AbstractDungeon.actionManager.turnHasEnded) {
            this.cantUseMessage = TEXT[9];
        } else {
            Iterator var1 = AbstractDungeon.player.powers.iterator();

            AbstractPower p;
            do {
                if (!var1.hasNext()) {
                    if (AbstractDungeon.player.hasPower("Entangled") && this.type == AbstractCard.CardType.ATTACK) {
                        this.cantUseMessage = TEXT[10];
                        return false;
                    }
                    if (AbstractDungeon.player.hasPower(BATwinsFlatFallPower.POWER_ID) && this.type == CardType.ATTACK) {
                        if (AbstractDungeon.player.getPower(BATwinsFlatFallPower.POWER_ID).amount == 0) {
                            this.cantUseMessage = flatFallMsg.TEXT[0];
                            return false;
                        }
                    }

                    var1 = AbstractDungeon.player.relics.iterator();

                    AbstractRelic r;
                    do {
                        if (!var1.hasNext()) {
                            var1 = AbstractDungeon.player.blights.iterator();

                            AbstractBlight b;
                            do {
                                if (!var1.hasNext()) {
                                    var1 = AbstractDungeon.player.hand.group.iterator();

                                    AbstractCard c;
                                    do {
                                        if (!var1.hasNext()) {
                                            if (AbstractDungeon.overlayMenu.energyPanel instanceof BATwinsEnergyPanel) {
                                                if (this.freeToPlay() || this.isInAutoplay) {
                                                    return true;
                                                }
                                                boolean hasEnoughEnergy = true;
                                                if (this.modifyEnergyType == BATwinsEnergyPanel.EnergyType.SHARE || AbstractDungeon.player.hasPower(BATwinsBorrowMePower.POWER_ID)) {
                                                    if (BATwinsEnergyPanel.MomoiCount + BATwinsEnergyPanel.MidoriCount < this.costForTurn) {
                                                        this.cantUseMessage = TEXT[11];
                                                        return false;
                                                    }

                                                } else if (this.modifyEnergyType == BATwinsEnergyPanel.EnergyType.MOMOI) {
                                                    if (BATwinsEnergyPanel.MomoiCount + BATwinsEnergyPanel.MidoriCount / 2 < this.costForTurn) {
                                                        this.cantUseMessage = TEXT[11];
                                                        return false;
                                                    }
                                                } else if (this.modifyEnergyType == BATwinsEnergyPanel.EnergyType.MIDORI) {
                                                    if (BATwinsEnergyPanel.MidoriCount + BATwinsEnergyPanel.MomoiCount / 2 < this.costForTurn) {
                                                        this.cantUseMessage = TEXT[11];
                                                        return false;
                                                    }
                                                }
                                            } else {
                                                if (EnergyPanel.totalCount < this.costForTurn && !this.freeToPlay() && !this.isInAutoplay) {
                                                    this.cantUseMessage = TEXT[11];
                                                    return false;
                                                }
                                            }
                                            return true;
                                        }

                                        c = (AbstractCard) var1.next();
                                    } while (c.canPlay(this));

                                    return false;
                                }

                                b = (AbstractBlight) var1.next();
                            } while (b.canPlay(this));

                            return false;
                        }

                        r = (AbstractRelic) var1.next();
                    } while (r.canPlay(this));

                    return false;
                }

                p = (AbstractPower) var1.next();
            } while (p.canPlayCard(this));

            this.cantUseMessage = TEXT[13];
        }
        return false;
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

        //如果有技艺大师buff则升级
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(BATwinsMasterCraftsmanshipPower.POWER_ID)) {
            this.upgrade();
            AbstractDungeon.player.getPower(BATwinsMasterCraftsmanshipPower.POWER_ID).flash();
        }
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
            temp.glowColor = this.glowColor;
            temp.cardToBringOut = this.cardToBringOut;
            temp.cardsToPreview = this.cardsToPreview;
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
        description = exchangeStr(description, "中毒", "batwinsmod:灼伤");
        description = exchangeStr(description, "[batwinsmod:midoriorbicon]", "[batwinsmod:momoiorbicon]");
        description = exchangeStr(description, "batwinsmod:桃牌", "batwinsmod:绿牌");
        description = exchangeStr(description, "虚弱", "易伤");
        return description;
    }

    private String exchangeStr(String description, String str1, String str2) {
        String temp = "#######";
        description = description.replace(str1, temp);
        description = description.replace(str2, str1);
        description = description.replace(temp, str2);
        return description;
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

    public boolean hasCardInBringOutCards(AbstractCard card) {
        if (this.bringOutCard) {
            if (this.cardToBringOut.stream().anyMatch(card1 -> card1.cardID.equals(card.cardID))) {
                return true;
            }
        }
        return false;
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
        super.renderCardPreview(sb);
        int count = 2;
        if (this.bringOutCard) {
            for (AbstractCard c : this.cardToBringOut) {
                if (this.cardsToPreview != c) {
                    float tmpScale = this.drawScale * 0.8F;
                    if (this.current_x > (float) Settings.WIDTH * 0.75F) {
                        c.current_x = this.current_x + ((IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.8F + 12.0F) * this.drawScale) * count;
                    } else {
                        c.current_x = this.current_x - ((IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.8F + 12.0F) * this.drawScale) * count;
                    }
                    c.current_y = this.current_y + (IMG_HEIGHT / 2.0F - IMG_HEIGHT / 2.0F * 0.8F) * this.drawScale;
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
}
