package be.ac.umons.slay.g02.players;

public class AIRandom extends Player implements AI {

    public AIRandom (Colors color) {
        this.name = "AIRandom";
        this.color = color;
    }

    @Override
    public void play() {

    }

    @Override
    public boolean canAddUnit() {
        return true;

    }

    @Override
    public boolean canMerge() {
        return true;

    }

    @Override
    public boolean canMove() {
        return true;

    }

    @Override
    public boolean checkCoins() {
        return true;

    }
}
