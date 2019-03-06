package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.ArrayList;
import java.util.Locale;

import be.ac.umons.slay.g02.players.Colors;
import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.Statistics;
import be.ac.umons.slay.g02.players.StatsLoader;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.camera;
import static be.ac.umons.slay.g02.gui.Main.cursor;
import static be.ac.umons.slay.g02.gui.Main.isAccountEnabled;
import static be.ac.umons.slay.g02.gui.Main.pm;
import static be.ac.umons.slay.g02.gui.Main.skinSgx;
import static be.ac.umons.slay.g02.gui.Main.skinSgxTable;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.soundButton3;
import static be.ac.umons.slay.g02.gui.Main.stage;
import static be.ac.umons.slay.g02.gui.Main.lang;

/**
 * classe qui affiche le menu principal
 */
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
    public static Drawable backgroundGrey
            = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backgrounds/background-gray.png"))));
    public static ImageButton buttonEditAvatar = new ImageButton(imageAnonymous);
    public static ImageButton buttonLoginAvatar = new ImageButton(imageAnonymous);
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
    public static boolean isPlayer2Logged = false;
    public static int playerRank = 4;
    public static int totalNumberPlayers = 11;

    public static HumanPlayer player1 = new HumanPlayer("toto", Colors.C1);
    public static HumanPlayer player2 = new HumanPlayer("titi", Colors.C1);

    private static Window windowExit = new Window(lang.get("text_exit"), skinSgx);
    private static Window windowSettings = new Window(lang.get("text_settings"), skinSgx);
    private static Window windowAlert = new Window(lang.get("text_disable_registration"), skinSgx);
    private static Window windowProfile = new Window(lang.get("text_profile"), skinSgx);
    private static Window windowEdit = new Window(lang.get("text_edit"), skinSgx);
    private static Window windowAvatarSelection = new Window(lang.get("text_choose_an_avatar"), skinSgx);
    private static Window windowLogOut = new Window(lang.get("text_log_out"), skinSgx);
    private static Window windowDelete = new Window(lang.get("text_delete_account"), skinSgx);
    private static Table mainTableLogin = new Table();
    public int buttonCenterWidth;
    // pour savoir dans quel bouton le joueur s'identifie
    public boolean isProfileLeft = false;
    public boolean isProfileRight = false;
    TextButton boutonNiveau;
    // doivent etre declares ici
    private Game game;
    private SpriteBatch batch;
    private Sprite sprite;
    private TextButton buttonPlay;
    private TextButton buttonHall;
    private TextButton buttonSettings;
    private TextButton buttonExit;
    private TextButton buttonEdit;
    private TextButton buttonLogOut;
    private TextButton buttonDelete;
    private TextButton buttonBack;
    private TextButton buttonSettingsBack;
    private TextButton buttonEditSave;
    private TextButton buttonEditCancel;
    private TextButton buttonLogin;
    private TextButton buttonSignUp;
    private TextButton buttonTabLogin;
    private TextButton buttonTabSignUp;
    private TextButton buttonSignUpCancel;
    private Button switchRegistration;
    private Button switchFullscreen;
    private ImageButton buttonProfileLeft;
    private ImageButton buttonProfileRight;
    private int buttonProfileHeight;
    private int windowSettingsWidth;
    private String playerName;
    private boolean isInLogin = false;
    private boolean isInEdit = false;


    public Menu(Game aGame) {
        game = aGame;

        buttonCenterWidth = SCREEN_WIDTH * 28 / 100;
        int buttonCenterHeight = SCREEN_HEIGHT * 7 / 100;
        int buttonCenterGap = SCREEN_HEIGHT * 7 / 100;
        int labelProfileWidth = buttonCenterWidth * 35 / 100;
        int tableCenterPositionX = SCREEN_WIDTH / 2;
        int tableCenterPositionY = SCREEN_HEIGHT / 3;
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

        // test remplissage  joueur1
        Statistics statis = new Statistics();
        statis.setAvgFullAmyValue(100);
        statis.setAvgLostUnits(200);
        statis.setAvgTotalMoney(5000);
        statis.setAvgTrees(400);
        statis.setMaxTrees(500);
        statis.setMinLandsTurn(1000000);
        player1.setStatistics(statis);


        Table tableCenter = new Table();
        tableCenter.setPosition(tableCenterPositionX, tableCenterPositionY);
        stage.addActor(tableCenter);

        buttonPlay = new TextButton(lang.get("button_play"), skinSgx, "big");
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                stage.clear();
                game.setScreen(new LevelSelection(game));
            }
        });
        buttonSettings = new TextButton(lang.get("button_settings"), skinSgx, "big");
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                showSettings();
            }
        });
        buttonExit = new TextButton(lang.get("button_exit"), skinSgx, "big");
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

            buttonHall = new TextButton(lang.get("button_hof"), skinSgx, "big");
            buttonHall.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundButton1.play(0.2f);
                    stage.clear();
                    game.setScreen(new Hall(game));
                }
            });

            final Label labelProfileLeft = new Label(lang.get("label_not_logged"), skinSgx, "title");
            Label labelProfileRight = new Label(lang.get("label_not_logged"), skinSgx, "title");
            labelProfileLeft.setFontScale(0.45f);
            labelProfileRight.setFontScale(0.45f);
            labelProfileLeft.setAlignment(1);
            labelProfileRight.setAlignment(1);
            // image anonyme (profil) a mettre dans une methode
            buttonProfileLeft = new ImageButton(imageAnonymous);
            buttonProfileLeft.addListener(new ClickListener() {
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {


                }

                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {


                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundButton1.play(0.2f);
                    if (!isPlayer1Logged) {
                        showLoginWindow();
                        isProfileLeft = true;
                    } else if (isPlayer1Logged)
                        showProfile(1);
                }
            });
            buttonProfileRight = new ImageButton(imageAnonymous);
            buttonProfileRight.addListener(new ClickListener() {
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {


                }

                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {


                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundButton1.play(0.2f);
                    if (!isPlayer2Logged)
                        showLoginWindow();
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

    public static void disableBox(SelectBox... box) {
        for (SelectBox b : box) {
            b.setDisabled(true);
            b.setTouchable(Touchable.disabled);
        }
    }

    public static void enableBox(SelectBox... box) {
        for (SelectBox b : box) {
            b.setDisabled(false);
            b.setTouchable(Touchable.enabled);
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
        soundButton3.dispose();
        skinSgx.dispose();
        skinSgxTable.dispose();
        stage.dispose();
    }

    public void showSettings() {
        disableButton(buttonPlay, buttonSettings, buttonExit);
        if (isAccountEnabled)
            disableButton(buttonHall, buttonProfileLeft, buttonProfileRight);
        windowSettings.clear();
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
        table.top().padTop(windowSettings.getHeight() * 16 / 100);

        Label labelVolume = new Label("Volume", skinSgx, "white");
        labelVolume.setHeight(SCREEN_HEIGHT * 13 / 200);
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

        Label labelFullscreen = new Label("Fullscreen", skinSgx, "white");
        labelFullscreen.setHeight(SCREEN_HEIGHT * 13 / 200);

        switchFullscreen = new Button(skinSgx, "switch");

        if (!Gdx.graphics.isFullscreen())
            switchFullscreen.setChecked(false);

        switchFullscreen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Gdx.graphics.isFullscreen()) {
                    soundButton2.play(0.2f);
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                } else {
                    soundButton2.play(0.2f);
                    Gdx.graphics.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);
                }
            }
        });

        table.add(labelFullscreen).height(Value.percentHeight(1f)).padRight(windowSettingsWidth * 8 / 100);
        table.add(switchFullscreen);
        table.row();
        Label labelRegistration = new Label(lang.get("label_registration"), skinSgx, "white");
        labelRegistration.setHeight(SCREEN_HEIGHT * 13 / 200);

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

        table.add(labelRegistration).height(Value.percentHeight(1f)).padRight(windowSettingsWidth * 7 / 200);
        table.add(switchRegistration);
        table.row();

        buttonSettingsBack = new TextButton(lang.get("button_back"), skinSgx, "big");
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

        tableBack.add(buttonSettingsBack).fill().width(windowSettings.getWidth() / 3).padBottom(windowSettings.getHeight() * 14 / 100).height(Value.percentHeight(1f));
        tableBack.bottom();

        stage.addActor(windowSettings);
    }

    public void showAlertSettings() {
        disableButton(buttonSettingsBack, switchRegistration, switchFullscreen);
        windowAlert.clear();
        windowAlert.setSize(windowSettingsWidth, windowSettingsWidth);
        windowAlert.setPosition(SCREEN_WIDTH / 2 - windowSettingsWidth / 2, SCREEN_HEIGHT / 2 - windowSettingsWidth / 2);
        windowAlert.setMovable(false);
        // place le titre de la fenetre au milieu
        windowAlert.getTitleTable().padLeft(windowAlert.getWidth() / 2 - windowAlert.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowAlert.addActor(table);
        table.setFillParent(true);

        table.top().padTop(windowSettingsWidth * 1 / 3);

        Label labelAlertSettings = new Label(lang.get("label_all_users_will_be_deconnected"), skinSgx, "white");
        labelAlertSettings.setAlignment(1);

        table.add(labelAlertSettings).center().colspan(2).padBottom(windowSettingsWidth * 1 / 7);

        table.row();

        TextButton buttonContinue = new TextButton(lang.get("button_continue"), skinSgx, "big");
        buttonContinue.setWidth(buttonCenterWidth / 2);
        buttonContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                enableButton(buttonSettingsBack, switchRegistration, switchFullscreen);
                windowAlert.remove();
            }
        });
        TextButton buttonCancel = new TextButton(lang.get("button_cancel"), skinSgx, "big");
        buttonCancel.setWidth(buttonCenterWidth / 2);

        buttonCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                switchRegistration.setChecked(true);
                enableButton(buttonSettingsBack, switchRegistration, switchFullscreen);
                windowAlert.remove();
            }
        });

        table.add(buttonContinue).padRight(20).width(Value.percentWidth(0.6f)).fill();
        table.add(buttonCancel).width(Value.percentWidth(0.6f)).fill();
        table.row();

        stage.addActor(windowAlert);
    }

    public void showLoginWindow() {
        disableButton(buttonPlay, buttonSettings, buttonExit);
        if (isAccountEnabled)
            disableButton(buttonHall, buttonProfileLeft, buttonProfileRight);
        mainTableLogin.clear();
        stage.addActor(mainTableLogin);
        mainTableLogin.setFillParent(true);

        buttonTabLogin = new TextButton(lang.get("text_log_in"), skinSgx, "number");
        buttonTabLogin.setDisabled(true);
        buttonTabLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonTabLogin.setDisabled(true);
                buttonTabSignUp.setDisabled(false);
            }
        });
        buttonTabSignUp = new TextButton(lang.get("text_sign_up"), skinSgx, "number");
        buttonTabSignUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonTabLogin.setDisabled(false);
                buttonTabSignUp.setDisabled(true);
            }
        });

        Table tableButtons = new Table();
        tableButtons.add(buttonTabLogin).width((SCREEN_HEIGHT * 70 / 100) / 2);
        tableButtons.add(buttonTabSignUp).width((SCREEN_HEIGHT * 70 / 100) / 2);

        buttonTabSignUp.setTouchable(Touchable.enabled);
        mainTableLogin.add(tableButtons);
        mainTableLogin.row();

        Stack content = new Stack();

        buttonLoginAvatar.setSize(SCREEN_WIDTH * 7 / 100, SCREEN_WIDTH * 7 / 100);
        buttonLoginAvatar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonLoginAvatar = new ImageButton(imageAnonymous);
                isInLogin = true;
                showAvatarSelectionWindow();
            }
        });

        Label labelUserName = new Label(lang.get("label_username"), skinSgx, "white");
        Label labelNewPassword1 = new Label(lang.get("label_password"), skinSgx, "white");
        Label labelNewPassword2 = new Label(lang.get("label_password_again"), skinSgx, "white");

        TextField fieldUserName = new TextField("", skinSgx);
        fieldUserName.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        System.out.print("username " + labelUserName);
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

        Label labelCurrentPasswordLogin = new Label(lang.get("label_username"), skinSgx, "white");
        Label labelNewPassword1Login = new Label(lang.get("label_password"), skinSgx, "white");

        TextField fieldCurrentPasswordLogin = new TextField("", skinSgx);
        fieldCurrentPasswordLogin.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        TextField fieldNewPassword1Login = new TextField("", skinSgx);

        fieldNewPassword1Login.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });

        Label messageError = new Label(lang.get("label_incorrect_password"), skinSgx, "white");
        messageError.setColor(Color.RED);
        Label messageErrorLoginPassword = new Label(lang.get("label_incorrect_password"), skinSgx, "white");
        messageErrorLoginPassword.setColor(Color.RED);
        Label messageErrorUsername = new Label(lang.get("label_username_inexistant"), skinSgx, "white");
        messageErrorUsername.setColor(Color.RED);

        buttonLogin = new TextButton(lang.get("text_log_in"), skinSgx, "big");
        buttonLogin.setWidth(buttonCenterWidth / 2);
        buttonLogin.setScale(1.2f);
        buttonLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                // find player
                //     player1=getStatisticsPlayer(fieldUserName,fieldCurrentPasswordLogin);
                // gerer messages d'erreurs avec exceptions

                // si tout va bien :

                mainTableLogin.remove();
                // a modifier
                showProfile(2);
            }
        });
        buttonSignUp = new TextButton(lang.get("text_sign_up"), skinSgx, "big");
        buttonSignUp.setWidth(buttonCenterWidth / 2);
        buttonSignUp.setScale(1.2f);
        buttonSignUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);

                // gerer messages d'erreurs avec exceptions

                // si tout va bien :

                mainTableLogin.remove();
                // a modifier
                showProfile(2);
            }
        });
        TextButton buttonLoginCancel = new TextButton(lang.get("text_cancel"), skinSgx, "big");
        buttonLoginCancel.setWidth(buttonCenterWidth / 2);
        buttonLoginCancel.setScale(1.2f);
        buttonLoginCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                mainTableLogin.remove();
                enableButton(buttonPlay, buttonSettings, buttonExit);
                if (isAccountEnabled)
                    enableButton(buttonHall, buttonProfileLeft, buttonProfileRight);
            }
        });
        buttonSignUpCancel = new TextButton(lang.get("text_cancel"), skinSgx, "big");
        buttonSignUpCancel.setWidth(buttonCenterWidth / 2);
        buttonSignUpCancel.setScale(1.2f);
        buttonSignUpCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);
                mainTableLogin.remove();
                enableButton(buttonPlay, buttonSettings, buttonExit);
                if (isAccountEnabled)
                    enableButton(buttonHall, buttonProfileLeft, buttonProfileRight);
            }
        });

        // -----------------------------------------------------------------------------------------------------
        // Sign Up

        final Table tableSignUp = new Table();
        tableSignUp.setBackground(backgroundGrey);

        Label avatarLabel = new Label(lang.get("label_click_to_change_avatar"), skinSgx);
        avatarLabel.setAlignment(1);
        tableSignUp.add(avatarLabel).colspan(2).width(Value.percentWidth(1f)).padBottom(SCREEN_HEIGHT * 2 / 100);
        tableSignUp.row();

        tableSignUp.add(buttonLoginAvatar).height(Value.percentHeight(1f)).width(Value.percentWidth(1f)).padBottom(SCREEN_HEIGHT * 4 / 100).colspan(2);
        tableSignUp.row();

        labelUserName.setWidth(SCREEN_HEIGHT * 70 / 100 * 55 / 100);
        fieldUserName.setWidth(SCREEN_HEIGHT * 70 / 100 * 45 / 100);
        labelUserName.setAlignment(1);
        tableSignUp.add(labelUserName).fill();
        tableSignUp.add(fieldUserName).fill().left().width(Value.percentWidth(1f));
        tableSignUp.row();
        tableSignUp.add(messageError).left().padBottom(SCREEN_HEIGHT * 3 / 100).colspan(2);
        tableSignUp.row();
        labelNewPassword1.setAlignment(1);
        labelNewPassword1.setWidth(SCREEN_HEIGHT * 70 / 100 * 55 / 100);
        fieldNewPassword1.setWidth(SCREEN_HEIGHT * 70 / 100 * 45 / 100);
        tableSignUp.add(labelNewPassword1);
        tableSignUp.add(fieldNewPassword1).fill().width(Value.percentWidth(1f));
        tableSignUp.row();
        tableSignUp.add(messageError).left().padBottom(SCREEN_HEIGHT * 3 / 100).colspan(2);
        tableSignUp.row();
        labelNewPassword2.setWidth(SCREEN_HEIGHT * 70 / 100 * 55 / 100);
        fieldNewPassword2.setWidth(SCREEN_HEIGHT * 70 / 100 * 45 / 100);
        labelNewPassword2.setAlignment(1);
        tableSignUp.add(labelNewPassword2).width(Value.percentWidth(1f)).fill();//.padRight(SCREEN_WIDTH * 2 / 100);
        tableSignUp.add(fieldNewPassword2).fill();
        tableSignUp.row();
        messageError.setAlignment(1);
        tableSignUp.add(messageError).padBottom(SCREEN_HEIGHT * 3 / 100).colspan(2);
        tableSignUp.row().padTop(SCREEN_HEIGHT * 70 / 100 * 4 / 100);
        buttonSignUpCancel.setWidth(SCREEN_HEIGHT * 70 / 100 * 21 / 100);
        buttonSignUp.setWidth(buttonSignUpCancel.getWidth());

        tableSignUp.add(buttonSignUpCancel).width(Value.percentWidth(1f)).left().padLeft(SCREEN_WIDTH * 9 / 100);
        tableSignUp.add(buttonSignUp).width(Value.percentWidth(1f)).right().padRight(SCREEN_WIDTH * 9 / 100);

