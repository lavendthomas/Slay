package be.ac.umons.slay.g02.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Locale;

import be.ac.umons.slay.g02.gui.screens.Menu;
import be.ac.umons.slay.g02.players.StatsLoader;

// gere les changements d'ecran
public class Main extends Game {
    public static Skin skinSgx;
    public static Skin skinSgxTable;
    public static Stage stage;

    public static I18NBundle lang;
    public static Preferences prefs;

    public static int SCREEN_HEIGHT;
    public static int SCREEN_WIDTH;
    public static final int VIRTUAL_WIDTH = 1920;
    public static final int VIRTUAL_HEIGHT = 1080;

    public static Sound soundButton1;
    public static Sound soundButton2;
    public static Sound soundButton3;

    public static OrthographicCamera camera;

    public static Pixmap pm;
    public static Cursor cursor;
    int xHotSpot;
    int yHotSpot;

    private static boolean isStatsLoad = false;
    public static ArrayList tabPlayers;

    @Override
    public void create() {
        // Language support
        FileHandle baseFileHandle = Gdx.files.internal("lang/Slay");
        Locale locale = new Locale("en", "UK", "VAR1");
        lang = I18NBundle.createBundle(baseFileHandle, locale);
        prefs = Gdx.app.getPreferences("Slay");

        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        SCREEN_WIDTH = Gdx.graphics.getWidth();

        skinSgx = new Skin(Gdx.files.internal("skins/sgx/sgx-ui.json"));
        skinSgxTable = new Skin(Gdx.files.internal("skins/sgx-table/sgx-ui.json"));

        soundButton1 = Gdx.audio.newSound(Gdx.files.internal("sounds/button_1.wav"));
        soundButton2 = Gdx.audio.newSound(Gdx.files.internal("sounds/button_2.wav"));
        soundButton3 = Gdx.audio.newSound(Gdx.files.internal("sounds/button_3.wav"));

        if (prefs.getBoolean("isFullScreenEnabled"))
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        prefs.putString("INCORRECT_PASSWORD", "Incorrect password");
        prefs.putString("USER_NAME_NOT_EXIST", "Username does not exist");
        prefs.putString("INCORRECT_LENGTH_PASSWORD", "Passwords must be at least 4 characters long");
        prefs.putString("INCORRECT_LENGTH_USER_NAME", "Usernames must be between 4 and 20 char.");
        prefs.putString("USER_NAME_NOT_AVAILABLE", "Username not available");
        prefs.putString("PASSWORDS_NOT_MATCH", "Passwords do not match");
        prefs.putString("USER_LOGGED", "User already logged");
        prefs.putString("NO_AVATAR", "Choose an avatar");
        prefs.putString("pathImageRobot", "profile/robot.jpg");
        prefs.putString("pathImageGirl", "profile/girl.jpg");
        prefs.putString("pathImagePanda", "profile/panda.jpg");
        prefs.putString("pathImagePink", "profile/pink.jpg");
        prefs.putString("pathImageSquid", "profile/squid.jpg");
        prefs.putString("pathImageBanana", "profile/banana.jpg");
        prefs.putString("pathImageBurger", "profile/burger.jpg");
        prefs.putString("pathImageBlue", "profile/blue.jpg");
        prefs.putString("pathImageMustache", "profile/mustache.jpg");
        prefs.putString("pathImagePenguin", "profile/penguin.jpg");
        prefs.putString("pathImageProfile", "profile/anonymous.png");
        prefs.putString("pathImageTmp", "profile/anonymous.png");
        prefs.putBoolean("isPlayer1Logged", false);
        prefs.putBoolean("isPlayer2Logged", false);
        prefs.putBoolean("isAccountEnabled", true);
        prefs.putInteger("playerRank", 0);
        prefs.putInteger("totalNumberPlayers", 0);
        prefs.putInteger("numPlayer", 0);

        prefs.flush();
        stage = new Stage(new ScreenViewport());
        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Changes the cursor appearance
        pm = new Pixmap(Gdx.files.internal("cursors/cursor.png"));
        xHotSpot = 0;
        yHotSpot = 0;
        cursor = Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot);
        Gdx.graphics.setCursor(cursor);
        Gdx.graphics.setTitle("");

        // Loads the hall of fame
        if (!isStatsLoad) {
            StatsLoader statsLoader = new StatsLoader();
            tabPlayers = statsLoader.createTab();
            isStatsLoad = true;
        }
        this.setScreen(new Menu(this));
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            skinSgx.getFont("title").getData().setScale(SCREEN_WIDTH * 0.8f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.8f / VIRTUAL_HEIGHT);
            skinSgxTable.getFont("font").getData().setScale(SCREEN_WIDTH * 1f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 1f / VIRTUAL_HEIGHT);
            skinSgxTable.getFont("title").getData().setScale(SCREEN_WIDTH * 0.9f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.9f / VIRTUAL_HEIGHT);
        }
        //stage.getViewport().setScreenBounds(0, 0, width, height);
        this.getScreen().resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}