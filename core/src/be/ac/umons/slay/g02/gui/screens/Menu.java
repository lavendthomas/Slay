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
import java.util.Map;

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
    public static Drawable imageRobot = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(prefs.getString("pathImageRobot")))));
    public static Drawable imagePanda = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(prefs.getString("pathImagePanda")))));
    public static Drawable imageGirl = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(prefs.getString("pathImageGirl")))));
    public static Drawable imagePink = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(prefs.getString("pathImagePink")))));
    public static Drawable imageSquid = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(prefs.getString("pathImageSquid")))));
    public static Drawable imageBanana = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(prefs.getString("pathImageBanana")))));
    public static Drawable imageBurger = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(prefs.getString("pathImageBurger")))));
    public static Drawable imageBlue = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(prefs.getString("pathImageBlue")))));
    public static Drawable imageMustache = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(prefs.getString("pathImageMustache")))));
    public static Drawable imagePenguin = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(prefs.getString("pathImagePenguin")))));
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
    public static HumanPlayer player1 = new HumanPlayer("P1", Colors.C1);
    public static HumanPlayer player2 = new HumanPlayer("P2", Colors.C1);
    private Window windowExit = new Window(lang.get("text_exit"), skinSgx);
    private Window windowSettings = new Window(lang.get("text_settings"), skinSgx);
    private Window windowAlert = new Window(lang.get("text_disable_registration"), skinSgx);
    private Window windowProfile = new Window(lang.get("text_profile"), skinSgx);
    private Window windowEdit = new Window(lang.get("text_edit"), skinSgx);
    private Window windowAvatarSelection = new Window(lang.get("text_choose_an_avatar"), skinSgx);
    private Window windowLogOut = new Window(lang.get("text_log_out"), skinSgx);
    private Window windowDelete = new Window(lang.get("text_delete_account"), skinSgx);
    private Table mainTableLogin = new Table();
    private Table tableEdit;
    private Table tableBackground;
    private Cell<ImageButton> cellAvatarSelect;
    private Cell<ImageButton> cellEdit;
    private Cell<ImageButton> cellSignUp;
    private Cell<ImageButton> cellAvatarProfile;
    private Cell<ImageButton> cellProfileLeft;
    private Cell<ImageButton> cellProfileRight;
    private Cell<Label> cellLabelEditMessage1;
    private Cell<Label> cellLabelEditMessage2;
    private Cell<Label> cellLabelEditMessage3;
    private Cell<Label> cellErrorUsernameLogin;
    private Cell<Label> cellErrorPasswordLogin;
    private Cell<Label> cellErrorUsernameSignUp;
    private Cell<Label> cellErrorPasswordSignUp;
    private Cell<Label> cellErrorPassword2SignUp;
    private Cell<Label> cellErrorAvatarSignUp;
    private Cell<Label> cellErrorPasswordDelete;
    private Label noMessageError0 = new Label("", skinSgx);
    private Label noMessageError1 = new Label("", skinSgx);
    private Label noMessageError2 = new Label("", skinSgx);
    private Label noMessageError3 = new Label("", skinSgx);
    private Label noMessageError4 = new Label("", skinSgx);
    private Label noMessageError5 = new Label("", skinSgx);
    private Label noMessageError6 = new Label("", skinSgx);
    private Label noMessageError7 = new Label("", skinSgx);
    private Label noMessageError8 = new Label("", skinSgx);
    private Label noMessageError9 = new Label("", skinSgx);
    private Label messageErrorDeletePassword = new Label("*" + prefs.getString("INCORRECT_PASSWORD"), skinSgx, "white");
    private Label messageErrorLoginPassword = new Label("*" + prefs.getString("INCORRECT_PASSWORD"), skinSgx, "white");
    private Label messageErrorUserLogged = new Label("*" + prefs.getString("USER_LOGGED"), skinSgx, "white");
    private Label messageErrorUsername = new Label("*" + prefs.getString("USER_NAME_NOT_EXIST"), skinSgx, "white");
    private Label messageErrorLengthPassword = new Label("*" + prefs.getString("INCORRECT_LENGTH_PASSWORD"), skinSgx, "white");
    private Label messageErrorLengthUsername = new Label("*" + prefs.getString("INCORRECT_LENGTH_USER_NAME"), skinSgx, "white");
    private Label messageUsernameNotAvailable = new Label("*" + prefs.getString("USER_NAME_NOT_AVAILABLE"), skinSgx, "white");
    private Label messageErrorPasswordNotMatch = new Label("*" + prefs.getString("PASSWORDS_NOT_MATCH"), skinSgx, "white");
    private Label messageErrorNoAvatar = new Label("*" + prefs.getString("NO_AVATAR"), skinSgx, "white");
    private Label labelProfileLeft;
    private Label labelProfileRight;
    private TextField fieldPasswordDelete = new TextField("", skinSgx);
    private TextField fieldCurrentPassword = new TextField("", skinSgx);
    private TextField fieldNewPassword1 = new TextField("", skinSgx);
    private TextField fieldNewPassword2 = new TextField("", skinSgx);
    private TextField fieldNameSignUp = new TextField("", skinSgx);
    private TextField fieldPasswordLogin = new TextField("", skinSgx);
    private TextField fieldNameLogin = new TextField("", skinSgx);
    private TextField fieldPassword1SignUp = new TextField("", skinSgx);
    private TextField fieldPassword2SignUp = new TextField("", skinSgx);
    private String messageErrorLogin = "";
    private String messageErrorSignUp = "";
    private String messageErrorEdit = "";
    private String messageErrorDelete = "";
    private boolean isProfileLeft = false;
    private boolean hasImportedAvatar = false;
    private boolean hasChangedAvatar = false;
    private boolean isInSignUp = false;
    private boolean isInEdit = false;
    private int buttonCenterWidth;
    private int windowSettingsWidth;
    private int buttonProfileHeight;
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
    private String playerName;
    private FileHandle sourceImage;
    private File selectedFile;

    private int buttonCenterHeight;
    private int buttonCenterGap;
    private int labelProfileWidth;
    private int tableCenterPositionX;
    private int tableCenterPositionY;

    public Menu(Game aGame) {
        game = aGame;
        init();
    }

    public void init() {
        stage.clear();

        if (SCREEN_WIDTH < SCREEN_HEIGHT) {
            buttonCenterWidth = SCREEN_WIDTH * 28 / 100;
            buttonProfileHeight = SCREEN_HEIGHT * 10 / 100;
            windowSettingsWidth = buttonCenterWidth * 3 / 4;
            tableCenterPositionX = SCREEN_WIDTH / 2;
            tableCenterPositionY = SCREEN_HEIGHT / 3;
            buttonCenterHeight = SCREEN_HEIGHT * 7 / 100;
        } else {
            buttonCenterWidth = Math.min(VIRTUAL_WIDTH * 28 / 100, (int) (SCREEN_WIDTH * 0.9));
            buttonProfileHeight = SCREEN_HEIGHT * 10 / 100;
            windowSettingsWidth = buttonCenterWidth * 3 / 4;
            tableCenterPositionX = VIRTUAL_WIDTH / 2;
            tableCenterPositionY = VIRTUAL_HEIGHT / 3;
            buttonCenterHeight = VIRTUAL_HEIGHT * 7 / 100;
        }
        buttonCenterGap = SCREEN_HEIGHT * 7 / 100;
        labelProfileWidth = buttonCenterWidth * 35 / 100;

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

        if (prefs.getBoolean("isAccountEnabled")) {
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
            labelProfileLeft = new Label("Not Logged", skinSgx, "medium");
            labelProfileRight = new Label("Not Logged", skinSgx, "medium");
            labelPlayer1.setAlignment(1);
            labelPlayer2.setAlignment(1);
            labelProfileLeft.setAlignment(1);
            labelProfileRight.setAlignment(1);
            if (prefs.getBoolean("isPlayer1Logged")) {
                imageProfile = imagePlayer1;
                labelProfileLeft = new Label("Logged", skinSgx, "medium");
                labelProfileLeft.setAlignment(1);
            } else {
                imageProfile = imageAnonymous;
            }
            createButtonProfileLeft();

            if (prefs.getBoolean("isPlayer2Logged")) {
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
            if (SCREEN_WIDTH > SCREEN_HEIGHT) {
                tableProfile.add(labelPlayer1).width(labelProfileWidth);
                tableProfile.add(labelPlayer2).width(labelProfileWidth);
                tableProfile.row().pad(10, 0, 0, 0);
                tableProfile.add(labelProfileLeft).width(labelProfileWidth).height(buttonProfileHeight / 5);
                tableProfile.add(labelProfileRight).width(labelProfileWidth).height(buttonProfileHeight / 5);
                tableProfile.row().pad(10, 0, 0, 0);
                tableProfile.add(buttonProfileLeft).width(labelProfileWidth).height(buttonProfileHeight);
                tableProfile.add(buttonProfileRight).width(labelProfileWidth).height(buttonProfileHeight);
                tableProfile.row();
                cellProfileLeft = tableProfile.getCell(buttonProfileLeft);
                cellProfileRight = tableProfile.getCell(buttonProfileRight);
                Label labelProfile = new Label("", skinSgx);
                tableProfile.add(labelProfile);
                tableProfile.add(labelProfile);
            }
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
        batch.setProjectionMatrix(camera.combined);
        sprite.draw(batch);
        batch.end();
        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        init();
        stage.getViewport().update(width, height, true);
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
        if (prefs.getBoolean("isAccountEnabled"))
            disableButton(buttonHall, buttonProfileLeft, buttonProfileRight);
        windowSettings.clear();
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            windowSettings.setSize(windowSettingsWidth, windowSettingsWidth);
        } else {
            windowSettings.setSize(windowSettingsWidth, 2 * windowSettingsWidth);
        }
        windowSettings.setPosition(SCREEN_WIDTH / 2 - windowSettings.getWidth() / 2, SCREEN_HEIGHT / 2 - windowSettings.getHeight() / 2);
        windowSettings.setMovable(false);
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
        final Slider sliderVolume = new Slider(prefs.getFloat("sliderPosition"), prefs.getFloat("sliderPosition"), 1, false, skinSgx);
        sliderVolume.setRange(0, sliderVolume.getWidth());
        sliderVolume.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int coordinatesPointer) {
                prefs.putFloat("volume", Math.min(1f, Math.max(0f, x / sliderVolume.getWidth())));
                prefs.putFloat("sliderPosition", sliderVolume.getVisualValue());
                prefs.flush();

                soundButton1.setVolume(1, prefs.getFloat("volume", 0.2f));
                soundButton2.setVolume(2, prefs.getFloat("volume", 0.2f));
                soundButton3.setVolume(3, prefs.getFloat("volume", 0.1f));
            }
        });
        table.add(labelVolume).height(Value.percentHeight(1f)).width(windowSettings.getWidth() / 3);
        table.add(sliderVolume).height(Value.percentHeight(1f)).width(windowSettings.getWidth() / 3);
        table.row();

        Label labelFullscreen = new Label("Fullscreen", skinSgx, "white");
        labelFullscreen.setHeight(SCREEN_HEIGHT * 13 / 200);

        switchFullscreen = new Button(skinSgx, "switch");
        if (prefs.getBoolean("isFullScreenEnabled"))
            switchFullscreen.setChecked(true);

        switchFullscreen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!prefs.getBoolean("isFullScreenEnabled")) {
                    soundButton2.play(prefs.getFloat("volume", 0.2f));
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    prefs.putBoolean("isFullScreenEnabled", true);

                } else {
                    soundButton2.play(prefs.getFloat("volume", 0.2f));
                    Gdx.graphics.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);
                    prefs.putBoolean("isFullScreenEnabled", false);
                }
                prefs.flush();
            }
        });
        table.add(labelFullscreen).height(Value.percentHeight(1f)).padRight(windowSettingsWidth * 8 / 100);
        table.add(switchFullscreen);
        table.row();
        Label labelRegistration = new Label(lang.get("label_registration"), skinSgx, "white");
        labelRegistration.setHeight(SCREEN_HEIGHT * 13 / 200);

        switchRegistration = new Button(skinSgx, "switch");

        if (prefs.getBoolean("isAccountEnabled"))
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

        if (prefs.getBoolean("isAccountEnabled") && SCREEN_WIDTH <= SCREEN_HEIGHT) {
            Label labelPlayer1 = new Label("Player 1", skinSgx, "title");
            Label labelPlayer2 = new Label("Player 2", skinSgx, "title");

            Label labelProfileLeft = new Label("Not Logged", skinSgx, "medium");
            Label labelProfileRight = new Label("Not Logged", skinSgx, "medium");
            labelPlayer1.setAlignment(1);
            labelPlayer2.setAlignment(1);

            labelProfileLeft.setAlignment(1);
            labelProfileRight.setAlignment(1);
            if (prefs.getBoolean("isPlayer1Logged")) {
                imageProfile = imagePlayer1;
                labelProfileLeft = new Label("Logged", skinSgx, "medium");
                labelProfileLeft.setAlignment(1);
            } else {
                imageProfile = imageAnonymous;
            }
            createButtonProfileLeft();

            if (prefs.getBoolean("isPlayer2Logged")) {
                imageProfile = imagePlayer2;
                labelProfileRight = new Label("Logged", skinSgx, "medium");
                labelProfileRight.setAlignment(1);
            } else {
                imageProfile = imageAnonymous;
            }
            createButtonProfileRight();
            table.add(labelPlayer1).width(labelProfileWidth);
            table.add(labelPlayer2).width(labelProfileWidth);
            table.row().pad(10, 0, 0, 0);
            table.add(labelProfileLeft).width(labelProfileWidth).height(buttonProfileHeight / 5);
            table.add(labelProfileRight).width(labelProfileWidth).height(buttonProfileHeight / 5);
            table.row().pad(10, 0, 0, 0);

            table.add(buttonProfileLeft).width(buttonProfileHeight).height(buttonProfileHeight);
            table.add(buttonProfileRight).width(buttonProfileHeight).height(buttonProfileHeight);
            table.row();

            Label labelProfile = new Label("", skinSgx);
            table.add(labelProfile);
            table.add(labelProfile);

        }
        buttonSettingsBack = new TextButton(lang.get("button_back"), skinSgx, "big");
        buttonSettingsBack.setWidth(buttonCenterWidth / 2);
        buttonSettingsBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                if (switchRegistration.isChecked()) {
                    prefs.putBoolean("isAccountEnabled", true);
                    prefs.flush();

                    stage.clear();
                    game.setScreen(new Menu(game));
                } else if (!switchRegistration.isChecked()) {
                    prefs.putBoolean("isAccountEnabled", false);
                    prefs.flush();
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
                if (prefs.getBoolean("isPlayer1Logged")) {
                    resetButtonProfile(cellProfileLeft, player1, labelProfileLeft, "isPlayer1Logged");
                    saveStatsPlayer(player1);
                }
                if (prefs.getBoolean("isPlayer2Logged")) {
                    resetButtonProfile(cellProfileRight, player2, labelProfileRight, "isPlayer2Logged");
                    saveStatsPlayer(player2);
                }
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

        Stack contentBack = new Stack();
        final Table tableBack = new Table();
        tableBack.setBackground(backgroundGrey);
        contentBack.addActor(tableBack);
        tableBackground = new Table();
        tableBackground.setFillParent(true);
        tableBackground.add(contentBack).size(SCREEN_HEIGHT * 70 / 100, SCREEN_HEIGHT * 77 / 100 - buttonCenterHeight);
        stage.addActor(tableBackground);
        mainTableLogin.clear();
        stage.addActor(mainTableLogin);
        mainTableLogin.setFillParent(true);

        imageProfile = imageAnonymous;

        buttonTabLogin = new TextButton(lang.get("text_log_in"), skinSgx, "number");
        buttonTabLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                isInSignUp = false;
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
                isInSignUp = true;
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
                        showAvatarSelectionWindow();
                    }
                });
                cellSignUp.setActor(buttonLoginAvatar);
            }
        });

        // To know if we are in LogIn or SignUp tab
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
        messageErrorLoginPassword.setColor(Color.RED);
        messageErrorUsername.setColor(Color.RED);
        messageErrorLengthPassword.setColor(Color.RED);
        messageErrorLengthUsername.setColor(Color.RED);
        messageUsernameNotAvailable.setColor(Color.RED);
        messageErrorPasswordNotMatch.setColor(Color.RED);
        messageErrorUserLogged.setColor(Color.RED);
        messageErrorNoAvatar.setColor(Color.RED);
        buttonLogin = new TextButton(lang.get("text_log_in"), skinSgx, "big");
        buttonLogin.setWidth(buttonCenterWidth / 2);

        buttonLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                messageErrorLogin = "";
                if (isProfileLeft && prefs.getBoolean("isPlayer2Logged")) {
                    if (player2.getName().equals(fieldNameLogin.getText())) {
                        messageErrorLogin = prefs.getString("USER_LOGGED");
                    }
                }
                if (!(isProfileLeft) && prefs.getBoolean("isPlayer1Logged")) {
                    if (player1.getName().equals(fieldNameLogin.getText()))
                        messageErrorLogin = prefs.getString("USER_LOGGED");
                }
                if (messageErrorLogin.equals(""))
                    messageErrorLogin = getPlayer(fieldNameLogin.getText(), fieldPasswordLogin.getText());
                if (!(messageErrorLogin.equals(""))) {
                    cellErrorUsernameLogin.clearActor();
                    cellErrorPasswordLogin.clearActor();

                    if (messageErrorLogin.equals(prefs.getString("USER_NAME_NOT_EXIST"))) {
                        cellErrorUsernameLogin.setActor(messageErrorUsername);
                        cellErrorPasswordLogin.setActor(noMessageError7);

                    } else if (messageErrorLogin.equals(prefs.getString("USER_LOGGED"))) {
                        cellErrorUsernameLogin.setActor(messageErrorUserLogged);
                        cellErrorPasswordLogin.setActor(noMessageError6);
                    } else if (messageErrorLogin.equals(prefs.getString("INCORRECT_PASSWORD"))) {
                        cellErrorUsernameLogin.setActor(noMessageError8);
                        cellErrorPasswordLogin.setActor(messageErrorLoginPassword);
                    }
                } else {
                    tableBackground.remove();
                    mainTableLogin.remove();
                    if (isProfileLeft) {
                        prefs.putBoolean("isPlayer1Logged", true);
                    } else {
                        prefs.putBoolean("isPlayer2Logged", true);
                    }
                    prefs.flush();
                    showProfile(prefs.getInteger("numPlayer"));
                }
            }
        });
        buttonSignUp = new TextButton(lang.get("text_sign_up"), skinSgx, "big");
        buttonSignUp.setWidth(buttonCenterWidth / 2);

        buttonSignUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));

                if (!hasChangedAvatar) {
                    messageErrorSignUp = prefs.getString("NO_AVATAR");
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

                    if (messageErrorSignUp.equals(prefs.getString("NO_AVATAR"))) {
                        cellErrorAvatarSignUp.setActor(messageErrorNoAvatar);
                    } else if (messageErrorSignUp.equals(prefs.getString("USER_LOGGED"))) {
                        cellErrorUsernameSignUp.setActor(messageErrorUserLogged);
                    } else if (messageErrorSignUp.equals(prefs.getString("USER_NAME_NOT_AVAILABLE"))) {
                        cellErrorUsernameSignUp.setActor(messageUsernameNotAvailable);
                    } else if (messageErrorSignUp.equals(prefs.getString("INCORRECT_LENGTH_USER_NAME"))) {
                        cellErrorUsernameSignUp.setActor(messageErrorLengthUsername);
                    } else if (messageErrorSignUp.equals(prefs.getString("INCORRECT_LENGTH_PASSWORD"))) {
                        cellErrorPasswordSignUp.setActor(messageErrorLengthPassword);
                    } else if (messageErrorSignUp.equals(prefs.getString("PASSWORDS_NOT_MATCH"))) {
                        cellErrorPassword2SignUp.setActor(messageErrorPasswordNotMatch);
                    }

                } else {
                    tableBackground.remove();
                    mainTableLogin.remove();
                    initializeStatsPlayer(fieldNameSignUp.getText(), fieldPassword1SignUp.getText(), prefs.getString("pathImageProfile"));
                    isInSignUp = false;
                    if (isProfileLeft) {
                        prefs.putBoolean("isPlayer1Logged", true);
                        prefs.putInteger("numPlayer", 1);
                        changeAvatar(player1);
                    } else {
                        prefs.putBoolean("isPlayer2Logged", true);
                        prefs.putInteger("numPlayer", 2);
                        changeAvatar(player2);
                    }
                    prefs.flush();
                    showProfile(prefs.getInteger("numPlayer"));
                }
            }
        });
        TextButton buttonLoginCancel = new TextButton(lang.get("text_cancel"), skinSgx, "big");
        buttonLoginCancel.setWidth(buttonCenterWidth / 2);
        buttonLoginCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                tableBackground.remove();
                mainTableLogin.remove();
                enableButton(buttonPlay, buttonSettings, buttonExit, buttonHall, buttonProfileLeft, buttonProfileRight);
                resetButtonsProfileLooks();
            }
        });
        buttonSignUpCancel = new TextButton(lang.get("text_cancel"), skinSgx, "big");
        buttonSignUpCancel.setWidth(buttonCenterWidth / 2);
        buttonSignUpCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                tableBackground.remove();
                mainTableLogin.remove();
                isInSignUp = false;
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
                resetButtonsProfileLooks();
            }
        });
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
        final Table tableLogin = new Table();
        tableLogin.setBackground(backgroundGrey);
        labelUserNameLogin.setWidth(SCREEN_HEIGHT * 70 / 100 * 55 / 100);
        fieldNameLogin.setWidth(SCREEN_HEIGHT * 70 / 100 * 45 / 100);
        labelUserNameLogin.setAlignment(1);
        tableLogin.add(labelUserNameLogin).fill();
        tableLogin.add(fieldNameLogin).fill().left().width(Value.percentWidth(1f));
        tableLogin.row();

        if (messageErrorLogin.equals(prefs.getString("USER_NAME_NOT_EXIST"))) {
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

        if (messageErrorLogin.equals(prefs.getString("INCORRECT_PASSWORD"))) {
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
                    return prefs.getString("INCORRECT_PASSWORD");
                }
            }
        }
        return prefs.getString("USER_NAME_NOT_EXIST");
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
    private static ArrayList getLevelStatsPlayer(HumanPlayer player) {
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

        if (isProfileLeft && prefs.getBoolean("isPlayer2Logged")) {
            if (player2.getName().equals(fieldNameSignUp.getText()))
                return prefs.getString("USER_LOGGED");
        }
        if (!(isProfileLeft) && prefs.getBoolean("isPlayer1Logged")) {
            if (player1.getName().equals(fieldNameSignUp.getText()))
                return prefs.getString("USER_LOGGED");
        }
        if (userName.length() < 4 || userName.length() > 20) {
            return prefs.getString("INCORRECT_LENGTH_USER_NAME");
        }
        Iterator iter = tabPlayers.iterator();
        while (iter.hasNext()) {
            HumanPlayer statis = (HumanPlayer) iter.next();

            if (statis.getName().equals(userName)) {
                return prefs.getString("USER_NAME_NOT_AVAILABLE");
            }
        }
        if (newPassword1.length() < 4) {
            return prefs.getString("INCORRECT_LENGTH_PASSWORD");
        }
        if (!(newPassword1.equals(newPassword2))) {
            return prefs.getString("PASSWORDS_NOT_MATCH");
        }
        return messageError;
    }

    // int player = 1 si c'est le joueur du bouton gauche, 2 sinon
    private void showProfile(int player) {
        disableButton(buttonPlay, buttonSettings, buttonExit, buttonHall, buttonProfileLeft, buttonProfileRight);
        windowProfile.clear();
        windowProfile.setSize(SCREEN_HEIGHT * 70 / 100, SCREEN_HEIGHT * 77 / 100);
        windowProfile.setPosition(SCREEN_WIDTH / 2 - windowProfile.getWidth() / 2, SCREEN_HEIGHT / 2 - windowProfile.getHeight() / 2);
        windowProfile.setMovable(false);
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
            prefs.putInteger("playerRank", player1.getGlobalStats().getRank());
            imagePlayer1 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(player1.getAvatar()))));
            imageProfile = imagePlayer1;
            imageAvatar = new ImageButton(imagePlayer1);
            cellProfileLeft.clearActor();
            createButtonProfileLeft();
            cellProfileLeft.setActor(buttonProfileLeft);
            labelProfileLeft.setText("Logged");

        } else if (player == 2) {
            playerName = player2.getName();
            prefs.putInteger("playerRank", player2.getGlobalStats().getRank());
            imagePlayer2 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(player2.getAvatar()))));
            imageProfile = imagePlayer2;
            imageAvatar = new ImageButton(imagePlayer2);
            cellProfileRight.clearActor();
            createButtonProfileRight();
            cellProfileRight.setActor(buttonProfileRight);
            labelProfileRight.setText("Logged");

        } else {
            imageAvatar = new ImageButton(imageAnonymous);
        }
        prefs.flush();

        Label labelWelcome = new Label("Welcome " + playerName + " !", skinSgx, "title-white");
        Label labelRank = new Label(lang.format("label_rank", prefs.getInteger("playerRank"), prefs.getInteger("totalNumberPlayers")), skinSgx, "white");
        Label labelNew = new Label("Rank : none ", skinSgx, "white");
        labelWelcome.setHeight(SCREEN_HEIGHT * 10 / 100);
        table.add(labelWelcome).height(Value.percentHeight(1f));
        table.row();
        table.add(imageAvatar).height(buttonProfileHeight).width(buttonProfileHeight);
        table.row();
        cellAvatarProfile = table.getCell(imageAvatar);
        if (prefs.getInteger("playerRank") == 0) {
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
                resetButtonsProfileLooks();
            }
        });
        buttonEdit.setWidth(windowProfile.getWidth() * 40 / 100);
        windowProfile.center();
        buttonLogOut.setWidth(windowProfile.getWidth() * 40 / 100);
        buttonDelete.setWidth(windowProfile.getWidth() * 40 / 100);
        buttonBack.setWidth(windowProfile.getWidth() * 40 / 100);

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
                // If there is an error message
                if (!(messageErrorEdit.equals(""))) {
                    isInSignUp = false;
                    cellLabelEditMessage1.clearActor();
                    cellLabelEditMessage2.clearActor();
                    cellLabelEditMessage3.clearActor();
                    if (messageErrorEdit.equals(prefs.getString("INCORRECT_PASSWORD"))) {
                        cellLabelEditMessage1.setActor(messageErrorLoginPassword);
                        cellLabelEditMessage2.setActor(noMessageError1);
                        cellLabelEditMessage3.setActor(noMessageError1);
                    } else if (messageErrorEdit.equals(prefs.getString("INCORRECT_LENGTH_PASSWORD"))) {
                        cellLabelEditMessage2.setActor(messageErrorLengthPassword);
                        cellLabelEditMessage1.setActor(noMessageError1);
                        cellLabelEditMessage3.setActor(noMessageError1);
                    } else if (messageErrorEdit.equals(prefs.getString("PASSWORDS_NOT_MATCH"))) {
                        cellLabelEditMessage3.setActor(messageErrorPasswordNotMatch);
                        cellLabelEditMessage1.setActor(noMessageError1);
                        cellLabelEditMessage2.setActor(noMessageError1);
                    }
                } else {
                    // Updates the avatar in the profile
                    windowEdit.remove();
                    enableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
                    if (hasChangedAvatar) {
                        cellAvatarProfile.clearActor();
                        imageProfile = imageTmp;
                        ImageButton imageAvatar = new ImageButton(imageProfile);
                        cellAvatarProfile.setActor(imageAvatar);

                        // Updates the avatar on the left in menu
                        if (isProfileLeft) {
                            prefs.putBoolean("isPlayer1Logged", true);
                            imagePlayer1 = imageProfile;
                            cellProfileLeft.clearActor();
                            createButtonProfileLeft();
                            cellProfileLeft.setActor(buttonProfileLeft);
                            changeAvatar(player1);
                        } //Updates the avatar on the right in menu
                        else {
                            prefs.putBoolean("isPlayer2Logged", true);
                            imagePlayer2 = imageProfile;
                            cellProfileRight.clearActor();
                            createButtonProfileRight();
                            cellProfileRight.setActor(buttonProfileRight);
                            changeAvatar(player2);
                        }
                        prefs.flush();
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
                prefs.putInteger("numPlayer", 2);
                // To know in which button the player is signing
                isProfileLeft = false;
                prefs.flush();
                buttonProfileRight.getImage().setColor(Color.WHITE);
                buttonProfileLeft.getImage().setColor(Color.DARK_GRAY);
                if (!prefs.getBoolean("isPlayer2Logged"))
                    showLoginWindow();
                else
                    showProfile(prefs.getInteger("numPlayer"));
            }
        });
    }

    private void createButtonProfileLeft() {
        buttonProfileLeft = new ImageButton(imageProfile);
        buttonProfileLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton1.play(prefs.getFloat("volume", 0.2f));
                prefs.putInteger("numPlayer", 1);
                isProfileLeft = true;
                prefs.flush();
                buttonProfileLeft.getImage().setColor(Color.WHITE);
                buttonProfileRight.getImage().setColor(Color.DARK_GRAY);
                if (!prefs.getBoolean("isPlayer1Logged"))

                    showLoginWindow();
                else
                    showProfile(prefs.getInteger("numPlayer"));
            }
        });
    }

    private void changeAvatar(HumanPlayer player) {
        if (hasImportedAvatar) {
            if (player.getAvatar().substring(0, player.getAvatar().length() - 5).equals("profile/" + "_" + player.getName()))
                Gdx.files.local(player.getAvatar()).delete();
            String extension = selectedFile.getName().substring(selectedFile.getName().length() - 4, selectedFile.getName().length());
            prefs.putString("pathImageProfile", "profile/" + "_" + player.getName() + extension);
            sourceImage.copyTo(Gdx.files.local(prefs.getString("pathImageProfile")));
        }
        if (hasChangedAvatar) {
            player.setAvatar(prefs.getString("pathImageProfile"));

            if (isPlayerInTabStats(player.getName()))
                updateAvatar(player.getName());
        }
    }

    private String validateEdit(TextField fieldCurrentPassword, HumanPlayer player, TextField fieldNewPassword1, TextField fieldNewPassword2) {
        String messageErrorEdit = "";

        if (fieldCurrentPassword.getText().equals("") && fieldNewPassword1.getText().equals("") && fieldNewPassword2.getText().equals(""))
            return messageErrorEdit;
        if (fieldCurrentPassword.getText() == null)
            return prefs.getString("INCORRECT_PASSWORD");
        if (!fieldCurrentPassword.getText().equals(player.getAccount().getPassword()))
            return prefs.getString("INCORRECT_PASSWORD");
        if (fieldNewPassword1.getText().length() < 4)
            return prefs.getString("INCORRECT_LENGTH_PASSWORD");
        if (!(fieldNewPassword1.getText().equals(fieldNewPassword2.getText())))
            return prefs.getString("PASSWORDS_NOT_MATCH");

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
        windowAvatarSelection.getTitleTable().padLeft(windowAvatarSelection.getWidth() / 2 - windowAvatarSelection.getTitleLabel().getWidth() / 2);

        final Table tableSelection = new Table();
        windowAvatarSelection.addActor(tableSelection);
        tableSelection.setFillParent(true);
        tableSelection.top().padTop(windowAvatarSelection.getHeight() * 13 / 100);

        hasImportedAvatar = false;
        prefs.putString("pathImageTmp", prefs.getString("pathImageProfile"));
        prefs.flush();
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
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG", "png", "jpg");
                fc.addChoosableFileFilter(filter);

                // affichage de la fenetre pour choisir l'image et teste si le bouton ok est presse

                int returnValue = fc.showOpenDialog(f);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fc.getSelectedFile();
                    Drawable imageImport = new TextureRegionDrawable(new TextureRegion(new Texture(selectedFile.getAbsolutePath())));
                    cellAvatarSelect.clearActor();
                    imageTmp = imageImport;
                    prefs.putString("pathImageTmp", selectedFile.getAbsolutePath());
                    prefs.flush();
                    buttonSelectionAvatar = new ImageButton(imageTmp);
                    cellAvatarSelect.setActor(buttonSelectionAvatar);
                    hasChangedAvatar = true;
                    hasImportedAvatar = true;
                    sourceImage = Gdx.files.absolute(prefs.getString("pathImageTmp"));
                    prefs.flush();
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
                prefs.putString("pathImageProfile", prefs.getString("pathImageTmp"));
                prefs.flush();

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
                prefs.putString("pathImageTmp", prefs.getString("pathImagePanda"));
                prefs.flush();

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
                prefs.putString("pathImageTmp", prefs.getString("pathImagePink"));
                prefs.flush();

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
                prefs.putString("pathImageTmp", prefs.getString("pathImageBanana"));
                prefs.flush();

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
                prefs.putString("pathImageTmp", prefs.getString("pathImageMustache"));
                prefs.flush();
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
                prefs.putString("pathImageTmp", prefs.getString("pathImageBurger"));
                prefs.flush();

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
                prefs.putString("pathImageTmp", prefs.getString("pathImageGirl"));
                prefs.flush();

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
                prefs.putString("pathImageTmp", prefs.getString("pathImageRobot"));
                prefs.flush();

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
                prefs.putString("pathImageTmp", prefs.getString("pathImagePenguin"));
                prefs.flush();

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
                prefs.putString("pathImageTmp", prefs.getString("pathImageBlue"));
                prefs.flush();

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
                prefs.putString("pathImageTmp", prefs.getString("pathImageSquid"));
                prefs.flush();

                buttonSelectionAvatar = new ImageButton(imageTmp);
                cellAvatarSelect.setActor(buttonSelectionAvatar);

                hasChangedAvatar = true;
            }
        });

        tableSelection.add(buttonSave).colspan(2).width(buttonImport.getWidth()).right();
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
                if (prefs.getInteger("numPlayer") == 1) {
                    resetButtonProfile(cellProfileLeft, player1, labelProfileLeft, "isPlayer1Logged");
                    saveStatsPlayer(player1);
                } else {
                    resetButtonProfile(cellProfileRight, player2, labelProfileRight, "isPlayer2Logged");
                    saveStatsPlayer(player2);
                }

                windowLogOut.remove();
                windowProfile.remove();
                enableButton(buttonPlay, buttonHall, buttonSettings, buttonExit, buttonProfileLeft, buttonProfileRight);
                resetButtonsProfileLooks();
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

    private void resetButtonProfile(Cell cell, HumanPlayer player, Label labelProfile, String string) {
        cell.clearActor();
        imageProfile = imageAnonymous;
        if (isProfileLeft) {
            createButtonProfileLeft();
            cell.setActor(buttonProfileLeft);
        } else {
            createButtonProfileRight();
            cell.setActor(buttonProfileRight);
        }
        labelProfile.setText("Not Logged");
        prefs.putBoolean(string, false);
        isProfileLeft = false;

    }

    public static void saveStatsPlayer(HumanPlayer player) {
        boolean isFound = isPlayerInTabStats(player.getName());
        if (isFound) {
            if (getScorePlayerInTabStats(player.getName()) == 0) {
                if (player.getGlobalStats().getScore() != 0)
                    prefs.putInteger("totalNumberPlayers", prefs.getInteger("totalNumberPlayers") + 1);
            }
            deletePlayer(player);

        } else {
            if (player.getGlobalStats().getScore() != 0)
                prefs.putInteger("totalNumberPlayers", prefs.getInteger("totalNumberPlayers") + 1);
        }
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
        prefs.flush();
    }

    private static GlobalStats setGlobalStats(HumanPlayer player) {
        GlobalStats globalStats = new GlobalStats();
        globalStats.setScore(player.getGlobalStats().getScore());
        globalStats.setRank(player.getGlobalStats().getRank());

        for (Map.Entry<String, Integer> entry : player.getGlobalStats().getStats().entrySet()) {
            String stat = entry.getKey();
            int value = entry.getValue();

            globalStats.getStats().put(stat, value);
        }
        return globalStats;
    }

    /**
     * réinitialise l'apparence des boutons grisés
     */
    private void resetButtonsProfileLooks() {
        buttonProfileLeft.getImage().setColor(Color.WHITE);
        buttonProfileRight.getImage().setColor(Color.WHITE);

    }

    private void updateAvatar(String name) {
        Iterator iter = tabPlayers.iterator();
        while (iter.hasNext()) {
            HumanPlayer player = (HumanPlayer) iter.next();
            if (player.getName().equals(name)) {
                player.setAvatar(prefs.getString("pathImageProfile"));
            }
        }
    }

    private static boolean isPlayerInTabStats(String name) {
        Iterator iter = tabPlayers.iterator();
        while (iter.hasNext()) {
            HumanPlayer player = (HumanPlayer) iter.next();
            if (player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static int getScorePlayerInTabStats(String name) {
        Iterator iter = tabPlayers.iterator();
        while (iter.hasNext()) {
            HumanPlayer player = (HumanPlayer) iter.next();
            if (player.getName().equals(name)) {
                return player.getGlobalStats().getScore();
            }
        }
        return 0;
    }

    private static void storePlayer(HumanPlayer player) {
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

    private static void deletePlayer(HumanPlayer player) {
        int i = 0;
        boolean isFound = false;
        while (i < tabPlayers.size() && !isFound) {
            HumanPlayer playerT = (HumanPlayer) tabPlayers.get(i);

            if (playerT.getName().equals(player.getName())) {
                tabPlayers.remove(i);
                isFound = true;
            }
            i++;
        }
    }

    private void recalculateRank() {
        for (int i = 0; i < tabPlayers.size(); i++) {
            HumanPlayer playerTab = (HumanPlayer) tabPlayers.get(i);
            playerTab.getGlobalStats().setRank(i + 1);
        }
    }

    private void showExit() {
        disableButton(buttonPlay, buttonSettings, buttonExit);
        if (prefs.getBoolean("isAccountEnabled"))
            disableButton(buttonHall, buttonProfileLeft, buttonProfileRight);
        windowExit.clear();
        windowExit.setSize(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4);
        windowExit.setPosition(SCREEN_WIDTH / 2 - windowExit.getWidth() / 2, SCREEN_HEIGHT / 2 - windowExit.getHeight() / 2);
        windowExit.setMovable(false);
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
                if (prefs.getBoolean("isPlayer1Logged"))
                    saveStatsPlayer(player1);

                if (prefs.getBoolean("isPlayer2Logged"))
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
                if (prefs.getBoolean("isAccountEnabled"))
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
        windowDelete.setSize(SCREEN_HEIGHT * 80 / 100, SCREEN_HEIGHT * 2 / 5);
        windowDelete.setPosition(SCREEN_WIDTH / 2 - windowDelete.getWidth() / 2, SCREEN_HEIGHT / 2 - windowDelete.getHeight() / 2);
        windowDelete.setMovable(false);
        windowDelete.getTitleTable().padLeft(windowDelete.getWidth() / 2 - windowDelete.getTitleLabel().getWidth() / 2);

        Table table = new Table();
        windowDelete.addActor(table);
        table.setFillParent(true);

        Label labelWarning1 = new Label(lang.get("label_all_data_will_be_erased"), skinSgx, "white");
        Label labelWarning2 = new Label(lang.get("label_including_achievements_in_hof"), skinSgx, "white");
        Label labelPassword = new Label(lang.get("label_password"), skinSgx, "white");
        Label noMessagePassword = new Label("", skinSgx, "white");
        messageErrorDeletePassword.setColor(Color.RED);


        fieldPasswordDelete.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if ((key == '\r' || key == '\n')) {
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
            }
        });
        setPasswordMode(fieldPasswordDelete);
        stage.setKeyboardFocus(fieldPasswordDelete);

        TextButton buttonConfirm = new TextButton(lang.get("button_confirm"), skinSgx, "big");
        buttonConfirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                HumanPlayer player;
                if (isProfileLeft)
                    player = player1;
                else
                    player = player2;

                if (!player.getAccount().getPassword().equals(fieldPasswordDelete.getText())) {
                    cellErrorPasswordDelete.clearActor();
                    cellErrorPasswordDelete.setActor(messageErrorDeletePassword);
                } else {
                    // suppression du joueur dans la table des statistiques

                    cellErrorPasswordDelete.clearActor();
                    cellErrorPasswordDelete.setActor(noMessageError9);
                    resetField(fieldPasswordDelete);

                    deletePlayer(player);
                    prefs.putInteger("totalNumberPlayers", prefs.getInteger("totalNumberPlayers") - 1);
                    prefs.flush();
                    recalculateRank();
                    if (isProfileLeft)
                        resetButtonProfile(cellProfileLeft, player, labelProfileLeft, "isPlayer1Logged");
                    else
                        resetButtonProfile(cellProfileRight, player2, labelProfileRight, "isPlayer2Logged");
                    windowDelete.remove();
                    windowProfile.remove();
                    enableButton(buttonPlay, buttonHall, buttonSettings, buttonExit, buttonProfileLeft, buttonProfileRight);
                    resetButtonsProfileLooks();
                }


            }
        });
        TextButton buttonCancel = new TextButton(lang.get("button_cancel"), skinSgx, "big");
        buttonCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundButton2.play(prefs.getFloat("volume", 0.2f));
                cellErrorPasswordDelete.clearActor();
                cellErrorPasswordDelete.setActor(noMessageError9);
                enableButton(buttonEdit, buttonLogOut, buttonDelete, buttonBack);
                resetField(fieldPasswordDelete);
                windowDelete.remove();

            }
        });
        table.padTop(windowDelete.getWidth() / 20);
        table.add(labelWarning1).colspan(2);
        table.row();
        table.add(labelWarning2).colspan(2);
        table.row().padTop(windowDelete.getHeight() * 11 / 100);
        table.add(labelPassword);
        labelPassword.setAlignment(1);
        table.add(fieldPasswordDelete).fill().width(windowDelete.getWidth() * 60 / 100);

        table.row();
        table.add(noMessagePassword).colspan(2).fill();
        cellErrorPasswordDelete = table.getCell(noMessagePassword);
        table.row().padTop(windowDelete.getHeight() * 6 / 100);
        table.add(buttonCancel).left().padLeft(SCREEN_WIDTH * 2 / 100).width(windowDelete.getWidth() / 4);
        table.add(buttonConfirm).right().padRight(SCREEN_WIDTH * 2 / 100).width(windowDelete.getWidth() / 4);

        stage.addActor(windowDelete);
    }
}