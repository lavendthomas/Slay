package be.ac.umons.slay.g02.level;

import java.util.List;

import be.ac.umons.slay.g02.players.Player;

public class Territory {

    /**
     * Owner of the territory
     */
    private Player owner;

    private List<Tile> cells;

    private int coins;
    private int income;
    private int outgoings;
    private int tileCount;
    private int treeCount;
    //private Player owner    Player à refaire car impossible interface héritant d'une classe mais besoin de modéliser le proprio d'un territoire

    public Territory(Tile... cells) {
        for (Tile cell : cells) {
            this.cells.add(cell);
        }
    }

    public void add(Tile cell) {
        cells.add(cell);
    }

    public boolean remove(Tile cell) {
        return this.cells.remove(cell);
    }

    public boolean hasSameOwner(Territory other) {
        return owner.equals(other.owner);
    }
}
