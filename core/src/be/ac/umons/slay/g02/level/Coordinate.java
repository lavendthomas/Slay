package be.ac.umons.slay.g02.level;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX (int x) {
        this.x = x;
    }

    public void setY (int y) {
        this.y = y;
    }

    public Coordinate[] getNeighbors () {
        Coordinate[] res = {new Coordinate(x,y-1),new Coordinate(x+1, y),
                new Coordinate(x+1, y+1), new Coordinate(x, y+1),
                new Coordinate(x-1, y-1), new Coordinate(x-1, y)};
        return res;
    }

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