//       Login

        final Table tableLogin = new Table();
        tableLogin.setBackground(backgroundGrey);

        labelCurrentPasswordLogin.setWidth(SCREEN_HEIGHT * 70 / 100 * 55 / 100);
        fieldCurrentPasswordLogin.setWidth(SCREEN_HEIGHT * 70 / 100 * 45 / 100);
        labelCurrentPasswordLogin.setAlignment(1);
        tableLogin.add(labelCurrentPasswordLogin).fill();
        tableLogin.add(fieldCurrentPasswordLogin).fill().left().width(Value.percentWidth(1f));
        tableLogin.row();
        messageErrorUsername.setAlignment(1);
        tableLogin.add(messageErrorUsername).left().padBottom(SCREEN_HEIGHT * 6 / 100).colspan(2);
        tableLogin.row();
        labelNewPassword1Login.setAlignment(1);
        labelNewPassword1Login.setWidth(SCREEN_HEIGHT * 70 / 100 * 55 / 100);
        fieldNewPassword1Login.setWidth(SCREEN_HEIGHT * 70 / 100 * 45 / 100);
        tableLogin.add(labelNewPassword1Login);
        tableLogin.add(fieldNewPassword1Login).fill().width(Value.percentWidth(1f));
        tableLogin.row();
        messageErrorLoginPassword.setAlignment(1);
        tableLogin.add(messageErrorLoginPassword).left().padBottom(SCREEN_HEIGHT * 8 / 100).colspan(2);
        tableLogin.row();

        buttonLoginCancel.setWidth(SCREEN_HEIGHT * 70 / 100 * 21 / 100);
        buttonLogin.setWidth(buttonLoginCancel.getWidth());

        tableLogin.add(buttonLoginCancel).width(Value.percentWidth(1f)).left().padLeft(SCREEN_HEIGHT * 70 / 100 * 15 / 100);
        tableLogin.add(buttonLogin).width(Value.percentWidth(1f)).right().padRight(SCREEN_HEIGHT * 70 / 100 * 15 / 100);

        content.addActor(tableLogin);
        content.addActor(tableSignUp);

        mainTableLogin.add(content).size(SCREEN_HEIGHT * 70 / 100, SCREEN_HEIGHT * 77 / 100 - buttonLogin.getHeight());

        ChangeListener tabListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tableLogin.setVisible(buttonTabLogin.isChecked());
                tableSignUp.setVisible(buttonTabSignUp.isChecked());
            }
        };
        buttonTabLogin.addListener(tabListener);
        buttonTabSignUp.addListener(tabListener);

        // Let only one tab button be checked at a time
        ButtonGroup tabs = new ButtonGroup();
        tabs.setMinCheckCount(1);
        tabs.setMaxCheckCount(1);
        tabs.add(buttonTabLogin);
        tabs.add(buttonTabSignUp);
    }

    // int player --> 1 si c'est le joueur du bouton gauche, 2 sinon
    public void showProfile(int player) {
        // en supprimant un compte, il faudra recreer un menu avec l'image anonyme
        // donc pas besoin de detruire la fenetre ou de s'occuper de reactiver les boutons quand tu supprimes
        disableButton(buttonPlay, buttonSettings, buttonExit);
        if (isAccountEnabled)
            disableButton(buttonHall, buttonProfileLeft, buttonProfileRight);
        windowProfile.clear();
        windowProfile.setSize(SCREEN_HEIGHT * 70 / 100, SCREEN_HEIGHT * 77 / 100);
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


        Label labelWelcome = new Label(lang.format("label_welcome", playerName), skinSgx, "title-white");

        ImageButton imageAvatar = new ImageButton(imageAnonymous);

        disableButton(imageAvatar);

        Label labelRank = new Label(lang.format("label_rank", playerRank, totalNumberPlayers), skinSgx, "white");

        labelWelcome.setHeight(SCREEN_HEIGHT * 10 / 100);
        table.add(labelWelcome).height(Value.percentHeight(1f));
        table.row();

        imageAvatar.setSize(buttonProfileHeight, buttonProfileHeight);
        table.add(imageAvatar).height(Value.percentHeight(1f)).width(Value.percentWidth(1f));
        table.row();

        labelRank.setHeight(SCREEN_HEIGHT * 10 / 100);
        table.add(labelRank).height(Value.percentHeight(1f));
        table.row();
        table.top();

        buttonEdit = new TextButton(lang.get("text_edit"), skinSgx, "big");
        buttonEdit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                showEdit();
            }
        });

        buttonLogOut = new TextButton(lang.get("text_log_out"), skinSgx, "big");
        buttonLogOut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                showLogOut();
            }
        });
        buttonDelete = new TextButton(lang.get("text_delete_account"), skinSgx, "big");
        buttonDelete.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                showDelete();
            }
        });
        buttonBack = new TextButton(lang.get("button_back"), skinSgx, "big");
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);
                windowProfile.remove();
                enableButton(buttonPlay, buttonSettings, buttonExit);
                if (isAccountEnabled)
                    enableButton(buttonHall, buttonProfileLeft, buttonProfileRight);
            }
        });

        buttonEdit.setWidth(windowProfile.getWidth() * 38 / 100);
        windowProfile.center();
        buttonLogOut.setWidth(windowProfile.getWidth() * 38 / 100);
        buttonDelete.setWidth(windowProfile.getWidth() * 38 / 100);
        buttonBack.setWidth(windowProfile.getWidth() * 38 / 100);

        table.add(buttonEdit).padTop(SCREEN_HEIGHT * 2 / 100).width(Value.percentWidth(1f));
        table.row();
        table.add(buttonLogOut).padTop(windowProfile.getHeight() * 4 / 100).width(Value.percentWidth(1f));
        table.row();
        table.add(buttonDelete).padTop(windowProfile.getHeight() * 4 / 100).width(Value.percentWidth(1f));
        table.row();
        table.add(buttonBack).padTop(windowProfile.getHeight() * 4 / 100).width(Value.percentWidth(1f));

        stage.addActor(windowProfile);
    }

    public void showEdit() {
        disableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
        windowEdit.clear();
        windowEdit.setSize(windowProfile.getWidth(), windowProfile.getHeight());
        windowEdit.setPosition(SCREEN_WIDTH / 2 - windowEdit.getWidth() / 2, SCREEN_HEIGHT / 2 - windowEdit.getHeight() / 2);
        windowEdit.setMovable(false);
        // place le titre de la fenetre au milieu
        windowEdit.getTitleTable().padLeft(windowEdit.getWidth() / 2 - windowEdit.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowEdit.addActor(table);
        table.setFillParent(true);

        table.left().padTop(windowEdit.getHeight() * 6 / 100);
        table.setSize(windowProfile.getWidth(), windowProfile.getWidth());

        buttonEditAvatar.setSize(windowEdit.getHeight() * 14 / 100, windowEdit.getHeight() * 14 / 100);
        buttonEditAvatar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.1f);
                buttonEditAvatar = new ImageButton(imageAnonymous);
                isInEdit = true;
                showAvatarSelectionWindow();
            }
        });
        buttonEditCancel = new TextButton(lang.get("button_cancel"), skinSgx, "big");
        buttonEditCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);
                windowEdit.remove();
                enableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
            }
        });
        buttonEditSave = new TextButton(lang.get("button_save"), skinSgx, "big");
        buttonEditSave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);


                //  a faire

            }
        });

        Label labelCurrentPassword = new Label(lang.get("label_current_password"), skinSgx, "white");
        Label labelNewPassword1 = new Label(lang.get("label_new_password"), skinSgx, "white");
        Label labelNewPassword2 = new Label(lang.get("label_new_password_again"), skinSgx, "white");

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

        Label messageError = new Label(lang.get("label_incorrect_password"), skinSgx, "white");
        messageError.setColor(Color.RED);
        Label avatarLabel = new Label(lang.get("label_click_to_change_avatar"), skinSgx);
        avatarLabel.setAlignment(1);
        table.add(avatarLabel).colspan(2).width(Value.percentWidth(1f)).padBottom(SCREEN_HEIGHT * 2 / 100);
        table.row();
        table.add(buttonEditAvatar).height(Value.percentHeight(1f)).width(Value.percentWidth(1f)).padBottom(SCREEN_HEIGHT * 4 / 100).colspan(2);
        table.row();
        labelCurrentPassword.setWidth(windowEdit.getWidth() * 55 / 100);
        fieldCurrentPassword.setWidth(windowEdit.getWidth() * 45 / 100);
        labelCurrentPassword.setAlignment(1);
        table.add(labelCurrentPassword).fill();
        table.add(fieldCurrentPassword).fill().left().width(Value.percentWidth(1f));
        table.row();
        table.add(messageError).left().padBottom(SCREEN_HEIGHT * 3 / 100).colspan(2);
        table.row();
        labelNewPassword1.setAlignment(1);
        labelNewPassword1.setWidth(windowEdit.getWidth() * 55 / 100);
        fieldNewPassword1.setWidth(windowEdit.getWidth() * 45 / 100);
        table.add(labelNewPassword1);
        table.add(fieldNewPassword1).fill().width(Value.percentWidth(1f));
        table.row();
        table.add(messageError).left().padBottom(SCREEN_HEIGHT * 3 / 100).colspan(2);
        table.row();
        labelNewPassword2.setWidth(windowEdit.getWidth() * 55 / 100);
        fieldNewPassword2.setWidth(windowEdit.getWidth() * 45 / 100);
        labelNewPassword2.setAlignment(1);
        table.add(labelNewPassword2).width(Value.percentWidth(1f)).fill();//.padRight(SCREEN_WIDTH * 2 / 100);
        table.add(fieldNewPassword2).fill();
        table.row();
        messageError.setAlignment(1);
        table.add(messageError).padBottom(SCREEN_HEIGHT * 3 / 100).colspan(2);
        table.row().padTop(windowEdit.getHeight() * 4 / 100);
        buttonEditCancel.setWidth(windowEdit.getWidth() * 21 / 100);
        buttonEditSave.setWidth(buttonEditCancel.getWidth());

        table.add(buttonEditCancel).width(Value.percentWidth(1f)).left().padLeft(windowEdit.getWidth() * 15 / 100);
        table.add(buttonEditSave).width(Value.percentWidth(1f)).right().padRight(windowEdit.getWidth() * 15 / 100);

        windowEdit.add(table);

        stage.addActor(windowEdit);
    }

    public void showAvatarSelectionWindow() {
        if (isInEdit)
            disableButton(buttonEditAvatar, buttonEditSave, buttonEditCancel);
        else if (isInLogin) {
            disableButton(buttonSignUpCancel, buttonSignUp, buttonLoginAvatar);
            buttonTabLogin.setTouchable(Touchable.disabled);
            buttonTabSignUp.setTouchable(Touchable.disabled);
        }

        windowAvatarSelection.clear();
        windowAvatarSelection.setSize(SCREEN_HEIGHT * 70 / 100, SCREEN_HEIGHT * 77 / 100);
        windowAvatarSelection.setPosition(SCREEN_WIDTH / 2 - windowAvatarSelection.getWidth() / 2, SCREEN_HEIGHT / 2 - windowAvatarSelection.getHeight() / 2);
        windowAvatarSelection.setMovable(false);
        // place le titre de la fenetre au milieu
        windowAvatarSelection.getTitleTable().padLeft(windowAvatarSelection.getWidth() / 2 - windowAvatarSelection.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowAvatarSelection.addActor(table);
        table.setFillParent(true);
        table.top().padTop(windowAvatarSelection.getHeight() * 13 / 100);

        TextButton buttonImport = new TextButton(lang.get("button_import"), skinSgx, "big");
        buttonImport.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.1f);

                // chercher une image sur le pc et remplacer l'image de l'avatar actuel par cette image
            }
        });
        TextButton buttonSave = new TextButton(lang.get("button_save"), skinSgx, "big");
        buttonSave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);

                // enregistrer le nouvel avatar (sauvegarder l'image dans le dossier des avatars si
                // elle est importee + modifier le chemin de l'image dans le fichier ou la classe)

                windowAvatarSelection.remove();

                if (isInEdit) {
                    enableButton(buttonEditAvatar, buttonEditSave, buttonEditCancel);
                    isInEdit = false;
                    showEdit();
                } else if (isInLogin) {
                    enableButton(buttonSignUpCancel, buttonSignUp, buttonLoginAvatar);
                    buttonTabLogin.setTouchable(Touchable.enabled);
                    buttonTabSignUp.setTouchable(Touchable.enabled);
                    isInLogin = false;
                }
            }
        });
        TextButton buttonCancel = new TextButton(lang.get("button_cancel"), skinSgx, "big");
        buttonCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);
                windowAvatarSelection.remove();

                if (isInEdit) {
                    enableButton(buttonEditAvatar, buttonEditSave, buttonEditCancel);
                    isInEdit = false;
                } else if (isInLogin) {
                    enableButton(buttonSignUpCancel, buttonSignUp, buttonLoginAvatar);
                    buttonTabLogin.setTouchable(Touchable.enabled);
                    buttonTabSignUp.setTouchable(Touchable.enabled);
                    isInLogin = false;
                }
            }
        });
        pandaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonEditAvatar = new ImageButton(imagePanda);
                showAvatarSelectionWindow();
            }
        });
        yetiButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonEditAvatar = new ImageButton(imageYeti);
                showAvatarSelectionWindow();
            }
        });
        wormButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonEditAvatar = new ImageButton(imageWorm);
                showAvatarSelectionWindow();
            }
        });
        mustacheButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonEditAvatar = new ImageButton(imageMustache);
                showAvatarSelectionWindow();
            }
        });
        bunnyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonEditAvatar = new ImageButton(imageBunny);
                showAvatarSelectionWindow();
            }
        });
        girlButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonEditAvatar = new ImageButton(imageGirl);
                showAvatarSelectionWindow();
            }
        });
        robotButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonEditAvatar = new ImageButton(imageRobot);
                showAvatarSelectionWindow();
            }
        });
        penguinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonEditAvatar = new ImageButton(imagePenguin);
                showAvatarSelectionWindow();
            }
        });
        birdButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonEditAvatar = new ImageButton(imageBird);
                showAvatarSelectionWindow();
            }
        });
        squidButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(0.2f);
                buttonEditAvatar = new ImageButton(imageSquid);
                showAvatarSelectionWindow();
            }
        });

        float widthImage = windowAvatarSelection.getWidth() * 2 / 15;
        float heightImage = windowAvatarSelection.getWidth() * 2 / 10;

        table.add(pandaButton).width(widthImage).height(heightImage).padRight(heightImage / 5);
        table.add(yetiButton).width(widthImage).height(heightImage);
        table.add(wormButton).width(widthImage).height(heightImage);
        table.add(mustacheButton).width(widthImage).height(heightImage).padRight(heightImage / 5);
        table.add(bunnyButton).width(widthImage).height(heightImage);
        table.row();
        table.add(girlButton).width(widthImage).height(heightImage).padRight(heightImage / 5);
        table.add(robotButton).width(widthImage).height(heightImage);
        table.add(penguinButton).width(widthImage).height(heightImage);
        table.add(birdButton).width(widthImage).height(heightImage).padRight(heightImage / 5);
        table.add(squidButton).width(widthImage).height(heightImage);
        table.row().padTop(windowAvatarSelection.getHeight() * 7 / 100);
        buttonImport.setWidth(windowAvatarSelection.getWidth() * 23 / 100);
        table.add(buttonImport).colspan(2).width(Value.percentWidth(1f)).left();
        table.add(buttonEditAvatar).width(heightImage).height(heightImage);
        buttonSave.setWidth(buttonImport.getWidth());
        table.add(buttonSave).colspan(2).width(Value.percentWidth(1f)).right();
        table.row().padTop(windowAvatarSelection.getHeight() * 8 / 100);
        buttonCancel.setWidth(buttonImport.getWidth());
        table.add(buttonCancel).colspan(5).width(Value.percentWidth(1f));

        stage.addActor(windowAvatarSelection);
    }

    private void showLogOut() {
        disableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
        windowLogOut.clear();
        windowLogOut.setSize(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4);
        windowLogOut.setPosition(SCREEN_WIDTH / 2 - windowLogOut.getWidth() / 2, SCREEN_HEIGHT / 2 - windowLogOut.getHeight() / 2);
        windowLogOut.setMovable(false);
        // place le titre de la fenetre au milieu
        windowLogOut.getTitleTable().padLeft(windowLogOut.getWidth() / 2 - windowLogOut.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowLogOut.addActor(table);
        table.setFillParent(true);
        table.padTop(windowLogOut.getHeight() * 17 / 100);

        Label labelLogOutConfirm = new Label(lang.get("label_are_you_sure"), skinSgx, "white");

        TextButton buttonYes = new TextButton(lang.get("text_yes"), skinSgx, "big");
        buttonYes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.2f);

                // remettre l'image anonyme dans le bouton du menu qui est concerne

                stage.clear();
                new Menu(game);
            }
        });

        TextButton buttonNo = new TextButton(lang.get("text_no"), skinSgx, "big");
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
                dispose();
                Gdx.app.exit();
            }
        });

        TextButton buttonNo = new TextButton(lang.get("text_no"), skinSgx, "big");
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
        windowDelete.clear();
        windowDelete.setSize(windowProfile.getWidth(), SCREEN_HEIGHT * 2 / 5);
        windowDelete.setPosition(SCREEN_WIDTH / 2 - windowDelete.getWidth() / 2, SCREEN_HEIGHT / 2 - windowDelete.getHeight() / 2);
        windowDelete.setMovable(false);
        // place le titre de la fenetre au milieu
        windowDelete.getTitleTable().padLeft(windowDelete.getWidth() / 2 - windowDelete.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowDelete.addActor(table);
        table.setFillParent(true);

        Label labelWarning1 = new Label(lang.get("label_all_data_will_be_erased"), skinSgx, "white");
        Label labelWarning2 = new Label(lang.get("label_including_achievements_in_hof"), skinSgx, "white");
        Label labelPassword = new Label(lang.get("label_password"), skinSgx, "white");
        Label incorrectPassword = new Label(lang.get("label_incorrect_password"), skinSgx, "white");
        incorrectPassword.setColor(Color.RED);
        TextField fieldPassword = new TextField("", skinSgx);
        fieldPassword.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });

        TextButton buttonConfirm = new TextButton(lang.get("button_confirm"), skinSgx, "big");
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
        TextButton buttonCancel = new TextButton(lang.get("button_cancel"), skinSgx, "big");
        buttonCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(0.1f);
                windowDelete.remove();
                enableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
            }
        });

        table.padTop(windowDelete.getWidth() / 20);
        table.add(labelWarning1).colspan(2);
        table.row();
        table.add(labelWarning2).colspan(2);
        table.row().padTop(windowDelete.getHeight() * 11 / 100);
        table.add(labelPassword);
        labelPassword.setAlignment(1);
        table.add(fieldPassword).fill().width(Value.percentWidth(1.5f));
        table.row();
        table.add(incorrectPassword).colspan(2).fill();
        table.row().padTop(windowDelete.getHeight() * 6 / 100);
        table.add(buttonConfirm).padRight(SCREEN_WIDTH * 2 / 100).fill().width(windowDelete.getWidth() / 4);
        table.add(buttonCancel).padLeft(SCREEN_WIDTH * 2 / 100).fill().width(windowDelete.getWidth() / 4);

        stage.addActor(windowDelete);
    }
}
