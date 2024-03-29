package be.ac.umons.slay.g02.players;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import be.ac.umons.slay.g02.gui.Main;

import static be.ac.umons.slay.g02.gui.Main.isInTest;
import static be.ac.umons.slay.g02.gui.Main.prefs;

/**
 * Class that retrieves all data from the xml file containing players data, by creating instances
 * of Account, HumanPlayer, GlobalStats and LevelStats and then putting the values in them
 */
public class StatsLoader {
    /**
     * Creates an instance of HumanPlayer for each player in the file and creates instances of
     * Account, HumanPlayer, GlobalStats and LevelStats for each one of them to store their data
     *
     * @return an instance of ArrayList containing all the instances of HumanPlayer created
     */
    public ArrayList createTab() {
        ArrayList tabScore = new ArrayList();
        HumanPlayer playerHuman = new HumanPlayer("", Colors.C1);
        GlobalStats globalStats;
        LevelStats levelStats;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        File fileStats = new File(Main.getNameFile());
        if (fileStats.exists() && fileStats.length() != 0) {
            try {
                final DocumentBuilder builder = factory.newDocumentBuilder();
                final Document document = builder.parse(new File(Main.getNameFile()));
                Element root = document.getDocumentElement();

                int levelLand = 1;

                for (int i = 0; i < root.getChildNodes().getLength(); i++) {
                    Node n = root.getChildNodes().item(i);
                    if (n.getNodeName().equals("player")) {
                        if (i != 0) {
                            tabScore.add(playerHuman);
                            levelLand = 1;
                        }
                        playerHuman = new HumanPlayer("", Colors.C1);

                        Account account = new Account();
                        ArrayList listStatsLevel = new ArrayList();
                        globalStats = new GlobalStats();
                        Element element = (Element) n;
                        globalStats.setRank(Integer.parseInt(element.getAttribute("rank")));

                        HashMap<String, Integer> hashmapGlobalStats = globalStats.getStats();

                        for (int j = 0; j < element.getChildNodes().getLength(); j++) {

                            boolean isfound = false;
                            Element elem = (Element) element.getChildNodes().item(j);

                            if (elem.getNodeName().equals("name")) {
                                playerHuman.setName(elem.getFirstChild().getNodeValue());
                                account.setUsername(elem.getFirstChild().getNodeValue());
                                isfound = true;
                            }
                            if (elem.getNodeName().equals("password")) {
                                account.setPassword(elem.getFirstChild().getNodeValue());
                                isfound = true;
                            }
                            if (elem.getNodeName().equals("avatar")) {
                                playerHuman.setAvatar(elem.getFirstChild().getNodeValue());
                                isfound = true;
                            }
                            if (elem.getNodeName().equals("score")) {
                                globalStats.setScore(Integer.parseInt(elem.getFirstChild().getNodeValue()));
                                isfound = true;
                            }

                            if (!isfound) {
                                putInHashmapStats(elem, hashmapGlobalStats);
                            }
                            if (elem.getNodeName().equals("levelStats")) {
                                levelStats = new LevelStats();
                                levelStats.setLevel(levelLand);
                                levelLand++;

                                HashMap<String, Integer> hashmapLevelStats = levelStats.getStats();

                                for (int k = 0; k < elem.getChildNodes().getLength(); k++) {
                                    Element e = (Element) elem.getChildNodes().item(k);

                                    putInHashmapStats(e, hashmapLevelStats);
                                }
                                listStatsLevel.add(levelStats);
                            }
                        }
                        playerHuman.setAccount(account);
                        playerHuman.setGlobalStats(globalStats);
                        playerHuman.setListLevelStats(listStatsLevel);
                        if (playerHuman.getGlobalStats().getScore() != 0) {
                            if (!isInTest) {
                                prefs.putInteger("totalNumberPlayers", prefs.getInteger("totalNumberPlayers") + 1);
                                prefs.flush();
                            }
                        } else playerHuman.getGlobalStats().setRank(0);
                    }
                }
                tabScore.add(playerHuman);

            } catch (final ParserConfigurationException e) {
                e.printStackTrace();
            } catch (final SAXException e) {
                e.printStackTrace();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return tabScore;
    }

    /**
     * Retrieves the value read by deleting the numbers before the statistic name (to get the
     * good key) then put it in the corresponding hashmap of statistics
     *
     * @param elementStat  the balise's name
     * @param hashmapStats the hashmap of statistics
     */
    private void putInHashmapStats(Element elementStat, HashMap<String, Integer> hashmapStats) {
        // Deletes the numbers (used for easier reading) before the statistics
        String stat = elementStat.getNodeName().substring(2);
        for (int i = 0; i < stat.length(); i++) {
            String a = "" + stat.charAt(0);
            if (!a.equals("_"))
                stat = stat.substring(1);
            else
                i = stat.length();
        }
        stat = stat.substring(2);
        if (hashmapStats.containsKey(stat))
            hashmapStats.put(stat, Integer.parseInt(elementStat.getFirstChild().getNodeValue()));
    }
}