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
    private Player player;
    private PhysicsWorld physicsWorld;

    private static final String TAG_ENTITY = "entity";
    private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
    private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
    private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";

    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_FOOTBALL = "football";
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER = "player";

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

    private void loadGameSprites() {

        final SimpleLevelLoader loader = new SimpleLevelLoader(vbom);

        loader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL) {   //The entity where all loaded entities are attached to
            public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException {
                return GameScene.this;
            }
        });

        loader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_ENTITY) {

            public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException {
                final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);
                final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
                final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);
                final Sprite object;

                //Parse XML files
                if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_FOOTBALL)) {

                    object = new Sprite(x, y, resourcesManager.football_region, vbom) {
                        @Override
                        protected void onManagedUpdate(float pSecondsElapsed) {
                            super.onManagedUpdate(pSecondsElapsed);
                            if (player.collidesWith(this)) {
                                this.setVisible(false);
                                this.setIgnoreUpdate(true);
                            }
                        }
                    };
                    object.registerEntityModifier(new LoopEntityModifier(new ScaleModifier(1, 1, 1.3f)));

                } else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER)) {

                    player = new Player(x, y, vbom, camera, physicsWorld) {
                        @Override
                        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                            if (pSceneTouchEvent.isActionDown()) {
                                this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
                            }
                            return true;
                        }
                    };
                    object = player;
                    registerTouchArea(object);
                    setTouchAreaBindingOnActionDownEnabled(true);

                } else {
                    throw new IllegalArgumentException();
                }
                object.setCullingEnabled(true);
                return object;
            }
        });
        loader.loadLevelFromAsset(activity.getAssets(), "level/1.xml");
    }

}
