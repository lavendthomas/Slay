package be.ac.umons.slay.g02.entities;

public enum SoldierLevel {

    L0(0, 10, 2),
    L1(1, 20, 5),
    L2(2, 40, 14),
    L3(3, 80, 41);

    private int costs;

    private int price;

    private int level;

    SoldierLevel(int level, int price, int costs) {
        this.level = level;
        this.costs = costs;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public int getCosts() {
        return costs;
    }

    public int getLevel() {
        return level;
    }
}

