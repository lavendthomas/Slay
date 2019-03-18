package be.ac.umons.slay.g02.players;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static be.ac.umons.slay.g02.gui.Main.tabPlayers;

public class FileBuilder {


    public FileBuilder() {

    }

    public void createFile() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

            // racine du fichier xml
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("statistiques");
            doc.appendChild(root);
            int rank = 1;
            int levelLand = 1;
            //  Set<Double> keys = map.descendingKeySet();
            int i = 0;
            while (i < tabPlayers.size()) {
                HumanPlayer statis = (HumanPlayer) tabPlayers.get(i);
                i++;
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
                pass.appendChild(doc.createTextNode(encrypt(statis.getAccount().getPassword())));
                player.appendChild(pass);

                Element avatar = doc.createElement("avatar");
                avatar.appendChild(doc.createTextNode(String.valueOf(statis.getAvatar())));
                player.appendChild(avatar);

                Element score = doc.createElement("score");
                score.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getScore())));
                player.appendChild(score);

                // Statistics stat2 = statis.getStatistics();

                Element games = doc.createElement("totalGames");
                games.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getTotalGames())));
                player.appendChild(games);

                Element wins = doc.createElement("totalWins");
                wins.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getTotalWins())));
                player.appendChild(wins);

                Element totalDefeats = doc.createElement("totalDefeats");
                totalDefeats.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getTotalDefeats())));
                player.appendChild(totalDefeats);

                Element avgTurns = doc.createElement("avgTurns");
                avgTurns.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgTurns())));
                player.appendChild(avgTurns);

                Element minTurns = doc.createElement("minTurns");
                minTurns.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMinTurns())));
                player.appendChild(minTurns);

                Element avgLandsTurn = doc.createElement("avgLandsTurn");
                avgLandsTurn.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgLandsTurn())));
                player.appendChild(avgLandsTurn);

                Element maxLandsTurn = doc.createElement("maxLandsTurn");
                maxLandsTurn.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxLandsTurn())));
                player.appendChild(maxLandsTurn);

                Element avgTrees = doc.createElement("avgTrees");
                avgTrees.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgTrees())));
                player.appendChild(avgTrees);

                Element maxTrees = doc.createElement("maxTrees");
                maxTrees.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxTrees())));
                player.appendChild(maxTrees);

                Element avgTotalMoney = doc.createElement("avgTotalMoney");
                avgTotalMoney.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgTotalMoney())));
                player.appendChild(avgTotalMoney);

                Element maxTotalMoney = doc.createElement("maxTotalMoney");
                maxTotalMoney.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxTotalMoney())));
                player.appendChild(maxTotalMoney);

                Element avgLosses = doc.createElement("avgLostUnits");
                avgLosses.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgLostUnits())));
                player.appendChild(avgLosses);

                Element maxUnits = doc.createElement("maxUnits");
                maxUnits.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxUnits())));
                player.appendChild(maxUnits);

                Element avgUnitsg = doc.createElement("avgUnits");
                avgUnitsg.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgUnits())));
                player.appendChild(avgUnitsg);

                Element avgLeftUnitsg = doc.createElement("avgLeftUnits");
                avgLeftUnitsg.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgLeftUnits())));
                player.appendChild(avgLeftUnitsg);

                Element avgArmy = doc.createElement("avgArmy");
                avgArmy.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgArmy())));
                player.appendChild(avgArmy);

                Element maxArmy = doc.createElement("maxArmy");
                maxArmy.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxArmy())));
                player.appendChild(maxArmy);

                Element minArmy = doc.createElement("minArmy");
                minArmy.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMinArmy())));
                player.appendChild(minArmy);

                Element totalSavings = doc.createElement("totalSavings");
                totalSavings.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getTotalSavings())));
                player.appendChild(totalSavings);

                Element avgSavings = doc.createElement("avgSavings");
                avgSavings.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgSavings())));
                player.appendChild(avgSavings);

                Element avgL0g = doc.createElement("avgL0");
                avgL0g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgL0())));
                player.appendChild(avgL0g);

                Element avgL1g = doc.createElement("avgL1");
                avgL1g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgL1())));
                player.appendChild(avgL1g);

                Element avgL2g = doc.createElement("avgL2");
                avgL2g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgL2())));
                player.appendChild(avgL2g);

                Element avgL3g = doc.createElement("avgL3");
                avgL3g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgL3())));
                player.appendChild(avgL3g);

                Element avgLostL0g = doc.createElement("avgLostL0");
                avgLostL0g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgLostL0())));
                player.appendChild(avgLostL0g);

                Element avgLostL1g = doc.createElement("avgLostL1");
                avgLostL1g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgLostL1())));
                player.appendChild(avgLostL1g);

                Element avgLostL2g = doc.createElement("avgLostL2");
                avgLostL2g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgLostL2())));
                player.appendChild(avgLostL2g);

                Element avgLostL3g = doc.createElement("avgLostL3");
                avgLostL3g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxLostL3())));
                player.appendChild(avgLostL3g);

                Element maxLostL0g = doc.createElement("maxLostL0");
                maxLostL0g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxLostL0())));
                player.appendChild(maxLostL0g);

                Element maxLostL1g = doc.createElement("maxLostL1");
                maxLostL1g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxLostL1())));
                player.appendChild(maxLostL1g);

                Element maxLostL2g = doc.createElement("maxLostL2");
                maxLostL2g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxLostL2())));
                player.appendChild(maxLostL2g);

                Element maxLostL3g = doc.createElement("maxLostL3");
                maxLostL3g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxLostL3())));
                player.appendChild(maxLostL3g);


                Element maxL0g = doc.createElement("maxL0");
                maxL0g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxL0())));
                player.appendChild(maxL0g);

                Element maxL1g = doc.createElement("maxL1");
                maxL1g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxL1())));
                player.appendChild(maxL1g);

                Element maxL2g = doc.createElement("maxL2");
                maxL2g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxL2())));
                player.appendChild(maxL2g);

                Element maxL3g = doc.createElement("maxL3");
                maxL3g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getMaxL3())));
                player.appendChild(maxL3g);

                Element avgLeftL0g = doc.createElement("avgLeftL0");
                avgLeftL0g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgLeftL0())));
                player.appendChild(avgLeftL0g);

                Element avgLeftL1g = doc.createElement("avgLeftL1");
                avgLeftL1g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgLeftL1())));
                player.appendChild(avgLeftL1g);

                Element avgLeftL2g = doc.createElement("avgLeftL2");
                avgLeftL2g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgLeftL2())));
                player.appendChild(avgLeftL2g);

                Element avgLeftL3g = doc.createElement("avgLeftL3");
                avgLeftL3g.appendChild(doc.createTextNode(String.valueOf(statis.getGlobalStats().getAvgLeftL3())));
                player.appendChild(avgLeftL3g);
                levelLand = 1;

                while (levelLand < 11) {
                    LevelStats levelStatis = statis.getListLevelStats(levelLand);

                    Element levelStats = doc.createElement("levelStats");
                    Attr level = doc.createAttribute("levelLand");
                    level.setValue(String.valueOf(levelLand));
                    levelLand++;
                    levelStats.setAttributeNode(level);

                    Element gamesLevel = doc.createElement("totalGamesLevel");
                    gamesLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getTotalGames())));
                    levelStats.appendChild(gamesLevel);

                    Element winsLevel = doc.createElement("totalWinsLevel");
                    winsLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getTotalWins())));
                    levelStats.appendChild(winsLevel);

                    Element defeatsLevel = doc.createElement("totalDefeatsLevel");
                    defeatsLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getTotalDefeats())));
                    levelStats.appendChild(defeatsLevel);

                    Element avgTurnsLevel = doc.createElement("avgTurnsLevel");
                    avgTurnsLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgTurns())));
                    levelStats.appendChild(avgTurnsLevel);


                    Element minTurnsLevel = doc.createElement("minTurnsLevel");
                    minTurnsLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMinTurns())));
                    levelStats.appendChild(minTurnsLevel);

                    Element avgLandsTurnLevel = doc.createElement("avgLandsTurnLevel");
                    avgLandsTurnLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgLandsTurn())));
                    levelStats.appendChild(avgLandsTurnLevel);


                    Element maxLandsTurnLevel = doc.createElement("maxLandsTurnLevel");
                    maxLandsTurnLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMaxLandsTurn())));
                    levelStats.appendChild(maxLandsTurnLevel);


                    Element avgTreesLevel = doc.createElement("avgTreesLevel");
                    avgTreesLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgTrees())));
                    levelStats.appendChild(avgTreesLevel);

                    Element maxTreesLevel = doc.createElement("maxTreesLevel");
                    maxTreesLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMaxTrees())));
                    levelStats.appendChild(maxTreesLevel);

                    Element avgTotalMoneyLevel = doc.createElement("avgTotalMoneyLevel");
                    avgTotalMoneyLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgTotalMoney())));
                    levelStats.appendChild(avgTotalMoneyLevel);

                    Element maxTotalMoneyLevel = doc.createElement("maxTotalMoneyLevel");
                    maxTotalMoneyLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMaxTotalMoney())));
                    levelStats.appendChild(maxTotalMoneyLevel);

                    Element avgLossesLevel = doc.createElement("avgLossesLevel");
                    avgLossesLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgLostUnits())));
                    levelStats.appendChild(avgLossesLevel);

                    Element maxUnitsLevel = doc.createElement("maxUnitsLevel");
                    maxUnitsLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMaxUnits())));
                    levelStats.appendChild(maxUnitsLevel);

                    Element avgUnitsLevel = doc.createElement("avgUnitsLevel");
                    avgUnitsLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgUnits())));
                    levelStats.appendChild(avgUnitsLevel);

                    Element avgLeftUnitsLevel = doc.createElement("avgLeftUnitsLevel");
                    avgLeftUnitsLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgLeftUnits())));
                    levelStats.appendChild(avgLeftUnitsLevel);

                    Element avgArmyLevel = doc.createElement("avgArmyLevel");
                    avgArmyLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgArmy())));
                    levelStats.appendChild(avgArmyLevel);

                    Element minArmyLevel = doc.createElement("minArmyLevel");
                    minArmyLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMinArmy())));
                    levelStats.appendChild(minArmyLevel);

                    Element maxArmyLevel = doc.createElement("maxArmyLevel");
                    maxArmyLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMaxArmy())));
                    levelStats.appendChild(maxArmyLevel);

                    Element totalSavingsLevel = doc.createElement("totalSavingsLevel");
                    totalSavingsLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getTotalSavings())));
                    levelStats.appendChild(totalSavingsLevel);

                    Element avgSavingsLevel = doc.createElement("avgSavingsLevel");
                    avgSavingsLevel.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgSavings())));
                    levelStats.appendChild(avgSavingsLevel);

                    Element avgTotalL0 = doc.createElement("avgL0Level");
                    avgTotalL0.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgL0())));
                    levelStats.appendChild(avgTotalL0);

                    Element avgTotalL1 = doc.createElement("avgL1Level");
                    avgTotalL1.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgL1())));
                    levelStats.appendChild(avgTotalL1);

                    Element avgTotalL2 = doc.createElement("avgL2Level");
                    avgTotalL2.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgL2())));
                    levelStats.appendChild(avgTotalL2);

                    Element avgTotalL3 = doc.createElement("avgL3Level");
                    avgTotalL3.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgL3())));
                    levelStats.appendChild(avgTotalL3);

                    Element avgLostL0Level = doc.createElement("avgLostL0Level");
                    avgLostL0Level.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgLostL0())));
                    levelStats.appendChild(avgLostL0Level);

                    Element avgLostL1Level = doc.createElement("avgLostL1Level");
                    avgLostL1Level.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgLostL1())));
                    levelStats.appendChild(avgLostL1Level);

                    Element avgLostL2Level = doc.createElement("avgLostL2Level");
                    avgLostL2Level.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgLostL2())));
                    levelStats.appendChild(avgLostL2Level);

                    Element avgLostL3Level = doc.createElement("avgLostL3Level");
                    avgLostL3Level.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgLostL3())));
                    levelStats.appendChild(avgLostL3Level);

                    Element maxLostL0Level = doc.createElement("maxLostL0Level");
                    maxLostL0Level.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMaxLostL0())));
                    levelStats.appendChild(maxLostL0Level);

                    Element maxLostL1Level = doc.createElement("maxLostL1Level");
                    maxLostL1Level.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMaxLostL1())));
                    levelStats.appendChild(maxLostL1Level);

                    Element maxLostL2Level = doc.createElement("maxLostL2Level");
                    maxLostL2Level.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMaxLostL2())));
                    levelStats.appendChild(maxLostL2Level);

                    Element maxLostL3Level = doc.createElement("maxLostL3Level");
                    maxLostL3Level.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMaxLostL3())));
                    levelStats.appendChild(maxLostL3Level);

                    Element maxL0Level = doc.createElement("maxL0Level");
                    maxL0Level.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMaxL0())));
                    levelStats.appendChild(maxL0Level);

                    Element maxL1Level = doc.createElement("maxL1Level");
                    maxL1Level.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMaxL1())));
                    levelStats.appendChild(maxL1Level);

                    Element maxL2Level = doc.createElement("maxL2Level");
                    maxL2Level.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMaxL2())));
                    levelStats.appendChild(maxL2Level);

                    Element maxL3Level = doc.createElement("maxL3Level");
                    maxL3Level.appendChild(doc.createTextNode(String.valueOf(levelStatis.getMaxL3())));
                    levelStats.appendChild(maxL3Level);

                    Element avgLeftL0 = doc.createElement("avgLeftL0Level");
                    avgLeftL0.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgLeftL0())));
                    levelStats.appendChild(avgLeftL0);

                    Element avgLeftL1 = doc.createElement("avgLeftL1Level");
                    avgLeftL1.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgLeftL1())));
                    levelStats.appendChild(avgLeftL1);

                    Element avgLeftL2 = doc.createElement("avgLeftL2Level");
                    avgLeftL2.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgLeftL2())));
                    levelStats.appendChild(avgLeftL2);

                    Element avgLeftL3 = doc.createElement("avgLeftL3Level");
                    avgLeftL3.appendChild(doc.createTextNode(String.valueOf(levelStatis.getAvgLeftL3())));
                    levelStats.appendChild(avgLeftL3);

                    player.appendChild(levelStats);

                }
            }

            // convert the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("statsPlayers4.xml"));

            transformer.transform(source, result);


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