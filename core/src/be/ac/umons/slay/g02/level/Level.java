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

    public ArrayList<Coordinate> getMovePoss(Coordinate coord) {
        ArrayList<Coordinate> res = new ArrayList<Coordinate>();
        if (tileMap[coord.getX()][coord.getY()].getTerritory() != null) {
            int n = 0; //mettre la position de départ
            getMove(res, coord, coord);
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

    private void getMove(ArrayList<Coordinate> list, Coordinate coord, Coordinate initial) {
        Coordinate[] neighbors = coord.getNeighbors();
        Tile initTile = tileMap[initial.getX()][initial.getY()];
        if (!list.contains(coord)) {
            list.add(coord);
        }

        for (Coordinate curr : neighbors) {
            Tile current = tileMap[curr.getX()][curr.getY()];

            if (current.getType().equals(TileType.NEUTRAL) && !list.contains(curr)) {

                int distance = HexManagement.distance(curr, initial);
                if (((current.getTerritory()) == null || !current.getTerritory().hasSameOwner(initTile.getTerritory())) && distance <= 4) {
                    list.add(curr);
                } else {
                    if (current.getTerritory() != null && current.getTerritory().hasSameOwner(initTile.getTerritory()) && distance <= 4) {
                        getMove(list, curr, initial);
                    }
                }
            }
        }
    }

    /**
     * Move entity
     * <p>
     * Les autres cas devront être éliminés avant de pouvoir faire appel à cette méthode (au moment de déterminer leur périmètre de déplacement)
     *
     * @param oldCoord
     * @param newCoord
     */
    public boolean move(Coordinate oldCoord, Coordinate newCoord) { //TODO return true or false

        if (oldCoord.equals(newCoord)) {
            // We don't move to the same cell
            return false;
        }

        Tile from = this.tileMap[oldCoord.getX()][oldCoord.getY()];
        Tile to = this.tileMap[newCoord.getX()][newCoord.getY()];
        if (to.getType().equals(TileType.NEUTRAL)) {

            if (from.getEntity() instanceof Soldier) {
                if (from.isEmpty()) {
                    // TODO Erreur car rien à déplacer
                }
                if (to.isEmpty()) { // juste déplacer l'entité vers les nouvelles coordonnées
                    moveEntity(oldCoord, newCoord);
                    return true;
                } else {
                    if (to.getEntity() instanceof be.ac.umons.slay.g02.entities.StaticEntity) {
                        if (to.getEntity() == StaticEntity.TREE) { //c'est un arbre séparation du cas où l'arbre appartient au territoire ou non pour les pièces gagnées
                            if (from.getTerritory().hasSameOwner(to.getTerritory())) {
                                moveEntity(oldCoord, newCoord);
                            } else {
                                moveEntity(oldCoord, newCoord);
                            }
                            return true;
                        } else if (to.getEntity() == StaticEntity.GRAVE) {
                            moveEntity(oldCoord, newCoord);
                            return true;
                        } else if (to.getEntity() == StaticEntity.CAPITAL) {
                            if (((Soldier) from.getEntity()).getSoldierLevel().getLevel() > 1) {
                                moveEntity(oldCoord, newCoord);
                                return true;
                            }
                            return false;
                        }
                    } else if (to.getEntity() instanceof Soldier) {
                        //TODO soldat du même territoire les fusionner
                        if (to.getTerritory().hasSameOwner(from.getTerritory())) {
                            int fromSold = ((Soldier) from.getEntity()).getSoldierLevel().getLevel();
                            int toSold = ((Soldier) to.getEntity()).getSoldierLevel().getLevel();
                            if (fromSold == 3 || toSold == 3 || (fromSold + toSold) >= 3) {
                                return false;
                            } else {
                                to.setEntity(new Soldier(SoldierLevel.fromLevel(fromSold + toSold)));
                                from.setEntity(null);
                                return true;
                            }
                        }

                        if (((Soldier) from.getEntity()).canAttack((Soldier) to.getEntity())) {
                            moveEntity(oldCoord, newCoord);
                            return true;
                        } else {
                            from.setEntity(null);
                            return false;
                            /*utile dans le cas L3 contre L3 pour tuer celui qui se déplace si perdu le combat */
                        }
                    }
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

    private void moveEntity(Coordinate oldCoord, Coordinate newCoord) {
        Tile newTile = tileMap[newCoord.getX()][newCoord.getY()];
        Tile oldTile = tileMap[oldCoord.getX()][oldCoord.getY()];

        // Move the Entity
        newTile.setEntity(oldTile.getEntity());
        oldTile.setEntity(null);
        newTile.setTerritory(oldTile.getTerritory());

        // The soldier left the old tile so we add +1 to the income
        newTile.getTerritory().changeIncome(1);

        splitTerritories(); //TODO find a better approach
        mergeTerritories();

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
            neighbourTilesInSameTerritory(new Coordinate(nbr.getX(), pos.getY()), territory, known);
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
