package be.ac.umons.slay.g02.level;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.gui.screens.HexManagement;


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
     * Creates an empty level
     *
     * @param x the width of the level
     * @param y the height of the level
     */
    public Level(int x, int y) {
        tileMap = new Tile[x][y];
        width = x;
        height = y;
    }

    /**
     * Gives the maximum width of the level
     *
     * @return
     */
    public int width() {
        return width;
    }

    /**
     * Gives the maximum height of the level
     * @return
     */
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

    void set(Entity entity, Coordinate coords) {
        tileMap[coords.getX()][coords.getY()].setEntity(entity);
    }

    /**
     * Return the tile at the mentioned coordinates
     * @param x
     * @param y
     * @return
     */
    public Tile get(int x, int y) {
        return tileMap[x][y];
    }

    /**
     * Return the tile at the mentioned coordinates
     *
     * @param coords the coordinates for which we want the tile
     * @return A Tile
     */
    public Tile get(Coordinate coords) {
        return tileMap[coords.getX()][coords.getY()];
    }

    void setTerritory(Territory t, Coordinate coords) {
        tileMap[coords.getX()][coords.getY()].setTerritory(t);
    }

    public void select(Coordinate coordinate) {

    }

    public void buy(Entity entity, Coordinate coordinate) {

    }

    public void endTurn() {

    }

    /**
     * Method that initialise variable for recursive method
     *
     * @param coord Coordinate to research
     * @return List of coordinates you can move
     */

    public ArrayList<Coordinate> getMoves(Coordinate coord) {
        ArrayList<Coordinate> res = new ArrayList<Coordinate>();
        if (tileMap[coord.getX()][coord.getY()].getTerritory() != null) {
            res.add(coord);
            getMoves(res, coord, coord);
            return res;
        }
        return res;
    }

    /**
     * Recursive method to add coordinate where can move
     *
     * @param list  List of coordinates
     * @param coord Actual coordinate
     */

    private void getMoves(ArrayList<Coordinate> list, Coordinate coord, Coordinate initial) {
        // Tester avec can move

        Coordinate[] neighbors = coord.getNeighbors();
        Tile initTile = tileMap[initial.getX()][initial.getY()];
        if (!list.contains(coord) && canMove(initial, coord)) {
            list.add(coord);
        }

        for (Coordinate curr : neighbors) {
            Tile current = tileMap[curr.getX()][curr.getY()];

            if (current.getType().equals(TileType.NEUTRAL)
                    && !list.contains(curr)
                    && canMove(initial, curr)) {

                int distance = HexManagement.distance(curr, initial);
                if (((current.getTerritory()) == null || !current.getTerritory().hasSameOwner(initTile.getTerritory())) && distance <= 4) {
                    list.add(curr);
                } else {
                    if (current.getTerritory() != null && current.getTerritory().hasSameOwner(initTile.getTerritory()) && distance <= 4) {
                        getMoves(list, curr, initial);
                    }
                }
            }
        }
    }

    private boolean canMove (Coordinate oldC, Coordinate newC) {
        if (oldC.equals(newC)) { // Empêche dplt sur sa propre cellule
            return false;
        }
        Tile from = get(oldC);
        Tile to = get(newC);
        if (to.getType().equals(TileType.NEUTRAL) && from.getEntity() instanceof Soldier) { // Empêhce dplct dans eau et vérif qu'il s'agit d'un soldat à dpl
            if (to.getTerritory() == null) { // Dplct vers une cellule n'appartenant à personne
                return true;

            } else if (to.hasSameOwner(from)) { // Dplct dans même territoire
                return true;

            } else { // Dplct dans un territoire ennemie
                if (to.getEntity() instanceof Soldier) { // Il y a un soldat sur la cellule ennemie
                    return ((Soldier) from.getEntity()).canAttack((Soldier) to.getEntity());

                } else if (to.getEntity() == StaticEntity.CAPITAL) {
                    return ((Soldier) from.getEntity()).getSoldierLevel().getLevel() > 0;

                } else { // Il faut vérifier s'il y a un soldat aux alentours
                    Coordinate[] neighbors = newC.getNeighbors();
                    Boolean bool = true;
                    for (Coordinate coordinate : neighbors) {
                        Tile current = get(coordinate);
                        if (current.getType().equals(TileType.NEUTRAL) // Ca sert à rien de regarder dans l'eau
                                && current.getEntity() != null) { // Eviter nullPointerException
                            if (current.getEntity() instanceof Soldier // Il y a un soldat dans le coin ?
                                    && to.hasSameOwner(current)) { // Appartient au même proprio que la cellule où on veut aller
                                bool = ((Soldier) from.getEntity()).canAttack((Soldier) current.getEntity());

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
     * Move entity from old coordinate to new coordinate
     *
     * @param oldCoord old coordinates
     * @param newCoord new coordinates
     */

    public void move(Coordinate oldCoord, Coordinate newCoord) {
        // Charge liste dplt poss + check new est dedans
        ArrayList<Coordinate> listMoves = getMoves(oldCoord);
        if (listMoves.contains(newCoord) && !oldCoord.equals(newCoord)) {
            Tile to = tileMap[newCoord.getX()][newCoord.getY()];
            Tile from = tileMap[oldCoord.getX()][oldCoord.getY()];

            if (to.hasSameOwner(from)) {
                if (to.getEntity() != StaticEntity.CAPITAL) { // Empêcher de bouffer sa propre capitale
                    // Move the Entity
                    to.setEntity(from.getEntity());
                    from.setEntity(null);
                    to.setTerritory(from.getTerritory());

                }
            } else {
                // Move the Entity
                to.setEntity(from.getEntity());
                from.setEntity(null);
                to.setTerritory(from.getTerritory());

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

        if (processed.contains(pos)) {
            // Base case: We already checked this cell
            return;
        } else {
            processed.add(pos);

            int x = pos.getX();
            int y = pos.getY();

            Coordinate[] neighbors = pos.getNeighbors();
            //Gdx.app.log("slay", Arrays.toString(neighbors));

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
    }

    /**
     * Lists all the cells in a neighbourhood, which is all adjacent cells
     * that are part of the same territory.
     * @param pos The position of one cell of the neighbourhood
     * @return A list of all the cells in the neighbourhood of which contains the position pos
     */
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
     * @param c the position to check
     * @return true if the position c is in the level
     */
    private boolean isInLevel(Coordinate c) {
        return c.getX() > 0 && c.getX() < width && c.getY() > 0 && c.getY() < height;
    }
}
