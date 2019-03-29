package be.ac.umons.slay.g02.players;

/**
 * Class assigned to a player
 */
public abstract class Player {
    String name;
    String avatar;
    Colors color;

    /**
     * Gives the color given to the player
     *
     * @return the player's color
     */
    public Colors getColor() {
        return color;
    }

    /**
     * Gives the player's name
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name
     *
     * @param name the player's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the player's color
     *
     * @param color the player's color
     */
    public void setColor(Colors color) {
        this.color = color;
    }

    /**
     * Gives the player's avatar
     *
     * @return the player's avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Sets the player's avatar
     *
     * @param avatar the player's avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
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