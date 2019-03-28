package be.ac.umons.slay.g02.players;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *   //TODO
 */
public class HumanPlayer extends Player {
    Account account;
    GlobalStats globalStats = new GlobalStats();
    List listLevelStats = new ArrayList();

    /**
     *  //TODO
     *
     * @param name
     * @param color
     */
    public HumanPlayer(String name, Colors color) {
        this.name = name;
        this.color = color;
    }

    /**
     *  //TODO
     *
     * @return
     */
    public List getListLevelStats() {
        return listLevelStats;
    }

    /**
     *  //TODO
     *
     * @param listLevelStats
     */
    public void setListLevelStats(List listLevelStats) {
        this.listLevelStats = listLevelStats;
    }

    public Account getAccount() {
        return account;
    }

    /**
     *  //TODO
     *
     * @param account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     *  //TODO
     *
     * @return
     */
    public GlobalStats getGlobalStats() {
        return globalStats;
    }

    /**
     *  //TODO
     *
     * @param globalStats
     */
    public void setGlobalStats(GlobalStats globalStats) {
        this.globalStats = globalStats;
    }

    /**
     *  //TODO
     *
     * @param level
     * @return
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

    /**
     *  //TODO
     *
     * @return
     */
    @Override
    public String toString() {
        return name;
    }
}