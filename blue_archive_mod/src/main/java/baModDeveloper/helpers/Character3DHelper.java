package baModDeveloper.helpers;

import baModDeveloper.BATwinsMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import org.lwjgl.opengl.EXTFramebufferObject;

import java.util.*;
import java.util.function.Consumer;

import static baModDeveloper.character.BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
import static baModDeveloper.character.BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
import static baModDeveloper.helpers.Character3DHelper.AnimationName.*;

public class Character3DHelper {
    protected static Map<AnimationName, String[]> AnimationNames = new HashMap<>();
    public static int ExpandScale=2;

    public float current_x = 0, current_y = 0;
    OrthographicCamera camera;
    FrameBuffer frameBuffer;
    TextureRegion region;
    Environment environment;
    PolygonSpriteBatch psb;
    ModelController momoiController;
    ModelController midoriController;
    private boolean inited = false;
    private List<Countdown> controlCountdown;

    public Character3DHelper() {
        if (BATwinsMod.Enable3D)
            init();
    }

    public static String getAnimationString(AnimationName name, AbstractCard.CardColor color) {
        switch (BATwinsMod.SelectedSkin) {
            case 0:
                AnimationNames = CharacterNormalHelper.AnimationNames;
                break;
            case 1:
                AnimationNames = CharacterMaidHelper.AnimationNames;
                break;
        }
        if (color == BATWINS_MOMOI_CARD)
            return AnimationNames.get(name)[0];
        else
            return AnimationNames.get(name)[1];
    }

