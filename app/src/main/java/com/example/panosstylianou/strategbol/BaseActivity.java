package com.example.panosstylianou.strategbol;

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
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOError;
import java.io.IOException;

/**
 * Created by panosstylianou on 02/06/15.
 */


public class BaseActivity extends BaseGameActivity
{

    private ResourcesManager resourcesManager;
    @Override
    public Engine onCreateEngine(EngineOptions pEngineOptions)
    {
        return new LimitedFPSEngine(pEngineOptions, 60);    //Create an Engine with a 60 second updates.
    }


    private Camera camera;
    public EngineOptions onCreateEngineOptions()
    {
        camera = new Camera(0,0,800,480);   //Set camera size 800x480
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(480,800), this.camera); //Set orientation Portrait
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
                // READ NEXT ARTICLE FOR THIS PART.
            }
        }));
        pOnPopulateSceneCallback.onPopulateSceneFinished();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        System.exit(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
        }
        return false;
    }

}
