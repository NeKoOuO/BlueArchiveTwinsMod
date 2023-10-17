package baModDeveloper.cards;

import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;

public abstract class BATwinsModCustomCard extends CustomCard {
    public BATwinsEnergyPanel.EnergyType energyType;
    public BATwinsEnergyPanel.EnergyType modifyEnergyType;
    public BATwinsModCustomCard(String ID, String NAME, String IMG_PATH, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET, BATwinsEnergyPanel.EnergyType ENERGYTYPE) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.energyType=ENERGYTYPE;
        this.modifyEnergyType=this.energyType;
    }

    @Override
    public boolean hasEnoughEnergy() {
        if (AbstractDungeon.actionManager.turnHasEnded) {
            this.cantUseMessage = TEXT[9];
            return false;
        } else {
            Iterator var1 = AbstractDungeon.player.powers.iterator();

            AbstractPower p;
            do {
                if (!var1.hasNext()) {
                    if (AbstractDungeon.player.hasPower("Entangled") && this.type == AbstractCard.CardType.ATTACK) {
                        this.cantUseMessage = TEXT[10];
                        return false;
                    }

                    var1 = AbstractDungeon.player.relics.iterator();

                    AbstractRelic r;
                    do {
                        if (!var1.hasNext()) {
                            var1 = AbstractDungeon.player.blights.iterator();

                            AbstractBlight b;
                            do {
                                if (!var1.hasNext()) {
                                    var1 = AbstractDungeon.player.hand.group.iterator();

                                    AbstractCard c;
                                    do {
                                        if (!var1.hasNext()) {
                                            if(AbstractDungeon.overlayMenu.energyPanel instanceof BATwinsEnergyPanel){
                                                boolean hasEnoughEnergy=true;
                                                if(this.energyType== BATwinsEnergyPanel.EnergyType.MOMOI){
                                                    if(BATwinsEnergyPanel.MomoiCount+BATwinsEnergyPanel.MidoriCount/2<this.costForTurn){
                                                        this.cantUseMessage=TEXT[11];
                                                        return false;
                                                    }
                                                } else if (this.energyType== BATwinsEnergyPanel.EnergyType.MIDORI) {
                                                    if(BATwinsEnergyPanel.MidoriCount+BATwinsEnergyPanel.MomoiCount/2<this.costForTurn){
                                                        this.cantUseMessage=TEXT[11];
                                                        return false;
                                                    }
                                                }
                                            }else{
                                                if (EnergyPanel.totalCount < this.costForTurn && !this.freeToPlay() && !this.isInAutoplay) {
                                                    this.cantUseMessage = TEXT[11];
                                                    return false;
                                                }
                                            }
                                            return true;
                                        }

                                        c = (AbstractCard)var1.next();
                                    } while(c.canPlay(this));

                                    return false;
                                }

                                b = (AbstractBlight)var1.next();
                            } while(b.canPlay(this));

                            return false;
                        }

                        r = (AbstractRelic)var1.next();
                    } while(r.canPlay(this));

                    return false;
                }

                p = (AbstractPower)var1.next();
            } while(p.canPlayCard(this));

            this.cantUseMessage = TEXT[13];
            return false;
        }
    }

}
