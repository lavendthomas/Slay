package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import be.ac.umons.slay.g02.gui.Main;
import be.ac.umons.slay.g02.players.Statis;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.cursor;
import static be.ac.umons.slay.g02.gui.Main.isAccountEnabled;
import static be.ac.umons.slay.g02.gui.Main.pm;
import static be.ac.umons.slay.g02.gui.Main.skinSgx;
import static be.ac.umons.slay.g02.gui.Main.skinSgxTable;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.soundButton3;
import static be.ac.umons.slay.g02.gui.Main.stage;

// classe qui affiche l'ecran de selection de niveau
public class LevelSelection implements Screen {

    private Game game;
    SpriteBatch batch;
    private Sprite sprite;
    private int cellHeight;

    public static int currentIslandNumber;

    // pour les tests
    public static final int TOTAL_NUMBER_ISLANDS = 10;

    // il faudra les recuperer dans Players
    public static String player1Name = "P1";
    public static String player2Name = "WWWWWWWWWWWWWWWWW";


    public LevelSelection(Game aGame) {
        game = aGame;

        int buttonGapY = SCREEN_HEIGHT * 7 / 100;
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

        // mettre le numero et la carte dans un groupe ? comme ca on remet que le groupe a jour ?
        Label labelIslandNumber = new Label(String.valueOf(currentIslandNumber), skinSgx);

        SelectBox<Integer> selectBoxNumber = new SelectBox<Integer>(skinSgx);

        Array<Integer> numberOfHumans = new Array<Integer>();
        numberOfHumans.add(0, 1, 2);
        selectBoxNumber.setItems(numberOfHumans);

        SelectBox<String> selectBoxPlayer = new SelectBox<String>(skinSgx);

        Array<String> playerNames = new Array<String>();
        playerNames.add(player1Name, player2Name);

        selectBoxPlayer.setItems(playerNames);

        SelectBox<String> selectBoxDifficulty = new SelectBox<String>(skinSgx);

        Array<String> difficultyDegrees = new Array<String>();
        difficultyDegrees.add("EASY", "MEDIUM", "ADVANCED");

        selectBoxDifficulty.setItems(difficultyDegrees);

        TextButton buttonStats = new TextButton("  Statistics  ", skinSgx, "big");
        buttonStats.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);

