package be.ac.umons.slay.g02.players;

/**
 * Class handling the statistics of all levels
 */
public class LevelStats extends Statistics {
    int level;

    /**
     * Gives the level played
     *
     * @return the level played
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level played to update the corresponding statistics
     *
     * @param level the level played
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
