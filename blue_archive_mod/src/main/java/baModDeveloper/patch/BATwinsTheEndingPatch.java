package baModDeveloper.patch;

import baModDeveloper.BATwinsMod;
import baModDeveloper.event.BATwinsSoraShopRoom;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;

import java.util.ArrayList;

public class BATwinsTheEndingPatch {
    @SuppressWarnings("unused")
    @SpirePatch(clz = TheEnding.class, method = "generateSpecialMap")
    public static class generateSpecialMapPatch {
        @SpirePostfixPatch
        public static void postfixPatch(TheEnding _instance, ArrayList<ArrayList<MapRoomNode>> ___map) {
            if(BATwinsMod.saveHelper.values.hasSoraPhone){
                MapRoomNode node = new MapRoomNode(0, 1);

                node.room = new BATwinsSoraShopRoom();
                MapRoomNode restNode = ___map.get(0).get(3);
                MapRoomNode enemyNode = ___map.get(1).get(3);
                connectNode(restNode,node);
                connectNode(node,enemyNode);
                for(int i=0;i<___map.get(1).size();i++){
                    if(___map.get(1).get(i).room==null){
                        ___map.get(1).set(i,node);
                        break;
                    }
                }
            }


        }
    }

    private static void connectNode(MapRoomNode src, MapRoomNode dst) {
        src.addEdge(new MapEdge(src.x, src.y, src.offsetX, src.offsetY, dst.x, dst.y, dst.offsetX, dst.offsetY, false));
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "nextRoomTransition", paramtypez = {SaveFile.class})
    public static class AbstractDungeonNextRoomTransitionPatch {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(Instanceof i) throws CannotCompileException {
                    try {
                        if (i.getType().getName().equals(EventRoom.class.getName()))
                            i.replace(String.format("{ $_ = $proceed($$) && !($1 instanceof %s); }", new Object[]{BATwinsSoraShopRoom.class.getName()}));
                    } catch (NotFoundException notFoundException) {
                    }
                }
            };
        }
    }
}
