package be.ac.umons.slay.g02;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;


public class Slay extends ApplicationAdapter {

    private TiledMap map;
    private HexagonalTiledMapRenderer renderer;
    OrthographicCamera camera;


    @Override
    public void create() {
        map = new TmxMapLoader().load("worlds/g02_02.tmx");

        renderer = new HexagonalTiledMapRenderer(map, 1);

        MapProperties prop = map.getProperties();
        int w = prop.get("width", Integer.class);
        int h = prop.get ("height", Integer.class);

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
	}

	@Override
	public void dispose () {
		map.dispose();
	}



}
