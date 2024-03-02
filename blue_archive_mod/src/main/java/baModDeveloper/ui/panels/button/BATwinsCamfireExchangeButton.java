package baModDeveloper.ui.panels.button;

import baModDeveloper.effect.BATwinsCamfireExchangeEffect;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.interfaces.PostCampfireSubscriber;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

public class BATwinsCamfireExchangeButton extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("ExchangeButton"));
    public static final String[] TEXT = uiStrings.TEXT;
    private static final String buttonImg = ModHelper.makeImgPath("UI", "exchangeButton");

    public BATwinsCamfireExchangeButton() {
        this.label = TEXT[0];
        this.usable = true;
        this.img = TextureLoader.getTexture(buttonImg);
        this.description = TEXT[0];
    }

    @Override
    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new BATwinsCamfireExchangeEffect());
        }
    }


}
