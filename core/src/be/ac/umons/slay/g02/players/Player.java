package be.ac.umons.slay.g02.players;


public abstract class Player {
    String name;
    Colors color;
    Statistics statistics;


    public Colors getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    @Override

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Player other = (Player) o;
        return name.equals(other.name);
    }
}
