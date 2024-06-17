package baModDeveloper.patch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BATwinsAbstractMonsterPatch {
    public static void addPixelMonster(AbstractMonster m) {
        renderPatch.monsterList.put(m, renderPatch.PixelDuration);
    }

    public static void takeTime() {
        List<AbstractMonster> monstersToRemove = new ArrayList<>();
        for (Map.Entry<AbstractMonster, Float> entry : renderPatch.monsterList.entrySet()) {
            AbstractMonster monster = entry.getKey();
            float value = entry.getValue();
            entry.setValue(value - Gdx.graphics.getDeltaTime());
            if (value < 0) {
                monstersToRemove.add(monster);
            }
        }

        for (AbstractMonster monster : monstersToRemove) {
            renderPatch.monsterList.remove(monster);
        }
    }

    @SpirePatch(clz = AbstractMonster.class, method = "render")
    public static class renderPatch {
        private static ShaderProgram shaderProgram;
        private static FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
        private static Color renderColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        private static Map<AbstractMonster, Float> monsterList = new HashMap<>();
        private static float PixelDuration = 3.0F;

        static {
            shaderProgram = new ShaderProgram(Gdx.files.internal("baModResources/shader/pixel/vertex.glsl"), Gdx.files.internal("baModResources/shader/pixel/fragment.glsl"));
            if (!shaderProgram.isCompiled()) {
                throw new RuntimeException(shaderProgram.getLog());
            }
        }

        @SpirePrefixPatch
        public static void prefixPatch(AbstractMonster __instance, SpriteBatch sb) {
            if (monsterList.containsKey(__instance)) {
                Color c = sb.getColor();
                sb.end();
                frameBuffer.begin();
                Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                Gdx.gl.glClear(16640);
                sb.begin();
                sb.setColor(c);
            }

        }

        @SpirePostfixPatch
        public static void postfixPatch(AbstractMonster __instance, SpriteBatch sb) {
            if (monsterList.containsKey(__instance)) {
                sb.end();
                frameBuffer.end();

                Texture renderImage = frameBuffer.getColorBufferTexture();
//            renderImage.bind(0);
                sb.begin();
                sb.setShader(shaderProgram);
                shaderProgram.setUniformf("iResolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                if (monsterList.get(__instance) > 1.0F) {
                    shaderProgram.setUniformf("pw", 8.0F);
                } else {
                    shaderProgram.setUniformf("pw", 1.0F + 7.0F * monsterList.get(__instance));
                }
                sb.setColor(renderColor);

                sb.draw(renderImage, 0.0F, 0.0F, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT, 1.0F, 1.0F, 0.0F, 0, 0, Settings.WIDTH, Settings.HEIGHT, false, true);
                sb.end();
                sb.setShader(null);
                sb.flush();
                sb.begin();
            }

        }

    }
}
