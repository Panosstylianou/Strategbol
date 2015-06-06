package com.example.panosstylianou.strategbol;

import com.example.panosstylianou.strategbol.SceneManager.SceneType;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

/**
 * Created by Damalas on 05/06/15.
 */

public class OptionScene extends BaseScene {


    @Override
    public void createScene() {

        createBackground();
        createHUD();
        createMenuChildScene();

    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(engine);
        this.disposeScene();
    }

    @Override
    public SceneType getSceneType() { return SceneType.SCENE_OPTIONS;}

    @Override
    public void disposeScene() {

        camera.setHUD(null);
        camera.setCenter(240, 400);

        // TODO code responsible for disposing scene
        // removing all game scene objects.

    }

    private void createBackground(){
        {   //Create a new sprite in the middle of the screen for the background
            Sprite spriteBG = new Sprite(240, 400, resourcesManager.menu_background_region, vbom);
            attachChild(spriteBG);
            spriteBG.setAlpha(0.2f);
        }
    }

    private HUD optionsHUD;
    private Text volumeText;
    private Text difficultyText;

    private void createHUD() {

        optionsHUD = new HUD();

        // CREATE VOLUME TEXT
        volumeText = new Text(150, 640, resourcesManager.font, "Volume", new TextOptions(HorizontalAlign.CENTER), vbom); //Initialize text with all characters that are going to be used to prepare memory
        volumeText.setAnchorCenter(0, 0);
        optionsHUD.attachChild(volumeText); //Attach Options Text to HUD

        // CREATE DIFFICULTY TEXT
        difficultyText = new Text(140, 240, resourcesManager.font, "Difficulty", new TextOptions(HorizontalAlign.CENTER), vbom); //Initialize text with all characters that are going to be used to prepare memory
        difficultyText.setAnchorCenter(0, 0);
        optionsHUD.attachChild(difficultyText); //Attach Options Text to HUD

        ResourcesManager.getInstance().camera.setHUD(optionsHUD);

        camera.setHUD(optionsHUD);

    }

    private MenuScene menuChildScene;
    private final int BACKGROUND = 0;
    private final int VOLUME_SLIDER = 1;
    private final int DIFFICULTY_LEVEL = 2;

    private void createMenuChildScene()
    {
        menuChildScene = new MenuScene(camera); //Built-in AndEngine MenuScene class
        menuChildScene.setPosition(240, 400);
        //Create menu buttons with ScaleMenuItemDecorator to make then animated - Could be changed
        final IMenuItem volumeSlider = new ScaleMenuItemDecorator(new SpriteMenuItem(VOLUME_SLIDER, resourcesManager.volume_region, vbom), 1.2f, 1);
        final IMenuItem difficultyLevel = new ScaleMenuItemDecorator(new SpriteMenuItem(DIFFICULTY_LEVEL, resourcesManager.difficulty_region, vbom), 1.2f, 1);
        final IMenuItem backgroundOption = new ScaleMenuItemDecorator(new SpriteMenuItem(BACKGROUND, resourcesManager.menu_background_region, vbom), 1.2f, 1);
        //Adding to scene
        menuChildScene.addMenuItem(volumeSlider);
        menuChildScene.addMenuItem(difficultyLevel);

        menuChildScene.buildAnimations();
        menuChildScene.setBackgroundEnabled(false);
        backgroundOption.setAlpha(0.2f);

        volumeSlider.setPosition(super.getOffsetCenterX(), volumeSlider.getY() - 275);
        difficultyLevel.setPosition(super.getOffsetCenterX(), difficultyLevel.getY() - 600);

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
