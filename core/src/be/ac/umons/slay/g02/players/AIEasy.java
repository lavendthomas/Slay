package be.ac.umons.slay.g02.players;

import java.util.List;

import be.ac.umons.slay.g02.level.LevelLoader;
import be.ac.umons.slay.g02.level.Playable;
import be.ac.umons.slay.g02.level.Territory;

public class AIEasy extends Player implements AI {

    private Playable level;
    private List<Territory> territories;


    public AIEasy (Colors color) { //TODO Initialiser sa list de territoire et la màj au fur et à mesure
        this.name = "AIEasy";
        this.color = color;
    }

    @Override
    public void play() {

    }


    @Override
    public boolean canAddUnit() {
        return true;
    }

    @Override
    public boolean canMerge() {
        return true;

    }

    @Override
    public boolean canMove() {
        return true;

    }

    @Override
    public boolean checkCoins() {
        return true;

    }

}
