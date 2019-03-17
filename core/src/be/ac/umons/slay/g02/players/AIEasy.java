package be.ac.umons.slay.g02.players;

import java.util.ArrayList;
import java.util.List;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.gui.screens.GameScreen;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.LevelLoader;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Territory;
import be.ac.umons.slay.g02.level.Tile;

public class AIEasy extends Player implements AI {

    private Playable level;
    private List<List<Coordinate>> allTerritories;

    public AIEasy (Colors color, String name) { //TODO Initialiser sa list de territoire et la màj au fur et à mesure
        this.color = color;
        this.name = name;
    }

    @Override
    public void play() {
        level = GameScreen.getLevel();
        allTerritories = AIMethods.loadTerritories(level);




        for (List<Coordinate> territory : allTerritories) {
            // Pour chaque territoire
            // Ajouter une unité ? Avant le déplacement ains déplace le nouveau soldat créé
            tryToAddUnit(territory);

            // Déplacement ?
            for (Coordinate coordinate : territory) {
                // Parcours toutes les cases de ce terroire
                Tile tile = level.get(coordinate);
                if (tile.getEntity() != null && tile.getEntity() instanceof Soldier) {
                    // Si il y a un soldat
                    Soldier soldier = (Soldier) tile.getEntity();
                    if (!soldier.getMoved()) {
                        // Il ne s'est pas encore déplacé
                        moveSoldier(soldier, coordinate);
                    }

                }
            }


        }

        // finir son tour
        level.nextTurn();



    }

    private void moveSoldier (Soldier soldier, Coordinate coordinate) {
        for (Coordinate c : level.getMoves(coordinate, 4)) {
            // Parcours de toutes les cases où mon soldat peut se déplacer
            if (!c.equals(coordinate)) {
                Tile current = level.get(c);
                if (current.getTerritory() != null) {
                    // Il y a un territoire

                    if (current.getTerritory().getOwner().equals(this)) {
                        // Même territoire

                        if (current.getEntity() != null) {
                            // Il y une entité

                            if (current.getEntity() == StaticEntity.TREE) {
                                // C'est un arbre => le couper
                                level.move(coordinate, c);
                                break;

                            } else if (current.getEntity() instanceof Soldier) {
                                // Il y a un soldat
                                if (canFusion(soldier, (Soldier) current.getEntity(), current.getTerritory())) {
                                    // Fusion sans risque ? Retirer dans easy pour plus simple
                                    level.move(coordinate, c);
                                    break;

                                }
                            } // Ajouter cas défense du territoire Pour IA plus dure
                        }
                    } else {
                        // Territoire adverse
                        level.move(coordinate, c);
                        break;
                    }
                } else {
                    // Pas de territoire
                    level.move(coordinate, c);
                    break;
                }
            }
        }
    }

    private boolean canFusion (Soldier sold1, Soldier sold2, Territory territory) {
        int newlvl = sold1.getSoldierLevel().getLevel() + sold2.getSoldierLevel().getLevel();
        int rest = territory.getIncome() - territory.getWages();
        int cost = SoldierLevel.fromLevel(newlvl).getCost();
        return rest > cost;
    }

    private boolean tryToAddUnit(List<Coordinate> territory) {
        // récup les données du territoire concerné
        Coordinate cFrom = territory.get(0);
        Territory terr = level.get(cFrom).getTerritory();
        for (int i = 1; i <= 4; i++) {
            Soldier entity = new Soldier(SoldierLevel.fromLevel(i));
            // Essai ajouter chacun des soldats possibles en commencant par le plus faible

            if (canBuy(entity, terr)) {
                // Je peux l'acheter => choix d'une case où la placer
                for (Coordinate coordinate : level.getMoves(entity, cFrom)) {
                    Tile current = level.get(coordinate);
                    if (current.getTerritory() != null) {
                        // Coordonée d'une tuile appartenant à quelqu'un
                        if (current.getTerritory().getOwner().equals(this)) {
                            // Coordonée d'une tuile m'appartenant
                            if (current.getEntity() != null) {
                                // Il y a une entité
                                if (current.getEntity() == StaticEntity.TREE
                                    || current.getEntity() == StaticEntity.GRAVE) {
                                    // Il y a un arbre => le couper OU une tombe donc place libre
                                    level.buy(entity, cFrom, coordinate);
                                } else if (current.getEntity() instanceof Soldier) {
                                    // Il y a un soldat => Fusion ?
                                    if (canFusion(entity, (Soldier) current.getEntity(), terr)) {
                                        // Peut fusionner => achat et fusion
                                        level.buy(entity, cFrom, coordinate);
                                    }
                                }
                            } else {
                                // Pas d'entité case libre
                                level.buy(entity, cFrom, coordinate);
                            }
                        } else {
                            // Coordonnée d'une tuile ennemie
                            level.buy(entity, cFrom, coordinate);
                        }

                    } else {
                        // Coordonnée d'une tuile neutre
                        level.buy(entity, cFrom, coordinate);
                    }
                }
            }
        }
        return true;

    }

    private boolean canBuy (Soldier soldier, Territory territory) {
        if (territory.canBuy(soldier)) {
            int rest = territory.getIncome() - territory.getWages();
            int cost = soldier.getCost();
            return rest > cost;
        }
        return false;
    }




}
