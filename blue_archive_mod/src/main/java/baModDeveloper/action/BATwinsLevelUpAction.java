package baModDeveloper.action;

import baModDeveloper.power.BATwinsExperiencePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsLevelUpAction extends AbstractGameAction {
    private int amount;
    private AbstractPlayer p;
    private boolean clearExp;

    public BATwinsLevelUpAction(int amount, boolean clearExp) {
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.clearExp = clearExp;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.p.hasPower(BATwinsExperiencePower.POWER_ID)) {
            BATwinsExperiencePower pow = (BATwinsExperiencePower) this.p.getPower(BATwinsExperiencePower.POWER_ID);
            pow.levelup(this.amount, this.clearExp);
        } else {
            addToTop(new ApplyPowerAction(this.p, this.p, new BATwinsExperiencePower(this.p, this.amount * 10)));
        }
        this.isDone = true;
        tickDuration();
    }
}