                // a faire
            }
        });

        Button buttonMinus = new Button(skinSgx, "minus");
        buttonMinus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                if (currentIslandNumber > 1)
                    currentIslandNumber--;
            }
        });

        Button buttonPlus = new Button(skinSgx, "plus");
        buttonPlus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                if (currentIslandNumber < TOTAL_NUMBER_ISLANDS)
                    currentIslandNumber++;
            }
        });

        TextButton buttonBack = new TextButton("Back", skinSgx, "big");
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                stage.clear();
                game.setScreen(new Menu(game));
            }
        });


        TextButton buttonPlay = new TextButton("Play", skinSgx, "big");
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton3.play(0.2f);
                stage.clear();
                game.setScreen(new GameScreen(game));
            }
        });

        // partie gauche
        Table table = new Table();
        table.setFillParent(true);
        table.left();
        table.setSize(SCREEN_WIDTH / 2, SCREEN_HEIGHT);

        labelIsland.setAlignment(1);
        table.add(labelIsland).colspan(2).fill();
        table.add(buttonMinus);
        labelIslandNumber.setAlignment(1);
        table.add(labelIslandNumber).fill();
        table.add(buttonPlus);
        Label label = new Label("", skinSgxTable, "title-white");
        label.setWidth(10);
        table.add(label);
        table.add(levelPreview).colspan(3);
        table.row().pad(buttonGapY, 0, buttonGapY, 0);

        table.add(labelHuman).colspan(2).width(Value.percentWidth(1.40f)).fill();
        selectBoxNumber.setWidth(SCREEN_WIDTH * 10 / 100);
        table.add(selectBoxNumber).colspan(3);
        label.setWidth(50);
        table.add(label);
        table.add(selectBoxPlayer).colspan(3);

        table.row().pad(buttonGapY, 0, buttonGapY, 0);

        table.add(labelDifficulty).colspan(2);
        table.add(selectBoxDifficulty).colspan(3);
        table.add(label).width(Value.percentWidth(1.00f)).fill();
        table.add(label);
        table.add(label);
        table.add(label);
        table.row().pad(buttonGapY + 50, 0, 0, 0);

        buttonBack.setWidth(SCREEN_WIDTH * 12 / 100);
        buttonPlay.setWidth(SCREEN_WIDTH * 12 / 100);
        buttonStats.setWidth(SCREEN_WIDTH * 12 / 100);
        Label labelB = new Label("", skinSgxTable, "title-white");

        table.add(buttonBack).fill();

        table.add(labelB);
        table.add(buttonPlay).colspan(4).fill();
        table.add(labelB);

        if (isAccountEnabled)
            table.add(buttonStats).colspan(2);

        table.row();

        buttonBack.setPosition(SCREEN_WIDTH * 7 / 100, SCREEN_HEIGHT * 10 / 100);
        buttonPlay.setPosition(SCREEN_WIDTH * 27 / 100, SCREEN_HEIGHT * 10 / 100);
        buttonStats.setPosition(SCREEN_WIDTH * 47 / 100, SCREEN_HEIGHT * 10 / 100);

        // pqrtie droite
        Table tableStats = new Table();
        tableStats.setFillParent(true);
        tableStats.padRight(SCREEN_WIDTH * 1 / 100);
        tableStats.right().top();

        Label labelTitle = new Label("\nHall Of Fame", skinSgxTable, "title-white");
        labelTitle.setHeight(SCREEN_HEIGHT * 15 / 100);
        labelTitle.setWidth(SCREEN_HEIGHT * 30 / 100);
        labelTitle.setAlignment(1);
        tableStats.add(labelTitle).colspan(4).width(Value.percentWidth(1f)).height(Value.percentHeight(1f));
        tableStats.row();

        TextButton cell = new TextButton("Rank", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.getLabel().setFontScale(0.8f);
        cell.setWidth(SCREEN_WIDTH * 5 / 100);
        cell.setHeight(cellHeight);
        tableStats.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Player", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.getLabel().setFontScale(0.8f);
        cell.setWidth(SCREEN_WIDTH * 16 / 100);
        cell.setHeight(SCREEN_HEIGHT * 6 / 100);
        tableStats.add(cell).height(Value.percentHeight(1.2f)).colspan(2).center().fill();

        cell = new TextButton("SCORE", skinSgxTable);
        cell.getLabel().setFontScale(0.8f);
        cell.getLabel().setColor(Color.BLACK);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        tableStats.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();
        tableStats.row();

        Iterator iter = Menu.tabScore.iterator();

        Color colorPurple = new Color(Color.rgba8888(174 / 255f, 174 / 255f, 255 / 255f, 1));
        Color colorBlack = new Color(Color.BLACK);
        Color colorWhite = new Color(Color.WHITE);
        Color currentColor = new Color(Color.GOLD);
        Color colorLabel = new Color(Color.BLACK);

        boolean isNext = true;

        int i = 0;
        while ((iter.hasNext()) && (i < 5)) {
            if (isNext) {
                currentColor = colorPurple;
                colorLabel = colorBlack;
                isNext = false;
            } else {
                currentColor = colorWhite;
                colorLabel = colorPurple;
                isNext = true;
            }
            i++;
            Statis stats = (Statis) iter.next();
            tableStats = displayStats(stats, tableStats, currentColor, colorLabel);
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
            tableStats = displayStats(statsPlayer, tableStats, currentColor, colorLabel);
        }
        name = "0000";
        statsPlayer = searchPlayer(name);
        if (null != statsPlayer) {
            tableStats = displayStats(statsPlayer, tableStats, currentColor, colorLabel);
        }

        stage.addActor(table);
        stage.addActor(tableStats);
        stage.addActor(buttonBack);
        stage.addActor(buttonPlay);
        stage.addActor(buttonStats);
    }

    // rajoute une ligne dans le hall of fame
    private Table displayStats(Statis stats, Table tableStats, Color currentColor, Color colorLabel) {
        TextButton cell;

        cell = new TextButton(String.valueOf(stats.getRank()), skinSgxTable);
        cell.getLabel().setColor(colorLabel);
        cell.getLabel().setFontScale(1f);
        tableStats.add(cell).fill();
        cell.setColor(currentColor);

        TextureRegionDrawable imageAvatar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(stats.getAvatar()))));
        ImageButton avatarButton = new ImageButton(imageAvatar);
        avatarButton.setSize(cell.getWidth() - 5, cell.getHeight() - 5);
        cell.setColor(currentColor);
        tableStats.add(avatarButton).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1.2f)).fill();
        cell = new TextButton(String.valueOf(stats.getName()), skinSgxTable);
        cell.setColor(currentColor);
        cell.getLabel().setColor(colorLabel);
        cell.getLabel().setFontScale(0.6f);
        cell.setHeight(cellHeight);
        tableStats.add(cell).height(Value.percentHeight(1.3f)).width(SCREEN_WIDTH * 16 / 100).fill();
        cell = new TextButton(String.valueOf(stats.getScore()), skinSgxTable);
        cell.setColor(currentColor);
        cell.getLabel().setColor(colorLabel);
        cell.getLabel().setFontScale(0.6f);
        cell.setHeight(cellHeight);
        tableStats.add(cell).height(Value.percentHeight(1.3f)).fill();

        tableStats.row();

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
        skinSgxTable.dispose();
        stage.dispose();
    }
}
