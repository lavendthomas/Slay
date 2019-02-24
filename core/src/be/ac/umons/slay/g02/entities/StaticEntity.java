package be.ac.umons.slay.g02.entities;

public enum StaticEntity implements Entity {
    CAPITAL(0, 0, "capital"),
    TREE(0, 0, "tree"),
    GRAVE(0, 0, "grave");

    private int price;
    private int cost;
    private String name;

    StaticEntity(int price, int cost, String name) {
        this.price = price;
        this.cost = cost;
        this.name = name;
    }

    public int getPrice() {
        return this.price;
    }

    public int getCost() {
        return this.cost;
    }

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
