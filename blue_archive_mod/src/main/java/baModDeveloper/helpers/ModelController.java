package baModDeveloper.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.megacrit.cardcrawl.core.Settings;

import java.util.function.Consumer;

public class ModelController {
    private G3dModelLoader loader;
    private Model model;
    private ModelInstance instance;
    private ModelBatch modelBatch;

    private AnimationController animationController;


    private static final float SCALE = 4.5F* Settings.scale;
    private float current_x=0,current_y=0,target_x=0,target_y=0;
    private static final float MOVESCALE=Settings.scale*4;

    private String StandAnima;

    public ModelController(String modelPath,float x,float y,float z,String StandAnima){
        loader=new G3dModelLoader(new JsonReader());
        model=loader.loadModel(Gdx.files.internal(modelPath));
        instance=new ModelInstance(model,0,0,0);

        animationController=new AnimationController(instance);

        modelBatch=new ModelBatch();
        instance.transform.setTranslation(z,y,x);
        instance.transform.rotate(0,3,1,180);
        instance.transform.rotate(1,0,0,34);
        instance.transform.scale(SCALE,SCALE,SCALE);

        for(int i=0;i<instance.materials.size;i++){
            instance.materials.get(i).set(ColorAttribute.createDiffuse(Color.WHITE));
            if(this.instance.materials.get(i).id.equals("Month")){
                this.instance.materials.get(i).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
            }
        }
        this.StandAnima=StandAnima;
    }

    public void update(){
        if(this.animationController.current==null||this.animationController.current.time>=this.animationController.current.duration){
            this.animationController.queue(this.StandAnima,-1,1,null,0.4F);
        }
        animationController.update(Gdx.graphics.getDeltaTime());
        if(Math.abs(this.current_x-this.target_x)>MOVESCALE){
            this.current_x+=Math.signum(this.target_x-this.current_x)*MOVESCALE;
        }else{
            this.current_x=this.target_x;
        }
        if(Math.abs(this.current_y-this.target_y)>MOVESCALE){
            this.current_y+=Math.signum(this.target_y-this.current_y)*MOVESCALE;
        }{
            this.current_y=this.target_y;
        }
        this.instance.transform.setTranslation(-500,current_y,current_x);
    }

    public void render(OrthographicCamera camera,Environment environment){
        modelBatch.begin(camera);
        modelBatch.render(instance,environment);
        modelBatch.end();
    }

    public void resetPosition(float x,float y){
        this.current_x=this.target_x=x;
        this.current_y=this.target_y=y;
    }

    public void setCurrentPosition(float x,float y){
        this.current_x=x;
        this.current_y=y;
    }

    public void moveCurrentPosition(float x,float y){
        this.current_x+=x;
        this.current_y+=y;
    }

    private void clearQueue(AnimationController controller){
        while (controller.current!=null){
            controller.setAnimation(null);
        }
    }

    public void setAnimation(Consumer<AnimationController> animation){
        this.clearQueue(animationController);
        resetPosition(this.target_x,this.target_y);
        animation.accept(this.animationController);
    }

    public void setStandAnima(String anima){
        if(this.animationController.current.animation.id.equals(this.StandAnima)){
            this.clearQueue(this.animationController);
        }
        this.StandAnima=anima;
    }

    public String getCurrentAnima(){
        if(this.animationController.current==null){
            return "";
        }
        return this.animationController.current.animation.id;
    }

    public void resetDefaultAnima(){
        this.clearQueue(this.animationController);
    }
}
