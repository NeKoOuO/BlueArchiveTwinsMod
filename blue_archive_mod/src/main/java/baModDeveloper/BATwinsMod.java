package baModDeveloper;

import baModDeveloper.cards.*;
import baModDeveloper.cards.bullets.*;
import baModDeveloper.cards.colorless.BATwinsAccelerate;
import baModDeveloper.cards.colorless.BATwinsExcitation;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.character.BATwinsCharacter.Enums;
import baModDeveloper.event.*;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.SaveHelper;
import baModDeveloper.localization.SoraItemStrings;
import baModDeveloper.patch.BATwinsAbstractMonsterPatch;
import baModDeveloper.patch.BATwinsCustomModeScreenPatch;
import baModDeveloper.potion.BATwinsAcceleratePotion;
import baModDeveloper.potion.BATwinsBurnPotion;
import baModDeveloper.potion.BATwinsConnectPotion;
import baModDeveloper.potion.BATwinsStaminaPotion;
import baModDeveloper.relic.*;
import baModDeveloper.ui.panels.icons.BATwinsMidoriEnergyOrbSmall;
import baModDeveloper.ui.panels.icons.BATwinsMomoiEnergyOrbSmall;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.helpers.ScreenPostProcessorManager;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.Settings.GameLanguage;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;

import static com.megacrit.cardcrawl.core.Settings.language;

