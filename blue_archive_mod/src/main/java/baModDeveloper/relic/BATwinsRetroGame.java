package baModDeveloper.relic;

import baModDeveloper.action.BATwinsExchangeAllAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsRetroGame extends CustomRelic {
    public static final String ID= ModHelper.makePath("RetroGame");
    private static final Texture texture= TextureLoader.getTexture(ModHelper.makeImgPath("relic","RetroGame"));
    private static final Texture outline=TextureLoader.getTexture(ModHelper.makeImgPath("relic","RetroGame_p"));
    private static final RelicTier type=RelicTier.BOSS;

    private final AbstractCard.CardColor[] colors=new AbstractCard.CardColor[2];
    public BATwinsRetroGame() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);
        colors[0]= BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
        colors[1]=BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStartPostDraw() {
        addToBot(new BATwinsExchangeAllAction(colors[AbstractDungeon.cardRandomRng.random(0,1)]));
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.masterHandSize+=2;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.masterHandSize-=2;
    }
}
