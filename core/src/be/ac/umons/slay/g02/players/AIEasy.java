package be.ac.umons.slay.g02.players;

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


    public AIEasy (Colors color) { //TODO Initialiser sa list de territoire et la màj au fur et à mesure
        this.name = "AIEasy";
        this.color = color;
    }

    @Override
    public void play() {
        level = GameScreen.getLevel();
        allTerritories = AIMethods.loadTerritories(level, this);
        // Série d'actions pour simuler le tour
        for (List<Coordinate> territory : allTerritories) {
            /*
            Pour chaque territoire faire :
                * unité à déplacer ?
                    ** couper un arbre
                    ** attaquer ennemie ou étendre son territoire
                    ** defendre territoire
                * acheter une unité ?
                    ** la placer comme pour le point précédent
             => Implémenter une méthode qui détermine le meilleur endroit où placer une unité

             */
        }


        // finir son tour
        level.nextTurn();



    }

    private void loadTerritories () {
        for (int i = 0; i < level.width(); i++) {
            for (int j = 0; j < level.height(); j++) {
                Tile current = level.get(i, j);
                if (current.getTerritory().getOwner().equals(this)) {
                    List<Coordinate> territory = level.neighbourTilesInSameTerritory(new Coordinate(i,j));
                }
            }
        }
    }


    boolean tryToAddUnit(Territory territory) {
        for (int i = 1; i <= 4; i++) {
            Entity e = new Soldier(SoldierLevel.fromLevel(i));
            if (territory.canBuy(new Soldier(SoldierLevel.fromLevel(i)))) {

            }
        }
        return true;

    }





}
