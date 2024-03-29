package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Application;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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
import be.ac.umons.slay.g02.level.TileType;
import be.ac.umons.slay.g02.players.AI;
import be.ac.umons.slay.g02.players.AIMethods;
import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.Player;
import be.ac.umons.slay.g02.players.Statistics;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.prefs;
import static be.ac.umons.slay.g02.gui.Main.skinSgx;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.soundButton3;
import static be.ac.umons.slay.g02.gui.screens.Menu.player1;
import static be.ac.umons.slay.g02.gui.screens.Menu.player2;
import static be.ac.umons.slay.g02.gui.screens.Menu.saveStatsPlayer;
import static be.ac.umons.slay.g02.level.Level.getPlayers;
import static be.ac.umons.slay.g02.players.Statistics.resetCurrentStats;
import static java.lang.Math.round;
import static java.lang.StrictMath.sqrt;

/**
 * Class displaying the game
 */
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
    private ImageButton lastButtonCLicked;

    private CheckBox checkboxPlayer1;
    private CheckBox checkboxPlayer2;

    private ArrayList<ImageButton> listButtonLDark = new ArrayList<ImageButton>();

    private static Window windowPause = new Window("Pause", skinSgx);
    private static Window windowQuit = new Window("Quit Game", skinSgx);
    private static Window windowEnd = new Window("End", skinSgx);

    private Label labelCoins;
    private Label labelIncome;
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
    private Coordinate previousClick = new Coordinate(0, 0);
    private Entity boughtEntity;
    private Viewport viewport;

    private int translateX;
    private int translateY;
    private int nbreH;
    private boolean AIisPaused = false;

    // The game continues while as long as it is false
    private boolean endPlay = false;

    private int worldW;
    private int worldH;
    private int numberHumans;
    private String levelName;

    /**
     * Class constructor initializing some values
     *
     * @param aGame     the instance of Game created in class Main
     * @param levelName the level name
     */
    GameScreen(Game aGame, String levelName, int numberHumans) {
        game = aGame;
        this.levelName = levelName;
        this.numberHumans = numberHumans;

        click = ClickState.NOTHING_SELECTED;
        windowPause.setVisible(false);
        windowEnd.setVisible(false);
        init();
    }

    /**
     * Creates the world and the interface
     */
    private void init() {
        try {
            // Creates the world :

            camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            LevelLoader.Map lvlLoader = LevelLoader.load(levelName, numberHumans);
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
            errorOffset = size * sqrt(3) - round(size * sqrt(3));

            // Loads the useful layers and the tileset
            territories = (TiledMapTileLayer) map.getLayers().get("Territories");
            entities = (TiledMapTileLayer) map.getLayers().get("Entities");
            effects = (TiledMapTileLayer) map.getLayers().get("Effects");

            set = map.getTileSets().getTileSet("tileset");
            tileMap = loadTileHashMap();
            worldH = nbreH * tileH + tileH / 2;

            if (nbreW % 2 == 0) {
                worldW = ((nbreW / 2) * tileW) + ((nbreW / 2) * (tileW / 2));
            } else {
                worldW = (int) (Math.ceil(nbreW / 2f) * tileW + Math.floor(nbreW / 2f) * (tileW / 2));
            }
            viewport = new FitViewport(worldW, worldH, camera);

            int midScreenW = SCREEN_WIDTH / 2;
            int midScreenH = SCREEN_HEIGHT / 2;
            translateX = midScreenW - (worldW / 2);
            translateY = midScreenH - (worldH / 2);
            camera.translate(-translateX, -translateY, 0);

        } catch (Exception e) {
            game.setScreen(new LevelSelection(game));
        }

        // Creates the interface
        loadButtons();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            changeZoom(0.02f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q) || Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            changeZoom(-0.02f);
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
            translateY -= 5;
            camera.translate(0, -5, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            translateY += 5;
            camera.translate(0, 5, 0);
        }
    }

    void changeZoom(float delta) {
        camera.zoom = (float) Math.max(0.2, Math.min(2.0, camera.zoom + delta));
    }

    /**
     * Displays the pause window
     */
    private void showPauseWindow() {
        int CORNER_SIZE = Math.min(SCREEN_WIDTH, SCREEN_HEIGHT);
        windowPause.clear();
        windowPause.setSize(CORNER_SIZE / 2, CORNER_SIZE / 2);
        windowPause.setPosition(SCREEN_WIDTH / 2 - windowPause.getWidth() / 2, SCREEN_HEIGHT / 2 - windowPause.getHeight() / 2);
        windowPause.setMovable(false);
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
                AIisPaused = false;
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
        buttonResume.setWidth(windowPause.getWidth() * 90 / 100);
        buttonQuit.setWidth(windowPause.getWidth() * 90 / 100);

        windowPause.add(buttonResume).padBottom(windowPause.getWidth() * 7 / 100).fill().width(Value.percentWidth(1f));
        windowPause.row();
        windowPause.add(buttonQuit).fill().width(Value.percentWidth(1f));

        hud.addActor(windowPause);
    }

    /**
     * Displays the quit window
     */
    private void showWindowQuit() {
        int CORNER_SIZE = Math.min(SCREEN_WIDTH, SCREEN_HEIGHT);

        windowQuit.clear();
        windowQuit.setSize(CORNER_SIZE / 4, CORNER_SIZE / 4);
        windowQuit.setPosition(SCREEN_WIDTH / 2 - windowQuit.getWidth() / 2, SCREEN_HEIGHT / 2 - windowQuit.getHeight() / 2);
        windowQuit.setMovable(false);
        windowQuit.getTitleTable().padLeft(windowQuit.getWidth() / 2 - windowQuit.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowQuit.addActor(table);
        table.setFillParent(true);
        table.padTop(windowQuit.getHeight() * 17 / 100);

        Label labelQuitConfirm = new Label("Are you sure?", skinSgx, "white");

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
        buttonYes.setWidth(CORNER_SIZE / 3 * 20 / 100);
        buttonNo.setWidth(CORNER_SIZE / 3 * 20 / 100);
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
        if (!endPlay) {
            handleInput();
            camera.update();
            Gdx.gl.glClearColor(103 / 255f, 173 / 255f, 244 / 255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            renderer.setView(camera);
            renderer.render();

            hud.getViewport().update(SCREEN_WIDTH, SCREEN_HEIGHT, true);

            hud.act();
            hud.draw();
        } else {
            Player winner = level.hasWon();

            updatePlayerStats(winner);
            Gdx.graphics.setContinuousRendering(false);
            hud.clear();
            game.setScreen(new EndGame(game, winner, numberHumans));
        }
    }

    /**
     * Updates all stats for winner and loser (if logged)
     * If the winner's name is equal to the player for whom the statistics are updated then we
     * increment the number of wins, otherwise we increment the number of defeats
     *
     * @param winner the winner of the game
     */
    private void updatePlayerStats(Player winner) {
        // Player 1
        if ((prefs.getBoolean("isPlayer1Logged")) && (getPlayers()[0].getName().equals(player1.getName()) ||
                getPlayers()[1].getName().equals(player1.getName()))) {
            // Number of games ++
            updatePlayerGames(player1);

            // If player1 is the winner
            if (winner.getName().equals(player1.getName())) {
                // Number of wins ++
                updatePlayerWins(player1);
                // All other stats are updated and the score is calculated
                updatePlayerScore(player1, true);
            }

            // If player1 is the loser
            else {
                // Number of defeats ++
                updatePlayerDefeats(player1);
                // All other stats are updated and the score is calculated
                updatePlayerScore(player1, false);
            }
        }
        // Player 2
        if ((prefs.getBoolean("isPlayer2Logged")) && (getPlayers()[1].getName().equals(player2.getName()) ||
                getPlayers()[0].getName().equals(player2.getName()))) {
            // Number of games ++
            updatePlayerGames(player2);

            // If player2 is the winner
            if (winner.getName().equals(player2.getName())) {
                // Number of wins ++
                updatePlayerWins(player2);
                // All other stats are updated and the score is calculated
                updatePlayerScore(player2, true);
            }
            // If player2 is the loser
            else {
                // Number of defeats ++
                updatePlayerDefeats(player2);
                // All other stats are updated and the score is calculated
                updatePlayerScore(player2, false);
            }
        }
    }

    private void updatePlayerGames(HumanPlayer player) {
        player.getGlobalStats().incrementStatInMap(player.getGlobalStats().getStats(), Statistics.GAMES);
        player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).incrementStatInMap
                (player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).getStats(), Statistics.GAMES);
    }

    private void updatePlayerWins(HumanPlayer player) {
        player.getGlobalStats().incrementStatInMap(player.getGlobalStats().getStats(), Statistics.WINS);
        player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).incrementStatInMap
                (player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).getStats(), Statistics.WINS);
    }

    private void updatePlayerDefeats(HumanPlayer player) {
        player.getGlobalStats().incrementStatInMap(player.getGlobalStats().getStats(), Statistics.DEFEATS);
        player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).incrementStatInMap
                (player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).getStats(), Statistics.DEFEATS);
    }

    private void updatePlayerScore(HumanPlayer player, boolean hasWon) {
        player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).updateStats();
        player.getGlobalStats().updateStats(player);
        // The score is calculated with the global statistics to be displayed in the hall of fame
        int score = player.getGlobalStats().calculateScore(hasWon);

        int scoreBefore = player.getGlobalStats().getScore();
        player.getGlobalStats().setScore(score + scoreBefore);
        saveStatsPlayer(player);

        // Resets the values used for calculations
        resetCurrentStats(player);
    }

    private Coordinate getCoordinate(float x, float y) {
        Vector2 vect = viewport.unproject(new Vector2(x, y));
        // Adjusts the axis system
        int offset = (int) (vect.y / size * errorOffset);

        vect.set((int) (vect.x - (tileW / 2)), (int) (vect.y - (tileH) + offset));
        return HexManagement.pixelToHex((int) vect.x, (int) vect.y, size);
    }

    void onTap(float x, float y) {
        if (!windowPause.isVisible() && level.getCurrentPlayer() instanceof HumanPlayer) {

            Coordinate clickPos = getCoordinate(x, y);

            if (level.isInLevel(clickPos)) {

                Tile clickedTile = level.get(clickPos);
                // Changes state if needed
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
                        if (level.isInLevel(previousClick)) {
                            if (level.getMoves(boughtEntity, previousClick).contains(clickPos)) {
                                level.buy(boughtEntity, previousClick, clickPos);
                            }
                            click = ClickState.NOTHING_SELECTED;
                        }
                        break;
                }
                showEffects(clickPos);

                if (clickedTile.getType() == TileType.NEUTRAL) {
                    // Remembers last position only when clicking in the map and on neutral tile
                    previousClick = clickPos;
                }
            }
        }
    }

    private void showEffects(Coordinate clickPos) {
        // Shows effects
        EffectsManagement.eraseCells(effects);

        switch (click) {

            case NOTHING_SELECTED:
                showMarket(clickPos, true);
                showCoins(clickPos, true);
                break;

            case ON_TERRITORY:
                /*
                     If we click on a territory but not on a soldier, tiles from that territory
                     have to be highlighted and we show the market for this territory
                */
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
                if (level.isInLevel(previousClick)) {
                    List<Coordinate> listBuyable = level.getMoves(boughtEntity, previousClick);
                    EffectsManagement.shadowMap(effects, level, set);
                    EffectsManagement.highlightCells(effects, listBuyable, tileMap.get("GREEN_HIGHLIGHT"));
                }
                break;
        }
    }

    private void showMarket(Coordinate c, boolean hidden) {
        boolean isShown = !hidden;
        List<String> canBuy = new ArrayList<String>();
        if (level.isInLevel(c)) {
            List<Entity> couldBuy = level.canBuy(c);
            if (couldBuy != null) {
                for (Entity e : level.canBuy(c)) {
                    canBuy.add(e.getName());
                }
            }
            boolean isVisibleL0 = isShown && canBuy.contains("L0");
            boolean isVisibleL1 = isShown && canBuy.contains("L1");
            boolean isVisibleL2 = isShown && canBuy.contains("L2");
            boolean isVisibleL3 = isShown && canBuy.contains("L3");

            listButtonLDark.clear();

            changeMarketDisplay(isShown, isVisibleL0, buttonL0);
            changeMarketDisplay(isShown, isVisibleL1, buttonL1);
            changeMarketDisplay(isShown, isVisibleL2, buttonL2);
            changeMarketDisplay(isShown, isVisibleL3, buttonL3);
        }
    }

    private void changeMarketDisplay(boolean isShown, boolean isVisibleL, ImageButton buttonL) {
        if (isVisibleL) {
            buttonL.getImage().setColor(Color.WHITE);
            buttonL.setTouchable(Touchable.enabled);
        } else {
            listButtonLDark.add(buttonL);
            buttonL.getImage().setColor(Color.DARK_GRAY);
            buttonL.setTouchable(Touchable.disabled);
        }
        buttonL.setVisible(isShown);
    }

    /**
     * Displays the coins of the selected territory and its income
     *
     * @param c        the coordinate of the territory on which the player has clicked
     * @param isHidden true if it has to be hidden, false otherwise
     */
    private void showCoins(Coordinate c, boolean isHidden) {
        if (isHidden) {
            buttonChest.setVisible(false);
            labelCoins.setVisible(false);
            labelIncome.setVisible(false);
            labelWages.setVisible(false);
        } else {
            buttonChest.setVisible(true);
            labelCoins.setVisible(true);
            labelIncome.setVisible(true);
            labelWages.setVisible(true);
            labelCoins.setText("" + level.get(c).getTerritory().getCoins());
            labelIncome.setText("+ " + level.get(c).getTerritory().getIncome());
            labelWages.setText("- " + level.get(c).getTerritory().getWages());
        }
    }

    /**
     * Reloads the visual of the level according to the changes made in logic
     */
    private void loadLevel() {
        showNextTurnEffects();
        if (level.getCurrentPlayer() instanceof AI && !AIisPaused) {
            // There is a winner
            boolean hasWon = ((AI) level.getCurrentPlayer()).play(level.getCurrentPlayer(), true);
            if (!hasWon)
                // Indicates the end of the game
                endPlay = true;
        }
        // Goes through each cell of the table of the logical part
        for (int i = 0; i < level.width(); i++) {
            for (int j = 0; j < level.height(); j++) {
                Tile tile = level.get(i, j);
                // If there is an entity in the cell, it is added to the graphical interface
                if (tile.getEntity() != null) {
                    TiledMapTile image = tileMap.get(tile.getEntity().getName().toUpperCase());
                    HexManagement.drawTile(new Coordinate(i, j), image, entities);

                }
                // There is no more entity in the cell
                else {
                    // The cell is cleared if it is not empty in the graphical interface
                    if (entities.getCell(i, j) != null)
                        entities.setCell(i, j, new TiledMapTileLayer.Cell());
                }
                if (tile.getTerritory() != null) { // Si la case appartient à un territoire, le rajouter à l'interface graphique
                    TiledMapTile image = tileMap.get(tile.getTerritory().getOwner().getColor().getName());
                    HexManagement.drawTile(new Coordinate(i, j), image, territories);
                }
                // There is no more territory on the cell
                else {
                    // The cell is cleared if it is not empty in the graphical interface
                    if (territories.getCell(i, j) != null)
                        territories.setCell(i, j, new TiledMapTileLayer.Cell());
                }
            }
        }
    }

    /**
     * Loads the image of tiles linked to their names
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
        loadButtons();
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
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

    /**
     * Loads the buttons
     */
    private void loadButtons() {
        int image_corner = Math.max(SCREEN_WIDTH, SCREEN_HEIGHT);
        hudCam = new OrthographicCamera();

        if (hud == null)
            hud = new Stage(new ScreenViewport());
        else
            hud.clear();

        if (numberHumans == 0) {
            // Initialises speed and pause buttons
            TextureRegionDrawable imageV4 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/v4.png"))));
            final ImageButton buttonV4 = new ImageButton(imageV4);
            TextureRegionDrawable imageV3 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/v3.png"))));
            final ImageButton buttonV3 = new ImageButton(imageV3);
            TextureRegionDrawable imageV2 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/v2.png"))));
            final ImageButton buttonV2 = new ImageButton(imageV2);
            TextureRegionDrawable imageV1 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/v1.png"))));
            final ImageButton buttonV1 = new ImageButton(imageV1);
            TextureRegionDrawable imagePause = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/v0.png"))));
            final ImageButton buttonV0 = new ImageButton(imagePause);

            // Adjusts speed and pause buttons position
            float offsetW = 10;
            float offsetH = buttonV4.getHeight();
            buttonV4.setPosition((SCREEN_WIDTH - offsetW) * 96 / 100 + SCREEN_HEIGHT * 1 / 200, offsetH);
            offsetW += buttonV3.getWidth() + 10;
            buttonV3.setPosition((SCREEN_WIDTH - offsetW) * 96 / 100 + SCREEN_HEIGHT * 1 / 200, offsetH);
            offsetW += buttonV2.getWidth() + 10;
            buttonV2.setPosition((SCREEN_WIDTH - offsetW) * 96 / 100 + SCREEN_HEIGHT * 1 / 200, offsetH);
            offsetW += buttonV1.getWidth() + 10;
            buttonV1.setPosition((SCREEN_WIDTH - offsetW) * 96 / 100 + SCREEN_HEIGHT * 1 / 200, offsetH);
            offsetW += buttonV0.getWidth() + 10;
            buttonV0.setPosition((SCREEN_WIDTH - offsetW) * 96 / 100 + SCREEN_HEIGHT * 1 / 200, offsetH);

            if (lastButtonCLicked == null)
                makeButtonGreen(true, buttonV3);
            else
                makeButtonGreen(true, lastButtonCLicked);

            // Adds speed and pause buttons :

            buttonV4.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!windowPause.isVisible()) {
                        lastButtonCLicked = buttonV4;
                        makeButtonGreen(true, buttonV4);
                        makeButtonGreen(false, buttonV0, buttonV1, buttonV2, buttonV3);
                        soundButton3.play(prefs.getFloat("volume", 0.1f));
                        AIisPaused = false;
                        AIMethods.setSpeed(200); // 5 tours / seconde
                    }
                }
            });

            buttonV3.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!windowPause.isVisible()) {
                        lastButtonCLicked = buttonV3;
                        makeButtonGreen(true, buttonV3);
                        makeButtonGreen(false, buttonV0, buttonV1, buttonV2, buttonV4);
                        soundButton3.play(prefs.getFloat("volume", 0.1f));
                        AIisPaused = false;
                        AIMethods.setSpeed(333); //3 tours / seconde
                    }
                }
            });

            buttonV2.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!windowPause.isVisible()) {
                        lastButtonCLicked = buttonV2;
                        makeButtonGreen(true, buttonV2);
                        makeButtonGreen(false, buttonV0, buttonV1, buttonV3, buttonV4);
                        soundButton3.play(prefs.getFloat("volume", 0.1f));
                        AIisPaused = false;
                        AIMethods.setSpeed(500); //2 tours / seconde
                    }
                }
            });

            buttonV1.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!windowPause.isVisible()) {
                        lastButtonCLicked = buttonV1;
                        makeButtonGreen(true, buttonV1);
                        makeButtonGreen(false, buttonV0, buttonV2, buttonV3, buttonV4);
                        soundButton3.play(prefs.getFloat("volume", 0.1f));
                        AIisPaused = false;
                        AIMethods.setSpeed(1000);
                    }
                }
            });

            buttonV0.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!windowPause.isVisible()) {
                        soundButton3.play(prefs.getFloat("volume", 0.1f));
                        lastButtonCLicked = buttonV0;
                        makeButtonGreen(true, buttonV0);
                        makeButtonGreen(false, buttonV1, buttonV2, buttonV3, buttonV4);
                        AIisPaused = true;
                    }
                }
            });

            hud.addActor(buttonV4);
            hud.addActor(buttonV3);
            hud.addActor(buttonV2);
            hud.addActor(buttonV1);
            hud.addActor(buttonV0);

        } else {
            // Adds button Next :

            TextureRegionDrawable imageNext = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/next.png"))));
            buttonNext = new ImageButton(imageNext);
            buttonNext.setSize(image_corner * 8 / 100, image_corner * 12 / 100);
            buttonNext.setPosition((SCREEN_WIDTH - buttonNext.getWidth()) * 96 / 100 + SCREEN_WIDTH * 1 / 200, 0);
            buttonNext.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!windowPause.isVisible()) {
                        boolean hasWon = level.nextTurn(); // true = il y a un gagnant
                        if (!hasWon) {
                            endPlay = true; // Signale fin de partie
                        }
                        click = ClickState.NOTHING_SELECTED;
                        EffectsManagement.eraseCells(effects);
                        showEffects(previousClick);
                        soundButton3.play(prefs.getFloat("volume", 0.1f));
                    }
                }
            });
            hud.addActor(buttonNext);
        }
        // Adds button Pause :

        TextureRegionDrawable imageDots = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/dots.png"))));
        buttonPause = new ImageButton(imageDots);
        buttonPause.setSize(image_corner * 2 / 100, image_corner * 5 / 100);
        buttonPause.setPosition((SCREEN_WIDTH - buttonPause.getWidth()) * 97 / 100, (SCREEN_HEIGHT - buttonPause.getHeight()) * 94 / 100);
        buttonPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!windowPause.isVisible()) {
                    soundButton1.play(prefs.getFloat("volume", 0.2f));
                    showPauseWindow();
                    AIisPaused = true;
                }
            }
        });

        // Adds entity market :

        TextureRegionDrawable imageL0 = new TextureRegionDrawable(new TextureRegion((new Texture(Gdx.files.internal("images/L0.png")))));
        buttonL0 = new ImageButton(imageL0);
        buttonL0.getImage().setScale(2.5f);
        buttonL0.setVisible(false);
        buttonL0.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!windowPause.isVisible()) {
                    soundButton1.play(prefs.getFloat("volume", 0.2f));
                    boughtEntity = new Soldier(SoldierLevel.L0);
                    click = ClickState.BUYING_UNIT;
                    showEffects(previousClick);
                    makeButtonGreen(true, buttonL0);
                    makeButtonGreen(false, buttonL1, buttonL2, buttonL3);
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
                    soundButton1.play(prefs.getFloat("volume", 0.2f));
                    boughtEntity = new Soldier(SoldierLevel.L1);
                    click = ClickState.BUYING_UNIT;
                    showEffects(previousClick);
                    makeButtonGreen(true, buttonL1);
                    makeButtonGreen(false, buttonL0, buttonL2, buttonL3);
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
                    soundButton1.play(prefs.getFloat("volume", 0.2f));
                    boughtEntity = new Soldier(SoldierLevel.L2);
                    click = ClickState.BUYING_UNIT;
                    showEffects(previousClick);
                    makeButtonGreen(true, buttonL2);
                    makeButtonGreen(false, buttonL1, buttonL0, buttonL3);
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
                    soundButton1.play(prefs.getFloat("volume", 0.2f));
                    boughtEntity = new Soldier(SoldierLevel.L3);
                    click = ClickState.BUYING_UNIT;
                    showEffects(previousClick);
                    makeButtonGreen(true, buttonL3);
                    makeButtonGreen(false, buttonL1, buttonL2, buttonL0);
                }
            }
        });

        // Player 1 :

        checkboxPlayer1 = new CheckBox("", skinSgx, "radio");
        checkboxPlayer1.getImage().setScale(1.5f);
        checkboxPlayer1.setTouchable(Touchable.disabled);
        checkboxPlayer1.setChecked(true);
        checkboxPlayer1.getImage().setColor(getPlayers()[0].getColor().toColor());

        ImageButton avatarP1 = new ImageButton(new TextureRegionDrawable(
                new TextureRegion(new Texture(
                        Gdx.files.internal(Level.getPlayers()[0].getAvatar())))));

        Label labelP1;
        if (Level.getPlayers()[0].getName().length() > 5) {
            String labelNom = Level.getPlayers()[0].getName().substring(0, 4) + "...";
            labelP1 = new Label(labelNom, skinSgx, "title-white");
        } else
            labelP1 = new Label(Level.getPlayers()[0].getName(), skinSgx, "title-white");
        labelP1.setFontScale(1.2f);

        // Player 2 :

        checkboxPlayer2 = new CheckBox("", skinSgx, "radio");
        checkboxPlayer2.getImage().setScale(1.5f);
        checkboxPlayer2.setTouchable(Touchable.disabled);
        checkboxPlayer2.setChecked(false);
        checkboxPlayer2.getImage().setColor(Color.WHITE);


        ImageButton avatarP2 = new ImageButton(new TextureRegionDrawable(
                new TextureRegion(new Texture(
                        Gdx.files.internal(Level.getPlayers()[1].getAvatar())))));

        Label labelP2;
        if (Level.getPlayers()[1].getName().length() > 5) {
            String labelNom2 = Level.getPlayers()[1].getName().substring(0, 4) + "...";
            labelP2 = new Label(labelNom2, skinSgx, "title-white");
        } else
            labelP2 = new Label(Level.getPlayers()[1].getName(), skinSgx, "title-white");
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

        labelWages = new Label("", skinSgx, "title-white");
        labelWages.setFontScale(1.5f);
        labelWages.setVisible(false);

        Table screenTablePlayers = new Table();

        if (SCREEN_WIDTH > SCREEN_HEIGHT && !(Gdx.app.getType() == Application.ApplicationType.Android)) {
            screenTablePlayers.setFillParent(true);
            screenTablePlayers.padTop(SCREEN_HEIGHT - (2 * (SCREEN_HEIGHT * 6 / 100) + 3 * SCREEN_HEIGHT * 2 / 100)).left().padLeft(SCREEN_HEIGHT * 2 / 100);
            screenTablePlayers.add(checkboxPlayer1).padRight(SCREEN_HEIGHT * 2 / 100 + checkboxPlayer1.getImage().getWidth() / 2.6f).padTop(checkboxPlayer1.getImage().getHeight() * 2 / 3);
            screenTablePlayers.add(avatarP1).height(SCREEN_HEIGHT * 6 / 100).width(SCREEN_HEIGHT * 6 / 100).padRight(SCREEN_HEIGHT * 2 / 100);
            screenTablePlayers.add(labelP1).left();
        }
        screenTablePlayers.row();

        if (SCREEN_WIDTH > SCREEN_HEIGHT && !(Gdx.app.getType() == Application.ApplicationType.Android)) {
            screenTablePlayers.add(checkboxPlayer2).padTop(checkboxPlayer1.getImage().getHeight() * 1.7f).padRight(SCREEN_HEIGHT * 2 / 100 + checkboxPlayer1.getImage().getWidth() / 2.6f);
            screenTablePlayers.add(avatarP2).height(SCREEN_HEIGHT * 6 / 100).width(SCREEN_HEIGHT * 6 / 100).padTop(SCREEN_HEIGHT * 2 / 100).padRight(SCREEN_HEIGHT * 2 / 100);
            screenTablePlayers.add(labelP2).left().padTop(SCREEN_HEIGHT * 2 / 100);
        }
        Table tableMarket = new Table();

        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            tableMarket.add(buttonL0).padRight(1.5f * buttonL1.getWidth());
            tableMarket.add(buttonL1).padRight(1.5f * buttonL1.getWidth());
            tableMarket.add(buttonL2).padRight(1.5f * buttonL1.getWidth());
            tableMarket.add(buttonL3).padRight(1.5f * buttonL1.getWidth());
        } else {
            tableMarket.add(buttonL3).padTop(1.5f * buttonL1.getHeight());
            tableMarket.row();
            tableMarket.add(buttonL2).padTop(1.5f * buttonL1.getHeight());
            tableMarket.row();
            tableMarket.add(buttonL1).padTop(1.5f * buttonL1.getHeight());
            tableMarket.row();
            tableMarket.add(buttonL0).padTop(1.5f * buttonL1.getHeight());
        }
        Table tableIncome = new Table();
        tableIncome.add(labelIncome);
        tableIncome.row();
        tableIncome.add(labelWages);

        Table screenTableIncome = new Table();
        screenTableIncome.setFillParent(true);
        screenTableIncome.add(tableIncome).padBottom(buttonChest.getY() - buttonChest.getHeight() * 9 / 10);

        Table screenTableMarket = new Table();
        screenTableMarket.setFillParent(true);
        screenTableMarket.addActor(buttonChest);
        screenTableMarket.addActor(labelCoins);
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            screenTableMarket.add(tableMarket).padTop(SCREEN_HEIGHT - buttonL0.getHeight());
        } else {
            screenTableMarket.add(tableMarket).padTop((SCREEN_HEIGHT - buttonL0.getHeight()) / 2).padRight(SCREEN_WIDTH - buttonL0.getWidth());
        }
        hud.addActor(buttonPause);
        hud.addActor(screenTableMarket);
        hud.addActor(screenTableIncome);
        hud.addActor(screenTablePlayers);

        hud.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER && !windowPause.isVisible()) {

                    // True if there is a winner
                    boolean hasWon = level.nextTurn();

                    if (!hasWon)
                        // Indicates the end of the game
                        endPlay = true;
                    click = ClickState.NOTHING_SELECTED;
                    EffectsManagement.eraseCells(effects);
                    showEffects(previousClick);
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

        // Creates a multiplexer to handle the input in hud
        if (multiplexer == null) {
            multiplexer = new InputMultiplexer();
            multiplexer.addProcessor(hud);
            multiplexer.addProcessor(new GestureDetector(new LevelGestureListener(this, camera)));
        }
    }

    /**
     * Changes the appearance of the ball before players name in the corner
     * <p>
     * The ball is the player's color and full when it is his turn
     * Otherwise, the ball is a white circle
     */
    private void showNextTurnEffects() {
        if (level.getCurrentPlayer() == getPlayers()[0]) {
            checkboxPlayer1.setChecked(true);
            checkboxPlayer1.getImage().setColor(getPlayers()[0].getColor().toColor());
            checkboxPlayer2.setChecked(false);
            checkboxPlayer2.getImage().setColor(Color.WHITE);
        } else {
            checkboxPlayer1.setChecked(false);
            checkboxPlayer1.getImage().setColor(Color.WHITE);
            checkboxPlayer2.setChecked(true);
            checkboxPlayer2.getImage().setColor(getPlayers()[1].getColor().toColor());
        }
    }

    /**
     * Turns the selected unit in market to green
     *
     * @param isSelected true if buttonL is the selected unit
     * @param buttonL    the units in the market
     */
    private void makeButtonGreen(boolean isSelected, ImageButton... buttonL) {
        if (isSelected)
            buttonL[0].getImage().setColor(Color.GREEN);
        else {
            for (ImageButton button : buttonL) {
                if (!listButtonLDark.contains(button))
                    button.getImage().setColor(Color.WHITE);
            }
        }
    }

    public static Playable getLevel() {
        return level;
    }

    /**
     * Enumeration of all possible screen states based on what the player has just clicked
     */
    enum ClickState {
        NOTHING_SELECTED,
        ON_TERRITORY,
        ON_SOLDIER,
        BUYING_UNIT
    }
}