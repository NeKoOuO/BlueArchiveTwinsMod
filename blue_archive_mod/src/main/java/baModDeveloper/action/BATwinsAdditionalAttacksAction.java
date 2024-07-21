package baModDeveloper.action;

import baModDeveloper.cards.BATwinsAdditionalAttacks;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.effect.BATwinsAdditionAttacksEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsAdditionalAttacksAction extends AbstractGameAction {
    private final AbstractCard.CardColor color;
    private final BATwinsAdditionalAttacks card;
    AbstractGameAction.AttackEffect effect;

    public BATwinsAdditionalAttacksAction(AbstractCard.CardColor color, BATwinsAdditionalAttacks card, AttackEffect effect) {
        this.color = color;
        this.card = card;
        this.effect = effect;
    }

    @Override
    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        this.card.calculateCardDamage((AbstractMonster) this.target);
        AbstractDungeon.effectsQueue.add(new BATwinsAdditionAttacksEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, target.hb.cX, target.hb.cY, BATwinsCharacter.getColorWithCardColor(this.color).cpy()));

        if (this.target != null) {
            this.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player,this.card.damage), this.effect));
        }

        this.isDone = true;

    }
}
