package com.example.panosstylianou.strategbol;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

/**
 * Created by panosstylianou on 02/06/15.
 */
public class MainMenuScene extends BaseScene
{

    //CLASS VARIABLES/OBJECTS
    private MenuScene menuChildScene;

    private final int MENU_PLAY = 0;
    private final int MENU_TUTORIAL = 1;
    private final int MENU_VOLUME_ON = 3;
    private final int MENU_VOLUME_OFF = 4;

    private IMenuItem volumeOnMenuItem;
    private IMenuItem volumeOffMenuItem;

    //CLASS METHODS
    @Override
    public void createScene()
    {
        createBackground();
        createMenuChildScene();
        checkMusic();
    }

    public void checkMusic()
    {
        if (resourcesManager.musicOn)
        {
            volumeOnMenuItem.setVisible(true);
            volumeOffMenuItem.setVisible(false);
            resourcesManager.mMusic.setVolume(1f);
        }
        else
        {
            volumeOnMenuItem.setVisible(false);
            volumeOffMenuItem.setVisible(true);
            resourcesManager.mMusic.setVolume(0f);
        }
    }

    public void changeMusic()
    {
        if (resourcesManager.musicOn)
        {
            volumeOnMenuItem.setVisible(false);
            volumeOffMenuItem.setVisible(true);
            resourcesManager.mMusic.setVolume(0f);
            resourcesManager.musicOn = false;
        }
        else
        {
            volumeOnMenuItem.setVisible(true);
            volumeOffMenuItem.setVisible(false);
            resourcesManager.mMusic.setVolume(1f);
            resourcesManager.musicOn = true;
        }
    }

    @Override
    public void onBackKeyPressed()
    {
        ResourcesManager.getInstance().unloadMenuTextures();
        this.disposeScene();
        System.exit(0);
    }

    @Override
    public SceneManager.SceneType getSceneType()
    {
        return SceneManager.SceneType.SCENE_MENU;
    }

    @Override
    public void disposeScene() {
        camera.setHUD(null);
        camera.setCenter(240, 400);
        camera.setChaseEntity(null);
    }

    private void createBackground()
    {
        //Create a new sprite in the middle of the screen for the background
        attachChild(new Sprite(240, 400, resourcesManager.menu_background_region, vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        });
    }

    private void createMenuChildScene()
    {
        menuChildScene = new MenuScene(camera); //Built-in AndEngine MenuScene class
        menuChildScene.setPosition(240, 400);
        //Create menu buttons with ScaleMenuItemDecorator to make then animated - Could be changed.
        //Last two float numbers refer to the scale of the object when selected and when unselected.
        final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
        final IMenuItem tutorialMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_TUTORIAL, resourcesManager.tutorial_region, vbom), 1.2f, 1);
        volumeOnMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_VOLUME_ON, resourcesManager.musicOn_region, vbom), 1.2f, 1);
        volumeOffMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_VOLUME_OFF, resourcesManager.musicOff_region, vbom), 1.2f, 1);

        //Adding to scene
        menuChildScene.addMenuItem(playMenuItem);
        menuChildScene.addMenuItem(tutorialMenuItem);
        menuChildScene.addMenuItem(volumeOnMenuItem);
        menuChildScene.addMenuItem(volumeOffMenuItem);

        menuChildScene.buildAnimations();
        menuChildScene.setBackgroundEnabled(false);

        //playMenuItem.setAlpha(0.5f);
        //tutorialMenuItem.setAlpha(0.5f);

        playMenuItem.setPosition(super.getOffsetCenterX(), playMenuItem.getY() - 350);
        tutorialMenuItem.setPosition(super.getOffsetCenterX(), tutorialMenuItem.getY() - 400);
        volumeOnMenuItem.setPosition(super.getOffsetCenterX() - 210, super.getOffsetCenterY() - 370);
        volumeOffMenuItem.setPosition(super.getOffsetCenterX() - 210, super.getOffsetCenterY() - 370);

        menuChildScene.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                MainMenuScene.this.onMenuItemClicked(pMenuScene, pMenuItem, pMenuItemLocalX, pMenuItemLocalY);
                return true;
            }
        });
        setChildScene(menuChildScene);
    }

    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
    {
        switch(pMenuItem.getID())
        {
            case MENU_PLAY:
                //Load Game Scene
                ResourcesManager.getInstance().unloadMenuTextures();
                SceneManager.getInstance().loadGameScene(engine);
                return true;
            case MENU_TUTORIAL:
                //Load Options Scene
                ResourcesManager.getInstance().unloadMenuTextures();
                SceneManager.getInstance().loadTutorialScene(engine);
                return true;
            case MENU_VOLUME_ON:
                //Handle Music Volume
                this.changeMusic();
                resourcesManager.saveData();
                return true;
            default:
                return false;
        }
    }

}
