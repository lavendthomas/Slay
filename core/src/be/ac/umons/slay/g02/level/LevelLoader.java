package be.ac.umons.slay.g02.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.players.Colors;
import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.Player;

public class LevelLoader {

    public static final String LEVELS_PATH = "worlds";

    /**
     * Returns a Map object from the the following files :
     *  - assets/world/filename.tmx
     *  - assets/world/filename.xml
     *
     *  Loads the world using the pattern mentioned in the requirements.
     *
     * @param levelname the name of the level (filename without extension)
     * @return a Map object consisting of a Slay Level and a libGDX TiledMap for the GUI.
     * @throws FileFormatException If the file don't use the correct format
     */
    public static Map load(String levelname) throws FileFormatException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String name;
        int width = 0;
        int height = 0;

        TiledMap map = new TmxMapLoader().load(LEVELS_PATH + "/" +levelname + ".tmx");

        MapProperties prop = map.getProperties();

        width = prop.get("width", Integer.class);
        height = prop.get("height", Integer.class);

        Level level = new Level(width, height);


        // Load water and playable territories

        TiledMapTileLayer background = (TiledMapTileLayer) map.getLayers().get("Background");

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                TileType type = Tile.fromId(background.getCell(i, j).getTile().getId()).toTileType(); //TODO crash si id = 0
                Coordinate coords = new Coordinate(i, j);
                level.set(new be.ac.umons.slay.g02.level.Tile(type), coords);
            }
        }
        // Add territories and players

        List<Player> players = new ArrayList<Player>();
        List<Integer> colors = new ArrayList<Integer>();

        TiledMapTileLayer terr = (TiledMapTileLayer) map.getLayers().get("Territories");
        //int p1nb = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Creates a new territory for each tile then merges them.
                Coordinate coords = new Coordinate(i, j);
                Colors color;

                if (terr.getCell(i, j) != null) {
                    int colorId = terr.getCell(i, j).getTile().getId();

                    int index = colors.indexOf(colorId);
                    if (index == -1) {
                        // Add a new player for this color
                        players.add(new HumanPlayer("p" + players.size(), Colors.fromId(colorId)));
                        colors.add(colorId);
                        index = colors.indexOf(colorId);
                    }

                    level.get(i, j).setTerritory(new Territory(players.get(index), level.get(coords)));
                }
            }
        }

        level.mergeTerritories();

        // Add entities

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            FileHandle fileXML = Gdx.files.internal(LEVELS_PATH + "/" +  levelname + ".xml");
            fileXML.copyTo(Gdx.files.local( "currentLevel.xml"));
            Document xml = builder.parse(Gdx.files.local( "currentLevel.xml").file());
            Element root = xml.getDocumentElement();
            name = root.getAttribute("name");

            for (int i = 0; i < root.getChildNodes().getLength(); i++) {
                Node n = root.getChildNodes().item(i);
                if (n.getNodeName() == "players") {
                    // handle players
                    Element plys = (Element) n;
                    int nbPlayers = Integer.parseInt(plys.getAttribute("number"));
                    //TODO nbplayers == players.size()

                } else if (n.getNodeName() == "items") {
                    // handle items
                    for (int j = 0; j < n.getChildNodes().getLength(); j++) {
                        // add each item to the map

                        Node item = n.getChildNodes().item(j);
                        if (item.getNodeName().equals("item")) {
                            Element itm = (Element) item;
                            String type = itm.getAttribute("type");
                            int x = Integer.parseInt(itm.getAttribute("x"));
                            int y = Integer.parseInt(itm.getAttribute("y"));
                            Coordinate coords = new Coordinate(x, y);
                            StaticEntity e = StaticEntity.fromString(type);
                            level.set(e, coords);
                            // TODO read the coins if the item is a capital and add it to the territory
                        }
                    }

                } else if (n.getNodeName() == "units") {
                    // handle units
                    for (int j = 0; j < n.getChildNodes().getLength(); j++) {
                        // add each item to the map

                        Node unit = n.getChildNodes().item(j);
                        if (unit.getNodeName().equals("unit")) {
                            Element unt = (Element) unit;
                            String type = unt.getAttribute("type");
                            int x = Integer.parseInt(unt.getAttribute("x"));
                            int y = Integer.parseInt(unt.getAttribute("y"));
                            Coordinate coords = new Coordinate(x, y);

                            if (type.equals("soldier")) {
                                int lvl = Integer.parseInt(unt.getAttribute("level"));
                                Soldier s = new Soldier(SoldierLevel.fromLevel(lvl));
                                if (level.get(coords).getTerritory() == null) {
                                    // A soldier has to belong to a territory
                                    throw new FileFormatException("A soldier has to belong to a territory");
                                }
                                level.set(s, coords);
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            Gdx.app.error("slay", e.getMessage());
            throw new FileFormatException(e);
        } catch (IOException e) {
            Gdx.app.error("slay", e.getMessage());
            throw new FileFormatException(e);
        } catch (SAXException e) {
            Gdx.app.error("slay", e.getMessage());
            throw new FileFormatException(e);
        }
        return new Map(level, map);
    }

    /**
     * Return object for loading the world. Consists of a Level object and a libGDX TiledMap.
     */
    public static class Map {
        private Level lvl;
        private TiledMap map;

        Map(Level lvl, TiledMap map) {
            this.lvl = lvl;
            this.map = map;
        }

        public Level getLevel() {
            return this.lvl;
        }

        public TiledMap getMap() {
            return this.map;
        }

    }

    /**
     * Table matching the id written in the TMX file to a TileType
     */
    public enum Tile {

        TERRITORY(7, TileType.NEUTRAL),
        WATER(9, TileType.WATER);

        int id;
        TileType type;

        Tile(int id, TileType type) {
            this.id = id;
            this.type = type;
        }


        /**
         * Returns the id of this TileType
         * @return
         */
        public int getId() {
            return id;
        }

        public TileType toTileType() {
            return type;
        }

        /**
         * Returns the TileType matching the id
         * @param id the id for which we want the id
         * @return A tileType object
         * @throws FileFormatException If the id does not match any TileType
         */
        public static Tile fromId(int id) throws FileFormatException {
            for (Tile tile : LevelLoader.Tile.values()) {
                if (tile.id == id) {
                    return tile;
                }
            }
            throw new FileFormatException();
        }
    }

}