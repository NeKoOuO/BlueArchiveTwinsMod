package baModDeveloper.relic;

import baModDeveloper.action.BATwinsSwordOfLightAction;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BATwinsSwordOfLight extends CustomRelic {
    public static final String ID = ModHelper.makePath("SwordOfLight");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "SwordOfLight"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "SwordOfLight_p"));
    private static final RelicTier type = RelicTier.SPECIAL;
    private final int damage = 50;
    private final int count = 1;

    public BATwinsSwordOfLight() {
        super(ID, texture, outline, type, LandingSound.HEAVY);

    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], this.count, this.damage);
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

//    @Override
//    public void onPlayCard(AbstractCard c, AbstractMonster m) {
//        this.counter++;
//        if(this.counter>=count){
//            flash();
//            addToBot((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature) AbstractDungeon.player, this));
//            addToBot((AbstractGameAction)new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
//            stopPulse();
//            this.grayscale=true;
//       }
//    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        this.counter++;
        if (this.counter == count) {
            flash();
            addToBot((AbstractGameAction) new RelicAboveCreatureAction((AbstractCreature) AbstractDungeon.player, this));
            addToBot(new BATwinsSwordOfLightAction());
            addToBot((AbstractGameAction) new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            stopPulse();
            this.grayscale = true;
        }
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
        stopPulse();
    }
}
