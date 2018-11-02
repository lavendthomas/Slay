package be.ac.umons.slay.g02;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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

public class Slay extends ApplicationAdapter implements InputProcessor, GestureDetector.GestureListener {
	TiledMap map;
	TiledMapRenderer renderer;
	OrthographicCamera camera;


	//Cooridnates on screen used for camera movement
	int oldX;
	int oldY;
	boolean dragging;
	
	@Override
	public void create () {
		/*
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		*/
		Gdx.input.setInputProcessor(this); // Handle use input


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
	}

	@Override
	public void render () {
		/*
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		*/
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

	}

	@Override
	public void dispose () {
		/*
		batch.dispose();
		img.dispose();
		*/
		map.dispose();
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
		Vector2 trans = new Vector2((float) ((oldX - screenX) * Math.sqrt((double)camera.zoom)), (float)((screenY - oldY) * Math.sqrt((double)camera.zoom)));
		camera.translate(trans);
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
		Gdx.app.log("test", "scrolled");
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
		return false;
	}

	@Override
	public void pinchStop() {

	}
}
