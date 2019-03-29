package be.ac.umons.slay.g02.tests;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import be.ac.umons.slay.g02.gui.Main;
import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.Statistics;
import be.ac.umons.slay.g02.players.StatsLoader;

import static org.junit.Assert.assertEquals;

/**
 * Class testing StatsLoader, i.e. it tests if players data that are in a xml file are
 * correctly retrieved by testing if the recovered value is equal to the stored value
 * <p>
 * Tests are for global and levels statistics
 */
public class StatsLoaderTests {
    static ArrayList tabPlayers;
    static Element root;
    static Element element;
    static HumanPlayer player;

    static boolean isFound;
    static int i;

    /**
     * Creates a player then goes to the first value to read
     * The file used has been specifically made for testing
     */
    @BeforeClass
    public static void setUp() {
        Main.setNameFile("PlayerDataTest.xml");
        Main.isInTest = true;

        // Creates a player
        StatsLoader statsLoader = new StatsLoader();
        tabPlayers = statsLoader.createTab();
        player = (HumanPlayer) tabPlayers.get(0);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        File fileStats = new File(Main.getNameFile());
        if (fileStats.exists() && fileStats.length() != 0) {
            try {
                final DocumentBuilder builder = factory.newDocumentBuilder();
                final Document document = builder.parse(new File(Main.getNameFile()));
                root = document.getDocumentElement();

                for (int i = 0; i < root.getChildNodes().getLength(); i++) {

                    Node n = root.getChildNodes().item(i);

                    if (n.getNodeName().equals("player"))
                        element = (Element) n;
                }
            } catch (final ParserConfigurationException e) {
                e.printStackTrace();
            } catch (final SAXException e) {
                e.printStackTrace();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    @After
    public void tearDown() {
        isFound = false;
        i = 0;
    }

    @Test
    public void testName() {
        String name = "";
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {
            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("name")) {
                name = elem.getFirstChild().getNodeValue();
                isFound = true;
            }
            i++;
        }
        assertEquals(player.getName(), name);
    }

    @Test
    public void testPassword() {
        String password = "";
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {
            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("password")) {
                password = elem.getFirstChild().getNodeValue();
                isFound = true;
            }
            i++;
        }
        assertEquals(player.getAccount().getPassword(), password);
    }

    @Test
    public void testAvatar() {
        String avatar = "";
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {
            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("avatar")) {
                avatar = elem.getFirstChild().getNodeValue();
                isFound = true;
            }
            i++;
        }
        assertEquals(player.getAvatar(), avatar);
    }

