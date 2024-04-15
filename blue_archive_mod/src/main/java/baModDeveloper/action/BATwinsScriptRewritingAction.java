package baModDeveloper.action;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.power.BATwinsBurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class BATwinsScriptRewritingAction extends AbstractGameAction {
    private boolean upgrade;
    private AbstractCard.CardColor color;

    public BATwinsScriptRewritingAction(boolean upgrade, AbstractCard.CardColor color, AbstractMonster target) {
        this.upgrade = upgrade;
        this.color = color;
        this.target = target;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.color == BATwinsCharacter.Enums.BATWINS_MOMOI_CARD) {
            if (!this.upgrade) {
                if (this.target.hasPower(PoisonPower.POWER_ID)) {
                    addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player, new BATwinsBurnPower(this.target, AbstractDungeon.player, this.target.getPower(PoisonPower.POWER_ID).amount)));
                }
            }

            if (this.target.hasPower(BATwinsBurnPower.POWER_ID)) {
                addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player, new PoisonPower(this.target, AbstractDungeon.player, this.target.getPower(BATwinsBurnPower.POWER_ID).amount)));
            }
        } else {
            if (!this.upgrade) {
                if (this.target.hasPower(BATwinsBurnPower.POWER_ID)) {
                    addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player, new PoisonPower(this.target, AbstractDungeon.player, this.target.getPower(BATwinsBurnPower.POWER_ID).amount)));
                }
            }
            if (this.target.hasPower(PoisonPower.POWER_ID)) {
                addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player, new BATwinsBurnPower(this.target, AbstractDungeon.player, this.target.getPower(PoisonPower.POWER_ID).amount)));
            }
        }

        this.isDone = true;

    }
}
