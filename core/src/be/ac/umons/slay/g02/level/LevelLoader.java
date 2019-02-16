package be.ac.umons.slay.g02.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.io.File;
import java.io.IOException;

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

public class LevelLoader {

    public static final String LEVELS_PATH = "worlds";
    public static final int WATER = 9;
    public static final int TERRITORY = 7;

    public static Map load(String levelname) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String name;
        int players;
        int width = 0;
        int height = 0;

        TiledMap map = new TmxMapLoader().load(LEVELS_PATH + File.separator + levelname + ".tmx");
        MapProperties prop = map.getProperties();

        width = prop.get("width", Integer.class);
        height = prop.get("height", Integer.class);

        Level level = new Level(width, height);


        // Load water and playable territories

        TiledMapTileLayer background = (TiledMapTileLayer) map.getLayers().get("Background");

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Coordinate coords = new Coordinate(i, j);
                switch (background.getCell(i, j).getTile().getId()) {
                    case WATER:
                        level.set(new Tile(TileType.WATER), coords);
                        break;
                    case TERRITORY:
                        level.set(new Tile(TileType.NEUTRAL), coords);
                        break;
                    // TODO default value ?
                }
            }
        }

        // Add territories

        TiledMapTileLayer terr = (TiledMapTileLayer) map.getLayers().get("Territories");

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Coordinate coords = new Coordinate(i, j);
                // TODO Detect if 2 cells are part of the same territory and add the territory
            }
        }

        // Add entities

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            File fileXML = Gdx.files.internal(LEVELS_PATH + File.separator + levelname + ".xml").file();
            Document xml = builder.parse(fileXML);
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return new Map(level, map);
    }

    public static class Map {
        private Level lvl;
        private TiledMap map;

        Map (Level lvl, TiledMap map) {
            this.lvl = lvl;
            this.map = map;
        }

        public Level getLevel(){
            return this.lvl;
        }

        public TiledMap getMap() {
            return this.map;
        }

    }

}