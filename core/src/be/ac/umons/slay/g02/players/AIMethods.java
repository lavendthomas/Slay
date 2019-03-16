package be.ac.umons.slay.g02.players;

import java.util.ArrayList;
import java.util.List;

import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Territory;
import be.ac.umons.slay.g02.level.Tile;

public class AIMethods {

    static boolean chopTrees(Playable level, Coordinate coordinate, Player player) {
        List<Coordinate> listMoves = level.getMoves(level.get(coordinate).getEntity(), coordinate);
        for (Coordinate c : listMoves) {
            Tile tile = level.get(c);
            if (tile.getEntity() != null) {
                if (tile.getEntity() == StaticEntity.TREE && tile.getTerritory().getOwner().equals(player)) {
                    level.move(coordinate, c);
                    return true;
                }
            }
        }
        return false;
    }

    static List<List<Coordinate>> loadTerritories (Playable level, Player player) {
        List<List<Coordinate>> allTerritories = new ArrayList<List<Coordinate>>();
        List<Coordinate> visited = new ArrayList<Coordinate>();

        for (int i = 0; i < level.width(); i++) {
            for (int j = 0; j < level.height(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                Tile current = level.get(i, j);
                if (current.getTerritory().getOwner().equals(player) && !visited.contains(coordinate)) {
                    List<Coordinate> territory = level.neighbourTilesInSameTerritory(coordinate);
                    allTerritories.add(territory);
                    visited.add(coordinate);
                }
            }
        }
        return allTerritories;
    }


}
