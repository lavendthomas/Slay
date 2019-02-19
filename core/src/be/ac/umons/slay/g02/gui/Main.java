package be.ac.umons.slay.g02.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import be.ac.umons.slay.g02.gui.screens.Menu;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

// gere les changements d'ecran
public class Main extends Game {
    public static Skin skinMain;
    public static Stage stage;

    public static int SCREEN_HEIGHT;
    public static int SCREEN_WIDTH;
    public static final int VIRTUAL_WIDTH = 1920;
    public static final int VIRTUAL_HEIGHT = 1080;

    public static Sound soundButton1;
    public static Sound soundButton2;

    public static OrthographicCamera camera;

    public static Pixmap pm;
    public static Cursor cursor;
    int xHotSpot;
    int yHotSpot;

    @Override
    public void create() {
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        SCREEN_WIDTH = Gdx.graphics.getWidth();

        skinMain = new Skin(Gdx.files.internal("skins/rainbow-raw/rainbow-ui.json"));

        soundButton1 = Gdx.audio.newSound(Gdx.files.internal("sounds/button_1.wav"));
        soundButton2 = Gdx.audio.newSound(Gdx.files.internal("sounds/button_2.wav"));

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

        this.setScreen(new Menu(this));
    }

    @Override
    public void dispose() {
        cursor.dispose();
        pm.dispose();
        soundButton1.dispose();
        soundButton2.dispose();
        skinMain.dispose();
        stage.dispose();
    }

    @Override
    public void render() {
        // permet de passer d'un ecran a l'autre
        super.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}