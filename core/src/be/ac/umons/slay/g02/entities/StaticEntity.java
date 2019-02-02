package be.ac.umons.slay.g02.entities;

public enum StaticEntity implements Entity {
    Capital (0, 0),
    Tree (0, 0),
    Grave (0, 0);

    private int price;
    private int cost;

    StaticEntity(int price, int cost) {
        this.price = price;
        this.cost = cost;
    }

    public int getPrice (){
        return this.price;
    }

    public int getCost() {
        return this.cost;
    }

}
