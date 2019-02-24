package be.ac.umons.slay.g02.players;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, Colors color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return name;
    }
}
