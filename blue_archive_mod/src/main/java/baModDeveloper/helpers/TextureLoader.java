package baModDeveloper.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureLoader {
    private static HashMap<String, Texture> textrues = new HashMap<>();
    public static final Logger logger = LogManager.getLogger(TextureLoader.class.getName());

    public static Texture getTexture(String id) {
        if (textrues.get(id) == null) {
            try {
                loadTexture(id);
            } catch (GdxRuntimeException e) {
                logger.error("Could not find Texture " + id);
                return getTexture(ModHelper.makeImgPath("relic", "defaultImg"));
            }
        }
        return textrues.get(id);
    }

    private static void loadTexture(String id) throws GdxRuntimeException {
        logger.info("BATwinsMod:LoadTexture" + id);
        Texture texture = new Texture(id);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textrues.put(id, texture);
    }
}
