package be.ac.umons.slay.g02.entities;

/**
 *   TODO
 */
public enum StaticEntity implements Entity {
    CAPITAL(0, 0, "capital"),
    TREE(0, 0, "tree"),
    GRAVE(0, 0, "grave");

    private int price;
    private int cost;
    private String name;

    /**
     * Class constructor
     *
     * @param price  TODO
     * @param cost
     * @param name
     */
    StaticEntity(int price, int cost, String name) {
        this.price = price;
        this.cost = cost;
        this.name = name;
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
     * Returns the StaticEntity corresponding to the name in parameter
     *
     * @param name The name of the StaticEntity to return
     * @return
     */
    public static StaticEntity fromString(String name) {
        for (StaticEntity entity : StaticEntity.values()) {
            if (entity.name.equals(name)) {
                return entity;
            }
        }
        return CAPITAL;
    }
}