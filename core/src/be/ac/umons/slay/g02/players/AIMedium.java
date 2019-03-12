package be.ac.umons.slay.g02.players;

public class AIMedium extends Player implements AI {

    public AIMedium (Colors color) {
        this.name = "AIMedium";
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
