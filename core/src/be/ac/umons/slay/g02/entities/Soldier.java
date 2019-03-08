package be.ac.umons.slay.g02.entities;

public class Soldier implements Entity {

    SoldierLevel soldierLevel;
    boolean moved;
    private final float PROBABILITY = 0.7f;


    public Soldier(SoldierLevel level) {
        this.soldierLevel = level;
    }

    public Soldier(SoldierLevel level, boolean moved) {
        this.soldierLevel = level;
        this.moved = moved;
    }

    public SoldierLevel getSoldierLevel() {
        return soldierLevel;
    }

    public int getPrice() {
        return soldierLevel.getPrice();
    }

    public int getCost() {
        return soldierLevel.getCost();
    }

    public String getName() {
        return soldierLevel.getName();
    }

    public boolean getMoved() {
        return this.moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean canAttack(Soldier other){
        if (soldierLevel.getLevel() > other.getSoldierLevel().getLevel()) {
            return true;
        } else if (soldierLevel.getLevel() == 3 && soldierLevel.getLevel() == other.getSoldierLevel().getLevel()) {
            return Math.random() < PROBABILITY;
        } else {
            return false;
        }
    }

}
