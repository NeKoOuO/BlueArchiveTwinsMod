package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class BATwinsByProving extends CustomRelic implements CustomSavable<String> {
    public static final String ID = ModHelper.makePath("ByProving");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "ByProving"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "ByProving_p"));
    private static final RelicTier type = RelicTier.SPECIAL;
    private AbstractCard card;

    public BATwinsByProving() {
        super(ID, texture, outline, type, LandingSound.MAGICAL);
    }

    public BATwinsByProving(AbstractCard card) {
        super(ID, texture, outline, type, LandingSound.MAGICAL);
        this.card = card.makeCopy();
        this.description = getUpdatedDescription();
        this.flavorText = String.format(DESCRIPTIONS[4], getCurrentDate());
    }

    @Override
    public String getUpdatedDescription() {
        if (card == null) {
            return DESCRIPTIONS[3];
        } else {
            switch (card.type) {
                case ATTACK:
                    return String.format(DESCRIPTIONS[0], card.name);
                case SKILL:
                    return String.format(DESCRIPTIONS[1], card.name);
                case POWER:
                    return String.format(DESCRIPTIONS[2], card.name);
                default:
                    return DESCRIPTIONS[3];
            }
        }
    }

    private String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

//    @Override
//    public void onPlayCard(AbstractCard c, AbstractMonster m) {
//        if(Objects.equals(c.cardID, this.card.cardID)){
//            this.flash();
//            switch (c.type){
//                case ATTACK:
//                    AbstractMonster monster= AbstractDungeon.getCurrRoom().monsters.getRandomMonster();
//                    int damage=c.damage;
//
//                    addToBot(new DamageAction(monster,new DamageInfo(AbstractDungeon.player,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
//                    return;
//                case SKILL:
//                    addToBot(new DrawCardAction(2));
//                    return;
//                case POWER:
//                    addToBot(new MakeTempCardInDrawPileAction(c.makeSameInstanceOf(),1,true,true));
//                    return;
//                default:
//                    return;
//            }
//        }
//
//    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction useCardAction) {
        if (this.card == null) {
            return;
        }
        if (Objects.equals(c.cardID, this.card.cardID)) {
            this.flash();
            switch (c.type) {
                case ATTACK:
                    AbstractMonster monster = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
                    int damage = c.damage;

                    addToBot(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                    return;
                case SKILL:
                    addToBot(new DrawCardAction(2));
                    return;
                case POWER:
                    addToBot(new MakeTempCardInDiscardAction(c.makeSameInstanceOf(), 1));
                    return;
                default:
                    return;
            }
        }
    }

    @Override
    public String onSave() {
        return card.cardID;
    }

    @Override
    public void onLoad(String s) {
        this.card = CardLibrary.getCard(s);
        this.description = getUpdatedDescription();
        this.flavorText = String.format("一块崭新的奖章，上面写着\n新手关卡通过证明！--%s。", getCurrentDate());
    }
}
