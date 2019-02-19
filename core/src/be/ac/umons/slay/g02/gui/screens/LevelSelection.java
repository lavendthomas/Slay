package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.cursor;
import static be.ac.umons.slay.g02.gui.Main.pm;
import static be.ac.umons.slay.g02.gui.Main.skinMain;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;

// classe qui affiche l'ecran de selection de niveau
public class LevelSelection implements Screen {

    private Game game;
    private Stage stageLevelSelection;

    public LevelSelection(Game aGame) {
        game = aGame;
        stageLevelSelection = new Stage(new ScreenViewport());


        Label.LabelStyle label1Style = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("skins/rainbow-raw/font-button-export.fnt"),
                Gdx.files.internal("skins/rainbow-raw/rainbow-ui.png"), false);
        label1Style.font = myFont;



        // affichage texte
        Label title = new Label("Level Selection Screen", skinMain,"button");
        title.setAlignment(Align.center);
        title.setY(SCREEN_HEIGHT*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        stageLevelSelection.addActor(title);



        TextButton buttonBack = new TextButton("Back", skinMain);
        buttonBack.setPosition(SCREEN_WIDTH/2-buttonBack.getWidth()/2-15,SCREEN_HEIGHT/4-buttonBack.getHeight()/2);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                game.setScreen(new Menu(game));
            }
        });

        stageLevelSelection.addActor(buttonBack);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stageLevelSelection);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stageLevelSelection.draw();
        // permet de jouer les events sur les boutons quand on met le curseur sur eux

        stageLevelSelection.act();
    }

    @Override
    public void resize(int width, int height) {
        skinMain.getFont("button").getData().setScale(SCREEN_WIDTH*0.8f/VIRTUAL_WIDTH,SCREEN_HEIGHT*0.8f/VIRTUAL_HEIGHT);
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
        skinMain.dispose();
        stageLevelSelection.dispose();
    }
}
