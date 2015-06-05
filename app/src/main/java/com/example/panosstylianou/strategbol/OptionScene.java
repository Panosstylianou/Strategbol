package com.example.panosstylianou.strategbol;

import com.example.panosstylianou.strategbol.SceneManager.SceneType;
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

    private void createBackground() {
        setBackground(new Background(Color.CYAN));
    }

    private HUD optionsHUD;
    private Text optionsText;

    private void createHUD() {

        optionsHUD = new HUD();

        // CREATE SCORE TEXT
        optionsText = new Text(20, 740, resourcesManager.font, "Options Menu", new TextOptions(HorizontalAlign.CENTER), vbom); //Initialize text with all characters that are going to be used to prepare memory
        optionsText.setAnchorCenter(0, 0);
        //optionsText.setText("Hi");
        optionsHUD.attachChild(optionsText); //Attach Options Text to HUD

        ResourcesManager.getInstance().camera.setHUD(optionsHUD);

        camera.setHUD(optionsHUD);

    }

}
