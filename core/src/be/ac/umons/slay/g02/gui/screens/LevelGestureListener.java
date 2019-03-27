package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class LevelGestureListener implements GestureDetector.GestureListener {
    OrthographicCamera camera;
    GameScreen game;

    LevelGestureListener(GameScreen gs, OrthographicCamera c) {
        camera = c;
        game = gs;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        game.onTap(x,y);
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
        camera.translate(- camera.zoom * deltaX, camera.zoom * deltaY);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        game.changeZoom((initialDistance - distance) * camera.zoom / 20000);
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
