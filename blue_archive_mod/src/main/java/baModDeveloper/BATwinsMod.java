package baModDeveloper;

import baModDeveloper.cards.*;
import baModDeveloper.event.BATwinsTrainingCamp;
import baModDeveloper.power.BATwinsSeeYouHaveASharenPower;
import baModDeveloper.relic.BATwinsAncientGameCartridges;
import baModDeveloper.relic.BATwinsByProving;
import baModDeveloper.relic.BATwinsMidorisGameConsole;
import baModDeveloper.relic.BATwinsMomoisGameConsole;
import baModDeveloper.ui.panels.button.BATwinsCamfireExchangeButton;
import baModDeveloper.ui.panels.icons.BATwinsMidoriEnergyOrbSmall;
import baModDeveloper.ui.panels.icons.BATwinsMomoiEnergyOrbSmall;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
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
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.nio.charset.StandardCharsets;

import static com.megacrit.cardcrawl.core.Settings.language;

@SpireInitializer
public class BATwinsMod implements EditCardsSubscriber,EditStringsSubscriber,EditCharactersSubscriber, EditKeywordsSubscriber , EditRelicsSubscriber ,AddAudioSubscriber,PostInitializeSubscriber{

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
        BaseMod.addColor(Enums.BATWINS_MOMOI_CARD, MOMOIColor, MOMOIColor, MOMOIColor, MOMOIColor, MOMOIColor, MOMOIColor, MOMOIColor, BATWINS_MOMOI_ATTACK_512, BATWINS_MOMOI_SKILL_512, BATWINS_MOMOI_POWER_512, MOMOI_BIG_ORB, BATWINS_MOMOI_ATTACK_1024, BATWINS_MOMOI_SKILL_1024, BATWINS_MOMOI_POWER_1024, MOMOI_ENERGY_ORB, MOMOI_SMALL_ORB);
        BaseMod.addColor(Enums.BATWINS_MIDORI_CARD, MIDORIColor, MIDORIColor, MIDORIColor, MIDORIColor, MIDORIColor, MIDORIColor, MIDORIColor, BATWINS_MIDORI_ATTACK_512, BATWINS_MIDORI_SKILL_512, BATWINS_MIDORI_POWER_512, MIDORI_BIG_ORB, BATWINS_MIDORI_ATTACK_1024, BATWINS_MIDORI_SKILL_1024, BATWINS_MIDORI_POWER_1024, MIDORI_ENERGY_ORB, MIDORI_SMALL_ORB);
    }

    public static void initialize() {
        new BATwinsMod();
    }

    @Override
    public void receiveEditCards() {
        CustomIconHelper.addCustomIcon(BATwinsMomoiEnergyOrbSmall.get());
        CustomIconHelper.addCustomIcon(BATwinsMidoriEnergyOrbSmall.get());

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
        BaseMod.addCard(new BATwinsBadDesigner());
        BaseMod.addCard(new BATwinsExcellentDesigner());
//        BaseMod.addCard(new BATwinsBugCard());
//        BaseMod.addCard(new BATwinsPlotRepair());
//        BaseMod.addCard(new BATwinsRushToDraft());
//        BaseMod.addCard(new BATwinsPlotRepair());
//        BaseMod.addCard(new BATwinsBubFix());
//        BaseMod.addCard(new BATwinsAdventureOpening());
//        BaseMod.addCard(new BATwinsAdventureBattle());
        BaseMod.addCard(new BATwinsLightSpeedStrike());
        BaseMod.addCard(new BATwinsSinglePlayerGame());
        BaseMod.addCard(new BATwinsCoolingTime());
        BaseMod.addCard(new BATwinsDoubleExperience());
        BaseMod.addCard(new BATwinsBDStudy());
        BaseMod.addCard(new BATwinsEmergencyRecovery());
        BaseMod.addCard(new BATwinsAbstractSchool());
        BaseMod.addCard(new BATwinsDefensiveCounterattack());
//        BaseMod.addCard(new BATwinsAdventureRewards());
        BaseMod.addCard(new BATwinsAccumulatedStrike());
        BaseMod.addCard(new BATwinsNormalAttackMethods());
        BaseMod.addCard(new BATwinsAlternatingAttack());
        BaseMod.addCard(new BATwinsTemporaryAssistance());
//        BaseMod.addCard(new BATwinsAdventureExperience());
        BaseMod.addCard(new BATwinsSwitchStrike());
        BaseMod.addCard(new BATwinsCoverCharge());
        BaseMod.addCard(new BATwinsMutualUnderstanding());
        BaseMod.addCard(new BATwinsMysteriousChest());
        BaseMod.addCard(new BATwisSeeYouHaveASharen());
        BaseMod.addCard(new BATwinsCheatingCodeEnabled());
        BaseMod.addCard(new BATwinsFundOverdraft());
        BaseMod.addCard(new BATwinsTakeActionsSeparately());
        BaseMod.addCard(new BATwinsRepeatOperation());
        BaseMod.addCard(new BATwinsConvenientConnectivity());
        BaseMod.addCard(new BATwinsEndCombo());
        BaseMod.addCard(new BATwinsReadingDocuments());
        BaseMod.addCard(new BATwinsMandatoryInstruction());
        BaseMod.addCard(new BATwinsTwoStageAttack());
        BaseMod.addCard(new BATwinsTakeABreak());
        BaseMod.addCard(new BATwinsAttackWithAllMight());
        BaseMod.addCard(new BATwinsDualProtection());
        BaseMod.addCard(new BATwinsOperateFreely());
        BaseMod.addCard(new BATwinsExperienceGiftPackage());
        BaseMod.addCard(new BATwinsSelfConnectivity());
        BaseMod.addCard(new BATwinsSkillCombination());
        BaseMod.addCard(new BATwinsIntegratingAndIntegrating());
//        BaseMod.addCard(new BATwinsPlotPrediction());
        BaseMod.addCard(new BATwinsMasterCraftsmanship());
        BaseMod.addCard(new BATwinsPoisonGasBomb());
        BaseMod.addCard(new BATwinsAdventureBegins());
        BaseMod.addCard(new BATwinsPropCollection());
        BaseMod.addCard(new BATwinsEquipmentUpgrade());
        BaseMod.addCard(new BATwinsAutomaticDefense());
        BaseMod.addCard(new BATwinsBenefitReducingMagic());
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
        BaseMod.loadCustomStringsFile(RelicStrings.class,"baModResources/localization/"+lang+"/relic.json");
        BaseMod.loadCustomStringsFile(EventStrings.class,"baModResources/localization/"+lang+"/event.json");
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

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new BATwinsMomoisGameConsole(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelicToCustomPool(new BATwinsMidorisGameConsole(),Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelic(new BATwinsAncientGameCartridges(),RelicType.SHARED);
        BaseMod.addRelic(new BATwinsByProving(),RelicType.SHARED);
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(ModHelper.makePath("campfire_momoi"),ModHelper.makeAudioPath("campfire_momoi"));
        BaseMod.addAudio(ModHelper.makePath("campfire_midori"),ModHelper.makeAudioPath("campfire_midori"));
    }

    @Override
    public void receivePostInitialize() {
        BaseMod.addEvent(BATwinsTrainingCamp.ID,BATwinsTrainingCamp.class);
    }
}