    public void init() {
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth()*ExpandScale, Gdx.graphics.getHeight()*ExpandScale);
        this.camera.position.set(0, 0, -120);
        this.camera.near = 1.0F;
        this.camera.far = 1000.0F;
        this.camera.rotate(new Vector3(0.0F, 1.0F, 0.0F), 90.0F);
        camera.update();


        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth()*ExpandScale, Gdx.graphics.getHeight()*ExpandScale, true);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0F, 1.0F, 1.0F, 1.0F));

        psb = new PolygonSpriteBatch();
        this.resetCharacterPosition();

        this.controlCountdown = new ArrayList<>();

        this.inited = true;
    }

    public void update() {

        momoiController.update();
        midoriController.update();

        if (!this.controlCountdown.isEmpty()) {
            Iterator<Countdown> iterator = this.controlCountdown.iterator();
            while (iterator.hasNext()) {
                Countdown pair = iterator.next();
                pair.time -= Gdx.graphics.getDeltaTime();
                if (pair.time <= 0.0F) {
                    if (pair.color == BATWINS_MOMOI_CARD)
                        pair.consumer.accept(this.momoiController);
                    else
                        pair.consumer.accept(this.midoriController);
                    iterator.remove(); // 使用迭代器安全删除元素
                }
            }
        }
    }

    public void render(SpriteBatch sb) {
        render(sb, false);
    }

    public void render(SpriteBatch sb, boolean flipHorizontal) {
        //3D相关
        sb.end();

        frameBuffer.begin();
//
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth()*ExpandScale, Gdx.graphics.getHeight()*ExpandScale);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        sb.setProjectionMatrix(camera.combined);
//        sb.begin();
        momoiController.render(camera, environment);
        midoriController.render(camera, environment);

        frameBuffer.end();
        try {
            Texture texture = this.frameBuffer.getColorBufferTexture();
            texture.bind(0);
            EXTFramebufferObject.glGenerateMipmapEXT(texture.glTarget);
            texture.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
        } catch (Throwable ex) {
            ModHelper.logger.warn("Generate mipmap for remember frame buffer failed: " + ex);
        }
        region = new TextureRegion(this.frameBuffer.getColorBufferTexture());


        region.flip(flipHorizontal, true);
        psb.begin();

        if (flipHorizontal) {
            psb.draw(region, this.current_x - 125.0F * Settings.scale, this.current_y,
                    Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                    Gdx.graphics.getWidth() / 2.0F, Gdx.graphics.getHeight() / 2.0F,
                    1.0F, 1.0F, 0.0F);
        } else {
            psb.draw(region, this.current_x, this.current_y,
                    Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                    Gdx.graphics.getWidth() / 2.0F, Gdx.graphics.getHeight() / 2.0F,
                    1.0F, 1.0F, 0.0F);
        }

        psb.setShader(null);
        psb.setBlendFunction(770, 771);
        psb.end();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        sb.setProjectionMatrix(BATwinsCardCrawlGamePatch.createPatch.camera.combined);
        sb.begin();
    }

    public void setMomoiAnimation(MomoiActionList action) {
        this.momoiController.setAnimation(action.getOperation());
        switch (action) {
            case MOVING:
                this.momoiController.moveCurrentPosition(300 * Settings.scale, 0);
                break;
            case REACTION:
                boolean already = false;
                for (Countdown c : this.controlCountdown) {
                    if (c.ID.equals("MOMOIREACTION")) {
                        c.time = 3.3F;
                        already = true;
                        break;
                    }
                }
                if (!already) {
                    this.momoiController.rotate(0, 1, 0, -90);
                    this.controlCountdown.add(new Countdown(modelController -> {
                        modelController.rotate(0, 1, 0, 90);
                    }, 3.3F, BATWINS_MOMOI_CARD, "MOMOIREACTION"));
                }
                break;
        }
    }

    public void setMidoriAnimation(MidoriActionList action) {
        this.midoriController.setAnimation(action.getOperation());
        switch (action) {
            case MOVING:
                this.midoriController.moveCurrentPosition(300 * Settings.scale, 0);
                break;
            case REACTION:
                boolean already = false;
                for (Countdown c : this.controlCountdown) {
                    if (c.ID.equals("MIDORIREACTION")) {
                        c.time = 3.3F;
                        already = true;
                        break;
                    }
                }
                if (!already) {
                    this.midoriController.rotate(0, 1, 0, -90);
                    this.controlCountdown.add(new Countdown(modelController -> {
                        modelController.rotate(0, 1, 0, 90);
                    }, 3.3F, BATWINS_MIDORI_CARD, "MIDORIREACTION"));
                }

                break;
        }
    }

    public void resetModelPosition(float x, float y, AbstractCard.CardColor color) {
        if (color == BATWINS_MOMOI_CARD) {
            this.momoiController.resetPosition(x, y);
        } else {
            this.midoriController.resetPosition(x, y);
        }

    }

    public void setPosition(float x, float y) {
        this.current_x = x;
        this.current_y = y;
    }

    public boolean inited() {
        return this.inited;
    }

    public void setStandAnima(AnimationName anima, AbstractCard.CardColor color) {
        if (color == BATWINS_MOMOI_CARD) {
            this.momoiController.setStandAnima(getAnimationString(anima, BATWINS_MOMOI_CARD));
        } else {
            this.midoriController.setStandAnima(getAnimationString(anima, BATWINS_MIDORI_CARD));
        }
    }

    public String getCurrentAnima(AbstractCard.CardColor color) {
        if (color == BATWINS_MOMOI_CARD) {
            return this.momoiController.getCurrentAnima();
        } else {
            return this.midoriController.getCurrentAnima();
        }
    }

    public void resetDefaultAnima(AbstractCard.CardColor color) {
        if (color == BATWINS_MOMOI_CARD) {
            this.momoiController.resetDefaultAnima();
        } else {
            this.midoriController.resetDefaultAnima();
        }
    }

