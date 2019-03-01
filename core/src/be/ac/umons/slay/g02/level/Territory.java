package be.ac.umons.slay.g02.level;

import java.util.ArrayList;
import java.util.List;

import be.ac.umons.slay.g02.entities.StaticEntity;
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

    public Territory(Player owner, Tile... cells) {
        this.owner = owner;
        this.cells = new ArrayList<Tile>();
        for (Tile cell : cells) {
            this.cells.add(cell);
        }
        tileCount = cells.length;
    }

    public void add(Tile cell) {
        if (cell.getEntity() == null) {
            // Never happends because a new cell is always occupied by a soldier
            income += 1;
        }
        cells.add(cell);
    }

    /**
     * Removes a cell
     * @param cell
     * @return
     */
    public boolean remove(Tile cell) {
        if (cell.getEntity() == null) {
            income -= 1;
        } else if (cell.getEntity() instanceof StaticEntity) {
            StaticEntity st = (StaticEntity) cell.getEntity();
            if (st == StaticEntity.TREE) {
                coins += 3;
            }
        }
        return this.cells.remove(cell);
    }

    public boolean hasSameOwner(Territory other) {
        if (other == null) {
            return false;
        }
        return owner.equals(other.owner);
    }

    List<Tile> getCells() {
        // TODO should return a copy for encapsulation
        return cells;
    }

    public Player getOwner() {
        return owner;
    }

    public void addCoins(int n) {
        coins += n;
    }

    /**
     * changes the income of the territory
     * @param delta the amount of income to add (in coins per turn)
     */
    public void changeIncome(int delta) {
        income += delta;
    }

    /**
     * Chenages the outgoings of the territory
     * @param delta the amout of outgoings to add (in coins per turn)
     */
    public void changeOutgoings(int delta) {
        outgoings += delta;
    }


    @Override
    public String toString() {
        return "{"+ owner + ":" + hashCode() +" $: " + coins +"}";
    }
}
