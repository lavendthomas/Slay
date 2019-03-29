package be.ac.umons.slay.g02.tests;

import org.junit.Test;

import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;

import static org.junit.Assert.assertEquals;

/**
 * Class testing Soldier
 */
public class SoldierTests {
    @Test
    public void cantAttackTest() {
        Soldier s1 = new Soldier(SoldierLevel.L0);
        Soldier s2 = new Soldier(SoldierLevel.L1);
        assertEquals(s1.canAttack(s2), false);
    }

    @Test
    public void canAttackTest() {
        Soldier s1 = new Soldier(SoldierLevel.L0);
        Soldier s2 = new Soldier(SoldierLevel.L1);
        assertEquals(s2.canAttack(s1), true);
    }
}