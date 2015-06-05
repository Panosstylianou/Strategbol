package com.example.panosstylianou.strategbol;

import com.badlogic.gdx.math.Vector2;
import com.example.panosstylianou.strategbol.SceneManager.SceneType;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
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

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(engine);
    }

    @Override
    public SceneType getSceneType() { return SceneType.SCENE_OPTIONS;}

    @Override
    public void disposeScene() {

        camera.setHUD(null);
        camera.setCenter(400, 240);

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
        optionsText = new Text(20, 420, resourcesManager.font, "Options Menu", new TextOptions(HorizontalAlign.CENTER), vbom); //Initialize text with all characters that are going to be used to prepare memory
        optionsText.setAnchorCenter(0, 0);
        //optionsText.setText("Hi");
        optionsHUD.attachChild(optionsText); //Attach Options Text to HUD

        ResourcesManager.getInstance().camera.setHUD(optionsHUD);

        camera.setHUD(optionsHUD);

    }

    private PhysicsWorld physicsWorld;

        private void createPhysics(){
        physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0,-17), false);    //Create Physics World with  60 steps per second
        registerUpdateHandler(physicsWorld);
    }

}
