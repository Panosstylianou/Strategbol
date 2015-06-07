package com.example.panosstylianou.strategbol;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
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

import java.io.IOException;

/**
 * @author Mateusz Mysliwiec
 * @author www.matim-dev.com
 * @version 1.0
 */
public class ResourcesManager
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------

    private static final ResourcesManager INSTANCE = new ResourcesManager();

    public ITextureRegion splash_region;
    private BitmapTextureAtlas splashTextureAtlas;

    public org.andengine.engine.Engine engine;
    public BaseActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;


    public ITextureRegion menu_background_region;
    public ITextureRegion play_region;
    public ITextureRegion options_region;

    public Font font;
    public Music mMusic;

    private BuildableBitmapTextureAtlas menuTextureAtlas;
    private BuildableBitmapTextureAtlas optionTextureAtlas;

    //Game Texture
    public BuildableBitmapTextureAtlas gameTextureAtlas;

    //Game Texture Regions
    public ITextureRegion player1_region;
    public ITextureRegion player2_region;
    public ITextureRegion player3_region;
    public ITextureRegion football_region;
    public ITextureRegion pitch_region;
    public ITextureRegion volume_region;
    public ITextureRegion difficulty_region;

    public ITiledTextureRegion player_region;

    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------

    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------

    public void loadMenuResources()
    {
        loadMenuGraphics();
        loadMenuAudio();
        loadMenuFonts();
    }

    public void loadGameResources()
    {
        loadGameGraphics();
        loadGameFonts();
        loadGameAudio();

    }

    public void loadOptionResources()
    {
        loadOptionGraphics();
        loadOptionAudio();
        loadOptionFonts();
    }

    private void loadOptionGraphics()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/option/");
        optionTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(optionTextureAtlas, activity, "mainBackground.png");
        volume_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(optionTextureAtlas, activity, "volumeCtr.png");
        difficulty_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(optionTextureAtlas, activity, "difficultyCtr.png");

        try
        {
            this.optionTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.optionTextureAtlas.load();
        }
        catch (final TextureAtlasBuilderException e)
        {
            Debug.e(e);
        }
    }

    private void loadOptionFonts()
    {
        FontFactory.setAssetBasePath("Aller/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "Aller_Rg.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
        font.load();
    }

    private void loadOptionAudio()
    {

    }

    private void loadMenuGraphics()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "mainBackground.png");
        play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "playBtn.png");
        options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "optionsBtn.png");

        try
        {
            this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.menuTextureAtlas.load();
        }
        catch (final TextureAtlasBuilderException e)
        {
            Debug.e(e);
        }

    }

    private void loadMenuFonts(){
        FontFactory.setAssetBasePath("Aller/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "Aller_Rg.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
        font.load();
    }

    private void loadMenuAudio()
    {
        MusicFactory.setAssetBasePath("sound/");
        try {
            mMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "shoeshine.mp3");
            mMusic.play();
            mMusic.setLooping(true);

        } catch (IOException e) {
            Debug.e(e.getMessage());
        }
    }

    private void loadGameGraphics()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
        gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

        pitch_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "pitch.png");
        player1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "player1.png");
        player2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "player1.png");
        player3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "player1.png");
        football_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "football.png");

        //player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "player.png", 3, 1);


        try
        {
            this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.gameTextureAtlas.load();
        }
        catch (final TextureAtlasBuilderException e)
        {
            Debug.e(e);
        }


    }

    private void loadGameFonts()
    {

    }

    private void loadGameAudio()
    {

    }

    public void loadSplashScreen()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
        splashTextureAtlas.load();

    }

    public void unloadSplashScreen()
    {
        splashTextureAtlas.unload();
        splash_region = null;

    }

    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(org.andengine.engine.Engine engine, BaseActivity activity, Camera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }

    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------

    public static ResourcesManager getInstance()
    {
        return INSTANCE;
    }

    public void unloadMenuTextures(){
        menuTextureAtlas.unload();
    }

    public void loadMenuTextures(){
        menuTextureAtlas.load();
    }

    public void unloadGameTextures(){
        //
    }



}

