package be.ac.umons.slay.g02.entities;

public enum SoldierLevel {

    L0(0, 10, 2),
    L1(1, 20, 5),
    L2(2, 40, 14),
    L3(3, 80, 41);

    private int level;
    private int price;
    private int cost;

    SoldierLevel(int level, int price, int cost) {
        this.level = level;
        this.price = price;
        this.cost = cost;
    }

    public int getLevel () {
        return this.level;
    }

    public int getPrice() {
        return this.price;
    }

    public int getCost() {
        return this.cost;
    }

    public static SoldierLevel fromLevel(int lvl) {
        for (SoldierLevel soldierLevel: SoldierLevel.values()) {
            if (lvl == soldierLevel.level) {
                return soldierLevel;
            }
        }
        return L0; // Give a default value if the level doesn't exist
    }

}

