package com.example.panosstylianou.strategbol;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.align.HorizontalAlign;

/**
 * Created by Damalas on 05/06/15.
 */

public class TutorialScene extends BaseScene {

    //VARIABLES
    private HUD tutorialHUD;
    private MenuScene menuChildScene;
    private Text someText;

    @Override
    public void createScene() {
        createBackground();
        createHUD();
        createMenuChildScene();

    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(engine);
        ResourcesManager.getInstance().unloadTutorialTextures();
        this.disposeScene();
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_TUTORIAL;
    }

    @Override
    public void disposeScene() {
        camera.setHUD(null);
        camera.setCenter(240, 400);
        camera.setChaseEntity(null);
    }

    private void createBackground() {
        //Create a new sprite in the middle of the screen for the background
        Sprite spriteBG = new Sprite(240, 400, resourcesManager.menu_background_region, vbom);
        attachChild(spriteBG);
        spriteBG.setAlpha(0.7f);

    }

    private void createHUD() {
        tutorialHUD = new HUD();
        //CREATE TEXT
        someText = new Text(240, 750, resourcesManager.font, "Tutorial Scene: 0123456789", new TextOptions(HorizontalAlign.CENTER), vbom); //Initialize text with all characters that are going to be used to prepare memory
        someText.setAnchorCenter(someText.getOffsetCenterX(), someText.getOffsetCenterY());
        someText.setScale(0.7f);
        someText.setText("Welcome to Strategbol!");
        tutorialHUD.attachChild(someText); //Attach Tutorial Text to HUD

        ResourcesManager.getInstance().camera.setHUD(tutorialHUD);
    }

    private void createMenuChildScene() {
        menuChildScene = new MenuScene(camera); //Built-in AndEngine MenuScene class
        menuChildScene.setPosition(240, 400);
        menuChildScene.setBackgroundEnabled(false);

        menuChildScene.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                //MainMenuScene.this.onMenuItemClicked(pMenuScene, pMenuItem, pMenuItemLocalX, pMenuItemLocalY);
                return true;
            }
        });
        setChildScene(menuChildScene);
    }

}