//    public void initWithTimeout() throws RuntimeException {
//        ModelLoaderThread thread = new ModelLoaderThread();
//        thread.start();
//        try {
//            thread.join(5000);
//            if (!thread.finishLoading) {
//                throw new RuntimeException("Model loading exceeded timeout");
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void clearMomoiAnima() {
        this.momoiController.resetDefaultAnima();
    }

    public void clearMidoriAnima() {
        this.midoriController.resetDefaultAnima();
    }

    public void resetCharacterPosition() {
        this.momoiController.resetPosition(150*ExpandScale * Settings.scale, 0);
        this.midoriController.resetPosition(-150*ExpandScale * Settings.scale, 0);
    }

    public enum AnimationName {
        NORMAL_IDLE,
        STAND_ATTACK_DELAY,
        MOVING,
        MOVING_END,
        ATTACK_START,
        ATTACKING,
        ATTACK_END,
        MOVE_JUMP,
        DEATH,
        DYING,
        RELOAD,
        PANIC,
        REACTION
    }

    public enum MomoiActionList {
        STAND_NORMAL(animationController -> {
            ModHelper.getLogger().info("STAND_NORMAL");
            animationController.queue(getAnimationString(STAND_ATTACK_DELAY, BATWINS_MOMOI_CARD), -1, 1, null, 0.5F);
        }),
        ATTACK(animationController -> {
            ModHelper.getLogger().info("ATTACK");
            animationController.queue(getAnimationString(ATTACK_START, BATWINS_MOMOI_CARD), 1, 1, null, 0.2F);
            animationController.queue(getAnimationString(ATTACKING, BATWINS_MOMOI_CARD), 1, 1, new AnimationController.AnimationListener() {
                @Override
                public void onEnd(AnimationController.AnimationDesc animationDesc) {
                    animationController.queue(getAnimationString(ATTACK_END, BATWINS_MOMOI_CARD), 1, 1, null, 0.2F);
                }

                @Override
                public void onLoop(AnimationController.AnimationDesc animationDesc) {

                }
            }, 0.2F);
        }),
        MOVING(animationController -> {
            ModHelper.getLogger().info("MOVING");
            animationController.queue(getAnimationString(AnimationName.MOVING, BATWINS_MOMOI_CARD), 4, 1.8F, null, 0.5F);
            animationController.queue(getAnimationString(MOVING_END, BATWINS_MOMOI_CARD), 1, 1.0F, null, 0.2F);
        }),
        JUMP(animationController -> {
            ModHelper.getLogger().info("JUMP");
            animationController.queue(getAnimationString(MOVE_JUMP, BATWINS_MOMOI_CARD), 1, 1, null, 0.5F);
        }),
        DEATH(animationController -> {
            ModHelper.getLogger().info("DEATH");
            animationController.queue(getAnimationString(AnimationName.DEATH, BATWINS_MOMOI_CARD), 1, 1, null, 0.5F);
        }),
        DYING(animationController -> {
            ModHelper.getLogger().info("DYING");
            animationController.queue(getAnimationString(AnimationName.DYING, BATWINS_MOMOI_CARD), -1, 1, null, 0.5F);
        }),
        RELOAD(animationController -> {
            ModHelper.getLogger().info("RELOAD");
            animationController.queue(getAnimationString(AnimationName.RELOAD, BATWINS_MOMOI_CARD), 1, 1, null, 0.5F);
        }),
        PANIC(animationController -> {
            ModHelper.getLogger().info("PANIC");
            animationController.queue(getAnimationString(AnimationName.PANIC, BATWINS_MOMOI_CARD), -1, 1, null, 0.5F);
        }),
        ESCAPE(animationController -> {
            ModHelper.getLogger().info("ESCAPE");
            animationController.queue(getAnimationString(AnimationName.MOVING, BATWINS_MOMOI_CARD), 7, 1, null, 0.5F);
        }),
        REACTION(animationController -> {
            ModHelper.getLogger().info("REACTION");

            animationController.queue(getAnimationString(AnimationName.REACTION, BATWINS_MOMOI_CARD), 1, 1, null, 0.5F);
        }),
        CONTINUEATTACK(animationController->{
            ModHelper.getLogger().info("CONTINUEATTACK");
            animationController.queue(getAnimationString(ATTACKING,BATWINS_MOMOI_CARD),5,1,null,0.0F);
        });
        private final Consumer<AnimationController> operation;

        private MomoiActionList(Consumer<AnimationController> operation) {
            this.operation = operation;
        }

        public Consumer<AnimationController> getOperation() {
            return operation;
        }
    }

    public enum MidoriActionList {
        STAND_NORMAL(animationController -> {
            ModHelper.getLogger().info("STAND_NORMAL");
            animationController.queue(getAnimationString(STAND_ATTACK_DELAY, BATWINS_MIDORI_CARD), -1, 1, null, 0.5F);
        }),
        ATTACK(animationController -> {
            ModHelper.getLogger().info("ATTACK");
            animationController.queue(getAnimationString(ATTACK_START, BATWINS_MIDORI_CARD), 1, 1, null, 0.2F);
            animationController.queue(getAnimationString(ATTACKING, BATWINS_MIDORI_CARD), 1, 1, new AnimationController.AnimationListener() {
                @Override
                public void onEnd(AnimationController.AnimationDesc animationDesc) {
                    animationController.queue(getAnimationString(ATTACK_END, BATWINS_MIDORI_CARD), 1, 1, null, 0.2F);
                }

                @Override
                public void onLoop(AnimationController.AnimationDesc animationDesc) {

                }
            }, 0.2F);
        }),
        MOVING(animationController -> {
            ModHelper.getLogger().info("MOVING");
            animationController.queue(getAnimationString(AnimationName.MOVING, BATWINS_MIDORI_CARD), 4, 1.8F, null, 0.5F);
            animationController.queue(getAnimationString(MOVING_END, BATWINS_MIDORI_CARD), 1, 1.0F, null, 0.2F);
        }),
        JUMP(animationController -> {
            ModHelper.getLogger().info("JUMP");
            animationController.queue(getAnimationString(MOVE_JUMP, BATWINS_MIDORI_CARD), 1, 1, null, 0.5F);
        }),
        DEATH(animationController -> {
            ModHelper.getLogger().info("DEATH");
            animationController.queue(getAnimationString(AnimationName.DEATH, BATWINS_MIDORI_CARD), 1, 1, null, 0.5F);
        }),
        DYING(animationController -> {
            ModHelper.getLogger().info("DYING");
            animationController.queue(getAnimationString(AnimationName.DYING, BATWINS_MIDORI_CARD), -1, 1, null, 0.5F);
        }),
        RELOAD(animationController -> {
            ModHelper.getLogger().info("RELOAD");
            animationController.queue(getAnimationString(AnimationName.RELOAD, BATWINS_MIDORI_CARD), 1, 1, null, 0.5F);
        }),
        PANIC(animationController -> {
            ModHelper.getLogger().info("PANIC");
            animationController.queue(getAnimationString(AnimationName.PANIC, BATWINS_MIDORI_CARD), -1, 1, null, 0.5F);
        }),
        ESCAPE(animationController -> {
            ModHelper.getLogger().info("ESCAPE");
            animationController.queue(getAnimationString(AnimationName.MOVING, BATWINS_MIDORI_CARD), 7, 1, null, 0.5F);
        }),
        REACTION(animationController -> {
            ModHelper.getLogger().info("REACTION");

            animationController.queue(getAnimationString(AnimationName.REACTION, BATWINS_MIDORI_CARD), 1, 1, null, 0.5F);
        }),
        CONTINUEATTACK(animationController->{
            ModHelper.getLogger().info("CONTINUEATTACK");
            animationController.queue(getAnimationString(ATTACKING,BATWINS_MIDORI_CARD),5,1,null,0.0F);
        });
        private final Consumer<AnimationController> operation;

        private MidoriActionList(Consumer<AnimationController> operation) {
            this.operation = operation;
        }

        public Consumer<AnimationController> getOperation() {
            return operation;
        }
    }

    private void renderFrame(){

    }

    public static class ModelLoaderThread extends Thread {
        public boolean finishLoading = false;
        private Character3DHelper character3DHelper;

        public ModelLoaderThread(Character3DHelper character3DHelper) {
            this.character3DHelper = character3DHelper;
        }

        @Override
        public void run() {
            character3DHelper.init();
            finishLoading = true;
        }
    }
    public void clearCountdown(){
        for(Countdown c:this.controlCountdown){
            if (c.color == BATWINS_MOMOI_CARD)
                c.consumer.accept(this.momoiController);
            else
                c.consumer.accept(this.midoriController);
        }
        this.controlCountdown.clear();
    }

    private static class Countdown {
        public Consumer<ModelController> consumer;
        public float time;
        public AbstractCard.CardColor color;
        public String ID;

        public Countdown(Consumer<ModelController> consumer, float time, AbstractCard.CardColor color, String ID) {
            this.consumer = consumer;
            this.time = time;
            this.color = color;
            this.ID = ID;
        }

    }
}
