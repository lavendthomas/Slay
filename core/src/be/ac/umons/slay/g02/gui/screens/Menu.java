package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import be.ac.umons.slay.g02.players.Account;
import be.ac.umons.slay.g02.players.Colors;
import be.ac.umons.slay.g02.players.FileBuilder;
import be.ac.umons.slay.g02.players.GlobalStats;
import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.LevelStats;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.camera;
import static be.ac.umons.slay.g02.gui.Main.cursor;
import static be.ac.umons.slay.g02.gui.Main.isAccountEnabled;
import static be.ac.umons.slay.g02.gui.Main.lang;
import static be.ac.umons.slay.g02.gui.Main.pm;
import static be.ac.umons.slay.g02.gui.Main.prefs;
import static be.ac.umons.slay.g02.gui.Main.skinSgx;
import static be.ac.umons.slay.g02.gui.Main.skinSgxTable;
import static be.ac.umons.slay.g02.gui.Main.soundButton1;
import static be.ac.umons.slay.g02.gui.Main.soundButton2;
import static be.ac.umons.slay.g02.gui.Main.soundButton3;
import static be.ac.umons.slay.g02.gui.Main.stage;
import static be.ac.umons.slay.g02.gui.Main.tabPlayers;

/**
 * classe qui affiche le menu principal
 */
