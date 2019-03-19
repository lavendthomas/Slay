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
import java.util.Random;

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
import be.ac.umons.slay.g02.gui.screens.LevelSelection;
import be.ac.umons.slay.g02.gui.screens.Menu;
import be.ac.umons.slay.g02.players.AIAdvanced;
import be.ac.umons.slay.g02.players.AIEasy;
import be.ac.umons.slay.g02.players.AIMedium;
import be.ac.umons.slay.g02.players.AIRandom;
import be.ac.umons.slay.g02.players.Colors;
import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.Player;

public class LevelLoader {

    private static final String LEVELS_PATH = "worlds";

    private static Player[] players;
    /**
     * .
     * Returns a Map object from the the following files :
     *  - assets/world/filename.tmx
     *  - assets/world/filename.xml
     *
     *  Loads the world using the pattern mentioned in the requirements.
     *
     * @param levelName the name of the level (filename without extension)
     * @return a Map object consisting of a Slay Level and a libGDX TiledMap for the GUI.
     * @throws FileFormatException If the file don't use the correct format
     */

    public static Map load(String levelName) throws FileFormatException {
        String tmxName = LEVELS_PATH + File.separator +levelName + ".tmx";
        String xmlName = LEVELS_PATH + File.separator +levelName + ".xml";

        // Init level and load water and playable territories
        TiledMap map = new TmxMapLoader().load(tmxName);
        Level level = loadBackground(map);

        //Load xml file
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Element root;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            FileHandle fileXML = Gdx.files.internal(xmlName);
            fileXML.copyTo(Gdx.files.local("currentLevel.xml"));
            Document xml = builder.parse(Gdx.files.local("currentLevel.xml").file());
            Gdx.files.local("currentLevel.xml").delete();
            root = xml.getDocumentElement();

            // Add players
            loadPlayers(root);

            // Add territories
            loadTerritories(root, level);

            // Add entities
            loadEntities(root, level);

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
     * Load map bottom from the TiledMap
     *
     * @param map TiledMap
     * @return The level with the bottom
     */

    private static Level loadBackground (TiledMap map) {
        MapProperties prop = map.getProperties();

        int width = prop.get("width", Integer.class);
        int height = prop.get("height", Integer.class);

        Level level = new Level(width, height);

        TiledMapTileLayer background = (TiledMapTileLayer) map.getLayers().get("Background");

        try {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int id = background.getCell(i, j).getTile().getId();
                    TileType type = TileSetManagement.fromId(id).getType();
                    if (type != null) {
                        Tile tile = new Tile(type);
                        Coordinate coord = new Coordinate(i, j);
                        level.set(tile, coord);
                    }
                }
            }
        } catch (FileFormatException e) {
            Gdx.app.error("slay", e.getMessage());
        }
        return level;
    }

    /**
     * Retrieve players from the game (number and type) and initialize them
     *
     * @param root The root of XML file
     */

    private static void loadPlayers (Element root) {
        List<Colors> alreadyUsed = new ArrayList<Colors>();
        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            Node n = root.getChildNodes().item(i);
            // Load players numbers and init players tab
            if (n.getNodeName().equals("players")) {
                Element plys = (Element) n;
                int nbPlayers = Integer.parseInt(plys.getAttribute("number"));
                players = new Player[nbPlayers];
                int countHuman = 0;
                int numberHumans = LevelSelection.numberHumans;
                for (int p = 0; p < players.length; p++) {
                    int rand = new Random().nextInt(8);
                    Colors color = Colors.fromId(rand);
                    while (alreadyUsed.contains(color)) {
                        rand = new Random().nextInt(8);
                        color = Colors.fromId(rand);
                    }
                    alreadyUsed.add(color);
                    Player player;
                    int difficulty;

                    switch (numberHumans) {
                        case 0:
                            // Two AIs
                            difficulty = LevelSelection.difficulty;
                            player = fromDifficulty (difficulty, color, "P" + (p+1));
                            break;
                        case 1:
                            // One AI and One human player
                            if (countHuman == 0) {
                                player = loadHumanPlayer(countHuman, color);
                                countHuman += 1;
                                break;
                            } else {
                                difficulty = LevelSelection.difficulty;
                                player = fromDifficulty(difficulty, color, "P" + (p+1));
                                break;
                            }

                        default:
                            // Two human players
                            player = loadHumanPlayer(countHuman, color);
                            countHuman += 1;
                            break;
                    }

                    players[p] = player;
                }
            }

        }
    }

    private static Player loadHumanPlayer (int countHuman, Colors color) {
        Player player;
        if (countHuman == 0) {
            if (Menu.isPlayer1Logged) {
                player = Menu.player1;
                player.setColor(color);
                player.setAvatar(Menu.player1.getAvatar());
            } else {
                player = new HumanPlayer("P1", color);
                player.setAvatar("profile" + File.separator + "anonymous.png");
            }
        } else {
            if (Menu.isPlayer2Logged) {
                player = Menu.player2;
                player.setColor(color);
                player.setAvatar(Menu.player2.getAvatar());
            } else {
                player = new HumanPlayer("P2", color);
                player.setAvatar("profile" + File.separator + "anonymous.png");
            }
        }
        return player;
    }

    /**
     * Method initiating artificial intelligence from its difficulty level
     *
     * @param difficulty Integer representing AI difficulty
     * @param color      Color assigned to AI
     * @param name       Name assigned to AI
     * @return           The artificial intelligence corresponding
     */

    private static Player fromDifficulty (int difficulty, Colors color, String name) {
        Player player;
        switch (difficulty) {
            case 1 :
                player = new AIEasy(color, name);
                break;
            case 2 :
                player = new AIMedium(color, name);
                break;
            case 3 :
                player = new AIAdvanced(color, name);
                break;
            default:
                player = new AIRandom(color, name);
                break;
        }
        return player;
    }

    /**
     * Load territories in the level
     *
     * @param root                  The root of XML file
     * @param level                 The level in which the territories
     * @throws FileFormatException  If the file don't use the correct format
     */

    private static void loadTerritories (Element root, Level level) throws FileFormatException {
        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            Node n = root.getChildNodes().item(i);

            if (n.getNodeName().equals("territories")) {
                for (int j = 0; j < n.getChildNodes().getLength(); j++) {
                    // Add each territory to the map

                    Node terr = n.getChildNodes().item(j);
                    if (terr.getNodeName().equals("terr")) {
                        Element tty = (Element) terr;
                        int numPlayer = Integer.parseInt(tty.getAttribute("player"));
                        int x = Integer.parseInt(tty.getAttribute("x"));
                        int y = Integer.parseInt(tty.getAttribute("y"));
                        Coordinate coord = new Coordinate(x, y);
                        if (level.get(coord).getType() == TileType.WATER) {
                            // A territory has to be on a neutral type
                            throw new FileFormatException("A territory has to be on a neutral type");
                        }
                        Territory territory = new Territory(players[numPlayer], level.get(coord));
                        level.get(coord).setTerritory(territory);
                    }
                }
            }

        }
        level.setPlayers(players);
        level.mergeTerritories();
    }

    /**
     * Load entities in the level
     *
     * @param root                  The root of XML file
     * @param level                 The level in which the territories
     * @throws FileFormatException  If the file don't use the correct format
     */

    private static void loadEntities (Element root, Level level) throws FileFormatException {
        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            Node n = root.getChildNodes().item(i);

            if (n.getNodeName().equals("items")) {
                // handle items
                for (int j = 0; j < n.getChildNodes().getLength(); j++) {
                    // add each item and soldier to the map

                    Node item = n.getChildNodes().item(j);
                    if (item.getNodeName().equals("item")) {
                        // Add each static entity
                        Element itm = (Element) item;
                        String type = itm.getAttribute("type");
                        int x = Integer.parseInt(itm.getAttribute("x"));
                        int y = Integer.parseInt(itm.getAttribute("y"));
                        Coordinate coord = new Coordinate(x, y);


                        StaticEntity entity = StaticEntity.fromString(type);

                        if (level.get(coord).getTerritory() == null && entity == StaticEntity.CAPITAL) {
                            // A capital has to belong to a territory
                            throw new FileFormatException("A static entity has to belong to a territory");
                        }

                        level.set(entity, coord);

                        if (entity == StaticEntity.CAPITAL) {
                            // If it is a capital, add capital and coins to the territory
                            Territory territory = level.get(coord).getTerritory();
                            int coins = Integer.parseInt(itm.getAttribute("coins"));
                            territory.setCapital(level.get(coord));
                            territory.setCoins(coins);
                        }

                    }
                }
            }  else if (n.getNodeName().equals("units")) {
                // handle units
                for (int j = 0; j < n.getChildNodes().getLength(); j++) {
                    // add each soldier to the map

                    Node unit = n.getChildNodes().item(j);
                    if (unit.getNodeName().equals("unit")) {
                        // Add each Soldier

                        Element unt = (Element) unit;
                        String type = unt.getAttribute("type");
                        int x = Integer.parseInt(unt.getAttribute("x"));
                        int y = Integer.parseInt(unt.getAttribute("y"));
                        Coordinate coord = new Coordinate(x, y);

                        if (type.equals("soldier")) {
                            int lvl = Integer.parseInt(unt.getAttribute("level"));
                            Soldier s = new Soldier(SoldierLevel.fromLevel(lvl), false);
                            if (level.get(coord).getTerritory() == null) {
                                // A soldier has to belong to a territory
                                throw new FileFormatException("A soldier has to belong to a territory");
                            }
                            level.set(s, coord);
                        }
                    }
                }
            }
        }


    }


    /**
     * Return object for loading the world. Consists of a Level object and a libGDX TiledMap.
     */

    public static class Map {
        private Playable level;
        private TiledMap map;

        Map(Playable level, TiledMap map) {
            this.level = level;
            this.map = map;
        }

        public Playable getLevel() {
            return this.level;
        }

        public TiledMap getMap() {
            return this.map;
        }

    }
}