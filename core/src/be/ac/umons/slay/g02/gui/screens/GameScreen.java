package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.ArrayList;
import java.util.HashMap;

import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Level;
import be.ac.umons.slay.g02.level.LevelLoader;
import be.ac.umons.slay.g02.level.Tile;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.cursor;
import static be.ac.umons.slay.g02.gui.Main.pm;
import static be.ac.umons.slay.g02.gui.Main.skinSgx;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.soundButton3;
import static be.ac.umons.slay.g02.gui.screens.Menu.disableButton;
import static be.ac.umons.slay.g02.gui.screens.Menu.enableButton;
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

    private ImageButton buttonPause;
    private ImageButton buttonNext;
    private TextButton buttonResume;
    private TextButton buttonQuit;

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
    private TiledMapTileLayer effects;
    private TiledMapTileSet set;
    private HashMap<String, TiledMapTile> tileMap;

    private Coordinate coord1;
    private Coordinate coord2;
    private final int UNREAL = -1;

    GameScreen(Game aGame) {
        game = aGame;

        coord1 = new Coordinate(UNREAL, UNREAL);
        coord2 = new Coordinate(UNREAL, UNREAL);

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

            effects = (TiledMapTileLayer) map.getLayers().get("Effects");

            set = map.getTileSets().getTileSet("tileset");

            tileMap = loadTileMap(set);
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

            worldW = nbreW / 2 * tileW + nbreW / 2 * tileW / 2;
            worldH = nbreH * tileH + tileH / 2;
            camera = new OrthographicCamera();
            stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera));

            stage.getViewport().setWorldSize(worldW, worldH);
            stage.getViewport().setScreenPosition(worldW / 2, worldH / 2);


            // boutons :

            TextureRegionDrawable imageDots = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("levels/dots.png"))));

            buttonPause = new ImageButton(imageDots);
            buttonPause.setSize(SCREEN_WIDTH * 2 / 100, SCREEN_HEIGHT * 5 / 100);
            buttonPause.setPosition((SCREEN_WIDTH - buttonPause.getWidth()) * 97 / 100, (SCREEN_HEIGHT - buttonPause.getHeight()) * 94 / 100);
            buttonPause.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundButton1.play(0.2f);
                    showPauseWindow();
                }
            });
            stage.addActor(buttonPause);

            TextureRegionDrawable imageNext = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("levels/next.png"))));

            buttonNext = new ImageButton(imageNext);
            buttonNext.setSize(SCREEN_WIDTH * 4 / 100, SCREEN_HEIGHT * 6 / 100);
            buttonNext.setPosition((SCREEN_WIDTH - buttonNext.getWidth()) * 96 / 100 + SCREEN_WIDTH * 1 / 200, buttonNext.getHeight() - SCREEN_HEIGHT * 1 / 100);
            buttonNext.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundButton3.play(0.2f);

                    //        methode pour terminer le tour du joueur
                }
            });
            stage.addActor(buttonNext);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPauseWindow() {
//      il faudra desactiver tout le background ici et le reactiver dans le bouton Resume
        disableButton(buttonPause, buttonNext);

        final Window windowPause = new Window("Pause", skinSgx);
        windowPause.setSize(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 3);
        windowPause.setPosition(SCREEN_WIDTH / 2 - windowPause.getWidth() / 2, SCREEN_HEIGHT / 2 - windowPause.getHeight() / 2);
        windowPause.setMovable(false);

        // place le titre de la fenetre au milieu
        windowPause.getTitleTable().padLeft(windowPause.getWidth() / 2 - windowPause.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        table.setDebug(true);
        windowPause.addActor(table);
        table.setFillParent(true);
        table.center();

        buttonResume = new TextButton("Resume", skinSgx, "big");
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                windowPause.remove();
                enableButton(buttonPause);
            }
        });

        buttonQuit = new TextButton("Quit Game", skinSgx, "big");
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                showWindowQuit();
            }
        });

        buttonResume.setWidth(windowPause.getWidth() * 40 / 100);

        buttonQuit.setWidth(windowPause.getWidth() * 40 / 100);

        windowPause.add(buttonResume).padBottom(windowPause.getWidth() * 7 / 100).fill().width(Value.percentWidth(1f));
        windowPause.row();
        windowPause.add(buttonQuit).fill().width(Value.percentWidth(1f));

        stage.addActor(windowPause);
    }

    private void showWindowQuit() {
        disableButton(buttonResume, buttonQuit);

        final Window windowQuit = new Window("Quit Game", skinSgx);
        windowQuit.setSize(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4);
        windowQuit.setPosition(SCREEN_WIDTH / 2 - windowQuit.getWidth() / 2, SCREEN_HEIGHT / 2 - windowQuit.getHeight() / 2);
        windowQuit.setMovable(false);

        // place le titre de la fenetre au milieu
        windowQuit.getTitleTable().padLeft(windowQuit.getWidth() / 2 - windowQuit.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowQuit.addActor(table);
        table.setFillParent(true);
        table.padTop(windowQuit.getHeight() * 17 / 100);

        Label labelQuitConfirm = new Label("Are you sure ?", skinSgx, "white");

        TextButton buttonYes = new TextButton("Yes", skinSgx, "big");
        buttonYes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                stage.clear();
                game.setScreen(new LevelSelection(game));
            }
        });

        TextButton buttonNo = new TextButton("No", skinSgx, "big");
        buttonNo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                windowQuit.remove();
                enableButton(buttonResume, buttonQuit);
            }
        });

        table.add(labelQuitConfirm).width(Value.percentWidth(1f)).colspan(2).padBottom(SCREEN_WIDTH / 3 * 8 / 100);
        table.row();
        buttonYes.setWidth(SCREEN_WIDTH / 3 * 20 / 100);
        buttonNo.setWidth(SCREEN_WIDTH / 3 * 20 / 100);
        table.add(buttonYes).padRight(windowQuit.getWidth() * 8 / 100).width(Value.percentWidth(1f));
        table.add(buttonNo).width(Value.percentWidth(1f));

        stage.addActor(windowQuit);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new GestureDetector(new LevelGestureListener(camera)));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(103 / 255f, 173 / 255f, 244 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        stage.getViewport().update(SCREEN_WIDTH, SCREEN_HEIGHT, true);

        stage.draw();
        stage.act();

        if (touchDown(SCREEN_WIDTH, SCREEN_HEIGHT, 0, 0)) {
            touchUp(SCREEN_WIDTH, SCREEN_HEIGHT, 0, 0);
        }
        Vector3 vect1;

        if (Gdx.input.justTouched()) {
            int shift = (int) ((SCREEN_HEIGHT - Gdx.input.getY()) / size * errorOffset);
            vect1 = stage.getViewport().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY() + tileH - shift, 0));
            vect1.set((int) (vect1.x - (tileW / 2)), (int) (vect1.y - (tileH / 2)), 0);
            ArrayList<Coordinate> list = new ArrayList<Coordinate>();

            //Si les 2 variables ont déjà été utilisé, les réinitialisé aux valeurs iréelles
            if (coord1.getX() >= 0 && coord2.getX() >= 0) {
                coord1.setX(UNREAL);
                coord2.setX(UNREAL);
            }

            //Si 1ere variable a été utilisé, stocké dans la seconde
            if (coord2.getX() < 0 && coord1.getX() >= 0) {
                coord2 = HexManagement.pixelToHex((int) vect1.x, (int) vect1.y, size);
                Gdx.app.debug("Click 2 ", "x : " + coord2.getX() + " y : " + coord2.getY());
                level.move(coord1, coord2);
                list = level.getMovePoss(coord1);
                for (Coordinate cur : list) {
                    HexManagement.eraseTile(cur, effects);
                }
            }

            //Si 1ere variable non encore utilisée, stocker dedans
            if (coord1.getX() < 0 && coord2.getX() < 0) {
                coord1 = HexManagement.pixelToHex((int) vect1.x, (int) vect1.y, size);
                Gdx.app.debug("Click 1 ", "x : " + coord1.getX() + " y : " + coord1.getY());
                list = level.getMovePoss(coord1);
                for (Coordinate cur : list) {
                    // Gdx.app.debug("Déplacements possibles ", "x : " + cur.getX() + " y : " + cur.getY());
                    HexManagement.drawTile(cur, set.getTile(17), effects);
                }
            }
            Gdx.app.debug("slay", "Coordinates: " + coord1 + " Tile: " + level.get(coord1) );
        }

        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            camera.zoom += 0.02;
        }
        loadLevel(level);
        renderer.render();
    }

    private void loadLevel(Level level) {
        for (int i = 0; i < level.getTileMap().length; i++) {
            for (int j = 0; j < level.getTileMap()[i].length; j++) {
                Tile tile = level.getTileMap()[i][j];
                if (tile.getEntity() != null) {
                    TiledMapTile image = tileMap.get(tile.getEntity().getName());
                    HexManagement.drawTile(new Coordinate(i, j), image, entities);
                } else {
                    if (entities.getCell(i, j) != null) {
                        entities.setCell(i, j, new TiledMapTileLayer.Cell());
                    }
                }
                if (tile.getTerritory() != null) {
                    TiledMapTile image = tileMap.get("hex_" + tile.getTerritory().getOwner().getColor().getName());
                    HexManagement.drawTile(new Coordinate(i, j), image, territories);
                } else {
                    if (territories.getCell(i, j) != null) {
                        territories.setCell(i, j, new TiledMapTileLayer.Cell());
                    }
                }
            }
        }
    }

    // méthode ptr à améliorer
    private HashMap<String, TiledMapTile> loadTileMap(TiledMapTileSet set) {
        String[] nameList = {"hex_green", "hex_red", "hex_darkgreen", "hex_pink", "hex_yellow", "hex_darkred", "hex_neutral", "hex_darkblue", "hex_water", "grave", "capital", "tree", "L0", "L1", "L2", "L3"};
        HashMap<String, TiledMapTile> tileMap = new HashMap<String, TiledMapTile>();
        for (int i = 0; i < nameList.length; i++) {
            tileMap.put(nameList[i], set.getTile(i + 1));
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
        cursor.dispose();
        pm.dispose();
        soundButton1.dispose();
        soundButton2.dispose();
        soundButton3.dispose();
        skinSgx.dispose();
        map.dispose();
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

