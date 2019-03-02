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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import be.ac.umons.slay.g02.gui.Main;
import be.ac.umons.slay.g02.players.Statis;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.isAccountEnabled;
import static be.ac.umons.slay.g02.gui.Main.skinSgx;
import static be.ac.umons.slay.g02.gui.Main.skinSgxTable;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.soundButton3;
import static be.ac.umons.slay.g02.gui.Main.stage;
import static be.ac.umons.slay.g02.gui.screens.Menu.backgroundGrey;
import static be.ac.umons.slay.g02.gui.screens.Menu.disableBox;
import static be.ac.umons.slay.g02.gui.screens.Menu.enableBox;
import static be.ac.umons.slay.g02.gui.screens.Menu.player1;

// classe qui affiche l'ecran de selection de niveau
public class LevelSelection implements Screen {

    private Game game;
    private SpriteBatch batch;
    private Sprite sprite;
    private int cellHeight;

    public int currentIslandNumber = 1;

    private Button buttonIsland;
    private Button buttonGlobal;
    private static TextButton buttonBack;
    private static TextButton buttonPlay;
    private static TextButton buttonStats;
    private static SelectBox<String> selectBoxDifficulty;
    private static SelectBox<String> selectBoxPlayer;
    private static SelectBox<Integer> selectBoxIsland;
    private static SelectBox<Integer> selectBoxNumber;
    private static SelectBox<String> selectBoxPlayerStats;
    private Array<Integer> islandNumbers;
    private Array<String> playerNames = new Array<String>();
    private static Table mainTableStats = new Table();
    private Table containerGlobal;
    private Table containerIsland;
    private Table tableButtonBack;

    // pour les tests
    public static final int TOTAL_NUMBER_ISLANDS = 10;

    // dans la fenetre de stats, c'est pas forcement le meme que celui choisi dans l'ecran de selection
    public String humanPlayerStats;
    // n'est utile que s'il n'y a qu'un joueur humain dans la partie, par defaut c'est P1
    public String humanPlayer;
    // permet de savoir s'il faut creer des IA au debut du jeu
    public int numberHumans = 0;
    // permet de selectionner le bon type d'IA, on peut mettre autre chose que int, par defaut c'est Easy = 1 (Medium = 2 ...)
    public int difficulty = 1;

    // il faudra les recuperer dans Players
    public static String player1Name = "P1";
    public static String player2Name = "WWWWWWWWWWWWWWW";


    public LevelSelection(Game aGame) {
        game = aGame;

        int buttonGapY = SCREEN_HEIGHT * 10 / 100;
        cellHeight = SCREEN_HEIGHT * 6 / 100;

        // background
        batch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("backgrounds/background.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setOrigin(0, 0);
        sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

        ImageButton levelPreview = new ImageButton(Menu.imageAnonymous);

        Label labelIsland = new Label("Island", skinSgx, "title");
        Label labelHuman = new Label("Number of human\nplayers", skinSgx, "title");
        labelHuman.setAlignment(1);
        Label labelDifficulty = new Label("Difficulty", skinSgx, "title");

        selectBoxIsland = new SelectBox<Integer>(skinSgx);
        islandNumbers = new Array<Integer>();
        fillIslandNumbers();
        selectBoxIsland.setItems(islandNumbers);
        ChangeListener selectBoxIslandListener = new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                soundButton1.play(0.2f);

                // changer la previsualisation de la carte - A RAJOUTER

                currentIslandNumber = selectBoxIsland.getSelected();
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
                soundButton1.play(0.2f);

                if (selectBoxNumber.getSelected() == 1 /* et qu'au moins une personne est connectee ! - A RAJOUTER */)
                    enableBox(selectBoxDifficulty, selectBoxPlayer);
                else if (selectBoxNumber.getSelected() == 2) {
                    disableBox(selectBoxDifficulty, selectBoxPlayer);
                } else {
                    enableBox(selectBoxDifficulty);
                    disableBox(selectBoxPlayer);
                }
                numberHumans = selectBoxNumber.getSelected();
            }
        };
        selectBoxNumber.addListener(selectBoxNumberListener);

