package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;

import be.ac.umons.slay.g02.gui.Main;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Level;
import be.ac.umons.slay.g02.level.LevelLoader;
import be.ac.umons.slay.g02.level.Tile;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

// classe qui affiche l'interface pendant une partie
public class GameScreen implements Screen, InputProcessor {

    private Stage stage;
    private Game game;

    private TiledMap map;
    private Level level;
    private HexagonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private int nbreW;
    private int nbreH;
    private int tileW;
    private int tileH;
    private int size;
    private int worldW;
    private int worldH;
    private double errorOffset;

    private TiledMapTileLayer background;
    private TiledMapTileLayer territories;
    private TiledMapTileLayer entities;
    private TiledMapTileSet set;
    private HashMap<String, TiledMapTile> tileMap;

    GameScreen(Game aGame) {
        game = aGame;

        Gdx.app.setLogLevel(Application.LOG_DEBUG); //TODO remove

        try {
            //Chargement de la map et du Level associé
            LevelLoader.Map lvlLoader = LevelLoader.load("g02_01");
            level = lvlLoader.getLevel();
            map = lvlLoader.getMap();

            // Chargement des couches et du tileset
            background = (TiledMapTileLayer) map.getLayers().get("Background");
            territories = (TiledMapTileLayer) map.getLayers().get("Territories");
            entities = (TiledMapTileLayer) map.getLayers().get("Entities");
            set = map.getTileSets().getTileSet("tileset");

            tileMap = chargeTileMap(set);
            //chargeLevel(level);

            // Chargement de la carte dans le renderer
            renderer = new HexagonalTiledMapRenderer(map);

            //Positionnement de la caméra selon la taille de la map
            MapProperties prop = map.getProperties();
            nbreW = prop.get("width", Integer.class);
            nbreH = prop.get("height", Integer.class);
            tileW = prop.get("tilewidth", Integer.class);
            tileH = prop.get("tileheight", Integer.class);
            size = prop.get("hexsidelength", Integer.class);
            errorOffset = size * sqrt(3) - round(size * sqrt(3));

            worldW = nbreW/2 * tileW + nbreW/2 * tileW/2;
            worldH = nbreH * tileH + tileH/2;
            camera = new OrthographicCamera();
            stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera));

            stage.getViewport().setWorldSize(worldW, worldH);
            stage.getViewport().setScreenPosition(worldW/2 , worldH/2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // bouton BACK
        TextButton buttonBack = new TextButton("Back", Main.skinMain);
        buttonBack.setPosition((int) (Main.SCREEN_WIDTH / 2) - buttonBack.getWidth() / 2 - 15, 2 * buttonBack.getHeight());
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.soundButton2.play(0.2f);
                game.setScreen(new Menu(game));
            }
        });
        stage.addActor(buttonBack);

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
        stage.getViewport().update(SCREEN_WIDTH, SCREEN_HEIGHT, true);

        stage.draw();
        stage.act();

        //récupère position clic gauche de souris

        Coordinate coord;

        if (Gdx.input.justTouched()) {
            int shift = (int) ((SCREEN_HEIGHT - Gdx.input.getY()) / size * errorOffset);
            Vector3 vect = stage.getViewport().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY() + tileH - shift, 0));
            vect.set((int) (vect.x - (tileW / 2)), (int) (vect.y - (tileH / 2)), 0);
            coord = HexManagement.pixelToHex((int) vect.x, (int) vect.y, size);
            Gdx.app.debug("slay", "1x " + coord.getX() + " 1y " + coord.getY());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            camera.zoom += 0.02;
        }
        chargeLevel(level);
        renderer.render();
    }

    public void chargeLevel (Level level) {
        for (int i = 0; i < level.getTileMap().length; i++) {
            for (int j = 0; j < level.getTileMap()[i].length; j++) {
                Tile tile =  level.getTileMap()[i][j];
                if (tile.getEntity() != null) {
                    TiledMapTile image = tileMap.get(tile.getEntity().getName());
                    HexManagement.drawTile(new Coordinate(i, j), image, entities);
                }
                if (tile.getTerritory() != null) {
                    TiledMapTile image = tileMap.get("hex_" + tile.getTerritory().getOwner().getColor().getName());
                    HexManagement.drawTile(new Coordinate(i, j), image, territories);
                }

            }
        }
    }

    // méthode ptr à améliorer
    public HashMap<String, TiledMapTile> chargeTileMap (TiledMapTileSet set) {
        String [] nameList = {"hex_green", "hex_red", "hex_darkgreen", "hex_pink", "hex_yellow", "hex_darkred", "hex_neutral", "hex_darkblue", "hex_water", "grave", "capital", "tree", "L0", "L1", "L2", "L3"};
        HashMap<String, TiledMapTile> tileMap = new HashMap<String, TiledMapTile> ();
        for (int i = 0; i < nameList.length; i ++) {
            tileMap.put(nameList[i], set.getTile(i+1));
        }
        return tileMap;
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

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        Main.skinMain.dispose();
        stage.dispose();
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

