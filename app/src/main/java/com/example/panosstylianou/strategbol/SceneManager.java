package com.example.panosstylianou.strategbol;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface;

public class SceneManager {

    //SCENES
    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene loadingScene;
    private BaseScene tutorialScene;

    //VARIABLES
    private static final SceneManager INSTANCE = new SceneManager();
    private SceneType currentSceneType = SceneType.SCENE_SPLASH;
    private BaseScene currentScene;
    private Engine engine = ResourcesManager.getInstance().engine;

    public enum SceneType {
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_GAME,
        SCENE_LOADING,
        SCENE_TUTORIAL,
    }

    public void setScene(BaseScene scene) {
        engine.setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
    }

    public void createSplashScene(IGameInterface.OnCreateSceneCallback pOnCreateSceneCallback) {
        ResourcesManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }

    private void disposeSplashScene() {
        ResourcesManager.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public SceneType getCurrentSceneType() {
        return currentSceneType;
    }

    public BaseScene getCurrentScene() {
        return currentScene;
    }

    public void createMenuScene() {   //Initialize Menu Scene and load resources
        ResourcesManager.getInstance().loadMenuResources();
        menuScene = new MainMenuScene();
        setScene(menuScene);
        disposeSplashScene();
    }

    public void loadGameScene(final Engine mEngine) //Load Game Resources while displaying Loading Scene
    {
        loadingScene = new LoadingScene();
        setScene(loadingScene);

        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameResources();
                gameScene = new GameScene();
                setScene(gameScene);
                //gameScene.setOnSceneTouchListener(this);
            }
        }));
    }

    public void loadMenuScene(final Engine mEngine) {
        loadingScene = new LoadingScene();
        setScene(loadingScene);

        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadMenuResources();
                menuScene = new MainMenuScene();
                setScene(menuScene);
            }
        }));
    }

    public void loadTutorialScene(final Engine mEngine) //Load Tutorial Resources
    {
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadTutorialResources();
                tutorialScene = new TutorialScene();
                setScene(tutorialScene);
            }
        }));
    }

}
