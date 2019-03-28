package be.ac.umons.slay.g02.tests;

import com.badlogic.gdx.Gdx;

import org.junit.BeforeClass;
import org.junit.Test;


import com.badlogic.gdx.Application;

import java.io.File;
import java.util.ArrayList;

import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.gui.Main;
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
import be.ac.umons.slay.g02.players.Statistics;
import be.ac.umons.slay.g02.players.StatsLoader;

import static org.junit.Assert.*;

/**
 *  Class testing ... TODO
 */
public class LevelTests {
    static ArrayList tabPlayers;
    static Level level;
    static int currentLL0 = 0;
    static int currentLL1 = 0;
    static int currentGL0 = 0;
    static int currentGL1 = 0;
    static Soldier soldier;
    static HumanPlayer player;

    /**
     *  Creates a player, a level and a soldier, then merges two units of player
     */
    /*
    @BeforeClass
    public static void setUp() {
        Main.isInTest = true;

        // Creates a player
        StatsLoader statsLoader = new StatsLoader();
        tabPlayers = statsLoader.createTab();
        player = (HumanPlayer) tabPlayers.get(0);

        // Creates a Level
        level = new Level(0, 0);

        // Creates a soldier of type L0
        soldier = new Soldier(SoldierLevel.L0);

        // Gets the value of counters for L0 and L1 from global statistics
        currentGL0 = player.getGlobalStats().getCurrentStats().get(Statistics.CURRENT_L0);
        currentGL1 = player.getGlobalStats().getCurrentStats().get(Statistics.CURRENT_L1);

        // Gets the value of counters for L0 and L1 from statistics of level 1
        currentLL0 = player.getListLevelStats(1).getCurrentStats().get(Statistics.CURRENT_L0);
        currentLL1 = player.getListLevelStats(1).getCurrentStats().get(Statistics.CURRENT_L1);

        // Simulates the merge of two L0 belonging to player, it creates one L1
        level.updatePlayerStatsMerge(player, 0, 0, 1);
    }*/

    /**
     * //TODO
     *
     * @return
     */
    static Playable loadLvl() {
        Playable level = new Level(5, 5);
        Player p1 = new HumanPlayer("P1", Colors.C1);
        p1.setAvatar("profile" + File.separator + "anonymous.png");
        Player p2 = new HumanPlayer("P2", Colors.C2);
        p2.setAvatar("profile" + File.separator + "anonymous.png");
        Player[] players = {p1, p2};
        level.setPlayers(players);

        Coordinate c11 = new Coordinate(1, 1);
        Coordinate c12 = new Coordinate(1, 2);
        Coordinate c13 = new Coordinate(1, 3);
        Coordinate c21 = new Coordinate(2, 1);
        Coordinate c22 = new Coordinate(2, 2);
        Coordinate c23 = new Coordinate(2, 3);
        Coordinate c31 = new Coordinate(3, 1);
        Coordinate c32 = new Coordinate(3, 2);
        Coordinate c33 = new Coordinate(3, 3);

        Tile t11 = new Tile(TileType.NEUTRAL);
        Tile t12 = new Tile(TileType.NEUTRAL);
        Tile t13 = new Tile(TileType.NEUTRAL);
        Tile t21 = new Tile(TileType.NEUTRAL);
        Tile t22 = new Tile(TileType.NEUTRAL);
        Tile t23 = new Tile(TileType.NEUTRAL);
        Tile t31 = new Tile(TileType.NEUTRAL);
        Tile t32 = new Tile(TileType.NEUTRAL);
        Tile t33 = new Tile(TileType.NEUTRAL);

        t13.setTerritory(new Territory(p1, t13, t22, t23));
        t22.setTerritory(new Territory(p1, t13, t22, t23));
        t23.setTerritory(new Territory(p1, t13, t22, t23));
        t33.setTerritory(new Territory(p1, t33));

        t21.setTerritory(new Territory(p2, t21, t31, t32));
        t31.setTerritory(new Territory(p2, t21, t31, t32));
        t32.setTerritory(new Territory(p2, t21, t31, t32));

        level.set(t11, c11);
        level.set(t12, c12);
        level.set(t13, c13);
        level.set(t21, c21);
        level.set(t22, c22);
        level.set(t23, c23);
        level.set(t31, c31);
        level.set(t32, c32);
        level.set(t33, c33);

        Tile water = new Tile(TileType.WATER);
        for(int i = 0; i < level.width(); i++) {
            for(int j = 0; j < level.height(); j++) {
                if (i == 0 || i == level.width() - 1 || j == 0 || j == level.height()-1)
                    level.set(water, new Coordinate(i, j));
            }

        }

        Soldier s0 = new Soldier(SoldierLevel.fromLevel(0), false);
        Soldier s1 = new Soldier(SoldierLevel.fromLevel(1), false);

        level.set(s0, c21);
        level.set(s0, c32);
        level.set(s1, c22);
        level.set(StaticEntity.CAPITAL, c13);
        level.set(StaticEntity.CAPITAL, c31);
        level.get(c13).getTerritory().setCoins(200);
        level.get(c31).getTerritory().setCoins(200);

        level.mergeTerritories();

        return level;
    }

