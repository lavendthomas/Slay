package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import be.ac.umons.slay.g02.players.AI;
import be.ac.umons.slay.g02.players.Player;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.camera;
import static be.ac.umons.slay.g02.gui.Main.cursor;
import static be.ac.umons.slay.g02.gui.Main.pm;
import static be.ac.umons.slay.g02.gui.Main.prefs;
import static be.ac.umons.slay.g02.gui.Main.skinSgx;
import static be.ac.umons.slay.g02.gui.Main.skinSgxTable;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.soundButton3;
import static be.ac.umons.slay.g02.gui.Main.stage;

/**
 * classe qui affiche l'écran de fin de jeu   TODO
 */
public class EndGame implements Screen {

    //  private static Window windowExit = new Window(lang.get("text_exit"), skinSgx);
    private Game game;
    private Sprite sprite;

    private SpriteBatch batch;

    /**
     *   TODO
     *
     * @param aGame the instance of Game created in class Main
     * @param winner
     * @param numberHuman
     */
    EndGame(Game aGame, Player winner, int numberHuman) {
        game = aGame;

        int buttonCenterWidth;

        if (SCREEN_WIDTH > SCREEN_HEIGHT)
            buttonCenterWidth = VIRTUAL_WIDTH * 30 / 100;
        else
            buttonCenterWidth = Math.min(VIRTUAL_WIDTH * 40 / 100, (int) (SCREEN_WIDTH * 0.9));

        int buttonCenterHeight = VIRTUAL_HEIGHT * 5 / 100;
        int buttonCenterGap = SCREEN_HEIGHT * 7 / 100;

        Table table = new Table();
        table.setFillParent(true);

        if (numberHuman == 1) {
            if (winner instanceof AI) {
                Label labelTooBad = new Label("Too bad you lost", skinSgxTable, "title-white");
                Label labelBetter = new Label("You'll do better next time", skinSgxTable, "title-white");
                table.add(labelTooBad);
                table.row().padTop(buttonCenterGap);
                table.add(labelBetter);
            } else {
                Label labelWon = new Label("You have won", skinSgxTable, "title-white");
                Label labelCongrats = new Label("Congratulations !", skinSgxTable, "title-white");
                table.add(labelCongrats);
                table.row().padTop(buttonCenterGap);
                table.add(labelWon);
            }
        } else {
            Label labelWinner = new Label("The winner is", skinSgxTable, "title-white");
            Label labelNameWinner = new Label(winner.getName(), skinSgxTable, "title-white");
            table.add(labelWinner);
            table.row().padTop(buttonCenterGap);
            table.add(labelNameWinner);
            table.row().padTop(buttonCenterGap);
        }
        table.row().padTop(buttonCenterGap * 1.5f);
        stage.addActor(table);

        // Background
        batch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("backgrounds/background.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        sprite = new Sprite(texture);
        sprite.setOrigin(0, 0);
        sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

        TextButton buttonSelection = new TextButton("BACK TO LEVEL SELECTION", skinSgx, "big");
        buttonSelection.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                stage.clear();

                game.setScreen(new LevelSelection(game));
            }
        });
        table.add(buttonSelection).width(buttonCenterWidth).height(buttonCenterHeight);
        table.row().pad(buttonCenterGap, 0, buttonCenterGap, 0);
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
        game.dispose();
        batch.end();
        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            skinSgxTable.getFont("title").getData().setScale(SCREEN_WIDTH * 1f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 1f / VIRTUAL_HEIGHT);
        }
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