@SpireInitializer
public class BATwinsMod implements EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber,
        EditKeywordsSubscriber, EditRelicsSubscriber, AddAudioSubscriber, PostInitializeSubscriber, ScreenPostProcessor,
        PostCreateStartingDeckSubscriber, PostBattleSubscriber,PostDungeonInitializeSubscriber {

    public static final Color BATwinsColor = new Color(254.0F / 255.0F, 168.0F / 255.0F, 198.0F / 255.0F, 1.0F);
    public static final Color MOMOIColor = new Color(254.0F / 255.0F, 168.0F / 255.0F, 198.0F / 255.0F, 1.0F);
    public static final Color MIDORIColor = new Color(85.0F / 255.0F, 171.0F / 255.0F, 72.0F / 255.0F, 1.0F);
    //shader绘制
    public static final List<BiConsumer<SpriteBatch, TextureRegion>> postProcessQueue = new ArrayList<>();
    private static final String BATWINS_CHARACTER_BUTTON = ModHelper.makeImgPath("char", "Character_Button");
    private static final String BATWINS_CHARACTER_PORTRAIT = ModHelper.makeImgPath("char", "Character_Portrait");
    private static final String BATWINS_MOMOI_ATTACK_512 = ModHelper.makeImgPath("512", "bg_attack_512");
    private static final String BATWINS_MOMOI_POWER_512 = ModHelper.makeImgPath("512", "bg_power_512");
    private static final String BATWINS_MOMOI_SKILL_512 = ModHelper.makeImgPath("512", "bg_skill_512");
    private static final String BATWINS_MIDORI_ATTACK_512 = ModHelper.makeImgPath("512", "bg_attack_512_2");
    private static final String BATWINS_MIDORI_POWER_512 = ModHelper.makeImgPath("512", "bg_power_512_2");
    private static final String BATWINS_MIDORI_SKILL_512 = ModHelper.makeImgPath("512", "bg_skill_512_2");
    private static final String MOMOI_SMALL_ORB = ModHelper.makeImgPath("512", "small_orb_double");
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
    //模组选项
    public static boolean AutoSort = true;
    public static boolean ShowExpBar = true;
    public static boolean Enable3D = false;
    public static int SelectedSkin = 0;
    public static boolean Tutorial = true;
    public static boolean EnableModelLighting = false;

    public static SaveHelper saveHelper;


    public BATwinsMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(Enums.BATWINS_MOMOI_CARD, MOMOIColor, MOMOIColor, MOMOIColor, MOMOIColor, MOMOIColor, MOMOIColor, MOMOIColor, BATWINS_MOMOI_ATTACK_512, BATWINS_MOMOI_SKILL_512, BATWINS_MOMOI_POWER_512, MOMOI_BIG_ORB, BATWINS_MOMOI_ATTACK_1024, BATWINS_MOMOI_SKILL_1024, BATWINS_MOMOI_POWER_1024, MOMOI_ENERGY_ORB, MOMOI_SMALL_ORB);
        BaseMod.addColor(Enums.BATWINS_MIDORI_CARD, MIDORIColor, MIDORIColor, MIDORIColor, MIDORIColor, MIDORIColor, MIDORIColor, MIDORIColor, BATWINS_MIDORI_ATTACK_512, BATWINS_MIDORI_SKILL_512, BATWINS_MIDORI_POWER_512, MIDORI_BIG_ORB, BATWINS_MIDORI_ATTACK_1024, BATWINS_MIDORI_SKILL_1024, BATWINS_MIDORI_POWER_1024, MIDORI_ENERGY_ORB, MIDORI_SMALL_ORB);

        ScreenPostProcessorManager.addPostProcessor(this);

        saveHelper=new SaveHelper();
    }

    public static void initialize() {
        new BATwinsMod();
        try {
            Properties defaults = new Properties();
            defaults.setProperty(ModHelper.makePath("AutoSort"), "true");
            defaults.setProperty(ModHelper.makePath("ShowExpBar"), "true");
            SpireConfig config = new SpireConfig(ModHelper.getModID(), "Common", defaults);
            AutoSort = config.getBool(ModHelper.makePath("AutoSort"));
            ShowExpBar = config.getBool(ModHelper.makePath("ShowExpBar"));
            Enable3D = config.getBool(ModHelper.makePath("Enable3D"));
            Tutorial = config.getBool(ModHelper.makePath("Tutorial"));
            SelectedSkin = config.getInt(ModHelper.makePath("SelectedSkin"));
            EnableModelLighting = config.getBool(ModHelper.makePath("EnableModelLighting"));

//            Settings.isDebug=true;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
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
//        BaseMod.addCard(new BATwinsIgniteTheUpperBody());
        BaseMod.addCard(new BATwinsNotReconciled());
        BaseMod.addCard(new BATwinsAdditionalAttacks());
        BaseMod.addCard(new BATwinsAccurateBlocking());
        BaseMod.addCard(new BATwinsGameLaunch());
        BaseMod.addCard(new BATwinsInspirationEmergence());
        BaseMod.addCard(new BATwinsEnchantedBullet());
//        BaseMod.addCard(new BATwinsPassageInForce());
//        BaseMod.addCard(new BATwinsSiteAdaptation());
        BaseMod.addCard(new BATwinsOnceMore());
//        BaseMod.addCard(new BATwinsBadDesigner());
//        BaseMod.addCard(new BATwinsExcellentDesigner());
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
//        BaseMod.addCard(new BATwinsEmergencyRecovery());
        BaseMod.addCard(new BATwinsAbstractSchool());
//        BaseMod.addCard(new BATwinsDefensiveCounterattack());
//        BaseMod.addCard(new BATwinsAdventureRewards());
//        BaseMod.addCard(new BATwinsAccumulatedStrike());
//        BaseMod.addCard(new BATwinsNormalAttackMethods());
        BaseMod.addCard(new BATwinsAlternatingAttack());
        BaseMod.addCard(new BATwinsTemporaryAssistance());
//        BaseMod.addCard(new BATwinsAdventureExperience());
        BaseMod.addCard(new BATwinsSwitchStrike());
        BaseMod.addCard(new BATwinsCoverCharge());
        BaseMod.addCard(new BATwinsMutualUnderstanding());
        BaseMod.addCard(new BATwinsMysteriousChest());
        BaseMod.addCard(new BATwinsSeeYouHaveASharen());
        BaseMod.addCard(new BATwinsCheatingCodeEnabled());
        BaseMod.addCard(new BATwinsFundOverdraft());
        BaseMod.addCard(new BATwinsTakeActionsSeparately());
        BaseMod.addCard(new BATwinsRepeatOperation());
        BaseMod.addCard(new BATwinsConvenientConnectivity());
        BaseMod.addCard(new BATwinsEndCombo());
        BaseMod.addCard(new BATwinsReadingDocuments());
//        BaseMod.addCard(new BATwinsMandatoryInstruction());
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
        BaseMod.addCard(new BATwinsBorrowMe());
        BaseMod.addCard(new BATwinsMaidForm());
        BaseMod.addCard(new BATwinsShiftingAndGhosting());
        BaseMod.addCard(new BATwinsLeaveItToMe());
        BaseMod.addCard(new BATwinsAssault());
        BaseMod.addCard(new BATwinsDontSayIt());
        BaseMod.addCard(new BATwinsItsSoPainful());
        BaseMod.addCard(new BATwinsCollaboration());
        BaseMod.addCard(new BATwinsPenetrationDamage());
        BaseMod.addCard(new BATwinsCheckTheStrategy());
//        BaseMod.addCard(new BATwinsLoginRewards());
        BaseMod.addCard(new BATwinsLearned());
        BaseMod.addCard(new BATwinsForceDetonation());
        BaseMod.addCard(new BATwinsScriptRewriting());
//        BaseMod.addCard(new BATwinsBullet());
        BaseMod.addCard(new BATwinsContinuousShooting());
        BaseMod.addCard(new BATwinsIncendiaryBullet());
        BaseMod.addCard(new BATwinsPoisonBullet());
        BaseMod.addCard(new BATwinsSniperBullet());
        BaseMod.addCard(new BATwinsArmorPiercingBullet());
        BaseMod.addCard(new BATwinsBreechLoading());
        BaseMod.addCard(new BATwinsStableShooting());
        BaseMod.addCard(new BATwinsRandomShooting());
        BaseMod.addCard(new BATwinsFocusShooting());
        BaseMod.addCard(new BATwinsHeavyBullets());
        BaseMod.addCard(new BATwinsBulletWarehouse());
//        BaseMod.addCard(new BATwinsExpansionMagazine());
        BaseMod.addCard(new BATwinsAccelerate());
        BaseMod.addCard(new BATwinsExcitation());
        BaseMod.addCard(new BATwinsTacticalRelay());
//        BaseMod.addCard(new BATwinsGorgeous());
        BaseMod.addCard(new BATwinsUniversalBullet());
        BaseMod.addCard(new BATwinsMultipleShadows());
    }

    @Override
    public void receiveEditStrings() {
        String lang = "ENG";
        if (language == GameLanguage.ZHS) {
            lang = "ZHS";
//        } else if(language==GameLanguage.ENG){
//            lang = "ENG";
        } else if (language == GameLanguage.ZHT) {
            lang = "ZHT";
        } else if (language == GameLanguage.JPN) {
            lang = "JPN";
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "baModResources/localization/" + lang + "/cards.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "baModResources/localization/" + lang + "/character.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "baModResources/localization/" + lang + "/power.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "baModResources/localization/" + lang + "/uistring.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "baModResources/localization/" + lang + "/relic.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, "baModResources/localization/" + lang + "/event.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, "baModResources/localization/" + lang + "/potion.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class, "baModResources/localization/" + lang + "/monster.json");
        BaseMod.loadCustomStringsFile(RunModStrings.class, "baModResources/localization/" + lang + "/runmod.json");

    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new BATwinsCharacter(CardCrawlGame.playerName), BATWINS_CHARACTER_BUTTON, BATWINS_CHARACTER_PORTRAIT, Enums.BATwins);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "ENG";
        if (language == GameLanguage.ZHS) {
            lang = "ZHS";
        } else if (language == GameLanguage.ZHT) {
            lang = "ZHT";
        } else if (language == GameLanguage.JPN) {
            lang = "JPN";
        }
        String json = Gdx.files.internal("baModResources/localization/" + lang + "/keyword.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword("batwinsmod", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
        this.initSoraItemString(lang);

    }

    private void initSoraItemString(String lang) {
        //添加小空商店道具
        String json = Gdx.files.internal("baModResources/localization/" + lang + "/soraItems.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Gson gson = new Gson();
        Type soraItemMapType = new TypeToken<Map<String, SoraItemStrings>>() {
        }.getType();
        ModHelper.soraItemStringsMap = gson.fromJson(json, soraItemMapType);

        ModHelper.getLogger().info(ModHelper.soraItemStringsMap);

    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new BATwinsMomoisGameConsole(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelicToCustomPool(new BATwinsMidorisGameConsole(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelic(new BATwinsAncientGameCartridges(), RelicType.SHARED);
        BaseMod.addRelic(new BATwinsByProving(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new BATwinsGameMagazine(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelicToCustomPool(new BATwinsGameGuide(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelic(new BATwinsFoldingShield(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new BATwinsHeadband(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelicToCustomPool(new BATwinsSpecialAmmunition(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelicToCustomPool(new BATwinsSwordOfLight(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelicToCustomPool(new BATwinsNekoHowitzer(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelic(new BATwinsFitnessRing(), RelicType.SHARED);
        BaseMod.addRelic(new BATwinsBroom(), RelicType.SHARED);
        BaseMod.addRelic(new BATwinsMaidAttire(), RelicType.SHARED);
//        BaseMod.addRelicToCustomPool(new BATwinsRubiksCube(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelicToCustomPool(new BATwinsRetroGame(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelic(new BATwinsBookOfProhibitions(), RelicType.SHARED);
        BaseMod.addRelic(new BATwinsLearningMaterials(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new BATwinsAdaptability(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelic(new BATwinsFlowers(), RelicType.SHARED);
        BaseMod.addRelic(new BATwinsOldTv(), RelicType.SHARED);
        BaseMod.addRelic(new BATwinsRankIcon(), RelicType.SHARED);
        BaseMod.addRelic(new BATwinsFullScoreAnswer(), RelicType.SHARED);
        BaseMod.addRelic(new BATwinsCrystalHaniwa(),RelicType.SHARED);
        BaseMod.addRelic(new BATwinsPackage(),RelicType.SHARED);
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(ModHelper.makePath("campfire_momoi"), ModHelper.makeAudioPath("campfire_momoi"));
        BaseMod.addAudio(ModHelper.makePath("campfire_midori"), ModHelper.makeAudioPath("campfire_midori"));
        BaseMod.addAudio(ModHelper.makePath("eateregg1"), ModHelper.makeAudioPath("eateregg1"));
        BaseMod.addAudio(ModHelper.makePath("eateregg2"), ModHelper.makeAudioPath("eateregg2"));
        BaseMod.addAudio(ModHelper.makePath("charSelect_momoi"), ModHelper.makeAudioPath("charSelect_momoi"));
        BaseMod.addAudio(ModHelper.makePath("charSelect_midori"), ModHelper.makeAudioPath("charSelect_midori"));
        BaseMod.addAudio(ModHelper.makePath("pixelTime"), ModHelper.makeAudioPath("PixelTime"));
        BaseMod.addAudio(ModHelper.makePath("coin"), ModHelper.makeAudioPath("coin"));
        BaseMod.addAudio(ModHelper.makePath("Momoi_Ex"), ModHelper.makeAudioPath("Momoi_Ex"));
        BaseMod.addAudio(ModHelper.makePath("Midori_Ex"), ModHelper.makeAudioPath("Midori_Ex"));
        BaseMod.addAudio(ModHelper.makePath("Alice"), ModHelper.makeAudioPath("alice"));
        BaseMod.addAudio(ModHelper.makePath("soraShop"), ModHelper.makeAudioPath("soraShop"));
        BaseMod.addAudio(ModHelper.makePath("soraEnterShop"), ModHelper.makeAudioPath("soraEnterShop"));
        BaseMod.addAudio(ModHelper.makePath("soraMsg1"), ModHelper.makeAudioPath("soraMsg1"));
        BaseMod.addAudio(ModHelper.makePath("soraMsg2"), ModHelper.makeAudioPath("soraMsg2"));
        BaseMod.addAudio(ModHelper.makePath("soraMsg3"), ModHelper.makeAudioPath("soraMsg3"));

    }

    @Override
    public void receivePostInitialize() {
        try {
            CreateConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //添加事件
        BaseMod.addEvent(BATwinsTrainingCamp.ID, BATwinsTrainingCamp.class);
        BaseMod.addEvent(new AddEventParams.Builder(BATwinsDirtShowdown.ID, BATwinsDirtShowdown.class).playerClass(Enums.BATwins).create());
        BaseMod.addEvent(BATwinsHurdleGame.ID, BATwinsHurdleGame.class);
        BaseMod.addEvent(new AddEventParams.Builder(BATwinsCommunication.ID, BATwinsCommunication.class).playerClass(Enums.BATwins).dungeonID(Exordium.ID).create());
        BaseMod.addEvent(new AddEventParams.Builder(BATwinsTheRoadIsLong.ID, BATwinsTheRoadIsLong.class).playerClass(Enums.BATwins).dungeonID(TheBeyond.ID).create());
        BaseMod.addEvent(BATwinsSoraShop.ID, BATwinsSoraShop.class);
        BaseMod.addEvent(new AddEventParams.Builder(BATwinsTransportationTask.ID,BATwinsTransportationTask.class).spawnCondition(()->{
            return AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(BATwinsPackage.ID);
        }).create());
        //添加药水
        BaseMod.addPotion(BATwinsAcceleratePotion.class, BATwinsAcceleratePotion.liquidColor, BATwinsAcceleratePotion.hybridColor, BATwinsAcceleratePotion.spotsColor, BATwinsAcceleratePotion.ID);
        BaseMod.addPotion(BATwinsConnectPotion.class, BATwinsConnectPotion.liquidColor, BATwinsConnectPotion.hybridColor, BATwinsConnectPotion.spotsColor, BATwinsConnectPotion.ID);
        BaseMod.addPotion(BATwinsBurnPotion.class, BATwinsBurnPotion.liquidColor, BATwinsBurnPotion.hybridColor, BATwinsBurnPotion.spotsColor, BATwinsBurnPotion.ID);
        BaseMod.addPotion(BATwinsStaminaPotion.class, BATwinsStaminaPotion.liquidColor, BATwinsStaminaPotion.hybridColor, BATwinsStaminaPotion.spotsColor, BATwinsStaminaPotion.ID);
        //添加怪物
//        BaseMod.addMonster(BATwinsAkane.ID, BATwinsAkane::new);
//        BaseMod.addStrongMonsterEncounter(Exordium.ID,new MonsterInfo(BATwinsAkane.ID,10));
//        BaseMod.addEliteEncounter(Exordium.ID,new MonsterInfo(BATwinsAkane.ID,10));
        BaseMod.addSaveField("BATwinsRunMods", new BATwinsCustomModeScreenPatch());


    }

    private void CreateConfig() throws IOException {
        SpireConfig spireConfig = new SpireConfig("BATwinsMod", "Common");
        ModPanel settingPanel = new ModPanel();
        ModLabeledToggleButton autoSort = new ModLabeledToggleButton("AutoSort", 500.0F, 600.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, AutoSort, settingPanel, modLabel -> {

        }, modToggleButton -> {
            spireConfig.setBool(ModHelper.makePath("AutoSort"), AutoSort = modToggleButton.enabled);
            CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();
            try {
                spireConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        settingPanel.addUIElement(autoSort);

        ModLabeledToggleButton showExpBar = new ModLabeledToggleButton("ShowExpBar", 500.0F, 500.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, ShowExpBar, settingPanel, modLabel -> {

        }, modToggleButton -> {
            spireConfig.setBool(ModHelper.makePath("ShowExpBar"), ShowExpBar = modToggleButton.enabled);
            CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();
            try {
                spireConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        settingPanel.addUIElement(showExpBar);

        ModLabeledToggleButton enable3D = new ModLabeledToggleButton("Enable3D", 500.0F, 400.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, Enable3D, settingPanel, modLabel -> {

        }, modToggleButton -> {
            spireConfig.setBool(ModHelper.makePath("Enable3D"), Enable3D = modToggleButton.enabled);
            CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();
            try {
                spireConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        settingPanel.addUIElement(enable3D);

        ModLabeledToggleButton enableModelLighting = new ModLabeledToggleButton("EnableModelLighting(Need restart game!)", 500.0F, 300.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, EnableModelLighting, settingPanel, modLabel -> {

        }, modToggleButton -> {
            spireConfig.setBool(ModHelper.makePath("EnableModelLighting"), EnableModelLighting = modToggleButton.enabled);
            CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();
            try {
                spireConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        settingPanel.addUIElement(enableModelLighting);

        Texture badgeTexture = ImageMaster.loadImage(ModHelper.makeImgPath("UI", "configButton"));
        BaseMod.registerModBadge(badgeTexture, "BATwinsMod", "0v0", "config", settingPanel);
    }

    @Override
    public void postProcess(SpriteBatch spriteBatch, TextureRegion textureRegion, OrthographicCamera orthographicCamera) {
        if (!postProcessQueue.isEmpty()) {
            for (BiConsumer<SpriteBatch, TextureRegion> consumer : postProcessQueue) {
                consumer.accept(spriteBatch, textureRegion);
            }
            postProcessQueue.clear();
        } else {
            spriteBatch.draw(textureRegion, 0.0F, 0.0F);
        }
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        BATwinsAbstractMonsterPatch.takeTime();

    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        BATwinsSelfConnectivity.EasterEggCard = AbstractDungeon.returnRandomCard();
    }

    @Override
    public void receivePostCreateStartingDeck(AbstractPlayer.PlayerClass playerClass, CardGroup cardGroup) {
        if (playerClass == Enums.BATwins && Settings.isTrial) {
            if (BATwinsCustomModeScreenPatch.NoMomoiCardModEnable) {
                for (AbstractCard card : cardGroup.group) {
                    if (card instanceof BATwinsModCustomCard && card.color == Enums.BATWINS_MOMOI_CARD) {
                        ((BATwinsModCustomCard) card).conversionColor(false);
                    }
                }
            } else if (BATwinsCustomModeScreenPatch.NoMidoriCardModEnable) {
                for (AbstractCard card : cardGroup.group) {
                    if (card instanceof BATwinsModCustomCard && card.color == Enums.BATWINS_MIDORI_CARD) {
                        ((BATwinsModCustomCard) card).conversionColor(false);
                    }
                }
            }
        }


    }

    @Override
    public void receivePostDungeonInitialize() {
        saveHelper.values=new SaveHelper.SaveValue();
    }
}
