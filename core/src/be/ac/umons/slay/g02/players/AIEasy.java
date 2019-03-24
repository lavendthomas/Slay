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

public class AIEasy extends Player implements AI {

    private Playable level;

    public AIEasy(Colors color, String name) {
        this.color = color;
        this.name = name;
        avatar = "profile" + File.separator + "ai_easy.png";
    }

    @Override
    public boolean play() {
        AIMethods.sleep();
        level = GameScreen.getLevel();
        List<List<Coordinate>> allTerritories = AIMethods.loadTerritories(level, this, false);
        for (List<Coordinate> territory : allTerritories) {
            // For each territory

            // Buy ?
            tryToAddUnit(territory);

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

    private void tryToAddUnit(List<Coordinate> territory) {
        // AIEasy only buys weak units
        Coordinate cFrom = territory.get(0);
        Tile tileFrom = level.get(cFrom);
        Soldier soldier = new Soldier(SoldierLevel.L0);
        if (tileFrom.getTerritory() != null && tileFrom.getTerritory().canBuy(soldier)) {
            Coordinate cTo = findBestPlace(level.getMoves(soldier, cFrom), cFrom);
            if (cTo != null) {
                level.buy(soldier, cFrom, cTo);
            }
        }

    }

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

        // Default case, random move
        int rand = new Random().nextInt(moves.size());
        return moves.get(rand);
    }
}
