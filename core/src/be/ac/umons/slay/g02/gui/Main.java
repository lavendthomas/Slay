package be.ac.umons.slay.g02.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import be.ac.umons.slay.g02.gui.screens.Menu;

// gere les changements d'ecran
public class Main extends Game {
    public static Skin skinSgx;
    public static Skin skinSgxTable;
    public static Stage stage;

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
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        SCREEN_WIDTH = Gdx.graphics.getWidth();

        skinSgx = new Skin(Gdx.files.internal("skins/sgx/sgx-ui.json"));
        skinSgxTable = new Skin(Gdx.files.internal("skins/sgx-table/sgx-ui.json"));

        soundButton1 = Gdx.audio.newSound(Gdx.files.internal("sounds/button_1.wav"));
        soundButton2 = Gdx.audio.newSound(Gdx.files.internal("sounds/button_2.wav"));
        soundButton3 = Gdx.audio.newSound(Gdx.files.internal("sounds/button_3.wav"));

        stage = new Stage(new ScreenViewport());
        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // change cursor aspect
        pm = new Pixmap(Gdx.files.internal("cursors/cursor_2.png"));
        // x = pm.getWidth()/2 et y = pm.getHeight()/2 si on veut que ca pointe au centre du curseur
        // = 0 ca pointe au bout de la fleche
        xHotSpot = 0;
        yHotSpot = 0;
        cursor = Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot);
        Gdx.graphics.setCursor(cursor);


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


        this.setScreen(new Menu(this));
    }

    @Override
    public void dispose() {
        cursor.dispose();
        pm.dispose();
        soundButton1.dispose();
        soundButton2.dispose();
        skinSgx.dispose();
        stage.dispose();
    }

    @Override
    public void render() {
        // permet de passer d'un ecran a l'autre
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        skinSgx.getFont("title").getData().setScale(SCREEN_WIDTH * 0.8f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.8f / VIRTUAL_HEIGHT);
        // fixe le probleme de decalage en fenetre reduite
        stage.getViewport().setScreenBounds(0, 0, width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}