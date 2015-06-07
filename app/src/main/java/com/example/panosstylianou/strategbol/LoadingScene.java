package com.example.panosstylianou.strategbol;

/**
 * Created by panosstylianou on 04/06/15.
 */
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import com.example.panosstylianou.strategbol.BaseScene;
import com.example.panosstylianou.strategbol.SceneManager.SceneType;


public class LoadingScene extends BaseScene
{
    @Override
    public void createScene()
    {
        setBackground(new Background(Color.WHITE));
        attachChild(new Text(240, 400, resourcesManager.font, "Loading...", vbom));
    }

    @Override
    public void onBackKeyPressed()
    {
        return;
    }

    @Override
    public SceneType getSceneType()
    {
        return SceneType.SCENE_LOADING;
    }

    @Override
    public void disposeScene()
    {

    }

}
