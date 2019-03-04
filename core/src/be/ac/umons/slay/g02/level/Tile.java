package be.ac.umons.slay.g02.level;

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

    void setTerritory(Territory t) {
        if (territory != null) {
            territory.remove(this);
        }
        territory = t;
        t.add(this);
    }

    boolean hasTerritory() {
        return !(territory == null);
    }

    @Override
    public String toString() {
        return "[" + type.toString() + ":" + entity + ":" + territory +"]";
    }
}
