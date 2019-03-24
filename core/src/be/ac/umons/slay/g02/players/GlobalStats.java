package be.ac.umons.slay.g02.players;

public class GlobalStats extends Statistics {
    private int rank;
    private int score = 0;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
