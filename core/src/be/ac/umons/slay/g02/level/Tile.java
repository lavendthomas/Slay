package be.ac.umons.slay.g02.level;

import com.badlogic.gdx.Gdx;

import be.ac.umons.slay.g02.entities.Entity;

public class Tile {
    private TileType type;
    private Entity entity;
    private Territory territory;

    public Tile(TileType type) {
        this.type = type;
    }

    public Tile(TileType type, Entity entity, Territory territory) {
        this.type = type;
        setEntity(entity);
        this.territory = territory;
    }

    public Entity getEntity() {
        return entity;
    }

    public TileType getType() {
        return type;
    }

    public boolean isEmpty() {
        return entity == null;
    }

    /**
     *
     * @param entity
     * @param rec If you should check for changes in income/wages and capitals or not.
     */
    public void setEntity(Entity entity, boolean rec) {
        Gdx.app.log("moves", "setEntity called on " + entity);
        // Update the income of the territory
        if (territory != null & rec) {
            territory.update(this.entity, entity);
        }
        this.entity = entity;
    }

    public void setEntity(Entity entity) {
        setEntity(entity, true);
    }

    /**
     * Return true if this cell has this entity in it.
     * @param entity
     * @return
     */
    public boolean contains(Entity entity) {
        return this.entity == entity;
    }

    public boolean hasSameOwner(Tile other) {
        if (territory == null) {
            return false;
        }
        return territory.hasSameOwner(other.territory);
    }

    public boolean mergeTerritories(Tile other) {
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
     * @param t the new territory
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
     * @param t the new territory
     */
    void setTerritory(Territory t) {
        setTerritory(t, true);
    }

    boolean hasTerritory() {
        return !(territory == null);
    }

    @Override
    public String toString() {
        return "[" + type.toString() + ":" + entity + ":" + territory +"]";
    }
}
