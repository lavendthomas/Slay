package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;

import java.util.ArrayList;

import be.ac.umons.slay.g02.players.StatsLoader;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.camera;
import static be.ac.umons.slay.g02.gui.Main.isAccountEnabled;
import static be.ac.umons.slay.g02.gui.Main.skinSgx;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.pm;
import static be.ac.umons.slay.g02.gui.Main.cursor;
import static be.ac.umons.slay.g02.gui.Main.stage;

// classe qui affiche le menu principal
public class Menu implements Screen {
    public static Drawable imagePanda = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/panda.jpg"))));
    public static Drawable imageYeti = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/yeti.jpg"))));
    public static Drawable imageWorm = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/worm.png"))));
    public static Drawable imageMustache = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/mustache.jpg"))));
    public static Drawable imageBunny = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/bunny.jpg"))));
    public static Drawable imageGirl = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/girl.jpg"))));
    public static Drawable imageRobot = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/robot.jpg"))));
    public static Drawable imagePenguin = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/penguin.jpg"))));
    public static Drawable imageBird = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/bird.jpg"))));
    public static Drawable imageSquid = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/squid.jpg"))));
    public static Drawable imageAnonymous
            = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/anonymous.png"))));
    public static ImageButton imageSelected = new ImageButton(imageAnonymous);
    public static ImageButton pandaButton = new ImageButton(imagePanda);
    public static ImageButton yetiButton = new ImageButton(imageYeti);
    public static ImageButton wormButton = new ImageButton(imageWorm);
    public static ImageButton mustacheButton = new ImageButton(imageMustache);
    public static ImageButton bunnyButton = new ImageButton(imageBunny);
    public static ImageButton girlButton = new ImageButton(imageGirl);
    public static ImageButton robotButton = new ImageButton(imageRobot);
    public static ImageButton penguinButton = new ImageButton(imagePenguin);
    public static ImageButton birdButton = new ImageButton(imageBird);
    public static ImageButton squidButton = new ImageButton(imageSquid);
    public static ArrayList tabScore;

    // pour les tests
    public static boolean isPlayer1Logged = true;
    public static boolean isPlayer2Logged = true;
    public static int playerRank = 4;
    public static int totalNumberPlayers = 11;
    public int buttonCenterWidth;

    // juste le temps des tests, a enlever
    TextButton boutonNiveau;
    private Game game;
    private SpriteBatch batch;
    private Sprite sprite;

    // doivent etre declares ici
    private TextButton buttonPlay;
    private TextButton buttonHall;
    private TextButton buttonSettings;
    private TextButton buttonExit;
    private Button switchRegistration;
    private ImageButton buttonProfileLeft;
    private ImageButton buttonProfileRight;
    private ImageButton buttonAvatar;
    private TextButton buttonEdit;
    private TextButton buttonLogOut;
    private TextButton buttonDelete;
    private TextButton buttonBack;
    private TextButton buttonSettingsBack;
    private TextButton buttonEditSave;
    private TextButton buttonEditCancel;
    private int buttonProfileHeight;
    private int windowSettingsWidth;
    private int windowProfileWidth;
    private String playerName;


