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

    public boolean setEntity(Entity entity) {
        if (this.entity == null) {
            this.entity = entity;
            return true;
        } else {
            return false;
        } //Possibilité d'améliorer en utilisant les erreurs au lieu de retourner un boolean
    }

    public void killEntity() {
        this.entity = null;
    }

}
