package be.ac.umons.slay.g02.level;

import java.util.List;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.players.Player;

/**
 *  An interface for level
 */
public interface Playable {
    /**
     * Width of the level
     */
    int width();

    /**
     * Height of the level
     */
    int height();

    /**
     * Returns the tile at the mentioned coordinates
     *
     * @param x the x coordinate (width)
     * @param y the y coordinate (height)
     * @return the tile at that position
     */
    Tile get(int i, int j);

    /**
     * Returns the tile at the mentioned coordinates
     *
     * @param coords the coordinates for which we want the tile
     * @return a Tile
     */
    Tile get(Coordinate pos);

    /**
     * Returns true if the position c is in the level
     *
     * @param c the position to check
     * @return true if the position c is in the level
     */
    boolean isInLevel(Coordinate c);

    /**
     * Lists all cells in a neighbourhood, which represents all adjacent cells
     * that are part of the same territory
     *
     * @param pos the position of one cell of the neighbourhood
     * @return a list of all the cells in the neighbourhood containing the position pos
     */
    List<Coordinate> neighbourTilesInSameTerritory(Coordinate pos);

    /**
     * Used to load positions in which to place a soldier freshly bought
     * Used to buy a soldier
     *
     * @param entity the new entity to place
     * @param start  the origin tile
     * @return Coordinates list
     */
    List<Coordinate> getMoves(Entity entity, Coordinate start);

    /**
     * Returns the list of coordinates that can be reached
     * from the start coordinate limited to n steps
     * Used to move a soldier
     *
     * @param start the starting coordinate
     * @param n     the maximum number of steps
     * @return List of coordinates that can be reached
     */
    List<Coordinate> getMoves(Coordinate start, int n);

    /**
     * Moves an entity from the start coordinates to the arrival coordinates
     *
     * @param fromC the start coordinates
     * @param toC   the arrival coordinates
     */
    void move(Coordinate from, Coordinate to);

    /**
     * Buys an entity
     *
     * @param entity the new entity to place
     * @param start  the origin Tile
     * @param to     the tile in which to place the entity
     * @return true is an entity has successfully been bought
     */
    boolean buy(Entity entity, Coordinate start,  Coordinate to);

    /**
     * Returns a list of all purchasable entities in the territory at the mentionned coordinate
     *
     * @param p the coordinate where we want to buy the Entity
     * @return List of entities we can buy
     */
    List<Entity> canBuy(Coordinate p);

    /**
     * Before perform turn change actions, verify if there is a winner using the method hasWon
     *
     * @return true if there is no winner and the game should be continued
     */
    boolean nextTurn();

    /**
     * Checks if a player won the game
     *
     * @return a player if he won the game, null if no one did
     */
    Player hasWon();

    /**
     *  Get the player whose turn it is
     *
     * @return a player
     */
    Player getCurrentPlayer();

    /**
     * Places the tile in parameter in the mentioned coordinates
     *
     * @param tile   The tile to place
     * @param coords The coordinates on which to place the tile
     */
    void set(Tile tile, Coordinate coords);

    /**
     * Changes the entity of the tile at the mentioned coordinates
     *
     * @param entity the entity to place
     * @param coords the coordinates of the tile on which to place the entity
     */
    void set(Entity entity, Coordinate coords);

    /**
     * Loads players, initializes the turn and the current player
     *
     * @param players Tab of players
     */
    void setPlayers(Player[] players);

    /**
     * Merges the territories of adjacent cells
     */
    void mergeTerritories();

    /**
     * Returns if it is possible to go from the starting coordinates
     * to the arrival coordinates
     *
     * @param fromC the starting coordinate
     * @param toC   the arrival coordinate
     * @return True if it is possible, false otherwise
     */
    boolean canMove(Coordinate fromC, Coordinate toC);
}
