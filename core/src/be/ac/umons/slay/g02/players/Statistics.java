package be.ac.umons.slay.g02.players;

import java.util.HashMap;
import java.util.LinkedHashMap;

import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.gui.screens.LevelSelection;

/**
 * Class containing the statistics used to calculate the player's score and the ones to be displayed
 * in game
 */
public class Statistics {
    private static final String LOST_ = "Lost_";
    public static final String MIN_ = "min_";
    public static final String MAX_ = "max_";
    private static int currentArmy = 0;

    // The hashmap containing the statistics to display and store in the xml file
    private LinkedHashMap<String, Integer> stats = new LinkedHashMap<String, Integer>();

    /*
        All the statistics stored and displayed in the window of statistics in the level selection
        screen (only some of them are used for the Score calculation)
    */
    public static final String GAMES = "Games";
    public static final String WINS = "Wins";
    public static final String DEFEATS = "Defeats";
    public static final String TURNS = "Turns";
    public static final String MIN_TURNS = MIN_ + TURNS;
    public static final String LANDS_TURN = "Lands_Turn";
    public static final String MAX_LANDS_TURN = MAX_ + LANDS_TURN;
    public static final String TREES = "Trees";
    public static final String MAX_TREES = MAX_ + TREES;
    public static final String ARMY = "Army";
    public static final String MIN_ARMY = MIN_ + ARMY;
    public static final String MAX_ARMY = MAX_ + ARMY;
    public static final String L0 = SoldierLevel.L0.getName();
    public static final String L1 = SoldierLevel.L1.getName();
    public static final String L2 = SoldierLevel.L2.getName();
    public static final String L3 = SoldierLevel.L3.getName();
    public static final String UNITS = "Units";
    public static final String LOST_L0 = LOST_ + L0;
    public static final String LOST_L1 = LOST_ + L1;
    public static final String LOST_L2 = LOST_ + L2;
    public static final String LOST_L3 = LOST_ + L3;
    public static final String LOST_UNITS = LOST_ + UNITS;
    public static final String MAX_LOST_L0 = MAX_ + LOST_L0;
    public static final String MAX_LOST_L1 = MAX_ + LOST_L1;
    public static final String MAX_LOST_L2 = MAX_ + LOST_L2;
    public static final String MAX_LOST_L3 = MAX_ + LOST_L3;
    public static final String MAX_LOST_UNITS = MAX_ + LOST_UNITS;
    public static final String MAX_L0 = MAX_ + L0;
    public static final String MAX_L1 = MAX_ + L1;
    public static final String MAX_L2 = MAX_ + L2;
    public static final String MAX_L3 = MAX_ + L3;
    public static final String MAX_UNITS = MAX_ + UNITS;

    // The hashmap containing the counters used for the calculations of all the other statistics
    private LinkedHashMap<String, Integer> currentStats = new LinkedHashMap<String, Integer>();

    // All counters in game (they are called currentStats in comments)
    public static final String CURRENT_TURNS = "currentTurns";
    public static final String CURRENT_LANDS = "currentLands";
    public static final String CURRENT_TREES = "currentTrees";
    public static final String CURRENT_LOST_L0 = "currentLost_L0";
    public static final String CURRENT_LOST_L1 = "currentLost_L1";
    public static final String CURRENT_LOST_L2 = "currentLost_L2";
    public static final String CURRENT_LOST_L3 = "currentLost_L3";
    public static final String CURRENT_L0 = "currentL0";
    public static final String CURRENT_L1 = "currentL1";
    public static final String CURRENT_L2 = "currentL2";
    public static final String CURRENT_L3 = "currentL3";

