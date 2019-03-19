package be.ac.umons.slay.g02.tests;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import org.junit.AfterClass;

/*import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import static org.mockito.Mockito.mock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.backends.headless.mock.audio.MockAudio;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.backends.headless.mock.input.MockInput;*/

// test pour initialiser gdx app, pour tester Menu, etc

public class GameTest {
    // This is our "test" application
    private static Application application;

    // Before running any tests, initialize the application with the headless backend
    public static final void main(String[] arg) {
        // Note that we don't need to implement any of the listener's methods
    /*    application = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        });*/

        // Use Mockito to mock the OpenGL methods since we are running headlessly
        //      Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;


    }


    // After we are done, clean up the application
    @AfterClass
    public static void cleanUp() {
        // Exit the application first
        application.exit();
        application = null;
    }
}



