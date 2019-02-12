package be.ac.umons.slay.g02.level;

public class Coordinate {
    private int x;
    private int y;

    Coordinate (int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX (){
        return this.x;
    }

    int getY (){
        return this.y;
    }
}
