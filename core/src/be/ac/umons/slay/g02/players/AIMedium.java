package be.ac.umons.slay.g02.players;

import java.io.File;
import java.util.List;

import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.gui.screens.GameScreen;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Territory;
import be.ac.umons.slay.g02.level.Tile;

/**
 * Class representing artificial intelligence of medium level
 */

public class AIMedium extends Player implements AI {

    /**
     * The level that is played
     */

    private Playable level;

    /**
     * Constructor of the class, initiating its name, its color and the path of its avatar
     *
     * @param color Player color
     * @param name  Player name
     */

    public AIMedium(Colors color, String name) {
        this.name = name;
        this.color = color;
        avatar = "profile" + File.separator + "ai_medium.png";
    }

    /**
     * Perform the actions to be done during a game turn
     *
     * @return true if performed successfully false otherwise
     */

    @Override
    public boolean play(Player player, boolean forMe) {
        AIMethods.sleep();
        level = GameScreen.getLevel();
        List<List<Coordinate>> allTerritories;
        if (forMe) {
            allTerritories = AIMethods.loadTerritories(level, this, false);
        } else {
            allTerritories = AIMethods.loadTerritories(level, player, false);
        }
        for (List<Coordinate> territory : allTerritories) {
            tryToAddUnit(territory);

            for (Coordinate cFrom : territory) {
                Tile tile = level.get(cFrom);
                if (tile.getEntity() != null && tile.getEntity() instanceof Soldier) {
                    Soldier soldier = (Soldier) tile.getEntity();
                    if (!soldier.getMoved()) {
                        Coordinate cTo = findBestPlace(level.getMoves(cFrom, 4), cFrom, null);
                        if (cTo == null) {
                            cTo = AIMethods.searchEnnemy(cFrom, level, this, null); // Soldat null car dplct
                        }
                        if (cTo != null) {
                            level.move(cFrom, cTo);
                        }
                    }
                }
            }
        }
        return level.nextTurn();
    }

    /**
     * Try to buy each type of unit
     *
     * @param territory List of coordinates representing the territory in which searched
     */

    private void tryToAddUnit(List<Coordinate> territory) {
        for (int i = 0; i < 4; i++) {
            // Try to buy every type of soldier
            Soldier soldier = new Soldier(SoldierLevel.fromLevel(i));
            Coordinate cFrom = territory.get(0);
            Tile tileFrom = level.get(cFrom);
            if (canBuy(soldier, tileFrom.getTerritory())) {
                Coordinate cTo = findBestPlace(level.getMoves(soldier, cFrom), cFrom, soldier);
                if (cTo == null) {
                    cTo = AIMethods.searchEnnemy(cFrom, level, this, soldier);
                }
                if (cTo != null) {
                    level.buy(soldier, cFrom, cTo);
                }
            }
        }
    }

    /**
     * Find the best coordinate to place a soldier
     *
     * @param moves List of accessible coordinates
     * @param cFrom Original coordinate
     * @return The best coordinate if found, null else
     */

    private Coordinate findBestPlace(List<Coordinate> moves, Coordinate cFrom, Soldier soldier) {

        // Seeking to occupy a neutral tile
        Coordinate cTo = AIMethods.captureNeutral(level, moves, cFrom, this);
        if (cTo != null) {
            return cTo;
        }

        // Cut trees of its territory
        cTo = AIMethods.chopTree(level, moves, cFrom, this);
        if (cTo != null) {
            return cTo;
        }

        // Merge soldiers
        cTo = AIMethods.fusion(level, moves, cFrom, this);
        if (cTo != null) {
            Soldier soldier1;
            Soldier soldier2 = (Soldier) level.get(cTo).getEntity();
            if (soldier == null) {
                soldier1 = (Soldier) level.get(cFrom).getEntity();
            } else {
                soldier1 = soldier;
            }

            if (canFusion(soldier1, soldier2, level.get(moves.get(0)).getTerritory())) {
                return cTo;
            }
        }

        // Attack enemy tiles
        cTo = AIMethods.attackEnemy(level, moves, cFrom, this);
        if (cTo != null) {
            return cTo;
        }

        // Default case, get closer to the enemy
        return cTo; // Always null
    }

    /**
     * Check if a soldier can be bought and its salary can be paid
     *
     * @param soldier   Soldier to buy
     * @param territory Territory in which the soldier will be placed
     * @return True if can buy the soldier, false else
     */

    private boolean canBuy(Soldier soldier, Territory territory) {
        if (territory != null && territory.canBuy(soldier)) {
            // Coins remaining after purchase
            int rest = territory.getCoins() - soldier.getPrice();
            // Salary after a turn
            int cost = soldier.getSoldierLevel().getCost();
            return rest > cost;
        }
        return false;
    }

    /**
     * Check if two soldiers can be fusion and new soldier calary con be paid
     *
     * @param sold1     Soldier to merge
     * @param sold2     Soldier to merge
     * @param territory Territory in which soldiers are
     * @return True if can fusion the soldier, false else
     */

    private boolean canFusion(Soldier sold1, Soldier sold2, Territory territory) {
        int newlvl = sold1.getSoldierLevel().getLevel() + sold2.getSoldierLevel().getLevel();
        int cost = SoldierLevel.fromLevel(newlvl).getCost();
        return territory.getCoins() > cost;
    }
}