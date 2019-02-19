package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import be.ac.umons.slay.g02.level.Level;
import be.ac.umons.slay.g02.level.LevelLoader;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.cursor;
import static be.ac.umons.slay.g02.gui.Main.pm;
import static be.ac.umons.slay.g02.gui.Main.skinMain;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;

// classe qui affiche l'interface pendant une partie
public class GameScreen implements Screen {
    private Stage stageGameScreen;
    private Game game;

    private TiledMap map;
    private HexagonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    private TiledMapTileSet tileset;

    public GameScreen(Game aGame) {
        game = aGame;
        stageGameScreen = new Stage(new ScreenViewport());

 /*       Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont fontRainbow = new BitmapFont(Gdx.files.internal("skins/rainbow/font-button-export.fnt"),
                Gdx.files.internal("skins/rainbow/rainbow-ui.png"), false);
        labelStyle.font = fontRainbow;
*/


        LevelLoader.Map m = LevelLoader.load("g02_01");
        Level level = m.getLevel();
        map = m.getMap();
        renderer = new HexagonalTiledMapRenderer(map, 1);
        tileset = map.getTileSets().getTileSet(0);



        MapProperties prop = map.getProperties();
        int w = prop.get("width", Integer.class);
        int h = prop.get ("height", Integer.class);

        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();


        camera = new OrthographicCamera(w * 64, h * 64);
        camera.position.set((w * 64)/2, (h * 64)/2, 0);
        camera.update();




        // bouton BACK
        TextButton buttonBack = new TextButton("Back", skinMain);
        buttonBack.setPosition(SCREEN_WIDTH/2-buttonBack.getWidth()/2,2*buttonBack.getHeight());
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                game.setScreen(new LevelSelection(game));
            }
        });

        stageGameScreen.addActor(buttonBack);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stageGameScreen);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        TiledMapTile tile = tileset.getTile(0);
        if (Gdx.input.justTouched()) {
            System.out.println(Gdx.input.getX());
            System.out.println(Gdx.input.getY());
        }

        stageGameScreen.draw();
        stageGameScreen.act();
    }

    @Override
    public void resize(int width, int height) {
        skinMain.getFont("button").getData().setScale(SCREEN_WIDTH*0.8f/VIRTUAL_WIDTH,SCREEN_HEIGHT*0.8f/VIRTUAL_HEIGHT);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        cursor.dispose();
        pm.dispose();
        soundButton1.dispose();
        soundButton2.dispose();
        skinMain.dispose();
        map.dispose();
        stageGameScreen.dispose();
    }
}

