package baModDeveloper.ui.panels.icons;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.screens.stats.AchievementItem;

public class BATwinsAchievementItem extends AchievementItem {
    private String lockImgUrl;
    private String unlockImgUrl;
    public BATwinsAchievementItem(String title,String desc,String lockImgUrl,String unlockImgUrl) {
        super(title, desc, "", "");
        this.isUnlocked=ModHelper.ENABLE_DLC;
        this.lockImgUrl=lockImgUrl;
        this.unlockImgUrl=unlockImgUrl;
        this.reloadImg();
    }

    @Override
    public void reloadImg() {
        String imgUrl;
        if(this.isUnlocked){
            imgUrl=unlockImgUrl;
        }else {
            imgUrl=lockImgUrl;
        }
        Texture texture=TextureLoader.getTexture(imgUrl);
        TextureAtlas.AtlasRegion region=new TextureAtlas.AtlasRegion(texture,0,0,texture.getWidth(),texture.getHeight());
        ReflectionHacks.setPrivate(this,AchievementItem.class,"img", region);
    }
}
