package be.ac.umons.slay.g02.level;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.entities.StaticEntity;

public class Tile {
    private TileType type;
    private Entity entity;
    private Territory territory;

    public Tile(TileType type) {
        this.type = type;
    }

    public Entity getEntity() {
        return entity;
    }

    public TileType getType() {
        return type;
    }


    /**
     * Buys a new Entity on to tile
     *
     * @param e  The new entity to place
     * @param to Tile where place the entity
     * @return boolean
     */
    boolean buy(Entity e, Tile to) {
        if (territory.buy(e)) {
            if (hasSameOwner(to)) {
                // In his own territory
                if (to.getEntity() != null) {
                    // Entity already present
                    if (to.getEntity() instanceof Soldier) {
                        // Place in my territory and there is already a soldier
                        int toLvl = ((Soldier) to.getEntity()).getSoldierLevel().getLevel();
                        int fromLvl = ((Soldier) e).getSoldierLevel().getLevel();
                        int newLvl = fromLvl + 1;
                        to.setEntity(new Soldier(SoldierLevel.fromLevel(newLvl), ((Soldier) to.getEntity()).getMoved()));
                        return true;
                    } else if (to.getEntity() == StaticEntity.CAPITAL) {
                        // Prevents from placing oneself on one's capital
                        return false;
                    } else {
                        // Residual case (tree or grave)
                        to.setEntity(e);
                        return true;
                    }
                } else {
                    // No entity
                    to.setEntity(e);
                    return true;
                }
            } else {
                // Enemy territory or neutral
                to.setEntity(e);
                ((Soldier) to.getEntity()).setMoved(true);
                to.setTerritory(territory);
                return true;
            }
        } else {
            return false;
        }
    }


    /**
     * @param entity
     * @param rec    If you should check for changes in income/wages and capitals or not.
     */
    void setEntity(Entity entity, boolean rec) {

        // Update the income of the territory
        if (territory != null && rec) {
            territory.update(this.entity, entity);
        }

        this.entity = entity;

    }

    void setEntity(Entity entity) {
        setEntity(entity, true);
    }

    /**
     * Return true if this cell has this entity in it.
     *
     * @param entity
     * @return
     */
    boolean contains(Entity entity) {
        return this.entity == entity;
    }

    boolean hasSameOwner(Tile other) {
        if (territory == null) {
            return false;
        }
        return territory.hasSameOwner(other.territory);
    }

    boolean mergeTerritories(Tile other) {
        if (hasSameOwner(other)) {
            other.territory.remove(other);
            other.territory = territory;
            territory.add(other);
            return true;
        } else {
            return false;
        }
    }

    public Territory getTerritory() {
        return this.territory;
    }


    /**
     * Changes the territory of the tile
     *
     * @param t      the new territory
     * @param update if the olf territory should be notified
     */
    void setTerritory(Territory t, boolean update) {
        if (territory != null && update) {
            territory.remove(this);
        }
        territory = t;
        if (territory != null) {
            t.add(this);
        }
    }

    /**
     * Changes the territory of the tile and notifies the old territory
     *
     * @param t the new territory
     */
    public void setTerritory(Territory t) {
        setTerritory(t, true);
    }

    boolean hasTerritory() {
        return !(territory == null);
    }

    @Override
    public String toString() {
        return "[" + type.toString() + ":" + entity + ":" + territory + "]";
    }
}
