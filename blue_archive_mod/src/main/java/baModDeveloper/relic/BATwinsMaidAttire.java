package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import java.util.Arrays;

public class BATwinsMaidAttire extends CustomRelic {
    public static final String ID= ModHelper.makePath("MaidAttire");
    private static final Texture texture= TextureLoader.getTexture(ModHelper.makeImgPath("relic","MaidAttire"));
    private static final Texture outline=TextureLoader.getTexture(ModHelper.makeImgPath("relic","MaidAttire_p"));
    private static final RelicTier type=RelicTier.SHOP;

    boolean[] costs=new boolean[4];
    private boolean triggered=false;
    public BATwinsMaidAttire() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);
        for(boolean i:costs){
            i=false;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if(targetCard.costForTurn<4&&targetCard.costForTurn>=0){
            this.costs[targetCard.costForTurn]=true;
        }
        for(boolean b:costs){
            if(!b){
                return;
            }
        }
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new IntangiblePlayerPower(AbstractDungeon.player,1)));
    }

    @Override
    public void atTurnStart() {
        Arrays.fill(costs, false);
    }
}
