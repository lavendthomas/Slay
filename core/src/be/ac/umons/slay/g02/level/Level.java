package be.ac.umons.slay.g02.level;

import java.util.LinkedList;
import java.util.List;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.entities.StaticEntity;


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

    public void select(Coordinate coordinate) {

    }

    public void buy(Entity entity, Coordinate coordinate) {

    }

    public void endTurn() {

    }

    public Entity getEntity() {
        return null;
    }

    /**
     * Move entity
     *
     * Les autres cas devront être éliminés avant de pouvoir faire appel à cette méthode (au moment de déterminer leur périmètre de déplacement)
     *
     * @param oldCoord
     * @param newCoord
     */
    public boolean move(Coordinate oldCoord, Coordinate newCoord) { //TODO return true or false
        Tile from = this.tileMap[oldCoord.getX()][oldCoord.getY()];
        Tile to = this.tileMap[newCoord.getX()][newCoord.getY()];
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
                            to.getTerritory().addCoins(3);
                            to.getTerritory().incrIncome();
                        } else {
                            moveEntity(oldCoord, newCoord);
                        }
                        return true;
                    } else if (to.getEntity() == StaticEntity.GRAVE) {
                        moveEntity(oldCoord, newCoord);
                        return true;
                    } else if (to.getEntity() == StaticEntity.CAPITAL) {
                        if (((Soldier) from.getEntity()).getSoldierLevel().getLevel() > 1 ) {
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
        return false;
    }

    private void moveEntity(Coordinate oldCoord, Coordinate newCoord) {
        this.tileMap[newCoord.getX()][newCoord.getY()].setEntity(this.tileMap[oldCoord.getX()][oldCoord.getY()].getEntity());
        this.tileMap[oldCoord.getX()][oldCoord.getY()].setEntity(null);
    }


        /**
         * Merges the territories of adjacent cells
         */
    public void mergeTerritories() {
        List<Coordinate> processed = new LinkedList<Coordinate>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Coordinate pos = new Coordinate(i, j);
                mergeTerritories(pos, processed);
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
            // Try to merge territory with cell on the left
            if (x > 1) {
                tileMap[x][y].mergeTerritories(tileMap[x - 1][y]);
                mergeTerritories(new Coordinate(x - 1, y), processed);
            }
            // Try to merge territory with the cell on the right
            if (x < width - 1) {
                tileMap[x][y].mergeTerritories(tileMap[x + 1][y]);
                mergeTerritories(new Coordinate(x + 1, y), processed);
            }
            // Try to merge territory with the cell above
            if (y > 1) {
                tileMap[x][y].mergeTerritories(tileMap[x][y - 1]);
                mergeTerritories(new Coordinate(x, y - 1), processed);
            }
            // Try to merge territory with the cell below
            if (y > 1) {
                tileMap[x][y].mergeTerritories(tileMap[x][y + 1]);
                mergeTerritories(new Coordinate(x, y + 1), processed);
            }
        }
    }

    private void splitTerritories() {
        /*
        Idea: We start from one cell and we keep in this territory all the cells that are
        adjacent to this one and in the same territory. All the others are separated in another
        territory and continue to split until all the cells in the territory are adjacent.
         */
    }

}
