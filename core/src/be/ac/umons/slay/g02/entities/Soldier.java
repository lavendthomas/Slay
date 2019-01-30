package be.ac.umons.slay.g02.entities;

public class Soldier extends MobileEntity {

    SoldierLevel level;

    Soldier(SoldierLevel level) {
        this.level = level;
    }

    @Override
    int getPrice() {
        return level.getPrice();
    }

    @Override
    int getCosts() {
        return level.getCosts();
    }
}
