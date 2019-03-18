package be.ac.umons.slay.g02.players;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HumanPlayer extends Player {

    Account account;
    GlobalStats globalStats = new GlobalStats();
    List listLevelStats = new ArrayList();


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

    public GlobalStats getGlobalStats() {
        return globalStats;
    }

    public void setGlobalStats(GlobalStats globalStats) {
        this.globalStats = globalStats;
    }

    public LevelStats getListLevelStats(int level) {
        Iterator iter = listLevelStats.iterator();
        while (iter.hasNext()) {
            LevelStats levelStats = new LevelStats();

            levelStats = (LevelStats) iter.next();
            if (levelStats.getLevel() == level) {
                return levelStats;
            }
        }
        return null;
    }

    public void setListLevelStats(LevelStats levelStats, int level) {
        listLevelStats.add(level, levelStats);
    }





    @Override
    public String toString() {
        return name;
    }
}
