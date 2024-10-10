package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

public class BATwinsCrystalHaniwa extends CustomRelic {
    public static final String ID = ModHelper.makePath("CrystalHaniwa");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "CrystalHaniwa"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "CrystalHaniwa_p"));
    private static final RelicTier type = RelicTier.SPECIAL;
    private int count=2;
    public BATwinsCrystalHaniwa() {
        super(ID, texture, outline, type, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        if (!this.grayscale && (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss||
                AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite)) {
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, 3, false), 3, true, AbstractGameAction.AttackEffect.NONE));
                addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, 3, false), 3, true, AbstractGameAction.AttackEffect.NONE));
            }
            if(this.count<0)
                this.grayscale=true;
            this.count--;
        }
    }
}
