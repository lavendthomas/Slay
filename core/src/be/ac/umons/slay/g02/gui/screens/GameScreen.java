package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.Point;

import be.ac.umons.slay.g02.gui.Main;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Level;
import be.ac.umons.slay.g02.level.LevelLoader;

import static be.ac.umons.slay.g02.gui.Main.SCREEN_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.SCREEN_WIDTH;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_HEIGHT;
import static be.ac.umons.slay.g02.gui.Main.VIRTUAL_WIDTH;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

// classe qui affiche l'interface pendant une partie
public class GameScreen implements Screen {

    private Stage stage;
    private Game game;

    private TiledMap map;
    private Level level;
    private HexagonalTiledMapRenderer renderer;
    OrthographicCamera camera;

    private int w;
    private int h;
    private int tileW;
    private int tileH;
    private int size;

    private TiledMapTileLayer background;
    private TiledMapTileLayer territories;
    private TiledMapTileLayer entities;
    private TiledMapTileSet set;


    public GameScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        //Chargement de la map et du Level associé
        LevelLoader.Map lvlLoader = LevelLoader.load("g02_01");
        level = lvlLoader.getLevel();
        map = lvlLoader.getMap();

        // Chargement des couches et du tileset
        background = (TiledMapTileLayer) map.getLayers().get("Background");
        territories = (TiledMapTileLayer) map.getLayers().get("Territories");
        entities = (TiledMapTileLayer) map.getLayers().get("Entities");
        set = map.getTileSets().getTileSet("tileset");


        // bouton BACK
        TextButton buttonBack = new TextButton("Back",Main.skinMain);
        buttonBack.setPosition(Main.SCREEN_WIDTH/2-buttonBack.getWidth()/2-15,2*buttonBack.getHeight());
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.soundButton2.play(0.2f);
                game.setScreen(new Menu(game));
            }
        });
        stage.addActor(buttonBack);

        // Chargement de la carte dans le renderer
        renderer = new HexagonalTiledMapRenderer(map, 1);

        // Essai modification d'une tuile
        background.getCell(0,0).setTile(set.getTile(1));

        //Positionnement de la caméra selon la taille de la map
        MapProperties prop = map.getProperties();
        w = prop.get("width", Integer.class);
        h = prop.get ("height", Integer.class);
        tileW = prop.get("tilewidth", Integer.class);
        tileH = prop.get("tileheight", Integer.class);
        size = tileW / 2;

        camera = new OrthographicCamera(w * tileW, h * tileH);
        camera.position.set((w * tileW)/2, (h * tileH)/2, 0);
        camera.update();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        stage.draw();
        stage.act();


        //récupère position clic gauche de souris
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            System.out.println("x " + x + " y " + y);
            Hex hex = pixelToHex(x, y);
            background.getCell(hex.q , hex.r).setTile(set.getTile(1));
        }
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        Main.skinMain.getFont("button").getData().setScale(SCREEN_WIDTH*0.8f/VIRTUAL_WIDTH,SCREEN_HEIGHT*0.8f/VIRTUAL_HEIGHT);
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
        Main.skinMain.dispose();
//      pm.dispose();
//      Main.soundButton1.dispose();
        Main.soundButton2.dispose();
        stage.dispose();
    }


    private Hex pixelToHex (int xp, int yp) {
        yp = SCREEN_HEIGHT - yp;
        int q =((2*xp)/3)/size;
        int r = (int) ((((-xp)/3) + ((sqrt(3)/3)*yp)) / size);
        System.out.println("q " + q + " r " + r);
        return hex_round(new Hex(q, r));
    }

    private Hex hex_round (Hex hex) {
        return cube_to_axial(cube_round(axial_to_cube(hex)));
    }

    private Hex cube_to_axial (Cube cube) {
        return new Hex(cube.x, cube.z);
    }

    private  Cube axial_to_cube (Hex hex) {
        int x = hex.q;
        int z = hex.r;
        int y = -x-z;
        return new Cube(x, y, z);
    }

    private Cube cube_round (Cube cube) {
        int rx = round(cube.x);
        int ry = round(cube.y);
        int rz = round(cube.z);

        int x_diff = abs(rx - cube.x);
        int y_diff = abs(ry - cube.y);
        int z_diff = abs(rz - cube.z);

        if ((x_diff > y_diff) && (x_diff > z_diff)) {
            rx = -ry-rz;
        }
        else if (y_diff > z_diff) {
            ry = -rx-rz;
        }
        else {
            rz = -rx-ry;
        }
        return new Cube(rx, ry, rz);
    }

    private class Hex {
        int q;
        int r;

        private Hex (int q, int r) {
            this.q = q;
            this.r = r;
        }
    }

    private class Cube {
        int x;
        int y;
        int z;

        private Cube (int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

}

