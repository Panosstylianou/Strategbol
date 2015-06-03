package com.example.panosstylianou.strategbol;

/**
 * Created by panosstylianou on 02/06/15.
 */

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
public class Player {

    public Rectangle plr;
    public static Player instance;
    Camera mCamera;
    boolean moveable;

    public static Player getSharedInstance() {
        if(instance == null)
            instance = new Player();
        return instance;
    }

    private Player() {

    }
}
