package com.example.panosstylianou.strategbol;

/**
 * Created by panosstylianou on 04/06/15.
 */

import com.badlogic.gdx.math.Vector2;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;

public class GameScene extends BaseScene implements IOnSceneTouchListener {

    //VARIABLES
    private Player TA_player1;
    private Player TA_player2;
    private Player TA_player3;
    private Player TA_player4;
    private Player TA_player5;

    private Player TB_player1;
    private Player TB_player2;
    private Player TB_player3;
    private Player TB_player4;
    private Player TB_player5;

    private PhysicsWorld physicsWorld;

    @Override
    public void createScene() {
        createBackground();
        createPhysics();
        loadGameSprites();
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
        return true;
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

    private Player createPlayer(float x, float y, Player player, ITextureRegion playerRegion) {
        player = new Player(x, y, vbom, camera, physicsWorld, playerRegion) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionMove()) {
                    this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
                }
                return true;
            }
        };
        registerTouchArea(player);
        setTouchAreaBindingOnActionDownEnabled(true);
        return player;
    }

    private Sprite createFootball (float x, float y) {
        final Sprite football;
        football = new Sprite(x, y, resourcesManager.football_region, vbom) {
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
                if (TA_player1.collidesWith(this) || TB_player1.collidesWith(this) || TA_player2.collidesWith(this) || TB_player2.collidesWith(this) || TA_player3.collidesWith(this) || TB_player3.collidesWith(this) || TA_player4.collidesWith(this) || TB_player4.collidesWith(this) || TA_player5.collidesWith(this) || TB_player5.collidesWith(this)) {
                    this.setVisible(false);
                    this.setIgnoreUpdate(true);
                }
            }
        };
        return football;
    }

    private void loadGameSprites() {

        Sprite footballSprite;

        footballSprite = createFootball(240, 400);
        TA_player1 = createPlayer(240, 350, TA_player1, ResourcesManager.getInstance().playerA_region);
        TA_player2 = createPlayer(120, 150, TA_player2, ResourcesManager.getInstance().playerA_region);
        TA_player3 = createPlayer(150, 300, TA_player3, ResourcesManager.getInstance().playerA_region);
        TA_player4 = createPlayer(330, 300, TA_player4, ResourcesManager.getInstance().playerA_region);
        TA_player5 = createPlayer(360, 150, TA_player5, ResourcesManager.getInstance().playerA_region);

        TB_player1 = createPlayer(240, 450, TB_player1, ResourcesManager.getInstance().playerB_region);
        TB_player2 = createPlayer(120, 650, TB_player2, ResourcesManager.getInstance().playerB_region);
        TB_player3 = createPlayer(150, 500, TB_player3, ResourcesManager.getInstance().playerB_region);
        TB_player4 = createPlayer(330, 500, TB_player4, ResourcesManager.getInstance().playerB_region);
        TB_player5 = createPlayer(360, 650, TB_player5, ResourcesManager.getInstance().playerB_region);


        this.attachChild(footballSprite);
        this.attachChild(TA_player1);
        this.attachChild(TA_player2);
        this.attachChild(TA_player3);
        this.attachChild(TA_player4);
        this.attachChild(TA_player5);

        this.attachChild(TB_player1);
        this.attachChild(TB_player2);
        this.attachChild(TB_player3);
        this.attachChild(TB_player4);
        this.attachChild(TB_player5);


    }

}
