package baModDeveloper.event;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.relic.BATwinsPackage;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BATwinsTransportationTask extends AbstractImageEvent {
    public static final String ID = ModHelper.makePath("TransportationTask");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String imgUrl = ModHelper.makeImgPath("event", "TransportationTask");
    private static int GLOD=80;
    private int glod=GLOD;
    private AbstractRelic relic;
    private enum CurrentPhase{
        START,TALK1,TALK2,TALK3,END,NOGAVEN;
    }
    private CurrentPhase phase;

    public BATwinsTransportationTask() {
        super(title, DESCRIPTIONS[0], imgUrl);
        this.phase=CurrentPhase.START;
        this.imageEventText.clearAllDialogs();
        this.imageEventText.updateBodyText(DESCRIPTIONS[0]);
        this.imageEventText.setDialogOption(String.format(OPTIONS[0],this.glod));
        this.imageEventText.setDialogOption(OPTIONS[1]);

        this.relic=AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON);
    }

    @Override
    protected void buttonEffect(int i) {
        if(this.phase==CurrentPhase.START){
            switch (i){
                case 0:
                    gainReword();
                    break;
                case 1:
                    this.phase=CurrentPhase.TALK1;
                    this.glod+=40;
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                    this.imageEventText.setDialogOption(String.format(OPTIONS[0],this.glod));
                    this.imageEventText.setDialogOption(OPTIONS[1]);
                    break;
            }
        }else if(this.phase==CurrentPhase.TALK1) {
            switch (i) {
                case 0:
                    gainReword();
                    break;
                case 1:
                    this.phase=CurrentPhase.TALK2;
                    this.glod+=17;
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                    this.imageEventText.setDialogOption(String.format(OPTIONS[0],this.glod));
                    this.imageEventText.setDialogOption(OPTIONS[1]);
                    break;
            }
        }else if(this.phase==CurrentPhase.TALK2) {
            switch (i) {
                case 0:
                    gainReword();
                    break;
                case 1:
                    this.phase = CurrentPhase.TALK3;
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                    this.imageEventText.setDialogOption(String.format(OPTIONS[2], this.glod,this.relic.name),this.relic);
                    this.imageEventText.setDialogOption(OPTIONS[1]);
            }
        }else if(this.phase==CurrentPhase.TALK3){
            switch (i) {
                case 0:
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(((float) Settings.WIDTH / 2), ((float) Settings.HEIGHT / 2), this.relic);
                    gainReword();
                    break;
                case 1:
                    this.phase = CurrentPhase.NOGAVEN;
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                    this.imageEventText.setDialogOption(OPTIONS[3]);
            }
        }else if(this.phase==CurrentPhase.END){
            openMap();
        }else if(this.phase==CurrentPhase.NOGAVEN){
            openMap();
        }
    }

    private void gainReword(){
        AbstractDungeon.player.loseRelic(BATwinsPackage.ID);
        AbstractDungeon.player.gainGold(this.glod);
        this.phase=CurrentPhase.END;
        this.imageEventText.clearAllDialogs();
        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

}
