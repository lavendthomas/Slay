package be.ac.umons.slay.g02.tests;

import org.junit.Test;

import be.ac.umons.slay.g02.gui.screens.HexManagement;
import be.ac.umons.slay.g02.level.Coordinate;

import static org.junit.Assert.*;

public class HexManagementTests {

    @Test
    public void distanceTests() {
        Coordinate p1 = new Coordinate(0,0);
        Coordinate p2 = new Coordinate(0,1);
        assertEquals(HexManagement.distance(p1, p2), 1);

        // Check that the distance between one point and itself is 0
        p1 = new Coordinate(58, -5);
        assertEquals(HexManagement.distance(p1, p1), 0);

        // Check that the distance between one point and its opposite are the same
        p2 = new Coordinate(-58, 5);
        Coordinate p0 = new Coordinate(0,0);
        assertEquals(HexManagement.distance(p1, p0), HexManagement.distance(p2, p0));
    }

    @Test
    public void distanceSymmetryTests() {
        // Check that the distance between one point and its inverse are the same
        Coordinate p1 = new Coordinate(58, -5);
        Coordinate p2 = new Coordinate(-58, 5);
        Coordinate p0 = new Coordinate(0,0);
        assertEquals(HexManagement.distance(p1, p0), HexManagement.distance(p2, p0));
    }
}