    @Test
    public void testScore() {
        int score = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {
            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("score")) {
                score = Integer.parseInt(elem.getFirstChild().getNodeValue());
                isFound = true;
            }
            i++;
        }
        assertEquals(player.getGlobalStats().getScore(), score);
    }

    @Test
    public void testGameG() {
        int game = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {
            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("__1__Games")) {
                game = Integer.parseInt(elem.getFirstChild().getNodeValue());
                isFound = true;
            }
            i++;
        }
        assertEquals((int) player.getGlobalStats().getStats().get(Statistics.GAMES), game);
    }

    @Test
    public void testGameL() {
        int game = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {

            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("levelStats")) {
                int k = 0;
                while (k < elem.getChildNodes().getLength() && !isFound) {
                    Element e = (Element) elem.getChildNodes().item(k);
                    if (e.getNodeName().equals("__1__Games")) {
                        game = Integer.parseInt(e.getFirstChild().getNodeValue());
                        isFound = true;
                    }
                    k++;
                }
            }
            i++;
        }
        assertEquals((int) player.getListLevelStats(1).getStats().get(Statistics.GAMES), game);
    }

    @Test
    public void testWinsG() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {
            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("__2__Wins")) {
                value = Integer.parseInt(elem.getFirstChild().getNodeValue());
                isFound = true;
            }
            i++;
        }
        assertEquals((int) player.getGlobalStats().getStats().get(Statistics.WINS), value);
    }

    @Test
    public void testWinsL() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {

            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("levelStats")) {
                int k = 0;
                while (k < elem.getChildNodes().getLength() && !isFound) {
                    Element e = (Element) elem.getChildNodes().item(k);
                    if (e.getNodeName().equals("__2__Wins")) {
                        value = Integer.parseInt(e.getFirstChild().getNodeValue());
                        isFound = true;
                    }
                    k++;
                }
            }
            i++;
        }
        assertEquals((int) player.getListLevelStats(1).getStats().get(Statistics.WINS), value);
    }

    @Test
    public void testDefeatsG() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {
            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("__3__Defeats")) {
                value = Integer.parseInt(elem.getFirstChild().getNodeValue());
                isFound = true;
            }
            i++;
        }
        assertEquals((int) player.getGlobalStats().getStats().get(Statistics.DEFEATS), value);
    }

    @Test
    public void testDefeatsL() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {

            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("levelStats")) {
                int k = 0;
                while (k < elem.getChildNodes().getLength() && !isFound) {
                    Element e = (Element) elem.getChildNodes().item(k);
                    if (e.getNodeName().equals("__3__Defeats")) {
                        value = Integer.parseInt(e.getFirstChild().getNodeValue());
                        isFound = true;
                    }
                    k++;
                }
            }
            i++;
        }
        assertEquals((int) player.getListLevelStats(1).getStats().get(Statistics.DEFEATS), value);
    }

    @Test
    public void testMinTurnsG() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {
            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("__4__min_Turns")) {
                value = Integer.parseInt(elem.getFirstChild().getNodeValue());
                isFound = true;
            }
            i++;
        }
        assertEquals((int) player.getGlobalStats().getStats().get(Statistics.MIN_TURNS), value);
    }

    @Test
    public void testMinTurnsL() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {

            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("levelStats")) {
                int k = 0;
                while (k < elem.getChildNodes().getLength() && !isFound) {
                    Element e = (Element) elem.getChildNodes().item(k);
                    if (e.getNodeName().equals("__4__min_Turns")) {
                        value = Integer.parseInt(e.getFirstChild().getNodeValue());
                        isFound = true;
                    }
                    k++;
                }
            }
            i++;
        }
        assertEquals((int) player.getListLevelStats(1).getStats().get(Statistics.MIN_TURNS), value);
    }

    @Test
    public void testTurnsG() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {
            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("__5__Turns")) {
                value = Integer.parseInt(elem.getFirstChild().getNodeValue());
                isFound = true;
            }
            i++;
        }
        assertEquals((int) player.getGlobalStats().getStats().get(Statistics.TURNS), value);
    }

    @Test
    public void testTurnsL() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {

            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("levelStats")) {
                int k = 0;
                while (k < elem.getChildNodes().getLength() && !isFound) {
                    Element e = (Element) elem.getChildNodes().item(k);
                    if (e.getNodeName().equals("__5__Turns")) {
                        value = Integer.parseInt(e.getFirstChild().getNodeValue());
                        isFound = true;
                    }
                    k++;
                }
            }
            i++;
        }
        assertEquals((int) player.getListLevelStats(1).getStats().get(Statistics.TURNS), value);
    }

    @Test
    public void testMaxLandsTurnsG() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {
            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("__6__max_Lands_Turn")) {
                value = Integer.parseInt(elem.getFirstChild().getNodeValue());
                isFound = true;
            }
            i++;
        }
        assertEquals((int) player.getGlobalStats().getStats().get(Statistics.MAX_LANDS_TURN), value);
    }

    @Test
    public void testMaxLandsTurnsL() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {

            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("levelStats")) {
                int k = 0;
                while (k < elem.getChildNodes().getLength() && !isFound) {
                    Element e = (Element) elem.getChildNodes().item(k);
                    if (e.getNodeName().equals("__6__max_Lands_Turn")) {
                        value = Integer.parseInt(e.getFirstChild().getNodeValue());
                        isFound = true;
                    }
                    k++;
                }
            }
            i++;
        }
        assertEquals((int) player.getListLevelStats(1).getStats().get(Statistics.MAX_LANDS_TURN), value);
    }

    @Test
    public void testLandsTurnsG() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {
            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("__7__Lands_Turn")) {
                value = Integer.parseInt(elem.getFirstChild().getNodeValue());
                isFound = true;
            }
            i++;
        }
        assertEquals((int) player.getGlobalStats().getStats().get(Statistics.LANDS_TURN), value);
    }

    @Test
    public void testLandsTurnsL() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {

            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("levelStats")) {
                int k = 0;
                while (k < elem.getChildNodes().getLength() && !isFound) {
                    Element e = (Element) elem.getChildNodes().item(k);
                    if (e.getNodeName().equals("__7__Lands_Turn")) {
                        value = Integer.parseInt(e.getFirstChild().getNodeValue());
                        isFound = true;
                    }
                    k++;
                }
            }
            i++;
        }
        assertEquals((int) player.getListLevelStats(1).getStats().get(Statistics.LANDS_TURN), value);
    }

    @Test
    public void testMaxTreesG() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {
            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("__8__max_Trees")) {
                value = Integer.parseInt(elem.getFirstChild().getNodeValue());
                isFound = true;
            }
            i++;
        }
        assertEquals((int) player.getGlobalStats().getStats().get(Statistics.MAX_TREES), value);
    }

    @Test
    public void testMaxTreesL() {
        int value = 0;
        Element elem;

        while (i < element.getChildNodes().getLength() && !isFound) {

            elem = (Element) element.getChildNodes().item(i);
            if (elem.getNodeName().equals("levelStats")) {
                int k = 0;
                while (k < elem.getChildNodes().getLength() && !isFound) {
                    Element e = (Element) elem.getChildNodes().item(k);
                    if (e.getNodeName().equals("__8__max_Trees")) {
                        value = Integer.parseInt(e.getFirstChild().getNodeValue());
                        isFound = true;
                    }
                    k++;
                }
            }
            i++;
        }
        assertEquals((int) player.getListLevelStats(1).getStats().get(Statistics.MAX_TREES), value);
    }
}