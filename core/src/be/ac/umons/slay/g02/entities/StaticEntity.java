package be.ac.umons.slay.g02.entities;

public abstract class StaticEntity extends Entity {


    @Override
    int getCosts() {
        // A static entity never has maintenance costs
        return 0;
    }

}
