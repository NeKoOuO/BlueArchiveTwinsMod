package baModDeveloper.action;

import baModDeveloper.cards.BATwinsAdditionalAttacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;
import com.megacrit.cardcrawl.vfx.combat.BloodShotParticleEffect;

public class BATwinsAdditionalAttacksAction extends AbstractGameAction {
    private AbstractCard.CardColor color;
    private DamageInfo info;
    AbstractGameAction.AttackEffect effect;
    public BATwinsAdditionalAttacksAction(AbstractCard.CardColor color,DamageInfo info,AbstractGameAction.AttackEffect effect){
        this.color=color;
        this.info=info;
        this.effect=effect;
    }
    @Override
    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
        AbstractDungeon.effectsQueue.add(new BloodShotParticleEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY,target.hb.cX,target.hb.cY));

        if (this.target != null) {
            this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
        }

        this.isDone = true;

    }
}
