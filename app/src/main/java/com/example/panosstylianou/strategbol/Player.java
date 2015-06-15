package com.example.panosstylianou.strategbol;

/**
 * Created by panosstylianou on 02/06/15.
 */

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public abstract class Player extends AnimatedSprite {

    //CONSTRUCTOR
    public Player(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld) {
        super(pX, pY, ResourcesManager.getInstance().player_region, vbo);
        createPhysics(camera, physicsWorld);
        //camera.setChaseEntity(this);
    }

    //VARIABLES
    private Body body;
    private boolean canRun = false;
    private boolean reachedDestination = false;

    public abstract void onDie();
    public abstract void onReachedDestination();

    private void createPhysics(final Camera camera, PhysicsWorld physicsWorld) {
        body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyDef.BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
        body.setUserData("player");
        body.setFixedRotation(true);

        physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false) {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                super.onUpdate(pSecondsElapsed);
                camera.onUpdate(0.1f);

                if (reachedDestination = true) {
                    onReachedDestination();
                }

                if (canRun) {
                    body.setLinearVelocity(new Vector2(5, body.getLinearVelocity().y));
                }
            }
        });
    }

    public void setRunning() {
        canRun = true;
        final long[] PLAYER_ANIMATE = new long[]{100, 100, 100};
        animate(PLAYER_ANIMATE, 0, 2, true);
    }

    public void jump() {
        body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, 12));
    }

    public boolean setReachedDestination(boolean newValue) {
        reachedDestination = newValue;
        return reachedDestination;
    }

    public boolean getReachedDestination() {
        return reachedDestination;
    }

}
