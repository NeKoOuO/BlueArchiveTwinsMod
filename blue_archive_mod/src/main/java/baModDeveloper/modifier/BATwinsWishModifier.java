package baModDeveloper.modifier;

import baModDeveloper.helpers.ModHelper;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class BATwinsWishModifier extends AbstractCardModifier {
    public static final String ID= ModHelper.makePath("WishMod");
    @Override
    public AbstractCardModifier makeCopy() {
        return new BATwinsWishModifier();
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cost=card.costForTurn=1;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {

    }
}
