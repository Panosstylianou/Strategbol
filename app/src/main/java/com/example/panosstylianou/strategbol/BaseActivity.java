package com.example.panosstylianou.strategbol;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;

import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOError;
import java.io.IOException;
import com.example.panosstylianou.strategbol.ResourcesManager;
import com.example.panosstylianou.strategbol.SceneManager;

/**
 * Created by panosstylianou on 02/06/15.
 */


public class BaseActivity extends BaseGameActivity implements IOnSceneTouchListener
{

    private ResourcesManager resourcesManager;
    @Override
    public Engine onCreateEngine(EngineOptions pEngineOptions) {
        return new LimitedFPSEngine(pEngineOptions, 60);    //Create an Engine with a 60 second updates.
    }

    //int width = this.getResources().getDisplayMetrics().widthPixels;
    //int height = this.getResources().getDisplayMetrics().heightPixels;

    private Camera camera;

    public EngineOptions onCreateEngineOptions()
    {
        camera = new Camera(0, 0, 480, 800);   //Set camera size 800x480
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), this.camera); //Set orientation Portrait
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);    //Set audio
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;

    }

    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException //Initialize resources Manager and pass the parameters
    {
        ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
        resourcesManager = ResourcesManager.getInstance();
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException
    {
        SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);

    }

    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
    {
        mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback()
        {
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                SceneManager.getInstance().createMenuScene();

                // load menu resources, create menu scene
                // set menu scene using scene manager
                // disposeSplashScene();
            }
        }));
        pOnPopulateSceneCallback.onPopulateSceneFinished();

    }
/*
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //super.onDestroy();
        System.exit(0);
    }
*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
        }
        return false;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        return true;
    }
}
