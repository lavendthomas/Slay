package be.ac.umons.slay.g02.entities;

/**
 *   TODO
 */
public enum SoldierLevel {

    L0(0, 10, 2, "L0"),
    L1(1, 20, 5, "L1"),
    L2(2, 40, 14, "L2"),
    L3(3, 80, 41, "L3");

    private int level;
    private int price;
    private int cost;
    private String name;

    /**
     * Class constructor
     *
     * @param level  TODO
     * @param price
     * @param cost
     * @param name
     */
    SoldierLevel(int level, int price, int cost, String name) {
        this.level = level;
        this.price = price;
        this.cost = cost;
        this.name = name;
    }

    /**
     *   TODO
     *
     * @return
     */
    public int getLevel () {
        return this.level;
    }

    /**
     *   TODO
     *
     * @return
     */
    public int getPrice() {
        return this.price;
    }

    /**
     *   TODO
     *
     * @return
     */
    public int getCost() {
        return this.cost;
    }

    /**
     *   TODO
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     *   TODO
     *
     * @param lvl
     * @return
     */
    public static SoldierLevel fromLevel(int lvl) {
        for (SoldierLevel soldierLevel: SoldierLevel.values()) {
            if (lvl == soldierLevel.level) {
                return soldierLevel;
            }
        }
        return L0; // Give a default value if the level doesn't exist
    }
}