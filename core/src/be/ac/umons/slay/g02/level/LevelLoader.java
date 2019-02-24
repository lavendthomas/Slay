package be.ac.umons.slay.g02.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import java.io.File;
import java.io.IOException;
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
import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.Player;

public class LevelLoader {

    public static final String LEVELS_PATH = "worlds";
    public static final int WATER = 9;
    public static final int TERRITORY = 7;

    public static Map load(String levelname) throws FileFormatException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String name;
        int players;
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

        Player p1 = new HumanPlayer("p1");
        Player p2 = new HumanPlayer("p2");

        // Add territories

        TiledMapTileLayer terr = (TiledMapTileLayer) map.getLayers().get("Territories");
        int p1nb = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Creates a new territory for each tile then merges them.
                Coordinate coords = new Coordinate(i, j);
                int id;
                if (terr.getCell(i, j) == null) {
                    // No cell if the id is 0
                    id = 0;
                } else {
                    id = terr.getCell(i, j).getTile().getId();
                }

                if (id != 0) {
                    if (p1nb == 0) {
                        p1nb = id;
                    }
                    if (id == p1nb) {
                        // The tile is owned by p1
                        be.ac.umons.slay.g02.level.Tile tile = level.get(coords);
                        level.setTerritory(new Territory(p1, tile), coords);
                    } else {
                        // Add the tile is owned by p2
                        be.ac.umons.slay.g02.level.Tile tile = level.get(coords);
                        level.setTerritory(new Territory(p2, tile), coords);
                    }
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
                    players = Integer.parseInt(plys.getAttribute("number"));

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

    public enum Tile {

        TERRITORY(7, TileType.NEUTRAL),
        WATER(9, TileType.WATER);

        int id;
        TileType type;

        Tile(int id, TileType type) {
            this.id = id;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public TileType toTileType() {
            return type;
        }

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