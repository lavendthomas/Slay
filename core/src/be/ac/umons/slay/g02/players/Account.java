package be.ac.umons.slay.g02.players;

/**
 * Class containing the username and the password of a logged user
 */
public class Account {

    String username;
    String password;

    /**
     * Gives the username
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets a username
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gives the password
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets a password
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}