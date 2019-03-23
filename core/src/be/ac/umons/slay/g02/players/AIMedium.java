package be.ac.umons.slay.g02.players;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.gui.screens.GameScreen;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Territory;
import be.ac.umons.slay.g02.level.Tile;

public class AIMedium extends Player implements AI {

    private Playable level;

    public AIMedium(Colors color, String name) {
        this.name = name;
        this.color = color;
        avatar = "profile" + File.separator + "ai_medium.png";
    }


    @Override
    public boolean play() {
        AIMethods.sleep();
        level = GameScreen.getLevel();
        List<List<Coordinate>> allTerritories = AIMethods.loadTerritories(level, this, false);
        for (List<Coordinate> territory : allTerritories) {
            // Pour chaque territoire
            tryToAddUnit(territory);

            // Déplacement ?
            for (Coordinate cFrom : territory) {
                // Parcours toutes les cases de ce terroire
                Tile tile = level.get(cFrom);
                if (tile.getEntity() != null && tile.getEntity() instanceof Soldier) {
                    // Si il y a un soldat
                    Soldier soldier = (Soldier) tile.getEntity();
                    if (!soldier.getMoved()) {
                        // Il ne s'est pas encore déplacé
                        Coordinate cTo = findBestPlace(level.getMoves(cFrom, 4), cFrom, null);
                        if (cTo == null) {
                            // Default case, get closer to the enemy
                            cTo = AIMethods.searchEnnemy(cFrom, level, this, null); // Soldat null car dplct
                        }
                        if (cTo != null) {
                            level.move(cFrom, cTo);
                        }
                    }
                }
            }
        }
        // finir son tour
        return level.nextTurn();
    }

    private void tryToAddUnit(List<Coordinate> territory) {
        for (int i = 1; i <= 4; i++) {
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

    private Coordinate findBestPlace(List<Coordinate> moves, Coordinate cFrom, Soldier soldier) {

        // Seeking to occupy a neutral tile
        Coordinate cTo = AIMethods.captureNeutral(level, moves, cFrom, this);
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

        // Cut trees of its territory
        cTo = AIMethods.chopTree(level, moves, cFrom, this);
        if (cTo != null) {
            return cTo;
        }

        // Default case, get closer to the enemy
        return cTo; // null value
    }

    private boolean canBuy (Soldier soldier, Territory territory) {
        if (territory.canBuy(soldier)) {
            // Coins remaining after purchase
            int rest = territory.getCoins() - soldier.getPrice();
            // Salary after a turn
            int cost = soldier.getSoldierLevel().getCost();
            return rest > cost;
        }
        return false;
    }

    private boolean canFusion (Soldier sold1, Soldier sold2, Territory territory) {
        if (sold1 != null && sold2 != null) {
            int newlvl = sold1.getSoldierLevel().getLevel() + sold2.getSoldierLevel().getLevel();
            int cost = SoldierLevel.fromLevel(newlvl).getCost();
            return territory.getCoins() > cost;
        }
        return false;
    }
}