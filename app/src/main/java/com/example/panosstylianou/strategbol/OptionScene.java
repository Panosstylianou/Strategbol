package com.example.panosstylianou.strategbol;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
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
//      createPhysics();
    }

    private HUD optionsHUD;
    private Text optionsText;

    private void createHUD() {

        optionsHUD = new HUD();

        // CREATE SCORE TEXT
        optionsText = new Text(20, 420, resourcesManager.font, "Options Menu", new TextOptions(HorizontalAlign.CENTER), vbom); //Initialize text with all characters that are going to be used to prepare memory
        optionsText.setAnchorCenter(super.getOffsetCenterX(), 10);
        optionsText.setText("I don't know what this will be yet");
        optionsHUD.attachChild(optionsText); //Attach Options Text to HUD

        ResourcesManager.getInstance().camera.setHUD(optionsHUD);

        camera.setHUD(optionsHUD);

    }

    private void createBackground() {
        setBackground(new Background(Color.CYAN));
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(engine);
    }

    @Override
    public SceneManager.SceneType getSceneType() { return SceneManager.SceneType.SCENE_OPTIONS;
    }

    @Override
    public void disposeScene() {

            camera.setHUD(null);
            camera.setCenter(400, 240);

            // TODO code responsible for disposing scene
            // removing all game scene objects.

    }
}
