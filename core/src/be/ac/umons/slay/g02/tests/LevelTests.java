package be.ac.umons.slay.g02.tests;

import org.junit.Test;

import java.io.File;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Level;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Territory;
import be.ac.umons.slay.g02.level.Tile;
import be.ac.umons.slay.g02.level.TileType;
import be.ac.umons.slay.g02.players.Colors;
import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.Player;

import static org.junit.Assert.assertEquals;

/**
 * Class testing Level
 */
public class LevelTests {

    private static Playable loadLvl() {
        Playable level = new Level(5, 5);
        Player p1 = new HumanPlayer("P1", Colors.C1);
        p1.setAvatar("profile" + File.separator + "anonymous.png");
        Player p2 = new HumanPlayer("P2", Colors.C2);
        p2.setAvatar("profile" + File.separator + "anonymous.png");
        Player[] players = {p1, p2};
        level.setPlayers(players);

        Coordinate c11 = new Coordinate(1, 1);
        Coordinate c12 = new Coordinate(1, 2);
        Coordinate c13 = new Coordinate(1, 3);
        Coordinate c21 = new Coordinate(2, 1);
        Coordinate c22 = new Coordinate(2, 2);
        Coordinate c23 = new Coordinate(2, 3);
        Coordinate c31 = new Coordinate(3, 1);
        Coordinate c32 = new Coordinate(3, 2);
        Coordinate c33 = new Coordinate(3, 3);

        Tile t11 = new Tile(TileType.NEUTRAL);
        Tile t12 = new Tile(TileType.NEUTRAL);
        Tile t13 = new Tile(TileType.NEUTRAL);
        Tile t21 = new Tile(TileType.NEUTRAL);
        Tile t22 = new Tile(TileType.NEUTRAL);
        Tile t23 = new Tile(TileType.NEUTRAL);
        Tile t31 = new Tile(TileType.NEUTRAL);
        Tile t32 = new Tile(TileType.NEUTRAL);
        Tile t33 = new Tile(TileType.NEUTRAL);

        t13.setTerritory(new Territory(p1, t13, t12, t22, t23, t33));
        t22.setTerritory(new Territory(p1, t13, t12, t22, t23, t33));
        t23.setTerritory(new Territory(p1, t13, t12, t22, t23, t33));
        t33.setTerritory(new Territory(p1, t13, t12, t22, t23, t33));
        t12.setTerritory(new Territory(p1, t13, t12, t22, t23, t33));

        t21.setTerritory(new Territory(p2, t21, t31, t32));
        t31.setTerritory(new Territory(p2, t21, t31, t32));
        t32.setTerritory(new Territory(p2, t21, t31, t32));

        level.set(t11, c11);
        level.set(t12, c12);
        level.set(t13, c13);
        level.set(t21, c21);
        level.set(t22, c22);
        level.set(t23, c23);
        level.set(t31, c31);
        level.set(t32, c32);
        level.set(t33, c33);

        Tile water = new Tile(TileType.WATER);
        for (int i = 0; i < level.width(); i++) {
            for (int j = 0; j < level.height(); j++) {
                if (i == 0 || i == level.width() - 1 || j == 0 || j == level.height() - 1)
                    level.set(water, new Coordinate(i, j));
            }
        }
        Soldier s0 = new Soldier(SoldierLevel.fromLevel(0), false);
        Soldier s1 = new Soldier(SoldierLevel.fromLevel(1), false);

        level.set(s0, c21);
        level.set(s0, c32);
        level.set(s1, c22);
        level.set(StaticEntity.TREE, c33);

        level.mergeTerritories();

        Territory terr1 = level.get(c13).getTerritory();
        terr1.setCapital(level.get(c13));
        terr1.setCoins(200);

        Territory terr2 = level.get(c31).getTerritory();
        terr2.setCapital(level.get(c31));
        terr2.setCoins(200);

        return level;
    }

    @Test
    public void mergeSoldier() {
        Coordinate c0 = new Coordinate(2, 1);
        Coordinate c1 = new Coordinate(3, 2);
        Playable level = loadLvl();
        level.nextTurn();
        level.move(c0, c1);
        assertEquals("L1", level.get(c1).getEntity().getName());
    }

    @Test
    public void chopTreeInTerritory() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 2);
        Coordinate c1 = new Coordinate(3, 3);
        int before = level.get(c0).getTerritory().getCoins();
        level.move(c0, c1);
        int after = level.get(c0).getTerritory().getCoins();

        assertEquals(before+3,after);
    }

    @Test
    public void chopTreeOnNeutral() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 2);
        Coordinate c1 = new Coordinate(1, 1);
        int before = level.get(c0).getTerritory().getCoins();
        level.move(c0, c1);
        int after = level.get(c0).getTerritory().getCoins();

        assertEquals(before,after);
    }

    @Test
    public void cantAttack() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 1);
        Coordinate c1 = new Coordinate(2, 2);
        assertEquals(false, level.canMove(c0, c1));
    }

    @Test
    public void canAttack() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 2);
        Coordinate c1 = new Coordinate(2, 1);

        assertEquals(true,level.canMove(c0, c1));
    }

    @Test
    public void attackSoldier() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 2);
        Coordinate c1 = new Coordinate(2, 1);
        Entity entity = level.get(c0).getEntity();

        level.move(c0, c1);
        assertEquals(entity,level.get(c1).getEntity());
    }

    @Test
    public void canMoveInTerritory() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 2);
        Coordinate c1 = new Coordinate(2, 1);
        assertEquals(true, level.canMove(c0, c1));
    }

    @Test
    public void moveInTerritory() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 2);
        Coordinate c1 = new Coordinate(2, 1);
        Entity entity = level.get(c0).getEntity();
        level.move(c0, c1);
        assertEquals(entity, level.get(c1).getEntity());
    }

    @Test
    public void cantConquerEnemyTerritoryProtected() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(3, 2);
        Coordinate c1 = new Coordinate(3, 3);

        assertEquals(false, level.canMove(c0, c1));
    }

    @Test
    public void conquerNeutralTerritory() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 2);
        Coordinate c1 = new Coordinate(1, 1);
        Player player = level.get(c0).getTerritory().getOwner();
        level.move(c0, c1);
        assertEquals(player, level.get(c1).getTerritory().getOwner());
    }

    @Test
    public void cantMoveOnWater() {
        Playable level = loadLvl();

        Coordinate c0 = new Coordinate(2, 1);
        Coordinate c1 = new Coordinate(2, 0);
        assertEquals(false, level.canMove(c0, c1));
    }

}