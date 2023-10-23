package baModDeveloper;

import baModDeveloper.cards.*;
import basemod.interfaces.EditKeywordsSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.Settings.GameLanguage;
import com.megacrit.cardcrawl.localization.*;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.character.BATwinsCharacter.Enums;
import baModDeveloper.helpers.ModHelper;
import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditStringsSubscriber;

import java.nio.charset.StandardCharsets;

import static com.megacrit.cardcrawl.core.Settings.language;

@SpireInitializer
public class BATwinsMod implements EditCardsSubscriber,EditStringsSubscriber,EditCharactersSubscriber, EditKeywordsSubscriber {

    public static final Color BATwinsColor = new Color(254.0F / 255.0F, 168.0F / 255.0F, 198.0F / 255.0F, 1.0F);
    public static final Color MOMOIColor = new Color(254.0F / 255.0F, 168.0F / 255.0F, 198.0F / 255.0F, 1.0F);
    public static final Color MIDORIColor = new Color(85.0F / 255.0F, 171.0F / 255.0F, 72.0F / 255.0F, 1.0F);
    private static final String BATWINS_CHARACTE_BUTTON = ModHelper.makeImgPath("char", "Character_Button");
    private static final String BATWINS_CHARACTER_PORTRAIT = ModHelper.makeImgPath("char", "Character_Portrait");
    private static final String BATWINS_MOMOI_ATTACK_512 = ModHelper.makeImgPath("512", "bg_attack_512");
    private static final String BATWINS_MOMOI_POWER_512 = ModHelper.makeImgPath("512", "bg_power_512");
    private static final String BATWINS_MOMOI_SKILL_512 = ModHelper.makeImgPath("512", "bg_skill_512");
    private static final String BATWINS_MIDORI_ATTACK_512 = ModHelper.makeImgPath("512", "bg_attack_512_2");
    private static final String BATWINS_MIDORI_POWER_512 = ModHelper.makeImgPath("512", "bg_power_512_2");
    private static final String BATWINS_MIDORI_SKILL_512 = ModHelper.makeImgPath("512", "bg_skill_512_2");
    private static final String MOMOI_SMALL_ORB = ModHelper.makeImgPath("512", "small_orb");
    private static final String MIDORI_SMALL_ORB = ModHelper.makeImgPath("512", "small_orb_2");
    private static final String BATWINS_MOMOI_ATTACK_1024 = ModHelper.makeImgPath("1024", "bg_attack_1024");
    private static final String BATWINS_MOMOI_POWER_1024 = ModHelper.makeImgPath("1024", "bg_power_1024");
    private static final String BATWINS_MOMOI_SKILL_1024 = ModHelper.makeImgPath("1024", "bg_skill_1024");
    private static final String BATWINS_MIDORI_ATTACK_1024 = ModHelper.makeImgPath("1024", "bg_attack_1024_2");
    private static final String BATWINS_MIDORI_POWER_1024 = ModHelper.makeImgPath("1024", "bg_power_1024_2");
    private static final String BATWINS_MIDORI_SKILL_1024 = ModHelper.makeImgPath("1024", "bg_skill_1024_2");
    private static final String MOMOI_BIG_ORB = ModHelper.makeImgPath("512", "card_orb");
    private static final String MIDORI_BIG_ORB = ModHelper.makeImgPath("512", "card_orb_2");
    private static final String MOMOI_ENERGY_ORB = ModHelper.makeImgPath("1024", "cost_orb");
    private static final String MIDORI_ENERGY_ORB = ModHelper.makeImgPath("1024", "cost_orb_2");

    public BATwinsMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(Enums.BATWINS_MOMOI_CARD, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATWINS_MOMOI_ATTACK_512, BATWINS_MOMOI_SKILL_512, BATWINS_MOMOI_POWER_512, MOMOI_BIG_ORB, BATWINS_MOMOI_ATTACK_1024, BATWINS_MOMOI_SKILL_1024, BATWINS_MOMOI_POWER_1024, MOMOI_ENERGY_ORB, MOMOI_SMALL_ORB);
        BaseMod.addColor(Enums.BATWINS_MIDORI_CARD, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATWINS_MIDORI_ATTACK_512, BATWINS_MIDORI_SKILL_512, BATWINS_MIDORI_POWER_512, MIDORI_BIG_ORB, BATWINS_MIDORI_ATTACK_1024, BATWINS_MIDORI_SKILL_1024, BATWINS_MIDORI_POWER_1024, MIDORI_ENERGY_ORB, MIDORI_SMALL_ORB);

    }

    public static void initialize() {
        new BATwinsMod();
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new BATwinsMomoiStrick());
        BaseMod.addCard(new BATwinsMidoriStrick());
        BaseMod.addCard(new BATwinsMomoiDefend());
        BaseMod.addCard(new BATwinsMidoriDefend());
        BaseMod.addCard(new BATwinsAlreadyAngry());
        BaseMod.addCard(new BATwinsPaintingConception());
        BaseMod.addCard(new BATwinsParoxysmalPain());
        BaseMod.addCard(new BATwinsPaintingArt());
        BaseMod.addCard(new BATwinsDeveloperCollaboration());
        BaseMod.addCard(new BATwinsExchange());
        BaseMod.addCard(new BATwinsKeepItToTheEnd());
        BaseMod.addCard(new BATwinsArtPolishing());
        BaseMod.addCard(new BATwinsBattleCommand());
        BaseMod.addCard(new BATwinsIgniteTheUpperBody());
        BaseMod.addCard(new BATwinsNotReconciled());
        BaseMod.addCard(new BATwinsAdditionalAttacks());
        BaseMod.addCard(new BATwinsAccurateBlocking());
        BaseMod.addCard(new BATwinsGameLaunch());
        BaseMod.addCard(new BATwinsInspirationEmergence());
        BaseMod.addCard(new BATwinsEnchantedBullet());
        BaseMod.addCard(new BATwinsPassageInForce());
        BaseMod.addCard(new BATwinsSiteAdaptation());
        BaseMod.addCard(new BATwinsOnceMore());
    }

    @Override
    public void receiveEditStrings() {
        String lang;
        if (language == GameLanguage.ZHS) {
            lang = "ZHS";
        } else {
            lang = "ENG";
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "baModResources/localization/" + lang + "/cards.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "baModResources/localization/" + lang + "/character.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "baModResources/localization/" + lang + "/power.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "baModResources/localization/" + lang + "/uistring.json");
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new BATwinsCharacter(CardCrawlGame.playerName), BATWINS_CHARACTE_BUTTON, BATWINS_CHARACTER_PORTRAIT, Enums.BATwins);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "ENG";
        if (language == GameLanguage.ZHS) {
            lang = "ZHS";
        }
        String json = Gdx.files.internal("baModResources/localization/" + lang + "/keyword.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword("batwinsmod", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
}
