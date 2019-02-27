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

import be.ac.umons.slay.g02.players.Statis;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.cursor;
import static be.ac.umons.slay.g02.gui.Main.pm;
import static be.ac.umons.slay.g02.gui.Main.skinSgxTable;
import static be.ac.umons.slay.g02.gui.Main.skinSgx;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.stage;
import static be.ac.umons.slay.g02.gui.Main.camera;

public class Hall implements Screen {
    private Game game;
    SpriteBatch batch;
    private Sprite sprite;


    public Hall(Game aGame) {
        game = aGame;

        int cellHeight = SCREEN_HEIGHT * 6 / 100;

        // background
        batch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("backgrounds/background.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setOrigin(0, 0);
        sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

        Table tableScreen = new Table();
        tableScreen.setFillParent(true);
        tableScreen.bottom();

        TextButton backButton = new TextButton("Back", skinSgx, "big");
        tableScreen.add(backButton).size(SCREEN_WIDTH, cellHeight * 3 / 2);

        Table tableHall = new Table(skinSgxTable);
        tableHall.setFillParent(true);
        tableHall.bottom();

        Label labelTitle = new Label("Hall Of Fame", skinSgxTable, "title-white");
        labelTitle.setHeight(SCREEN_HEIGHT * 9 / 100);
        tableHall.add(labelTitle).colspan(SCREEN_WIDTH / 2).height(Value.percentHeight(1f));
        tableHall.row();

        TextButton cell = new TextButton("Rank", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.getLabel().setFontScale(0.8f);
        cell.setWidth(SCREEN_WIDTH * 5 / 100);
        cell.setHeight(cellHeight);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Player", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.getLabel().setFontScale(0.8f);
        cell.setWidth(SCREEN_WIDTH * 36 / 100);
        cell.setHeight(cellHeight);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).colspan(2).center().fill();

        cell = new TextButton("Wins", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        cell.getLabel().setFontScale(0.8f);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Games", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        cell.getLabel().setFontScale(0.8f);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Turns", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        cell.getLabel().setFontScale(0.8f);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Lands\nturn", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        cell.getLabel().setFontScale(0.8f);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Cut trees", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        cell.getLabel().setFontScale(0.8f);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Losses", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        cell.getLabel().setFontScale(0.8f);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("Full army\nvalue", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setWidth(SCREEN_WIDTH * 7 / 100);
        cell.setHeight(cellHeight);
        cell.getLabel().setFontScale(0.8f);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();

        cell = new TextButton("SCORE", skinSgxTable);
        cell.getLabel().setColor(Color.BLACK);
        cell.setWidth(SCREEN_WIDTH * 10 / 100);
        cell.setHeight(cellHeight);
        cell.getLabel().setFontScale(0.8f);
        tableHall.add(cell).height(Value.percentHeight(1.2f)).width(Value.percentWidth(1f)).center().fill();
        tableHall.row();

        int j = 0;

        Iterator iter = Menu.tabScore.iterator();
        ImageButton avatarButton;
        TextureRegionDrawable imageAvatar;

        Color colorPurple = new Color(Color.rgba8888(174 / 255f, 174 / 255f, 255 / 255f, 1));
        Color colorBlack = new Color(Color.BLACK);
        Color colorWhite = new Color(Color.WHITE);
        Color currentColor = colorPurple;
        Color colorLabel = colorWhite;
        boolean isNext = true;

        while (iter.hasNext()) {
            if (isNext) {
                currentColor = colorPurple;
                colorLabel = colorBlack;
                isNext = false;
            } else {
                currentColor = colorWhite;
                colorLabel = colorPurple;
                isNext = true;
            }

            Statis stats = (Statis) iter.next();
            j++;
            cell = new TextButton(String.valueOf(stats.getRank()), skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.getLabel().setFontScale(0.8f);
            cell.setWidth(SCREEN_WIDTH * 5 / 100);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).width(Value.percentWidth(1f)).fill();
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.getLabel().setFontScale(0.8f);

            imageAvatar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(stats.getAvatar()))));
            avatarButton = new ImageButton(imageAvatar);
            avatarButton.setSize(cellHeight, cell.getHeight() * 95 / 100);
            cell.setColor(currentColor);
            cell.getLabel().setFontScale(0.8f);
            tableHall.add(avatarButton).height(Value.percentHeight(1.3f)).width(Value.percentWidth(1.3f)).fill();
            cell = new TextButton(String.valueOf(stats.getName()), skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            cell.setWidth(SCREEN_WIDTH * 35 / 100 - cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).width(Value.percentWidth(1f));
            cell.getLabel().setFontScale(0.8f);
            cell = new TextButton(String.valueOf(stats.getWins()), skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell.getLabel().setFontScale(0.8f);
            cell = new TextButton(String.valueOf(stats.getGames()), skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            cell.getLabel().setFontScale(0.8f);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell = new TextButton(String.valueOf(stats.getTurns()), skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            cell.getLabel().setFontScale(0.8f);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell = new TextButton(String.valueOf(stats.getLands()), skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.getLabel().setFontScale(0.8f);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell = new TextButton(String.valueOf(stats.getTrees()), skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.getLabel().setFontScale(0.8f);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell = new TextButton(String.valueOf(stats.getLosses()), skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.getLabel().setFontScale(0.8f);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell = new TextButton(String.valueOf(stats.getArmy()), skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setFontScale(0.8f);
            cell.getLabel().setColor(colorLabel);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            cell = new TextButton(String.valueOf(stats.getScore()), skinSgxTable);
            cell.setColor(currentColor);
            cell.getLabel().setColor(colorLabel);
            cell.getLabel().setFontScale(0.8f);
            cell.setHeight(cellHeight);
            tableHall.add(cell).height(Value.percentHeight(1.3f)).fill();
            tableHall.row();
        }

        // affiche au moins 20 lignes
        for (int i = j; i < 50; i++) {
            Label nameLabel2 = new Label("", skinSgxTable);
            tableHall.add(nameLabel2).right();
            nameLabel2 = new Label("", skinSgxTable);
            tableHall.add(nameLabel2).right();
            nameLabel2 = new Label("", skinSgxTable);
            tableHall.add(nameLabel2).right();
            nameLabel2 = new Label("", skinSgxTable);
            tableHall.add(nameLabel2).right();
            nameLabel2 = new Label("", skinSgxTable);
            tableHall.add(nameLabel2).right();
            nameLabel2 = new Label("", skinSgxTable);
            tableHall.add(nameLabel2).right();
            nameLabel2 = new Label("", skinSgxTable);
            tableHall.add(nameLabel2).right();
            nameLabel2 = new Label("", skinSgxTable);
            tableHall.add(nameLabel2).right();
            nameLabel2 = new Label("", skinSgxTable);
            tableHall.add(nameLabel2).right();
            nameLabel2 = new Label("", skinSgxTable);
            tableHall.add(nameLabel2).right();
            nameLabel2 = new Label("", skinSgxTable);
            tableHall.add(nameLabel2).right();
            tableHall.row();
        }

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                stage.clear();
                game.setScreen(new Menu(game));
            }
        });

        Table container = new Table(skinSgxTable);

        // no-bg pour afficher notre background, sinon il en rajoute un par dessus
        ScrollPane pane = new ScrollPane(tableHall, skinSgxTable, "no-bg");

        container.setFillParent(true);

        container.add(pane).fill().expand();
        container.setDebug(false);

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

        // place le background correctement dans la fenetre
        batch.setProjectionMatrix(camera.combined);

        sprite.draw(batch);

        batch.end();

        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        skinSgx.getFont("title").getData().setScale(SCREEN_WIDTH * 0.8f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.8f / VIRTUAL_HEIGHT);
        skinSgxTable.getFont("font").getData().setScale(SCREEN_WIDTH * 0.8f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.8f / VIRTUAL_HEIGHT);
        skinSgxTable.getFont("title").getData().setScale(SCREEN_WIDTH * 0.8f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.8f / VIRTUAL_HEIGHT);
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
        soundButton2.dispose();
        skinSgx.dispose();
        skinSgxTable.dispose();
        stage.dispose();
    }
}


