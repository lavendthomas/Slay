package be.ac.umons.slay.g02;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;


public class Slay extends ApplicationAdapter {
	private TiledMap map;
	private TiledMapRenderer renderer;
	OrthographicCamera camera;

	
	@Override
	public void create () {

	    map = new TmxMapLoader().load("levels/level1.tmx");

		renderer = new HexagonalTiledMapRenderer(map, .9f);

		//Source : http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
		MapProperties properties = map.getProperties(); //Récupère propriétés de la carte

		int tileWidth         = properties.get("tilewidth", Integer.class);
		int tileHeight        = properties.get("tileheight", Integer.class);

        int mapWidthInTiles   = properties.get("width", Integer.class);
        int mapHeightInTiles  = properties.get("height", Integer.class);

        int width = mapWidthInTiles * tileWidth;
        int height = mapHeightInTiles * tileHeight;

		camera = new OrthographicCamera(width, height);
        camera.position.set(width/2, height/2, 0);
        camera.update();


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//camera.update();
		renderer.setView(camera);
		renderer.render();

	}

	@Override
	public void dispose () {
		map.dispose();
	}



}
