package be.ac.umons.slay.g02.players;

import java.util.ArrayList;
import java.util.List;

import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.gui.screens.HexManagement;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Tile;

public class AIMethods {
    private static int speed = 333; // 3 tours / seconde

    static List<List<Coordinate>> loadTerritories (Playable level, Player player, Boolean searchEnemy) {
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

    static void sleep() {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setSpeed (int sp) {
        speed = sp;
    }

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

    static int searchStrongerSoldier (Playable level, Player player, Boolean searchEnemy) {
        List<List<Coordinate>> territories = loadTerritories(level, player, searchEnemy);
        int strongestLevel = -1;

        for (List<Coordinate> territory : territories) {
            for (Coordinate coordinate : territory) {
                Tile current = level.get(coordinate);

                if (current.getTerritory() != null) {
                    if (searchEnemy) {
                        if (!current.getTerritory().getOwner().equals(player)) {
                            if (current.getEntity() != null && current.getEntity() instanceof Soldier) {
                                strongestLevel = Math.max(strongestLevel,
                                        ((Soldier) current.getEntity()).getSoldierLevel().getLevel());
                            }
                        }
                    } else {
                        if (current.getTerritory().getOwner().equals(player)) {
                            if (current.getEntity() != null && current.getEntity() instanceof Soldier) {
                                strongestLevel = Math.max(strongestLevel,
                                        ((Soldier) current.getEntity()).getSoldierLevel().getLevel());
                            }
                        }
                    }
                }
            }
        }
        return strongestLevel;

    }
}
