package be.ac.umons.slay.g02.level;

/**
 * Class representing coordinates
 */
public class Coordinate {
    private int x;
    private int y;

    /**
     * Class constructor
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get x coordinate
     *
     * @return X coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get y coordinate
     *
     * @return Y coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Set x coordinate
     *
     * @param x X coordinate to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set y coordinate
     *
     * @param y Y coordinate to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get a table of nearby coordinates at a coordinates
     *
     * @return Table of nearby coordinates
     */

    public Coordinate[] getNeighbors() {
        // The neighbors are different if we are on an odd or even column.\
        if (x % 2 == 0) {
            Coordinate[] res = {
                    new Coordinate(x, y + 1),
                    new Coordinate(x - 1, y + 1),
                    new Coordinate(x - 1, y),
                    new Coordinate(x, y - 1),
                    new Coordinate(x + 1, y),
                    new Coordinate(x + 1, y + 1)
            };
            return res;
        } else {
            Coordinate[] res = {
                    new Coordinate(x, y - 1),
                    new Coordinate(x + 1, y),
                    new Coordinate(x + 1, y - 1),
                    new Coordinate(x, y + 1),
                    new Coordinate(x - 1, y - 1),
                    new Coordinate(x - 1, y)
            };
            return res;
        }
    }

    /**
     * Test the equality between this coordinate and another
     *
     * @param o The object to test
     * @return  True if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Coordinate other = (Coordinate) o;
        if (x != other.getX())
            return false;
        if (y != other.getY())
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "<" + x + "," + y + ">";
    }
}