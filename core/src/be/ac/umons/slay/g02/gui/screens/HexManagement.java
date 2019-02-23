package be.ac.umons.slay.g02.gui.screens;

import be.ac.umons.slay.g02.level.Coordinate;

import static java.lang.Math.abs;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class HexManagement {
    private int size;

    public HexManagement(int size) {
        this.size = size;
    }

    public Coordinate pixelToHex (int xp, int yp) {
        double x = (2/3f * xp)/this.size;
        double y = (-xp/3f + sqrt(3)/3f*yp) / this.size;
        return cube_to_oddq(cube_round(x, -x-y, y));
    }


    private Coordinate cube_to_oddq (Cube cube) {
        int x = cube.x;
        int y = cube.z + (cube.x + (cube.x&1)) / 2;
        return new Coordinate(x, y);
    }

    private Cube cube_round (double x, double y, double z) {
        int rx = (int) round(x);
        int ry = (int) round(y);
        int rz = (int) round(z);

        double x_diff = abs(rx - x);
        double y_diff = abs(ry - y);
        double z_diff = abs(rz - z);

        if ((x_diff > y_diff) && (x_diff > z_diff)) {
            rx = -ry-rz;
        }
        else if (y_diff > z_diff) {
            ry = -rx-rz;
        }
        else {
            rz = -rx-ry;
        }
        return new Cube(rx, ry, rz);
    }

    private class Cube {
        int x;
        int y;
        int z;

        private Cube (int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

}
