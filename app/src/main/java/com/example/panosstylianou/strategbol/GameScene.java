package com.example.panosstylianou.strategbol;

/**
 * Created by panosstylianou on 04/06/15.
 */

import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
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

    private Sprite circle;
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

                int myEventAction = pSceneTouchEvent.getAction();
                float X = pSceneTouchEvent.getX();
                float Y = pSceneTouchEvent.getY();

                double distance = (X - circle.getX())*(X - circle.getX()) + (Y - circle.getY()) * (Y - circle.getY());
                distance = Math.sqrt(distance);

                switch (myEventAction) {
                    case MotionEvent.ACTION_DOWN:
                        circle.setPosition(this.getSceneCenterCoordinates()[0], this.getSceneCenterCoordinates()[1]);
                        circle.setVisible(true);
                        break;
                    case MotionEvent.ACTION_MOVE: {
                        if (distance < circle.getHeight() / 2) {
                            this.setPosition(X, Y);
                            break;
                        }
                    }
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        };
        registerTouchArea(player);
        setTouchAreaBindingOnActionDownEnabled(true);
        return player;
    }

    private Sprite createBall (float x, float y) {
        final Sprite ball;
        ball = new Sprite(x, y, resourcesManager.football_region, vbom) {
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
                for (int i = 1; i < GameScene.this.getChildCount() - 2; i++) { //Get Child Index (Ignore Pitch, Ball & Circle Sprite - index 0 & 11 & 12)
                    if (GameScene.this.getChildByIndex(i).collidesWith(this)) {

                        float x = GameScene.this.getChildByIndex(i).getX();
                        float yUp = GameScene.this.getChildByIndex(i).getY() + GameScene.this.getChildByIndex(i).getHeight()/2;
                        float yDown = GameScene.this.getChildByIndex(i).getY() - GameScene.this.getChildByIndex(i).getHeight()/2;

                        if (this.getY() - GameScene.this.getChildByIndex(i).getY() > 0) {
                            this.setPosition(x, yUp);
                        } else {
                            this.setPosition(x, yDown);
                        }
                    }
                }
            }
        };
        return ball;
    }

    private void loadGameSprites() {

        Sprite ballSprite;
        ballSprite = createBall(240, 400);

        circle = new Sprite(0, 0, resourcesManager.circle_region, vbom);
        circle.setVisible(false);

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

        this.attachChild(TA_player1); //Index 1
        this.attachChild(TA_player2); //Index 2
        this.attachChild(TA_player3); //Index 3
        this.attachChild(TA_player4); //Index 4
        this.attachChild(TA_player5); //Index 5

        this.attachChild(TB_player1); //Index 6
        this.attachChild(TB_player2); //Index 7
        this.attachChild(TB_player3); //Index 8
        this.attachChild(TB_player4); //Index 9
        this.attachChild(TB_player5); //Index 10

        this.attachChild(ballSprite); //Index 11
        this.attachChild(circle); //Index 12

    }

}
