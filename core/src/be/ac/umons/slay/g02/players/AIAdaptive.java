package be.ac.umons.slay.g02.players;

import java.io.File;
import java.util.HashMap;
import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.gui.screens.GameScreen;
import be.ac.umons.slay.g02.level.Level;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Tile;

public class AIAdaptive extends Player implements AI {


    /**
     * The level that is played
     */

    private Playable level;
    private Player[] players;
    private AIEasy easy;
    private AIMedium medium;
    private AIAdvanced advanced;
    private AIRandom random;


    /**
     * Constructor of the class, initiating its name, its color and the path of its avatar
     *
     * @param color Player color
     * @param name  Player name
     */

    public AIAdaptive(Colors color, String name) {
        this.color = color;
        this.name = name;
        avatar = "profile" + File.separator + "ai_adaptive.png";
        easy = new AIEasy(color, name);
        medium = new AIMedium(color, name);
        advanced = new AIAdvanced(color, name);
        random = new AIRandom(color, name);
    }

    /**
     * Perform the actions to be done during a game turn
     *
     * @return true if performed successfully false otherwise
     */

    @Override
    public boolean play(Player player, boolean forMe) {
        level = GameScreen.getLevel();
        players = Level.getPlayers();
        int [] scores = scoreCount();
        int hard = 0; // Random by default

        for(int i = 0; i < players.length; i++) {
            if(players[i].equals(this)) {
                for (int j = 0; j < players.length; j++ ) {
                    if(i != j) {
                        if(scores[i]*0.5 > scores[j]) {
                            // Enemy very loose => Random
                            hard = 0;
                        } else if(scores[i]*0.75 > scores[j]) {
                            // Enemy loose a bit => Easy
                            hard = 1;
                        } else if(scores[i] < scores[j]*1.1 && scores[i] > scores[j]*.9) {
                            // Equal to plus or minus 10 percent => Medium
                            hard = 2;
                        } else if(scores[i] < scores[j]*0.9) {
                            // AI loose
                            hard = 3;
                        }
                    }
                }

            }
        }

        boolean bool;
        switch (hard) {
            case 1 :
                bool = easy.play(this, false);
                break;
            case 2 :
                bool = medium.play(this, false);
                break;
            case 3 :
                bool = advanced.play(this, false);
                break;
            default:
                bool = random.play(this, false);
                break;
        }
        return bool;
    }

    /**
     * Calculate the score of each player to estimate who wins
     *
     * @return Table of integers containing the score of each player
     */

    private int[] scoreCount() {

        int[] scores = new int[players.length];

        HashMap<Player, Integer> pl = new HashMap<Player, Integer>(players.length);
        for (int i=0; i<players.length; i++) {
            pl.put(players[i], i);
        }

        for (int i = 0; i < level.width(); i++) {
            for (int j = 0; j < level.height(); j++) {
                Tile tile = level.get(i, j);

                if (tile.getTerritory() != null) {
                    Player owner = tile.getTerritory().getOwner();

                    scores[pl.get(owner)] += 3; // Plus three points per possessed tile

                    if (tile.getEntity() != null) {
                        if (tile.getEntity() instanceof Soldier) {
                            int level = ((Soldier) tile.getEntity()).getSoldierLevel().getLevel();
                            // The stronger the soldier, the more points there are
                            scores[pl.get(owner)] += level;

                        } else if (tile.getEntity() instanceof StaticEntity) {
                            if (tile.getEntity() == StaticEntity.TREE) {
                                //-1 per tree because higher risk of loosing units
                                scores[pl.get(owner)] -= 1;
                            }
                        }
                    }
                }
            }
        }
        return scores;
    }
}