    /**
     * Class constructor
     * It initializes the hashmaps containing the statistics for all worlds and the global ones
     * All statistics are put to 0 before the game
     */
    public Statistics() {
        putToZero(currentStats, CURRENT_TURNS);
        putToZero(currentStats, CURRENT_TREES);
        putToZero(currentStats, CURRENT_LOST_L0);
        putToZero(currentStats, CURRENT_LOST_L1);
        putToZero(currentStats, CURRENT_LOST_L2);
        putToZero(currentStats, CURRENT_LOST_L3);
        putToZero(currentStats, CURRENT_L0);
        putToZero(currentStats, CURRENT_L1);
        putToZero(currentStats, CURRENT_L2);
        putToZero(currentStats, CURRENT_L3);
        putToZero(currentStats, CURRENT_LANDS);

        putToZero(stats, GAMES);
        putToZero(stats, WINS);
        putToZero(stats, DEFEATS);
        putToZero(stats, MIN_TURNS);
        putToZero(stats, TURNS);
        putToZero(stats, MAX_LANDS_TURN);
        putToZero(stats, LANDS_TURN);
        putToZero(stats, MAX_TREES);
        putToZero(stats, TREES);
        putToZero(stats, MIN_ARMY);
        putToZero(stats, MAX_ARMY);
        putToZero(stats, ARMY);
        putToZero(stats, MAX_LOST_L0);
        putToZero(stats, MAX_LOST_L1);
        putToZero(stats, MAX_LOST_L2);
        putToZero(stats, MAX_LOST_L3);
        putToZero(stats, MAX_LOST_UNITS);
        putToZero(stats, LOST_L0);
        putToZero(stats, LOST_L1);
        putToZero(stats, LOST_L2);
        putToZero(stats, LOST_L3);
        putToZero(stats, LOST_UNITS);
        putToZero(stats, MAX_L0);
        putToZero(stats, MAX_L1);
        putToZero(stats, MAX_L2);
        putToZero(stats, MAX_L3);
        putToZero(stats, MAX_UNITS);
        putToZero(stats, L0);
        putToZero(stats, L1);
        putToZero(stats, L2);
        putToZero(stats, L3);
        putToZero(stats, UNITS);
    }

    /**
     * Gives the hashmap containing the statistics to display and store
     *
     * @return the hashmap of statistics
     */
    public LinkedHashMap<String, Integer> getStats() {
        return stats;
    }

    /**
     * Gives the hashmap containing the counters used for the calculations of all the other statistics
     *
     * @return the hashmap of currentStats
     */
    public LinkedHashMap<String, Integer> getCurrentStats() {
        return currentStats;
    }

    /**
     * Updates a hashmap of statistics when a statistic is incremented
     *
     * @param hashmapStats the hashmap of statistics
     * @param stat         the statistic to increment
     */
    public static void incrementStatInMap(HashMap<String, Integer> hashmapStats, String stat) {
        hashmapStats.put(stat, hashmapStats.get(stat) + 1);
    }

    /**
     * Updates a hashmap of statistics when the value of a statistic is modified but not incremented
     *
     * @param hashmapStats the hashmap of statistics
     * @param stat         the statistic to change
     * @param value        the value added to the statistic
     */
    public static void addToStat(HashMap<String, Integer> hashmapStats, String stat, int value) {
        hashmapStats.put(stat, hashmapStats.get(stat) + value);
    }

    /**
     * Puts a key in a hashmap of statistics and initializes it to zero
     *
     * @param hashmapStats the hashmap of statistics
     * @param statistic    the key to initialize
     */
    public static void putToZero(HashMap<String, Integer> hashmapStats, String statistic) {
        hashmapStats.put(statistic, 0);
    }

