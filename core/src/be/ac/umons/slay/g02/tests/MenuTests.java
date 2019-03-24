package be.ac.umons.slay.g02.tests;

import com.badlogic.gdx.Game;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import be.ac.umons.slay.g02.gui.Main;
import be.ac.umons.slay.g02.gui.screens.Menu;
import be.ac.umons.slay.g02.players.StatsLoader;

import static be.ac.umons.slay.g02.gui.Main.prefs;
import static org.junit.Assert.assertEquals;

public class MenuTests {

    public Menu menu;
    public Main main;
    private Game game;

    public static ArrayList tabPlayers;


    @Before
    public void setUp() throws Exception {
        main = new Main();
        main.create();
        menu = new Menu(main);
        //   menu= (Menu)main.getScreen();
        //   menu = new Menu();
        // remplissage de la table des joueurs

        StatsLoader statsLoader = new StatsLoader();
        tabPlayers = statsLoader.createTab();
    }


    @Test
    public void name() {
    }

    @Test
    public void validatePlayerTests() {
        String username = "titi";
        String newPassword1 = "e";
        String newPassword2 = "";
        String message = "";
        message = menu.validatePlayer(username, newPassword1, newPassword2);
        assertEquals(message, prefs.getBoolean("INCORRECT_LENGTH_PASSWORD"), 1);

    }
}
