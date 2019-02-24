package be.ac.umons.slay.g02.players;


public class Statis {

    private String name;
    private String avatar;
    private String password;
    private int rank;
    private int wins;
    private int games;
    private int turns;
    private int lands;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private int trees;
    private int losses;
    private int army;
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public int getLands() {
        return lands;
    }

    public void setLands(int lands) {
        this.lands = lands;
    }

    public int getTrees() {
        return trees;
    }

    public void setTrees(int trees) {
        this.trees = trees;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getArmy() {
        return army;
    }

    public void setArmy(int army) {
        this.army = army;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
