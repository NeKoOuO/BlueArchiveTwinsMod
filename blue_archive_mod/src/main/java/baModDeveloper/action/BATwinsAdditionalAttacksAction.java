package baModDeveloper.action;

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
    private final DamageInfo info;
    AbstractGameAction.AttackEffect effect;

    public BATwinsAdditionalAttacksAction(AbstractCard.CardColor color, DamageInfo info, AbstractGameAction.AttackEffect effect) {
        this.color = color;
        this.info = info;
        this.effect = effect;
    }

    @Override
    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
        AbstractDungeon.effectsQueue.add(new BATwinsAdditionAttacksEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, target.hb.cX, target.hb.cY, BATwinsCharacter.getColorWithCardColor(this.color).cpy()));

        if (this.target != null) {
            this.addToTop(new DamageAction(this.target, this.info, this.effect));
        }

        this.isDone = true;

    }
}
