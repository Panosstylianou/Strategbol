package com.example.panosstylianou.strategbol.Activities;

import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

/**
 * Created by panosstylianou on 02/06/15.
 */
public class PlayActivity extends BaseGameActivity implements IOnSceneTouchListener
{

    //CLASS VARIABLES/OBJECTS


    //CLASS METHODS
    @Override
    public EngineOptions onCreateEngineOptions()
    {
        return null;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException, Exception
    {

    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException, Exception
    {

    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
    {

    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
    {
        return false;
    }
}
