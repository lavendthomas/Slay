package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.camera;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.pm;
import static be.ac.umons.slay.g02.gui.Main.cursor;

// classe qui affiche le menu principal
public class Menu implements Screen {

    private Game game;
    private Stage stageMenu;
    public static Skin skinMenu;
    TextButton buttonPlay;
    TextButton buttonHall;
    TextButton buttonSettings;
    TextButton buttonExit;
    ImageButton buttonProfileLeft;
    ImageButton buttonProfileRight;
    int buttonCenterWidth;
    int buttonCenterHeight;
    int buttonCenterGap;
    int buttonProfileGap;
    int tableCenterPositionX;
    int tableCenterPositionY;
    int buttonProfileHeight;
    int tableProfilePositionX;
    int tableProfilePositionY;

    Drawable imageAnonymous;

    Table tableCenter;
    Table tableProfile;

    Texture texture;

    // juste le temps des tests, a enlever
    TextButton boutonNiveau;

    SpriteBatch batch;
    private Sprite sprite;


    public Menu(Game aGame) {
        game = aGame;
        stageMenu = new Stage(new ScreenViewport());

        skinMenu = new Skin(Gdx.files.internal("skins/rainbow-soldier/rainbow-ui.json"));

        buttonCenterWidth = SCREEN_WIDTH * 2 / 7;
        buttonCenterHeight = SCREEN_HEIGHT / 14;
        buttonCenterGap = SCREEN_HEIGHT / 14;
        buttonProfileGap = SCREEN_WIDTH / 40;
        tableCenterPositionX = SCREEN_WIDTH / 2 - 20;
        tableCenterPositionY = SCREEN_HEIGHT / 3;
        // a revoir pour que ca s'adapte a toutes les resolutions
        buttonProfileHeight = 15 * buttonCenterWidth / buttonCenterHeight + 15 * buttonCenterHeight / buttonCenterWidth;
        tableProfilePositionX = SCREEN_WIDTH * 1765 / VIRTUAL_WIDTH;
        tableProfilePositionY = SCREEN_HEIGHT * 80 / VIRTUAL_HEIGHT;

        imageAnonymous = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/anonymous.png"))));

        // background
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("backgrounds/menu_5.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setOrigin(0, 0);
        sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

        tableCenter = new Table();
        tableCenter.setPosition(tableCenterPositionX, tableCenterPositionY);
        stageMenu.addActor(tableCenter);
        tableProfile = new Table();
        tableProfile.setPosition(tableProfilePositionX, tableProfilePositionY);
        // juste pour afficher les contours de la table si besoin, a enlever
        tableProfile.setDebug(false);
        stageMenu.addActor(tableProfile);

        buttonPlay = new TextButton("PLAY", skinMenu);
        buttonHall = new TextButton("HALL OF FAME", skinMenu);
        buttonSettings = new TextButton("SETTINGS", skinMenu);
        buttonExit = new TextButton("EXIT", skinMenu);


        // image anonyme (profil) a mettre dans une methode
        // a transformer en ImageTextButton pour afficher Not Logged
        buttonProfileLeft = new ImageButton(imageAnonymous);
        buttonProfileRight = new ImageButton(imageAnonymous);


        tableCenter.add(buttonPlay).width(buttonCenterWidth).height(buttonCenterHeight);
        tableCenter.row().pad(buttonCenterGap, 0, buttonCenterGap, 0);
        tableCenter.add(buttonHall).width(buttonCenterWidth).height(buttonCenterHeight);
        tableCenter.row().pad(0, 0, buttonCenterGap, 0);
        tableCenter.add(buttonSettings).width(buttonCenterWidth).height(buttonCenterHeight);
        tableCenter.row();
        tableCenter.add(buttonExit).width(buttonCenterWidth).height(buttonCenterHeight);

        tableProfile.add(buttonProfileLeft).width(buttonProfileHeight).height(buttonProfileHeight);
        tableProfile.add(buttonProfileRight).width(buttonProfileHeight).height(buttonProfileHeight).padLeft(buttonProfileGap);


        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                game.setScreen(new LevelSelection(game));
            }
        });

        buttonHall.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonHall.setText("Coming Soon");
            }
        });

        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonSettings.setText("Coming Soon");
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        buttonProfileLeft.addListener(new ClickListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                buttonProfileLeft.addAction(Actions.alpha(50));


            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                buttonProfileLeft.clearActions();
                buttonProfileLeft.addAction(Actions.fadeIn(0));
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        buttonProfileRight.addListener(new ClickListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                buttonProfileRight.addAction(Actions.alpha(50));


            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                buttonProfileRight.clearActions();
                buttonProfileRight.addAction(Actions.fadeIn(0));
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        // bouton le temps de faire les tests, a enlever
        boutonNiveau = new TextButton("NIVEAU", skinMenu);
        boutonNiveau.setPosition(tableCenterPositionX + buttonCenterWidth * 5 / 6, tableCenterPositionY/*Main.SCREEN_HEIGHT/2*/);
        boutonNiveau.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                game.setScreen(new GameScreen(game));
            }
        });
        stageMenu.addActor(boutonNiveau);
    }



/*
        button.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                progressLabel.addAction(new TemporalAction(duration){
                    LabelFormatter formatter = new LabelFormatter(message);
                    @Override protected void update(float percent) {
                        progressLabel.setText(formatter.getText(percent));
                    }
                });
            }
        });
*/

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stageMenu);
    }

    @Override
    public void render(float delta) {
        batch.begin();

        // place le background correctement dans la fenetre
        batch.setProjectionMatrix(camera.combined);

        sprite.draw(batch);

        batch.end();

        stageMenu.draw();
        // permet de jouer les events sur les boutons quand on met le curseur sur eux

        stageMenu.act();



/*      // tests affichage rectangle, marche pas

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.identity();
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.rect(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 500, 500);
        shapeRenderer.end();
*/
/*
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.rect(100, 100, 100, 100);
        shape.end();
*/
/*
        ShapeRenderer shapeRenderer = new ShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.line(50, 500, 1000, 1000);
        shapeRenderer.rect(500, 600, 1000, 1000);
        shapeRenderer.circle(200, 300, 200);
        shapeRenderer.end();
*/
    }

    @Override
    public void resize(int width, int height) {
        skinMenu.getFont("button").getData().setScale(SCREEN_WIDTH * 0.8f / VIRTUAL_WIDTH, SCREEN_HEIGHT * 0.8f / VIRTUAL_HEIGHT);
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
        skinMenu.dispose();
        stageMenu.dispose();
    }
}
