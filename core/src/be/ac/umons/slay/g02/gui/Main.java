package be.ac.umons.slay.g02.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import be.ac.umons.slay.g02.gui.screens.Menu;
import com.badlogic.gdx.scenes.scene2d.Stage;


// gere les changements d'ecran
public class Main extends Game {
    public static Skin skinRainbow;
    private Stage stage;

    public static int SCREEN_HEIGHT;
    public static int SCREEN_WIDTH;
    public static final int VIRTUAL_WIDTH = 1920;
    public static final int VIRTUAL_HEIGHT = 1080;

    public static Sound soundButton1;
    public static Sound soundButton2;

    @Override
    public void create() {
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        SCREEN_WIDTH = Gdx.graphics.getWidth();

        skinRainbow = new Skin(Gdx.files.internal("skins/rainbow/rainbow-ui.json"));

        soundButton1 = Gdx.audio.newSound(Gdx.files.internal("sounds/button_1.wav"));
        soundButton2 = Gdx.audio.newSound(Gdx.files.internal("sounds/button_2.wav"));

        stage = new Stage();

        this.setScreen(new Menu(this));
    }

    @Override
    public void dispose() {
        soundButton1.dispose();
        soundButton2.dispose();
        skinRainbow.dispose();
        stage.dispose();
    }

    @Override
    public void render() {
        // permet de passer d'un ecran a l'autre
        super.render();

        //      Gdx.gl.glClearColor(1,1,1,1);
        //     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


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