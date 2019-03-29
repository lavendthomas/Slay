package be.ac.umons.slay.g02.gui.screens;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import be.ac.umons.slay.g02.level.Coordinate;

import static java.lang.Math.abs;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

/**
 * Class to handle calculations related to hexes
 */

public class HexManagement {

    /**
     * Method to draw a tile on a layer with given coordinates
     *
     * @param coord Coordinate of the tile to change
     * @param tile  TiledMapTile
     * @param layer TiledMapTileLayer
     */
    static void drawTile(Coordinate coord, TiledMapTile tile, TiledMapTileLayer layer) {
        if (coord.getX() >= 0 && coord.getX() < layer.getWidth() && coord.getY() >= 0 && coord.getY() < layer.getTileHeight()) {
            if (layer.getCell(coord.getX(), coord.getY()) == null) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(tile);
                layer.setCell(coord.getX(), coord.getY(), cell);
            } else {
                layer.getCell(coord.getX(), coord.getY()).setTile(tile);
            }
        }
    }

    /**
     * Method to erase a tile on a layer with given coordinates
     *
     * @param coord Coordinate of the tile to erase
     * @param layer TiledMapTileLayer
     */

    static void eraseTile(Coordinate coord, TiledMapTileLayer layer) {
        if (coord.getX() >= 0 && coord.getX() < layer.getWidth() && coord.getY() >= 0 && coord.getY() < layer.getTileHeight()) {
            layer.setCell(coord.getX(), coord.getY(), new TiledMapTileLayer.Cell());
        }
    }

    /**
     * Method to convert the coordinates given in pixels into coordinates in the table of hexagons
     *
     * @param xp   Integer x axis in pixel
     * @param yp   Integer y axis in pixel
     * @param size Integer size of hexagon
     * @return Coordinates of hexagon in the table
     */

    static Coordinate pixelToHex(int xp, int yp, int size) {
        double x = (2 / 3f * xp) / size;
        double y = (-xp / 3f + sqrt(3) / 3f * yp) / size;
        return cubeToOddq(cubeRound(x, -x - y, y));

    }

    /**
     * Method to convert the cube coordinates into table coordinates
     *
     * @param cube Cube coordinate
     * @return Table coordinate
     */

    private static Coordinate cubeToOddq(Cube cube) {
        int col = cube.x;
        int row = cube.z + (cube.x + (cube.x & 1)) / 2;
        return new Coordinate(col, row);
    }

    /**
     * Method to round to the nearest hexagon in system of cube coordinate
     *
     * @param x Coordinate x of cube to be rounded
     * @param y Coordinate y of cube to be rounded
     * @param z Coordinate z of cube to be rounded
     * @return The nearest cube
     */

    private static Cube cubeRound(double x, double y, double z) {
        int rx = (int) round(x);
        int ry = (int) round(y);
        int rz = (int) round(z);

        double x_diff = abs(rx - x);
        double y_diff = abs(ry - y);
        double z_diff = abs(rz - z);

        if ((x_diff > y_diff) && (x_diff > z_diff)) {
            rx = -ry - rz;
        } else if (y_diff > z_diff) {
            ry = -rx - rz;
        } else {
            rz = -rx - ry;
        }
        return new Cube(round(rx), round(ry), round(rz));
    }

    public static int distance(Coordinate a, Coordinate b) {
        Cube ac = oddqToCube(a);
        Cube bc = oddqToCube(b);
        return (Math.abs(ac.x - bc.x) + Math.abs(ac.y - bc.y) + Math.abs(ac.z - bc.z)) / 2;
    }

    private static Cube oddqToCube(Coordinate hex) {
        int x = hex.getY();
        int z = hex.getX() - (hex.getY() - (hex.getY() & 1)) / 2;
        int y = -x - z;
        return new Cube(x, y, z);
    }


    /**
     * Inner class representing a hexagon in cubic coordinates
     */
    private static class Cube {
        int x;
        int y;
        int z;

        /**
         * Constructor of the class
         *
         * @param x Integer representing the cubic x coordinate
         * @param y Integer representing the cubic y coordinate
         * @param z Integer representing the cubic z coordinate
         */

        private Cube(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
