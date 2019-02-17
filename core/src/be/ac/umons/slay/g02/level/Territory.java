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

    /**
     * Adds all of the ressources from the other territory to this one
     *
     * @param other
     */
    public void mergeResources(Territory other) {
        coins += other.coins;
    }

    public void add(Tile cell) {
        income += 1;
        tileCount += 1;
        cells.add(cell);
    }

    public boolean remove(Tile cell) {
        income -= 1;        // TODO always ?
        tileCount -= 1;
        return this.cells.remove(cell);
    }

    public boolean hasSameOwner(Territory other) {
        return owner.equals(other.owner);
    }
}
