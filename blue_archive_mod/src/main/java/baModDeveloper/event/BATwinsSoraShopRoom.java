package baModDeveloper.event;

import basemod.eventUtil.EventUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.EventRoom;

public class BATwinsSoraShopRoom extends EventRoom {

    public BATwinsSoraShopRoom(){
        super();
    }
    @Override
    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.event= EventUtils.getEvent(BATwinsSoraShop.ID);
        this.event.onEnterRoom();
    }
}
