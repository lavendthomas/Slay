package be.ac.umons.slay.g02.tests;

import com.badlogic.gdx.Gdx;

import org.junit.BeforeClass;
import org.junit.Test;


import com.badlogic.gdx.Application;

import java.util.List;

import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.FileFormatException;
import be.ac.umons.slay.g02.level.Level;
import be.ac.umons.slay.g02.level.LevelLoader;
import be.ac.umons.slay.g02.level.Tile;


import static org.junit.Assert.*;

public class LevelTests {

    @BeforeClass
    public void init() {

    }

    @Test
    public void mergeSoldier() {

        Level lvl = null;
        try {
            lvl = (Level) LevelLoader.load("test_01").getLevel();
        } catch (FileFormatException e) {
            fail("Level could not be loaded");
        }

        lvl.move(new Coordinate(4,6), new Coordinate(4,6));

        assertEquals("L1",lvl.get(new Coordinate(4,6)).getEntity());

    }

    @Test
    public void mergeSoliderDifferentLevels() {

        Level lvl = null;
        try {
            lvl = (Level) LevelLoader.load("test_01").getLevel();
        } catch (FileFormatException e) {
            fail("Level could not be loaded");
        }

        //TODO

    }


    @Test
    public void attackCapital() {
        /*
        Test that if we attack a capital and the territory is split in 2 territories,
        then a new capital will be created
         */
        Level lvl = null;
        try {
            lvl = (Level) LevelLoader.load("test_01").getLevel();
        } catch (FileFormatException e) {
            fail("Level could not be loaded");
        }

        lvl.move(new Coordinate(5,6), new Coordinate(9,5));
        lvl.nextTurn();
        lvl.move(new Coordinate(16,4), new Coordinate(14,5));
        lvl.nextTurn();
        lvl.move(new Coordinate(9,5), new Coordinate(13,4));
        lvl.nextTurn();
        lvl.move(new Coordinate(14,5), new Coordinate(13,6));
        lvl.nextTurn();
        lvl.move(new Coordinate(14,4),new Coordinate(14,4));
        lvl.nextTurn();
        lvl.nextTurn();
        lvl.move(new Coordinate(14,4), new Coordinate(15,5));

        // Check that the right territory has a capital
        //assertEquals(1,lvl.get(new Coordinate(14, 5)).getTerritory().getCapitals().size());
        // Check that the left territory has a capital()
        //assertEquals(1,lvl.get(new Coordinate(16, 4)).getTerritory().getCapitals().size());

    }



}
