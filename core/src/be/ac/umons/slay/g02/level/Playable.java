package be.ac.umons.slay.g02.level;

import be.ac.umons.slay.g02.entities.Entity;

public interface Playable {
    void buy (Entity entity, Coordinate coordinate);
    void endTurn();
}