public class Menu implements Screen {
    public final static String INCORRECT_PASSWORD = "Incorrect password";
    public final static String USER_NAME_NOT_EXIST = "Username does not exist";
    public final static String INCORRECT_LENGTH_PASSWORD = "Passwords must be at least 4 characters long";
    public final static String INCORRECT_LENGTH_USER_NAME = "Usernames must be between 4 and 20 char.";
    public final static String USER_NAME_NOT_AVAILABLE = "Username not available";
    public final static String PASSWORDS_NOT_MATCH = "Passwords do not match";
    public final static String NO_AVATAR = "Choose an avatar";
    public static String pathImageRobot = "profile/robot.jpg";
    public static String pathImageGirl = "profile/girl.jpg";
    public static String pathImagePanda = "profile/panda.jpg";
    public static String pathImagePink = "profile/pink.jpg";
    public static String pathImageSquid = "profile/squid.jpg";
    public static String pathImageBanana = "profile/banana.jpg";
    public static String pathImageBurger = "profile/burger.jpg";
    public static String pathImageBlue = "profile/blue.jpg";
    public static String pathImageMustache = "profile/mustache.jpg";
    public static String pathImagePenguin = "profile/penguin.jpg";
    public static String pathImageProfile = "profile/anonymous.png";
    public static String pathImageTmp = "profile/anonymous.png";
    public static Drawable imageRobot = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathImageRobot))));
    public static Drawable imagePanda = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathImagePanda))));
    public static Drawable imageGirl = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathImageGirl))));
    public static Drawable imagePink = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathImagePink))));
    public static Drawable imageSquid = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathImageSquid))));
    public static Drawable imageBanana = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathImageBanana))));
    public static Drawable imageBurger = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathImageBurger))));
    public static Drawable imageBlue = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathImageBlue))));
    public static Drawable imageMustache = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathImageMustache))));
    public static Drawable imagePenguin = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathImagePenguin))));
    public static Drawable imageAnonymous = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("profile/anonymous.png"))));
    public static Drawable backgroundGrey = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backgrounds/background-gray.png"))));
    public static Drawable imageProfile = imageAnonymous;
    public static Drawable imagePlayer1 = imageAnonymous;
    public static Drawable imagePlayer2 = imageAnonymous;
    public static Drawable imageTmp = imageAnonymous;
    private static ImageButton buttonEditAvatar = new ImageButton(imageProfile);
    private static ImageButton buttonSelectionAvatar = new ImageButton(imageProfile);
    private static ImageButton buttonLoginAvatar = new ImageButton(imageAnonymous);
    private static ImageButton pandaButton = new ImageButton(imagePanda);
    private static ImageButton pinkButton = new ImageButton(imagePink);
    private static ImageButton bananaButton = new ImageButton(imageBanana);
    private static ImageButton mustacheButton = new ImageButton(imageMustache);
    private static ImageButton burgerButton = new ImageButton(imageBurger);
    private static ImageButton girlButton = new ImageButton(imageGirl);
    private static ImageButton robotButton = new ImageButton(imageRobot);
    private static ImageButton penguinButton = new ImageButton(imagePenguin);
    private static ImageButton blueButton = new ImageButton(imageBlue);
    private static ImageButton squidButton = new ImageButton(imageSquid);
    // pour les tests
    public static boolean isPlayer1Logged = false;
    public static boolean isPlayer2Logged = false;
    private boolean hasImportedAvatar;
    private static int playerRank = 0;
    public static int totalNumberPlayers = 0;
    private static int numPlayer = 0;
    public static HumanPlayer player1 = new HumanPlayer("P1", Colors.C1);
    public static HumanPlayer player2 = new HumanPlayer("P2", Colors.C1);
    private static Window windowExit = new Window(lang.get("text_exit"), skinSgx);
    private static Window windowSettings = new Window(lang.get("text_settings"), skinSgx);
    private static Window windowAlert = new Window(lang.get("text_disable_registration"), skinSgx);
    private static Window windowProfile = new Window(lang.get("text_profile"), skinSgx);
    private static Window windowEdit = new Window(lang.get("text_edit"), skinSgx);
    private static Window windowAvatarSelection = new Window(lang.get("text_choose_an_avatar"), skinSgx);
    private static Window windowLogOut = new Window(lang.get("text_log_out"), skinSgx);
    private static Window windowDelete = new Window(lang.get("text_delete_account"), skinSgx);
    private static Table mainTableLogin = new Table();
    private static Table tableEdit;
    private static Cell<ImageButton> cellAvatarSelect;
    private static Cell<ImageButton> cellEdit;
    private static Cell<ImageButton> cellSignUp;
    private static Cell<ImageButton> cellProfileLeft;
    private static Cell<ImageButton> cellProfileRight;
    private static Cell<ImageButton> cellAvatarProfile;
    private static Cell<Label> cellLabelProfileLeft;
    private static Cell<Label> cellLabelProfileRight;
    private static Cell<Label> cellLabelEditMessage1;
    private static Cell<Label> cellLabelEditMessage2;
    private static Cell<Label> cellLabelEditMessage3;
    private static Cell<Label> cellErrorUsernameLogin;
    private static Cell<Label> cellErrorPasswordLogin;
    private static Cell<Label> cellErrorUsernameSignUp;
    private static Cell<Label> cellErrorPasswordSignUp;
    private static Cell<Label> cellErrorPassword2SignUp;
    private static Cell<Label> cellErrorAvatarSignUp;
    private boolean hasChangedAvatar = false;
    Label noMessageError0 = new Label("", skinSgx);
    Label noMessageError1 = new Label("", skinSgx);
    Label noMessageError2 = new Label("", skinSgx);
    Label noMessageError3 = new Label("", skinSgx);
    Label noMessageError4 = new Label("", skinSgx);
    Label noMessageError5 = new Label("", skinSgx);
    Label noMessageError6 = new Label("", skinSgx);
    Label noMessageError7 = new Label("", skinSgx);
    Label noMessageError8 = new Label("", skinSgx);
    Label messageErrorLoginPassword = new Label("*" + INCORRECT_PASSWORD, skinSgx, "white");
    Label messageErrorUsername = new Label("*" + USER_NAME_NOT_EXIST, skinSgx, "white");
    Label messageErrorLengthPassword = new Label("*" + INCORRECT_LENGTH_PASSWORD, skinSgx, "white");
    Label messageErrorLengthUsername = new Label("*" + INCORRECT_LENGTH_USER_NAME, skinSgx, "white");
    Label messageUsernameNotAvailable = new Label("*" + USER_NAME_NOT_AVAILABLE, skinSgx, "white");
    Label messageErrorPasswordNotMatch = new Label("*" + PASSWORDS_NOT_MATCH, skinSgx, "white");
    Label messageErrorNoAvatar = new Label("*" + NO_AVATAR, skinSgx, "white");
    private static TextField fieldCurrentPassword = new TextField("", skinSgx);
    private static TextField fieldNewPassword1 = new TextField("", skinSgx);
    private static TextField fieldNewPassword2 = new TextField("", skinSgx);
    private static TextField fieldNameSignUp = new TextField("", skinSgx);
    private static TextField fieldPasswordLogin = new TextField("", skinSgx);
    private static TextField fieldNameLogin = new TextField("", skinSgx);
    private static TextField fieldPassword1SignUp = new TextField("", skinSgx);
    private static TextField fieldPassword2SignUp = new TextField("", skinSgx);
    private String messageErrorLogin = "";
    private String messageErrorSignUp = "";
    private String messageErrorEdit = "";
    private int buttonCenterWidth;
    // pour savoir dans quel bouton le joueur s'identifie
    private boolean isProfileLeft = false;
    private boolean isProfileRight = false;
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
    private boolean isInLogIn = false;
    private boolean isInSignUp = false;
    private boolean isInEdit = false;
    private FileHandle sourceImage;
    private File selectedFile;


    public Menu(Game aGame) {
        game = aGame;

        init();

    }

    public void init() {

        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        stage.clear();
        /*
        buttonCenterWidth = SCREEN_WIDTH * 28 / 100;
        int buttonCenterHeight = SCREEN_HEIGHT * 7 / 100;
        int buttonCenterGap = SCREEN_HEIGHT * 7 / 100;
        int labelProfileWidth = buttonCenterWidth * 35 / 100;
        int tableCenterPositionX = SCREEN_WIDTH / 2;
        int tableCenterPositionY = SCREEN_HEIGHT / 3;
        buttonProfileHeight = SCREEN_HEIGHT * 10 / 100;
        windowSettingsWidth = buttonCenterWidth * 3 / 4;
        */


        buttonCenterWidth = VIRTUAL_WIDTH * 28 / 100;
        int buttonCenterHeight = VIRTUAL_HEIGHT * 7 / 100;
        int buttonCenterGap = SCREEN_HEIGHT * 7 / 100;
        int labelProfileWidth = buttonCenterWidth * 35 / 100;
        int tableCenterPositionX = VIRTUAL_WIDTH / 2;
        int tableCenterPositionY = VIRTUAL_HEIGHT / 3;
        buttonProfileHeight = VIRTUAL_HEIGHT * 10 / 100;
        windowSettingsWidth = Math.min(SCREEN_WIDTH, 700);

        // background
        batch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("backgrounds/background-title.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setOrigin(0, 0);
        sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);


        Table tableCenter = new Table();
        tableCenter.setPosition(tableCenterPositionX, tableCenterPositionY);
        stage.addActor(tableCenter);

        buttonPlay = new TextButton(lang.get("button_play"), skinSgx, "big");
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                stage.clear();
                game.setScreen(new LevelSelection(game));
            }
        });
        buttonSettings = new TextButton(lang.get("button_settings"), skinSgx, "big");
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                showSettings();
            }
        });
        buttonExit = new TextButton(lang.get("button_exit"), skinSgx, "big");
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
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
                    soundButton1.play(prefs.getFloat("volume", 0.2f));
                    stage.clear();
                    game.setScreen(new Hall(game));
                }
            });

            Label labelPlayer1 = new Label("Player 1", skinSgx, "title");
            Label labelPlayer2 = new Label("Player 2", skinSgx, "title");

            Label labelProfileLeft = new Label("Not Logged", skinSgx, "medium");
            Label labelProfileRight = new Label("Not Logged", skinSgx, "medium");
            labelPlayer1.setAlignment(1);
            labelPlayer2.setAlignment(1);

            labelProfileLeft.setAlignment(1);
            labelProfileRight.setAlignment(1);

            if (isPlayer1Logged) {
                imageProfile = imagePlayer1;
                labelProfileLeft = new Label("Logged", skinSgx, "medium");
                labelProfileLeft.setAlignment(1);
            } else {
                imageProfile = imageAnonymous;
            }
            createButtonProfileLeft();

            if (isPlayer2Logged) {
                imageProfile = imagePlayer2;
                labelProfileRight = new Label("Logged", skinSgx, "medium");
                labelProfileRight.setAlignment(1);
            } else {
                imageProfile = imageAnonymous;
            }
            createButtonProfileRight();

            tableCenter.add(buttonPlay).width(buttonCenterWidth).height(buttonCenterHeight);
            tableCenter.row().pad(buttonCenterGap, 0, buttonCenterGap, 0);
            tableCenter.add(buttonHall).width(buttonCenterWidth).height(buttonCenterHeight);
            tableCenter.row().pad(0, 0, buttonCenterGap, 0);
            tableCenter.add(buttonSettings).width(buttonCenterWidth).height(buttonCenterHeight);
            tableCenter.row();
            tableCenter.add(buttonExit).width(buttonCenterWidth).height(buttonCenterHeight);
            tableProfile.add(labelPlayer1).width(labelProfileWidth);
            tableProfile.add(labelPlayer2).width(labelProfileWidth);
            tableProfile.row().pad(10, 0, 0, 0);
            tableProfile.add(labelProfileLeft).width(labelProfileWidth).height(buttonProfileHeight / 5);
            tableProfile.add(labelProfileRight).width(labelProfileWidth).height(buttonProfileHeight / 5);
            tableProfile.row().pad(10, 0, 0, 0);
            cellLabelProfileLeft = tableProfile.getCell(labelProfileLeft);
            cellLabelProfileRight = tableProfile.getCell(labelProfileRight);
            tableProfile.add(buttonProfileLeft).width(buttonProfileHeight).height(buttonProfileHeight);
            tableProfile.add(buttonProfileRight).width(buttonProfileHeight).height(buttonProfileHeight);
            tableProfile.row();
            cellProfileLeft = tableProfile.getCell(buttonProfileLeft);
            cellProfileRight = tableProfile.getCell(buttonProfileRight);
            Label labelProfile = new Label("", skinSgx);
            tableProfile.add(labelProfile);
            tableProfile.add(labelProfile);

            stage.addActor(tableProfile);
        } else {
            tableCenter.add(buttonPlay).width(buttonCenterWidth).height(buttonCenterHeight);
            tableCenter.row().pad(buttonCenterGap, 0, buttonCenterGap, 0);
            tableCenter.add(buttonSettings).width(buttonCenterWidth).height(buttonCenterHeight);
            tableCenter.row();
            tableCenter.add(buttonExit).width(buttonCenterWidth).height(buttonCenterHeight);
        }
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

    public static void disableField(TextField... field) {
        for (TextField f : field) {
            f.setDisabled(true);
        }
    }

    public static void enableField(TextField... field) {
        for (TextField f : field) {
            f.setDisabled(false);
        }
    }

    public static void resetField(TextField... field) {
        for (TextField f : field) {
            f.setText("");
        }
    }

    public static void setPasswordMode(TextField... field) {
        for (TextField f : field) {
            f.setPasswordCharacter('*');
            f.setPasswordMode(true);
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

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("", width + " " + height);
        init();
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

    private void showSettings() {
        disableButton(buttonPlay, buttonSettings, buttonExit);
        if (isAccountEnabled)
            disableButton(buttonHall, buttonProfileLeft, buttonProfileRight);
        windowSettings.clear();
        windowSettings.setSize(windowSettingsWidth, windowSettingsWidth);
        Gdx.app.log("", SCREEN_WIDTH / 2 - windowSettings.getWidth() / 2 + " screen width: " + SCREEN_WIDTH + " settw: " + windowSettingsWidth);
        windowSettings.setPosition(SCREEN_WIDTH / 2 - windowSettings.getWidth() / 2, SCREEN_HEIGHT / 2 /*- windowSettings.getWidth() / 2*/);
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
                prefs.putFloat("volume", Math.min(1f, Math.max(0f, x / sliderVolume.getWidth())));
                prefs.flush();

                soundButton1.setVolume(1, prefs.getFloat("volume", 0.2f));
                soundButton2.setVolume(2, prefs.getFloat("volume", 0.2f));
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
                    soundButton2.play(prefs.getFloat("volume", 0.2f));
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                } else {
                    soundButton2.play(prefs.getFloat("volume", 0.2f));
                    Gdx.graphics.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);
                    prefs.putBoolean("isFullScreenEnabled", false);
                    prefs.flush();
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
                    soundButton2.play(prefs.getFloat("volume", 0.2f));
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
                soundButton2.play(prefs.getFloat("volume", 0.2f));
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

    private void showAlertSettings() {
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
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                enableButton(buttonSettingsBack, switchRegistration, switchFullscreen);
                windowAlert.remove();
            }
        });
        TextButton buttonCancel = new TextButton(lang.get("button_cancel"), skinSgx, "big");
        buttonCancel.setWidth(buttonCenterWidth / 2);

        buttonCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
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

    private void showLoginWindow() {
        disableButton(buttonPlay, buttonSettings, buttonExit, buttonHall, buttonProfileLeft, buttonProfileRight);
        mainTableLogin.clear();
        stage.addActor(mainTableLogin);
        mainTableLogin.setFillParent(true);

        imageProfile = imageAnonymous;

        buttonTabLogin = new TextButton(lang.get("text_log_in"), skinSgx, "number");
        buttonTabLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                buttonTabLogin.setDisabled(true);
                buttonTabSignUp.setDisabled(false);
                resetField(fieldNameSignUp, fieldPassword1SignUp, fieldPassword2SignUp);
                disableField(fieldNameSignUp, fieldPassword1SignUp, fieldPassword2SignUp);
                enableField(fieldNameLogin, fieldPasswordLogin);
                stage.setKeyboardFocus(fieldNameLogin);
                imageProfile = imageAnonymous;
                hasChangedAvatar = false;
                cellErrorAvatarSignUp.clearActor();
                cellErrorUsernameSignUp.clearActor();
                cellErrorPasswordSignUp.clearActor();
                cellErrorPassword2SignUp.clearActor();
                cellErrorAvatarSignUp.setActor(noMessageError0);
                cellErrorUsernameSignUp.setActor(noMessageError1);
                cellErrorPasswordSignUp.setActor(noMessageError2);
                cellErrorPassword2SignUp.setActor(noMessageError3);

            }
        });
        buttonTabSignUp = new TextButton(lang.get("text_sign_up"), skinSgx, "number");
        buttonTabSignUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                buttonTabLogin.setDisabled(false);
                buttonTabSignUp.setDisabled(true);
                resetField(fieldNameLogin, fieldPasswordLogin);
                disableField(fieldNameLogin, fieldPasswordLogin);
                enableField(fieldNameSignUp, fieldPassword1SignUp, fieldPassword2SignUp);
                stage.setKeyboardFocus(fieldNameSignUp);
                cellSignUp.clearActor();
                cellErrorUsernameLogin.clearActor();
                cellErrorPasswordLogin.clearActor();
                cellErrorUsernameLogin.setActor(noMessageError4);
                cellErrorPasswordLogin.setActor(noMessageError5);
                buttonLoginAvatar = new ImageButton(imageAnonymous);
                buttonLoginAvatar.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        isInSignUp = true;
                        showAvatarSelectionWindow();
                    }
                });
                cellSignUp.setActor(buttonLoginAvatar);
            }
        });

        // pour distinguer si on est dans LogIn ou SignUp
        if (!isInSignUp) {
            stage.setKeyboardFocus(fieldNameLogin);
            buttonTabLogin.setDisabled(true);
            disableField(fieldNameSignUp, fieldPassword1SignUp, fieldPassword2SignUp);
            enableField(fieldNameLogin, fieldPasswordLogin);
            stage.setKeyboardFocus(fieldNameLogin);
        } else {
            stage.setKeyboardFocus(fieldNameSignUp);
            buttonTabSignUp.setDisabled(true);
            buttonTabSignUp.setChecked(true);
            isInSignUp = false;
            disableField(fieldNameLogin, fieldPasswordLogin);
            enableField(fieldNameSignUp, fieldPassword1SignUp, fieldPassword2SignUp);
            stage.setKeyboardFocus(fieldNameSignUp);
        }

        Table tableButtons = new Table();
        tableButtons.add(buttonTabLogin).width((SCREEN_HEIGHT * 70 / 100) / 2);
        tableButtons.add(buttonTabSignUp).width((SCREEN_HEIGHT * 70 / 100) / 2);

        buttonTabSignUp.setTouchable(Touchable.enabled);
        mainTableLogin.add(tableButtons);
        mainTableLogin.row();

        Stack content = new Stack();

        buttonLoginAvatar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isInSignUp = true;
                showAvatarSelectionWindow();
            }
        });
        Label labelUserNameLogin = new Label(lang.get("label_username"), skinSgx, "white");
        Label labelUserName = new Label(lang.get("label_username"), skinSgx, "white");
        Label labelNewPassword1 = new Label(lang.get("label_password"), skinSgx, "white");
        Label labelNewPassword2 = new Label(lang.get("label_password_again"), skinSgx, "white");

        resetField(fieldNameSignUp);
        fieldNameSignUp.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        resetField(fieldPassword1SignUp);
        fieldPassword1SignUp.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        resetField(fieldPassword2SignUp);
        fieldPassword2SignUp.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        Label labelCurrentPasswordLogin = new Label(lang.get("label_password"), skinSgx, "white");

        resetField(fieldNameLogin);
        fieldNameLogin.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        resetField(fieldPasswordLogin);
        fieldPasswordLogin.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        setPasswordMode(fieldPassword1SignUp, fieldPassword2SignUp, fieldPasswordLogin);

        Label noMessage = new Label("", skinSgx);
        messageErrorLoginPassword.setColor(Color.RED);
        messageErrorUsername.setColor(Color.RED);
        messageErrorLengthPassword.setColor(Color.RED);
        messageErrorLengthUsername.setColor(Color.RED);
        messageUsernameNotAvailable.setColor(Color.RED);
        messageErrorPasswordNotMatch.setColor(Color.RED);
        messageErrorNoAvatar.setColor(Color.RED);
        buttonLogin = new TextButton(lang.get("text_log_in"), skinSgx, "big");
        buttonLogin.setWidth(buttonCenterWidth / 2);
        buttonLogin.setScale(1.2f);
        buttonLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));

                messageErrorLogin = getPlayer(fieldNameLogin.getText(), fieldPasswordLogin.getText());


                if (!(messageErrorLogin.equals(""))) {
                    cellErrorUsernameLogin.clearActor();
                    cellErrorPasswordLogin.clearActor();

                    if (messageErrorLogin.equals(USER_NAME_NOT_EXIST)) {
                        cellErrorUsernameLogin.setActor(messageErrorUsername);
                        cellErrorPasswordLogin.setActor(noMessageError7);

                    } else if (messageErrorLogin.equals(INCORRECT_PASSWORD)) {
                        cellErrorUsernameLogin.setActor(noMessageError8);
                        cellErrorPasswordLogin.setActor(messageErrorLoginPassword);

                    }
                } else {
                    mainTableLogin.remove();
                    if (isProfileLeft)
                        isPlayer1Logged = true;
                    else
                        isPlayer2Logged = true;
                    showProfile(numPlayer);
                }
            }
        });
        buttonSignUp = new TextButton(lang.get("text_sign_up"), skinSgx, "big");
        buttonSignUp.setWidth(buttonCenterWidth / 2);
        buttonSignUp.setScale(1.2f);
        buttonSignUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));

                if (!hasChangedAvatar) {
                    messageErrorSignUp = NO_AVATAR;
                } else {
                    messageErrorSignUp = validatePlayer(fieldNameSignUp.getText(), fieldPassword1SignUp.getText(), fieldPassword2SignUp.getText());
                }
                if (!(messageErrorSignUp.equals(""))) {
                    isInSignUp = true;
                    cellErrorAvatarSignUp.clearActor();
                    cellErrorUsernameSignUp.clearActor();
                    cellErrorPasswordSignUp.clearActor();
                    cellErrorPassword2SignUp.clearActor();
                    cellErrorAvatarSignUp.setActor(noMessageError0);
                    cellErrorUsernameSignUp.setActor(noMessageError1);
                    cellErrorPasswordSignUp.setActor(noMessageError2);
                    cellErrorPassword2SignUp.setActor(noMessageError3);

                    if (messageErrorSignUp.equals(NO_AVATAR)) {
                        cellErrorAvatarSignUp.setActor(messageErrorNoAvatar);
                    } else if (messageErrorSignUp.equals(USER_NAME_NOT_AVAILABLE)) {
                        cellErrorUsernameSignUp.setActor(messageUsernameNotAvailable);
                    } else if (messageErrorSignUp.equals(INCORRECT_LENGTH_USER_NAME)) {
                        cellErrorUsernameSignUp.setActor(messageErrorLengthUsername);
                    } else if (messageErrorSignUp.equals(INCORRECT_LENGTH_PASSWORD)) {
                        cellErrorPasswordSignUp.setActor(messageErrorLengthPassword);
                    } else if (messageErrorSignUp.equals(PASSWORDS_NOT_MATCH)) {
                        cellErrorPassword2SignUp.setActor(messageErrorPasswordNotMatch);
                    }

                } else {
                    mainTableLogin.remove();
                    initializeStatsPlayer(fieldNameSignUp.getText(), fieldPassword1SignUp.getText(), pathImageProfile);
                    if (isProfileLeft) {
                        isPlayer1Logged = true;
                        numPlayer = 1;
                        player1.getGlobalStats().setScore(10000);
                        changeAvatar(player1);
                    } else {
                        isPlayer2Logged = true;
                        player2.getGlobalStats().setScore(25000);
                        numPlayer = 2;
                        changeAvatar(player2);
                    }
                    showProfile(numPlayer);
                }
            }
        });
        TextButton buttonLoginCancel = new TextButton(lang.get("text_cancel"), skinSgx, "big");
        buttonLoginCancel.setWidth(buttonCenterWidth / 2);
        buttonLoginCancel.setScale(1.2f);
        buttonLoginCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                mainTableLogin.remove();
                enableButton(buttonPlay, buttonSettings, buttonExit, buttonHall, buttonProfileLeft, buttonProfileRight);
            }
        });
        buttonSignUpCancel = new TextButton(lang.get("text_cancel"), skinSgx, "big");
        buttonSignUpCancel.setWidth(buttonCenterWidth / 2);
        buttonSignUpCancel.setScale(1.2f);
        buttonSignUpCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                mainTableLogin.remove();
                enableButton(buttonPlay, buttonSettings, buttonExit, buttonHall, buttonProfileLeft, buttonProfileRight);
                cellSignUp.clearActor();
                buttonLoginAvatar = new ImageButton(imageAnonymous);
                cellSignUp.setActor(buttonLoginAvatar);
                buttonLoginAvatar.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        isInSignUp = true;
                        showAvatarSelectionWindow();
                    }
                });
                hasChangedAvatar = false;

            }
        });

        // -----------------------------------------------------------------------------------------------------
        // Sign Up

        final Table tableSignUp = new Table();
        tableSignUp.setBackground(backgroundGrey);
        tableSignUp.padTop(SCREEN_WIDTH * 1 / 100);
        Label avatarLabel = new Label(lang.get("label_click_to_change_avatar"), skinSgx);
        avatarLabel.setAlignment(1);
        tableSignUp.add(avatarLabel).colspan(2).padBottom(SCREEN_HEIGHT * 2 / 100);
        tableSignUp.row();

        tableSignUp.add(buttonLoginAvatar).width(SCREEN_WIDTH * 7 / 100).height(SCREEN_WIDTH * 7 / 100).padBottom(SCREEN_HEIGHT * 1 / 100).colspan(2);
        tableSignUp.row();

        cellSignUp = tableSignUp.getCell(buttonLoginAvatar);
        messageErrorNoAvatar.setAlignment(1);
        messageErrorLengthUsername.setAlignment(1);
        messageErrorPasswordNotMatch.setAlignment(1);
        messageErrorLengthPassword.setAlignment(1);
        tableSignUp.add(noMessageError0).center().padBottom(SCREEN_HEIGHT * 20 / 1000).colspan(2);
        tableSignUp.row();
        cellErrorAvatarSignUp = tableSignUp.getCell(noMessageError0);
        labelUserName.setWidth(SCREEN_HEIGHT * 70 / 100 * 55 / 100);
        fieldNameSignUp.setWidth(SCREEN_HEIGHT * 70 / 100 * 45 / 100);
        labelUserName.setAlignment(1);
        tableSignUp.add(labelUserName).fill();
        tableSignUp.add(fieldNameSignUp).fill().left().width(Value.percentWidth(1f));
        tableSignUp.row();


        tableSignUp.add(noMessageError1).center().padBottom(SCREEN_HEIGHT * 3 / 100).colspan(2);
        tableSignUp.row();
        cellErrorUsernameSignUp = tableSignUp.getCell(noMessageError1);
        labelNewPassword1.setAlignment(1);
        labelNewPassword1.setWidth(SCREEN_HEIGHT * 70 / 100 * 55 / 100);
        fieldPassword1SignUp.setWidth(SCREEN_HEIGHT * 70 / 100 * 45 / 100);
        tableSignUp.add(labelNewPassword1);
        tableSignUp.add(fieldPassword1SignUp).fill().width(Value.percentWidth(1f));
        tableSignUp.row();

        tableSignUp.add(noMessageError2).center().padBottom(SCREEN_HEIGHT * 3 / 100).colspan(2);
        tableSignUp.row();
        cellErrorPasswordSignUp = tableSignUp.getCell(noMessageError2);
        labelNewPassword2.setWidth(SCREEN_HEIGHT * 70 / 100 * 55 / 100);
        fieldPassword2SignUp.setWidth(SCREEN_HEIGHT * 70 / 100 * 45 / 100);
        labelNewPassword2.setAlignment(1);
        tableSignUp.add(labelNewPassword2).width(Value.percentWidth(1f)).fill();//.padRight(SCREEN_WIDTH * 2 / 100);
        tableSignUp.add(fieldPassword2SignUp).fill();
        tableSignUp.row();

        tableSignUp.add(noMessageError3).center().colspan(2);
        tableSignUp.row().padTop(SCREEN_HEIGHT * 70 / 100 * 4 / 100);
        cellErrorPassword2SignUp = tableSignUp.getCell(noMessageError3);
        buttonSignUpCancel.setWidth(SCREEN_HEIGHT * 70 / 100 * 21 / 100);
        buttonSignUp.setWidth(buttonSignUpCancel.getWidth());

        tableSignUp.add(buttonSignUpCancel).width(Value.percentWidth(1f)).left().padLeft(SCREEN_WIDTH * 9 / 100).padBottom(SCREEN_HEIGHT * 2 / 100);
        tableSignUp.add(buttonSignUp).width(Value.percentWidth(1f)).right().padRight(SCREEN_WIDTH * 9 / 100).padBottom(SCREEN_HEIGHT * 2 / 100);
        tableSignUp.row();