        selectBoxPlayer = new SelectBox<String>(skinSgx);
        playerNames.add(player1Name, player2Name);
        selectBoxPlayer.setItems(playerNames);
        humanPlayer = selectBoxPlayer.getItems().first();
        ChangeListener selectBoxPlayerListener = new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                soundButton1.play(0.2f);
                humanPlayer = selectBoxPlayer.getSelected();
            }
        };
        selectBoxPlayer.addListener(selectBoxPlayerListener);
        disableBox(selectBoxPlayer);

        selectBoxDifficulty = new SelectBox<String>(skinSgx);
        Array<String> difficultyDegrees = new Array<String>();
        difficultyDegrees.add("EASY", "MEDIUM", "ADVANCED");
        selectBoxDifficulty.setItems(difficultyDegrees);
        ChangeListener selectBoxDifficultyListener = new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                soundButton1.play(0.2f);
                difficulty = selectBoxDifficulty.getSelectedIndex() + 1;
            }
        };
        selectBoxDifficulty.addListener(selectBoxDifficultyListener);

        buttonBack = new TextButton("Back", skinSgx, "big");
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                stage.clear();
                game.setScreen(new Menu(game));
            }
        });
        buttonPlay = new TextButton("Play", skinSgx, "big");
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton3.play(0.2f);
                stage.clear();
                game.setScreen(new GameScreen(game));
            }
        });

        // partie gauche
        final Table table = new Table();
        table.setFillParent(true);
        table.left().padBottom(SCREEN_HEIGHT * 22 / 100);
        table.setSize(SCREEN_WIDTH / 2, SCREEN_HEIGHT);

        labelIsland.setAlignment(1);
        table.add(labelIsland).colspan(2).fill();
        table.add(selectBoxIsland).colspan(3);
        table.add(levelPreview).colspan(3).padLeft(SCREEN_WIDTH * 7 / 100);
        table.row().pad(buttonGapY, 0, 0, 0);
        table.add(labelHuman).colspan(2).width(Value.percentWidth(1.40f)).fill();
        table.add(selectBoxNumber).colspan(3);
        table.add(selectBoxPlayer).colspan(3).padLeft(SCREEN_WIDTH * 5 / 100);
        table.row().pad(buttonGapY * 7 / 4, 0, 0, 0);
        table.add(labelDifficulty).colspan(2);
        table.add(selectBoxDifficulty).colspan(3).padLeft(SCREEN_WIDTH * 1 / 100);

        buttonBack.setWidth(SCREEN_WIDTH * 12 / 100);
        buttonPlay.setWidth(SCREEN_WIDTH * 12 / 100);

        if (!isAccountEnabled) {
            table.center().padRight(SCREEN_WIDTH * 6 / 100);
            buttonBack.setPosition(SCREEN_WIDTH / 2 - buttonBack.getWidth() * 3 / 2, SCREEN_HEIGHT * 10 / 100);
            buttonPlay.setPosition(SCREEN_WIDTH / 2 + buttonBack.getWidth() / 2, SCREEN_HEIGHT * 10 / 100);
        } else if (isAccountEnabled) {
            // partie droite
            buttonStats = new TextButton("Statistics", skinSgx, "big");
            buttonStats.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundButton1.play(0.2f);
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

            Iterator iter = Menu.tabScore.iterator();

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
                Statis stats = (Statis) iter.next();
                tableStats = displayHall(stats, tableStats, currentColor, colorLabel);
            }
            String name = "ttt";
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
            Statis statsPlayer = searchPlayer(name);
            if (null != statsPlayer) {
                tableStats = displayHall(statsPlayer, tableStats, colorGreen, colorLabel);
            }
            name = "0000";
            statsPlayer = searchPlayer(name);
            if (null != statsPlayer) {
                tableStats = displayHall(statsPlayer, tableStats, colorGreen, colorLabel);
            }

            stage.addActor(tableStats);
            stage.addActor(buttonStats);

            buttonBack.setPosition(SCREEN_WIDTH * 7 / 100, SCREEN_HEIGHT * 10 / 100);
            buttonPlay.setPosition(SCREEN_WIDTH * 27 / 100, SCREEN_HEIGHT * 10 / 100);
            buttonStats.setPosition(SCREEN_WIDTH * 47 / 100, SCREEN_HEIGHT * 10 / 100);
        }

        stage.addActor(table);
        stage.addActor(buttonBack);
        stage.addActor(buttonPlay);
    }

    private void fillIslandNumbers() {
        for (int i = 1; i <= 10; i++) {
            islandNumbers.add(i);
        }
    }

    // rajoute une ligne dans le hall of fame
    private Table displayHall(Statis stats, Table tableStats, Color currentColor, Color colorLabel) {
        TextButton cell;

        cell = new TextButton(String.valueOf(stats.getRank()), skinSgxTable);
        cell.getLabel().setColor(colorLabel);
        tableStats.add(cell).fill();
        cell.setColor(currentColor);

        TextureRegionDrawable imageAvatar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(stats.getAvatar()))));
        ImageButton avatarButton = new ImageButton(imageAvatar);
        avatarButton.setSize(SCREEN_HEIGHT * 6 / 100, SCREEN_HEIGHT * (6 / 100) * 95 / 100);
        cell.setColor(currentColor);
        tableStats.add(avatarButton).height(Value.percentHeight(1.27f)).width(Value.percentWidth(1.24f)).fill();
        cell = new TextButton(String.valueOf(stats.getName()), skinSgxTable);
        // coupe le nom s'il est trop long
        cell.setClip(true);
        cell.setColor(currentColor);
        cell.getLabel().setColor(colorLabel);
        cell.setHeight(cellHeight);
        tableStats.add(cell).height(Value.percentHeight(1.3f)).width(SCREEN_WIDTH * 16 / 100).fill();
        cell = new TextButton(String.valueOf(stats.getScore()), skinSgxTable);
        cell.setColor(currentColor);
        cell.getLabel().setColor(colorLabel);
        cell.setHeight(cellHeight);
        tableStats.add(cell).height(Value.percentHeight(1.3f)).fill();
        tableStats.row();
        tableStats.setClip(true);
        return tableStats;
    }

    private Statis searchPlayer(String name) {
        Iterator iter = Menu.tabScore.iterator();
        while (iter.hasNext()) {

            Statis statis = (Statis) iter.next();
            if (statis.getName().equals(name)) {
                return statis;
            }
        }
        return null;
    }

    private void showStats() {
        Menu.disableButton(buttonBack, buttonPlay, buttonStats);
        Menu.disableBox(selectBoxIsland, selectBoxDifficulty, selectBoxPlayer, selectBoxNumber);
        mainTableStats.clear();
        stage.addActor(mainTableStats);
        mainTableStats.setFillParent(true);

        Label labelStats = new Label("Statistics", skinSgx, "title-white");

        selectBoxPlayerStats = new SelectBox<String>(skinSgx);
        selectBoxPlayerStats.setItems(playerNames);
        humanPlayerStats = selectBoxPlayerStats.getItems().first();
        ChangeListener selectBoxPlayerStatsListener = new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                soundButton2.play(0.2f);
                humanPlayerStats = selectBoxPlayerStats.getSelected();
            }
        };
        selectBoxPlayerStats.addListener(selectBoxPlayerStatsListener);

        Stack contentTop = new Stack();
        final Table tableTop = new Table();
        tableTop.setBackground(backgroundGrey);
        labelStats.setAlignment(1);
        tableTop.add(labelStats).width(Value.percentWidth(1f)).height(Value.percentHeight(1f)).padBottom(SCREEN_HEIGHT * 1 / 100);
        tableTop.row();
        tableTop.add(selectBoxPlayerStats).height(Value.percentHeight(1f));
        contentTop.addActor(tableTop);
        final Stack content = new Stack();

        buttonIsland = new TextButton("Island " + currentIslandNumber, skinSgx, "number");
        buttonIsland.setDisabled(true);
        buttonIsland.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonIsland.setDisabled(true);
                buttonGlobal.setDisabled(false);
                content.swapActor(containerGlobal, containerIsland);
            }
        });
        buttonGlobal = new TextButton("Global", skinSgx, "number");
        buttonGlobal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonIsland.setDisabled(false);
                buttonGlobal.setDisabled(true);
                content.swapActor(containerIsland, containerGlobal);
            }
        });

        Table tableTabs = new Table();
        tableTabs.add(buttonIsland).width((SCREEN_HEIGHT * 70 / 100) / 2);
        tableTabs.add(buttonGlobal).width((SCREEN_HEIGHT * 70 / 100) / 2);

        TextButton buttonStatBack = new TextButton("Back", skinSgxTable, "number");
        buttonStatBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                mainTableStats.remove();
                Menu.enableButton(buttonBack, buttonPlay, buttonStats);
                enableBox(selectBoxIsland, selectBoxNumber);

                if (selectBoxNumber.getSelected() == 1 /* et qu'au moins une personne est connectee ! - A RAJOUTER */)
                    enableBox(selectBoxDifficulty, selectBoxPlayer);
                else if (selectBoxNumber.getSelected() == 2) {
                    disableBox(selectBoxDifficulty, selectBoxPlayer);
                } else {
                    enableBox(selectBoxDifficulty);
                    disableBox(selectBoxPlayer);
                }
            }
        });

        Label labelScroll = new Label("", skinSgx, "white");
        Label labelGames = new Label("Games : " + player1.getStatistics().getTotalGames(), skinSgx, "white");
        Label labelWins = new Label("Wins : " + player1.getStatistics().getTotalWins(), skinSgx, "white");
        Label labelDefeats = new Label("Defeats : " + player1.getStatistics().getTotalDefeats(), skinSgx, "white");
        Label labelMinTurns = new Label("Minimum number of turns : " + player1.getStatistics().getMinTurns(), skinSgx, "white");
        Label labelAvgTurns = new Label("Average number of turns : " + player1.getStatistics().getAvgTurns(), skinSgx, "white");
        Label labelAvgLands = new Label("Average number of lands/turn : " + player1.getStatistics().getAvgLandsTurn(), skinSgx, "white");
        Label labelMaxLands = new Label("Maximum number of lands/turn : " + player1.getStatistics().getMinLandsTurn(), skinSgx, "white");
        Label labelAvgTrees = new Label("Average number of cut trees : " + player1.getStatistics().getAvgTrees(), skinSgx, "white");
        Label labelMaxTrees = new Label("Maximum number of cut trees : " + player1.getStatistics().getMaxTrees(), skinSgx, "white");
        Label labelAvgMoney = new Label("Average amount of earned money : " + player1.getStatistics().getAvgTotalMoney(), skinSgx, "white");
        Label labelMaxMoney = new Label("Maximum amount of earned money : " + player1.getStatistics().getMaxTotalMoney(), skinSgx, "white");
        Label labelAvgSavings = new Label("Average amount of savings : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");


        // a rajouter aussi pour le global + faire le lien avec la classe Statistics :

        Label labelMaxSavings = new Label("Maximum amount of savings : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelMinArmy = new Label("Minimum army value : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgArmy = new Label("Average army value : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelMaxArmy = new Label("Maximum army value : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgUnits = new Label("Average number of units : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgL0 = new Label("Average number of units 0 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgL1 = new Label("Average number of units 1 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgL2 = new Label("Average number of units 2 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgL3 = new Label("Average number of units 3 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelMaxUnits = new Label("Maximum number of units : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelMaxL0 = new Label("Maximum number of units 0 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelMaxL1 = new Label("Maximum number of units 1 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelMaxL2 = new Label("Maximum number of units 2 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelMaxL3 = new Label("Maximum number of units 3 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgLostUnits = new Label("Average number of lost units : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgLostL0 = new Label("Average number of lost units 0 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgLostL1 = new Label("Average number of lost units 1 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgLostL2 = new Label("Average number of lost units 2 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgLostL3 = new Label("Average number of lost units 3 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelMaxLostUnits = new Label("Maximum number of lost units : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelMaxLostL0 = new Label("Maximum number of lost units 0 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelMaxLostL1 = new Label("Maximum number of lost units 1 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelMaxLostL2 = new Label("Maximum number of lost units 2 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelMaxLostL3 = new Label("Maximum number of lost units 3 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgLeftUnits = new Label("Average number of left units : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgLeftL0 = new Label("Average number of left units 0 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgLeftL1 = new Label("Average number of left units 1 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgLeftL2 = new Label("Average number of left units 2 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");
        Label labelAvgLeftL3 = new Label("Average number of left units 3 : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");


        final Table tableIsland = new Table();
        tableIsland.left();
        tableIsland.setBackground(backgroundGrey);
        tableIsland.add(labelGames).left().padLeft(SCREEN_WIDTH * 2 / 100).padTop(SCREEN_WIDTH * 2 / 100);
        tableIsland.row();
        tableIsland.add(labelWins).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableIsland.row();
        tableIsland.add(labelDefeats).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableIsland.row();
        tableIsland.add(labelAvgTurns).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableIsland.row();
        tableIsland.add(labelMinTurns).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableIsland.row();
        tableIsland.add(labelAvgLands).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableIsland.row();
        tableIsland.add(labelMaxLands).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableIsland.row();
        tableIsland.add(labelAvgTrees).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableIsland.row();
        tableIsland.add(labelMaxTrees).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableIsland.row();
        tableIsland.add(labelAvgMoney).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableIsland.row();
        tableIsland.add(labelMaxMoney).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableIsland.row();
        tableIsland.add(labelAvgSavings).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableIsland.row().padTop(100);
        // affiche le scroll
        tableIsland.add(labelScroll).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableIsland.row().padTop(100);

        tableButtonBack = new Table();
        tableButtonBack.add(buttonStatBack).fill().width(SCREEN_HEIGHT * 70 / 100).height(Value.percentHeight(1.2f));
        tableButtonBack.bottom();

        containerIsland = new Table(skinSgxTable);
        // no-bg pour afficher notre background, sinon il en rajoute un par dessus
        ScrollPane paneIsland = new ScrollPane(tableIsland, skinSgxTable, "no-bg");
        containerIsland.setFillParent(true);
        containerIsland.add(paneIsland).fill().expand();

        Label labelScrollG = new Label("", skinSgx, "white");
        Label labelGamesG = new Label("Games : " + player1.getStatistics().getTotalGames(), skinSgx, "white");
        Label labelWinsG = new Label("Wins : " + player1.getStatistics().getTotalWins(), skinSgx, "white");
        Label labelDefeatsG = new Label("Defeats : " + player1.getStatistics().getTotalDefeats(), skinSgx, "white");
        Label labelAverTurnsG = new Label("Average number of turns : " + player1.getStatistics().getAvgTurns(), skinSgx, "white");
        Label labelMinTurnsG = new Label("Minimum number of turns : " + player1.getStatistics().getMinTurns(), skinSgx, "white");
        Label labelAverLandsG = new Label("Average number of Lands/turns : " + player1.getStatistics().getAvgLandsTurn(), skinSgx, "white");
        Label labelMaxLandsG = new Label("Maximum number of Lands/turns : " + player1.getStatistics().getMinLandsTurn(), skinSgx, "white");
        Label labelAverTreesG = new Label("Average number of cut trees : " + player1.getStatistics().getAvgTrees(), skinSgx, "white");
        Label labelMaxTreesG = new Label("Maximum number of cut trees : " + player1.getStatistics().getMaxTrees(), skinSgx, "white");
        Label labelAverMoneyG = new Label("Average total amount of money : " + player1.getStatistics().getAvgTotalMoney(), skinSgx, "white");
        Label labelMaxMoneyG = new Label("Maximum total amount of money : " + player1.getStatistics().getMaxTotalMoney(), skinSgx, "white");
        Label labelAverSavingG = new Label("Average total savings : " + player1.getStatistics().getTotalSavings(), skinSgx, "white");

        final Table tableGlobal = new Table();
        tableGlobal.left();
        tableGlobal.setBackground(backgroundGrey);
        tableGlobal.add(labelGamesG).left().padLeft(SCREEN_WIDTH * 2 / 100).padTop(SCREEN_WIDTH * 2 / 100);
        tableGlobal.row();
        tableGlobal.add(labelWinsG).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableGlobal.row();
        tableGlobal.add(labelDefeatsG).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableGlobal.row();
        tableGlobal.add(labelAverTurnsG).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableGlobal.row();
        tableGlobal.add(labelMinTurnsG).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableGlobal.row();
        tableGlobal.add(labelAverLandsG).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableGlobal.row();
        tableGlobal.add(labelMaxLandsG).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableGlobal.row();
        tableGlobal.add(labelAverTreesG).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableGlobal.row();
        tableGlobal.add(labelMaxTreesG).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableGlobal.row();
        tableGlobal.add(labelAverMoneyG).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableGlobal.row();
        tableGlobal.add(labelMaxMoneyG).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableGlobal.row();
        tableGlobal.add(labelAverSavingG).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableGlobal.row().padTop(100);
        // affiche le scroll
        tableGlobal.add(labelScrollG).left().padLeft(SCREEN_WIDTH * 2 / 100);
        tableGlobal.row().padTop(100);

        containerGlobal = new Table(skinSgxTable);
        // no-bg pour afficher notre background, sinon il en rajoute un par dessus
        ScrollPane paneGlobal = new ScrollPane(tableGlobal, skinSgxTable, "no-bg");
        containerGlobal.setFillParent(true);
        containerGlobal.add(paneGlobal).fill().expand();

        content.addActor(containerGlobal);
        content.addActor(containerIsland);
        content.addActor(tableButtonBack);

        mainTableStats.add(contentTop).size(SCREEN_HEIGHT * 70 / 100, labelStats.getHeight() + selectBoxPlayerStats.getHeight() + SCREEN_HEIGHT * 7 / 100);
        mainTableStats.row();
        mainTableStats.add(tableTabs);
        mainTableStats.row();
        mainTableStats.add(content).size(SCREEN_HEIGHT * 70 / 100, SCREEN_HEIGHT * 77 / 100 - buttonIsland.getHeight() - (labelStats.getHeight() + selectBoxPlayerStats.getHeight() + SCREEN_HEIGHT * 7 / 100));

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
        skinSgx.getFont("title").getData().setScale(SCREEN_WIDTH * 0.8f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.8f / VIRTUAL_HEIGHT);
        skinSgxTable.getFont("font").getData().setScale(SCREEN_WIDTH * 1f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 1f / VIRTUAL_HEIGHT);
        skinSgxTable.getFont("title").getData().setScale(SCREEN_WIDTH * 0.9f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.9f / VIRTUAL_HEIGHT);
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