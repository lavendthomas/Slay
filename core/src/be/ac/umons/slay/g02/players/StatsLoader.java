package be.ac.umons.slay.g02.players;

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


public class StatsLoader {


    public StatsLoader() {

    }

    public ArrayList createTab() {
        ArrayList tabScore = new ArrayList();
        Statis statis = new Statis();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();

            final Document document = builder.parse(new File("statsPlayers.xml"));

            Element root = document.getDocumentElement();

            for (int i = 0 ; i < root.getChildNodes().getLength() ; i++) {
                Node n = root.getChildNodes().item(i);
                if (n.getNodeName().equals("player")) {
                    if (i != 0) {
                        tabScore.add(statis);
                    }
                    statis = new Statis();

                    Element plys = (Element) n;
                    statis.setRank(Integer.parseInt(plys.getAttribute("rank")));

                    for (int j = 0 ; j < plys.getChildNodes().getLength() ; j++) {

                        Element plys2 = (Element) plys.getChildNodes().item(j);

                        if (plys2.getNodeName().equals("name")) {
                            statis.setName(plys2.getFirstChild().getNodeValue());
                        }
                        if (plys2.getNodeName() == "password") {
                            statis.setPassword(plys2.getFirstChild().getNodeValue());
                        }
                        if (plys2.getNodeName() == "avatar") {
                            statis.setAvatar(plys2.getFirstChild().getNodeValue());
                        }
                        if (plys2.getNodeName() == "army") {
                            statis.setArmy(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName() == "games") {
                            statis.setGames(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName() == "wins") {
                            statis.setWins(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName() == "lands") {
                            statis.setLands(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName() == "losses") {
                            statis.setLosses(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName() == "trees") {
                            statis.setTrees(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName() == "turns") {
                            statis.setTurns(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName() == "score") {
                            statis.setScore(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                    }
                }
            }
            tabScore.add(statis);

        } catch (final ParserConfigurationException e) {
            e.printStackTrace();
        } catch (final SAXException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return tabScore;
    }
}