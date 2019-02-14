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

public class LevelLoader {

    public static final String LEVELS_PATH = "levels";
    public static final int WATER = 9;
    public static final int TERRITORY = 7;

    public static Level load(String levelname) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String name;
        int players;
        int width = 0;
        int height = 0;

        // Load the size of the map from the TMX file
        /*
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            File fileXML = Gdx.files.internal(LEVELS_PATH + File.separator + levelname +".tmx").file();
            Document xml = builder.parse(fileXML);
            Element map = xml.getDocumentElement();

            for (int i=0; i<map.getChildNodes().getLength(); i++) {
                Node n = map.getChildNodes().item(i);
                if (n.getNodeName().equals("layer")) {
                    Element elem = (Element) n;
                    width = Integer.parseInt(elem.getAttribute("width"));
                    height = Integer.parseInt(elem.getAttribute("height"));
                    break;          // We assume every layer has the same size
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } */

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
                // TODO Detect if 2 cells are part of the same territory and add the teritory
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
                            System.out.println(type + " " + x + " " + y);
                            //TODO find the object to add from enum or something like that
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
                            System.out.println(type + " " + x + " " + y);
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
        return level;
    }
}