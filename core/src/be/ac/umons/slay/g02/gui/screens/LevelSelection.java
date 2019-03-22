package be.ac.umons.slay.g02.gui.screens;

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

import be.ac.umons.slay.g02.gui.Main;
import be.ac.umons.slay.g02.players.GlobalStats;
import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.LevelStats;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.isAccountEnabled;
import static be.ac.umons.slay.g02.gui.Main.prefs;
import static be.ac.umons.slay.g02.gui.Main.skinSgx;
import static be.ac.umons.slay.g02.gui.Main.skinSgxTable;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.soundButton3;
import static be.ac.umons.slay.g02.gui.Main.stage;
import static be.ac.umons.slay.g02.gui.screens.Menu.backgroundGrey;
import static be.ac.umons.slay.g02.gui.screens.Menu.disableBox;
import static be.ac.umons.slay.g02.gui.screens.Menu.enableBox;
import static be.ac.umons.slay.g02.gui.screens.Menu.isPlayer1Logged;
import static be.ac.umons.slay.g02.gui.screens.Menu.isPlayer2Logged;
import static be.ac.umons.slay.g02.gui.screens.Menu.player1;
import static be.ac.umons.slay.g02.gui.screens.Menu.player2;

// classe qui affiche l'ecran de selection de niveau
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

    // pour savoir quelle carte afficher pour la preview et quand on clique sur Play
    private int currentIslandNumber = 1;
    // dans la fenetre des stats
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
    private Array<Integer> islandNumbers;
    private Array<String> playerNames = new Array<String>();
    private ArrayList labelsIsland;
    private ArrayList labelsGlobal;
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

    public static final int TOTAL_NUMBER_ISLANDS = 10;

    // dans la fenetre de stats, c'est pas forcement le meme que celui choisi dans l'ecran de selection
    private String humanPlayerStats;
    // n'est utile que s'il n'y a qu'un joueur humain dans la partie, par defaut c'est P1
    private String humanPlayer;
    // permet de savoir s'il faut creer des IA au debut du jeu
    public static int numberHumans = 0;
    // permet de selectionner le bon type d'IA, on peut mettre autre chose que int, par defaut c'est Easy = 1 (Medium = 2 ...)
    public static int difficulty1 = 1;
    public static int difficulty2 = 1;



    public LevelSelection(Game aGame) {
        game = aGame;

        init();
    }

    private void init() {

        stage.clear();

        int buttonGapY = SCREEN_HEIGHT * 10 / 100;
        cellHeight = SCREEN_HEIGHT * 6 / 100;

        // background
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
        Label labelBoxPlayer = new Label("", skinSgx, "title");
        selectBoxIsland = new SelectBox<Integer>(skinSgx);
        islandNumbers = new Array<Integer>();
        fillIslandNumbers();
        selectBoxIsland.setItems(islandNumbers);
        ChangeListener selectBoxIslandListener = new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
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
        Array<Integer> numberOfHumans = new Array<Integer>();
        numberOfHumans.add(0, 1, 2);
        selectBoxNumber.setItems(numberOfHumans);
        ChangeListener selectBoxNumberListener = new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));

                if (selectBoxNumber.getSelected() == 1 /* et qu'au moins une personne est connectee ! - A RAJOUTER */) {
                    enableBox(selectBoxDifficulty1, selectBoxPlayer);
                    disableBox(selectBoxDifficulty2);
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

        selectBoxPlayer = new SelectBox<String>(skinSgx);

        if (isPlayer1Logged)
            playerNames.add(player1.getName());

        if (isPlayer2Logged)
            playerNames.add(player2.getName());

        if (isPlayer1Logged || isPlayer2Logged) {
            selectBoxPlayer.setItems(playerNames);
            humanPlayer = selectBoxPlayer.getItems().first();
            ChangeListener selectBoxPlayerListener = new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                    soundButton1.play(prefs.getFloat("volume", 0.2f));
                    humanPlayer = selectBoxPlayer.getSelected();
                }
            };
            selectBoxPlayer.addListener(selectBoxPlayerListener);
            disableBox(selectBoxPlayer);
        }
        selectBoxDifficulty1 = new SelectBox<String>(skinSgx);
        Array<String> difficultyDegrees = new Array<String>();
        difficultyDegrees.add("EASY", "MEDIUM", "ADVANCED", "RANDOM");
        selectBoxDifficulty1.setItems(difficultyDegrees);
        ChangeListener selectBoxDifficultyListener1 = new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                difficulty1 = selectBoxDifficulty1.getSelectedIndex() + 1;
            }
        };
        selectBoxDifficulty1.addListener(selectBoxDifficultyListener1);

        selectBoxDifficulty2 = new SelectBox<String>(skinSgx);
        selectBoxDifficulty2.setItems(difficultyDegrees);
        ChangeListener selectBoxDifficultyListener2 = new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
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
                game.setScreen(new GameScreen(game, String.format("g02_%02d", currentIslandNumber)));
            }
        });

        // partie gauche
        final Table table = new Table();
        table.setFillParent(true);
        table.left().padBottom(SCREEN_HEIGHT * 22 / 100);
        table.setSize(SCREEN_WIDTH / 2, SCREEN_HEIGHT);

        labelIsland.setAlignment(1);
        table.add(labelIsland);
        table.add(selectBoxIsland);

        table.add(levelPreview).padLeft(SCREEN_WIDTH * 7 / 100).height(SCREEN_HEIGHT * 25 / 100).width(SCREEN_HEIGHT * 25 / 100);
        table.row().pad(buttonGapY, 0, 0, 0);
        table.add(labelHuman).width(SCREEN_WIDTH * 25 / 100);
        table.add(selectBoxNumber);
        if (isPlayer1Logged || isPlayer2Logged) {
            table.add(selectBoxPlayer).padLeft(SCREEN_WIDTH * 5 / 100);
        } else table.add(labelBoxPlayer).colspan(3).padLeft(SCREEN_WIDTH * 5 / 100);
        table.row().pad(SCREEN_HEIGHT * 17 / 100, 0, 0, 0);
        table.add(labelDifficulty);
        table.add(selectBoxDifficulty1).left().colspan(2);
        table.add(selectBoxDifficulty2).left().colspan(2);

        cellPreview = table.getCell(levelPreview);


        buttonBack.setWidth(SCREEN_WIDTH * 12 / 100);
        buttonPlay.setWidth(SCREEN_WIDTH * 12 / 100);

        if (!isAccountEnabled) {
            table.center().padRight(SCREEN_WIDTH * 6 / 100);
            buttonBack.setPosition(SCREEN_WIDTH / 2 - buttonBack.getWidth() * 3 / 2, SCREEN_HEIGHT * 10 / 100);
            buttonPlay.setPosition(SCREEN_WIDTH / 2 + buttonBack.getWidth() / 2, SCREEN_HEIGHT * 10 / 100);
        } else if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            // partie droite
            buttonStats = new TextButton("Statistics", skinSgx, "big");
            buttonStats.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundButton1.play(prefs.getFloat("volume", 0.2f));
                    showStats();
                }
            });
            buttonStats.setWidth(SCREEN_WIDTH * 12 / 100); // TODO fixed lenght

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

            if (isPlayer1Logged) {
                tableStats = displayHall(player1.getGlobalStats().getRank(), player1.getName(), player1.getAvatar(), player1.getGlobalStats().getScore(), tableStats, colorGreen, colorLabel);
            }

            if (isPlayer2Logged) {
                tableStats = displayHall(player2.getGlobalStats().getRank(), player2.getName(), player2.getAvatar(), player2.getGlobalStats().getScore(), tableStats, colorGreen, colorLabel);

            }
            stage.addActor(tableStats);
            stage.addActor(buttonStats);

            buttonBack.setPosition(SCREEN_WIDTH * 7 / 100, SCREEN_HEIGHT * 10 / 100);
            buttonPlay.setPosition(SCREEN_WIDTH * 27 / 100, SCREEN_HEIGHT * 10 / 100);
            buttonStats.setPosition(SCREEN_WIDTH * 47 / 100, SCREEN_HEIGHT * 10 / 100);
        } else {
            buttonBack.setPosition(SCREEN_WIDTH / 2 - buttonBack.getWidth() * 3 / 2, SCREEN_HEIGHT * 10 / 100);
            buttonPlay.setPosition(SCREEN_WIDTH / 2 + buttonBack.getWidth() / 2, SCREEN_HEIGHT * 10 / 100);
            // TODO add button to go to hall of fame in portrait mode
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

    // rajoute une nouvelle ligne dans le hall of fame
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
        // coupe le nom s'il est trop long
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

    private HumanPlayer searchPlayer(String name) {
        Iterator iter = Main.tabPlayers.iterator();
        while (iter.hasNext()) {

            HumanPlayer statis = (HumanPlayer) iter.next();
            if (statis.getName().equals(name)) {
                return statis;
            }
        }
        return null;
    }

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

    private Stack createStatContent(int islandNumber, String humanPlayerStats) {
        isInIsland = true;
        HumanPlayer player;

        if (humanPlayerStats.equals(player1.getName()))
            player = player1;
        else
            player = player2;

        LevelStats levelS = (LevelStats) player.getListLevelStats(islandNumber);
        GlobalStats globalS = player.getGlobalStats();

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

                if (selectBoxNumber.getSelected() == 1 /* et qu'au moins une personne est connectee ! - A RAJOUTER */) {
                    enableBox(selectBoxDifficulty1, selectBoxPlayer);
                    disableBox(selectBoxDifficulty2);
                } else if (selectBoxNumber.getSelected() == 2) {
                    disableBox(selectBoxDifficulty1, selectBoxDifficulty2, selectBoxPlayer);
                } else {
                    enableBox(selectBoxDifficulty1, selectBoxDifficulty2);
                    disableBox(selectBoxPlayer);
                }
            }
        });

        labelsIsland = new ArrayList();
        Label labelScroll = new Label("", skinSgx, "white");
        Label labelGames = new Label("Games : " + levelS.getTotalGames(), skinSgx, "white");
        Label labelWins = new Label("Wins : " + levelS.getTotalWins(), skinSgx, "white");
        labelsIsland.add(labelWins);
        Label labelDefeats = new Label("Defeats : " + levelS.getTotalDefeats(), skinSgx, "white");
        labelsIsland.add(labelDefeats);
        Label labelMinTurns = new Label("Minimum number of turns : " + levelS.getMinTurns(), skinSgx, "white");
        labelsIsland.add(labelMinTurns);
        Label labelAvgTurns = new Label("Average number of turns : " + levelS.getAvgTurns(), skinSgx, "white");
        labelsIsland.add(labelAvgTurns);
        Label labelAvgLands = new Label("Average number of lands/turn : " + levelS.getAvgLandsTurn(), skinSgx, "white");
        labelsIsland.add(labelAvgLands);
        Label labelMaxLands = new Label("Maximum number of lands/turn : " + levelS.getMaxLandsTurn(), skinSgx, "white");
        labelsIsland.add(labelMaxLands);
        Label labelAvgTrees = new Label("Average number of cut trees : " + levelS.getAvgTrees(), skinSgx, "white");
        labelsIsland.add(labelAvgTrees);
        Label labelMaxTrees = new Label("Maximum number of cut trees : " + levelS.getMaxTrees(), skinSgx, "white");
        labelsIsland.add(labelMaxTrees);
        Label labelAvgMoney = new Label("Average amount of earned money : " + levelS.getAvgTotalMoney(), skinSgx, "white");
        labelsIsland.add(labelAvgMoney);
        Label labelMaxMoney = new Label("Maximum amount of earned money : " + levelS.getMaxTotalMoney(), skinSgx, "white");
        labelsIsland.add(labelMaxMoney);
        Label labelAvgSavings = new Label("Average amount of savings : " + levelS.getAvgSavings(), skinSgx, "white");
        labelsIsland.add(labelAvgSavings);
        Label labelMaxSavings = new Label("Maximum amount of savings : " + levelS.getTotalSavings(), skinSgx, "white");
        labelsIsland.add(labelMaxSavings);
        Label labelMinArmy = new Label("Minimum army value : " + levelS.getMinArmy(), skinSgx, "white");
        labelsIsland.add(labelMinArmy);
        Label labelAvgArmy = new Label("Average army value : " + levelS.getAvgArmy(), skinSgx, "white");
        labelsIsland.add(labelAvgArmy);
        Label labelMaxArmy = new Label("Maximum army value : " + levelS.getMaxArmy(), skinSgx, "white");
        labelsIsland.add(labelMaxArmy);
        Label labelAvgUnits = new Label("Average number of units : " + levelS.getAvgUnits(), skinSgx, "white");
        labelsIsland.add(labelAvgUnits);
        Label labelAvgL0 = new Label("Average number of units 0 : " + levelS.getAvgL0(), skinSgx, "white");
        labelsIsland.add(labelAvgL0);
        Label labelAvgL1 = new Label("Average number of units 1 : " + levelS.getAvgL1(), skinSgx, "white");
        labelsIsland.add(labelAvgL1);
        Label labelAvgL2 = new Label("Average number of units 2 : " + levelS.getAvgL2(), skinSgx, "white");
        labelsIsland.add(labelAvgL2);
        Label labelAvgL3 = new Label("Average number of units 3 : " + levelS.getAvgL3(), skinSgx, "white");
        labelsIsland.add(labelAvgL3);
        Label labelMaxUnits = new Label("Maximum number of units : " + levelS.getMaxUnits(), skinSgx, "white");
        labelsIsland.add(labelMaxUnits);
        Label labelMaxL0 = new Label("Maximum number of units 0 : " + levelS.getMaxL0(), skinSgx, "white");
        labelsIsland.add(labelMaxL0);
        Label labelMaxL1 = new Label("Maximum number of units 1 : " + levelS.getMaxL1(), skinSgx, "white");
        labelsIsland.add(labelMaxL1);
        Label labelMaxL2 = new Label("Maximum number of units 2 : " + levelS.getMaxL2(), skinSgx, "white");
        labelsIsland.add(labelMaxL2);
        Label labelMaxL3 = new Label("Maximum number of units 3 : " + levelS.getMaxL3(), skinSgx, "white");
        labelsIsland.add(labelMaxL3);
        Label labelAvgLostUnits = new Label("Average number of lost units : " + levelS.getAvgLostUnits(), skinSgx, "white");
        labelsIsland.add(labelAvgLostUnits);
        Label labelAvgLostL0 = new Label("Average number of lost units 0 : " + levelS.getAvgLostL0(), skinSgx, "white");
        labelsIsland.add(labelAvgLostL0);
        Label labelAvgLostL1 = new Label("Average number of lost units 1 : " + levelS.getAvgLostL1(), skinSgx, "white");
        labelsIsland.add(labelAvgLostL1);
        Label labelAvgLostL2 = new Label("Average number of lost units 2 : " + levelS.getAvgLostL2(), skinSgx, "white");
        labelsIsland.add(labelAvgLostL2);
        Label labelAvgLostL3 = new Label("Average number of lost units 3 : " + levelS.getAvgLostL3(), skinSgx, "white");
        labelsIsland.add(labelAvgLostL3);
        Label labelMaxLostUnits = new Label("Maximum number of lost units : " + levelS.getMaxUnits(), skinSgx, "white");
        labelsIsland.add(labelMaxLostUnits);
        Label labelMaxLostL0 = new Label("Maximum number of lost units 0 : " + levelS.getMaxLostL0(), skinSgx, "white");
        labelsIsland.add(labelMaxLostL0);
        Label labelMaxLostL1 = new Label("Maximum number of lost units 1 : " + levelS.getMaxLostL1(), skinSgx, "white");
        labelsIsland.add(labelMaxLostL1);
        Label labelMaxLostL2 = new Label("Maximum number of lost units 2 : " + levelS.getMaxLostL2(), skinSgx, "white");
        labelsIsland.add(labelMaxLostL2);
        Label labelMaxLostL3 = new Label("Maximum number of lost units 3 : " + levelS.getMaxLostL3(), skinSgx, "white");
        labelsIsland.add(labelMaxLostL3);
        Label labelAvgLeftUnits = new Label("Average number of left units : " + levelS.getAvgLeftUnits(), skinSgx, "white");
        labelsIsland.add(labelAvgLeftUnits);
        Label labelAvgLeftL0 = new Label("Average number of left units 0 : " + levelS.getAvgLeftL0(), skinSgx, "white");
        labelsIsland.add(labelAvgLeftL0);
        Label labelAvgLeftL1 = new Label("Average number of left units 1 : " + levelS.getAvgLeftL1(), skinSgx, "white");
        labelsIsland.add(labelAvgLeftL1);
        Label labelAvgLeftL2 = new Label("Average number of left units 2 : " + levelS.getAvgLeftL2(), skinSgx, "white");
        labelsIsland.add(labelAvgLeftL2);
        Label labelAvgLeftL3 = new Label("Average number of left units 3 : " + levelS.getAvgLeftL3(), skinSgx, "white");
        labelsIsland.add(labelAvgLeftL3);

        labelsGlobal = new ArrayList();
        Label labelScrollG = new Label("", skinSgx, "white");
        Label labelGamesG = new Label("Games : " + globalS.getTotalGames(), skinSgx, "white");
        Label labelWinsG = new Label("Wins : " + globalS.getTotalWins(), skinSgx, "white");
        labelsGlobal.add(labelWinsG);
        Label labelDefeatsG = new Label("Defeats : " + globalS.getTotalDefeats(), skinSgx, "white");
        labelsGlobal.add(labelDefeatsG);
        Label labelMinTurnsG = new Label("Minimum number of turns : " + globalS.getMinTurns(), skinSgx, "white");
        labelsGlobal.add(labelMinTurnsG);
        Label labelAverTurnsG = new Label("Average number of turns : " + globalS.getAvgTurns(), skinSgx, "white");
        labelsGlobal.add(labelAverTurnsG);
        Label labelAverLandsG = new Label("Average number of lands/turn : " + globalS.getAvgLandsTurn(), skinSgx, "white");
        labelsGlobal.add(labelAverLandsG);
        Label labelMaxLandsG = new Label("Maximum number of lands/turn : " + globalS.getMaxLandsTurn(), skinSgx, "white");
        labelsGlobal.add(labelMaxLandsG);
        Label labelAverTreesG = new Label("Average number of cut trees : " + globalS.getAvgTrees(), skinSgx, "white");
        labelsGlobal.add(labelAverTreesG);
        Label labelMaxTreesG = new Label("Maximum number of cut trees : " + globalS.getMaxTrees(), skinSgx, "white");
        labelsGlobal.add(labelMaxTreesG);
        Label labelAverMoneyG = new Label("Average amount of earned money : " + globalS.getAvgTotalMoney(), skinSgx, "white");
        labelsGlobal.add(labelAverMoneyG);
        Label labelMaxMoneyG = new Label("Maximum amount of earned money : " + globalS.getMaxTotalMoney(), skinSgx, "white");
        labelsGlobal.add(labelMaxMoneyG);
        Label labelAverSavingG = new Label("Average amount of savings : " + globalS.getAvgSavings(), skinSgx, "white");
        labelsGlobal.add(labelAverSavingG);
        Label labelMaxSavingsG = new Label("Maximum amount of savings : " + globalS.getTotalSavings(), skinSgx, "white");
        labelsGlobal.add(labelMaxSavingsG);
        Label labelMinArmyG = new Label("Minimum army value : " + globalS.getMinArmy(), skinSgx, "white");
        labelsGlobal.add(labelMinArmyG);
        Label labelAvgArmyG = new Label("Average army value : " + globalS.getAvgArmy(), skinSgx, "white");
        labelsGlobal.add(labelAvgArmyG);
        Label labelMaxArmyG = new Label("Maximum army value : " + globalS.getMaxArmy(), skinSgx, "white");
        labelsGlobal.add(labelMaxArmyG);
        Label labelAvgUnitsG = new Label("Average number of units : " + globalS.getAvgUnits(), skinSgx, "white");
        labelsGlobal.add(labelAvgUnitsG);
        Label labelAvgL0G = new Label("Average number of units 0 : " + globalS.getAvgL0(), skinSgx, "white");
        labelsGlobal.add(labelAvgL0G);
        Label labelAvgL1G = new Label("Average number of units 1 : " + globalS.getAvgL1(), skinSgx, "white");
        labelsGlobal.add(labelAvgL1G);
        Label labelAvgL2G = new Label("Average number of units 2 : " + globalS.getAvgL2(), skinSgx, "white");
        labelsGlobal.add(labelAvgL2G);
        Label labelAvgL3G = new Label("Average number of units 3 : " + globalS.getAvgL3(), skinSgx, "white");
        labelsGlobal.add(labelAvgL3G);
        Label labelMaxUnitsG = new Label("Maximum number of units : " + globalS.getMaxUnits(), skinSgx, "white");
        labelsGlobal.add(labelMaxUnitsG);
        Label labelMaxL0G = new Label("Maximum number of units 0 : " + globalS.getMaxL0(), skinSgx, "white");
        labelsGlobal.add(labelMaxL0G);
        Label labelMaxL1G = new Label("Maximum number of units 1 : " + globalS.getMaxL1(), skinSgx, "white");
        labelsGlobal.add(labelMaxL1G);
        Label labelMaxL2G = new Label("Maximum number of units 2 : " + globalS.getMaxL2(), skinSgx, "white");
        labelsGlobal.add(labelMaxL2G);
        Label labelMaxL3G = new Label("Maximum number of units 3 : " + globalS.getMaxL3(), skinSgx, "white");
        labelsGlobal.add(labelMaxL3G);
        Label labelAvgLostUnitsG = new Label("Average number of lost units : " + globalS.getAvgLostUnits(), skinSgx, "white");
        labelsGlobal.add(labelAvgLostUnitsG);
        Label labelAvgLostL0G = new Label("Average number of lost units 0 : " + globalS.getAvgLostL0(), skinSgx, "white");
        labelsGlobal.add(labelAvgLostL0G);
        Label labelAvgLostL1G = new Label("Average number of lost units 1 : " + globalS.getAvgLostL1(), skinSgx, "white");
        labelsGlobal.add(labelAvgLostL1G);
        Label labelAvgLostL2G = new Label("Average number of lost units 2 : " + globalS.getAvgLostL2(), skinSgx, "white");
        labelsGlobal.add(labelAvgLostL2G);
        Label labelAvgLostL3G = new Label("Average number of lost units 3 : " + globalS.getAvgLostL3(), skinSgx, "white");
        labelsGlobal.add(labelAvgLostL3G);
        Label labelMaxLostUnitsG = new Label("Maximum number of lost units : " + globalS.getMaxUnits(), skinSgx, "white");
        labelsGlobal.add(labelMaxLostUnitsG);
        Label labelMaxLostL0G = new Label("Maximum number of lost units 0 : " + globalS.getMaxLostL0(), skinSgx, "white");
        labelsGlobal.add(labelMaxLostL0G);
        Label labelMaxLostL1G = new Label("Maximum number of lost units 1 : " + globalS.getMaxLostL1(), skinSgx, "white");
        labelsGlobal.add(labelMaxLostL1G);
        Label labelMaxLostL2G = new Label("Maximum number of lost units 2 : " + globalS.getMaxLostL2(), skinSgx, "white");
        labelsGlobal.add(labelMaxLostL2G);
        Label labelMaxLostL3G = new Label("Maximum number of lost units 3 : " + globalS.getMaxLostL3(), skinSgx, "white");
        labelsGlobal.add(labelMaxLostL3G);
        Label labelAvgLeftUnitsG = new Label("Average number of left units : " + globalS.getAvgLeftUnits(), skinSgx, "white");
        labelsGlobal.add(labelAvgLeftUnitsG);
        Label labelAvgLeftL0G = new Label("Average number of left units 0 : " + globalS.getAvgLeftL0(), skinSgx, "white");
        labelsGlobal.add(labelAvgLeftL0G);
        Label labelAvgLeftL1G = new Label("Average number of left units 1 : " + globalS.getAvgLeftL1(), skinSgx, "white");
        labelsGlobal.add(labelAvgLeftL1G);
        Label labelAvgLeftL2G = new Label("Average number of left units 2 : " + globalS.getAvgLeftL2(), skinSgx, "white");
        labelsGlobal.add(labelAvgLeftL2G);
        Label labelAvgLeftL3G = new Label("Average number of left units 3 : " + globalS.getAvgLeftL3(), skinSgx, "white");
        labelsGlobal.add(labelAvgLeftL3G);

        tableIsland = new Table();
        tableIsland.left();
        tableIsland.setBackground(backgroundGrey);
        tableIsland.add(labelGames).left().padLeft(SCREEN_WIDTH * 2 / 100).padTop(SCREEN_HEIGHT * 3 / 100);
        addLabelToTableStat(tableIsland, labelsIsland);
        tableIsland.row().padTop(buttonStatBack.getHeight() * 6 / 5);
        // affiche le scroll
        tableIsland.add(labelScroll).left().padLeft(SCREEN_WIDTH * 2 / 100);

        containerIsland = new Table(skinSgxTable);
        // no-bg pour afficher notre background, sinon il en rajoute un par dessus
        ScrollPane paneIsland = new ScrollPane(tableIsland, skinSgxTable, "no-bg");
        containerIsland.setFillParent(true);
        containerIsland.add(paneIsland).fill().expand();

        tableGlobal = new Table();
        tableGlobal.left();
        tableGlobal.setBackground(backgroundGrey);
        tableGlobal.add(labelGamesG).left().padLeft(SCREEN_WIDTH * 2 / 100).padTop(SCREEN_HEIGHT * 3 / 100);
        addLabelToTableStat(tableGlobal, labelsGlobal);
        tableGlobal.row().padTop(buttonStatBack.getHeight() * 6 / 5);
        // affiche le scroll
        tableGlobal.add(labelScrollG).left().padLeft(SCREEN_WIDTH * 2 / 100);

        containerGlobal = new Table(skinSgxTable);
        // no-bg pour afficher notre background, sinon il en rajoute un par dessus
        ScrollPane paneGlobal = new ScrollPane(tableGlobal, skinSgxTable, "no-bg");
        containerGlobal.setFillParent(true);
        containerGlobal.add(paneGlobal).fill().expand();

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

        // let only one tab button be checked at a time
        ButtonGroup tabs = new ButtonGroup();
        tabs.setMinCheckCount(1);
        tabs.setMaxCheckCount(1);
        tabs.add(buttonIsland);
        tabs.add(buttonGlobal);

        return content;
    }

    private void addLabelToTableStat(Table table, ArrayList labels) {
        for (int i = 0; i < labels.size(); i++) {
            table.row();
            table.add((Actor) labels.get(i)).left().padLeft(SCREEN_WIDTH * 2 / 100);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        // place le background correctement dans la fenetre
        batch.setProjectionMatrix(Main.camera.combined);
        sprite.draw(batch);
        batch.end();
        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        //skinSgx.getFont("title").getData().setScale(SCREEN_WIDTH * 0.8f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.8f / VIRTUAL_HEIGHT);
        //skinSgxTable.getFont("font").getData().setScale(SCREEN_WIDTH * 1f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 1f / VIRTUAL_HEIGHT);
        //skinSgxTable.getFont("title").getData().setScale(SCREEN_WIDTH * 0.9f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.9f / VIRTUAL_HEIGHT);

        init();
        stage.getViewport().update(width, height, true);
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