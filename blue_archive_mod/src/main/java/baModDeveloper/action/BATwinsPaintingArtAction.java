package baModDeveloper.action;

import baModDeveloper.power.BATwinsBurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class BATwinsPaintingArtAction extends AbstractGameAction {
    DamageInfo.DamageType type;
    private AbstractCard card;
    private int poisonNum;
    private boolean exchange;

    public BATwinsPaintingArtAction(AbstractCard card, int poisonNum, boolean exchange, DamageInfo.DamageType type) {
        this.card = card;
        this.poisonNum = poisonNum;
        this.exchange = exchange;
        this.type = type;
    }

    @Override
    public void update() {
        this.target = (AbstractCreature) AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.card.calculateCardDamage((AbstractMonster) this.target);
            if (!exchange) {
                addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.type), AttackEffect.POISON));
                addToTop((new ApplyPowerAction(this.target, AbstractDungeon.player, new PoisonPower(this.target, AbstractDungeon.player, this.poisonNum))));
            } else {
                addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.type), AttackEffect.FIRE));
                addToTop((new ApplyPowerAction(this.target, AbstractDungeon.player, new BATwinsBurnPower(this.target, AbstractDungeon.player, this.poisonNum))));
            }

        }


        this.isDone = true;
    }
}
