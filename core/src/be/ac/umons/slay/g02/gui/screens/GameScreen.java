package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Level;
import be.ac.umons.slay.g02.level.LevelLoader;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Tile;
import be.ac.umons.slay.g02.level.TileSetManagement;

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
import static be.ac.umons.slay.g02.level.TileSetManagement.WHITE_HIGHLIGHT;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

// classe qui affiche l'interface pendant une partie
public class GameScreen implements Screen {

    private Stage stage;
    private Game game;

    private TiledMap map;
    private Playable level;
    private HexagonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private ImageButton buttonPause;
    private ImageButton buttonNext;
    private TextButton buttonResume;
    private TextButton buttonQuit;
    private ImageButton buttonL0;
    private ImageButton buttonL1;
    private ImageButton buttonL2;
    private ImageButton buttonL3;
    private boolean visibleL0;
    private boolean visibleL1;
    private boolean visibleL2;
    private boolean visibleL3;

    private static Window windowPause = new Window("Pause", skinSgx);
    private static Window windowQuit = new Window("Quit Game", skinSgx);

    private int tileW;
    private int tileH;
    private int size;
    private double errorOffset;

    private TiledMapTileLayer background;
    private TiledMapTileLayer territories;
    private TiledMapTileLayer entities;
    private TiledMapTileLayer effects;
    private TiledMapTileSet set;
    private Map<String, TiledMapTile> tileMap;

    private Stage hud;
    private OrthographicCamera hudCam;
    private InputMultiplexer multiplexer;

    ClickState click;
    Coordinate previousClick;
    private Coordinate coord1;
    private Coordinate coord2;
    private final int UNREAL = -1;
    private List<Coordinate> listMove = new ArrayList<Coordinate>();

    GameScreen(Game aGame) {
        game = aGame;
        coord1 = new Coordinate(UNREAL, UNREAL);
        coord2 = new Coordinate(UNREAL, UNREAL);
        click = ClickState.NOTHING_SELECTED;

        try {
            //Chargement de la map et du Level associé
            LevelLoader.Map lvlLoader = LevelLoader.load("g02_01 - Copie");
            level = lvlLoader.getLevel();
            map = lvlLoader.getMap();

            // Chargement des couches et du tileset
            background = (TiledMapTileLayer) map.getLayers().get("Background");
            territories = (TiledMapTileLayer) map.getLayers().get("Territories");
            entities = (TiledMapTileLayer) map.getLayers().get("Entities");

            effects = (TiledMapTileLayer) map.getLayers().get("Effects");

            set = map.getTileSets().getTileSet("tileset");

            tileMap = loadTileHashMap();
            //chargeLevel(level);

            // Chargement de la carte dans le renderer
            renderer = new HexagonalTiledMapRenderer(map);

            //Positionnement de la caméra selon la taille de la map
            MapProperties prop = map.getProperties();
            int nbreW = prop.get("width", Integer.class);
            int nbreH = prop.get("height", Integer.class);
            tileW = prop.get("tilewidth", Integer.class);
            tileH = prop.get("tileheight", Integer.class);
            size = prop.get("hexsidelength", Integer.class);
            errorOffset = size * sqrt(3) - round(size * sqrt(3));

            int worldW = nbreW / 2 * tileW + nbreW / 2 * tileW / 2;
            int worldH = nbreH * tileH + tileH / 2;
            camera = new OrthographicCamera();
            stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera));

            stage.getViewport().setWorldSize(worldW + tileH / 2, worldH);

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
            //stage.addActor(buttonPause);


