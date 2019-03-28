package be.ac.umons.slay.g02.players;

import java.util.Map;

import be.ac.umons.slay.g02.gui.screens.LevelSelection;

/**
 *   //TODO
 */
public class GlobalStats extends Statistics {
    private int rank;
    private int score = 0;

    /**
     *  //TODO
     *
     * @return
     */
    public int getRank() {
        return rank;
    }

    /**
     *  //TODO
     *
     * @param rank
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     *  //TODO
     *
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     *  //TODO
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Updates the values of the statistics in the hashmap stats
     *
     * A global statistic can be :
     * - a minimum value, i.e. the minimum value of the statistic among all worlds (0 is excluded if
     * at least one value of the statistic in a world is different of zero
     * - a maximum value, i.e. the maximum value of the statistic among all worlds
     * - a total value, i.e. the value total of a statistic for all games played
     */
    public void updateStats(HumanPlayer player) {
        for (Map.Entry<String, Integer> key : getStats().entrySet()) {
            String stat = key.getKey();
            int globalValue = key.getValue();

            // If the global statistic to update is a minimum
            if (stat.startsWith(MIN_)) {

                // Searches for the minimum value among all levels

                for (int i = 1; i <= LevelSelection.TOTAL_NUMBER_ISLANDS; i++) {
                    int levelValue = player.getListLevelStats(i).getStats().get(stat);

                    // Avoids doing Math.min(0, other) in order to get a value other than zero
                    if (levelValue != 0) {
                        if (globalValue == 0)
                            getStats().put(stat, levelValue);
                        else
                            getStats().put(stat, Math.min(levelValue, globalValue));
                    }
                }
            }
            // If the global statistic to update is a maximum
            else if (stat.startsWith(MAX_)) {

                // Searches for the maximum value among all levels

                for (int i = 1; i <= LevelSelection.TOTAL_NUMBER_ISLANDS; i++) {
                    int levelValue = player.getListLevelStats(i).getStats().get(stat);
                    getStats().put(stat, Math.max(levelValue, globalValue));
                }
            }

            /*
                All other global statistics are total ones (Games, Wins and Defeats are excluded
                because they are not used to calculate average statistics, which is why they have
                been updated elsewhere)
            */
            else if (stat != GAMES && stat != WINS && stat != DEFEATS)
                updateTotal();
        }
    }
}