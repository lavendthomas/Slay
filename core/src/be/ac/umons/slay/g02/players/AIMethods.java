package be.ac.umons.slay.g02.players;

import java.util.ArrayList;
import java.util.List;
import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.gui.screens.HexManagement;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Tile;

/**
 * Class containing common methods to more artificial intelligence
 */

public class AIMethods {

    /**
     * Number of milliseconds that artificial intelligence must wait before playing
     */

    private static int speed = 333; // 3 tours / seconde

    /**
     * Search all territories of a player or his enemies
     *
     * @param level         Level in which searched
     * @param player        Player starting the search
     * @param searchEnemy   Boolean determining whether to search for the player's (false) or enemy's territories (true)
     * @return              List of all territories represented by the list of coordinates belonging to it
     */

    static List<List<Coordinate>> loadTerritories (Playable level, Player player, boolean searchEnemy) {
        List<List<Coordinate>> allTerritories = new ArrayList<List<Coordinate>>();
        List<Coordinate> visited = new ArrayList<Coordinate>();

        for (int i = 0; i < level.width(); i++) {
            for (int j = 0; j < level.height(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                Tile current = level.get(i, j);

                if (!searchEnemy) {
                    if (current.getTerritory() != null &&
                            current.getTerritory().getOwner().equals(player)
                            && !visited.contains(coordinate)) {
                        List<Coordinate> territory = level.neighbourTilesInSameTerritory(coordinate);
                        allTerritories.add(territory);
                        visited.addAll(territory);
                    }
                } else {
                    if (current.getTerritory() != null &&
                            !current.getTerritory().getOwner().equals(player)
                            && !visited.contains(coordinate)) {
                        List<Coordinate> territory = level.neighbourTilesInSameTerritory(coordinate);
                        allTerritories.add(territory);
                        visited.addAll(territory);
                    }
                }
            }
        }
        return allTerritories;
    }

    /**
     * Make artificial intelligence wait for a while
     */

    static void sleep() {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change speed value
     * @param sp    New speed value
     */

    public static void setSpeed (int sp) {
        speed = sp;
    }

    /**
     * Search for trees to be cut in the player's territory
     *
     * @param level     Level in which searched
     * @param moves     List of coordinates in which to search
     * @param cFrom     Original coordinate
     * @param player    Player starting the search
     * @return          Coordinate of a tile containing a tree to cut, null otherwise
     */

    static Coordinate chopTree (Playable level, List<Coordinate> moves, Coordinate cFrom, Player player) {
        for (Coordinate cTo : moves) {
            if (!cFrom.equals(cTo)) {
                // Parcours de toutes les cases où mon soldat peut être placé
                Tile current = level.get(cTo);
                // Récupère la tuile correspondante
                if (current.getTerritory() != null) {
                    // Il y a un territoire

                    if (current.getTerritory().getOwner().equals(player)) {
                        // Même territoire

                        if (current.getEntity() != null) {
                            // Il y a une entité
                            if (current.getEntity() == StaticEntity.TREE) {
                                // D'abord sur les arbres
                                return cTo;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Look for neutral tile to occupy
     *
     * @param level     Level in which searched
     * @param moves     List of coordinates in which to search
     * @param cFrom     Original coordinate
     * @param player    Player starting the search
     * @return          Coordinate of a neutral tile, null otherwise
     */
    static Coordinate captureNeutral (Playable level, List<Coordinate> moves, Coordinate cFrom, Player player) {
        for (Coordinate cTo : moves) {
            if (!cFrom.equals(cTo)) {
                // Parcours de toutes les cases où mon soldat peut être placé
                Tile current = level.get(cTo);
                // Récupère la tuile correspondante
                if (current.getTerritory() == null) {
                    return cTo;
                }
            }
        }
        return null;
    }

    /**
     * Search for graves to be cut in the player's territory
     *
     * @param level     Level in which searched
     * @param moves     List of coordinates in which to search
     * @param cFrom     Original coordinate
     * @param player    Player starting the search
     * @return          Coordinate of a tile containing a grave to clean, null otherwise
     */

    static Coordinate cleanGraves (Playable level, List<Coordinate> moves, Coordinate cFrom, Player player) {

        for (Coordinate cTo : moves) {
            if (!cFrom.equals(cTo)) {
                Tile current = level.get(cTo);
                if (current.getTerritory() != null) {

                    if (current.getTerritory().getOwner().equals(player)) {

                        if (current.getEntity() != null) {
                            if (current.getEntity() == StaticEntity.GRAVE) {
                                return cTo;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Look for enemy tile to occupy
     *
     * @param level     Level in which searched
     * @param moves     List of coordinates in which to search
     * @param cFrom     Original coordinate
     * @param player    Player starting the search
     * @return          Coordinate of a enemy tile, null otherwise
     */

    static Coordinate attackEnemy (Playable level, List<Coordinate> moves, Coordinate cFrom, Player player) {
        for (Coordinate cTo : moves) {
            if (!cFrom.equals(cTo)) {
                Tile current = level.get(cTo);
                if (current.getTerritory() != null) {
                    if (!current.getTerritory().getOwner().equals(player)) {
                        return cTo;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Search for soldier to fusion in the player's territory
     *
     * @param level     Level in which searched
     * @param moves     List of coordinates in which to search
     * @param cFrom     Original coordinate
     * @param player    Player starting the search
     * @return          Coordinate of a tile containing a soldier to fusion, null otherwise
     */

    static Coordinate fusion (Playable level, List<Coordinate> moves, Coordinate cFrom, Player player) {
        for (Coordinate cTo : moves) {
            if (!cFrom.equals(cTo)) {
                Tile current = level.get(cTo);
                if (current.getTerritory() != null) {
                    if (current.getTerritory().getOwner().equals(player)) {
                        if (current.getEntity() != null) {
                            if (current.getEntity() instanceof Soldier) {
                                if (((Soldier) current.getEntity()).getSoldierLevel().getLevel() == 0) {
                                    return cTo;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Find the nearest coordinate in the list of the original coordinate
     *
     * @param cFrom         Original coordinate
     * @param coordinates   List in which to look for the nearest coordinate
     * @return              The nearest coordinate to the original coordinates
     */

    private static Coordinate nearestCoordinates (Coordinate cFrom, List<Coordinate> coordinates) {
        int dstMin = 1000;
        if (coordinates.size() != 0 && cFrom != null) {
            Coordinate cMin = coordinates.get(0);
            for (Coordinate coordinate : coordinates) {
                int temp = HexManagement.distance(cFrom, coordinate);
                if (temp < dstMin) {
                    dstMin = temp;
                    cMin = coordinate;
                }
            }
            return cMin;
        }
        return null;
    }

    /**
     * Find the coordinate in the territory of the player closest to enemy territory
     *
     * @param cFrom     Original coordinate
     * @param level     Level in which searched
     * @param player    Player starting the search
     * @param toBuy     Boolean to differentiate the case of a purchase (true) from the case of a move (false)
     * @return          Coordinate found
     */

    static Coordinate searchEnnemy (Coordinate cFrom, Playable level, Player player, Soldier toBuy) {
        List<List<Coordinate>> allEnemy = loadTerritories(level, player, true);

        List<Coordinate> listNear = new ArrayList<Coordinate>();
        for (List<Coordinate> terrEnemy : allEnemy) {
            listNear.add(nearestCoordinates(cFrom, terrEnemy));
        }

        Coordinate cEnemy = nearestCoordinates(cFrom, listNear);

        if (toBuy == null) {
            return nearestCoordinates(cEnemy, level.getMoves(cFrom, 4));
        } else {
            return nearestCoordinates(cEnemy, level.getMoves(toBuy, cFrom));
        }
    }
}
