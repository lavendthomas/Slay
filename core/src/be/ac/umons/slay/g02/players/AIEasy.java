package be.ac.umons.slay.g02.players;

import java.io.File;
import java.util.List;
import java.util.Random;

import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.gui.screens.GameScreen;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Tile;

/**
 * Class representing artificial intelligence of easy level
 */

public class AIEasy extends Player implements AI {

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

    public AIEasy(Colors color, String name) {
        this.color = color;
        this.name = name;
        avatar = "profile" + File.separator + "ai_easy.png";
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
        int n = 0;
        for (List<Coordinate> territory : allTerritories) {
            // For each territory

            // Buy ?
            tryToAddUnit(territory, n);

            // Move ?
            for (Coordinate cFrom : territory) {
                // Search a soldier
                Tile tile = level.get(cFrom);
                if (tile.getEntity() != null && tile.getEntity() instanceof Soldier) {
                    Soldier soldier = (Soldier) tile.getEntity();
                    if (!soldier.getMoved()) {
                        // Soldier has not already moved
                        Coordinate cTo = findBestPlace(level.getMoves(cFrom, 4), cFrom);
                        level.move(cFrom, cTo);
                    }
                }
            }
        }
        // End of its turn
        return level.nextTurn();
    }

    /**
     * Try to buy weak units
     *
     * @param territory List of coordinates representing the territory in which searched
     */

    private void tryToAddUnit(List<Coordinate> territory, int n) {
        // AIEasy only buys weak units
        Coordinate cFrom = territory.get(0);
        Tile tileFrom = level.get(cFrom);
        Soldier soldier = new Soldier(SoldierLevel.L0);
        if (tileFrom.getTerritory() != null && tileFrom.getTerritory().canBuy(soldier) && n < 3) {
            Coordinate cTo = findBestPlace(level.getMoves(soldier, cFrom), cFrom);
            if (cTo != null) {
                level.buy(soldier, cFrom, cTo);
                n++;
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

    private Coordinate findBestPlace(List<Coordinate> moves, Coordinate cFrom) {

        // Cut trees of its territory
        Coordinate cTo = AIMethods.chopTree(level, moves, cFrom, this);
        if (cTo != null) {
            return cTo;
        }

        // Clean the graves of its territory
        cTo = AIMethods.cleanGraves(level, moves, cFrom, this);
        if (cTo != null) {
            return cTo;
        }

        // Seeking to occupy a neutral tile
        cTo = AIMethods.captureNeutral(level, moves, cFrom, this);
        if (cTo != null) {
            return cTo;
        }

        // Attack enemy tiles
        cTo = AIMethods.attackEnemy(level, moves, cFrom, this);
        if (cTo != null) {
            return cTo;
        }

        // Merge soldiers
        cTo = AIMethods.fusion(level, moves, cFrom, this);
        if (cTo != null) {
            return cTo;
        }

        // Default case, random move
        int rand = new Random().nextInt(moves.size());
        return moves.get(rand);
    }
}
