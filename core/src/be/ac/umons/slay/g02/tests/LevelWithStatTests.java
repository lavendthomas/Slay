package be.ac.umons.slay.g02.tests;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.gui.Main;
import be.ac.umons.slay.g02.level.Level;
import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.Statistics;
import be.ac.umons.slay.g02.players.StatsLoader;

import static org.junit.Assert.assertEquals;

public class LevelWithStatTests {

    static ArrayList tabPlayers;
    static Level level;
    static int currentLL0 = 0;
    static int currentLL1 = 0;
    static int currentGL0 = 0;
    static int currentGL1 = 0;
    static Soldier soldier;
    static HumanPlayer player;

    /**
     * Creates a player, a level and a soldier, then merges two units of player
     */
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
    }


    /**
     * Tests if the counter currentLostL. (for global statistics) is correctly updated
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
     * Tests if the counter currentLostL. (for statistics of a specific level) is correctly updated
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
     * Checks that the two L0 are removed, i.e. the counter of L0 is subtracted from two (in
     * global statistics)
     */
    @Test
    public void testUpdatePlayerStatsMergeGlobalOld() {
        assertEquals((int) player.getGlobalStats().getCurrentStats().get(Statistics.CURRENT_L0),
                currentGL0 - 2);
    }

    /**
     * Checks that the L1 is added, i.e. the counter of L1 is increased by one (in global
     * statistics)
     */
    @Test
    public void testUpdatePlayerStatsMergeGlobalNew() {
        assertEquals((int) player.getGlobalStats().getCurrentStats().get(Statistics.CURRENT_L1),
                currentGL1 + 1);
    }

    /**
     * Checks that the two L0 are removed, i.e. the counter of L0 is subtracted from two (in
     * statistics of the level)
     */
    @Test
    public void testUpdatePlayerStatsMergeLevelOld() {
        assertEquals((int) player.getListLevelStats(1).getCurrentStats().get(Statistics.CURRENT_L0),
                currentLL0 - 2);
    }

    /**
     * Checks that the L1 is added, i.e. the counter of L1 is increased by one (in statistics of
     * the level)
     */
    @Test
    public void testUpdatePlayerStatsMergeLevelNew() {
        assertEquals((int) player.getListLevelStats(1).getCurrentStats().get(Statistics.CURRENT_L1),
                currentLL1 + 1);
    }
}
