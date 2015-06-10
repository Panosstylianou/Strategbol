package com.example.panosstylianou.strategbol;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

/**
 * Created by Damalas on 05/06/15.
 */

public class OptionScene extends BaseScene
{

    //CLASS VARIABLES/OBJECTS
    private HUD optionsHUD;
    private Text volumeText;
    private MenuScene menuChildScene;
    private final int VOLUME_SLIDER = 0;


    //CLASS METHODS
    @Override
    public void createScene()
    {
        createBackground();
        createHUD();
        createMenuChildScene();
    }

    @Override
    public void onBackKeyPressed()
    {
        SceneManager.getInstance().loadMenuScene(engine);
        ResourcesManager.getInstance().unloadOptionTextures();
        this.disposeScene();
    }

    @Override
    public SceneManager.SceneType getSceneType()
    {
        return SceneManager.SceneType.SCENE_OPTIONS;
    }

    @Override
    public void disposeScene()
    {
        camera.setHUD(null);
        camera.setCenter(240, 400);

        // TODO code responsible for disposing scene
        // removing all game scene objects.
    }

    private void createBackground()
    {
        //Create a new sprite in the middle of the screen for the background
        Sprite spriteBG = new Sprite(240, 400, resourcesManager.menu_background_region, vbom);
        attachChild(spriteBG);
        spriteBG.setAlpha(0.7f);

    }

    private void createHUD()
    {
        optionsHUD = new HUD();

        // CREATE VOLUME TEXT
        volumeText = new Text(150, 640, resourcesManager.font, "Volume", new TextOptions(HorizontalAlign.CENTER), vbom); //Initialize text with all characters that are going to be used to prepare memory
        volumeText.setAnchorCenter(0, 0);

        optionsHUD.attachChild(volumeText); //Attach Options Text to HUD
        ResourcesManager.getInstance().camera.setHUD(optionsHUD);
    }

    private void createMenuChildScene()
    {
        menuChildScene = new MenuScene(camera); //Built-in AndEngine MenuScene class
        menuChildScene.setPosition(240, 400);

        //Create menu buttons with ScaleMenuItemDecorator to make then animated - Could be changed
        final IMenuItem volumeSlider = new ScaleMenuItemDecorator(new SpriteMenuItem(VOLUME_SLIDER, resourcesManager.volume_region, vbom), 1.2f, 1);

        //Adding to scene
        menuChildScene.addMenuItem(volumeSlider);
        menuChildScene.buildAnimations();
        menuChildScene.setBackgroundEnabled(false);

        volumeSlider.setPosition(super.getOffsetCenterX(), volumeSlider.getY() - 275);

        menuChildScene.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
            {
                //MainMenuScene.this.onMenuItemClicked(pMenuScene, pMenuItem, pMenuItemLocalX, pMenuItemLocalY);
                return true;
            }
        });
        setChildScene(menuChildScene);
    }

}