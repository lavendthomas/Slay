package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import be.ac.umons.slay.g02.gui.Main;
import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.LevelStats;
import be.ac.umons.slay.g02.players.Statistics;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.prefs;
import static be.ac.umons.slay.g02.gui.Main.skinSgx;
import static be.ac.umons.slay.g02.gui.Main.skinSgxTable;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.soundButton3;
import static be.ac.umons.slay.g02.gui.Main.stage;
import static be.ac.umons.slay.g02.gui.screens.Menu.backgroundGrey;
import static be.ac.umons.slay.g02.gui.screens.Menu.disableBox;
import static be.ac.umons.slay.g02.gui.screens.Menu.disableButton;
import static be.ac.umons.slay.g02.gui.screens.Menu.enableBox;
import static be.ac.umons.slay.g02.gui.screens.Menu.player1;
import static be.ac.umons.slay.g02.gui.screens.Menu.player2;

/**
 * Class displaying the level selection screen
 */
public class LevelSelection implements Screen {
    private Drawable imageLevel1 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("levels/level1.png"))));
    private Drawable imageLevel2 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("levels/level2.png"))));
    private Drawable imageLevel3 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("levels/level3.png"))));
    private Drawable imageLevel4 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("levels/level4.png"))));
    private Drawable imageLevel5 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("levels/level5.png"))));
    private Drawable imageLevel6 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("levels/slay1.png"))));
    private Drawable imageLevel7 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("levels/slay2.png"))));
    private Drawable imageLevel8 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("levels/slay3.png"))));
    private Drawable imageLevel9 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("levels/slay4.png"))));
    private Drawable imageLevel10 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("levels/slay5.png"))));

    private Game game;
    private SpriteBatch batch;
    private Sprite sprite;
    private int cellHeight;

    // Level to display in the preview and to load in the game is launched
    public static int currentIslandNumber = 1;
    // Level in the selection box in the statistics window
    private int islandNumberStats = 1;

    private Button buttonIsland;
    private Button buttonGlobal;
    private TextButton buttonBack;
    private TextButton buttonPlay;
    private TextButton buttonStats;
    private SelectBox<String> selectBoxDifficulty1;
    private SelectBox<String> selectBoxDifficulty2;
    private SelectBox<String> selectBoxPlayer;
    private SelectBox<Integer> selectBoxIsland;
    private SelectBox<Integer> selectBoxNumber;
    private SelectBox<Integer> selectBoxIslandStats;
    private SelectBox<String> selectBoxPlayerStats;
    private LinkedHashMap<String, String> statsList;
    private Array<Integer> islandNumbers;
    private Array<String> playerNames = new Array<String>();
    private ArrayList<Label> labelsIsland;
    private ArrayList<Label> labelsGlobal;
    private Table mainTableStats = new Table();
    private Table containerGlobal;
    private Table containerIsland;
    private Table tableButtonBack;
    private Cell<ImageButton> cellPreview;
    private Table tableBackground;
    private Table tableTabs;
    private Table tableIsland;
    private Table tableGlobal;
    private Cell cellContent;
    private Cell cellTableTabs;
    private boolean isInIsland;
    private final String AVERAGE_BEGINNING = "Ave";
    private final String LEFT_UNITS = "remaining units";
    public static final int TOTAL_NUMBER_ISLANDS = 10;

    private String humanPlayerStats;
    public static String humanPlayer;

    // Used to know if there is AI when starting a game
    public static int numberHumans = 0;

    // Used to select the right type of AI
    public static int difficulty1 = 1;
    public static int difficulty2 = 1;

    /**
     * Class constructor
     *
     * @param aGame the instance of Game created in class Main
     */
    public LevelSelection(Game aGame) {
        game = aGame;
        init();
    }

    private void init() {
        stage.clear();

        int buttonGapY = SCREEN_HEIGHT * 10 / 100;
        cellHeight = SCREEN_HEIGHT * 6 / 100;

        numberHumans = 0;
        difficulty1 = 1;
        difficulty2 = 1;

        // Background
        batch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("backgrounds/background.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setOrigin(0, 0);
        sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

        ImageButton levelPreview = new ImageButton(imageLevel1);

        Label labelIsland = new Label("Island", skinSgx, "title");
        Label labelHuman = new Label("Number of human\nplayers", skinSgx, "title");
        labelHuman.setAlignment(1);
        Label labelDifficulty = new Label("Difficulty", skinSgx, "title");
        selectBoxIsland = new SelectBox<Integer>(skinSgx);
        selectBoxIsland.setScale(2f);
        islandNumbers = new Array<Integer>();
        fillIslandNumbers();
        selectBoxIsland.setItems(islandNumbers);
        ChangeListener selectBoxIslandListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));

                currentIslandNumber = selectBoxIsland.getSelected();
                Drawable imageLevel;

                switch (currentIslandNumber) {
                    case 1:
                        imageLevel = imageLevel1;
                        break;
                    case 2:
                        imageLevel = imageLevel2;
                        break;
                    case 3:
                        imageLevel = imageLevel3;
                        break;
                    case 4:
                        imageLevel = imageLevel4;
                        break;
                    case 5:
                        imageLevel = imageLevel5;
                        break;
                    case 6:
                        imageLevel = imageLevel6;
                        break;
                    case 7:
                        imageLevel = imageLevel7;
                        break;
                    case 8:
                        imageLevel = imageLevel8;
                        break;
                    case 9:
                        imageLevel = imageLevel9;
                        break;
                    case 10:
                        imageLevel = imageLevel10;
                        break;
                    default:
                        imageLevel = imageLevel1;
                }
                ImageButton levelPreview = new ImageButton(imageLevel);

                cellPreview.clearActor();
                cellPreview.setActor(levelPreview);
            }
        };
        selectBoxIsland.addListener(selectBoxIslandListener);

        selectBoxNumber = new SelectBox<Integer>(skinSgx);
        final Array<Integer> numberOfHumans = new Array<Integer>();
        numberOfHumans.add(0, 1, 2);
        selectBoxNumber.setItems(numberOfHumans);
        ChangeListener selectBoxNumberListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));

                if (selectBoxNumber.getSelected() == 1) {
                    enableBox(selectBoxDifficulty1);
                    disableBox(selectBoxDifficulty2);

                    if (prefs.getBoolean("isPlayer1Logged")
                            || prefs.getBoolean("isPlayer2Logged")) {
                        enableBox(selectBoxPlayer);
                    }
                } else if (selectBoxNumber.getSelected() == 2) {
                    disableBox(selectBoxDifficulty1, selectBoxDifficulty2, selectBoxPlayer);
                } else {
                    enableBox(selectBoxDifficulty1, selectBoxDifficulty2);
                    disableBox(selectBoxPlayer);
                }
                numberHumans = selectBoxNumber.getSelected();
            }
        };
        selectBoxNumber.addListener(selectBoxNumberListener);

        playerNames.clear();

        selectBoxPlayer = new SelectBox<String>(skinSgx);

        if (prefs.getBoolean("isPlayer1Logged"))
            playerNames.add(player1.getName());

        if (prefs.getBoolean("isPlayer2Logged"))
            playerNames.add(player2.getName());

        if (!(prefs.getBoolean("isPlayer1Logged") || prefs.getBoolean("isPlayer2Logged")))
            playerNames.add("              ");

        selectBoxPlayer.setItems(playerNames);
        humanPlayer = selectBoxPlayer.getItems().first();
        ChangeListener selectBoxPlayerListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                humanPlayer = selectBoxPlayer.getSelected();
            }
        };
        selectBoxPlayer.addListener(selectBoxPlayerListener);
        disableBox(selectBoxPlayer);

        selectBoxDifficulty1 = new SelectBox<String>(skinSgx);
        Array<String> difficultyDegrees = new Array<String>();
        difficultyDegrees.add("EASY", "MEDIUM", "ADVANCED", "RANDOM");
        difficultyDegrees.add("ADAPTIVE");
        selectBoxDifficulty1.setItems(difficultyDegrees);
        ChangeListener selectBoxDifficultyListener1 = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                difficulty1 = selectBoxDifficulty1.getSelectedIndex() + 1;
            }
        };
        selectBoxDifficulty1.addListener(selectBoxDifficultyListener1);

        selectBoxDifficulty2 = new SelectBox<String>(skinSgx);
        selectBoxDifficulty2.setItems(difficultyDegrees);
        ChangeListener selectBoxDifficultyListener2 = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                difficulty2 = selectBoxDifficulty2.getSelectedIndex() + 1;
            }
        };
        selectBoxDifficulty2.addListener(selectBoxDifficultyListener2);

        buttonBack = new TextButton("Back", skinSgx, "big");
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                stage.clear();
                game.setScreen(new Menu(game));
            }
        });
        buttonPlay = new TextButton("Play", skinSgx, "big");
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton3.play(prefs.getFloat("volume", 0.1f));
                stage.clear();
                game.setScreen(new GameScreen(game, String.format("g02_%02d", currentIslandNumber), numberHumans));
            }
        });

        final Table table = new Table();
        table.setFillParent(true);
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            table.left().padBottom(SCREEN_HEIGHT * 22 / 100);
            table.setSize(SCREEN_WIDTH / 2, SCREEN_HEIGHT);

            labelIsland.setAlignment(1);
            table.add(labelIsland);
            table.add(selectBoxIsland);

            table.add(levelPreview).padLeft(SCREEN_WIDTH * 7 / 100).height(SCREEN_HEIGHT * 25 / 100).width(SCREEN_HEIGHT * 25 / 100);
            table.row().pad(buttonGapY, 0, 0, 0);
            table.add(labelHuman).width(SCREEN_WIDTH * 25 / 100);
            table.add(selectBoxNumber);
            table.add(selectBoxPlayer).padLeft(SCREEN_WIDTH * 7.2f / 100);
            table.row().pad(SCREEN_HEIGHT * 17 / 100, 0, 0, 0);
            table.add(labelDifficulty);
            table.add(selectBoxDifficulty1).left().colspan(1);
            table.add(selectBoxDifficulty2).padLeft(SCREEN_WIDTH * 7.2f / 100);

            cellPreview = table.getCell(levelPreview);

            buttonBack.setWidth(SCREEN_WIDTH * 12 / 100);
            buttonPlay.setWidth(SCREEN_WIDTH * 12 / 100);
        } else {
            table.left().padBottom(SCREEN_HEIGHT * 22 / 100);
            table.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

            labelIsland.setAlignment(1);

            table.add(labelIsland).padLeft(SCREEN_WIDTH / 4);
            table.row().pad(buttonGapY / 4, 0, 0, 0);
            table.add(selectBoxIsland).size(SCREEN_WIDTH * 5 / 100);
            table.add(levelPreview).padLeft(SCREEN_WIDTH * 7 / 100).height(SCREEN_HEIGHT * 25 / 100).width(SCREEN_HEIGHT * 25 / 100);
            table.row().pad(buttonGapY, 0, 0, 0);
            table.add(labelHuman).padLeft(SCREEN_WIDTH / 4).width(SCREEN_WIDTH * 25 / 100);
            table.row().pad(buttonGapY / 4, 0, 0, 0);
            table.add(selectBoxNumber).size(SCREEN_WIDTH * 5 / 100);
            table.add(selectBoxPlayer).height(SCREEN_WIDTH * 5 / 100);
            table.row().pad(SCREEN_HEIGHT * 17 / 100, 0, 0, 0);
            table.add(labelDifficulty).padLeft(SCREEN_WIDTH / 4);
            table.row().pad(buttonGapY / 4, 0, 0, 0);
            table.add(selectBoxDifficulty1).height(SCREEN_WIDTH * 5 / 100).left().colspan(1).padLeft(SCREEN_WIDTH / 8);
            table.add(selectBoxDifficulty2).height(SCREEN_WIDTH * 5 / 100);

            cellPreview = table.getCell(levelPreview);

            buttonBack.setWidth(SCREEN_WIDTH * 12 / 100);
            buttonPlay.setWidth(SCREEN_WIDTH * 12 / 100);
        }

        if (!prefs.getBoolean("isAccountEnabled") && Gdx.app.getType() != Application.ApplicationType.Android) {
            table.center().padRight(SCREEN_WIDTH * 6 / 100);
            buttonBack.setPosition(SCREEN_WIDTH / 2 - buttonBack.getWidth() * 3 / 2, SCREEN_HEIGHT * 10 / 100);
            buttonPlay.setPosition(SCREEN_WIDTH / 2 + buttonBack.getWidth() / 2, SCREEN_HEIGHT * 10 / 100);
        } else if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            buttonStats = new TextButton("Statistics", skinSgx, "big");
            buttonStats.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundButton1.play(prefs.getFloat("volume", 0.2f));
                    showStats();
                }
            });
            buttonStats.setWidth(SCREEN_WIDTH * 12 / 100);

            Table tableStats = new Table();
            tableStats.setFillParent(true);
            tableStats.padRight(SCREEN_WIDTH * 1 / 100);
            tableStats.right().top();
            tableStats.setClip(true);

            Label labelTitle = new Label("\nHall Of Fame", skinSgxTable, "title-white");
            labelTitle.setHeight(SCREEN_HEIGHT * 15 / 100);
            labelTitle.setWidth(SCREEN_HEIGHT * 30 / 100);
            labelTitle.setAlignment(1);
            tableStats.add(labelTitle).colspan(4).width(Value.percentWidth(1f)).height(Value.percentHeight(1f));
            tableStats.row();

            Color colorViolet = new Color(195 / 255f, 195 / 255f, 251 / 255f, 1);
            Color colorBlack = new Color(Color.BLACK);
            Color colorYellow = new Color(241 / 255f, 222 / 255f, 169 / 255f, 1);
            Color colorGray = new Color(229 / 255f, 229 / 255f, 229 / 255f, 1);
            Color colorGreen = new Color(178 / 255f, 220 / 255f, 189 / 255f, 1);

            TextButton cell = new TextButton("Rank", skinSgxTable);
            cell.getLabel().setColor(Color.BLACK);
            cell.setColor(colorViolet);
            cell.setWidth(SCREEN_WIDTH * 5 / 100);
            cell.setHeight(cellHeight);
            tableStats.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

            cell = new TextButton("Player", skinSgxTable);
            cell.getLabel().setColor(Color.BLACK);
            cell.setColor(colorViolet);
            cell.setWidth(SCREEN_WIDTH * 16 / 100);
            cell.setHeight(SCREEN_HEIGHT * 6 / 100);
            tableStats.add(cell).height(Value.percentHeight(1.2f)).colspan(2).center().fill();

            cell = new TextButton("SCORE", skinSgxTable);
            cell.getLabel().setColor(Color.BLACK);
            cell.setColor(colorViolet);
            cell.setWidth(SCREEN_WIDTH * 7 / 100);
            cell.setHeight(cellHeight);
            tableStats.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();
            tableStats.row();

            Iterator iter = Main.tabPlayers.iterator();

            Color currentColor = colorYellow;
            Color colorLabel = colorBlack;

            boolean isNext = true;

            // Hall of fame is empty
            if (!iter.hasNext()) {
                Texture transparency = new Texture(Gdx.files.internal("profile/transparency.jpg"));
                currentColor = Color.CLEAR;
                cell = new TextButton("", skinSgxTable);
                cell.getLabel().setColor(currentColor);
                tableStats.add(cell).fill();
                cell.setColor(currentColor);
                TextureRegionDrawable imageAvatar = new TextureRegionDrawable(new TextureRegion(transparency));
                ImageButton avatarButton = new ImageButton(imageAvatar);
                avatarButton.setSize(SCREEN_HEIGHT * 6 / 100, SCREEN_HEIGHT * (6 / 100) * 95 / 100);
                avatarButton.getImage().setColor(currentColor);
                tableStats.add(avatarButton).height(Value.percentHeight(1.27f)).width(Value.percentWidth(1.24f)).fill();
                Label labelEmpty = new Label("", skinSgxTable, "title-white");
                tableStats.add(labelEmpty).height(Value.percentHeight(1.3f)).width(SCREEN_WIDTH * 16 / 100).fill();
                cell = new TextButton("", skinSgxTable);
                cell.setColor(currentColor);
                cell.setHeight(cellHeight);
                tableStats.add(cell).height(Value.percentHeight(1.3f)).fill();
                tableStats.row();

                Label label0 = new Label("", skinSgxTable, "title-white");
                tableStats.add(label0).colspan(4).height(Value.percentHeight(1f));
                tableStats.row();
                label0 = new Label("EMPTY\n", skinSgxTable, "title-white");
                label0.setAlignment(1);
                tableStats.add(label0).colspan(4).height(Value.percentHeight(1f));
                tableStats.row();
            }

            // Hall of fame is not empty
            int i = 0;
            while ((iter.hasNext()) && (i < 5)) {
                if (isNext) {
                    currentColor = colorYellow;
                    colorLabel = colorBlack;
                    isNext = false;
                } else {
                    currentColor = colorGray;
                    colorLabel = colorBlack;
                    isNext = true;
                }
                i++;
                HumanPlayer stats = (HumanPlayer) iter.next();
                if (stats.getGlobalStats().getScore() != 0)
                    tableStats = displayHall(stats.getGlobalStats().getRank(), stats.getName(), stats.getAvatar(), stats.getGlobalStats().getScore(), tableStats, currentColor, colorLabel);
            }

            Label label0 = new Label("", skinSgxTable, "title-white");
            label0.setHeight(cellHeight);
            tableStats.add(label0).colspan(4).height(Value.percentHeight(1f));
            tableStats.row();
            tableStats.add(label0).colspan(4).height(Value.percentHeight(1f));
            tableStats.row();
            label0 = new Label("Current Players\n", skinSgxTable, "title-white");
            label0.setAlignment(1);
            tableStats.add(label0).colspan(4).height(Value.percentHeight(1f));
            tableStats.row();

            if (prefs.getBoolean("isPlayer1Logged"))
                tableStats = displayHall(player1.getGlobalStats().getRank(), player1.getName(), player1.getAvatar(), player1.getGlobalStats().getScore(), tableStats, colorGreen, colorLabel);
            if (prefs.getBoolean("isPlayer2Logged"))
                tableStats = displayHall(player2.getGlobalStats().getRank(), player2.getName(), player2.getAvatar(), player2.getGlobalStats().getScore(), tableStats, colorGreen, colorLabel);

            if (!(prefs.getBoolean("isPlayer1Logged") || prefs.getBoolean("isPlayer2Logged"))) {
                tableStats.row().padTop(cellHeight / 2);
                label0 = new Label("NONE", skinSgxTable, "title-white");
                tableStats.add(label0).colspan(4);
            }

            stage.addActor(tableStats);
            stage.addActor(buttonStats);

            buttonBack.setPosition(SCREEN_WIDTH * 7 / 100, SCREEN_HEIGHT * 10 / 100);
            buttonPlay.setPosition(SCREEN_WIDTH * 27 / 100, SCREEN_HEIGHT * 10 / 100);
            buttonStats.setPosition(SCREEN_WIDTH * 47 / 100, SCREEN_HEIGHT * 10 / 100);

            if (!(prefs.getBoolean("isPlayer1Logged") || prefs.getBoolean("isPlayer2Logged"))) {
                disableButton(buttonStats);
            }
        } else {
            buttonBack.setPosition(SCREEN_WIDTH / 2 - buttonBack.getWidth() * 3 / 2, SCREEN_HEIGHT * 10 / 100);
            buttonPlay.setPosition(SCREEN_WIDTH / 2 + buttonBack.getWidth() / 2, SCREEN_HEIGHT * 10 / 100);
        }
        stage.addActor(table);
        stage.addActor(buttonBack);
        stage.addActor(buttonPlay);
    }

    private void fillIslandNumbers() {
        for (int i = 1; i <= TOTAL_NUMBER_ISLANDS; i++) {
            islandNumbers.add(i);
        }
    }

    /**
     * Adds a new row in the hall of fame displayed in level selection screen
     *
     * @param rank         the player's rank
     * @param name         the player's name
     * @param avatar       the player's avatar
     * @param score        the player's score
     * @param tableStats   the table in which put the statistics
     * @param currentColor the color of the button
     * @param colorLabel   the color of the label
     * @return the full table
     */
    private Table displayHall(int rank, String name, String avatar, int score, Table tableStats, Color currentColor, Color colorLabel) {
        TextButton cell;

        cell = new TextButton(String.valueOf(rank), skinSgxTable);
        cell.getLabel().setColor(colorLabel);
        tableStats.add(cell).fill();
        cell.setColor(currentColor);

        TextureRegionDrawable imageAvatar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(avatar))));
        ImageButton avatarButton = new ImageButton(imageAvatar);
        avatarButton.setSize(SCREEN_HEIGHT * 6 / 100, SCREEN_HEIGHT * (6 / 100) * 95 / 100);
        cell.setColor(currentColor);
        tableStats.add(avatarButton).height(Value.percentHeight(1.27f)).width(Value.percentWidth(1.24f)).fill();
        cell = new TextButton(String.valueOf(name), skinSgxTable);
        cell.setClip(true);
        cell.setColor(currentColor);
        cell.getLabel().setColor(colorLabel);
        cell.setHeight(cellHeight);
        tableStats.add(cell).height(Value.percentHeight(1.3f)).width(SCREEN_WIDTH * 16 / 100).fill();
        cell = new TextButton(String.valueOf(score), skinSgxTable);
        cell.setColor(currentColor);
        cell.getLabel().setColor(colorLabel);
        cell.setHeight(cellHeight);
        tableStats.add(cell).height(Value.percentHeight(1.3f)).fill();
        tableStats.row();
        tableStats.setClip(true);
        return tableStats;
    }

    /**
     * Displays the window containing the statistics of the currently logged players
     */
    private void showStats() {
        Menu.disableButton(buttonBack, buttonPlay, buttonStats);
        Menu.disableBox(selectBoxIsland, selectBoxDifficulty1, selectBoxDifficulty2, selectBoxPlayer, selectBoxNumber);

        Stack contentBack = new Stack();
        final Table tableBack = new Table();
        tableBack.setBackground(backgroundGrey);
        contentBack.addActor(tableBack);
        tableBackground = new Table();
        tableBackground.setFillParent(true);
        tableBackground.add(contentBack).size(SCREEN_HEIGHT * 70 / 100, SCREEN_HEIGHT * 76 / 100);
        stage.addActor(tableBackground);
        mainTableStats.clear();
        stage.addActor(mainTableStats);
        mainTableStats.setFillParent(true);

        Label labelStats = new Label("Statistics", skinSgx, "title-white");
        Label labelIsland = new Label("Island ", skinSgx, "white");

        selectBoxPlayerStats = new SelectBox<String>(skinSgx);
        selectBoxPlayerStats.setItems(playerNames);
        humanPlayerStats = selectBoxPlayerStats.getItems().first();
        ChangeListener selectBoxPlayerStatsListener = new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                humanPlayerStats = selectBoxPlayerStats.getSelected();
                cellTableTabs.clearActor();
                cellContent.clearActor();
                Stack content = createStatContent(islandNumberStats, humanPlayerStats);
                cellTableTabs.setActor(tableTabs);
                cellContent.setActor(content);
            }
        };
        selectBoxPlayerStats.addListener(selectBoxPlayerStatsListener);

        selectBoxIslandStats = new SelectBox<Integer>(skinSgx);
        selectBoxIslandStats.setItems(islandNumbers);
        islandNumberStats = selectBoxIslandStats.getItems().first();
        ChangeListener selectBoxIslandStatsListener = new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                soundButton2.play(0.2f);
                islandNumberStats = selectBoxIslandStats.getSelected();
                cellTableTabs.clearActor();
                cellContent.clearActor();
                Stack content = createStatContent(islandNumberStats, humanPlayerStats);
                cellTableTabs.setActor(tableTabs);
                cellContent.setActor(content);
            }
        };
        selectBoxIslandStats.addListener(selectBoxIslandStatsListener);

        Stack contentTop = new Stack();
        final Table tableTop = new Table();
        tableTop.setBackground(backgroundGrey);
        labelStats.setAlignment(1);
        tableTop.add(labelStats).width(Value.percentWidth(1f)).height(Value.percentHeight(1f)).padBottom(SCREEN_HEIGHT * 1 / 100);
        tableTop.row();
        tableTop.add(selectBoxPlayerStats).height(Value.percentHeight(1f));
        contentTop.addActor(tableTop);
        Stack contentTopIsland = new Stack();
        final Table tableTopIsland = new Table();
        tableTopIsland.setBackground(backgroundGrey);
        labelIsland.setWidth(SCREEN_HEIGHT * 70 / 100 / 2);
        tableTopIsland.add(labelIsland).height(Value.percentHeight(1f)).padBottom(SCREEN_HEIGHT * 1 / 100);
        tableTopIsland.add(selectBoxIslandStats).height(Value.percentHeight(1f)).padBottom(SCREEN_HEIGHT * 1 / 100);
        contentTopIsland.addActor(tableTopIsland);

        Stack content;
        content = createStatContent(islandNumberStats, humanPlayerStats);

        mainTableStats.add(contentTop).size(SCREEN_HEIGHT * 70 / 100, labelStats.getHeight() + selectBoxPlayerStats.getHeight() + SCREEN_HEIGHT * 3 / 100);
        mainTableStats.row();
        mainTableStats.add(contentTopIsland).size(SCREEN_HEIGHT * 70 / 100, selectBoxIslandStats.getHeight() + SCREEN_HEIGHT * 3 / 100);
        mainTableStats.row();
        mainTableStats.add(tableTabs);
        mainTableStats.row();
        mainTableStats.add(content).size(SCREEN_HEIGHT * 70 / 100, SCREEN_HEIGHT * 77 / 100 - buttonIsland.getHeight() - (labelStats.getHeight() + selectBoxPlayerStats.getHeight() + SCREEN_HEIGHT * 7 / 100));

        cellTableTabs = mainTableStats.getCell(tableTabs);
        cellContent = mainTableStats.getCell(content);
    }

    /**
     * Creates the stack for the window displaying logged players statistics
     * It contains the selected player's global statistics and the ones for the selected island
     *
     * @param islandNumber     the number displayed in the select box
     * @param humanPlayerStats the username displayed in the select box
     * @return the stack created
     */
    private Stack createStatContent(int islandNumber, String humanPlayerStats) {
        isInIsland = true;
        HumanPlayer player;

        if (humanPlayerStats.equals(player1.getName()))
            player = player1;
        else
            player = player2;

        LevelStats playerStatistics = player.getListLevelStats(islandNumber);
        LinkedHashMap<String, Integer> levelS = playerStatistics.getStats();
        LinkedHashMap<String, Integer> globalS = player.getGlobalStats().getStats();

        final Stack content = new Stack();

        buttonIsland = new TextButton("Island " + islandNumberStats, skinSgx, "number");
        buttonIsland.setDisabled(true);
        buttonIsland.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                isInIsland = true;
                buttonIsland.setDisabled(true);
                buttonGlobal.setDisabled(false);
                content.swapActor(containerGlobal, containerIsland);
            }
        });
        buttonGlobal = new TextButton("Global", skinSgx, "number");
        buttonGlobal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                isInIsland = false;
                buttonIsland.setDisabled(false);
                buttonGlobal.setDisabled(true);
                content.swapActor(containerIsland, containerGlobal);
            }
        });
        tableTabs = new Table();
        tableTabs.add(buttonIsland).width((SCREEN_HEIGHT * 70 / 100) / 2);
        tableTabs.add(buttonGlobal).width((SCREEN_HEIGHT * 70 / 100) / 2);

        TextButton buttonStatBack = new TextButton("Back", skinSgxTable, "number");
        buttonStatBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                tableBackground.remove();
                mainTableStats.remove();
                Menu.enableButton(buttonBack, buttonPlay, buttonStats);
                enableBox(selectBoxIsland, selectBoxNumber);

                if (selectBoxNumber.getSelected() == 1) {
                    enableBox(selectBoxDifficulty1);
                    disableBox(selectBoxDifficulty2);

                    if (prefs.getBoolean("isPlayer1Logged")
                            || prefs.getBoolean("isPlayer2Logged")) {
                        enableBox(selectBoxPlayer);
                    }
                } else if (selectBoxNumber.getSelected() == 2) {
                    disableBox(selectBoxDifficulty1, selectBoxDifficulty2, selectBoxPlayer);
                } else {
                    enableBox(selectBoxDifficulty1, selectBoxDifficulty2);
                    disableBox(selectBoxPlayer);
                }
            }
        });
        final String MINIMUM_NUMBER_OF = "Minimum number of ";
        final String MAXIMUM_NUMBER_OF = "Maximum number of ";
        final String LOST_UNITS = "lost units";
        final String L0 = " 0";
        final String L1 = " 1";
        final String L2 = " 2";
        final String L3 = " 3";
        final String AVERAGE_NUMBER_OF = "Average number of ";

        // Texts displayed for all statistics

        final String GAMES = "Games";
        final String WINS = "Wins";
        final String DEFEATS = "Defeats";
        final String MIN_TURNS = MINIMUM_NUMBER_OF + "turns";
        final String MAX_LANDS_TURN = MAXIMUM_NUMBER_OF + "lands/turn";
        final String MAX_TREES = MAXIMUM_NUMBER_OF + "cut trees";
        final String MIN_ARMY = "Minimum army value";
        final String MAX_ARMY = "Maximum army value";
        final String MAX_LOST_L0 = MAXIMUM_NUMBER_OF + LOST_UNITS + L0;
        final String MAX_LOST_L1 = MAXIMUM_NUMBER_OF + LOST_UNITS + L1;
        final String MAX_LOST_L2 = MAXIMUM_NUMBER_OF + LOST_UNITS + L2;
        final String MAX_LOST_L3 = MAXIMUM_NUMBER_OF + LOST_UNITS + L3;
        final String MAX_LOST_UNITS = MAXIMUM_NUMBER_OF + LOST_UNITS;
        final String MAX_L0 = MAXIMUM_NUMBER_OF + "units" + L0;
        final String MAX_L1 = MAXIMUM_NUMBER_OF + "units" + L1;
        final String MAX_L2 = MAXIMUM_NUMBER_OF + "units" + L2;
        final String MAX_L3 = MAXIMUM_NUMBER_OF + "units" + L3;
        final String MAX_UNITS = MAXIMUM_NUMBER_OF + "units";

        final String AVG_TURNS = AVERAGE_NUMBER_OF + "turns";
        final String AVG_LANDS_TURN = AVERAGE_NUMBER_OF + "lands/turn";
        final String AVG_TREES = AVERAGE_NUMBER_OF + "cut trees";
        final String AVG_ARMY = "Average army value";
        final String AVG_L0 = AVERAGE_NUMBER_OF + "units" + L0;
        final String AVG_L1 = AVERAGE_NUMBER_OF + "units" + L1;
        final String AVG_L2 = AVERAGE_NUMBER_OF + "units" + L2;
        final String AVG_L3 = AVERAGE_NUMBER_OF + "units" + L3;
        final String AVG_UNITS = AVERAGE_NUMBER_OF + "units";
        final String AVG_LOST_L0 = AVERAGE_NUMBER_OF + LOST_UNITS + " 0";
        final String AVG_LOST_L1 = AVERAGE_NUMBER_OF + LOST_UNITS + " 1";
        final String AVG_LOST_L2 = AVERAGE_NUMBER_OF + LOST_UNITS + " 2";
        final String AVG_LOST_L3 = AVERAGE_NUMBER_OF + LOST_UNITS + " 3";
        final String AVG_LOST_UNITS = AVERAGE_NUMBER_OF + LOST_UNITS;
        final String AVG_LEFT_L0 = AVERAGE_NUMBER_OF + LEFT_UNITS + L0;
        final String AVG_LEFT_L1 = AVERAGE_NUMBER_OF + LEFT_UNITS + L1;
        final String AVG_LEFT_L2 = AVERAGE_NUMBER_OF + LEFT_UNITS + L2;
        final String AVG_LEFT_L3 = AVERAGE_NUMBER_OF + LEFT_UNITS + L3;
        final String AVG_LEFT_UNITS = AVERAGE_NUMBER_OF + LEFT_UNITS;

        labelsIsland = new ArrayList<Label>();
        labelsGlobal = new ArrayList<Label>();
        statsList = new LinkedHashMap<String, String>();

        /*
            Statistics displayed to the player, in that order (after Games statistic which is not in
            the label list because it is not added to the table in the same way as the other labels -
            see createContainer(...) method)
        */
        statsList.put(WINS, Statistics.WINS);
        statsList.put(DEFEATS, Statistics.DEFEATS);

        statsList.put(MIN_TURNS, Statistics.MIN_TURNS);
        statsList.put(AVG_TURNS, Statistics.TURNS);

        statsList.put(AVG_LANDS_TURN, Statistics.LANDS_TURN);
        statsList.put(MAX_LANDS_TURN, Statistics.MAX_LANDS_TURN);

        statsList.put(AVG_TREES, Statistics.TREES);
        statsList.put(MAX_TREES, Statistics.MAX_TREES);

        statsList.put(MIN_ARMY, Statistics.MIN_ARMY);
        statsList.put(AVG_ARMY, Statistics.ARMY);
        statsList.put(MAX_ARMY, Statistics.MAX_ARMY);

        statsList.put(AVG_L0, Statistics.L0);
        statsList.put(AVG_L1, Statistics.L1);
        statsList.put(AVG_L2, Statistics.L2);
        statsList.put(AVG_L3, Statistics.L3);
        statsList.put(AVG_UNITS, Statistics.UNITS);

        statsList.put(MAX_L0, Statistics.MAX_L0);
        statsList.put(MAX_L1, Statistics.MAX_L1);
        statsList.put(MAX_L2, Statistics.MAX_L2);
        statsList.put(MAX_L3, Statistics.MAX_L3);
        statsList.put(MAX_UNITS, Statistics.MAX_UNITS);

        statsList.put(AVG_LOST_L0, Statistics.LOST_L0);
        statsList.put(AVG_LOST_L1, Statistics.LOST_L1);
        statsList.put(AVG_LOST_L2, Statistics.LOST_L2);
        statsList.put(AVG_LOST_L3, Statistics.LOST_L3);
        statsList.put(AVG_LOST_UNITS, Statistics.LOST_UNITS);

        statsList.put(MAX_LOST_L0, Statistics.MAX_LOST_L0);
        statsList.put(MAX_LOST_L1, Statistics.MAX_LOST_L1);
        statsList.put(MAX_LOST_L2, Statistics.MAX_LOST_L2);
        statsList.put(MAX_LOST_L3, Statistics.MAX_LOST_L3);
        statsList.put(MAX_LOST_UNITS, Statistics.MAX_LOST_UNITS);

        statsList.put(AVG_LEFT_L0, Statistics.L0);
        statsList.put(AVG_LEFT_L1, Statistics.L1);
        statsList.put(AVG_LEFT_L2, Statistics.L2);
        statsList.put(AVG_LEFT_L3, Statistics.L3);
        statsList.put(AVG_LEFT_UNITS, Statistics.UNITS);

        String colon = " : ";

        // Creates all labels
        Label labelScroll = new Label("", skinSgx);
        Label labelScrollG = new Label("", skinSgx);
        Label labelGames = new Label(GAMES + colon + levelS.get(Statistics.GAMES), skinSgx, "white");
        Label labelGamesG = new Label(GAMES + colon + globalS.get(Statistics.GAMES), skinSgx, "white");
        createLabel(labelsIsland, levelS, playerStatistics, player);
        createLabel(labelsGlobal, globalS, playerStatistics, player);

        // Fills containerIsland and containerGlobal
        createContainer(labelsIsland, labelGames, labelScroll, buttonStatBack);
        createContainer(labelsGlobal, labelGamesG, labelScrollG, buttonStatBack);

        tableButtonBack = new Table();
        tableButtonBack.add(buttonStatBack).fill().width(SCREEN_HEIGHT * 70 / 100).height(Value.percentHeight(1.2f));
        tableButtonBack.bottom();

        if (isInIsland) {
            buttonIsland.setDisabled(true);
            buttonGlobal.setDisabled(false);
            content.addActor(containerGlobal);
            content.addActor(containerIsland);
        } else {
            buttonIsland.setDisabled(false);
            buttonGlobal.setDisabled(true);
            content.addActor(containerIsland);
            content.addActor(containerGlobal);
        }
        content.addActor(tableButtonBack);

        ChangeListener tab_listener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tableIsland.setVisible(buttonIsland.isChecked());
                tableGlobal.setVisible(buttonGlobal.isChecked());
            }
        };
        buttonIsland.addListener(tab_listener);
        buttonGlobal.addListener(tab_listener);

        // Lets only one tab button be checked at a time
        ButtonGroup tabs = new ButtonGroup();
        tabs.setMinCheckCount(1);
        tabs.setMaxCheckCount(1);
        tabs.add(buttonIsland);
        tabs.add(buttonGlobal);

        return content;
    }

    /**
     * Creates the labels for the window displaying players statistics and put them in a list
     * Each label displays a description of the statistic with the corresponding value in the
     * hashmap containing all statistics (excepted for the average ones)
     *
     * @param labelList    the list of the labels to display
     * @param hashmapStats the hashmap of statistics
     */
    private void createLabel(ArrayList<Label> labelList, LinkedHashMap<String, Integer> hashmapStats,
                             LevelStats levelStat, HumanPlayer player) {
        int i = 0;

        for (Map.Entry<String, String> entry : statsList.entrySet()) {
            String value = entry.getValue();

            // Makes different colors in the same label
            skinSgx.getFont("font").getData().markupEnabled = true;
            Label.LabelStyle style = new Label.LabelStyle(skinSgx.getFont("font"), null);
            String colorWhite = "[#ffffff]";
            String colorBlue = "[#aec0ff]";
            String colorText;
            if (i % 2 == 0)
                colorText = colorBlue;
            else
                colorText = colorWhite;
            String text = colorText + entry.getKey() + " : ";

            // Average statistics are calculated, not stored in a hashmap
            if (text.contains(AVERAGE_BEGINNING)) {
                if (text.contains(LEFT_UNITS)) {
                    // The average statistic is calculated with statistics from LevelStats (for a specific world)
                    if (hashmapStats.equals(levelStat.getStats()))

                        labelList.add(new Label(text + levelStat.calculateAvgLeft(value), style));

                        // The average statistic is calculated with statistics from GlobalStats
                    else
                        labelList.add(new Label(text + player.getGlobalStats().calculateAvgGlobal
                                (value, player), style));
                } else {
                    // The average statistic is calculated with statistics from LevelStats (for a specific world)
                    if (hashmapStats.equals(levelStat.getStats()))

                        labelList.add(new Label(text + levelStat.calculateAvg(value,
                                levelStat.getStats()), style));

                        // The average statistic is calculated with statistics from GlobalStats
                    else {
                        labelList.add(new Label(text + player.getGlobalStats().calculateAvgGlobal
                                (value, player), style));
                    }
                }
            }
            // Other statistics are retrieved from the hashmap
            else
                labelList.add(new Label(text + hashmapStats.get(value), style));
            i++;
        }
    }

    /**
     * Fills the container for the Island tab or the Global one, in the window displaying players statistics
     *
     * @param labelList      the list of the labels to display
     * @param labGames       the label for Games statistic
     * @param labScroll      the label to display the scroll
     * @param buttonStatBack the button Back
     */
    private void createContainer(ArrayList labelList, Label labGames, Label labScroll, Button buttonStatBack) {
        Table table = new Table();
        Table container = new Table(skinSgxTable);

        if (labelList == labelsIsland) {
            tableIsland = table;
            containerIsland = container;
        } else {
            tableGlobal = table;
            containerGlobal = container;
        }
        table = new Table();
        table.left();
        table.setBackground(backgroundGrey);
        table.add(labGames).left().padLeft(SCREEN_WIDTH * 2 / 100).padTop(SCREEN_HEIGHT * 3 / 100);
        addLabelToTableStat(table, labelList);
        table.row().padTop(buttonStatBack.getHeight() * 6 / 5);
        table.add(labScroll).left().padLeft(SCREEN_WIDTH * 2 / 100);

        ScrollPane paneIsland = new ScrollPane(table, skinSgxTable, "no-bg");
        container.setFillParent(true);
        container.add(paneIsland).fill().expand();
    }

    /**
     * Adds the labels of statistics from the list to the table of statistics
     *
     * @param table  the table of statistics
     * @param labels the list of labels to add
     */
    private void addLabelToTableStat(Table table, ArrayList labels) {
        for (int i = 0; i < labels.size(); i++) {
            table.row();
            table.add((Actor) labels.get(i)).left().padLeft(SCREEN_WIDTH * 2 / 100);
        }
    }

    /**
     * Gives the level currently played
     *
     * @return the level played
     */
    public static int getCurrentIslandNumber() {
        return currentIslandNumber;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.setProjectionMatrix(Main.camera.combined);
        sprite.draw(batch);
        batch.end();
        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            skinSgx.getFont("title").getData().setScale(SCREEN_WIDTH * 0.8f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.8f / VIRTUAL_HEIGHT);
            skinSgxTable.getFont("font").getData().setScale(SCREEN_WIDTH * 1f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 1f / VIRTUAL_HEIGHT);
            skinSgxTable.getFont("title").getData().setScale(SCREEN_WIDTH * 0.9f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.9f / VIRTUAL_HEIGHT);
        }
        init();
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
    }
}