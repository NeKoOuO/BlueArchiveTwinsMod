package baModDeveloper.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.metrics.MetricData;

public class BATwinsMetricDataPatch {
    @SpirePatch(clz = MetricData.class,method = SpirePatch.CLASS)
    public static class FiledPatch{
        public static SpireField<Integer> campfire_exchange=new SpireField<>(()->0);
    }

}
