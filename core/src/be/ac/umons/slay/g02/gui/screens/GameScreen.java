package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.HashMap;
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
import be.ac.umons.slay.g02.players.Player;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.prefs;
import static be.ac.umons.slay.g02.gui.Main.skinSgx;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.soundButton3;

// classe qui affiche l'interface pendant une partie
public class GameScreen implements Screen {
    private Game game;

    private static Playable level;
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
    private ImageButton buttonChest;

    private static Window windowPause = new Window("Pause", skinSgx);
    private static Window windowQuit = new Window("Quit Game", skinSgx);

    private Label labelCoins;
    private Label labelIncome;


    // pour les tests
    private Label labelWages;


    private int tileW;
    private int tileH;
    private int size;
    private double errorOffset;

    private TiledMapTileLayer territories;
    private TiledMapTileLayer entities;
    private TiledMapTileLayer effects;
    private TiledMapTileSet set;
    private Map<String, TiledMapTile> tileMap;

    private Stage hud;
    private OrthographicCamera hudCam;
    private InputMultiplexer multiplexer;

    private ClickState click;
    private Coordinate previousClick;
    private Entity boughtEntity;
    private Viewport viewport;


    private int translateX;
    private int translateY;
    private int nbreH;

    GameScreen(Game aGame, String levelName) {
        game = aGame;
        click = ClickState.NOTHING_SELECTED;
        windowPause.setVisible(false);

        try {
            camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            LevelLoader.Map lvlLoader = LevelLoader.load(levelName);
            level = lvlLoader.getLevel();
            TiledMap map = lvlLoader.getMap();
            renderer = new HexagonalTiledMapRenderer(map);
            camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

            MapProperties prop = map.getProperties();
            int nbreW = prop.get("width", Integer.class);
            nbreH = prop.get("height", Integer.class);
            tileW = prop.get("tilewidth", Integer.class);
            tileH = prop.get("tileheight", Integer.class);
            size = prop.get("hexsidelength", Integer.class);


            // Chargement des couches utiles et du tileset
            territories = (TiledMapTileLayer) map.getLayers().get("Territories");
            entities = (TiledMapTileLayer) map.getLayers().get("Entities");
            effects = (TiledMapTileLayer) map.getLayers().get("Effects");

            set = map.getTileSets().getTileSet("tileset");

            tileMap = loadTileHashMap();



            int worldW;
            int worldH = nbreH * tileH + tileH / 2;

            if (nbreW % 2 == 0) {
                worldW = ((nbreW / 2) * tileW) + ((nbreW / 2) * (tileW / 2));
            } else {
                worldW = (int) (Math.ceil(nbreW / 2f) * tileW + Math.floor(nbreW / 2f) * (tileW / 2));
            }

            viewport = new FitViewport(worldW, worldH, camera);

            int midScreenW = SCREEN_WIDTH / 2;
            int midScreenH = SCREEN_HEIGHT / 2;
            translateX = midScreenW - (int)  (worldW / 2);
            translateY = midScreenH - (int)  (worldH / 2);

            camera.translate(-translateX, -translateY, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        loadButtons();

    }

    private void handleInput() { //TODO Bloquer dépassements (trop zoom, trop à gauche ...)
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            translateX -= 5;
            camera.translate(-5, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            translateX += 5;
            camera.translate(5, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            translateY -=5;
            camera.translate(0, -5, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            translateY += 5;
            camera.translate(0, 5, 0);
        }

    }


    private void showPauseWindow() {
        windowPause.clear();
        windowPause.setSize(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 3);
        windowPause.setPosition(SCREEN_WIDTH / 2 - windowPause.getWidth() / 2, SCREEN_HEIGHT / 2 - windowPause.getHeight() / 2);
        windowPause.setMovable(false);
        // place le titre de la fenetre au milieu
        windowPause.getTitleTable().padLeft(windowPause.getWidth() / 2 - windowPause.getTitleLabel().getWidth() / 2);
        windowPause.setVisible(true);

        Table table = new Table();
        table.setDebug(true);
        windowPause.addActor(table);
        table.setFillParent(true);
        table.center();

        buttonResume = new TextButton("Resume", skinSgx, "big");
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                windowPause.remove();
                windowPause.setVisible(false);
            }
        });
        buttonQuit = new TextButton("Quit Game", skinSgx, "big");
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
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
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                game.setScreen(new LevelSelection(game));
            }
        });

        TextButton buttonNo = new TextButton("No", skinSgx, "big");
        buttonNo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                windowQuit.remove();
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

        loadLevel();
        handleInput();
        camera.update();
        Gdx.gl.glClearColor(103 / 255f, 173 / 255f, 244 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();

        hud.getViewport().update(SCREEN_WIDTH, SCREEN_HEIGHT, true);

        hud.act();
        hud.draw();
    }

    private Coordinate getCoordinate() {
        Vector2 vect = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        // Bien mettre le système d'axe
        vect.set((int) (vect.x - (tileW / 2)), (int) (vect.y - (tileH)));
        return HexManagement.pixelToHex((int) vect.x, (int) vect.y, size);
    }

    void onTap() { // TODO Bloquer quand click sur territoire IA
        if (!windowPause.isVisible()) {
            Coordinate clickPos = getCoordinate();
            if (level.isInLevel(clickPos)) {
                Tile clickedTile = level.get(clickPos);
                // Change state if needed
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
                        if (clickedTile.getTerritory() == null
                                || !(level.get(clickPos).getTerritory().getOwner().equals(level.getCurrentPlayer()))) {

                            click = ClickState.NOTHING_SELECTED;
                        } else if (clickedTile.getEntity() instanceof Soldier) {
                            click = ClickState.ON_SOLDIER;
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
                        if (level.getMoves(boughtEntity, previousClick).contains(clickPos)) {
                            level.buy(boughtEntity, previousClick, clickPos);
                        }
                        click = ClickState.NOTHING_SELECTED;
                        break;
                }

                showEffects(clickPos);
            }

            previousClick = clickPos;
        }
    }

    private void showEffects(Coordinate clickPos) {
        // Show effects
        EffectsManagement.eraseCells(effects);

        switch (click) {

            case NOTHING_SELECTED:
                showMarket(clickPos, true);
                showCoins(clickPos, true);
                break;

            case ON_TERRITORY:
                // If we click on a territory but not on a soldier, tiles from that territory
                // have to be highlighted and we show the market for this territory
                if (level.get(clickPos).getTerritory().getOwner().equals(level.getCurrentPlayer())) {
                    List<Coordinate> listTerr = level.neighbourTilesInSameTerritory(clickPos);
                    EffectsManagement.highlightCells(effects, listTerr, tileMap.get("WHITE_HIGHLIGHT"));

                    showMarket(clickPos, false);
                    showCoins(clickPos, false);
                    break;
                } else {
                    showMarket(clickPos, true);
                    showCoins(clickPos, true);
                }
            case ON_SOLDIER:
                List<Coordinate> listMove = level.getMoves(clickPos, 4);
                EffectsManagement.shadowMap(effects, level, set);
                EffectsManagement.highlightCells(effects, listMove, tileMap.get("GREEN_HIGHLIGHT"));
                showMarket(clickPos, true); // Move soldier -> No shop
                showCoins(clickPos, false);
                break;

            case BUYING_UNIT:
                List<Coordinate> listBuyable = level.getMoves(boughtEntity, previousClick);
                EffectsManagement.shadowMap(effects, level, set);
                EffectsManagement.highlightCells(effects, listBuyable, tileMap.get("GREEN_HIGHLIGHT"));
                break;
        }
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
        boolean isVisibleL0 = shown && canBuy.contains("L0");
        boolean isVisibleL1 = shown && canBuy.contains("L1");
        boolean isVisibleL2 = shown && canBuy.contains("L2");
        boolean isVisibleL3 = shown && canBuy.contains("L3");

        changeMarketDisplay(shown, isVisibleL0, buttonL0);
        changeMarketDisplay(shown, isVisibleL1, buttonL1);
        changeMarketDisplay(shown, isVisibleL2, buttonL2);
        changeMarketDisplay(shown, isVisibleL3, buttonL3);
    }

    private void changeMarketDisplay(boolean shown, boolean isVisibleL, ImageButton buttonL) {
        if (isVisibleL) {
            buttonL.getImage().clearActions();
            buttonL.getImage().addAction(Actions.color(Color.WHITE));
            buttonL.setTouchable(Touchable.enabled);
        } else {
            buttonL.getImage().addAction(Actions.color(Color.DARK_GRAY));
            buttonL.setTouchable(Touchable.disabled);
        }
        buttonL.setVisible(shown);
    }

    /**
     * Display the coins of the selected territory and its income
     *
     * @param c
     * @param hidden
     */
    private void showCoins(Coordinate c, boolean hidden) {
        if (hidden) {
            buttonChest.setVisible(false);
            labelCoins.setVisible(false);
            labelIncome.setVisible(false);

            // pour les tests
            labelWages.setVisible(false);
        } else {
            buttonChest.setVisible(true);
            labelCoins.setVisible(true);
            labelIncome.setVisible(true);
            labelCoins.setText("" + level.get(c).getTerritory().getCoins());
            labelIncome.setText("+ " + level.get(c).getTerritory().getIncome());

            // pour les tests
            labelWages.setVisible(true);
            labelWages.setText("- " + level.get(c).getTerritory().getWages());
        }
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
        viewport.setScreenBounds(0, 0, width, height);
        hud.getViewport().setScreenBounds(0, 0, width, height);
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
        hud.dispose();
    }

    private void loadButtons() {

        // Add buttons Pause and Next

        TextureRegionDrawable imageDots = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/dots.png"))));
        buttonPause = new ImageButton(imageDots);
        buttonPause.setSize(SCREEN_WIDTH * 2 / 100, SCREEN_HEIGHT * 5 / 100);
        buttonPause.setPosition((SCREEN_WIDTH - buttonPause.getWidth()) * 97 / 100, (SCREEN_HEIGHT - buttonPause.getHeight()) * 94 / 100);
        buttonPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!windowPause.isVisible()) {
                    soundButton1.play(prefs.getFloat("volume", 0.2f));
                    showPauseWindow();
                }
            }
        });
        TextureRegionDrawable imageNext = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/next.png"))));
        buttonNext = new ImageButton(imageNext);
        buttonNext.setSize(SCREEN_WIDTH * 4 / 100, SCREEN_HEIGHT * 6 / 100);
        buttonNext.setPosition((SCREEN_WIDTH - buttonNext.getWidth()) * 96 / 100 + SCREEN_WIDTH * 1 / 200, buttonNext.getHeight() / 2);
        buttonNext.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!windowPause.isVisible()) {
                    level.nextTurn();
                    click = ClickState.NOTHING_SELECTED;
                    EffectsManagement.eraseCells(effects);
                    soundButton3.play(prefs.getFloat("volume", 0.1f));
                }
            }
        });

        hudCam = new OrthographicCamera();
        hud = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, hudCam));

        // Add entity market

        TextureRegionDrawable imageL0 = new TextureRegionDrawable(new TextureRegion((new Texture(Gdx.files.internal("images/L0.png")))));
        buttonL0 = new ImageButton(imageL0);
        buttonL0.getImage().setScale(2.5f);
        buttonL0.setVisible(false);
        buttonL0.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!windowPause.isVisible()) {
                    soundButton3.play(prefs.getFloat("volume", 0.1f));
                    boughtEntity = new Soldier(SoldierLevel.L0);
                    click = ClickState.BUYING_UNIT;
                    showEffects(previousClick);
                }
            }
        });

        TextureRegionDrawable imageL1 = new TextureRegionDrawable(new TextureRegion((new Texture(Gdx.files.internal("images/L1.png")))));
        buttonL1 = new ImageButton(imageL1);
        buttonL1.getImage().setScale(2.5f);
        buttonL1.setVisible(false);
        buttonL1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!windowPause.isVisible()) {
                    soundButton3.play(prefs.getFloat("volume", 0.1f));
                    boughtEntity = new Soldier(SoldierLevel.L1);
                    click = ClickState.BUYING_UNIT;
                    showEffects(previousClick);
                }
            }
        });

        TextureRegionDrawable imageL2 = new TextureRegionDrawable(new TextureRegion((new Texture(Gdx.files.internal("images/L2.png")))));
        buttonL2 = new ImageButton(imageL2);
        buttonL2.getImage().setScale(2.5f);
        buttonL2.setVisible(false);
        buttonL2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!windowPause.isVisible()) {
                    soundButton3.play(prefs.getFloat("volume", 0.1f));
                    boughtEntity = new Soldier(SoldierLevel.L2);
                    click = ClickState.BUYING_UNIT;
                    showEffects(previousClick);
                }
            }
        });

        TextureRegionDrawable imageL3 = new TextureRegionDrawable(new TextureRegion((new Texture(Gdx.files.internal("images/L3.png")))));
        buttonL3 = new ImageButton(imageL3);
        buttonL3.getImage().setScale(2.5f);
        buttonL3.setVisible(false);
        buttonL3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!windowPause.isVisible()) {
                    soundButton3.play(prefs.getFloat("volume", 0.1f));
                    boughtEntity = new Soldier(SoldierLevel.L3);
                    click = ClickState.BUYING_UNIT;
                    showEffects(previousClick);
                }
            }
        });

        // Player 1

        Player player1 = level.getCurrentPlayer();

        CheckBox checkboxPlayer1 = new CheckBox("", skinSgx, "radio");
        checkboxPlayer1.getImage().setScale(2f);
        checkboxPlayer1.setTouchable(Touchable.disabled);
        checkboxPlayer1.setChecked(Level.isPlayer1Turn);
        checkboxPlayer1.getImage().setColor(player1.getColor().toColor());

        ImageButton avatarP1 = new ImageButton(Menu.imageBunny); // (player1.getAvatar());

        // quand ce sera implemente, il faudra changer le nom pour : level.getCurrentPlayer().getName()
        // au lieu de : LevelSelection.player1Name

        Label labelP1 = new Label(LevelSelection.player1Name, skinSgx, "title-white");
        labelP1.setFontScale(1.2f);

        // Player 2

        CheckBox checkboxPlayer2 = new CheckBox("", skinSgx, "radio");
        checkboxPlayer2.getImage().setScale(2f);
        checkboxPlayer2.setTouchable(Touchable.disabled);
        checkboxPlayer2.setChecked(!Level.isPlayer1Turn);

        /*
             ça il faut le faire qu'une seule fois : après que le premier ait passé son tour pour la
             première fois, faudrait mettre un compteur juste pour ça...
             dans nextTurn() on sait si c'est le P1 avec level.getCurrentPlayer().getName()
             si c'est lui, on uncheck sa checkbox, on check l'autre (et on met la couleur à la box 2
             si c'est le premier tour)
         */
        //      checkboxPlayer2.getImage().setColor(player2.getColor().toColor());

        ImageButton avatarP2 = new ImageButton(Menu.imagePanda); // (player2.getAvatar());

        // quand ce sera implemente, il faudra changer le nom pour : level.getCurrentPlayer().getName()
        // au lieu de : LevelSelection.player2Name

        Label labelP2 = new Label(LevelSelection.player2Name, skinSgx, "title-white");
        labelP2.setFontScale(1.2f);

        // Territory's money

        TextureRegionDrawable imageChest = new TextureRegionDrawable(new TextureRegion((new Texture(Gdx.files.internal("images/chest.png")))));
        buttonChest = new ImageButton(imageChest);
        buttonChest.getImage().setScale(1.5f);
        buttonChest.setPosition(buttonChest.getWidth(), (SCREEN_HEIGHT - 1.5f * buttonChest.getHeight()) * 94 / 100);
        buttonChest.setVisible(false);

        labelCoins = new Label("", skinSgx, "title-white");
        labelCoins.setFontScale(1.5f);
        labelCoins.setPosition(3 * buttonChest.getWidth(), buttonChest.getY() + buttonChest.getHeight() * 3 / 4);
        labelCoins.setVisible(false);

        labelIncome = new Label("", skinSgx, "title-white");
        labelIncome.setFontScale(1.5f);
        labelIncome.setVisible(false);


        // pour les tests
        labelWages = new Label("", skinSgx, "title-white");
        labelWages.setFontScale(1.5f);
        labelWages.setVisible(false);


        Table screenTablePlayers = new Table();
        screenTablePlayers.setFillParent(true);
        screenTablePlayers.padTop(SCREEN_HEIGHT - (2 * (SCREEN_HEIGHT * 6 / 100) + 3 * SCREEN_HEIGHT * 2 / 100)).left().padLeft(SCREEN_HEIGHT * 2 / 100);
        screenTablePlayers.add(checkboxPlayer1).padRight(SCREEN_HEIGHT * 2 / 100 + checkboxPlayer1.getImage().getWidth()).padTop(checkboxPlayer1.getImage().getHeight());
        screenTablePlayers.add(avatarP1).height(SCREEN_HEIGHT * 6 / 100).width(SCREEN_HEIGHT * 6 / 100).padRight(SCREEN_HEIGHT * 2 / 100);
        screenTablePlayers.add(labelP1).left();
        screenTablePlayers.row();
        screenTablePlayers.add(checkboxPlayer2).padTop(checkboxPlayer1.getImage().getHeight() * 2).padRight(SCREEN_HEIGHT * 2 / 100 + checkboxPlayer1.getImage().getWidth());
        screenTablePlayers.add(avatarP2).height(SCREEN_HEIGHT * 6 / 100).width(SCREEN_HEIGHT * 6 / 100).padTop(SCREEN_HEIGHT * 2 / 100).padRight(SCREEN_HEIGHT * 2 / 100);
        screenTablePlayers.add(labelP2).left().padTop(SCREEN_HEIGHT * 2 / 100);

        Table tableMarket = new Table();
        tableMarket.add(buttonL0).padRight(1.5f * buttonL1.getWidth());
        tableMarket.add(buttonL1).padRight(1.5f * buttonL1.getWidth());
        tableMarket.add(buttonL2).padRight(1.5f * buttonL1.getWidth());
        tableMarket.add(buttonL3).padRight(1.5f * buttonL1.getWidth());

        Table tableIncome = new Table();
        tableIncome.add(labelIncome);


        // pour les tests
        tableIncome.row();
        tableIncome.add(labelWages);


        Table screenTableIncome = new Table();
        screenTableIncome.setFillParent(true);
        screenTableIncome.add(tableIncome).padBottom(buttonChest.getY() - buttonChest.getHeight() * 9 / 10);

        Table screenTableMarket = new Table();
        screenTableMarket.setFillParent(true);
        screenTableMarket.addActor(buttonChest);
        screenTableMarket.addActor(labelCoins);
        screenTableMarket.add(tableMarket).padTop(SCREEN_HEIGHT - buttonL0.getHeight());

        hud.addActor(buttonNext);
        hud.addActor(buttonPause);
        hud.addActor(screenTableMarket);
        hud.addActor(screenTableIncome);
        hud.addActor(screenTablePlayers);

        hud.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER && !windowPause.isVisible()) {
                    level.nextTurn();
                    click = ClickState.NOTHING_SELECTED;
                    EffectsManagement.eraseCells(effects);
                    soundButton3.play(prefs.getFloat("volume", 0.1f));
                } else if (keycode == Input.Keys.ESCAPE && !windowPause.isVisible()) {
                    soundButton1.play(prefs.getFloat("volume", 0.2f));
                    showPauseWindow();
                } else if (keycode == Input.Keys.ESCAPE && windowPause.isVisible()) {
                    soundButton2.play(prefs.getFloat("volume", 0.2f));
                    windowPause.remove();
                    windowPause.setVisible(false);
                }
                return false;

            }
        });

        // Create multiplexer to handle input in stage and hud
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(hud);
        multiplexer.addProcessor(new GestureDetector(new LevelGestureListener(this, camera)));

    }

    public static Playable getLevel() {
        return level;
    }

    enum ClickState {
        NOTHING_SELECTED,
        ON_TERRITORY,
        ON_SOLDIER,
        BUYING_UNIT
    }
}