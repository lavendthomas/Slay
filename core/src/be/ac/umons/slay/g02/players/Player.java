package be.ac.umons.slay.g02.players;


public abstract class Player {
    String name;

    public String getName() {
        return name;
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