//       Login :

        final Table tableLogin = new Table();
        tableLogin.setBackground(backgroundGrey);

        labelUserNameLogin.setWidth(SCREEN_HEIGHT * 70 / 100 * 55 / 100);
        fieldNameLogin.setWidth(SCREEN_HEIGHT * 70 / 100 * 45 / 100);
        labelUserNameLogin.setAlignment(1);
        tableLogin.add(labelUserNameLogin).fill();
        tableLogin.add(fieldNameLogin).fill().left().width(Value.percentWidth(1f));
        tableLogin.row();

        if (messageErrorLogin.equals(USER_NAME_NOT_EXIST)) {
            messageErrorUsername.setAlignment(1);
            tableLogin.add(messageErrorUsername).left().padBottom(SCREEN_HEIGHT * 6 / 100).colspan(2);
        } else tableLogin.add(noMessageError4).left().padBottom(SCREEN_HEIGHT * 6 / 100).colspan(2);
        tableLogin.row();

        cellErrorUsernameLogin = tableLogin.getCell(noMessageError4);

        labelCurrentPasswordLogin.setAlignment(1);
        labelCurrentPasswordLogin.setWidth(SCREEN_HEIGHT * 70 / 100 * 55 / 100);
        fieldPasswordLogin.setWidth(SCREEN_HEIGHT * 70 / 100 * 45 / 100);
        tableLogin.add(labelCurrentPasswordLogin);
        tableLogin.add(fieldPasswordLogin).fill().width(Value.percentWidth(1f));
        tableLogin.row();

        if (messageErrorLogin.equals(INCORRECT_PASSWORD)) {
            messageErrorLoginPassword.setAlignment(1);
            tableLogin.add(messageErrorLoginPassword).left().padBottom(SCREEN_HEIGHT * 8 / 100).colspan(2);
        } else tableLogin.add(noMessageError5).left().padBottom(SCREEN_HEIGHT * 8 / 100).colspan(2);
        tableLogin.row();
        cellErrorPasswordLogin = tableLogin.getCell(noMessageError5);

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

    /**
     * initialisation d'une joueur
     *
     * @param username:     username du joueur
     * @param password      : password du joueur
     * @param avatar:avatar du joueur
     */
    private void initializeStatsPlayer(String username, String password, String avatar) {

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        GlobalStats globalStats = initializeGlobalStats();
        ArrayList listLevelStats = initializeLevelStatsPlayer();

        if (isProfileLeft) {
            player1.setName(username);
            player1.setAvatar(avatar);

            player1.setAccount(account);
            player1.setGlobalStats(globalStats);
            player1.setListLevelStats(listLevelStats);
        } else {
            player2.setName(username);
            player2.setAvatar(avatar);
            player2.setAccount(account);
            player2.setGlobalStats(globalStats);
            player2.setListLevelStats(listLevelStats);
        }

    }

    /**
     * initialise les statistiques des 10 niveaux
     *
     * @return listLevelStats
     */
    private ArrayList initializeLevelStatsPlayer() {
        ArrayList listLevelStats = new ArrayList();
        for (int i = 0; i < 10; i++) {
            LevelStats levelStats = new LevelStats();
            levelStats.setLevel(i + 1);
            listLevelStats.add(levelStats);
        }
        return listLevelStats;
    }

    /**
     * initialise les statistiques globales
     *
     * @return globalStats
     */

    private GlobalStats initializeGlobalStats() {
        GlobalStats globalStats = new GlobalStats();
        return globalStats;
    }

    private void reset(String userName, String currentPasswordLogin) {

    }

    /**
     * récupération du joueur dans la table des joueurs
     *
     * @param userName
     * @param currentPasswordLogin
     * @return message d'anomalie
     */
    private String getPlayer(String userName, String currentPasswordLogin) {
        String messageError = "";

        Iterator iter = tabPlayers.iterator();
        while (iter.hasNext()) {
            HumanPlayer player = (HumanPlayer) iter.next();

            if (player.getName().equals(userName)) {
                if (player.getAccount().getPassword().equals(currentPasswordLogin)) {
                    createPlayer(player);
                    return messageError;
                } else {
                    return INCORRECT_PASSWORD;
                }
            }
        }
        return USER_NAME_NOT_EXIST;
    }

    /**
     * création d'un joueur à partir du joueur 1 ou joueur 2
     *
     * @param player joueur 1 ou joueur 2
     */
    private void createPlayer(HumanPlayer player) {

        Account account = new Account();
        account = player.getAccount();
        GlobalStats globalStats = new GlobalStats();
        globalStats = player.getGlobalStats();
        ArrayList listLevelStats = getLevelStatsPlayer(player);

        if (isProfileLeft) {
            player1.setName(player.getName());
            player1.setAvatar(player.getAvatar());
            player1.setAccount(account);
            player1.setGlobalStats(globalStats);
            player1.setListLevelStats(listLevelStats);
        } else {
            player2.setName(player.getName());
            player2.setAvatar(player.getAvatar());
            player2.setAccount(account);
            player2.setGlobalStats(globalStats);
            player2.setListLevelStats(listLevelStats);
        }

    }

    /**
     * récupération des 10 niveaux de statistique d'un joueur
     *
     * @param player
     * @return liste des 10 niveaux de statistique
     */
    private ArrayList getLevelStatsPlayer(HumanPlayer player) {

        ArrayList listLevelStats = new ArrayList();
        for (int i = 0; i < 10; i++) {
            listLevelStats.add(player.getListLevelStats().get(i));
        }
        return listLevelStats;
    }

    /**
     * Validation de la saisie
     *
     * @param userName
     * @param newPassword1
     * @param newPassword2
     * @return message d'anomalie
     */
    public String validatePlayer(String userName, String newPassword1, String newPassword2) {
        String messageError = "";
        if (userName.length() < 4 || userName.length() > 20) {
            return INCORRECT_LENGTH_USER_NAME;
        }

        Iterator iter = tabPlayers.iterator();
        while (iter.hasNext()) {
            HumanPlayer statis = (HumanPlayer) iter.next();

            if (statis.getName().equals(userName)) {
                return USER_NAME_NOT_AVAILABLE;
            }

        }
        if (newPassword1.length() < 4) {
            return INCORRECT_LENGTH_PASSWORD;
        }

        if (!(newPassword1.equals(newPassword2))) {
            return PASSWORDS_NOT_MATCH;
        }
        return messageError;
    }

    // int player --> 1 si c'est le joueur du bouton gauche, 2 sinon
    private void showProfile(int player) {
        disableButton(buttonPlay, buttonSettings, buttonExit, buttonHall, buttonProfileLeft, buttonProfileRight);
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
        ImageButton imageAvatar;

        isInSignUp = false;

        if (player == 1) {
            playerName = player1.getName();
            playerRank = player1.getGlobalStats().getRank();

            imagePlayer1 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(player1.getAvatar()))));
            imageProfile = imagePlayer1;
            imageAvatar = new ImageButton(imagePlayer1);
            cellProfileLeft.clearActor();
            createButtonProfileLeft();
            cellProfileLeft.setActor(buttonProfileLeft);
            cellLabelProfileLeft.clearActor();
            Label labelProfileLeft = new Label("Logged", skinSgx, "medium");
            labelProfileLeft.setAlignment(1);
            cellLabelProfileLeft.setActor(labelProfileLeft);
        } else if (player == 2) {
            playerName = player2.getName();
            playerRank = player2.getGlobalStats().getRank();
            imagePlayer2 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(player2.getAvatar()))));
            imageProfile = imagePlayer2;
            imageAvatar = new ImageButton(imagePlayer2);
            cellProfileRight.clearActor();
            createButtonProfileRight();
            cellProfileRight.setActor(buttonProfileRight);
            cellLabelProfileRight.clearActor();
            Label labelProfileRight = new Label("Logged", skinSgx, "medium");
            labelProfileRight.setAlignment(1);
            cellLabelProfileRight.setActor(labelProfileRight);
        } else {
            imageAvatar = new ImageButton(imageAnonymous);
        }

        Label labelWelcome = new Label("Welcome " + playerName + " !", skinSgx, "title-white");

        disableButton(imageAvatar);

        Label labelRank = new Label(lang.format("label_rank", playerRank, totalNumberPlayers), skinSgx, "white");
        Label labelNew = new Label("Rank : none ", skinSgx, "white");
        labelWelcome.setHeight(SCREEN_HEIGHT * 10 / 100);
        table.add(labelWelcome).height(Value.percentHeight(1f));
        table.row();

        table.add(imageAvatar).height(buttonProfileHeight).width(buttonProfileHeight);
        table.row();
        cellAvatarProfile = table.getCell(imageAvatar);
        if (playerRank == 0) {
            labelNew.setHeight(SCREEN_HEIGHT * 10 / 100);
            table.add(labelNew).height(Value.percentHeight(1f));
        } else {
            labelRank.setHeight(SCREEN_HEIGHT * 10 / 100);
            table.add(labelRank).height(Value.percentHeight(1f));
        }
        table.row();
        table.top();

        buttonEdit = new TextButton(lang.get("text_edit"), skinSgx, "big");
        buttonEdit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                showEdit();
            }
        });

        buttonLogOut = new TextButton(lang.get("text_log_out"), skinSgx, "big");
        buttonLogOut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                showLogOut();
            }
        });
        buttonDelete = new TextButton(lang.get("text_delete_account"), skinSgx, "big");
        buttonDelete.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                showDelete();
            }
        });
        buttonBack = new TextButton(lang.get("button_back"), skinSgx, "big");
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                windowProfile.remove();
                enableButton(buttonPlay, buttonSettings, buttonExit, buttonHall, buttonProfileLeft, buttonProfileRight);
                imageProfile = imageAnonymous;
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

    private void showEdit() {
        disableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
        windowEdit.clear();
        windowEdit.setSize(windowProfile.getWidth(), windowProfile.getHeight());
        windowEdit.setPosition(SCREEN_WIDTH / 2 - windowEdit.getWidth() / 2, SCREEN_HEIGHT / 2 - windowEdit.getHeight() / 2);
        windowEdit.setMovable(false);
        // place le titre de la fenetre au milieu
        windowEdit.getTitleTable().padLeft(windowEdit.getWidth() / 2 - windowEdit.getTitleLabel().getWidth() / 2);

        hasChangedAvatar = false;

        tableEdit = new Table();
        windowEdit.addActor(tableEdit);
        tableEdit.setFillParent(true);

        tableEdit.left().padTop(windowEdit.getHeight() * 6 / 100);
        tableEdit.setSize(windowProfile.getWidth(), windowProfile.getWidth());

        float widthImage = windowEdit.getHeight() * 14 / 100;
        float heightImage = windowEdit.getHeight() * 14 / 100;

        buttonEditCancel = new TextButton(lang.get("button_cancel"), skinSgx, "big");
        buttonEditCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                windowEdit.remove();
                enableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
            }
        });
        buttonEditSave = new TextButton(lang.get("button_save"), skinSgx, "big");
        buttonEditSave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                if (isProfileLeft) {
                    messageErrorEdit = validateEdit(fieldCurrentPassword, player1, fieldNewPassword1, fieldNewPassword2);
                } else {
                    messageErrorEdit = validateEdit(fieldCurrentPassword, player2, fieldNewPassword1, fieldNewPassword2);
                }
                // message d'anomalie
                if (!(messageErrorEdit.equals(""))) {
                    isInSignUp = false;
                    cellLabelEditMessage1.clearActor();
                    cellLabelEditMessage2.clearActor();
                    cellLabelEditMessage3.clearActor();
                    if (messageErrorEdit.equals(INCORRECT_PASSWORD)) {
                        cellLabelEditMessage1.setActor(messageErrorLoginPassword);
                        cellLabelEditMessage2.setActor(noMessageError1);
                        cellLabelEditMessage3.setActor(noMessageError1);
                    } else if (messageErrorEdit.equals(INCORRECT_LENGTH_PASSWORD)) {
                        cellLabelEditMessage2.setActor(messageErrorLengthPassword);
                        cellLabelEditMessage1.setActor(noMessageError1);
                        cellLabelEditMessage3.setActor(noMessageError1);
                    } else if (messageErrorEdit.equals(PASSWORDS_NOT_MATCH)) {
                        cellLabelEditMessage3.setActor(messageErrorPasswordNotMatch);
                        cellLabelEditMessage1.setActor(noMessageError1);
                        cellLabelEditMessage2.setActor(noMessageError1);
                    }
                } else {
                    // mise à jour de l'avatar dans le profil
                    windowEdit.remove();
                    enableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
                    cellAvatarProfile.clearActor();
                    imageProfile = imageTmp;
                    ImageButton imageAvatar = new ImageButton(imageProfile);
                    cellAvatarProfile.setActor(imageAvatar);
                    // mise à jour avatar gauche dans le menu
                    if (isProfileLeft) {
                        isPlayer1Logged = true;
                        imagePlayer1 = imageProfile;
                        cellProfileLeft.clearActor();
                        createButtonProfileLeft();
                        cellProfileLeft.setActor(buttonProfileLeft);
                        changeAvatar(player1);

                    } else {
                        //mise à jour avatar droit dans le menu
                        isPlayer2Logged = true;
                        imagePlayer2 = imageProfile;
                        cellProfileRight.clearActor();
                        createButtonProfileRight();
                        cellProfileRight.setActor(buttonProfileRight);
                        changeAvatar(player2);
                    }
                }
            }
        });

        Label labelCurrentPassword = new Label(lang.get("label_current_password"), skinSgx, "white");
        Label labelNewPassword1 = new Label(lang.get("label_new_password"), skinSgx, "white");
        Label labelNewPassword2 = new Label(lang.get("label_new_password_again"), skinSgx, "white");

        resetField(fieldCurrentPassword);
        fieldCurrentPassword.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        resetField(fieldNewPassword1);
        fieldNewPassword1.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        resetField(fieldNewPassword2);
        fieldNewPassword2.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        setPasswordMode(fieldCurrentPassword, fieldNewPassword1, fieldNewPassword2);
        stage.setKeyboardFocus(fieldCurrentPassword);

        float heightImageEdit = windowEdit.getHeight() * 14 / 100;


        Label noMessageError1 = new Label("", skinSgx, "white");
        Label noMessageError2 = new Label("", skinSgx, "white");
        Label noMessageError3 = new Label("", skinSgx, "white");
        messageErrorLoginPassword.setColor(Color.RED);
        messageErrorLoginPassword.setAlignment(1);
        messageErrorLengthPassword.setColor(Color.RED);
        messageErrorLengthPassword.setAlignment(1);
        messageErrorPasswordNotMatch.setColor(Color.RED);
        messageErrorPasswordNotMatch.setAlignment(1);

        Label avatarLabel = new Label("Click on the image to change it", skinSgx);
        avatarLabel.setAlignment(1);
        tableEdit.add(avatarLabel).colspan(2).width(Value.percentWidth(1f)).padBottom(SCREEN_HEIGHT * 2 / 100);
        tableEdit.row();
        tableEdit.add(buttonEditAvatar).width(heightImageEdit).height(heightImageEdit).padBottom(SCREEN_HEIGHT * 4 / 100).colspan(2);
        tableEdit.row();
        labelCurrentPassword.setWidth(windowEdit.getWidth() * 55 / 100);
        fieldCurrentPassword.setWidth(windowEdit.getWidth() * 45 / 100);
        labelCurrentPassword.setAlignment(1);
        tableEdit.add(labelCurrentPassword).fill();
        tableEdit.add(fieldCurrentPassword).fill().left().width(Value.percentWidth(1f));
        tableEdit.row();
        tableEdit.add(noMessageError1).left().padBottom(SCREEN_HEIGHT * 3 / 100).colspan(2).center();
        cellLabelEditMessage1 = tableEdit.getCell(noMessageError1);
        tableEdit.row();
        labelNewPassword1.setWidth(windowEdit.getWidth() * 55 / 100);
        fieldNewPassword1.setWidth(windowEdit.getWidth() * 45 / 100);
        tableEdit.add(labelNewPassword1);
        tableEdit.add(fieldNewPassword1).fill().width(Value.percentWidth(1f));
        tableEdit.row();
        tableEdit.add(noMessageError2).left().padBottom(SCREEN_HEIGHT * 3 / 100).colspan(2).center();
        cellLabelEditMessage2 = tableEdit.getCell(noMessageError2);
        tableEdit.row();
        labelNewPassword2.setWidth(windowEdit.getWidth() * 55 / 100);
        fieldNewPassword2.setWidth(windowEdit.getWidth() * 45 / 100);
        labelNewPassword2.setAlignment(1);
        tableEdit.add(labelNewPassword2).width(Value.percentWidth(1f)).fill();//.padRight(SCREEN_WIDTH * 2 / 100);
        tableEdit.add(fieldNewPassword2).fill();
        tableEdit.row();
        tableEdit.add(noMessageError3).padBottom(SCREEN_HEIGHT * 3 / 100).colspan(2).center();
        cellLabelEditMessage3 = tableEdit.getCell(noMessageError3);
        tableEdit.row().padTop(windowEdit.getHeight() * 4 / 100);
        buttonEditCancel.setWidth(windowEdit.getWidth() * 21 / 100);
        buttonEditSave.setWidth(buttonEditCancel.getWidth());
        tableEdit.add(buttonEditCancel).width(Value.percentWidth(1f)).left().padLeft(windowEdit.getWidth() * 15 / 100);
        tableEdit.add(buttonEditSave).width(Value.percentWidth(1f)).right().padRight(windowEdit.getWidth() * 15 / 100);

        cellEdit = tableEdit.getCell(buttonEditAvatar);

        cellEdit.clearActor();
        buttonEditAvatar = new ImageButton(imageProfile);
        buttonEditAvatar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                isInEdit = true;
                showAvatarSelectionWindow();
            }
        });
        cellEdit.setActor(buttonEditAvatar);
        cellEdit.setActor(buttonEditAvatar);
        windowEdit.add(tableEdit);
        stage.addActor(windowEdit);
    }

    private void createButtonProfileRight() {
        buttonProfileRight = new ImageButton(imageProfile);
        buttonProfileRight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                numPlayer = 2;
                isProfileLeft = false;
                if (!isPlayer2Logged)
                    showLoginWindow();
                else
                    showProfile(numPlayer);
            }
        });
    }

    private void createButtonProfileLeft() {
        buttonProfileLeft = new ImageButton(imageProfile);
        buttonProfileLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                numPlayer = 1;
                isProfileLeft = true;
                if (!isPlayer1Logged)
                    showLoginWindow();
                else
                    showProfile(numPlayer);
            }
        });
    }

    private void changeAvatar(HumanPlayer player) {
        if (hasImportedAvatar) {
            if (player.getAvatar().substring(0, player.getAvatar().length() - 5).equals("profile/" + "_" + player.getName()))
                Gdx.files.local(player.getAvatar()).delete();
            String extension = selectedFile.getName().substring(selectedFile.getName().length() - 4, selectedFile.getName().length());
            pathImageProfile = "profile/" + "_" + player.getName() + extension;
            sourceImage.copyTo(Gdx.files.local(pathImageProfile));
        }
        player.setAvatar(pathImageProfile);
        if (hasChangedAvatar) {
            if (isPlayerInTabStats(player.getName()))
                updateAvatar(player.getName());
        }

    }

    private String validateEdit(TextField fieldCurrentPassword, HumanPlayer player, TextField fieldNewPassword1, TextField fieldNewPassword2) {
        String messageErrorEdit = "";


        if (fieldCurrentPassword.getText().equals("") && fieldNewPassword1.getText().equals("") && fieldNewPassword2.getText().equals(""))
            return messageErrorEdit;
        if (fieldCurrentPassword.getText() == null)
            return INCORRECT_PASSWORD;
        if (!fieldCurrentPassword.getText().equals(player.getAccount().getPassword()))
            return INCORRECT_PASSWORD;
        if (fieldNewPassword1.getText().length() < 4)
            return INCORRECT_LENGTH_PASSWORD;
        if (!(fieldNewPassword1.getText().equals(fieldNewPassword2.getText())))
            return PASSWORDS_NOT_MATCH;

        player.getAccount().setPassword(fieldNewPassword1.getText());
        return messageErrorEdit;
    }

    private void showAvatarSelectionWindow() {
        if (isInEdit)
            disableButton(buttonEditAvatar, buttonEditSave, buttonEditCancel);
        else if (isInSignUp) {
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

        final Table tableSelection = new Table();
        windowAvatarSelection.addActor(tableSelection);
        tableSelection.setFillParent(true);
        tableSelection.top().padTop(windowAvatarSelection.getHeight() * 13 / 100);

        hasImportedAvatar = false;
        pathImageTmp = pathImageProfile;
        imageTmp = imageProfile;
        buttonSelectionAvatar = new ImageButton(imageProfile);

        float widthImageSelect = windowAvatarSelection.getWidth() * 2 / 15;
        float heightImageSelect = windowAvatarSelection.getWidth() * 2 / 10;

        tableSelection.add(robotButton).width(widthImageSelect).height(heightImageSelect).padRight(heightImageSelect / 5);
        tableSelection.add(bananaButton).width(widthImageSelect).height(heightImageSelect);
        tableSelection.add(burgerButton).width(widthImageSelect).height(heightImageSelect);
        tableSelection.add(penguinButton).width(widthImageSelect).height(heightImageSelect).padRight(heightImageSelect / 5);
        tableSelection.add(squidButton).width(widthImageSelect).height(heightImageSelect);
        tableSelection.row();
        tableSelection.add(pinkButton).width(widthImageSelect).height(heightImageSelect).padRight(heightImageSelect / 5);
        tableSelection.add(pandaButton).width(widthImageSelect).height(heightImageSelect);
        tableSelection.add(blueButton).width(widthImageSelect).height(heightImageSelect);
        tableSelection.add(girlButton).width(widthImageSelect).height(heightImageSelect).padRight(heightImageSelect / 5);
        tableSelection.add(mustacheButton).width(widthImageSelect).height(heightImageSelect);
        tableSelection.row().padTop(windowAvatarSelection.getHeight() * 7 / 100);

        TextButton buttonImport = new TextButton(lang.get("button_import"), skinSgx, "big");
        buttonImport.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                JFrame f = new JFrame();
                f.setVisible(true);
                f.toFront();
                f.setVisible(false);
                f.dispose();

                fc.setDialogTitle("Select an image");
                fc.setAcceptAllFileFilterUsed(false);
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and GIF images", "png", "jpg", "jpeg", "gif");
                fc.addChoosableFileFilter(filter);


                // affichage de la fenetre pour choisir l'image et teste si le bouton ok est presse

                int returnValue = fc.showOpenDialog(f);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fc.getSelectedFile();
                    Drawable imageImport = new TextureRegionDrawable(new TextureRegion(new Texture(selectedFile.getAbsolutePath())));
                    cellAvatarSelect.clearActor();
                    imageTmp = imageImport;
                    pathImageTmp = selectedFile.getAbsolutePath();
                    buttonSelectionAvatar = new ImageButton(imageTmp);
                    cellAvatarSelect.setActor(buttonSelectionAvatar);
                    hasChangedAvatar = true;
                    hasImportedAvatar = true;
                    sourceImage = Gdx.files.absolute(pathImageTmp);
                }
            }
        });


        buttonImport.setWidth(windowAvatarSelection.getWidth() * 23 / 100);
        tableSelection.add(buttonImport).colspan(2).width(Value.percentWidth(1f)).left();
        tableSelection.add(buttonSelectionAvatar).width(heightImageSelect).height(heightImageSelect);

        cellAvatarSelect = tableSelection.getCell(buttonSelectionAvatar);

        TextButton buttonSave = new TextButton(lang.get("button_save"), skinSgx, "big");
        buttonSave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));

                tableSelection.remove();
                windowAvatarSelection.remove();
                pathImageProfile = pathImageTmp;

                if (isInEdit) {
                    enableButton(buttonEditSave, buttonEditCancel);
                    isInEdit = false;
                    cellEdit.clearActor();
                    buttonEditAvatar = new ImageButton(imageTmp);
                    buttonEditAvatar.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            soundButton1.play(prefs.getFloat("volume", 0.2f));
                            isInEdit = true;
                            showAvatarSelectionWindow();
                        }
                    });
                    cellEdit.setActor(buttonEditAvatar);

                } else if (isInSignUp) {
                    enableButton(buttonSignUpCancel, buttonSignUp);
                    buttonTabLogin.setTouchable(Touchable.enabled);
                    buttonTabSignUp.setTouchable(Touchable.enabled);
                    messageErrorSignUp = "";

                    imageProfile = imageTmp;
                    cellSignUp.clearActor();
                    buttonLoginAvatar = new ImageButton(imageProfile);
                    buttonLoginAvatar.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            soundButton1.play(0.2f);
                            isInSignUp = true;
                            showAvatarSelectionWindow();
                        }
                    });
                    cellSignUp.setActor(buttonLoginAvatar);
                    cellErrorAvatarSignUp.clearActor();
                    cellErrorAvatarSignUp.setActor(noMessageError1);
                }
            }
        });
        TextButton buttonCancel = new TextButton(lang.get("button_cancel"), skinSgx, "big");
        buttonCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));

                tableSelection.remove();
                windowAvatarSelection.remove();
                imageTmp = imageProfile;
                hasImportedAvatar = false;

                if (isInEdit) {
                    enableButton(buttonEditAvatar, buttonEditSave, buttonEditCancel);
                    isInEdit = false;
                    hasChangedAvatar = false;
                } else if (isInSignUp) {
                    enableButton(buttonSignUpCancel, buttonSignUp, buttonLoginAvatar);
                    buttonTabLogin.setTouchable(Touchable.enabled);
                    buttonTabSignUp.setTouchable(Touchable.enabled);
                }
            }
        });
        pandaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cellAvatarSelect.clearActor();
                imageTmp = imagePanda;
                pathImageTmp = pathImagePanda;
                buttonSelectionAvatar = new ImageButton(imageTmp);
                cellAvatarSelect.setActor(buttonSelectionAvatar);
                hasChangedAvatar = true;
            }
        });
        pinkButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cellAvatarSelect.clearActor();
                imageTmp = imagePink;
                pathImageTmp = pathImagePink;
                buttonSelectionAvatar = new ImageButton(imageTmp);
                cellAvatarSelect.setActor(buttonSelectionAvatar);
                hasChangedAvatar = true;
            }
        });
        bananaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cellAvatarSelect.clearActor();
                imageTmp = imageBanana;
                pathImageTmp = pathImageBanana;
                buttonSelectionAvatar = new ImageButton(imageTmp);
                cellAvatarSelect.setActor(buttonSelectionAvatar);

                hasChangedAvatar = true;
            }
        });
        mustacheButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cellAvatarSelect.clearActor();
                imageTmp = imageMustache;
                pathImageTmp = pathImageMustache;
                buttonSelectionAvatar = new ImageButton(imageTmp);
                cellAvatarSelect.setActor(buttonSelectionAvatar);

                hasChangedAvatar = true;
            }
        });
        burgerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cellAvatarSelect.clearActor();
                imageTmp = imageBurger;
                pathImageTmp = pathImageBurger;
                buttonSelectionAvatar = new ImageButton(imageTmp);
                cellAvatarSelect.setActor(buttonSelectionAvatar);

                hasChangedAvatar = true;
            }
        });
        girlButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cellAvatarSelect.clearActor();
                imageTmp = imageGirl;
                pathImageTmp = pathImageGirl;
                buttonSelectionAvatar = new ImageButton(imageTmp);
                cellAvatarSelect.setActor(buttonSelectionAvatar);
                hasChangedAvatar = true;
            }
        });
        robotButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cellAvatarSelect.clearActor();
                imageTmp = imageRobot;
                pathImageTmp = pathImageRobot;
                buttonSelectionAvatar = new ImageButton(imageTmp);
                cellAvatarSelect.setActor(buttonSelectionAvatar);
                hasChangedAvatar = true;
            }
        });
        penguinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cellAvatarSelect.clearActor();
                imageTmp = imagePenguin;
                pathImageTmp = pathImagePenguin;
                buttonSelectionAvatar = new ImageButton(imageTmp);
                cellAvatarSelect.setActor(buttonSelectionAvatar);

                hasChangedAvatar = true;
            }
        });
        blueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cellAvatarSelect.clearActor();
                imageTmp = imageBlue;
                pathImageTmp = pathImageBlue;
                buttonSelectionAvatar = new ImageButton(imageTmp);
                cellAvatarSelect.setActor(buttonSelectionAvatar);

                hasChangedAvatar = true;
            }
        });
        squidButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cellAvatarSelect.clearActor();
                imageTmp = imageSquid;
                pathImageTmp = pathImageSquid;
                buttonSelectionAvatar = new ImageButton(imageTmp);
                cellAvatarSelect.setActor(buttonSelectionAvatar);

                hasChangedAvatar = true;
            }
        });
        buttonSave.setWidth(buttonImport.getWidth());
        tableSelection.add(buttonSave).colspan(2).width(Value.percentWidth(1f)).right();
        tableSelection.row().padTop(windowAvatarSelection.getHeight() * 8 / 100);
        buttonCancel.setWidth(buttonImport.getWidth());
        tableSelection.add(buttonCancel).colspan(5).width(Value.percentWidth(1f));

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
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                if (numPlayer == 1) {
                    // remettre l'image anonyme dans le bouton du menu qui est concerne
                    cellProfileLeft.clearActor();
                    imageProfile = imageAnonymous;
                    createButtonProfileLeft();
                    cellProfileLeft.setActor(buttonProfileLeft);
                    cellLabelProfileLeft.clearActor();
                    Label labelProfileLeft = new Label("Not logged", skinSgx, "medium");
                    labelProfileLeft.setAlignment(1);
                    cellLabelProfileLeft.setActor(labelProfileLeft);
                    isPlayer1Logged = false;

                    saveStatsPlayer(player1);
                    isProfileLeft = false;
                    player1.setName("");
                    // remettre l'avatar à anonymous
                } else {
                    cellProfileRight.clearActor();
                    imageProfile = imageAnonymous;
                    createButtonProfileRight();
                    isPlayer2Logged = false;
                    cellProfileRight.setActor(buttonProfileRight);
                    Label labelProfileRight = new Label("Not logged", skinSgx, "medium");
                    labelProfileRight.setAlignment(1);
                    cellLabelProfileRight.setActor(labelProfileRight);
                    saveStatsPlayer(player2);
                    isProfileRight = false;
                    player2.setName("");
                }

                windowLogOut.remove();
                windowProfile.remove();
                enableButton(buttonPlay, buttonHall, buttonSettings, buttonExit, buttonProfileLeft, buttonProfileRight);
            }
        });

        TextButton buttonNo = new TextButton(lang.get("text_no"), skinSgx, "big");
        buttonNo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
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

    private void saveStatsPlayer(HumanPlayer player) {
        // lire la table
        boolean isFound = isPlayerInTabStats(player.getName());
        if (isFound) {
            deletePlayer(player);
        } else totalNumberPlayers++;
        HumanPlayer player3 = new HumanPlayer(player.getName(), player.getColor());
        Account account3 = new Account();
        account3.setPassword(player.getAccount().getPassword());
        account3.setUsername(player.getAccount().getUsername());
        player3.setAccount(account3);
        GlobalStats globalStats3 = setGlobalStats(player);
        player3.setGlobalStats(globalStats3);
        ArrayList listLevelStats3 = getLevelStatsPlayer(player);
        player3.setListLevelStats(listLevelStats3);
        player3.setAvatar(player.getAvatar());
        storePlayer(player3);
    }

    private GlobalStats setGlobalStats(HumanPlayer player) {

        GlobalStats globalStats = new GlobalStats();
        globalStats.setScore(player.getGlobalStats().getScore());
        globalStats.setRank(player.getGlobalStats().getRank());
        globalStats.setTotalSavings(player.getGlobalStats().getTotalSavings());
        globalStats.setTotalWins(player.getGlobalStats().getTotalWins());
        globalStats.setTotalGames(player.getGlobalStats().getTotalGames());
        globalStats.setTotalDefeats(player.getGlobalStats().getTotalDefeats());
        globalStats.setMinTurns(player.getGlobalStats().getMinTurns());
        globalStats.setMaxTrees(player.getGlobalStats().getMaxTrees());
        globalStats.setMaxTotalMoney(player.getGlobalStats().getMaxTotalMoney());
        globalStats.setAvgTurns(player.getGlobalStats().getAvgTurns());
        globalStats.setAvgTrees(player.getGlobalStats().getAvgTrees());
        globalStats.setAvgTotalMoney(player.getGlobalStats().getAvgTotalMoney());
        globalStats.setAvgLostUnits(player.getGlobalStats().getAvgLostUnits());
        globalStats.setAvgLandsTurn(player.getGlobalStats().getAvgLandsTurn());
        globalStats.setMaxLandsTurn(player.getGlobalStats().getMaxLandsTurn());
        globalStats.setAvgArmy(player.getGlobalStats().getAvgArmy());
        globalStats.setMaxArmy(player.getGlobalStats().getMaxArmy());
        globalStats.setMinArmy(player.getGlobalStats().getMinArmy());
        globalStats.setAvgL0(player.getGlobalStats().getAvgL0());
        globalStats.setAvgL1(player.getGlobalStats().getAvgL1());
        globalStats.setAvgL2(player.getGlobalStats().getAvgL2());
        globalStats.setAvgL3(player.getGlobalStats().getAvgL3());
        globalStats.setAvgLeftL0(player.getGlobalStats().getAvgLeftL0());
        globalStats.setAvgLeftL1(player.getGlobalStats().getAvgLeftL1());
        globalStats.setAvgLeftL2(player.getGlobalStats().getAvgLeftL2());
        globalStats.setAvgLeftL3(player.getGlobalStats().getAvgLeftL3());
        globalStats.setAvgLostL0(player.getGlobalStats().getAvgLostL0());
        globalStats.setAvgLostL1(player.getGlobalStats().getAvgLostL1());
        globalStats.setAvgLostL2(player.getGlobalStats().getAvgLostL2());
        globalStats.setAvgLostL3(player.getGlobalStats().getAvgLostL3());
        globalStats.setAvgLeftUnits(player.getGlobalStats().getAvgLeftUnits());
        globalStats.setAvgUnits(player.getGlobalStats().getAvgUnits());
        globalStats.setMaxL0(player.getGlobalStats().getMaxL0());
        globalStats.setMaxL1(player.getGlobalStats().getMaxL1());
        globalStats.setMaxL2(player.getGlobalStats().getMaxL2());
        globalStats.setMaxL3(player.getGlobalStats().getMaxL3());
        globalStats.setAvgSavings(player.getGlobalStats().getAvgSavings());
        globalStats.setMaxLostL0(player.getGlobalStats().getMaxLostL0());
        globalStats.setMaxLostL1(player.getGlobalStats().getMaxLostL1());
        globalStats.setMaxLostL2(player.getGlobalStats().getMaxLostL2());
        globalStats.setMaxLostL3(player.getGlobalStats().getMaxLostL3());
        globalStats.setMaxLostUnits(player.getGlobalStats().getMaxLostUnits());
        globalStats.setMaxUnits(player.getGlobalStats().getMaxUnits());
        return globalStats;


    }

    private void updateAvatar(String name) {

        Iterator iter = tabPlayers.iterator();
        while (iter.hasNext()) {
            HumanPlayer player = (HumanPlayer) iter.next();
            if (player.getName().equals(name)) {
                player.setAvatar(pathImageProfile);
            }
        }
    }

    private boolean isPlayerInTabStats(String name) {

        Iterator iter = tabPlayers.iterator();
        while (iter.hasNext()) {
            HumanPlayer player = (HumanPlayer) iter.next();
            if (player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void storePlayer(HumanPlayer player) {

        boolean isFound = false;
        int i = 0;
        while (i < tabPlayers.size() && !isFound) {
            HumanPlayer playerTab = (HumanPlayer) tabPlayers.get(i);

            if (playerTab.getGlobalStats().getScore() > player.getGlobalStats().getScore()) {
                i++;
            } else
                isFound = true;
        }

        player.getGlobalStats().setRank(i + 1);
        tabPlayers.add(i, player);
        int j = i + 1;
        while (j < tabPlayers.size()) {
            HumanPlayer playerTab = (HumanPlayer) tabPlayers.get(j);
            playerTab.getGlobalStats().setRank(j + 1);
            j++;
        }

    }

    private void deletePlayer(HumanPlayer player) {
        int i = 0;
        boolean isFound = false;
        while (i < tabPlayers.size() && !isFound) {
            HumanPlayer playerT = (HumanPlayer) tabPlayers.get(i);

            if (playerT.getName().equals(player.getName())) {
                //   if (playerT.getAccount().getPassword().equals(player.getAccount().getPassword())) {
                tabPlayers.remove(i);
                isFound = true;
                //  }

            }
            i++;
        }
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

                if (isPlayer1Logged)
                    saveStatsPlayer(player1);
                if (isPlayer2Logged)
                    saveStatsPlayer(player2);
                FileBuilder fileBuilder = new FileBuilder();
                fileBuilder.createFile();
                dispose();
                Gdx.app.exit();
            }
        });

        TextButton buttonNo = new TextButton(lang.get("text_no"), skinSgx, "big");
        buttonNo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
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
        setPasswordMode(fieldPassword);
        stage.setKeyboardFocus(fieldPassword);

        TextButton buttonConfirm = new TextButton(lang.get("button_confirm"), skinSgx, "big");
        buttonConfirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
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
                soundButton2.play(prefs.getFloat("volume", 0.2f));
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


