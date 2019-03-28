package be.ac.umons.slay.g02.level;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Table matching the id written in the TMX file to a tile name
 *
 */
public enum TileSetManagement {
    VOID(0),
    LIGHT_GREEN(1),
    RED(2),
    DARK_GREEN(3),
    PINK(4),
    YELLOW(5),
    DARK_RED(6),
    BLUE(7),
    TERRITORY(8, TileType.NEUTRAL),
    WATER(9, TileType.WATER),
    GRAVE(10),
    CAPITAL(11),
    TREE(12),
    L0(13),
    L1(14),
    L2(15),
    L3(16),
    GREEN_HIGHLIGHT(17),
    DARKEN_HIGHLIGHT(18),
    WHITE_HIGHLIGHT(19);

    int id;
    TileType type;

    TileSetManagement(int id, TileType type) {
        this.id = id;
        this.type = type;
    }

    TileSetManagement(int id) {
        this(id, null);
    }

    /**
     * Returns the id of this TileType
     * @return
     */
    public int getId() {
        return id;
    }

    public TileType getType() {
        return type;
    }

    /**
     * Returns the TileType matching the id
     * @param id the id for which we want the id
     * @return A tileType object
     * @throws FileFormatException If the id does not match any TileType
     */
    public static TileSetManagement fromId(int id) throws FileFormatException {
        for (TileSetManagement tile : TileSetManagement.values()) {
            if (tile.id == id) {
                return tile;
            }
        }
        throw new FileFormatException();
    }

    /**
     * Get id from tile name
     *
     * @param name tile name
     * @return id of tile in TMX File
     */
    public static int fromName(String name) {
        for (TileSetManagement tile : TileSetManagement.values()) {
            if (tile.toString().equals(name)) {
                return tile.getId();
            }
        }
        return 0;
    }

    /**
     * Get list of tile names
     *
     * @return list of all tile names
     */
    public static List<String> getNames () {
        List<String> res = new ArrayList<String>();
        for (TileSetManagement tile : TileSetManagement.values()) {
            res.add(tile.toString());
        }
        return res;
    }
}
