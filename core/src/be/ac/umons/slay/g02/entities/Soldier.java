package be.ac.umons.slay.g02.entities;

/**
 *   TODO
 */
public class Soldier implements Entity {

    SoldierLevel soldierLevel;
    boolean moved;
    private final float PROBABILITY = 0.7f;

    /**
     * Class constructor
     *
     * @param level the level of the soldier
     */
    public Soldier(SoldierLevel level) {
        this.soldierLevel = level;
    }

    /**
     *   TODO
     *
     * @param level
     * @param moved
     */
    public Soldier(SoldierLevel level, boolean moved) {
        this.soldierLevel = level;
        this.moved = moved;
    }

    /**
     *   TODO
     *
     * @return
     */
    public SoldierLevel getSoldierLevel() {
        return soldierLevel;
    }

    /**
     *   TODO
     *
     * @return
     */
    public int getPrice() {
        return soldierLevel.getPrice();
    }

    /**
     *   TODO
     *
     * @return
     */
    public int getCost() {
        return soldierLevel.getCost();
    }

    /**
     *   TODO
     *
     * @return
     */
    public String getName() {
        return soldierLevel.getName();
    }

    /**
     *   TODO
     *
     * @return
     */
    public boolean getMoved() {
        return this.moved;
    }

    /**
     *   TODO
     *
     * @param moved
     */
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    /**
     *   TODO
     *
     * @param other
     * @return
     */
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
