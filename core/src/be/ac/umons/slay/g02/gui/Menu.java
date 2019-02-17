package be.ac.umons.slay.g02.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.awt.Rectangle;

import javax.swing.GroupLayout;

import static com.badlogic.gdx.utils.Align.bottomRight;

// est utilisee comme la classe main
public class Menu extends Game {
    private SpriteBatch batch;
    private Skin skinRainbow;
    private Stage stage;


    ShapeRenderer shapeRenderer;

    TextureAtlas buttonAtlas;
    NinePatch buttonNinePatch;

    private Game game;


    int SCREEN_HEIGHT;
    int SCREEN_WIDTH;
    private static final int VIRTUAL_WIDTH = 1920;
    private static final int VIRTUAL_HEIGHT = 1080;
    TextButton buttonPlay;
    TextButton buttonHall;
    TextButton buttonSettings;
    TextButton buttonExit;
    ImageButton buttonProfileLeft;
    ImageButton buttonProfileRight;
    Sound buttonMenuSound;
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

    Pixmap pm;
    Cursor cursor;
    int xHotSpot;
    int yHotSpot;





    Texture texture;
    TextureRegion mainBackground;

    FitViewport fitViewport;




    @Override
    public void create() {
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        SCREEN_WIDTH = Gdx.graphics.getWidth();

        buttonCenterWidth = SCREEN_WIDTH*2/7;
        buttonCenterHeight = SCREEN_HEIGHT/14;
        buttonCenterGap = SCREEN_HEIGHT/14;
        buttonProfileGap = SCREEN_WIDTH/40;
        tableCenterPositionX = SCREEN_WIDTH/2-20;
        tableCenterPositionY = SCREEN_HEIGHT/3;
        // a revoir pour que ca s'adapte a toutes les resolutions
        buttonProfileHeight = 15*buttonCenterWidth/buttonCenterHeight+15*buttonCenterHeight/buttonCenterWidth;
        tableProfilePositionX = SCREEN_WIDTH*1765/VIRTUAL_WIDTH;
        tableProfilePositionY = SCREEN_HEIGHT*80/VIRTUAL_HEIGHT;

        imageAnonymous = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/anonymous.png"))));

        buttonMenuSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button_2.wav"));




        // determine l'ecran actuel, test
        setScreen(new MainScreen(game));




        batch = new SpriteBatch();
        skinRainbow = new Skin(Gdx.files.internal("skins/rainbow/rainbow-ui.json"));

        stage = new Stage();



        // a changer car l'image ne s'affiche pas bien
        stage.addActor(new Image(new Texture("backgrounds/menu_5.png")));


        // ne pas faire ca, ca donne un truc bizarre en plein ecran
        texture = new Texture(Gdx.files.internal("backgrounds/menu_5.png"));
        mainBackground = new TextureRegion(texture, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        batch = new SpriteBatch();

















        tableCenter = new Table();
        tableCenter.setPosition(tableCenterPositionX,tableCenterPositionY);
        stage.addActor(tableCenter);

        tableProfile = new Table();
        tableProfile.setPosition(tableProfilePositionX,tableProfilePositionY);
        // juste pour afficher les contours de la table si besoin, a enlever
        tableProfile.setDebug(false);
        stage.addActor(tableProfile);






        buttonPlay = new TextButton("PLAY", skinRainbow);
        buttonHall = new TextButton("HALL OF FAME", skinRainbow);
        buttonSettings = new TextButton("SETTINGS", skinRainbow);
        buttonExit = new TextButton("EXIT", skinRainbow);





        // image anonyme (profil) a mettre dans une methode
        // a transformer en ImageTextButton pour afficher Not Logged
        buttonProfileLeft = new ImageButton(imageAnonymous);
        buttonProfileRight = new ImageButton(imageAnonymous);





        tableCenter.add(buttonPlay).width(buttonCenterWidth).height(buttonCenterHeight);
        tableCenter.row().pad(buttonCenterGap, 0,buttonCenterGap, 0);
        tableCenter.add(buttonHall).width(buttonCenterWidth).height(buttonCenterHeight);
        tableCenter.row().pad(0, 0,buttonCenterGap, 0);
        tableCenter.add(buttonSettings).width(buttonCenterWidth).height(buttonCenterHeight);
        tableCenter.row();
        tableCenter.add(buttonExit).width(buttonCenterWidth).height(buttonCenterHeight);

        tableProfile.add(buttonProfileLeft).width(buttonProfileHeight).height(buttonProfileHeight);
        tableProfile.add(buttonProfileRight).width(buttonProfileHeight).height(buttonProfileHeight).padLeft(buttonProfileGap);







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




        // change cursor aspect
        pm = new Pixmap(Gdx.files.internal("cursors/cursor_2.png"));
        // x = pm.getWidth()/2 et y = pm.getHeight()/2 si on veut que ca pointe au centre du curseur
        // = 0 ca pointe au bout de la fleche
        xHotSpot = 0;
        yHotSpot = 0;
        cursor = Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot);
        Gdx.graphics.setCursor(cursor);









        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonMenuSound.play(0.2f);
                game.setScreen(new LevelSelection(game));
                //       buttonPlay.setText("Coming Soon");
            }
        });

        buttonHall.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonMenuSound.play(0.2f);
                buttonHall.setText("Coming Soon");
            }
        });

        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonMenuSound.play(0.2f);
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

        Gdx.input.setInputProcessor(stage);




    }

    @Override
    public void dispose() {
        cursor.dispose();
        skinRainbow.dispose();
        pm.dispose();
        buttonMenuSound.dispose();
        batch.dispose();
        stage.dispose();
    }

    @Override
    public void render() {
        // permet de passer d'un ecran a l'autre
        super.render();

        //      Gdx.gl.glClearColor(1,1,1,1);
        //     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


/*
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.identity();
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.rect(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 500, 500);
        shapeRenderer.end();
*/


        batch.begin();




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

/*      // test affichage message
        BitmapFont textFont = new BitmapFont(Gdx.files.internal("skins/default.png"), Gdx.files.internal("skins/default.fnt"), false);
        textFont.draw(batch, "Level Complete", 500, 500);
*/













        batch.end();

        stage.draw();
        // permet de jouer les events sur les boutons quand on met le curseur sur eux
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        skinRainbow.getFont("button").getData().setScale(SCREEN_WIDTH*0.8f/VIRTUAL_WIDTH,SCREEN_HEIGHT*0.8f/VIRTUAL_HEIGHT);


    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}