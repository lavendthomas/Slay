package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import be.ac.umons.slay.g02.gui.Main;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_WIDTH;

// classe qui affiche l'interface pendant une partie
public class GameScreen implements Screen, InputProcessor, GestureDetector.GestureListener {
    private Stage stage;
    private Game game;

    TiledMap map;
    TiledMapRenderer renderer;
    OrthographicCamera camera;

    //Coordinates on screen used for camera movement
    int oldX;
    int oldY;
    boolean dragging;

    public GameScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

 /*       Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont fontRainbow = new BitmapFont(Gdx.files.internal("skins/rainbow/font-button-export.fnt"),
                Gdx.files.internal("skins/rainbow/rainbow-ui.png"), false);
        labelStyle.font = fontRainbow;
        labelStyle.fontColor = Color.RED;
*/

/*        // change cursor aspect
        pm = new Pixmap(Gdx.files.internal("cursors/cursor_2.png"));
        // x = pm.getWidth()/2 et y = pm.getHeight()/2 si on veut que ca pointe au centre du curseur
        // = 0 ca pointe au bout de la fleche
        xHotSpot = 0;
        yHotSpot = 0;
        cursor = Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot);
        Gdx.graphics.setCursor(cursor);
*/

        map = new TmxMapLoader().load("levels/hex_map.tmx");

        renderer = new HexagonalTiledMapRenderer(map, 2f);

        // Source : http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
        MapProperties properties = map.getProperties();
        int tileWidth         = properties.get("tilewidth", Integer.class);
        int tileHeight        = properties.get("tileheight", Integer.class);
        int mapWidthInTiles   = properties.get("width", Integer.class);
        int mapHeightInTiles  = properties.get("height", Integer.class);
        int mapWidthInPixels  = mapWidthInTiles  * tileWidth;
        int mapHeightInPixels = mapHeightInTiles * tileHeight;


        camera = new OrthographicCamera(3200.f, 1800.f);
        camera.position.x = mapWidthInPixels * .5f;
        camera.position.y = mapHeightInPixels * .35f;


        MapLayer layer1 = map.getLayers().get(0);
        layer1.setVisible(true);

        MapLayer layer2 = map.getLayers().get(1);
        layer2.setVisible(true);



        // bouton BACK
        TextButton buttonBack = new TextButton("Back",Main.skinRainbow);
        buttonBack.setPosition(Main.SCREEN_WIDTH/2-buttonBack.getWidth()/2-15,2*buttonBack.getHeight());
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.soundButton2.play(0.2f);
                game.setScreen(new Menu(game));
            }
        });

        stage.addActor(buttonBack);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();


        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            //Gdx.app.log("test", "left");
            camera.translate(-1, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(1, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 1, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            camera.zoom += 0.02;
        }


        renderer.setView(camera);
        renderer.render();


        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        Main.skinRainbow.getFont("button").getData().setScale(SCREEN_WIDTH*0.8f/VIRTUAL_WIDTH,SCREEN_HEIGHT*0.8f/VIRTUAL_HEIGHT);
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
//      cursor.dispose();
        map.dispose();
        Main.skinRainbow.dispose();
//      pm.dispose();
//      Main.soundButton1.dispose();
        Main.soundButton2.dispose();
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        Gdx.app.log("test", "keyDown");
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        Gdx.app.log("test", "keyUp");
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("test", "touchDown");
        oldX = screenX;
        oldY = screenY;
        dragging = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Gdx.app.debug("test", "touchUp");
        if (button != Input.Buttons.LEFT || pointer > 0) {
            return false;
        }
        dragging = false;

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Gdx.app.log("test", "X: " +screenX + " Y: " + screenY);
        if (!dragging) {
            return false;
        }
        Gdx.app.log("test", "Zoom : " +camera.zoom);
        Vector3 trans = new Vector3((float) ((oldX - screenX) * Math.sqrt((double)camera.zoom)), (float)((screenY - oldY) * Math.sqrt((double)camera.zoom)), 0);
        camera.translate(trans);
        //camera.unproject(trans);
        oldX = screenX;
        oldY = screenY;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.zoom += ((float) amount / 10);
        return true;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        camera.zoom += distance - initialDistance;
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        Gdx.app.log("test", "pinch to zoom : " +initialPointer1.toString());
        return false;
    }

    @Override
    public void pinchStop() {
        Gdx.app.log("test", "pinchStop");
    }
}

