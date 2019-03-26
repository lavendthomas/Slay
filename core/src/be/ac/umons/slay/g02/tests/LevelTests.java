package be.ac.umons.slay.g02.tests;

import com.badlogic.gdx.Gdx;

import org.junit.BeforeClass;
import org.junit.Test;


import com.badlogic.gdx.Application;

import java.io.File;
import java.util.List;

import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.FileFormatException;
import be.ac.umons.slay.g02.level.Level;
import be.ac.umons.slay.g02.level.LevelLoader;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Territory;
import be.ac.umons.slay.g02.level.Tile;
import be.ac.umons.slay.g02.level.TileType;
import be.ac.umons.slay.g02.players.Colors;
import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.Player;


import static org.junit.Assert.*;

public class LevelTests {

    static Playable loadLvl() {

        Playable level = new Level(5, 5);
        Tile back = new Tile(TileType.NEUTRAL);
        Tile water = new Tile(TileType.WATER);
        for(int i = 0; i < level.width(); i++) {
            for(int j = 0; j < level.height(); j++) {
                if(i == 0 || i == level.width()-1 || j == 0 || j == level.height()) {
                    level.set(water, new Coordinate(i, j));
                } else {
                    level.set(back, new Coordinate(i, j));
                }
            }
        }
        Player p1 = new HumanPlayer("P1", Colors.C1);
        p1.setAvatar("profile" + File.separator + "anonymous.png");
        Player p2 = new HumanPlayer("P2", Colors.C2);
        p2.setAvatar("profile" + File.separator + "anonymous.png");
        Player[] players = {p1, p2};
        level.setPlayers(players);

        Coordinate c1 = new Coordinate(1, 3);
        level.get(c1).setTerritory(new Territory(p1, level.get(c1)));

        Coordinate c2 = new Coordinate(2, 2);
        level.get(c2).setTerritory(new Territory(p1, level.get(c2)));

        Coordinate c3 = new Coordinate(2, 3);
        level.get(c3).setTerritory(new Territory(p1, level.get(c3)));

        Coordinate c4 = new Coordinate(2, 1);
        level.get(c4).setTerritory(new Territory(p2, level.get(c4)));

        Coordinate c5 = new Coordinate(3, 1);
        level.get(c5).setTerritory(new Territory(p2, level.get(c5)));

        Coordinate c6 = new Coordinate(3, 2);
        level.get(c6).setTerritory(new Territory(p2, level.get(c6)));

        Territory t1 = level.get(c1).getTerritory();
        t1.setCapital(level.get(c1));
        t1.setCoins(20);

        Territory t2 = level.get(c5).getTerritory();
        t2.setCapital(level.get(c5));
        t2.setCoins(20);

        Soldier s1 = new Soldier(SoldierLevel.fromLevel(0), false);
        level.set(s1, c4);

        Soldier s2 = new Soldier(SoldierLevel.fromLevel(0), false);
        level.set(s2, c6);

        Soldier s3 = new Soldier(SoldierLevel.fromLevel(1), false);
        level.set(s3, c2);

        return level;

    }

    @Test
    public void mergeSoldier() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 1);
        Coordinate c1 = new Coordinate(3, 2);
        level.move(c0, c1);
        assertEquals("L1",level.get(c1).getEntity().getName());
    }

    @Test
    public void attackSoldier() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 2);
        Coordinate c1 = new Coordinate(2, 1);
        level.move(c0, c1);
        assertEquals("L1",level.get(c1).getEntity().getName());
    }

    @Test
    public void attackCapital() {
        /*
        Test that if we attack a capital and the territory is split in 2 territories,
        then a new capital will be created
         */
        Level lvl = null;
        try {
            lvl = (Level) LevelLoader.load("test_01", 1).getLevel();
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
