package com.example.panosstylianou.strategbol;

/**
 * Created by panosstylianou on 02/06/15.
 */

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public abstract class Player extends Sprite {

    //CONSTRUCTOR
    public Player(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld) {
        super(pX, pY, ResourcesManager.getInstance().player_region, vbom);
        createPhysics(camera, physicsWorld);
        //camera.setChaseEntity(this);
    }

    //VARIABLES
    private Body body;
    private boolean canRun = true;
    private int energy = 100;

    public abstract boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY);

    private void createPhysics(final Camera camera, PhysicsWorld physicsWorld) {
        body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyDef.BodyType.KinematicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
        body.setUserData("player");
        body.setFixedRotation(true);

        physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false) {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                super.onUpdate(pSecondsElapsed);
                camera.onUpdate(0.1f);

                if (onReachDestination()) {
                    //TODO
                }

                if (canRun) {
                    //TODO
                }
            }
        });
    }

    public void hasBall() {
        changePlayerEnergy(-10);
        //TODO
    }

    public void kick() {
        changePlayerEnergy(-25);
        //TODO
    }

    public void dribble() {
        changePlayerEnergy(-25);
        //TODO
    }

    public void tackle() {
        changePlayerEnergy(-15);
        //TODO
    }

    public int changePlayerEnergy(int newEnergy) {
        if ((energy - newEnergy) < 0) {
            //TODO
        } else {
            energy = energy + newEnergy;
        }
        return energy;
    }

    public void run() {
        Vector2 velocity = new Vector2(body.getLinearVelocity().x, 12); //Velocity Vector
        body.setLinearVelocity(velocity);
    }

    public boolean onReachDestination() {
        canRun = false; //Player cannot run for the next 2 turns.
        return true;
    }

}
