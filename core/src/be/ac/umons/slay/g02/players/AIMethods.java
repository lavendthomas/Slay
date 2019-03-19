package be.ac.umons.slay.g02.players;

import java.util.ArrayList;
import java.util.List;

import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Tile;

class AIMethods {

    static List<List<Coordinate>> loadTerritories (Playable level) {
        List<List<Coordinate>> allTerritories = new ArrayList<List<Coordinate>>();
        List<Coordinate> visited = new ArrayList<Coordinate>();

        for (int i = 0; i < level.width(); i++) {
            for (int j = 0; j < level.height(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                Tile current = level.get(i, j);

                if (current.getTerritory() != null &&
                        current.getTerritory().getOwner().equals(level.getCurrentPlayer())
                        && !visited.contains(coordinate)) {
                    List<Coordinate> territory = level.neighbourTilesInSameTerritory(coordinate);
                    allTerritories.add(territory);
                    visited.addAll(territory);
                }
            }
        }
        return allTerritories;
    }
    static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