            TextureRegionDrawable imageNext = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("levels/next.png"))));

            buttonNext = new ImageButton(imageNext);
            buttonNext.setSize(SCREEN_WIDTH * 4 / 100, SCREEN_HEIGHT * 6 / 100);
            buttonNext.setPosition((SCREEN_WIDTH - buttonNext.getWidth()) * 96 / 100 + SCREEN_WIDTH * 1 / 200, buttonNext.getHeight() - SCREEN_HEIGHT * 1 / 100);

            buttonNext.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    level.nextTurn();
                    soundButton3.play(0.1f);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        hudCam = new OrthographicCamera();
        hud = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, hudCam));

        //buttonNext.setBounds(buttonNext.getX(), buttonNext.getY() ,buttonNext.getWidth(), buttonNext.getHeight());

        hud.addActor(buttonNext);
        hud.addActor(buttonPause);

        // Add entity market

        TextureRegionDrawable imageL0 = new TextureRegionDrawable(new TextureRegion((new Texture(Gdx.files.internal("images/L0.png")))));
        buttonL0 = new ImageButton(imageL0);
        buttonL0.setSize(SCREEN_WIDTH * 4 / 100, SCREEN_HEIGHT * 6 / 100);
        buttonL0.setPosition((SCREEN_WIDTH - buttonNext.getWidth() - buttonL0.getWidth() ) * 96 / 100 + SCREEN_WIDTH * 1 / 200, buttonNext.getHeight() - SCREEN_HEIGHT * 1 / 100);
        buttonL0.setVisible(false);
        buttonL0.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton3.play(0.1f);
            }
        });
        hud.addActor(buttonL0);

        TextureRegionDrawable imageL1 = new TextureRegionDrawable(new TextureRegion((new Texture(Gdx.files.internal("images/L1.png")))));
        buttonL1 = new ImageButton(imageL1);
        buttonL1.setSize(SCREEN_WIDTH * 4 / 100, SCREEN_HEIGHT * 6 / 100);
        buttonL1.setPosition((SCREEN_WIDTH - buttonNext.getWidth() - buttonL0.getWidth() - buttonL1.getWidth() ) * 96 / 100 + SCREEN_WIDTH * 1 / 200, buttonNext.getHeight() - SCREEN_HEIGHT * 1 / 100);
        buttonL1.setVisible(false);
        buttonL1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton3.play(0.1f);
            }
        });
        hud.addActor(buttonL1);

        TextureRegionDrawable imageL2 = new TextureRegionDrawable(new TextureRegion((new Texture(Gdx.files.internal("images/L2.png")))));
        buttonL2 = new ImageButton(imageL2);
        buttonL2.setSize(SCREEN_WIDTH * 4 / 100, SCREEN_HEIGHT * 6 / 100);
        buttonL2.setPosition((SCREEN_WIDTH - buttonNext.getWidth() - buttonL0.getWidth() - buttonL1.getWidth() - buttonL2.getWidth() ) * 96 / 100 + SCREEN_WIDTH * 1 / 200, buttonNext.getHeight() - SCREEN_HEIGHT * 1 / 100);
        buttonL2.setVisible(false);
        buttonL2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton3.play(0.1f);
            }
        });
        hud.addActor(buttonL2);

        TextureRegionDrawable imageL3 = new TextureRegionDrawable(new TextureRegion((new Texture(Gdx.files.internal("images/L3.png")))));
        buttonL3 = new ImageButton(imageL3);
        buttonL3.setSize(SCREEN_WIDTH * 4 / 100, SCREEN_HEIGHT * 6 / 100);
        buttonL3.setPosition((SCREEN_WIDTH - buttonNext.getWidth() - buttonL0.getWidth() - buttonL1.getWidth() - buttonL2.getWidth() - buttonL3.getWidth() ) * 96 / 100 + SCREEN_WIDTH * 1 / 200, buttonNext.getHeight() - SCREEN_HEIGHT * 1 / 100);
        buttonL3.setVisible(false);
        buttonL3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton3.play(0.1f);
            }
        });
        hud.addActor(buttonL3);



        // Create multiplexer to handle input in stage and hud

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(new LevelGestureListener(this, camera)));
        multiplexer.addProcessor(hud);
        multiplexer.addProcessor(stage);


    }

    private void showPauseWindow() {
//      il faudra desactiver tout le background ici et le reactiver dans le bouton Resume
        disableButton(buttonPause, buttonNext);
        windowPause.clear();
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
                soundButton2.play(0.1f);
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

        hud.addActor(windowPause);
    }

    private void showWindowQuit() {
        disableButton(buttonResume, buttonQuit);
        windowQuit.clear();
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
                soundButton2.play(0.1f);
                stage.clear();
                game.setScreen(new LevelSelection(game));
            }
        });

        TextButton buttonNo = new TextButton("No", skinSgx, "big");
        buttonNo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);
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

        hud.addActor(windowQuit);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(103 / 255f, 173 / 255f, 244 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);

        stage.getViewport().update(SCREEN_WIDTH + tileH * 2 / 5, SCREEN_HEIGHT, true);
        hud.getViewport().update(SCREEN_WIDTH, SCREEN_HEIGHT, true);


        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            camera.zoom += 0.02;
        }
        loadLevel();
        renderer.render();

        stage.act();
        hud.act();

        stage.draw();
        hud.draw();


    }

    private Coordinate rectifyCoord() {
        Vector3 vect;
        int shift = (int) ((SCREEN_HEIGHT - Gdx.input.getY()) / size * errorOffset);
        vect = stage.getViewport().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY() + tileH - shift, 0));
        vect.set((int) (vect.x - (tileW / 2)), (int) (vect.y - (tileH / 2)), 0);
        return HexManagement.pixelToHex((int) vect.x, (int) vect.y, size);
    }

    public void onTap() {

        Coordinate clickPos = rectifyCoord();

        if (level.isInLevel(clickPos)) {
            Tile clickedTile = level.get(clickPos);
            // CHange state if needed
            switch (click) {
                case NOTHING_SELECTED:
                    if (clickedTile.getTerritory() != null && !(clickedTile.getEntity() instanceof Soldier) &&
                            clickedTile.getTerritory().getOwner().equals(level.getCurrentPlayer())) {
                        click = ClickState.ON_TERRITORY;
                    } else if (clickedTile.getTerritory() != null && clickedTile.getEntity() instanceof Soldier &&
                            clickedTile.getTerritory().getOwner().equals(level.getCurrentPlayer())) {
                        click = ClickState.ON_SOLDIER;
                    }
                    break;
                case ON_TERRITORY:
                    if (clickedTile.getEntity() instanceof Soldier) {
                        click = ClickState.ON_SOLDIER;
                    } else if (clickedTile.getTerritory() == null) {
                        click = ClickState.NOTHING_SELECTED;
                    }
                    break;
                case ON_SOLDIER:
                    // We clicked on a soldier then on a territory so the soldier should be moved
                    if (level.getMoves(previousClick, 4).contains(clickPos)) {
                        level.move(previousClick, clickPos);
                        click = ClickState.NOTHING_SELECTED;
                    } else {
                        click = ClickState.NOTHING_SELECTED;
                    }
                    break;
                case BUYING_UNIT:
                    break;
            }


            // Show effects
            EffectsManagement.eraseCells(effects);


            switch (click) {

                case NOTHING_SELECTED:
                    showMarket(clickPos, true);
                    break;

                case ON_TERRITORY:
                    // If we click on a territory but not on a soldier, tiles from that territory
                    // have to be highlighted and we show the market for this territory
                    List<Coordinate> listTerr = level.neighbourTilesInSameTerritory(clickPos);
                    EffectsManagement.highlightCells(effects, listTerr, tileMap.get("WHITE_HIGHLIGHT"));

                    showMarket(clickPos, false);
                    break;

                case ON_SOLDIER:
                    listMove = level.getMoves(clickPos, 4);
                    EffectsManagement.shadowMap(effects, level, set);
                    EffectsManagement.highlightCells(effects, listMove, tileMap.get("GREEN_HIGHLIGHT"));

                    showMarket(clickPos, true); // Move soldier -> No shop
                    break;

                case BUYING_UNIT:
                    break;
            }
        }

        previousClick = clickPos;
    }


    private void showMarket(Coordinate c, boolean hidden) {
        boolean shown = !hidden;
        List<String> canBuy = new ArrayList<String>();
        List<Entity> couldBuy = level.canBuy(c);
        if (couldBuy != null) {
            for (Entity e : level.canBuy(c)) {
                canBuy.add(e.getName());
            }
        }

        Gdx.app.log("", c.toString() + canBuy.toString());
        visibleL0 =  shown && canBuy.contains("L0");
        visibleL1 =  shown && canBuy.contains("L1");
        visibleL2 =  shown && canBuy.contains("L2");
        visibleL3 =  shown && canBuy.contains("L3");

        buttonL0.setVisible(visibleL0);
        buttonL1.setVisible(visibleL1);
        buttonL2.setVisible(visibleL2);
        buttonL3.setVisible(visibleL3);
    }

    /**
     * Reload the visual of the level according to the changes made in the logic
     */


    private void loadLevel() {
        for (int i = 0; i < level.width(); i++) {
            for (int j = 0; j < level.height(); j++) { // Parcours de chaque case du tableau de la partie logique
                Tile tile = level.get(i, j);
                if (tile.getEntity() != null) { // Si la case contient une entité, la rajouter à l'interface graphique
                    TiledMapTile image = tileMap.get(tile.getEntity().getName().toUpperCase());
                    HexManagement.drawTile(new Coordinate(i, j), image, entities);

                } else { // Il n'y a pas ou plus d'entité présente dans la case
                    if (entities.getCell(i, j) != null) { // Si la cellule n'est pas encore vide dans l'interface graphique, la vider
                        entities.setCell(i, j, new TiledMapTileLayer.Cell());

                    }
                }
                if (tile.getTerritory() != null) { // Si la case appartient à un territoire, le rajouter à l'interface graphique
                    TiledMapTile image = tileMap.get(tile.getTerritory().getOwner().getColor().getName());
                    HexManagement.drawTile(new Coordinate(i, j), image, territories);

                } else { // Il n'y a pas ou plus de territoire présent sur la case
                    if (territories.getCell(i, j) != null) { // Si la cellule n'est pas encore vide dans l'interface graphique, la vider
                        territories.setCell(i, j, new TiledMapTileLayer.Cell());

                    }
                }
            }
        }
    }

    /**
     * Load image of tile linked to their names
     *
     * @return Map of tile names and tile
     */

    private Map<String, TiledMapTile> loadTileHashMap() {
        List<String> namesList = TileSetManagement.getNames();
        HashMap<String, TiledMapTile> tileMap = new HashMap<String, TiledMapTile>();
        for (String name : namesList) {
            tileMap.put(name, set.getTile(TileSetManagement.fromName(name)));
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
        stage.dispose();
        hud.dispose();
    }


    enum ClickState {
        NOTHING_SELECTED,
        ON_TERRITORY,
        ON_SOLDIER,
        BUYING_UNIT;
    }
}

