package baModDeveloper.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.core.Settings;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ShaderHelper {
    private static final Map<String,ShaderProgram> shaderMap=new HashMap<>();
    private static final FrameBuffer frameBuffer=new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),false,false);
    public static void renderShader(ShaderProgram shaderProgram, SpriteBatch spriteBatch, Consumer<SpriteBatch> method){
        spriteBatch.end();
        frameBuffer.begin();
        spriteBatch.begin();
        method.accept(spriteBatch);
        spriteBatch.end();
        frameBuffer.end();
        spriteBatch.setShader(shaderProgram);
        spriteBatch.begin();
        Texture renderImage=frameBuffer.getColorBufferTexture();
        spriteBatch.setColor(Color.WHITE.cpy());
        spriteBatch.draw(renderImage, 0.0F, 0.0F, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT, 1.0F, 1.0F, 0.0F, 0, 0, Settings.WIDTH, Settings.HEIGHT, false, true);

        spriteBatch.end();
        spriteBatch.setShader(null);
        spriteBatch.flush();
        spriteBatch.begin();
    }

    public static ShaderProgram getShaderProgram(String key){
        if(shaderMap.containsKey(key)){
            return shaderMap.get(key);
        }else{
            ShaderProgram shaderProgram=new ShaderProgram(Gdx.files.internal("baModResources/shader/"+key+"/vertex.glsl"),Gdx.files.internal("baModResources/shader/"+key+"/fragment.glsl"));
            if(!shaderProgram.isCompiled()){
                throw new RuntimeException(shaderProgram.getLog());
            }
            shaderMap.put(key,shaderProgram);
            return shaderProgram;
        }
    }

}