    public Menu(Game aGame) {
        game = aGame;

        buttonCenterWidth = SCREEN_WIDTH * 28 / 100;
        int buttonCenterHeight = SCREEN_HEIGHT * 7 / 100;
        int buttonCenterGap = SCREEN_HEIGHT * 7 / 100;
        int labelProfileWidth = buttonCenterWidth * 35 / 100;
        int tableCenterPositionX = SCREEN_WIDTH / 2;
        int tableCenterPositionY = SCREEN_HEIGHT / 3;
        windowProfileWidth = SCREEN_WIDTH / 2 - SCREEN_WIDTH / 2 * 10 / 100;
        buttonProfileHeight = SCREEN_HEIGHT * 10 / 100;
        windowSettingsWidth = buttonCenterWidth * 3 / 4;

        // background
        batch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("backgrounds/background-title.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setOrigin(0, 0);
        sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

        // load Hall Of Fame (ca doit etre fait dans le menu)
        StatsLoader statsLoader = new StatsLoader();
        tabScore = statsLoader.createTab();

        Table tableCenter = new Table();
        tableCenter.setPosition(tableCenterPositionX, tableCenterPositionY);
        stage.addActor(tableCenter);

        buttonPlay = new TextButton("PLAY", skinSgx, "big");
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                stage.clear();
                game.setScreen(new LevelSelection(game));
            }
        });
        buttonSettings = new TextButton("SETTINGS", skinSgx, "big");
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                showSettings();
            }
        });
        buttonExit = new TextButton("EXIT", skinSgx, "big");
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showExit();
            }
        });


        if (isAccountEnabled) {

            Table tableProfile = new Table();
            tableProfile.setFillParent(true);
            tableProfile.right().bottom();

            buttonHall = new TextButton("HALL OF FAME", skinSgx, "big");
            buttonHall.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundButton1.play(0.2f);
                    stage.clear();
                    game.setScreen(new Hall(game));
                }
            });

            Label labelProfileLeft = new Label("Not Logged", skinSgx, "title");
            Label labelProfileRight = new Label("Not Logged", skinSgx, "title");
            labelProfileLeft.setFontScale(0.45f);
            labelProfileRight.setFontScale(0.45f);
            labelProfileLeft.setAlignment(1);
            labelProfileRight.setAlignment(1);
            // image anonyme (profil) a mettre dans une methode
            buttonProfileLeft = new ImageButton(imageAnonymous);
            buttonProfileLeft.addListener(new ClickListener() {
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    //             buttonProfileLeft.addAction(Actions.alpha(50));
                }

                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    //               buttonProfileLeft.clearActions();
                    //              buttonProfileLeft.addAction(Actions.fadeIn(0));
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundButton1.play(0.2f);
                    if (!isPlayer1Logged)
                        showLogginWindow();
                    else if (isPlayer1Logged)
                        showProfile(1);
                }
            });
            buttonProfileRight = new ImageButton(imageAnonymous);
            buttonProfileRight.addListener(new ClickListener() {
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    //                buttonProfileRight.addAction(Actions.alpha(50));
                }

                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    //              buttonProfileRight.clearActions();
                    //             buttonProfileRight.addAction(Actions.fadeIn(0));
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundButton1.play(0.2f);
                    if (!isPlayer2Logged)
                        showLogginWindow();
                    else if (isPlayer2Logged)
                        showProfile(2);
                }
            });

            tableCenter.add(buttonPlay).width(buttonCenterWidth).height(buttonCenterHeight);
            tableCenter.row().pad(buttonCenterGap, 0, buttonCenterGap, 0);
            tableCenter.add(buttonHall).width(buttonCenterWidth).height(buttonCenterHeight);
            tableCenter.row().pad(0, 0, buttonCenterGap, 0);
            tableCenter.add(buttonSettings).width(buttonCenterWidth).height(buttonCenterHeight);
            tableCenter.row();
            tableCenter.add(buttonExit).width(buttonCenterWidth).height(buttonCenterHeight);

            tableProfile.add(labelProfileLeft).width(labelProfileWidth);
            tableProfile.add(labelProfileRight).width(labelProfileWidth);
            tableProfile.row().pad(10, 0, 0, 0);
            ;

            tableProfile.add(buttonProfileLeft).width(buttonProfileHeight).height(buttonProfileHeight);
            tableProfile.add(buttonProfileRight).width(buttonProfileHeight).height(buttonProfileHeight);
            tableProfile.row();

            Label labelProfile = new Label("", skinSgx);
            tableProfile.add(labelProfile);
            tableProfile.add(labelProfile);

            tableProfile.row();
            stage.addActor(tableProfile);
        } else {
            tableCenter.add(buttonPlay).width(buttonCenterWidth).height(buttonCenterHeight);
            tableCenter.row().pad(buttonCenterGap, 0, buttonCenterGap, 0);
            tableCenter.add(buttonSettings).width(buttonCenterWidth).height(buttonCenterHeight);
            tableCenter.row();
            tableCenter.add(buttonExit).width(buttonCenterWidth).height(buttonCenterHeight);
        }


        // bouton le temps de faire les tests, a enlever
        boutonNiveau = new TextButton("NIVEAU", skinSgx, "big");
        boutonNiveau.setScale(1.2f);
        boutonNiveau.setPosition(tableCenterPositionX + buttonCenterWidth * 5 / 6, tableCenterPositionY/*Main.SCREEN_HEIGHT/2*/);
        boutonNiveau.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                stage.clear();
                game.setScreen(new GameScreen(game));
            }
        });
        stage.addActor(boutonNiveau);
    }

    public static void disableButton(Button... button) {
        for (Button b : button) {
            b.setDisabled(true);
            b.setTouchable(Touchable.disabled);
        }
    }

    public static void enableButton(Button... button) {
        for (Button b : button) {
            b.setDisabled(false);
            b.setTouchable(Touchable.enabled);
        }
    }

    public static void removeFromStage(Actor... actor) {
        for (Actor a : actor) {
            stage.getActors().removeValue(a, true);
        }
    }

    public static void addToStage(Actor... actor) {
        for (Actor a : actor) {
            stage.addActor(a);
        }
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


        // affichage rectangle,
/*
        ShapeRenderer shapeRenderer = new ShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.identity();
        shapeRenderer.setColor(1, 1, 1, 1);
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
        skinSgx.dispose();
        stage.dispose();
    }

    public void showSettings() {
        disableButton(buttonPlay, buttonSettings, buttonExit);
        if (isAccountEnabled)
            disableButton(buttonHall, buttonProfileLeft, buttonProfileRight);
        Window windowSettings = new Window("Settings", skinSgx);
        windowSettings.setSize(windowSettingsWidth, windowSettingsWidth);
        windowSettings.setPosition(SCREEN_WIDTH / 2 - windowSettings.getWidth() / 2, SCREEN_HEIGHT / 2 - windowSettings.getHeight() / 2);
        windowSettings.setMovable(false);

        // place le titre de la fenetre au milieu
        windowSettings.getTitleTable().padLeft(windowSettings.getWidth() / 2 - windowSettings.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        Table tableBack = new Table();
        windowSettings.addActor(table);
        windowSettings.addActor(tableBack);
        table.setFillParent(true);
        tableBack.setFillParent(true);
        table.top().padTop(windowSettings.getHeight() * 1 / 4);

        Label labelVolume = new Label("Volume", skinSgx, "white");
        labelVolume.setHeight(SCREEN_HEIGHT * 9 / 100);
        final Slider sliderVolume = new Slider(windowSettings.getWidth() / 3, windowSettings.getWidth() / 3, 1, false, skinSgx);

        // ne marche pas pour l'instant
        sliderVolume.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int coordinatesPointer) {
                sliderVolume.setRange(0, sliderVolume.getWidth());
                soundButton1.setVolume(1, x / sliderVolume.getWidth());
                soundButton2.setVolume(2, x / sliderVolume.getWidth());
            }
        });

        table.add(labelVolume).height(Value.percentHeight(1f)).width(windowSettings.getWidth() / 3);
        table.add(sliderVolume).height(Value.percentHeight(1f)).width(windowSettings.getWidth() / 3);
        table.row();

        Label labelRegistration = new Label("Registration", skinSgx, "white");

        switchRegistration = new Button(skinSgx, "switch");

        if (isAccountEnabled)
            switchRegistration.setChecked(true);

        switchRegistration.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!switchRegistration.isChecked()) {
                    showAlertSettings();
                } else {
                    soundButton2.play(0.2f);
                }
            }
        });

        table.add(labelRegistration);
        table.add(switchRegistration);
        table.row();

        buttonSettingsBack = new TextButton("Back", skinSgx, "big");
        buttonSettingsBack.setWidth(buttonCenterWidth / 2);
        buttonSettingsBack.setScale(1.2f);
        buttonSettingsBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                if (switchRegistration.isChecked()) {
                    isAccountEnabled = true;
                    stage.clear();
                    game.setScreen(new Menu(game));
                } else if (!switchRegistration.isChecked()) {
                    isAccountEnabled = false;
                    stage.clear();
                    game.setScreen(new Menu(game));
                }
            }
        });

        tableBack.add(buttonSettingsBack).fill().width(windowSettings.getWidth() / 3).padBottom(windowSettings.getHeight() * 1 / 7);
        tableBack.bottom();

        stage.addActor(windowSettings);
    }

    public void showAlertSettings() {
        disableButton(buttonSettingsBack, switchRegistration);
        final Window windowAlert = new Window("Disable Registration", skinSgx);
        windowAlert.setSize(windowSettingsWidth, windowSettingsWidth);
        windowAlert.setPosition(SCREEN_WIDTH / 2 - windowSettingsWidth / 2, SCREEN_HEIGHT / 2 - windowSettingsWidth / 2);
        windowAlert.setMovable(false);

        // place le titre de la fenetre au milieu
        windowAlert.getTitleTable().padLeft(windowAlert.getWidth() / 2 - windowAlert.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowAlert.addActor(table);
        table.setFillParent(true);

        table.top().padTop(windowSettingsWidth * 1 / 3);

        Label labelAlertSettings = new Label("All logged users\nwill be disconnected", skinSgx, "white");
        labelAlertSettings.setAlignment(1);

        table.add(labelAlertSettings).center().colspan(2).padBottom(windowSettingsWidth * 1 / 7);

        table.row();

        TextButton buttonContinue = new TextButton("Continue", skinSgx, "big");
        buttonContinue.setWidth(buttonCenterWidth / 2);
        buttonContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                enableButton(buttonSettingsBack, switchRegistration);
                windowAlert.remove();
            }
        });
        TextButton buttonCancel = new TextButton("Cancel", skinSgx, "big");
        buttonCancel.setWidth(buttonCenterWidth / 2);

        buttonCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                switchRegistration.setChecked(true);
                enableButton(buttonSettingsBack, switchRegistration);
                windowAlert.remove();
            }
        });

        table.add(buttonContinue).padRight(20).width(Value.percentWidth(0.6f)).fill();
        table.add(buttonCancel).width(Value.percentWidth(0.6f)).fill();
        table.row();

        stage.addActor(windowAlert);
    }

    public void showLogginWindow() {
        Window windowLoggin = new Window("Log In", skinSgx);
        windowLoggin.setSize(windowLoggin.getWidth(), windowLoggin.getHeight());
        windowLoggin.setPosition(SCREEN_WIDTH / 2 - windowLoggin.getWidth() / 2, SCREEN_HEIGHT / 2 - windowLoggin.getHeight() / 2);
        windowLoggin.setMovable(false);

        // place le titre de la fenetre au milieu
        windowLoggin.getTitleTable().padLeft(windowLoggin.getWidth() / 2 - windowLoggin.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowLoggin.addActor(table);
        table.setFillParent(true);

        stage.addActor(windowLoggin);
    }

    // int player --> 1 si c'est le joueur du bouton gauche, 2 sinon
    public void showProfile(int player) {
        // en supprimant un compte, il faudra recreer un menu avec l'image anonyme
        // donc pas besoin de detruire la fenetre ou de s'occuper de reactiver les boutons quand tu supprimes
        disableButton(buttonPlay, buttonSettings, buttonExit);
        if (isAccountEnabled)
            disableButton(buttonHall, buttonProfileLeft, buttonProfileRight);
        final Window windowProfile = new Window("Profile", skinSgx);
        windowProfile.setSize(windowProfileWidth, SCREEN_WIDTH / 2 - SCREEN_WIDTH / 2 * 10 / 100);
        windowProfile.setPosition(SCREEN_WIDTH / 2 - windowProfile.getWidth() / 2, SCREEN_HEIGHT / 2 - windowProfile.getHeight() / 2);
        windowProfile.setMovable(false);

        // place le titre de la fenetre au milieu
        windowProfile.getTitleTable().padLeft(windowProfile.getWidth() / 2 - windowProfile.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowProfile.addActor(table);
        table.setFillParent(true);
        table.bottom().padTop(SCREEN_HEIGHT * 6 / 100);
        table.setSize(windowProfile.getWidth(), windowProfile.getHeight());


        if (player == 1)
            playerName = LevelSelection.player1Name;
        else if (player == 2)
            playerName = LevelSelection.player2Name;


        Label labelWelcome = new Label("Welcome " + playerName + " !", skinSgx, "title-white");

        ImageButton buttonAvatar = new ImageButton(imageAnonymous);
        buttonAvatar.addListener(new ClickListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //             afficher message
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //               retire message
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                imageSelected = new ImageButton(imageAnonymous);
                //            System.out.print("ok-anonym");
                showAvatarSelectionWindow();
            }
        });

        disableButton(buttonAvatar);

        Label labelRank = new Label("Rank : " + playerRank + " / " + totalNumberPlayers, skinSgx, "white");

        labelWelcome.setHeight(SCREEN_HEIGHT * 10 / 100);
        table.add(labelWelcome).height(Value.percentHeight(1f));
        table.row();

        buttonAvatar.setSize(buttonProfileHeight, buttonProfileHeight);
        table.add(buttonAvatar).height(Value.percentHeight(1f)).width(Value.percentWidth(1f));
        table.row();

        labelRank.setHeight(SCREEN_HEIGHT * 10 / 100);
        table.add(labelRank).height(Value.percentHeight(1f));
        table.row();
        table.top();

        buttonEdit = new TextButton("Edit", skinSgx, "big");
        buttonEdit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                showEdit();
            }
        });
        buttonLogOut = new TextButton("Log Out", skinSgx, "big");
        buttonLogOut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                showLogOut();
            }
        });
        buttonDelete = new TextButton("Delete Account", skinSgx, "big");
        buttonDelete.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                showDelete();
            }
        });
        buttonBack = new TextButton("Back", skinSgx, "big");
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);
                windowProfile.remove();
                removeFromStage(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
                enableButton(buttonPlay, buttonSettings, buttonExit);
                if (isAccountEnabled)
                    enableButton(buttonHall, buttonProfileLeft, buttonProfileRight);
            }
        });

        buttonEdit.setPosition(SCREEN_WIDTH / 2 - windowProfile.getWidth() * 16 / 100, windowProfile.getHeight() / 2 + windowProfile.getHeight() * 8 / 100);
        buttonEdit.setWidth(windowProfile.getWidth() * 32 / 100);
        windowProfile.center();
        buttonLogOut.setPosition(SCREEN_WIDTH / 2 - windowProfile.getWidth() * 16 / 100, windowProfile.getHeight() / 2 - windowProfile.getHeight() * 5 / 100);

        buttonLogOut.setWidth(windowProfile.getWidth() * 32 / 100);

        buttonDelete.setPosition(SCREEN_WIDTH / 2 - windowProfile.getWidth() * 16 / 100, windowProfile.getHeight() / 2 - windowProfile.getHeight() * 18 / 100);
        buttonDelete.setWidth(windowProfile.getWidth() * 32 / 100);

        buttonBack.setPosition(SCREEN_WIDTH / 2 - windowProfile.getWidth() * 16 / 100, windowProfile.getHeight() / 2 - windowProfile.getHeight() * 31 / 100);
        buttonBack.setWidth(windowProfile.getWidth() * 32 / 100);
/*
        table.add(buttonEdit).padTop(SCREEN_HEIGHT*10/100);
        table.row();

        table.add(buttonLogOut).padTop(SCREEN_HEIGHT*2/100);
        table.row();

        table.add(buttonDelete).padTop(SCREEN_HEIGHT*2/100);
        table.row();

        table.add(buttonBack).padTop(SCREEN_HEIGHT*2/100);
*/
        addToStage(windowProfile, buttonEdit, buttonLogOut, buttonDelete, buttonBack);
    }

    public void showEdit() {
        disableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
        final Window windowEdit = new Window("Edit", skinSgx);
        windowEdit.setSize(SCREEN_WIDTH / 2 - SCREEN_WIDTH / 2 * 10 / 100, SCREEN_WIDTH / 2 - SCREEN_WIDTH / 2 * 10 / 100);
        windowEdit.setPosition(SCREEN_WIDTH / 2 - windowProfileWidth / 2, SCREEN_HEIGHT / 2 - windowProfileWidth / 2);
        windowEdit.setMovable(false);

        // place le titre de la fenetre au milieu
        windowEdit.getTitleTable().padLeft(windowEdit.getWidth() / 2 - windowEdit.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        //  table.setDebug(true);
        windowEdit.addActor(table);
        table.setFillParent(true);

        table.top().left();
        table.setSize(windowProfileWidth, windowProfileWidth);

        buttonAvatar = new ImageButton(imageAnonymous);
        buttonAvatar.setSize(buttonProfileHeight, buttonProfileHeight);


        buttonAvatar.addListener(new ClickListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //             afficher message
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //               retire message
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {

                imageSelected = new ImageButton(imageAnonymous);
                //           System.out.print("ok ano edit");
                showAvatarSelectionWindow();
            }
        });

        buttonEditCancel = new TextButton("Cancel", skinSgx, "big");
        buttonEditCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);
                windowEdit.remove();
                enableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
            }
        });

        buttonEditSave = new TextButton("Save", skinSgx, "big");
        buttonEditSave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);

                // a faire
            }
        });

        Label labelCurrentPassword = new Label("Current Password", skinSgx, "title-white");

        labelCurrentPassword.setHeight(SCREEN_HEIGHT * 10 / 100);
        labelCurrentPassword.setAlignment(0);
        Label labelNewPassword1 = new Label("New Password", skinSgx, "title-white");
        Label labelNewPassword2 = new Label("Re-enter New Password", skinSgx, "title-white");

        TextField fieldCurrentPassword = new TextField("", skinSgx);
        fieldCurrentPassword.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        TextField fieldNewPassword1 = new TextField("", skinSgx);

        fieldNewPassword1.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        TextField fieldNewPassword2 = new TextField("", skinSgx);

        fieldNewPassword2.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });

        Label messageError = new Label("*incorrect password", skinSgx, "title-white");
        messageError.setColor(Color.RED);
        table.add(buttonAvatar).colspan(2).height(Value.percentHeight(1f)).width(Value.percentWidth(1f)).padBottom(SCREEN_HEIGHT * 5 / 100).padTop(SCREEN_HEIGHT * 5 / 100);
        table.row();
        table.add(labelCurrentPassword).left().colspan(2);
        table.row();
        table.add(fieldCurrentPassword).fill().colspan(2);
        table.row();
        table.add(messageError).left().padBottom(SCREEN_HEIGHT * 5 / 100).colspan(2);
        table.row();
        table.add(labelNewPassword1).left().colspan(2);

        table.row();
        table.add(fieldNewPassword1).left().fill().colspan(2);
        table.row();
        table.add(messageError).left().padBottom(SCREEN_HEIGHT * 5 / 100).colspan(2);
        table.row();
        table.add(labelNewPassword2).left().width(Value.percentWidth(1f)).fill().colspan(2);

        table.row();
        table.add(fieldNewPassword2).fill().left().colspan(2);
        table.row();
        table.add(messageError).left().padBottom(SCREEN_HEIGHT * 5 / 100).colspan(2);
        table.row();

        buttonEditCancel.setWidth(table.getWidth() * 19 / 100);
        buttonEditSave.setWidth(table.getWidth() * 19 / 100);
        table.add(buttonEditCancel).padRight(table.getWidth() * 15 / 100).width(Value.percentWidth(1f));
        table.add(buttonEditSave).width(Value.percentWidth(1f));

        windowEdit.add(table);

        stage.addActor(windowEdit);
    }

    public void showAvatarSelectionWindow() {
        disableButton(buttonAvatar, buttonEditSave, buttonEditCancel);
        final Window windowAvatarSelection = new Window("Choose an avatar", skinSgx);
        windowAvatarSelection.setSize(SCREEN_WIDTH / 2, SCREEN_WIDTH / 2);
        windowAvatarSelection.setPosition(SCREEN_WIDTH / 2 - windowAvatarSelection.getWidth() / 2, SCREEN_HEIGHT / 2 - windowAvatarSelection.getHeight() / 2);
        windowAvatarSelection.setMovable(false);

        // place le titre de la fenetre au milieu
        windowAvatarSelection.getTitleTable().padLeft(windowAvatarSelection.getWidth() / 2 - windowAvatarSelection.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        table.setDebug(true);
        windowAvatarSelection.addActor(table);
        table.setFillParent(true);
        table.top().padTop(SCREEN_WIDTH * 1 / 15);

        TextButton buttonImport = new TextButton("Import...", skinSgx, "big");
        buttonImport.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.1f);

                // chercher une image sur le pc et remplacer l'image de l'avatar actuel par cette image
            }
        });
        TextButton buttonSave = new TextButton("Save", skinSgx, "big");
        buttonSave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);

                // enregistrer le nouvel avatar (sauvegarder l'image dans le dossier des avatars si
                // elle est importee + modifier le chemin de l'image dans le fichier ou la classe)

                windowAvatarSelection.remove();

                showEdit();
            }
        });
        TextButton buttonCancel = new TextButton("Cancel", skinSgx, "big");
        buttonCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);

                //   windowAvatarSelection.clearListeners();
                //       windowAvatarSelection.getChildren().clear();
                //    windowAvatarSelection.remove();

                windowAvatarSelection.remove();
                enableButton(buttonAvatar, buttonEditSave, buttonEditCancel);

                //          showEdit();
            }
        });

        pandaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                //           System.out.print("ok-panda");
                // remplacer l'image de l'avatar actuel par celle contenue dans ce bouton (ici image)
                imageSelected = new ImageButton(imagePanda);
                //   imageSelected.setBackground(imagePanda) ;
                // windowAvatarSelection.clearListeners();
                // windowAvatarSelection.getChildren().clear();
                // stage.getActors().removeValue(windowAvatarSelection, true);
                //    windowAvatarSelection.remove();
                pandaButton.setDisabled(true);
                windowAvatarSelection.setClip(false);
                windowAvatarSelection.setTransform(true);
                showAvatarSelectionWindow();
            }
        });
        yetiButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                //            System.out.print("ok-panda");
                // remplacer l'image de l'avatar actuel par celle contenue dans ce bouton (ici image)
                imageSelected = new ImageButton(imagePanda);
                //   imageSelected.setBackground(imagePanda) ;
                // windowAvatarSelection.clearListeners();
                // windowAvatarSelection.getChildren().clear();
                // stage.getActors().removeValue(windowAvatarSelection, true);
                windowAvatarSelection.remove();

                showAvatarSelectionWindow();
            }
        });

        wormButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                imageSelected = new ImageButton(imageWorm);
                windowAvatarSelection.remove();
                showAvatarSelectionWindow();
            }
        });
        mustacheButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                imageSelected = new ImageButton(imageMustache);
                windowAvatarSelection.remove();
                //showAvatarSelectionWindow();
            }
        });
        bunnyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                imageSelected = new ImageButton(imageBunny);
                windowAvatarSelection.remove();
                showAvatarSelectionWindow();
            }
        });

        float widthImage = windowAvatarSelection.getWidth() * 2 / 15;
        float heightImage = windowAvatarSelection.getWidth() * 2 / 10;

        table.add(pandaButton).width(widthImage).height(heightImage).padRight(heightImage / 5);
        table.add(yetiButton).width(widthImage).height(heightImage);
        table.add(wormButton).width(widthImage).height(heightImage);
        table.add(mustacheButton).width(widthImage).height(heightImage).padRight(heightImage / 5);
        ;
        table.add(bunnyButton).width(widthImage).height(heightImage);

        table.row();


        ImageButton girlButton = new ImageButton(imageGirl);
        girlButton.setSize(50f, 50f);
        table.add(girlButton).width(widthImage).height(heightImage).padRight(heightImage / 5);
        ;
        table.add(robotButton).width(widthImage).height(heightImage);
        table.add(penguinButton).width(widthImage).height(heightImage);
        table.add(birdButton).width(widthImage).height(heightImage).padRight(heightImage / 5);
        ;
        table.add(squidButton).width(widthImage).height(heightImage);
        table.row().padTop(SCREEN_WIDTH / 50);

        table.add(buttonImport).colspan(2).width(Value.percentWidth(1f));
        table.add(imageSelected).width(heightImage).height(heightImage);
        buttonSave.setWidth(100);
        table.add(buttonSave).colspan(2).width(Value.percentWidth(1f));

        table.row().padTop(20);

        table.add(buttonCancel).colspan(5);
        table.row();

        stage.addActor(windowAvatarSelection);
    }

    public ImageButton createImageButton(TextureRegionDrawable image) {
        ImageButton imageButton = new ImageButton(image);
        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                //        System.out.print("ok");
            }
        });

        return imageButton;
    }

    private void showLogOut() {
        disableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);

        final Window windowLogOut = new Window("Log Out", skinSgx);
        windowLogOut.setSize(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4);
        windowLogOut.setPosition(SCREEN_WIDTH / 2 - windowLogOut.getWidth() / 2, SCREEN_HEIGHT / 2 - windowLogOut.getHeight() / 2);
        windowLogOut.setMovable(false);

        // place le titre de la fenetre au milieu
        windowLogOut.getTitleTable().padLeft(windowLogOut.getWidth() / 2 - windowLogOut.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowLogOut.addActor(table);
        table.setFillParent(true);
        table.padTop(windowLogOut.getHeight() * 17 / 100);

        Label labelLogOutConfirm = new Label("Are you sure ?", skinSgx, "white");

        TextButton buttonYes = new TextButton("Yes", skinSgx, "big");
        buttonYes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);

                // remettre l'image anonyme dans le bouton du menu qui est concerne

                stage.clear();
                new Menu(game);
            }
        });

        TextButton buttonNo = new TextButton("No", skinSgx, "big");
        buttonNo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                windowLogOut.remove();
                enableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
            }
        });

        table.add(labelLogOutConfirm).width(Value.percentWidth(1f)).colspan(2).padBottom(SCREEN_WIDTH / 3 * 8 / 100);
        table.row();
        buttonYes.setWidth(SCREEN_WIDTH / 3 * 20 / 100);
        buttonNo.setWidth(SCREEN_WIDTH / 3 * 20 / 100);
        table.add(buttonYes).padRight(windowLogOut.getWidth() * 8 / 100).width(Value.percentWidth(1f));
        table.add(buttonNo).width(Value.percentWidth(1f));

        stage.addActor(windowLogOut);
    }

    private void showExit() {
        disableButton(buttonPlay, buttonSettings, buttonExit);
        if (isAccountEnabled)
            disableButton(buttonHall, buttonProfileLeft, buttonProfileRight);

        final Window windowExit = new Window("Exit", skinSgx);
        windowExit.setSize(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4);
        windowExit.setPosition(SCREEN_WIDTH / 2 - windowExit.getWidth() / 2, SCREEN_HEIGHT / 2 - windowExit.getHeight() / 2);
        windowExit.setMovable(false);

        // place le titre de la fenetre au milieu
        windowExit.getTitleTable().padLeft(windowExit.getWidth() / 2 - windowExit.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowExit.addActor(table);
        table.setFillParent(true);
        table.padTop(windowExit.getHeight() * 17 / 100);

        Label labelExitConfirm = new Label("Are you sure ?", skinSgx, "white");

        TextButton buttonYes = new TextButton("Yes", skinSgx, "big");
        buttonYes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                //  System.exit(0);
            }
        });

        TextButton buttonNo = new TextButton("No", skinSgx, "big");
        buttonNo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);
                windowExit.remove();
                enableButton(buttonPlay, buttonSettings, buttonExit);
                if (isAccountEnabled)
                    enableButton(buttonHall, buttonProfileLeft, buttonProfileRight);
            }
        });

        table.add(labelExitConfirm).width(Value.percentWidth(1f)).colspan(2).padBottom(SCREEN_WIDTH / 3 * 8 / 100);
        table.row();
        buttonYes.setWidth(SCREEN_WIDTH / 3 * 20 / 100);
        buttonNo.setWidth(SCREEN_WIDTH / 3 * 20 / 100);
        table.add(buttonYes).padRight(windowExit.getWidth() * 8 / 100).width(Value.percentWidth(1f));
        table.add(buttonNo).width(Value.percentWidth(1f));

        stage.addActor(windowExit);
    }

    private void showDelete() {
        disableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
        final Window windowDelete = new Window("Delete Account", skinSgx);
        windowDelete.setSize(SCREEN_WIDTH / 2, SCREEN_HEIGHT * 2 / 5);
        windowDelete.setPosition(SCREEN_WIDTH / 2 - windowDelete.getWidth() / 2, SCREEN_HEIGHT / 2 - windowDelete.getHeight() / 2);
        windowDelete.setMovable(false);

        // place le titre de la fenetre au milieu
        windowDelete.getTitleTable().padLeft(windowDelete.getWidth() / 2 - windowDelete.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowDelete.addActor(table);
        table.setFillParent(true);


        Label labelWarning1 = new Label("All your data will be erased", skinSgx, "white");
        Label labelWarning2 = new Label("including your achievements in the Hall Of Fame", skinSgx, "white");
        Label labelPassword = new Label("Enter your password", skinSgx, "white");
        TextField fieldPassword = new TextField("", skinSgx);
        fieldPassword.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });


        TextButton buttonConfirm = new TextButton("Confirm", skinSgx, "big");
        buttonConfirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);
                /*
                   il faudra supprimer toutes les donnees concernant le joueur dans les fichiers + (ne pas oublier)
                   dans le hall of fame + detruire enfin les classes liees au joueur
                   et on peut eventuellement supprimer l'image de son avatar du fichier contenant tous les avatars
                   si elle ne fait pas partie des images proposees par defaut (c'est comme ca qu'on la reconnaitra)

                   + remettre l'image du bouton de profil concerne en anonyme

                */

                stage.clear();
                new Menu(game);
            }
        });
        TextButton buttonCancel = new TextButton("Cancel", skinSgx, "big");
        buttonCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);
                windowDelete.remove();
                enableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
            }
        });


        table.add(labelWarning1);
        table.row();
        table.add(labelWarning2);
        table.row();
        table.add(labelPassword);
        table.add(fieldPassword);
        table.row();
        table.add(buttonConfirm);
        table.row();
        table.add(buttonCancel);


        stage.addActor(windowDelete);
    }
}
