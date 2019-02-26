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
        this.entity = entity;
        this.territory = territory;
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean isEmpty() {
        return this.entity == null;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
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
