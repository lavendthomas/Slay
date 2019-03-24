package be.ac.umons.slay.g02.players;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HumanPlayer extends Player {
    Account account;
    GlobalStats globalStats = new GlobalStats();
    List listLevelStats = new ArrayList();
    Statistics statistics = new Statistics();

    public HumanPlayer(String name, Colors color) {
        this.name = name;
        this.color = color;
    }

    public List getListLevelStats() {
        return listLevelStats;
    }

    public void setListLevelStats(List listLevelStats) {
        this.listLevelStats = listLevelStats;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public GlobalStats getGlobalStats() {
        return globalStats;
    }

    public void setGlobalStats(GlobalStats globalStats) {
        this.globalStats = globalStats;
    }

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
