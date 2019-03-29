package be.ac.umons.slay.g02.players;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import be.ac.umons.slay.g02.gui.Main;

import static be.ac.umons.slay.g02.gui.Main.tabPlayers;

/**
 * Class building the xml file containing the data of all players with an account
 */
public class FileBuilder {
    Document doc;

    private String nameFile = Main.getNameFile();

    /**
     * Creates the xml file of players data
     * <p>
     * It retrieves all players in tabPlayers in which there is all players with an account, then
     * writes all the values they have obtained for each statistic
     */
    public void createFile() {

        if (!tabPlayers.isEmpty()) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

                // Root of the xml file
                doc = docBuilder.newDocument();
                Element root = doc.createElement("statistics");
                doc.appendChild(root);
                int rank = 1;
                int island;
                int i = 0;

                while (i < tabPlayers.size()) {
                    HumanPlayer player = (HumanPlayer) tabPlayers.get(i);
                    i++;
                    Element newElement = doc.createElement("player");
                    root.appendChild(newElement);

                    Attr attr = doc.createAttribute("rank");
                    attr.setValue(String.valueOf(rank));
                    rank++;
                    newElement.setAttributeNode(attr);

                    Element nom = doc.createElement("name");
                    nom.appendChild(doc.createTextNode(player.getName()));

                    newElement.appendChild(nom);

                    Element pass = doc.createElement("password");
                    pass.appendChild(doc.createTextNode(player.getAccount().getPassword()));
                    newElement.appendChild(pass);

                    Element avatar = doc.createElement("avatar");
                    avatar.appendChild(doc.createTextNode(String.valueOf(player.getAvatar())));
                    newElement.appendChild(avatar);

                    Element score = doc.createElement("score");
                    score.appendChild(doc.createTextNode(String.valueOf(player.getGlobalStats().getScore())));
                    newElement.appendChild(score);

                    LinkedHashMap<String, Integer> hashmapGlobalStats = player.getGlobalStats().getStats();

                    writeValues(doc, newElement, hashmapGlobalStats);

                    island = 1;

                    while (island < 11) {
                        Element levelStats = doc.createElement("levelStats");
                        Attr level = doc.createAttribute("Island");
                        level.setValue(String.valueOf(island));
                        levelStats.setAttributeNode(level);

                        LinkedHashMap<String, Integer> hashmapLevelStats = player.getListLevelStats(island).getStats();

                        writeValues(doc, levelStats, hashmapLevelStats);
                        newElement.appendChild(levelStats);
                        island++;
                    }
                }
                // Converts the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(nameFile));

                transformer.transform(source, result);

            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (TransformerException tfe) {
                tfe.printStackTrace();
            }
        }
    }

    /**
     * Get the values a player has obtained for the statistics, from the hasmap of statistics in
     * the parameter entry, and writes them one by one in the file
     *
     * @param doc
     * @param newElement
     * @param hashmapStats
     */
    private void writeValues(Document doc, Element newElement, HashMap<String, Integer> hashmapStats) {
        int i = 1;
        for (Map.Entry<String, Integer> entry : hashmapStats.entrySet()) {
            String stat = entry.getKey();

            // Retrieves the value of the stat
            String value = String.valueOf(hashmapStats.get(stat));

            // Displays numbers before the statistics in the xml file for easier reading
            stat = "__" + i++ + "__" + stat;

            Element elementStat = doc.createElement(stat);
            elementStat.appendChild(doc.createTextNode(String.valueOf(value)));
            newElement.appendChild(elementStat);
        }
    }
}