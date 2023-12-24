package baModDeveloper.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.Comparator;

public class ColorComparer implements Comparator<AbstractCard> {
    @Override
    public int compare(AbstractCard o1, AbstractCard o2) {
        return o1.color.compareTo(o2.color);
    }
}
