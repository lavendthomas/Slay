package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sun.org.apache.xerces.internal.parsers.IntegratedParserConfiguration;

import java.util.HashMap;

import be.ac.umons.slay.g02.gui.Main;
import be.ac.umons.slay.g02.level.Level;
import be.ac.umons.slay.g02.level.LevelLoader;
import be.ac.umons.slay.g02.level.Tile;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_WIDTH;

// classe qui affiche l'interface pendant une partie
public class GameScreen implements Screen {

    private Stage stage;
    private Game game;

    private TiledMap map;
    private Level level;
    private HexagonalTiledMapRenderer renderer;
    OrthographicCamera camera;

    int x;
    int y;
    int w;
    int h;

    TiledMapTileLayer background;
    TiledMapTileLayer territories;
    TiledMapTileLayer entities;
    TiledMapTileSet set;


    public GameScreen(Game aGame) {

        game = aGame;
        stage = new Stage(new ScreenViewport());

        //Chargement de la map et du Level associé
        LevelLoader.Map lvlLoader = LevelLoader.load("g02_07");
        level = lvlLoader.getLevel();
        map = lvlLoader.getMap();

        MapProperties prop = map.getProperties();
        w = prop.get("width", Integer.class);
        h = prop.get ("height", Integer.class);


        background = (TiledMapTileLayer) map.getLayers().get("Background");
        territories = (TiledMapTileLayer) map.getLayers().get("Territories");
        entities = (TiledMapTileLayer) map.getLayers().get("Entities");
        set = map.getTileSets().getTileSet("tileset");

        renderer = new HexagonalTiledMapRenderer(map, 1);

        // bouton BACK
        TextButton buttonBack = new TextButton("Back",Main.skinMain);
        buttonBack.setPosition(Main.SCREEN_WIDTH/2-buttonBack.getWidth()/2-15,2*buttonBack.getHeight());
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.soundButton2.play(0.2f);
                game.setScreen(new Menu(game));
            }
        });
        background.getCell(x,y).setTile(set.getTile(1)); // temporaire

        stage.addActor(buttonBack);
        camera = new OrthographicCamera(w * 64, h * 64);
        camera.position.set((w * 64)/2, (h * 64)/2, 0);
        camera.update();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        stage.draw();
        stage.act();


        //récupère position clic gauche de souris
        if (Gdx.input.justTouched()) {
            x = Gdx.input.getX();
            y = Gdx.input.getY();
            System.out.println("x " + x + " y " + y);
        }

    }

    @Override
    public void resize(int width, int height) {
        Main.skinMain.getFont("button").getData().setScale(SCREEN_WIDTH*0.8f/VIRTUAL_WIDTH,SCREEN_HEIGHT*0.8f/VIRTUAL_HEIGHT);
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
//      cursor.dispose();
        map.dispose();
        Main.skinMain.dispose();
//      pm.dispose();
//      Main.soundButton1.dispose();
        Main.soundButton2.dispose();
        stage.dispose();
    }
}

