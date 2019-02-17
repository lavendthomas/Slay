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
        tileCount = cells.length;
    }

    public void add(Tile cell) {
        income += 1;        // TODO always ? Tree,...
        tileCount += 1;
        cells.add(cell);
    }

    public boolean remove(Tile cell) {
        income -= 1;        // TODO always ? Tree,...
        tileCount -= 1;
        return this.cells.remove(cell);
    }

    public boolean hasSameOwner(Territory other) {
        return owner.equals(other.owner);
    }

    /**
     * Splits a territory in multiple territories if part of their cells are not linked
     */
    public void split() {
        /*
        Idea: We start from one cell and we keep in this territory all the cells that are
        adjacent to this one and in the same territory. All the others are separated in another
        territory and continue to split until all the cells in the territory are adjacent.
         */
        if (cells.size() == 0) {
            return;
        }
        Tile startCell = cells.get(0);


    }

    public void addCoins (int n) {
        this.coins += n;
    }

    public void incrIncome () {
        this.income += 1;
    }
}
