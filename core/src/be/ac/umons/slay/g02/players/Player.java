package be.ac.umons.slay.g02.players;

/**
 *    //TODO
 */
public abstract class Player {
    String name;
    String avatar;
    Colors color;

    /**
     *  //TODO
     *
     * @return
     */
    public Colors getColor() {
        return color;
    }

    /**
     *  //TODO
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *  //TODO
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *  //TODO
     *
     * @param color
     */
    public void setColor(Colors color) {
        this.color = color;
    }

    /**
     *  //TODO
     *
     * @return
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     *  //TODO
     *
     * @param avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     *  //TODO
     *
     * @param o
     * @return
     */
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