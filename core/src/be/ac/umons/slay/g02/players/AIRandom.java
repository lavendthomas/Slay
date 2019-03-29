package be.ac.umons.slay.g02.players;

import java.io.File;
import java.util.List;
import java.util.Random;

import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.gui.screens.GameScreen;
import be.ac.umons.slay.g02.level.Coordinate;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Territory;
import be.ac.umons.slay.g02.level.Tile;

/**
 * Class representing artificial intelligence of random level
 */

public class AIRandom extends Player implements AI {

    /**
     * Constructor of the class, initiating its name, its color and the path of its avatar
     *
     * @param color Player color
     * @param name  Player name
     */

    public AIRandom(Colors color, String name) {
        this.name = name;
        this.color = color;
        avatar = "profile" + File.separator + "ai_random.png";
    }

    /**
     * Perform actions to be done during a game turn randomly
     *
     * @return true if performed successfully false otherwise
     */

    @Override
    public boolean play(Player player, boolean forMe) {
        AIMethods.sleep();
        Playable level = GameScreen.getLevel();
        List<List<Coordinate>> allTerritories = AIMethods.loadTerritories(level, this, false);

        for (List<Coordinate> territory : allTerritories) {

            // Buy a unit ?
            Coordinate cFrom = territory.get(0);
            Territory terr = level.get(cFrom).getTerritory();

            Soldier entity = new Soldier(SoldierLevel.fromLevel(new Random().nextInt(5)));
            if (terr.canBuy(entity)) {
                // Can buy soldier => choice random tile
                List<Coordinate> moves = level.getMoves(entity, cFrom);
                int rand = new Random().nextInt(moves.size());
                level.buy(entity, cFrom, moves.get(rand));
            }


            // Move a unit ?
            for (Coordinate coordinate : territory) {
                // Go through all the boxes of this terroir
                Tile tile = level.get(coordinate);
                if (tile.getEntity() != null && tile.getEntity() instanceof Soldier) {
                    // There is a soldier
                    Soldier soldier = (Soldier) tile.getEntity();
                    if (!soldier.getMoved()) {
                        // Soldier not already moved = > choice random tile
                        List<Coordinate> moves = level.getMoves(coordinate, 4);
                        int rand = new Random().nextInt(moves.size());
                        level.move(coordinate, moves.get(rand));
                    }
                }
            }
        }
        // Ends its turn
        return level.nextTurn();
    }
}
