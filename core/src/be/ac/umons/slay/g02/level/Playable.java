package be.ac.umons.slay.g02.level;

import be.ac.umons.slay.g02.entities.Entity;

public interface Playable {
    void select(Coordinate coordinate);
    void buy (Entity entity, Coordinate coordinate);
    void endTurn();
}
