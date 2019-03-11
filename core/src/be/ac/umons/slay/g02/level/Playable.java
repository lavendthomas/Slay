package be.ac.umons.slay.g02.level;

import java.util.List;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.players.Player;

public interface Playable {
    int width();

    int height();

    Tile get(int i, int j);

    Tile get(Coordinate pos);

    boolean isInLevel(Coordinate c);

    List<Coordinate> neighbourTilesInSameTerritory(Coordinate pos);

    List<Coordinate> getMoves(Coordinate start, int n);

    void move(Coordinate from, Coordinate to);

    boolean buy(Entity entity, Coordinate coordinate);

    List<Entity> canBuy(Coordinate p);

    void nextTurn();

    Player getCurrentPlayer();
}