    /**
     *  //TODO
     */
    @Test
    public void mergeSoldier() {
        Coordinate c0 = new Coordinate(2, 1);
        Coordinate c1 = new Coordinate(3, 2);
        Playable level = loadLvl();
        level.nextTurn();
        level.move(c0, c1);
        assertEquals("L1",level.get(c1).getEntity().getName());
    }

    /**
     *  //TODO
     */
    @Test
    public void attackSoldier() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 2);
        Coordinate c1 = new Coordinate(2, 1);

        level.move(c0, c1);
        assertEquals("L1",level.get(c1).getEntity().getName());
    }

    @Test
    public void conquerEnemyTerritory() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 2);
        Coordinate c1 = new Coordinate(2, 1);

        level.move(c0, c1);
        //TODO Comportement bizarre après le split dans move
        assertEquals("P1",level.get(c1).getTerritory().getOwner().getName());
    }

    @Test
    public void cantConquerEnemyTerritory() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(3, 2);
        Coordinate c1 = new Coordinate(3, 3);

        assertEquals(false, level.canMove(c0, c1));
    }



    @Test
    public void conquerNeutralTerritory() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 2);
        Coordinate c1 = new Coordinate(1, 2);
        level.move(c0, c1);
        assertEquals("P1",level.get(c1).getTerritory().getOwner().getName());
    }

    @Test
    public void cantAttack() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 1);
        Coordinate c1 = new Coordinate(2, 2);
        assertEquals(false,level.canMove(c0, c1));
    }



    /**
     *  Tests if the counter currentLostL. (for global statistics) is correctly updated
     */
    @Test
    public void testUpdatePlayerStatsLostGlobal() {
        // Get the number of lost unit L0 - in the global statistics
        int currentLostL0 = player.getGlobalStats().getCurrentStats().get(Statistics.CURRENT_LOST_L0);

        // When a soldier L0 is killed, this number is increased by one
        level.updatePlayerStatsLost(player, soldier);

        // Checks if it is true
        assertEquals((int) player.getGlobalStats().getCurrentStats().get(Statistics.CURRENT_LOST_L0),
                currentLostL0 + 1);
    }

    /**
     *  Tests if the counter currentLostL. (for statistics of a specific level) is correctly updated
     */
    @Test
    public void testUpdatePlayerStatsLostLevel() {
        // Get the number of lost unit L0 - in the statistics of level 1
        int currentLostL0 = player.getListLevelStats(1).getCurrentStats().get(Statistics.CURRENT_LOST_L0);

        // When a soldier L0 is killed, this number is increased by one
        level.updatePlayerStatsLost(player, soldier);

        // Checks if it is true
        assertEquals((int) player.getListLevelStats(1).getCurrentStats().get(Statistics.CURRENT_LOST_L0),
                currentLostL0 + 1);
    }

    /**
     *  Checks that the two L0 are removed, i.e. the counter of L0 is subtracted from two (in
     *  global statistics)
     */
    @Test
    public void testUpdatePlayerStatsMergeGlobalOld() {
        assertEquals((int) player.getGlobalStats().getCurrentStats().get(Statistics.CURRENT_L0),
                currentGL0 - 2);
    }

    /**
     *  Checks that the L1 is added, i.e. the counter of L1 is increased by one (in global
     *  statistics)
     */
    @Test
    public void testUpdatePlayerStatsMergeGlobalNew() {
        assertEquals((int) player.getGlobalStats().getCurrentStats().get(Statistics.CURRENT_L1),
                currentGL1 + 1);
    }

    /**
     *  Checks that the two L0 are removed, i.e. the counter of L0 is subtracted from two (in
     *  statistics of the level)
     */
    @Test
    public void testUpdatePlayerStatsMergeLevelOld() {
        assertEquals((int) player.getListLevelStats(1).getCurrentStats().get(Statistics.CURRENT_L0),
                currentLL0 - 2);
    }

    /**
     *  Checks that the L1 is added, i.e. the counter of L1 is increased by one (in statistics of
     *  the level)
     */
    @Test
    public void testUpdatePlayerStatsMergeLevelNew() {
        assertEquals((int) player.getListLevelStats(1).getCurrentStats().get(Statistics.CURRENT_L1),
                currentLL1 + 1);
    }
}