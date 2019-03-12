package be.ac.umons.slay.g02.players;

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

    Colors (String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public static Colors fromId(int id) {
        for (Colors color: Colors.values()) {
            if (id == color.id) {
                return color;
            }
        }
        return C1; // Give a default value if the level doesn't exist
    }
}
