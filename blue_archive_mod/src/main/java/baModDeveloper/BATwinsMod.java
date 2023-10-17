package baModDeveloper;

import baModDeveloper.cards.BATwinsMidoriStrick;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.Settings.GameLanguage;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;

import baModDeveloper.cards.BATwinsMomoiStrick;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.character.BATwinsCharacter.Enums;
import baModDeveloper.helpers.ModHelper;
import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditStringsSubscriber;

@SpireInitializer
public class BATwinsMod implements EditCardsSubscriber,EditStringsSubscriber,EditCharactersSubscriber{

    public static final Color BATwinsColor=new Color(254.0F / 255.0F, 168.0F / 255.0F, 198.0F / 255.0F, 1.0F);
    public static final Color MOMOIColor=new Color(254.0F / 255.0F, 168.0F / 255.0F, 198.0F / 255.0F, 1.0F);
    public static final Color MIDORIColor=new Color(85.0F / 255.0F, 171.0F / 255.0F, 72.0F / 255.0F, 1.0F);
    private static final String BATWINS_CHARACTE_BUTTON=ModHelper.makeImgPath("char","Character_Button");
    private static final String BATWINS_CHARACTER_PORTRAIT=ModHelper.makeImgPath("char", "Character_Portrait");
    private static final String BATWINS_MOMOI_ATTACK_512=ModHelper.makeImgPath("512", "bg_attack_512");
    private static final String BATWINS_MOMOI_POWER_512=ModHelper.makeImgPath("512", "bg_power_512");
    private static final String BATWINS_MOMOI_SKILL_512=ModHelper.makeImgPath("512", "bg_skill_512");
    private static final String BATWINS_MIDORI_ATTACK_512=ModHelper.makeImgPath("512", "bg_attack_512_2");
    private static final String BATWINS_MIDORI_POWER_512=ModHelper.makeImgPath("512", "bg_power_512_2");
    private static final String BATWINS_MIDORI_SKILL_512=ModHelper.makeImgPath("512", "bg_skill_512_2");
    private static final String SMALL_ORB=ModHelper.makeImgPath("char", "small_orb");
    private static final String BATWINS_MOMOI_ATTACK_1024=ModHelper.makeImgPath("1024", "bg_attack_1024");
    private static final String BATWINS_MOMOI_POWER_1024=ModHelper.makeImgPath("1024", "bg_power_1024");
    private static final String BATWINS_MOMOI_SKILL_1024=ModHelper.makeImgPath("1024", "bg_skill_1024");
    private static final String BATWINS_MIDORI_ATTACK_1024=ModHelper.makeImgPath("1024", "bg_attack_1024_2");
    private static final String BATWINS_MIDORI_POWER_1024=ModHelper.makeImgPath("1024", "bg_power_1024_2");
    private static final String BATWINS_MIDORI_SKILL_1024=ModHelper.makeImgPath("1024", "bg_skill_1024_2");
    private static final String BIG_ORB=ModHelper.makeImgPath("char", "card_orb");
    private static final String ENERY_ORB=ModHelper.makeImgPath("char", "cost_orb");
     
    public BATwinsMod(){
        BaseMod.subscribe(this);
        BaseMod.addColor(Enums.BATWINS_MOMOI_CARD, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATWINS_MOMOI_ATTACK_512, BATWINS_MOMOI_SKILL_512, BATWINS_MOMOI_POWER_512,ENERY_ORB, BATWINS_MOMOI_ATTACK_1024, BATWINS_MOMOI_SKILL_1024, BATWINS_MOMOI_POWER_1024, BIG_ORB, SMALL_ORB);
        BaseMod.addColor(Enums.BATWINS_MIDORI_CARD, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATwinsColor, BATWINS_MIDORI_ATTACK_512, BATWINS_MIDORI_SKILL_512, BATWINS_MIDORI_POWER_512,ENERY_ORB, BATWINS_MIDORI_ATTACK_1024, BATWINS_MIDORI_SKILL_1024, BATWINS_MIDORI_POWER_1024, BIG_ORB, SMALL_ORB);

    }

    public static void initialize(){
        new BATwinsMod();
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new BATwinsMomoiStrick());
        BaseMod.addCard(new BATwinsMidoriStrick());
    }

    @Override
    public void receiveEditStrings() {
        String lang;
        if(Settings.language==GameLanguage.ZHS){
            lang="ZHS";
        }else{
            lang="ENG";
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "baModResources/localization/"+lang+"/cards.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "baModResources/localization/"+lang+"/character.json");
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new BATwinsCharacter(CardCrawlGame.playerName),BATWINS_CHARACTE_BUTTON, BATWINS_CHARACTER_PORTRAIT,Enums.BATwins);
    }

}
