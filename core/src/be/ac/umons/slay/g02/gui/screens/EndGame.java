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
 * classe qui affiche l'écran de fin de jeu
 */
public class EndGame implements Screen {

    //  private static Window windowExit = new Window(lang.get("text_exit"), skinSgx);
    private Game game;
    private Sprite sprite;

    private SpriteBatch batch;

    EndGame(Game aGame, Player winner, int numberHuman) {
        game = aGame;

        int buttonCenterWidth;

        if (SCREEN_WIDTH > SCREEN_HEIGHT)
            buttonCenterWidth = VIRTUAL_WIDTH * 20 / 100;
        else
            buttonCenterWidth = Math.min(VIRTUAL_WIDTH * 28 / 100, (int) (SCREEN_WIDTH * 0.9));

        int buttonCenterHeight = VIRTUAL_HEIGHT * 5 / 100;
        int buttonCenterGap = SCREEN_HEIGHT * 7 / 100;
        //     int tableCenterPositionX = SCREEN_WIDTH / 2;
        //     int tableCenterPositionY = SCREEN_HEIGHT / 3;

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
/*
        Table tableCenter = new Table();
        tableCenter.setPosition(tableCenterPositionX, tableCenterPositionY);
        stage.addActor(tableCenter);

        TextButton buttonExit = new TextButton(lang.get("button_exit"), skinSgx, "big");
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                showExit();
            }
        });  */

        TextButton buttonSelection = new TextButton("SELECTION SCREEN", skinSgx, "big");
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
        //      tableCenter.add(buttonExit).width(buttonCenterWidth).height(buttonCenterHeight);
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


        batch.end();
        batch.begin();

        // place le background correctement dans la fenetre
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
 /*   private void showExit() {
        windowExit.clear();
        windowExit.setSize(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4);
        windowExit.setPosition(SCREEN_WIDTH / 2 - windowExit.getWidth() / 2, SCREEN_HEIGHT / 2 - windowExit.getHeight() / 2);
        windowExit.setMovable(false);
        // place le titre de la fenetre au milieu
        windowExit.getTitleTable().padLeft(windowExit.getWidth() / 2 - windowExit.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowExit.addActor(table);
        table.setFillParent(true);
        table.padTop(windowExit.getHeight() * 17 / 100);

        Label labelExitConfirm = new Label(lang.get("label_are_you_sure"), skinSgx, "white");

        TextButton buttonYes = new TextButton(lang.get("text_yes"), skinSgx, "big");
        buttonYes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        TextButton buttonNo = new TextButton(lang.get("text_no"), skinSgx, "big");
        buttonNo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                windowExit.remove();
            }
        });

        table.add(labelExitConfirm).width(Value.percentWidth(1f)).colspan(2).padBottom(SCREEN_WIDTH / 3 * 8 / 100);
        table.row();
        buttonYes.setWidth(SCREEN_WIDTH / 3 * 20 / 100);
        buttonNo.setWidth(SCREEN_WIDTH / 3 * 20 / 100);
        table.add(buttonYes).padRight(windowExit.getWidth() * 8 / 100).width(Value.percentWidth(1f));
        table.add(buttonNo).width(Value.percentWidth(1f));

  //      stage.addActor(windowExit);
    }
*/

}