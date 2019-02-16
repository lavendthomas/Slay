package be.ac.umons.slay.g02;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;

import be.ac.umons.slay.g02.level.Level;
import be.ac.umons.slay.g02.level.LevelLoader;

public class Slay extends ApplicationAdapter implements ApplicationListener {

    private TiledMap map;
    private HexagonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    private TiledMapTileSet tileset;


    @Override
    public void create() {

        LevelLoader.Map m = LevelLoader.load("g02_01");
        Level level = m.getLevel();
        map = m.getMap();
        renderer = new HexagonalTiledMapRenderer(map, 1);
        tileset = map.getTileSets().getTileSet(0);



        MapProperties prop = map.getProperties();
        int w = prop.get("width", Integer.class);
        int h = prop.get ("height", Integer.class);

        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();


        camera = new OrthographicCamera(w * 64, h * 64);
        camera.position.set((w * 64)/2, (h * 64)/2, 0);
        camera.update();
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.setView(camera);
		renderer.render();
        TiledMapTile tile = tileset.getTile(0);
        if (Gdx.input.justTouched()) {
            System.out.println(Gdx.input.getX());
            System.out.println(Gdx.input.getY());
        }
	}


	@Override
	public void dispose () {
		map.dispose();
	}

}
