package baModDeveloper.patch;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

public class MonsterRoomElitePatch {
    @SpirePatch(clz = MonsterRoomElite.class,method = "applyEmeraldEliteBuff")
    public static class applyEmeraldEliteBuffPatch{
        @SpirePostfixPatch
        public static void postfixPatch(MonsterRoomElite _instance){
            if(ModHelper.BIRTH_DAY&&AbstractDungeon.player.chosenClass== BATwinsCharacter.Enums.BATwins){
                if (Settings.isFinalActAvailable && AbstractDungeon.getCurrMapNode().hasEmeraldKey) {
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        {
                            this.duration = 2.0F;
                        }

                        @Override
                        public void update() {
                            if (this.duration == 2.0F) {
                                AbstractDungeon.actionManager.addToBottom(new TalkAction(
                                        AbstractDungeon.getCurrRoom().monsters.monsters.get(0),
                                        CardCrawlGame.languagePack.getUIString(ModHelper.makePath("EliteBirthDay")).TEXT[0]
                                ));
                            }
                            if (this.duration <= 0.0F) {
                                AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.GREEN));
                                for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                                    AbstractDungeon.actionManager.addToBottom(new EscapeAction(monster));
                                }
                                this.isDone=true;
                            }
                            this.duration -= Gdx.graphics.getDeltaTime();


                        }
                    });
                }
            }

        }
    }
}
