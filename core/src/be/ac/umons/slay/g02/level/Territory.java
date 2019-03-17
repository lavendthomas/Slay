package be.ac.umons.slay.g02.level;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public int getCoins() {
        return this.coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getIncome() {
        return this.income;
    }

    // pour les tests
    public int getWages() {
        return this.wages;
    }

    public void add(Tile cell) {
        if (cell.getEntity() != StaticEntity.TREE) {
            income += 1;
        }
        if (cell.getEntity() != null){
            // We add the wage if it is a soldier
            wages += cell.getEntity().getCost();
        }
        cells.add(cell);
        Gdx.app.debug("moves", "Calling newCapital in cell from " + cell);
        // newCapital(); //TODO useful ?
    }

    /**
     * Removes a cell
     *
     * @param cell
     * @return
     */
    public boolean remove(Tile cell) {
        if (cell.getEntity() != StaticEntity.TREE) {
            income -= 1;
        }
        if (cell.getEntity() != null) {
            // We remove the wage if it is a soldier
            wages -= cell.getEntity().getCost();

            if (cell.getEntity() instanceof StaticEntity) {
                StaticEntity se = (StaticEntity) cell.getEntity();
                if (se == StaticEntity.CAPITAL) {
                    // If we removed a capital we have to recreate one.
                    Gdx.app.debug("moves", "calling newCapital in remove from " + cell);
                    newCapital();
                }
            }
        }

        // if less than 2 cells then delete the territory (delete the pointer to this territory from all cells

        if (cells.size() < 2) {
            for (Tile c : cells) {

                // We remove the Entity if it is a soldier or a capital
                if (c.getEntity() != null && c.getEntity().getCost() > 0) {
                    c.setEntity(null);
                } else if (c.getEntity() != null && c.getEntity() == StaticEntity.CAPITAL) {
                    c.setEntity(null);
                }

                // We remove the territory from all cells
                c.setTerritory(null, false);

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

    /**
     * Updates the income and wages of the territory
     * and makes sure there is one and only one capital on the territory
     *
     * @param removed
     * @param added
     */
    void update(Entity removed, Entity added) {
        if (removed != StaticEntity.TREE) {
            income -= 1;
        }
        if (removed != null) {
            wages -= removed.getCost();
            if (removed instanceof StaticEntity) {
                StaticEntity se = (StaticEntity) removed;
                switch (se) {
                    case TREE:
                        coins += 3;
                        break;
                    case CAPITAL:
                        // The capital was removed so we have to create a new one
                        capital = null;
                        Gdx.app.debug("moves", "Calling newCapital in update from " + removed + " and " + added);
                        newCapital();
                        break;
                }
            }
        }
        if (added != StaticEntity.TREE) {
            income += 1;
        }
        if (added != null) {
            wages += added.getCost();
        }
    }

    /**
     * Places a capital in the territory if needed.
     *
     * @return true if a capital had to be placed
     */
    boolean newCapital() {

        // Territory empty or too small => No capital
        if (cells.size() == 1) {
            return false;
        }

        List<Tile> capitals = getCapitals();
        //Gdx.app.log("moves", owner.getName() + capitals.toString());

        if (capitals.size() == 1) {
            // If a capital is already there we delete don't anything
            return false;

        } else if (capitals.size() > 1) {
            // We have to many capitals (e.g. if merge), we remove all of them but one.
            for (int i = 1; i < capitals.size(); i++) {
                capitals.get(i).setEntity(null, false);
            }
            return false; // no new capital
        }

        // There are no capitals one has to be added.
        List<Tile> emptyTiles = new ArrayList<Tile>();
        for (Tile cell : cells) {
            if (cell.getEntity() == null) {
                emptyTiles.add(cell);
            }
        }

        if (emptyTiles.size() != 0) {
            // Place a capital in one of the empty cells

            int nc = new Random().nextInt(emptyTiles.size());
            Tile newCap = emptyTiles.get(nc);
            newCap.setEntity(StaticEntity.CAPITAL, false);
            setCapital(newCap);

        } else {
            // Try to place it on a tree, then on a random cell

            List<Tile> treeTiles = new ArrayList<Tile>();
            for (Tile cell : cells) {
                if (cell.contains(StaticEntity.TREE)) {
                    treeTiles.add(cell);
                }
            }
            if (treeTiles.size() != 0) {
                // Place a capital in one of the cells with a tree

                int nc = new Random().nextInt(treeTiles.size());
                Tile newCap = treeTiles.get(nc);
                newCap.setEntity(StaticEntity.CAPITAL, false);
                setCapital(newCap);
            } else {
                // Place a capital in a random cell

                int nc = new Random().nextInt(cells.size());
                Tile newCap = cells.get(nc);
                newCap.setEntity(StaticEntity.CAPITAL, false);
                setCapital(newCap);
            }
        }

        return true;
    }

    /**
     * Sets the capital of the territory and removes all other capitals in the territory.
     * Also sets the entity of the Tile c as a StaticEntity.CAPITAL
     *
     * @param c the tile to set as a capital
     * @return true if the capital is changed
     */
    boolean setCapital(Tile c) {
        for (Tile cell : cells) {
            if (cell.contains(StaticEntity.CAPITAL)) {
                cell.setEntity(null, false);
            }
        }
        c.setEntity(StaticEntity.CAPITAL, false);
        capital = c;
        return true;
    }

    /**
     * Adds money and kills soldiers if funds are not sufficient
     */
    void nextTurn() {
        coins += income - wages;

        // If not enough money we kill all soldiers (all entities that have a maintaining cost)
        if (coins < 0) {
            for (Tile c : cells) {
                if (c.getEntity() != null && c.getEntity().getCost() > 0) {
                    c.setEntity(StaticEntity.GRAVE);
                }
            }
            coins = income - wages;
        }
    }

    /**
     * Returns true if the territory has the necessary funds to buy the entity
     *
     * @param e The entity we want to buy
     * @return true if the territory has the necessary funds
     */
    public boolean canBuy(Entity e) {
        return coins >= e.getPrice();
    }

    /**
     * Removes the funds necessary to buy the entity if they are sufficient
     *
     * @param e The entity to buy
     * @return true if the entity was bought successfully
     */
    boolean buy(Entity e) {
        if (canBuy(e)) {
            coins -= e.getPrice();
            return true;
        } else {
            return false;
        }
    }


    private List<Tile> getCapitals() {
        List<Tile> capitals = new ArrayList<Tile>();
        for (Tile cell : cells) {
            if (cell.contains(StaticEntity.CAPITAL)) {
                capitals.add(cell);
            }
        }
        return capitals;
    }

    @Override
    public String toString() {
        return "{" + owner + ":" + hashCode() + " $: " + coins + " +:" + income + " -: " + wages + "}";
    }
}
