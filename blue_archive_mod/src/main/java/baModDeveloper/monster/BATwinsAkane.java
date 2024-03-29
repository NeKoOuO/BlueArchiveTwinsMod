package baModDeveloper.monster;

import baModDeveloper.helpers.ModHelper;
import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class BATwinsAkane extends CustomMonster {
    public static final String ID= ModHelper.makePath("Akane");
    private static final MonsterStrings monsterStrings= CardCrawlGame.languagePack.getMonsterStrings(ID);
    private static final String NAME=monsterStrings.NAME;
    private static final int HP=80;
    private static final int A_2_HP=85;

    private static final float HB_X=3.0F;
    private static final float HB_Y=-10.0F;
    private static final float HB_W=189.0F;
    private static final float HB_H=321.0F;
    private static final float OffsetX=-100.0F;
    private static final float OffsetY=0.0F;
    private static final String ImgUrl=ModHelper.makeImgPath("monster","Akane");

    private static final int BASE_DMG=12;
    private static final int DEBUFF_DMG=6;
    private static final int BLOCK=7;

    private static final int A_2_BASE_DMG=15;
    private static final int A_2_DEBUFF_DMG=8;
    private static final int A_2_BLOCK=10;

    private static final int DEBUFF=2;
    private static final int DOWNDEX=1;

    private int baseDmg=BASE_DMG;
    private int debuffDmg=DEBUFF_DMG;
    private int block=BLOCK;

    private boolean firstTurn=true;
    public BATwinsAkane() {
        super(NAME, ID, HP, HB_X, HB_Y, HB_W, HB_H, ImgUrl, OffsetX, OffsetY);

        if(AbstractDungeon.ascensionLevel>=8){
            setHp(A_2_HP);
        }else{
            setHp(HP);
        }

        if(AbstractDungeon.ascensionLevel>=3){
            this.baseDmg=A_2_BASE_DMG;
            this.debuffDmg=A_2_DEBUFF_DMG;
            this.block=A_2_BLOCK;
        }else{
            this.baseDmg=BASE_DMG;
            this.debuffDmg=DEBUFF_DMG;
            this.block=BLOCK;
        }

        this.damage.add(new DamageInfo(this,this.baseDmg));
        this.damage.add(new DamageInfo(this,this.debuffDmg));


    }

    @Override
    public void takeTurn() {
        switch (this.nextMove){
            case 1:
                addToBot(new DamageAction(AbstractDungeon.player,this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            case 2:
                addToBot(new DamageAction(AbstractDungeon.player,this.damage.get(1), AbstractGameAction.AttackEffect.LIGHTNING));
                addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new FrailPower(AbstractDungeon.player,DEBUFF,true)));
                break;
            case 3:
                addToBot(new GainBlockAction(this,this.block));
                addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new DexterityPower(AbstractDungeon.player,-DOWNDEX),-DOWNDEX));
                break;
            case 4:
                addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new WeakPower(AbstractDungeon.player,DEBUFF,true)));
                addToBot(new TalkAction(this,monsterStrings.DIALOG[0]));
                break;
        }
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        if(firstTurn){
            setMove(monsterStrings.MOVES[0], (byte) 4, Intent.DEBUFF);
            this.firstTurn=false;
        }else {
            if(lastMove((byte) 4)||lastMove((byte) 3)){
                setMove(monsterStrings.MOVES[1], (byte) 2,Intent.ATTACK_DEBUFF,this.damage.get(1).base);
            } else if (lastMove((byte) 2)) {
                setMove(monsterStrings.MOVES[2], (byte) 1,Intent.ATTACK,this.damage.get(0).base);
            }else if(lastMove((byte) 1)){
                setMove((byte) 3,Intent.DEFEND_DEBUFF);
            }
        }

    }
}
