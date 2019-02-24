package be.ac.umons.slay.g02.players;

public enum Colors {
    C1("green", 1),
    C2("red", 2),
    C3("darkgreen", 3),
    C4("pink", 4),
    C5("yellow", 5),
    C6("darkred", 6),
    C7("darkblue", 8);

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
