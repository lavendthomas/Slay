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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Iterator;

import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.Statistics;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.camera;
import static be.ac.umons.slay.g02.gui.Main.prefs;
import static be.ac.umons.slay.g02.gui.Main.skinSgx;
import static be.ac.umons.slay.g02.gui.Main.skinSgxTable;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.stage;
import static be.ac.umons.slay.g02.gui.Main.tabPlayers;

/**
 * Class displaying the hall of fame with all registered users who have played at least once
 */
public class Hall implements Screen {
    private Game game;
    SpriteBatch batch;
    private Sprite sprite;
    private int cellHeight;
    private int scrollHeight;

    /**
     * Class constructor
     *
     * @param aGame the instance of Game created in class Main
     */
    public Hall(Game aGame) {
        game = aGame;

        cellHeight = SCREEN_HEIGHT * 6 / 100;
        scrollHeight = (int) (SCREEN_HEIGHT * 1f / 100 * 9 / 100 + cellHeight * 1.2f / 100) + cellHeight * 3 / 2;

        // Background
        batch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("backgrounds/background.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setOrigin(0, 0);
        sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

        Table tableScreen = new Table();
        tableScreen.setFillParent(true);
        tableScreen.bottom();

        // Button Back
        TextButton backButton = new TextButton("Back", skinSgx, "big");
        tableScreen.add(backButton).size(SCREEN_WIDTH, cellHeight * 3 / 2);

        Table tableHall = new Table(skinSgxTable);
        tableHall.setFillParent(true);
        tableHall.top();

        // Screen title
        Label labelTitle = new Label("Hall Of Fame", skinSgxTable, "title-white");
        labelTitle.setHeight(SCREEN_HEIGHT * 9 / 100);
        Label labelScroll = new Label("", skinSgx);

        tableHall.add(labelTitle).colspan(SCREEN_WIDTH / 2).height(Value.percentHeight(1f));
        tableHall.row();

        Color colorViolet = new Color(195 / 255f, 195 / 255f, 251 / 255f, 1);
        Color colorBlack = new Color(Color.BLACK);
        Color colorYellow = new Color(241 / 255f, 222 / 255f, 169 / 255f, 1);
        Color colorGray = new Color(229 / 255f, 229 / 255f, 229 / 255f, 1);

        // First row

        TextButton cell = new TextButton("Rank", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setColor(colorViolet);
        cell.setWidth(SCREEN_WIDTH * 5 / 100);
        cell.setHeight(cellHeight);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Player", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setColor(colorViolet);
        cell.setWidth(SCREEN_WIDTH * 36 / 100);
        cell.setHeight(cellHeight);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).colspan(2).center().fill();

        cell = new TextButton("Wins", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setColor(colorViolet);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Games", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setColor(colorViolet);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Turns", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setColor(colorViolet);
        cell.setHeight(cellHeight);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Lands\n/ turn", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setColor(colorViolet);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Cut trees", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setColor(colorViolet);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Losses", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setColor(colorViolet);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Army\nvalue", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setColor(colorViolet);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("SCORE", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setColor(colorViolet);
        cell.setWidth(SCREEN_WIDTH * 10 / 100);
        cell.setHeight(cellHeight);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();
        tableHall.row();

        // Creates a new row for each registered user with a score > 0

        Iterator iter = tabPlayers.iterator();
        ImageButton avatarButton;
        TextureRegionDrawable imageAvatar;

        Color currentColor;
        Color colorLabel;
        boolean isNext = true;

        while (iter.hasNext()) {
            if (isNext) {
                currentColor = colorYellow;
                colorLabel = colorBlack;
                isNext = false;
            } else {
                currentColor = colorGray;
                colorLabel = colorBlack;
                isNext = true;
            }

            HumanPlayer player = (HumanPlayer) iter.next();

            if (player.getGlobalStats().getScore() != 0) {

                // Increases the scroll's height by the height of the new row
                scrollHeight += cellHeight * 1.3f / 100;

                // Rank
                cell = new TextButton(String.valueOf(player.getGlobalStats().getRank()), skinSgxTable);
                cell.setColor(currentColor);
                cell.getLabel().setColor(colorLabel);
                cell.setWidth(SCREEN_WIDTH * 5 / 100);
                cell.setHeight(cellHeight);
                tableHall.add(cell).height(Value.percentHeight(1.3f)).width(Value.percentWidth(1f)).fill();
                cell.setColor(currentColor);
                cell.getLabel().setColor(colorLabel);

                // Avatar
                imageAvatar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(player.getAvatar()))));
                avatarButton = new ImageButton(imageAvatar);
                avatarButton.setSize(cellHeight, cell.getHeight() * 95 / 100);
                cell.setColor(currentColor);
                tableHall.add(avatarButton).height(Value.percentHeight(1.3f)).width(Value.percentWidth(1.3f)).fill();

                // Name
                cell = new TextButton(String.valueOf(player.getName()), skinSgxTable);
                cell.setClip(true);
                cell.setColor(currentColor);
                cell.getLabel().setColor(colorLabel);
                cell.setHeight(cellHeight);
                cell.setWidth(SCREEN_WIDTH * 35 / 100 - cellHeight);
                tableHall.add(cell).height(Value.percentHeight(1.3f)).width(Value.percentWidth(1f));

                // Statistics values are retrieved from the hashmap stats in class GlobalStats

                cell = new TextButton(String.valueOf(player.getGlobalStats().getStats().get(Statistics.WINS)), skinSgxTable);
                cell.setColor(currentColor);
                cell.getLabel().setColor(colorLabel);
                cell.setHeight(cellHeight);
                tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();

                cell = new TextButton(String.valueOf(player.getGlobalStats().getStats().get(Statistics.GAMES)), skinSgxTable);
                cell.setColor(currentColor);
                cell.getLabel().setColor(colorLabel);
                cell.setHeight(cellHeight);
                tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();

                cell = new TextButton(String.valueOf(player.getGlobalStats().calculateAvg(Statistics.TURNS)
                        / LevelSelection.TOTAL_NUMBER_ISLANDS), skinSgxTable);
                cell.setColor(currentColor);
                cell.getLabel().setColor(colorLabel);
                cell.setHeight(cellHeight);
                tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();

                cell = new TextButton(String.valueOf(player.getGlobalStats().calculateAvg(Statistics.LANDS_TURN)
                        / LevelSelection.TOTAL_NUMBER_ISLANDS), skinSgxTable);
                cell.setColor(currentColor);
                cell.getLabel().setColor(colorLabel);
                cell.setHeight(cellHeight);
                tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();

                cell = new TextButton(String.valueOf(player.getGlobalStats().calculateAvg(Statistics.TREES)
                        / LevelSelection.TOTAL_NUMBER_ISLANDS), skinSgxTable);
                cell.setColor(currentColor);
                cell.getLabel().setColor(colorLabel);
                cell.setHeight(cellHeight);
                tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();

                cell = new TextButton(String.valueOf(player.getGlobalStats().calculateAvg(Statistics.LOST_UNITS)
                        / LevelSelection.TOTAL_NUMBER_ISLANDS), skinSgxTable);
                cell.setColor(currentColor);
                cell.getLabel().setColor(colorLabel);
                cell.setHeight(cellHeight);
                tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();

                cell = new TextButton(String.valueOf(player.getGlobalStats().calculateAvg(Statistics.ARMY)
                        / LevelSelection.TOTAL_NUMBER_ISLANDS), skinSgxTable);
                cell.setColor(currentColor);
                cell.getLabel().setColor(colorLabel);
                cell.setHeight(cellHeight);
                tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();

                cell = new TextButton(String.valueOf(player.getGlobalStats().getScore()), skinSgxTable);
                cell.setColor(currentColor);
                cell.getLabel().setColor(colorLabel);
                cell.setHeight(cellHeight);
                tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
                tableHall.row();
            }
        }

        // If there is no registered user, an empty row must be created so that the first row is correctly displayed
        if (tabPlayers.isEmpty()) {
            Texture transparency = new Texture(Gdx.files.internal("profile/transparency.jpg"));
            scrollHeight += cellHeight * 1.3f;
            currentColor = Color.CLEAR;
            colorLabel = Color.CLEAR;
            cell = new TextButton("", skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setWidth(SCREEN_WIDTH * 5 / 100);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).width(Value.percentWidth(1f)).fill();
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            imageAvatar = new TextureRegionDrawable(new TextureRegion(transparency));
            avatarButton = new ImageButton(imageAvatar);
            avatarButton.setSize(cellHeight, cell.getHeight() * 95 / 100);
            avatarButton.getImage().setColor(Color.CLEAR);
            cell.setColor(currentColor);
            tableHall.add(avatarButton).height(Value.percentHeight(1.3f)).width(Value.percentWidth(1.3f)).fill();
            cell = new TextButton("", skinSgxTable);
            cell.setClip(true);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            cell.setWidth(SCREEN_WIDTH * 35 / 100 - cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).width(Value.percentWidth(1f));
            cell = new TextButton("", skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell = new TextButton("", skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell = new TextButton("", skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell = new TextButton("", skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell = new TextButton("", skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell = new TextButton("", skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell = new TextButton("", skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell = new TextButton("", skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
        }

        // Displays the scroll
        Table tableScroll = new Table(skinSgxTable);
        tableScroll.bottom();
        tableScroll.add(labelScroll);
        tableScroll.add(tableHall).padTop(scrollHeight);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                stage.clear();
                game.setScreen(new Menu(game));
            }
        });
        Table container = new Table(skinSgxTable);
        ScrollPane pane = new ScrollPane(tableScroll, skinSgxTable, "no-bg");

        container.setFillParent(true);
        container.add(pane).fill().expand();

        stage.addActor(container);
        stage.addActor(tableScreen);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
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
    }
}