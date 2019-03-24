package be.ac.umons.slay.g02.players;

import java.io.File;
import java.util.List;

import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.gui.screens.GameScreen;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Territory;
import be.ac.umons.slay.g02.level.Tile;


public class AIAdvanced extends Player implements AI {

    private Playable level;
    private int strongestEnemy;
    private int strongestAlly;

    public AIAdvanced(Colors color, String name) {
        this.name = name;
        this.color = color;
        avatar = "profile" + File.separator + "ai_advanced.png";
    }

    @Override
    public boolean play() {
        AIMethods.sleep();
        level = GameScreen.getLevel();
        List<List<Coordinate>> allTerritories = AIMethods.loadTerritories(level, this, false);
        strongestEnemy = AIMethods.searchStrongerSoldier(level, this, true);
        strongestAlly = AIMethods.searchStrongerSoldier(level, this, false);

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

    private void buyUnit (Soldier soldier, Coordinate cFrom) {
            Coordinate cTo = findBestPlace(level.getMoves(soldier, cFrom), cFrom, soldier);
            if (cTo == null) {
                cTo = AIMethods.searchEnnemy(cFrom, level, this, soldier);
            }
            if (cTo != null) {
                level.buy(soldier, cFrom, cTo);
                strongestAlly = strongestEnemy;
            }

    }

    private int wichSoldier () {
        if (strongestEnemy != -1) {
            if (strongestEnemy == 3 && strongestAlly < 3) {
                // Ennemie a le soldat max => essaie de l'acheter
                return 3;
            }
            if (strongestEnemy > strongestAlly) {
                // Ennemie a au moins un soldat plus fort
                return strongestEnemy;
            }
        }
        return 0;
    }

    private void tryToAddUnit(List<Coordinate> territory) {
        int soughtLevel = wichSoldier();
        Coordinate cFrom = territory.get(0);
        Tile tileFrom = level.get(cFrom);
        Soldier soldier = new Soldier(SoldierLevel.fromLevel(soughtLevel));
        if (canBuy(soldier, tileFrom.getTerritory())) {
            buyUnit(soldier, cFrom);
        }
    }

    private Coordinate findBestPlace(List<Coordinate> moves, Coordinate cFrom, Soldier soldier) {
        Soldier soldier1;
        if (soldier == null) {
            soldier1 = (Soldier) level.get(cFrom).getEntity();
        } else {
            soldier1 = soldier;
        }
        int lvl = soldier1.getSoldierLevel().getLevel();
        Coordinate cTo;
        switch (lvl) {
            case 0:
                // Seeking to occupy a neutral tile
                cTo = AIMethods.captureNeutral(level, moves, cFrom, this);
                if (cTo != null) {
                    return cTo;
                }

                // Cut trees of its territory
                cTo = AIMethods.chopTree(level, moves, cFrom, this);
                if (cTo != null) {
                    return cTo;
                }

                // Attack enemy tiles
                cTo = AIMethods.attackEnemy(level, moves, cFrom, this);
                if (cTo != null) {
                    return cTo;
                }

                // Merge ?
                if (wichSoldier() == 1) {
                    // Merge soldiers
                    cTo = AIMethods.fusion(level, moves, cFrom, this);
                    if (cTo != null) {
                        Soldier soldier2 = (Soldier) level.get(cTo).getEntity();
                        if (canFusion(soldier1, soldier2, level.get(moves.get(0)).getTerritory())) {
                            return cTo;
                        }
                    }
                }
            case 1:
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

                // Cut trees of its territory
                cTo = AIMethods.chopTree(level, moves, cFrom, this);
                if (cTo != null) {
                    return cTo;
                }

                // Merges ?
                if (wichSoldier() == 2) {
                    // Merge soldiers
                    cTo = AIMethods.fusion(level, moves, cFrom, this);
                    if (cTo != null) {
                        Soldier soldier2 = (Soldier) level.get(cTo).getEntity();
                        if (canFusion(soldier1, soldier2, level.get(moves.get(0)).getTerritory())) {
                            return cTo;
                        }
                    }
                }
            case 2:
                // Attack enemy tiles
                cTo = AIMethods.attackEnemy(level, moves, cFrom, this);
                if (cTo != null) {
                    return cTo;
                }
                // Merge ?
                if (wichSoldier() == 3) {
                    // Merge soldiers
                    cTo = AIMethods.fusion(level, moves, cFrom, this);
                    if (cTo != null) {
                        Soldier soldier2 = (Soldier) level.get(cTo).getEntity();
                        if (canFusion(soldier1, soldier2, level.get(moves.get(0)).getTerritory())) {
                            return cTo;
                        }
                    }
                }

                // Seeking to occupy a neutral tile
                cTo = AIMethods.captureNeutral(level, moves, cFrom, this);
                if (cTo != null) {
                    return cTo;
                }

                // Cut trees of its territory
                cTo = AIMethods.chopTree(level, moves, cFrom, this);
                if (cTo != null) {
                    return cTo;
                }
            case 3:
                // Attack enemy tiles
                cTo = AIMethods.attackEnemy(level, moves, cFrom, this);
                if (cTo != null) {
                    return cTo;
                }

                // Seeking to occupy a neutral tile
                cTo = AIMethods.captureNeutral(level, moves, cFrom, this);
                if (cTo != null) {
                    return cTo;
                }

                // Cut trees of its territory
                cTo = AIMethods.chopTree(level, moves, cFrom, this);
                if (cTo != null) {
                    return cTo;
                }
            default:
                // Default case, get closer to the enemy
                return null; // null pour chercher à se rapprocher de l'ennemie par défaut
        }
    }

    private boolean canBuy (Soldier soldier, Territory territory) {
        if (territory.canBuy(soldier)) {
            // Coins remaining after purchase
            int rest = territory.getCoins() - soldier.getPrice() + territory.getIncome();
            // Salary after a turn
            int cost = soldier.getSoldierLevel().getCost();
            return rest > cost;
        }
        return false;
    }

    private boolean canFusion (Soldier sold1, Soldier sold2, Territory territory) {
        int newlvl = sold1.getSoldierLevel().getLevel() + sold2.getSoldierLevel().getLevel();
        //Salary for one turn
        int cost = SoldierLevel.fromLevel(newlvl).getCost();
        int rest = territory.getCoins() + territory.getIncome();
        return rest > cost;
    }
}