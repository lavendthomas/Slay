package be.ac.umons.slay.g02.entities;

public class Soldier implements Entity {

    SoldierLevel soldierLevel;
    boolean moved;

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

    boolean getMoved () {
        return this.moved;
    }

    public boolean canAttack (Soldier other){
        if (this.soldierLevel.getLevel() > other.getSoldierLevel().getLevel()) {
            return true;
        }
        else if (this.soldierLevel.getLevel() == 3 && this.soldierLevel.getLevel() == other.getSoldierLevel().getLevel()) {
            return Math.random() <0.7; // 0.7 Pour plus de chances d'avoir true
        }
        else {
            return false;
        }
    }

    // a la place de canAttack
    public boolean canMove (Soldier other) {
        if (this.soldierLevel.getLevel() == 3 && this.soldierLevel.getLevel() == other.getSoldierLevel().getLevel())
            return Math.random() <0.7; // 0.7 Pour plus de chances d'avoir true

        return (this.soldierLevel.getLevel() > other.getSoldierLevel().getLevel());
    }


}
