package baModDeveloper.action;

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
    private int count;
    private AbstractCard card;
    int poisonNum;
    public BATwinsPaintingArtAction(AbstractCard card,int count,int poisonNum){
        this.card=card;
        this.count=count;
        this.poisonNum=poisonNum;
    }
    @Override
    public void update() {
        for(int i=0;i<this.count;i++){
            this.target=(AbstractCreature) AbstractDungeon.getMonsters().getRandomMonster();
            if(this.target!=null){
                this.card.calculateCardDamage((AbstractMonster) this.target);
                addToTop(new DamageAction(this.target,new DamageInfo(AbstractDungeon.player,this.card.damage,this.card.damageTypeForTurn),AttackEffect.POISON));
                addToTop((new ApplyPowerAction(this.target,AbstractDungeon.player,new PoisonPower(this.target,AbstractDungeon.player,this.poisonNum))));
            }
        }

        this.isDone=true;
    }
}