    /**
     * Resets the currentStats
     */
    public static void resetCurrentStats(HumanPlayer player) {
        for (String key : player.getGlobalStats().getCurrentStats().keySet()) {
            player.getGlobalStats().getCurrentStats().put(key, 0);
            player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).getCurrentStats().put(key, 0);
        }
        currentArmy = 0;
    }

    /**
     * Updates the values of the statistics in the hashmap stats by calculations with the currentStats
     */
    public void updateStats() {
        updateTotal();
        calculateCurrentArmy();
        updateMin();
        updateMax();
    }

    /**
     *  //TODO
     *
     * @param key
     */
    private void calculateTotal(String key) {
        if (key.equals(UNITS)) {
            // total of units = sum of all units of all types
            addToStat(stats, key,
                    stats.get(L0)
                            + stats.get(L1)
                            + stats.get(L2)
                            + stats.get(L3));
        } else if (key.equals(LOST_UNITS)) {
            // total of lost units = sum of all lost units of all types
            addToStat(stats, key,
                    stats.get(LOST_L0)
                            + stats.get(LOST_L1)
                            + stats.get(LOST_L2)
                            + stats.get(LOST_L3));
        } else if (key.equals(ARMY)) {
            // total of army value = sum of values of (total of units of all types)
            addToStat(stats, key,
                    stats.get(L0) * SoldierLevel.L0.getPrice()
                            + stats.get(L1) * SoldierLevel.L1.getPrice()
                            + stats.get(L2) * SoldierLevel.L2.getPrice()
                            + stats.get(L3) * SoldierLevel.L3.getPrice());
        } else if (key.equals(LANDS_TURN)) {
            // total of lands/turn in stats += currentLands/currentTurns
            if (!(currentStats.get(CURRENT_TURNS).intValue() == 0)) {
                addToStat(stats, key, currentStats.get(CURRENT_LANDS) / currentStats.get(CURRENT_TURNS));
            }
        } else {
            // value in stats += currentStat
            String current = "current" + key;
            addToStat(stats, key, currentStats.get(current));
        }
    }

    /**
     * totalL0 - totalLostL0  //TODO
     *
     * @param key
     * @return
     */
    private int calculateTotalLeft(String key) {
        return stats.get(key) - stats.get(LOST_ + key);
    }

    /**
     *  //TODO
     *
     * @param key
     * @return
     */
    public int calculateAvg(String key) {
        if (stats.get(GAMES) == 0) return 0;
        return stats.get(key) / stats.get(GAMES);
    }

    /**
     *  //TODO
     *
     * @param key
     * @return
     */
    public int calculateAvgLeft(String key) {
        if (stats.get(GAMES) == 0) return 0;
        return calculateTotalLeft(key) / stats.get(GAMES);
    }

    /**
     *  //TODO
     *
     * @param key
     */
    private void calculateMin(String key) {
        // min value in stats = currentStat (because no game has been played)
        if (stats.get(MIN_ + key) == 0) {

            if (key.equals(ARMY))
                stats.put(MIN_ + key, currentArmy);
            else
                stats.put(MIN_ + key, currentStats.get("current" + key));
        }
        // min value in stats = min(currentStat, min value in stats)
        else {

            if (key.equals(ARMY))
                stats.put(MIN_ + key, Math.min(currentArmy, stats.get(MIN_ + key)));
            else
                stats.put(MIN_ + key, Math.min(currentStats.get("current" + key), stats.get(MIN_ + key)));
        }
    }

    /**
     *  //TODO
     *
     * @param key
     */
    private void calculateMax(String key, String current) {
        if (key.equals(ARMY)) {
            // max army value in stats = max(sum of values of all current units of all types, max army value in stats)
            stats.put(MAX_ + key, Math.max(currentArmy, stats.get(MAX_ + key)));
        } else if (key.equals(UNITS)) {
            // max of units in stats = max(sum of all current units of all types, max of units in stats)
            stats.put(MAX_ + key,
                    Math.max(currentStats.get(CURRENT_L0)
                            + currentStats.get(CURRENT_L1)
                            + currentStats.get(CURRENT_L2)
                            + currentStats.get(CURRENT_L3), stats.get(MAX_ + key)));
        } else if (key.equals(LOST_UNITS)) {
            // max of lost units in stats = max(sum of all current lost units of all types, max of lost units in stats)
            stats.put(MAX_ + key,
                    Math.max(currentStats.get(CURRENT_LOST_L0)
                            + currentStats.get(CURRENT_LOST_L1)
                            + currentStats.get(CURRENT_LOST_L2)
                            + currentStats.get(CURRENT_LOST_L3), stats.get(MAX_ + key)));
        } else if (key.equals(LANDS_TURN)) {
            // max lands/turn in stats = max(currentLands/currentTurns, max lands/turn in stats)
            stats.put(MAX_ + key,
                    Math.max(currentStats.get(CURRENT_LANDS) / currentStats.get(CURRENT_TURNS), stats.get(MAX_ + key)));
        } else
            // max value in stats = max(currentStat, max value in stats)
            stats.put(MAX_ + key, Math.max(currentStats.get(current), stats.get(MAX_ + key)));
    }

    /**
     * Gets the army value from the game that has just been played (then used to determine MIN_ARMY and MAX_ARMY)
     *
     * It is the sum of all player's units values (= total number of units of type A multiplied by their price)
     * It is stored in the variable currentArmy to simplify the reading of calculations in the other methods
     */
    public void calculateCurrentArmy() {
        currentArmy = currentStats.get(CURRENT_L0) * SoldierLevel.L0.getPrice()
                + currentStats.get(CURRENT_L1) * SoldierLevel.L1.getPrice()
                + currentStats.get(CURRENT_L2) * SoldierLevel.L2.getPrice()
                + currentStats.get(CURRENT_L3) * SoldierLevel.L3.getPrice();
    }

    /**
     * Calculates the score of the player
     *
     * The statistics used are :
     * - number of cut trees [bonus]
     * - number of tiles/turn [bonus]
     * - army value [malus]
     * - number of lost units (killed by the enemy or dead because of bankrupt) [malus]
     * - number of turns to end the game [malus]
     * - total number of games
     * - total number of wins or defeat (depending on whether the player has won or has lost)
     * The minimum possible score is 50
     * This method could be used to calculate the score of any level played, but it is currently only
     * used to display the score of global statistics
     *
     * @return true if the player for whom the score is calculated has won the game, false otherwise
     */
    public int calculateScore(boolean hasWon) {
        float multiplier = 0.75f;
        int factorTurns = 50;
        int factorTrees = 200;
        int factorLandsTurn = 100;
        int factorArmy = 5;
        int factorLosses = 100;
        int factorWins = 5000;
        int factorDefeats = 500;

        double score = (factorTrees * stats.get(TREES)
                + factorLandsTurn * stats.get(LANDS_TURN)
                - factorArmy * stats.get(ARMY)
                - factorLosses * stats.get(LOST_UNITS))
                - factorTurns * stats.get(TURNS);

        // The more the player loses, the lower the score
        if (hasWon)
            score += factorWins * Math.pow(multiplier, stats.get(GAMES) - stats.get(WINS));
        else
            score -= factorDefeats * Math.pow(multiplier, stats.get(GAMES) - stats.get(DEFEATS));

        // Minimum score
        if (score <= 0)
            score = 50;

        return (int) score;
    }

    /**
     * Updates the values of the "total" statistics (then used to calculate the "average" statistics)
     * in the hashmap stats by calculations with the currentStats
     */
    public void updateTotal() {
        // Must be done before calculateTotal(ARMY)
        calculateTotal(L0);
        calculateTotal(L1);
        calculateTotal(L2);
        calculateTotal(L3);
        calculateTotal(UNITS);

        calculateTotal(TURNS);

        calculateTotal(TREES);

        calculateTotal(LOST_L0);
        calculateTotal(LOST_L1);
        calculateTotal(LOST_L2);
        calculateTotal(LOST_L3);
        calculateTotal(LOST_UNITS);

        calculateTotal(ARMY);

        calculateTotal(LANDS_TURN);
    }

    /**
     * Updates the values of the "min" statistics in the hashmap stats by calculations with the currentStats
     */
    private void updateMin() {
        calculateMin(TURNS);

        calculateMin(ARMY);
    }

    /**
     * Updates the values of the "max" statistics in the hashmap stats by calculations with the currentStats
     */
    private void updateMax() {
        calculateMax(TREES, CURRENT_TREES);

        calculateMax(LOST_L0, CURRENT_LOST_L0);
        calculateMax(LOST_L1, CURRENT_LOST_L1);
        calculateMax(LOST_L2, CURRENT_LOST_L2);
        calculateMax(LOST_L3, CURRENT_LOST_L3);
        calculateMax(LOST_UNITS, "");

        calculateMax(L0, CURRENT_L0);
        calculateMax(L1, CURRENT_L1);
        calculateMax(L2, CURRENT_L2);
        calculateMax(L3, CURRENT_L3);
        calculateMax(UNITS, "");

        calculateMax(ARMY, "");

        calculateMax(LANDS_TURN, "");
    }
}