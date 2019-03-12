package be.ac.umons.slay.g02.players;

public interface AI {
    boolean canAddUnit();
    boolean canMerge();
    boolean canMove();
    boolean checkCoins();
    void play();

}
