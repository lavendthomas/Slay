package be.ac.umons.slay.g02.level;

import java.util.ArrayList;
import java.util.List;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.players.Player;

public class Territory {

    /**
     * Owner of the territory
     */
    private Player owner;

    private List<Tile> cells;
    private Tile capital;

    private int coins;
    private int income;
    private int wages;

    public Territory(Player owner, Tile... cells) {
        this.owner = owner;
        this.cells = new ArrayList<Tile>();
        for (Tile cell : cells) {
            add(cell);
        }
    }

    public void add(Tile cell) {
        if (cell.getEntity() == null) {
            income += 1;
        } else {
            // We add the wage if it is a soldier
            wages += cell.getEntity().getCost();
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
        } else {
            // We remove the wage if it is a soldier
            wages -= cell.getEntity().getCost();
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

    void update(Entity removed, Entity added) {
        if (removed == null) {
            income -= 1;
        } else {
            wages -= removed.getCost();
            if (removed instanceof StaticEntity) {
                StaticEntity se = (StaticEntity) removed;
                if (se == StaticEntity.TREE) {
                    coins += 3;
                }
            }
        }
        if (added == null) {
            income += 1;
        } else {
            wages += added.getCost();
        }
    }

    boolean setCaptial(Tile c) {
        if (c == capital) {
            return false;
        } else {
            capital = c;
            return true;
        }
    }

    @Override
    public String toString() {
        return "{"+ owner + ":" + hashCode() +" $: " + coins + " +:" + income + " -: " + wages +"}";
    }
}
