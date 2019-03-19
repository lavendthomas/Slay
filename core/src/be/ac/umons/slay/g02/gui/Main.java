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


    // booleen temporaire, faudra peut-etre le mettre dans une autre classe, true par defaut
    public static boolean isAccountEnabled = true;


    @Override
    public void create() {
        // Language support

        FileHandle baseFileHandle = Gdx.files.internal("lang/Slay");
        Locale locale = new Locale("en", "UK", "VAR1");
        lang = I18NBundle.createBundle(baseFileHandle, locale);

        prefs = Gdx.app.getPreferences("Slay");
        isAccountEnabled = prefs.getBoolean("isAccountEnabled", true);

        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        SCREEN_WIDTH = Gdx.graphics.getWidth();

        if (prefs.getBoolean("isFullScreenEnabled", false)) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }

        skinSgx = new Skin(Gdx.files.internal("skins/sgx/sgx-ui.json"));
        skinSgxTable = new Skin(Gdx.files.internal("skins/sgx-table/sgx-ui.json"));

        soundButton1 = Gdx.audio.newSound(Gdx.files.internal("sounds/button_1.wav"));
        soundButton2 = Gdx.audio.newSound(Gdx.files.internal("sounds/button_2.wav"));
        soundButton3 = Gdx.audio.newSound(Gdx.files.internal("sounds/button_3.wav"));

        stage = new Stage(new ScreenViewport());
        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // change cursor aspect
        pm = new Pixmap(Gdx.files.internal("cursors/cursor.png"));
        // x = pm.getWidth()/2 et y = pm.getHeight()/2 si on veut que ca pointe au centre du curseur
        // = 0 ca pointe au bout de la fleche
        xHotSpot = 0;
        yHotSpot = 0;
        cursor = Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot);
        Gdx.graphics.setCursor(cursor);
        Gdx.graphics.setTitle("");

   /*
    // si besoin d'un labelstyle
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("skins/sgx/font-title-export.fnt"),
                Gdx.files.internal("skins/sgx/sgx-ui.png"), false);
        labelStyle.font = myFont;*/


   /* NOTE : on peut changer la couleur des labels en faisant :
       nomLabel.setColor(Color.  );
       et pour les labels dans les boutons :
       nomBouton.getLabel().setColor(Color.  );
       et le fond des boutons :
       nomBouton.setColor(valeur/255f, valeur/255f, valeur/255f, 1); --> les valeurs sont des int
   */

        // load Hall Of Fame (ca doit etre fait dans le menu)
        if (!isStatsLoad) {
            StatsLoader statsLoader = new StatsLoader();
            tabPlayers = statsLoader.createTab();
            isStatsLoad = true;
        }


        this.setScreen(new Menu(this));

        //     menu = new Menu(this);
        //     setScreen(menu);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {
        // permet de passer d'un ecran a l'autre
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        SCREEN_HEIGHT = height;
        SCREEN_WIDTH = width;
        skinSgx.getFont("title").getData().setScale(SCREEN_WIDTH * 0.8f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.8f / VIRTUAL_HEIGHT);
        skinSgxTable.getFont("font").getData().setScale(SCREEN_WIDTH * 1f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 1f / VIRTUAL_HEIGHT);
        skinSgxTable.getFont("title").getData().setScale(SCREEN_WIDTH * 0.9f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.9f / VIRTUAL_HEIGHT);
        // fixe le probleme de decalage en fenetre reduite
        stage.getViewport().setScreenBounds(0, 0, width, height);



/*      // ne change pas la taille de la fenetre et ca bug (mais ca garde le ratio)

        Vector2 size = Scaling.fit.apply(1920, 1080, width, height);
        int viewportX = (int)(width - size.x) / 2;
        int viewportY = (int)(height - size.y) / 2;
        int viewportWidth = (int)size.x;
        int viewportHeight = (int)size.y;
        Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
*/
        this.getScreen().resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}