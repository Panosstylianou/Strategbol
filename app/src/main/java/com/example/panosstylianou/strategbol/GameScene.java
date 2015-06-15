package com.example.panosstylianou.strategbol;

/**
 * Created by panosstylianou on 04/06/15.
 */

import android.widget.Button;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import java.io.IOException;


public class GameScene extends BaseScene implements IOnSceneTouchListener {

    //VARIABLES
    private HUD gameHUD;
    private Text scoreText;
    private Text gameOverText;
    private Player player;
    private PhysicsWorld physicsWorld;

    private boolean firstTouch = false;
    private int score = 0;

    private static final String TAG_ENTITY = "entity";
    private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
    private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
    private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";

    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER1 = "player1";
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER2 = "player2";
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER3 = "player3";
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_FOOTBALL = "football";
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER = "player";

    @Override
    public void createScene() {
        createBackground();
        createHUD();
        createPhysics();
        loadLevel(1);
        createGameOverText();
        setOnSceneTouchListener(this);
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(engine);
        ResourcesManager.getInstance().unloadGameTextures();
        this.disposeScene();
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_GAME;
    }

    @Override
    public void disposeScene() {
        camera.setHUD(null);
        camera.setCenter(240, 400);
        camera.setChaseEntity(null);
    }

    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.isActionDown()) {
            if (!firstTouch){

            }
            else {

            }
        }
        return false;
    }

    private void createHUD() { //Create Heads-Up Display for User Interface
        gameHUD = new HUD();
        // CREATE SCORE TEXT
        scoreText = new Text(20, 740, resourcesManager.font, "Game Scene: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom); //Initialize text with all characters that are going to be used to prepare memory
        scoreText.setAnchorCenter(0, 0);
        scoreText.setText("");
        gameHUD.attachChild(scoreText); //Attach Score Text to HUD

        ResourcesManager.getInstance().camera.setHUD(gameHUD);
        camera.setHUD(gameHUD);
    }

    private void createPhysics() {
        physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -17), false); //Create Physics World with  60 steps per second
        registerUpdateHandler(physicsWorld);
    }

    private void createBackground() {
            attachChild(new Sprite(240, 400, resourcesManager.pitch_region, vbom) { //Create a new sprite in the middle of the screen for the background
                @Override
                protected void preDraw(GLState pGLState, Camera pCamera) {
                    super.preDraw(pGLState, pCamera);
                    pGLState.enableDither();
                }
            });
    }

    private void addToScore(int i) {
        score += i;
        scoreText.setText("Score: " + score);
    }

    private boolean gameOverDisplayed = false;

    private void createGameOverText() {
        gameOverText = new Text(0, 0, resourcesManager.font, "Game Over!", vbom);
    }

    private void displayGameOverText() {
        camera.setChaseEntity(null);
        gameOverText.setPosition(camera.getCenterX(), camera.getCenterY());
        attachChild(gameOverText);
        gameOverDisplayed = true;
    }

    private void loadLevel(int levelID) {

        final SimpleLevelLoader levelLoader = new SimpleLevelLoader(vbom);
        final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);

        levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL) {   //The entity where all loaded entities are attached to
            public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException {
                final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
                final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);

                // TODO later we will specify camera BOUNDS and create invisible walls
                // on the beginning and on the end of the level.

                return GameScene.this;
            }
        });

        levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_ENTITY) {

            public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException {
                final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);
                final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
                final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);
                final Sprite levelObject;

                //Parse XML files
                if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER1)) {

                    levelObject = new Sprite(x, y, resourcesManager.player1_region, vbom);
                    PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyDef.BodyType.StaticBody, FIXTURE_DEF).setUserData("player1");

                } else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER2)) {

                    levelObject = new Sprite(x, y, resourcesManager.player2_region, vbom);
                    final Body body = PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyDef.BodyType.StaticBody, FIXTURE_DEF);
                    body.setUserData("player2");
                    physicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));

                } else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER3)) {

                    levelObject = new Sprite(x, y, resourcesManager.player3_region, vbom);
                    final Body body = PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyDef.BodyType.StaticBody, FIXTURE_DEF);
                    body.setUserData("player3");
                    physicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));

                } else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_FOOTBALL)) {

                    levelObject = new Sprite(x, y, resourcesManager.football_region, vbom) {
                        @Override
                        protected void onManagedUpdate(float pSecondsElapsed) {
                            super.onManagedUpdate(pSecondsElapsed);
//                            if (player.collidesWith(this)) {
//                                addToScore(10);
//                                this.setVisible(false);
//                                this.setIgnoreUpdate(true);
//                            }
                        }
                    };
                    levelObject.registerEntityModifier(new LoopEntityModifier(new ScaleModifier(1, 1, 1.1f)));

                } else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER)) {

                    player = new Player(x, y, vbom, camera, physicsWorld) {
                        @Override
                        public void onDie() {
                            if (!gameOverDisplayed) {
                                displayGameOverText();
                            }
                        }
                        @Override
                        public void onReachedDestination() {

                        }
                    };
                    levelObject = player;

                } else {
                    throw new IllegalArgumentException();
                }

                levelObject.setCullingEnabled(true);
                return levelObject;
            }
        });
        levelLoader.loadLevelFromAsset(activity.getAssets(), "level/" + levelID + ".xml");
    }

}
