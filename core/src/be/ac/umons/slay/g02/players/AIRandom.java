package be.ac.umons.slay.g02.players;

import java.io.File;
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

public class AIRandom extends Player implements AI {

    private Playable level;

    public AIRandom (Colors color, String name) {
        this.name = name;
        this.color = color;
        avatar = "profile" + File.separator + "ai_random.png";
    }
    @Override
    public boolean play() {
        AIMethods.sleep();
        level = GameScreen.getLevel();
        List<List<Coordinate>> allTerritories = AIMethods.loadTerritories(level);

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
                        List<Coordinate> moves = level.getMoves(coordinate, 4);
                        int rand = new Random().nextInt(moves.size());
                        level.move(coordinate, moves.get(rand));
                        }
                    }
                }
            }
        // finir son tour
        return level.nextTurn();
    }

    private void tryToAddUnit(List<Coordinate> territory) {
        // récup les données du territoire concerné
        Coordinate cFrom = territory.get(0);
        Territory terr = level.get(cFrom).getTerritory();
        for (int i = 1; i <= 4; i++) {
            Soldier entity = new Soldier(SoldierLevel.fromLevel(i));
            // Essai ajouter chacun des soldats possibles en commencant par le plus faible
            if (terr.canBuy(entity)) {
                // Je peux l'acheter => choix d'une case où la placer
                List<Coordinate> moves = level.getMoves(entity, cFrom);
                int rand = new Random().nextInt(moves.size());
                level.buy(entity, cFrom, moves.get(rand));
            }
        }
    }
}
