package baModDeveloper.cards;

import baModDeveloper.action.BATwinsGainEnergyAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsExperiencePower;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsAdventureRewards extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("AdventureRewards");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","defaultSkill");
    private static final int COST=0;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.SKILL;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    private static final CardTarget TARGET=CardTarget.SELF;
    private static final CardRarity RARITY=CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MIDORI;

    public BATwinsAdventureRewards() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseMagicNumber=3;
        this.magicNumber=this.baseMagicNumber;
        this.tags.add(BATwinsCardTags.Adventure);
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int choice= AbstractDungeon.cardRandomRng.random(0,this.magicNumber);
        useCardEffect(choice);
        if(upgraded){
            int choice2=-1;
            do{
                choice2=AbstractDungeon.cardRandomRng.random(0,this.magicNumber);
            }while(choice==choice2);
            useCardEffect(choice2);
        }

    }

    @Override
    public void upgrade() {
        if(!upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription=CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.originRawDescription=CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    private void useCardEffect(int choice){
        switch (choice){
            case 0:
                addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new BATwinsExperiencePower(AbstractDungeon.player,5)));
                break;
            case 1:
                addToBot(new HealAction(AbstractDungeon.player,AbstractDungeon.player,3));
                break;
            case 2:
                addToBot(new BATwinsGainEnergyAction(1, BATwinsEnergyPanel.EnergyType.ALL));
                addToBot(new DrawCardAction(2));
                break;
            case 3:
                addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
                break;
        }
    }
}
