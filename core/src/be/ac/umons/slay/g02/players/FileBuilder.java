package be.ac.umons.slay.g02.players;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FileBuilder {

    TreeMap<Double, Statis> map;

    public FileBuilder(TreeMap<Double, Statis> map) {
        this.map = map;
    }

    public void createFile() {
        ArrayList tabScore = new ArrayList();
        Statis stats = new Statis();

        stats.setName("WWWWWWWWWWWWWWWWW");
        stats.setPassword("pass");
        stats.setRank(1);
        stats.setArmy(11);
        stats.setScore(22300);
        stats.setAvatar("profile/anonymous.png");
        stats.setGames(6);
        stats.setLands(5);
        stats.setLosses(124);
        stats.setTrees(124);
        stats.setTurns(124);
        stats.setWins(124);
        tabScore.add(stats);

        Statis stats2 = new Statis();
        stats2.setName("yyyyyyyyy");
        stats2.setPassword("password");
        stats2.setArmy(2);
        stats2.setRank(2);
        stats2.setScore(19800);
        stats2.setAvatar("profile/worm.png");
        stats2.setGames(124);
        stats2.setLands(124);
        stats2.setLosses(124);
        stats2.setTurns(124);
        stats2.setTrees(124);
        stats2.setWins(124);
        tabScore.add(stats2);

        Statis stats3 = new Statis();
        stats3.setName("uuuu");
        stats3.setPassword("uuuu");
        stats3.setArmy(3);
        stats3.setRank(3);
        stats3.setScore(115000);
        stats3.setAvatar("profile/wolf.jpg");
        stats3.setGames(124);
        stats3.setLands(124);
        stats3.setLosses(124);
        stats3.setTurns(124);
        stats3.setTrees(124);
        stats3.setWins(124);
        tabScore.add(stats3);

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

            // racine du fichier xml
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("statistiques");
            doc.appendChild(root);
            int rank = 1;

            Set<Double> keys = map.descendingKeySet();
            for (Double key : keys) {
                Statis statis = map.get(key);

                Element player = doc.createElement("player");
                root.appendChild(player);

                Attr attr = doc.createAttribute("rank");
                attr.setValue(String.valueOf(rank));
                rank++;
                player.setAttributeNode(attr);

                Element nom = doc.createElement("name");
                nom.appendChild(doc.createTextNode(statis.getName()));
                player.appendChild(nom);

                Element pass = doc.createElement("password");
                pass.appendChild(doc.createTextNode(encrypt(statis.getPassword())));
                player.appendChild(pass);

                Element avatar = doc.createElement("avatar");
                avatar.appendChild(doc.createTextNode(String.valueOf(statis.getAvatar())));
                player.appendChild(avatar);

                Element army = doc.createElement("army");
                army.appendChild(doc.createTextNode(String.valueOf(statis.getArmy())));
                player.appendChild(army);

                Element games = doc.createElement("games");
                games.appendChild(doc.createTextNode(String.valueOf(statis.getGames())));
                player.appendChild(games);

                Element wins = doc.createElement("wins");
                wins.appendChild(doc.createTextNode(String.valueOf(statis.getWins())));
                player.appendChild(wins);

                Element lands = doc.createElement("lands");
                lands.appendChild(doc.createTextNode(String.valueOf(statis.getLands())));
                player.appendChild(lands);

                Element losses = doc.createElement("losses");
                losses.appendChild(doc.createTextNode(String.valueOf(statis.getLosses())));
                player.appendChild(losses);

                Element trees = doc.createElement("trees");
                trees.appendChild(doc.createTextNode(String.valueOf(statis.getTrees())));
                player.appendChild(trees);

                Element turns = doc.createElement("turns");
                turns.appendChild(doc.createTextNode(String.valueOf(statis.getTurns())));
                player.appendChild(turns);

                Element score = doc.createElement("score");
                score.appendChild(doc.createTextNode(String.valueOf(statis.getScore())));
                player.appendChild(score);
            }

            // convert the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("statsPlayers.xml"));

            transformer.transform(source, result);

            System.out.println("Fichier sauvegardé avec succès!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public String encrypt(String password) {
        String encrypted = "";
        for (int i = 0; i < password.length(); i++) {
            int c = password.charAt(i) ^ 48;
            encrypted = encrypted + (char) c;
        }
        return encrypted;
    }

    public String decrypt(String password) {
        String toEncrypt = "";
        for (int i = 0; i < password.length(); i++) {
            int c = password.charAt(i) ^ 48;
            toEncrypt = toEncrypt + (char) c;
        }
        return toEncrypt;
    }
}