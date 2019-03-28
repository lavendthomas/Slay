package be.ac.umons.slay.g02.level;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.entities.StaticEntity;

import static be.ac.umons.slay.g02.gui.Main.prefs;
import static be.ac.umons.slay.g02.gui.screens.Menu.player1;
import static be.ac.umons.slay.g02.gui.screens.Menu.player2;

/**
 * TODO
 */
public class Tile {
    private TileType type;
    private Entity entity;
    private Territory territory;

    /**
     * TODO
     *
     * @param type
     */
    public Tile(TileType type) {
        this.type = type;
    }

    /**
     * TODO
     *
     * @return
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * TODO
     *
     * @return
     */
    public TileType getType() {
        return type;
    }

    /**
     * Buys a new entity when placing it on a tile
     *
     * @param e  The new entity to place
     * @param to Tile on which to place the entity
     * @return true if the entity can be placed on the selected tile, false otherwise
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
                        to.setEntity(new Soldier(SoldierLevel.fromLevel(newLvl),
                                ((Soldier) to.getEntity()).getMoved()));
                        /*
                            Updates currentL. for stats - when two soldiers merge, they are deleted
                            and another type is added
                        */
                        if (prefs != null && prefs.getBoolean("isPlayer1Logged") && to.getTerritory().getOwner()
                                .getName().equals(player1.getName()))
                            Level.updatePlayerStatsMerge(player1, fromLvl, toLvl, newLvl);

                        else if (prefs != null && prefs.getBoolean("isPlayer2Logged") && to.getTerritory().getOwner()
                                .getName().equals(player2.getName()))
                            Level.updatePlayerStatsMerge(player2, fromLvl, toLvl, newLvl);

                        return true;

                    } else if (to.getEntity() == StaticEntity.CAPITAL)
                        // Prevents from placing oneself on one's capital
                        return false;

                        // Residual case (tree or grave)
                    else {
                        // Tree
                        if (to.getEntity() == StaticEntity.TREE) {
                            if (prefs != null && prefs.getBoolean("isPlayer1Logged") && to.getTerritory().getOwner()
                                    .getName().equals(player1.getName()))
                                Level.updatePlayerStatsTrees(player1);

                            else if (prefs != null && prefs.getBoolean("isPlayer2Logged") && to.getTerritory().getOwner()
                                    .getName().equals(player2.getName()))
                                Level.updatePlayerStatsTrees(player2);
                        }
                        to.setEntity(e);
                        return true;
                    }
                }
                // No entity
                else {
                    to.setEntity(e);
                    return true;
                }
            }
            // Enemy territory or neutral
            else {
                if (to.getEntity() != null && to.getEntity() instanceof Soldier) {
                    /*
                        Updates currentLostL. for stats - when a soldier is killed by the enemy
                        The stats are updated for the player who is not the current player
                    */
                    if (prefs != null && prefs.getBoolean("isPlayer1Logged") && (to.getTerritory().getOwner()
                            .getName().equals(player1.getName())))
                        Level.updatePlayerStatsLost(player1, to.getEntity());

                    else if (prefs != null && prefs.getBoolean("isPlayer2Logged") && (to.getTerritory().getOwner()
                            .getName().equals(player2.getName())))
                        Level.updatePlayerStatsLost(player2, to.getEntity());
                }
                to.setEntity(e);
                ((Soldier) to.getEntity()).setMoved(true);
                to.setTerritory(territory);
                return true;
            }
        } else
            return false;
    }

    /**
     *  TODO
     *
     * @param entity
     * @param rec    If you should check for changes in income/wages and capitals or not.
     */
    void setEntity(Entity entity, boolean rec) {
        // Updates the income of the territory
        if (territory != null && rec)
            territory.update(this.entity, entity);

        this.entity = entity;
    }

    /**
     *  TODO
     *
     * @param entity
     */
    void setEntity(Entity entity) {
        setEntity(entity, true);
    }

    /**
     * Returns true if this cell has this entity in it
     *
     * @param entity     TODO
     * @return
     */
    boolean contains(Entity entity) {
        return this.entity == entity;
    }

    /**
     *  TODO
     *
     * @param other
     * @return
     */
    boolean hasSameOwner(Tile other) {
        if (territory == null)
            return false;
        return territory.hasSameOwner(other.territory);
    }

    /**
     *  TODO
     *
     * @param other
     * @return
     */
    boolean mergeTerritories(Tile other) {
        if (hasSameOwner(other)) {
            other.territory.remove(other);
            other.territory = territory;
            territory.add(other);
            return true;
        } else
            return false;
    }

    /**
     *  TODO
     *
     * @return
     */
    public Territory getTerritory() {
        return this.territory;
    }

    /**
     * Changes the territory of the tile
     *
     * @param t      the new territory
     * @param update if the old territory should be notified
     */
    void setTerritory(Territory t, boolean update) {
        if (territory != null && update)
            territory.remove(this);
        territory = t;
        if (territory != null)
            t.add(this);
    }

    /**
     * Changes the territory of the tile and notifies the old territory
     *
     * @param t the new territory
     */
    public void setTerritory(Territory t) {
        setTerritory(t, true);
    }

    /**
     *  TODO
     *
     * @return
     */
    boolean hasTerritory() {
        return !(territory == null);
    }

    /**
     *  TODO
     *
     * @return
     */
    @Override
    public String toString() {
        return "[" + type.toString() + ":" + entity + ":" + territory + "]";
    }
}