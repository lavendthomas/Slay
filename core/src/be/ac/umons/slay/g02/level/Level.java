package be.ac.umons.slay.g02.level;

import java.util.LinkedList;
import java.util.List;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.entities.Soldier;
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

    public void moveEntity(Coordinate oldCoord, Coordinate newCoord) {
        if (this.tileMap[oldCoord.getX()][oldCoord.getY()].isEmpty()) {
            //Erreur
        }
        if (this.tileMap[newCoord.getX()][newCoord.getY()].isEmpty()) {
            this.tileMap[newCoord.getX()][newCoord.getY()].setEntity(this.tileMap[oldCoord.getX()][oldCoord.getY()].getEntity());
            this.tileMap[oldCoord.getX()][oldCoord.getY()].setEntity(null);
        } else {
            if (this.tileMap[oldCoord.getX()][oldCoord.getY()].getEntity() instanceof be.ac.umons.slay.g02.entities.StaticEntity) {
                if (this.tileMap[oldCoord.getX()][oldCoord.getY()].getEntity() == StaticEntity.TREE) {
                    //Le couper + gain pièces
                } else if (this.tileMap[oldCoord.getX()][oldCoord.getY()].getEntity() == StaticEntity.GRAVE) {
                    //La supprimer et se déplacer
                } else if (this.tileMap[oldCoord.getX()][oldCoord.getY()].getEntity() == StaticEntity.CAPITAL) {
                    // Vérifier que le soldat et d'un niveau suffisant pour la détruire et déplacer
                }
            } else {
                if (((Soldier) this.tileMap[oldCoord.getX()][oldCoord.getY()].getEntity()).canAttack((Soldier) this.tileMap[newCoord.getX()][newCoord.getY()].getEntity())) {
                    this.tileMap[newCoord.getX()][newCoord.getY()].setEntity(this.tileMap[oldCoord.getX()][oldCoord.getY()].getEntity());
                    this.tileMap[oldCoord.getX()][oldCoord.getY()].setEntity(null);
                } else {
                    this.tileMap[oldCoord.getX()][oldCoord.getY()].setEntity(null);
                    /*utile dans le cas L3 contre L3 pour tuer celui qui se déplace si perdu le combat
                     * Les autres cas devront être éliminés avant de pouvoir faire appel à cette méthode (au moment de déterminer leur périmètre de déplacement)*/
                }
            }

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

}
