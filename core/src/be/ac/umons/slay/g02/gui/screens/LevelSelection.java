package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
    Table table;

    int buttonGapY;

    Label labelIsland;
    Label labelHuman;
    Label labelDifficulty;
    Label labelHall;

    TextButton buttonStats;
    TextButton buttonMore;
    TextButton buttonBack;
    TextButton buttonPlay;

    public LevelSelection(Game aGame) {
        game = aGame;
        stageLevelSelection = new Stage(new ScreenViewport());


        Label.LabelStyle label1Style = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("skins/rainbow-raw/font-button-export.fnt"),
                Gdx.files.internal("skins/rainbow-raw/rainbow-ui.png"), false);
        label1Style.font = myFont;



        buttonGapY = SCREEN_HEIGHT/14;


/*
        // affichage texte
        Label title = new Label("Level Selection Screen", skinMain,"button");
        title.setAlignment(Align.center);
        title.setY(SCREEN_HEIGHT*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        stageLevelSelection.addActor(title);
*/



        labelIsland = new Label("Island", skinMain,"button");
        //      labelIsland.setPosition(SCREEN_WIDTH/100 , SCREEN_HEIGHT*4/5);
        //       stageLevelSelection.addActor(labelIsland);

        labelHuman = new Label("Number of human players", skinMain,"button");
//        labelHuman.setPosition(SCREEN_WIDTH/100 , SCREEN_HEIGHT*3/5);
//        stageLevelSelection.addActor(labelHuman);

        labelDifficulty = new Label("Difficulty", skinMain,"button");
  //      labelDifficulty.setPosition(SCREEN_WIDTH/100 , SCREEN_HEIGHT*2/5);
        //      stageLevelSelection.addActor(labelDifficulty);

        labelHall = new Label("Hall Of Fame", skinMain,"button");



        buttonStats = new TextButton("Statistiques", skinMain);
//        buttonStats.setPosition(SCREEN_WIDTH/20, buttonStats.getHeight());
        buttonStats.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                game.setScreen(new Menu(game));
            }
        });

        buttonMore = new TextButton("More", skinMain);
//        buttonMore.setPosition(SCREEN_WIDTH/20, buttonMore.getHeight());
        buttonMore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                game.setScreen(new Menu(game));
            }
        });

        buttonBack = new TextButton("Back", skinMain);
 //       buttonBack.setPosition(SCREEN_WIDTH/20, buttonBack.getHeight());
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                game.setScreen(new Menu(game));
            }
        });

 //       stageLevelSelection.addActor(buttonBack);

        buttonPlay = new TextButton("Play", skinMain);
 //       buttonPlay.setPosition(SCREEN_WIDTH/2-buttonPlay.getWidth()/2, buttonPlay.getHeight());
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                game.setScreen(new GameScreen(game));
            }
        });

 //       stageLevelSelection.addActor(buttonPlay);

        table = new Table();
        table.setFillParent(true);

        table.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        table.setDebug(true);

        stageLevelSelection.addActor(table);

        table.add(labelIsland);
        //       table.add(buttonIslandLeft);
//       table.add(labelIslandNumber);
        //       table.add(buttonIslandRight);
        //      table.add(imageMap);
        table.add(buttonStats);
        table.add(labelHall);
        table.row().pad(buttonGapY, 0, buttonGapY, 0);
        table.add(labelHuman);
        //     table.add(buttonHumanNumber);
        table.row().pad(buttonGapY, 0, buttonGapY, 0);
        table.add(labelDifficulty);
        //     table.add(buttonDifficulty);
        table.row().pad(buttonGapY, 0, buttonGapY, 0);
        table.add(buttonBack);
        table.add(buttonPlay);

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
