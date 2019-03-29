package be.ac.umons.slay.g02.players;

import com.badlogic.gdx.graphics.Color;

/**
 * Enumeration of all colors attributable to a player
 */
public enum Colors {
    C1("LIGHT_GREEN", 1),
    C2("RED", 2),
    C3("DARK_GREEN", 3),
    C4("PINK", 4),
    C5("YELLOW", 5),
    C6("DARK_RED", 6),
    C7("BLUE", 7);

    private String name;
    private int id;

    /**
     * The name and associated number of the color
     *
     * @param name its name
     * @param id   its identifier
     */
    Colors(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Gives the color's name
     *
     * @return the color's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gives the color associated with the id
     *
     * @param id the id for which we want the corresponding color
     * @return the corresponding color
     */
    public static Colors fromId(int id) {
        for (Colors color : Colors.values()) {
            if (id == color.id) {
                return color;
            }
        }
        return C1; // Give a default value if the level doesn't exist
    }

    /**
     * Converts an instance of Colors into an instance of Color (no "s")
     *
     * @return the corresponding instance of Color
     */
    public Color toColor() {
        Color color = new Color();

        // LIGHT_GREEN
        if (this.name.equals(C1.getName()))
            color = new Color(19 / 255f, 244 / 255f, 27 / 255f, 1);
            // RED
        else if (this.name.equals(C2.getName()))
            color = new Color(244 / 255f, 19 / 255f, 64 / 255f, 1);
            // DARK_GREEN
        else if (this.name.equals(C3.getName()))
            color = new Color(8 / 255f, 100 / 255f, 19 / 255f, 1);
            // PINK
        else if (this.name.equals(C4.getName()))
            color = new Color(254 / 255f, 2 / 255f, 202 / 255f, 1);
            // YELLOW
        else if (this.name.equals(C5.getName()))
            color = new Color(255 / 255f, 253 / 255f, 27 / 255f, 1);
            // DARK_RED
        else if (this.name.equals(C6.getName()))
            color = new Color(187 / 255f, 9 / 255f, 64 / 255f, 1);
            // BLUE
        else if (this.name.equals(C7.getName()))
            color = new Color(2 / 255f, 10 / 255f, 158 / 255f, 1);

        return color;
    }
}