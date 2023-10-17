package baModDeveloper.patch;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.CardTrailEffect;

@SpirePatch(clz = CardTrailEffect.class,method = "init")
public class BATwinsCardTrailEffectFix {
    public static BATwinsEnergyPanel.EnergyType cardType= BATwinsEnergyPanel.EnergyType.MIDORI;
    @SpirePostfixPatch
    public static void InitFix(CardTrailEffect _instance, float x, float y, @ByRef Color[] ___color){
        if(AbstractDungeon.player instanceof BATwinsCharacter){
            ___color[0]=((BATwinsCharacter) AbstractDungeon.player).getCardTrailColor(BATwinsCardTrailEffectFix.cardType);
        }
    }
}
