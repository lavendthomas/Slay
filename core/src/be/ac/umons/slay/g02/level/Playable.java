package be.ac.umons.slay.g02.level;

import java.util.List;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.players.Player;

/**
 *  An interface for level
 */
public interface Playable {
    int width();

    int height();

    Tile get(int i, int j);

    Tile get(Coordinate pos);

    boolean isInLevel(Coordinate c);

    List<Coordinate> neighbourTilesInSameTerritory(Coordinate pos);

    List<Coordinate> getMoves(Entity entity, Coordinate start);

    List<Coordinate> getMoves(Coordinate start, int n);

    void move(Coordinate from, Coordinate to);

    boolean buy(Entity entity, Coordinate start,  Coordinate to);

    List<Entity> canBuy(Coordinate p);

    boolean nextTurn();

    Player hasWon();

    Player getCurrentPlayer();

    void set(Tile tile, Coordinate coords);

    void set(Entity entity, Coordinate coords);

    void setPlayers(Player[] players);

    void mergeTerritories();

    boolean canMove(Coordinate fromC, Coordinate toC);
}
