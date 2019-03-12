package be.ac.umons.slay.g02.level;

import com.badlogic.gdx.Gdx;

import net.bytebuddy.dynamic.scaffold.MethodGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.gui.screens.HexManagement;
import be.ac.umons.slay.g02.players.Player;


public class Level implements Playable {

    private Tile[][] tileMap;

    /**
     * Width of the level
     */
    private int width;

    /**
     * Height of the level
     */
    private int height;

    /**
     * List of players in this level
     */
    private Player[] players;

    /**
     * Current turn
     */
    private int turn = 0;

    /**
     * Current player
     */
    private Player currentPlayer;

    /**
     * Creates an empty level
     *
     * @param x the width of the level
     * @param y the height of the level
     */
    Level(int x, int y) {
        tileMap = new Tile[x][y];
        width = x;
        height = y;
    }

    void setPlayers(Player[] players) {
        this.players = players;
        turn = 0;
        currentPlayer = players[turn];

    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gives the maximum width of the level
     *
     * @return
     */
    @Override
    public int width() {
        return width;
    }

    /**
     * Gives the maximum height of the level
     *
     * @return
     */
    @Override
    public int height() {
        return height;
    }


    /**
     * Places the tile in parameter in the mentioned coordinates
     *
     * @param tile   The tile to place
     * @param coords The coordiantes to place the tile to
     */
    void set(Tile tile, Coordinate coords) {
        tileMap[coords.getX()][coords.getY()] = tile;
    }

    /**
     * Changes the entity of the tile at the mentioned coordinates
     * @param entity the entity to place
     * @param coords the coordinates of the tile to place the entity on.
     */
    public void set(Entity entity, Coordinate coords) {
        tileMap[coords.getX()][coords.getY()].setEntity(entity);
    }

    /**
     * Return the tile at the mentioned coordinates
     *
     * @param x
     * @param y
     * @return
     */
    @Override
    public Tile get(int x, int y) {
        return tileMap[x][y];
    }

    /**
     * Return the tile at the mentioned coordinates
     *
     * @param coords the coordinates for which we want the tile
     * @return A Tile
     */
    @Override
    public Tile get(Coordinate coords) {
        return tileMap[coords.getX()][coords.getY()];
    }

    void setTerritory(Territory t, Coordinate coords) {
        tileMap[coords.getX()][coords.getY()].setTerritory(t);
    }

    @Override
    public boolean buy(Entity entity, Coordinate coordinate) {
        return get(coordinate).buy(entity);
    }

    @Override
    public void nextTurn() {
        List<Territory> processed = new LinkedList<Territory>();

        turn = (turn + 1) % players.length;
        currentPlayer = players[turn];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Coordinate c = new Coordinate(i, j);
                Tile t = get(i, j);
                Territory terr = t.getTerritory();


                if (terr != null && !processed.contains(terr)) {
                    // Adds funds and kills soldier for all territories
                    t.getTerritory().nextTurn();
                    processed.add(terr);
                }

                // Spawn trees
                if (canSpawnTree(c)) {
                    t.setEntity(StaticEntity.TREE);
                }



                if (t.getTerritory() != null && t.getTerritory().getOwner().equals(currentPlayer)) {
                    if (t.getEntity() != null && t.getEntity() instanceof Soldier) {
                        ((Soldier) t.getEntity()).setMoved(false);
                    }
                }
            }
        }
    }

    /**
     * Returns true if a tree should be placed at the mentioned coordinate
     *
     * @param c
     * @return true if a tree shpuld be placed
     */
    private boolean canSpawnTree(Coordinate c) {
        Tile cell = get(c);
        if (cell.getEntity() != null || cell.getType() == TileType.WATER) {
            return false;
        }
        int treeCount = 0;
        for (Coordinate nbr : c.getNeighbors()) {
            if (isInLevel(nbr) && get(nbr).getEntity() == StaticEntity.TREE) {
                treeCount++;
            }
        }

        double odds = 0.01 + ((treeCount * Math.log10(treeCount + 1)) / 10);
        double random = new Random().nextDouble();

        return random < odds && turn == 0;
    }

    /**
     * Return the list of coordinates that can be reached
     * from the start coordinate limited to n steps
     *
     * @param start Starting coordinate
     * @param n     Maximum number of steps
     * @return List of coordinates that can be reached
     */
    @Override
    public List<Coordinate> getMoves(Coordinate start, int n) {
        ArrayList<Coordinate> visited = new ArrayList<Coordinate>();
        visited.add(start);
        List<ArrayList<Coordinate>> fringes = new ArrayList<ArrayList<Coordinate>>();
        ArrayList<Coordinate> startArray = new ArrayList<Coordinate>(1);
        startArray.add(start);
        fringes.add(startArray);

        for (int k = 1; k <= n; k++) {
            fringes.add(new ArrayList<Coordinate>());
            for (Coordinate c : fringes.get(k - 1)) {
                for (Coordinate neighbour : c.getNeighbors()) {
                    if (!visited.contains(neighbour) && canMove(start, neighbour)) {
                        // Not already visited and can move to

                        if (!get(neighbour).hasSameOwner(get(start))) {
                            // Not same owner so stop in this direction

                            visited.add(neighbour);
                        } else {
                            // Same owner so continue in this direction

                            visited.add(neighbour);
                            fringes.get(k).add(neighbour);
                        }
                    }
                }
            }
        }
        return visited;
    }

    /**
     * Returns a list of all entites we can buy in the territory at the mentionned coordianate.
     * @param p the coordinate where we want to buy the Entity
     * @return
     */
    @Override
    public List<Entity> canBuy(Coordinate p) {
        if (get(p).getTerritory() == null) {
            return null;
        }
        List<Entity> canBuyE = new ArrayList<Entity>();
        for (SoldierLevel lvl : SoldierLevel.values()) {
            Soldier s = new Soldier(lvl);
            if (get(p).getTerritory().canBuy(s)) {
                canBuyE.add(s);
            }
        }
        return canBuyE;
    }

    /**
     * Return if it's possible to go from the starting coordinates
     * to the arrival coordinates
     *
     * @param fromC Starting coordinate
     * @param toC   Arrival coordinate
     * @return True if it's possible, else false
     */
    private boolean canMove(Coordinate fromC, Coordinate toC) {
        if (fromC.equals(toC) || !get(fromC).getTerritory().getOwner().equals(currentPlayer)) {
            // Prevent movement on the starting cell
            return false;
        }

        // Get the matching tiles
        Tile from = get(fromC);
        Tile to = get(toC);

        if (to.getType().equals(TileType.NEUTRAL) && from.getEntity() instanceof Soldier && !(((Soldier) from.getEntity()).getMoved())) {
            //Prevent movement int the water, verify that you are trying to move a soldier and entity has not already moved

            if (to.getTerritory() == null) {
                // Moving to a cell that doesn't belong to anyone
                return true;
            }

            else if (to.hasSameOwner(from)) {
                // Moving in the same territory

                if (to.getEntity() != null && to.getEntity() instanceof Soldier) {
                    // Moving on other soldier

                    int toLvl = ((Soldier) to.getEntity()).getSoldierLevel().getLevel();
                    int fromLvl = ((Soldier) from.getEntity()).getSoldierLevel().getLevel();

                    // Soldier can fusion ?
                    return toLvl + fromLvl < 3;

                }
                return true;
            }

            else {
                // Moving in a other territory

                if (to.getEntity() instanceof Soldier) {
                    // The arrival cell contains a soldier so check if it can be attacked

                    return ((Soldier) from.getEntity()).canAttack((Soldier) to.getEntity());
                }

                else if (to.getEntity() == StaticEntity.CAPITAL) {
                    // The arrival cell contains a capital so check if we can attack it

                    return ((Soldier) from.getEntity()).getSoldierLevel().getLevel() > 0;
                }

                else {
                    // Check if a soldier is watching the arrival cell

                    // Get the neighbour direct from the arrival cell
                    Coordinate[] neighbors = toC.getNeighbors();
                    boolean bool = true;

                    for (Coordinate coordinate : neighbors) {
                        // Check all neighbours
                        Tile current = get(coordinate);

                        if (current.getType().equals(TileType.NEUTRAL)
                                && current.getEntity() != null) {
                            // To not check in the water and avoid nullPointerException

                            if (current.getEntity() instanceof Soldier
                                    && to.hasSameOwner(current)) {
                                // If a soldier has been found
                                //      and belongs to the same owner as the arrival cell

                                bool = ((Soldier) from.getEntity()).canAttack(
                                        (Soldier) current.getEntity());

                            }
                        }

                    }
                    return bool;
                }
            }
        }
        return false;
    }

    /**
     * Move an entity from the start coordinates to the arrival coordinates
     *
     * @param fromC Start coordinates
     * @param toC   Arrival coordinates
     */
    @Override
    public void move(Coordinate fromC, Coordinate toC) {

        // Load the list of possible moves
        List<Coordinate> listMoves = getMoves(fromC, 4);

        // Check that the arrival coordinates are in the list of possible moves
        //      and check that coordinates are different
        if (listMoves.contains(toC) && !fromC.equals(toC)) {
            Tile to = get(toC);
            Tile from = get(fromC);

            // Prevent movement on its own capital
            if (to.hasSameOwner(from)) {
                if (to.getEntity() != StaticEntity.CAPITAL) {
                    if (to.getEntity() instanceof Soldier) {
                        // Soldier fusion
                        int toLvl = ((Soldier) to.getEntity()).getSoldierLevel().getLevel();
                        int fromLvl = ((Soldier) from.getEntity()).getSoldierLevel().getLevel();
                        int newLvl = toLvl + fromLvl + 1;
                        if (((Soldier) to.getEntity()).getMoved()) {
                            // If to soldier has already moved (no need to check each other because it can move)
                            to.setEntity(new Soldier(SoldierLevel.fromLevel(newLvl), true));
                        } else {
                            to.setEntity(new Soldier(SoldierLevel.fromLevel(newLvl), false));
                        }
                        from.setEntity(null);

                    } else {
                        // Just move the Entity
                        to.setEntity(from.getEntity());
                        from.setEntity(null);
                        to.setTerritory(from.getTerritory());
                        ((Soldier) to.getEntity()).setMoved(true);
                    }

                }
            } else {
                // Move the Entity
                to.setEntity(from.getEntity());
                from.setEntity(null);
                to.setTerritory(from.getTerritory());
                ((Soldier) to.getEntity()).setMoved(true);

            }

            splitTerritories(); //TODO find a better approach
            mergeTerritories();
        }
    }


    /**
     * Merges the territories of adjacent cells
     */
    public void mergeTerritories() {
        List<Coordinate> processed = new LinkedList<Coordinate>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Coordinate pos = new Coordinate(i, j);
                if (!processed.contains(pos)) {
                    mergeTerritories(pos, processed);
                }
            }
        }
    }

    /**
     * Merges the territories from a specific position.
     *
     * @param pos
     * @param processed
     */
    private void mergeTerritories(Coordinate pos, List<Coordinate> processed) {
        // Source: https://codereview.stackexchange.com/questions/90108/recursively-evaluate-neighbors-in-a-two-dimensional-grid

        // Base case: We already checked this cell
        if (!processed.contains(pos)) {
            processed.add(pos);

            int x = pos.getX();
            int y = pos.getY();

            Coordinate[] neighbors = pos.getNeighbors();

            for (Coordinate nb : neighbors) {
                if (isInLevel(nb)) {
                    if (tileMap[x][y].mergeTerritories(tileMap[nb.getX()][nb.getY()])) {
                        mergeTerritories(nb, processed);
                    }
                }
            }
        }
    }

    public void splitTerritories() {
        /*
        Idea: We start from one cell and we keep in this territory all the cells that are
        adjacent to this one and in the same territory. All the others are separated in another
        territory and continue to split until all the cells in the territory are adjacent.
         */

        List<Territory> processedTerritories = new LinkedList<Territory>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Coordinate pos = new Coordinate(i, j);
                Tile cell = tileMap[i][j];

                if (!cell.hasTerritory()) {
                    continue;
                }

                if (!processedTerritories.contains(cell.getTerritory())) { // TODO check that this works
                    List<Coordinate> neighbours = neighbourTilesInSameTerritory(pos);
                    List<Tile> tiles = new LinkedList<Tile>();  // List of all tiles in neighbourhood
                    for (Coordinate p : neighbours) {         // in the same territory
                        tiles.add(tileMap[p.getX()][p.getY()]);
                    }
                    // If the list is equal
                    if (tiles.containsAll(cell.getTerritory().getCells())) {
                        // If all the cells in the territory are in the neighbourhood,
                        // we don't have to split anything
                        // TODO useless because being checked after ?
                        continue;
                    } else {
                        // Remove all the cells that are not in the neighborhood
                        // and create a new territory for them
                        Territory newTerr = new Territory(cell.getTerritory().getOwner());
                        processedTerritories.add(newTerr);
                        List<Tile> tilesInTerritory = cell.getTerritory().getCells();
                        Tile[] tilesArray = new Tile[tilesInTerritory.size()];
                        tilesInTerritory.toArray(tilesArray);
                        for (Tile t : tilesArray) {
                            if (!tiles.contains(t)) {
                                t.getTerritory().remove(t);
                                t.setTerritory(newTerr);
                            }
                        }
                    }
                    processedTerritories.add(cell.getTerritory());
                }
            }
        }

        for (Territory t : processedTerritories) {
            t.newCapital();
        }
    }

    /**
     * Lists all the cells in a neighbourhood, which is all adjacent cells
     * that are part of the same territory.
     *
     * @param pos The position of one cell of the neighbourhood
     * @return A list of all the cells in the neighbourhood of which contains the position pos
     */
    @Override
    public List<Coordinate> neighbourTilesInSameTerritory(Coordinate pos) {
        List<Coordinate> neighbours = new LinkedList<Coordinate>();
        Tile cell = tileMap[pos.getX()][pos.getY()];

        if (cell.getTerritory() == null) {
            return neighbours;
        } else {
            neighbourTilesInSameTerritory(pos, cell.getTerritory(), neighbours);
            return neighbours;
        }
    }

    /**
     * Adds all of the neighbours in the same territory to the known list
     *
     * @param pos       The current position
     * @param territory the territory of the neighbourhood
     * @param known     All already known cells in the neighbourhood in the same territory
     */
    private void neighbourTilesInSameTerritory(Coordinate pos, Territory territory, List<Coordinate> known) {
        Tile cell = tileMap[pos.getX()][pos.getY()];
        if (cell.getTerritory() == null) {
            return;
        }
        if (known.contains(pos)) {
            return;
        }
        if (!cell.getTerritory().equals(territory)) {
            return;
        }
        known.add(pos);

        for (Coordinate nbr : pos.getNeighbors()) {
            neighbourTilesInSameTerritory(nbr, territory, known);
        }
    }

    public int countTerritories() {
        List<Territory> territories = new LinkedList<Territory>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Tile cell = tileMap[i][j];
                if (cell.hasTerritory()) {
                    if (!territories.contains(cell.getTerritory())) {
                        territories.add(cell.getTerritory());
                    }
                }
            }
        }
        return territories.size();
    }

    /**
     * Returns true if the position c is in the level
     *
     * @param c the position to check
     * @return true if the position c is in the level
     */
    @Override
    public boolean isInLevel(Coordinate c) {
        return c.getX() > 0 && c.getX() < width && c.getY() > 0 && c.getY() < height;
    }
}
