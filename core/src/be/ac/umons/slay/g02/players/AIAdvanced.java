package be.ac.umons.slay.g02.players;

public class AIAdvanced extends Player implements AI {

    public AIAdvanced (Colors color) {
        this.name = "AIAdvanced";
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
