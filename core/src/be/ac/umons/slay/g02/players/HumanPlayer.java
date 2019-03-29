package be.ac.umons.slay.g02.players;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class assigned to a user with an account
 */
public class HumanPlayer extends Player {
    Account account;
    GlobalStats globalStats = new GlobalStats();
    List listLevelStats = new ArrayList();

    /**
     * Constructor class which determines the player's attributes
     *
     * @param name  the player's name to display
     * @param color the player's color in game
     */
    public HumanPlayer(String name, Colors color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Gives the list of all instances of LevelStats (there are as many as there are playable
     * levels)
     *
     * @return the list of all instances of LevelStats
     */
    public List getListLevelStats() {
        return listLevelStats;
    }

    /**
     * Sets the list of all instances of LevelStats (there are as many as there are playable
     * levels)
     *
     * @param listLevelStats the list of all instances of LevelStats
     */
    public void setListLevelStats(List listLevelStats) {
        this.listLevelStats = listLevelStats;
    }

    /**
     * Gets the player's account
     *
     * @return the player's account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets the player's account
     *
     * @param account the player's account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Gives the instance of GlobalStats assigned to the player
     *
     * @return the player's instance of GlobalStats
     */
    public GlobalStats getGlobalStats() {
        return globalStats;
    }

    /**
     * Sets the instance of GlobalStats assigned to the player
     *
     * @param globalStats the player's instance of GlobalStats
     */
    public void setGlobalStats(GlobalStats globalStats) {
        this.globalStats = globalStats;
    }

    /**
     * Gives the instance of LevelStats corresponding to the level number in the entry
     *
     * @param level the level number for which we want the instance of LevelStats
     * @return the corresponding instance of LevelStats
     */
    public LevelStats getListLevelStats(int level) {
        Iterator iter = listLevelStats.iterator();
        while (iter.hasNext()) {
            LevelStats levelStats;
            levelStats = (LevelStats) iter.next();
            if (levelStats.getLevel() == level) {
                return levelStats;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}