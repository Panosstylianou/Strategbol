package com.example.panosstylianou.strategbol;

import android.content.SharedPreferences;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Mateusz Mysliwiec
 * @author www.matim-dev.com
 * @version 1.0
 */

public class ResourcesManager {

    //VARIABLES

    private static final ResourcesManager INSTANCE = new ResourcesManager();

    public Engine engine;
    public BaseActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    public Font font;
    public Music mMusic;
    public boolean musicOn = false;

    //TEXTURES
    private BitmapTextureAtlas splashTextureAtlas;
    private BuildableBitmapTextureAtlas menuTextureAtlas;
    private BuildableBitmapTextureAtlas tutorialTextureAtlas;
    private BuildableBitmapTextureAtlas gameTextureAtlas;

    //TEXTURE REGIONS
    public ITextureRegion splash_region;
    public ITextureRegion menu_background_region;
    public ITextureRegion tutorial_region;
    public ITextureRegion play_region;
    public ITextureRegion pitch_region;
    public ITextureRegion football_region;
    public ITextureRegion musicOn_region;
    public ITextureRegion musicOff_region;
    public ITextureRegion info_region;
    public ITextureRegion player_region;

    public void saveData() {
        SharedPreferences config = activity.getSharedPreferences("config", 0); //Create object of SharedPreferences.
        SharedPreferences.Editor editor = config.edit(); //Get Editor
        editor.putBoolean("music", musicOn); //Put value
        editor.commit(); //Commit edits
    }

    public void loadData() {
        SharedPreferences config = activity.getSharedPreferences("config", 0);
        musicOn = config.getBoolean("music", musicOn);
    }

    public void loadMenuResources() {
        loadData();
        loadMenuGraphics();

        if (font == null) {
            loadFonts();
        } else {
            return;
        }

        if (mMusic == null) {
            loadAudio();
        } else {
            return;
        }
    }

    public void loadGameResources() {
        loadGameGraphics();
    }

    public void loadTutorialResources() {
        loadTutorialGraphics();
    }

    private void loadTutorialGraphics() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/tutorial/");
        tutorialTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tutorialTextureAtlas, activity, "mainBackground.png");

        try {
            this.tutorialTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.tutorialTextureAtlas.load();
        } catch (final TextureAtlasBuilderException e) {
            Debug.e(e);
        }
    }

    private void loadFonts() {
        FontFactory.setAssetBasePath("Aller/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "AllerDisplay.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
        font.load();
    }

    private void loadAudio() {
        MusicFactory.setAssetBasePath("sound/");
        try {
            mMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "rockafellerSkank.mp3");
            mMusic.play();
            mMusic.setLooping(true);

        } catch (IOException e) {
            Debug.e(e.getMessage());
        }
    }

    private void loadMenuGraphics() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "mainBackground.png");
        play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "playBtn.png");
        tutorial_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "tutorialBtn.png");
        musicOn_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "musicOn.png");
        musicOff_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "musicOff.png");
        info_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "info.png");


        try {
            this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.menuTextureAtlas.load();
        } catch (final TextureAtlasBuilderException e) {
            Debug.e(e);
        }

    }

    private void loadGameGraphics() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
        gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

        pitch_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "pitch.png");
        football_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "football.png");
        player_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "player.png");

        try {
            this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.gameTextureAtlas.load();
        } catch (final TextureAtlasBuilderException e) {
            Debug.e(e);
        }
    }

    public void loadSplashScreen() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
        splashTextureAtlas.load();
    }

    public void unloadSplashScreen() {
        splashTextureAtlas.unload();
        splash_region = null;
    }

    public static void prepareManager(org.andengine.engine.Engine engine, BaseActivity activity, Camera camera, VertexBufferObjectManager vbom) {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }

    public static ResourcesManager getInstance() {
        return INSTANCE;
    }

    public void unloadMenuTextures() {
        menuTextureAtlas.unload();
        menu_background_region = null;
        play_region = null;
        tutorial_region = null;
        musicOn_region = null;
        musicOff_region = null;
        info_region = null;

        //font.unload();
        //font = null;

        //mMusic.stop();
        //mMusic.release();
        //mMusic = null;
    }

    public void unloadTutorialTextures() {
        tutorialTextureAtlas.unload();
        menu_background_region = null;

        //font.unload();
        //font = null;

        //mMusic.stop();
        //mMusic.release();
        //mMusic = null;
    }

    public void unloadGameTextures() {
        gameTextureAtlas.unload();
        football_region = null;
        pitch_region = null;
        player_region = null;

        //font.unload();
        //font = null;

        //mMusic.stop();
        //mMusic.release();
        //mMusic = null;
    }

}

