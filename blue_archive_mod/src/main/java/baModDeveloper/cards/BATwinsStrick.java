package baModDeveloper.cards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import baModDeveloper.helpers.ModHelper;
import basemod.abstracts.CustomCard;

public class BATwinsStrick extends CustomCard{

    public static final String ID=ModHelper.makePath("Strick");
    private static final String NAME="打击";
    private static final String IMG_PATH="baModResources/img/cards/anger.png";
    private static final int COST=1;
    private static final String DESCRIPTION="dfald";
    private static final CardType TYPE=CardType.ATTACK;
    private static final CardColor COLOR=CardColor.COLORLESS;
    private static final CardTarget TARGET=CardTarget.ENEMY;
    private static final CardRarity RARITY=CardRarity.BASIC;

    public BATwinsStrick() {
        super(ID,NAME,IMG_PATH,COST,DESCRIPTION,TYPE,COLOR,RARITY,TARGET);
        this.damage=this.baseDamage=100;
        this.tags.add(CardTags.STARTER_STRIKE);
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeDamage(100);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageType.NORMAL)));
    }
    
}
