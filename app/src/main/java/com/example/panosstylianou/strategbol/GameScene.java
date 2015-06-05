package com.example.panosstylianou.strategbol;

/**
 * Created by panosstylianou on 04/06/15.
 */

import com.badlogic.gdx.math.Vector2;
import com.example.panosstylianou.strategbol.BaseScene;
import com.example.panosstylianou.strategbol.SceneManager.SceneType;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;


public class GameScene extends BaseScene{

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
    public SceneType getSceneType() {
        return SceneType.SCENE_GAME;
    }

    @Override
    public void disposeScene() {

        camera.setHUD(null);
        camera.setCenter(400, 240);

        // TODO code responsible for disposing scene
        // removing all game scene objects.

    }

    private void createBackground(){
        setBackground(new Background(Color.GREEN)); //Set the background for the Game Scene
    }

    private HUD gameHUD;
    private Text scoreText;

    private void createHUD(){       //Create Heads-Up Display for User Interface

        gameHUD = new HUD();

        // CREATE SCORE TEXT
        scoreText = new Text(20, 420, resourcesManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom); //Initialize text with all characters that are going to be used to prepare memory
        scoreText.setAnchorCenter(0, 0);
        scoreText.setText("Score: 0");
        gameHUD.attachChild(scoreText); //Attach Score Text to HUD

        ResourcesManager.getInstance().camera.setHUD(gameHUD);

        camera.setHUD(gameHUD);

    }

    private int score = 0;

    private void addToScore(int i){
       score += i;
        scoreText.setText("Score:" + score);
    }

    private PhysicsWorld physicsWorld;

        private void createPhysics(){
        physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0,-17), false);    //Create Physics World with  60 steps per second
        registerUpdateHandler(physicsWorld);
    }

}
