package baModDeveloper.patch;

import baModDeveloper.BATwinsMod;
import baModDeveloper.event.BATwinsSoraShopRoom;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;

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
}
