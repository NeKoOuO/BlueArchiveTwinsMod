package baModDeveloper.patch;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.core.BATwinsEnergyManager;
import baModDeveloper.power.BATwinsBorrowMePower;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class BATwinsAbstractPlayerPatch {
    public static void useBATwinsEnergy(int cost, AbstractPlayer player, AbstractCard c) {
        if (c instanceof BATwinsModCustomCard) {
            if (player.hasPower(BATwinsBorrowMePower.POWER_ID)) {
                ((BATwinsEnergyManager) player.energy).use(cost, BATwinsEnergyPanel.EnergyType.SHARE);
            } else {
                ((BATwinsEnergyManager) player.energy).use(cost, ((BATwinsModCustomCard) c).modifyEnergyType);
            }
        } else {
            ((BATwinsEnergyManager) player.energy).use(cost, BATwinsEnergyPanel.EnergyType.SHARE);
        }

    }

    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class useCardPatch {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (isMethodCalled(m)) {
                        m.replace("{if(" + AbstractDungeon.class.getName() + ".player instanceof " + BATwinsCharacter.class.getName() + "){" +
                                BATwinsAbstractPlayerPatch.class.getName() + ".useBATwinsEnergy($1,this,c);" +
                                "}else{$_=$proceed($$);}}");
                    }
                }

                private boolean isMethodCalled(MethodCall m) {
                    return m.getClassName().equals(EnergyManager.class.getName()) && m.getMethodName().equals("use");
                }
            };
        }


    }
}
