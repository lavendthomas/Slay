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

import be.ac.umons.slay.g02.gui.screens.Menu;


public class StatsLoader {


    public StatsLoader() {

    }

    public ArrayList createTab() {

        ArrayList tabScore = new ArrayList();
        HumanPlayer playerHuman = new HumanPlayer("", Colors.C1);
        GlobalStats globalStats = new GlobalStats();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();

            final Document document = builder.parse(new File("statsPlayers2.xml"));

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
                    LevelStats levelStats;
                    Element plys = (Element) n;
                    globalStats.setRank(Integer.parseInt(plys.getAttribute("rank")));

                    for (int j = 0; j < plys.getChildNodes().getLength(); j++) {
                        Element plys2 = (Element) plys.getChildNodes().item(j);

                        if (plys2.getNodeName().equals("name")) {
                            playerHuman.setName(plys2.getFirstChild().getNodeValue());
                            account.setUsername(plys2.getFirstChild().getNodeValue());
                        }
                        if (plys2.getNodeName().equals("password")) {
                            account.setPassword(decrypt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avatar")) {
                            playerHuman.setAvatar(plys2.getFirstChild().getNodeValue());
                        }
                        if (plys2.getNodeName().equals("score")) {
                            globalStats.setScore(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("totalGames")) {
                            globalStats.setTotalGames(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("totalWins")) {
                            globalStats.setTotalWins(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("totalDefeats")) {
                            globalStats.setTotalDefeats(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgTurns")) {
                            globalStats.setAvgTurns(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("minTurns")) {
                            globalStats.setMinTurns(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgLandsTurn")) {
                            globalStats.setAvgLandsTurn(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("maxLandsTurn")) {
                            globalStats.setMaxLandsTurn(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgTrees")) {
                            globalStats.setAvgTrees(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("maxTrees")) {
                            globalStats.setMaxTrees(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgTotalMoney")) {
                            globalStats.setAvgTotalMoney(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("maxTotalMoney")) {
                            globalStats.setMaxTotalMoney(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("maxUnits")) {
                            globalStats.setMaxUnits(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgUnits")) {
                            globalStats.setAvgUnits(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgLeftUnits")) {
                            globalStats.setAvgLeftUnits(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgLostUnits")) {
                            globalStats.setAvgLostUnits(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("maxArmy")) {
                            globalStats.setMaxArmy(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgArmy")) {
                            globalStats.setAvgArmy(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("minArmy")) {
                            globalStats.setMinArmy(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("totalSavings")) {
                            globalStats.setTotalSavings(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgSavings")) {
                            globalStats.setAvgSavings(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgL0")) {
                            globalStats.setAvgL0(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgL1")) {
                            globalStats.setAvgL1(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgL2")) {
                            globalStats.setAvgL2(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgL3")) {
                            globalStats.setAvgL3(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }

                        if (plys2.getNodeName().equals("avgLeftL0")) {
                            globalStats.setAvgLeftL0(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgLeftL1")) {
                            globalStats.setAvgLeftL1(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgLeftL2")) {
                            globalStats.setAvgLeftL2(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgLeftL3")) {
                            globalStats.setAvgLeftL3(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgLostL0")) {
                            globalStats.setAvgLostL0(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgLostL1")) {
                            globalStats.setAvgLostL1(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgLostL2")) {
                            globalStats.setAvgLostL2(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("avgLostL3")) {
                            globalStats.setAvgLostL3(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("maxLostL0")) {
                            globalStats.setMaxLostL0(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("maxLostL1")) {
                            globalStats.setMaxLostL1(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("maxLostL2")) {
                            globalStats.setMaxLostL2(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("maxLostL3")) {
                            globalStats.setMaxLostL3(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("maxL0")) {
                            globalStats.setMaxL0(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("maxL1")) {
                            globalStats.setMaxL1(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("maxL2")) {
                            globalStats.setMaxL2(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }
                        if (plys2.getNodeName().equals("maxL3")) {
                            globalStats.setMaxL3(Integer.parseInt(plys2.getFirstChild().getNodeValue()));
                        }

                        if (plys2.getNodeName().equals("levelStats")) {

                            levelStats = new LevelStats();
                            levelStats.setLevel(levelLand);
                            levelLand++;
                            for (int k = 0; k < plys2.getChildNodes().getLength(); k++) {

                                Element plys3 = (Element) plys2.getChildNodes().item(k);


                                if (plys3.getNodeName().equals("totalGamesLevel")) {
                                    levelStats.setTotalGames(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("totalWinsLevel")) {
                                    levelStats.setTotalWins(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("totalDefeatsLevel")) {
                                    levelStats.setTotalDefeats(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgTurnsLevel")) {
                                    levelStats.setAvgTurns(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("minTurnsLevel")) {
                                    levelStats.setMinTurns(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgLandsTurnLevel")) {
                                    levelStats.setAvgLandsTurn(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("maxLandsTurnLevel")) {
                                    levelStats.setMaxLandsTurn(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgTreesLevel")) {
                                    levelStats.setAvgTrees(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("maxTreesLevel")) {
                                    levelStats.setMaxTrees(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgTotalMoneyLevel")) {
                                    levelStats.setAvgTotalMoney(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("maxTotalMoneyLevel")) {
                                    levelStats.setMaxTotalMoney(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgLossesLevel")) {
                                    levelStats.setAvgLostUnits(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("maxUnitsLevel")) {
                                    levelStats.setMaxUnits(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgUnitsLevel")) {
                                    levelStats.setAvgUnits(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgLeftUnitsLevel")) {
                                    levelStats.setAvgLeftUnits(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgArmyLevel")) {
                                    levelStats.setAvgArmy(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("minArmyLevel")) {
                                    levelStats.setMinArmy(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("maxArmyLevel")) {
                                    levelStats.setMaxArmy(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("totalSavingsLevel")) {
                                    levelStats.setTotalSavings(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgSavingsLevel")) {
                                    levelStats.setAvgSavings(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgL0Level")) {
                                    levelStats.setAvgL0(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgL1Level")) {
                                    levelStats.setAvgL1(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgL2Level")) {
                                    levelStats.setAvgL2(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgL3Level")) {
                                    levelStats.setAvgL3(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgLeftL0Level")) {
                                    levelStats.setAvgLeftL0(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgLeftL1Level")) {
                                    levelStats.setAvgLeftL1(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgLeftL2Level")) {
                                    levelStats.setAvgLeftL2(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgLeftL3Level")) {
                                    levelStats.setAvgLeftL3(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgLostL0Level")) {
                                    levelStats.setAvgLostL0(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgLostL1Level")) {
                                    levelStats.setAvgLostL1(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgLostL2Level")) {
                                    levelStats.setAvgLostL2(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("avgLostL3Level")) {
                                    levelStats.setAvgLostL3(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("maxLostL0Level")) {
                                    levelStats.setMaxLostL0(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("maxLostL1Level")) {
                                    levelStats.setMaxLostL1(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("maxLostL2Level")) {
                                    levelStats.setMaxLostL2(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("maxLostL3Level")) {
                                    levelStats.setMaxLostL3(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("maxL0Level")) {
                                    levelStats.setMaxL0(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("maxL1Level")) {
                                    levelStats.setMaxL1(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("maxL2Level")) {
                                    levelStats.setMaxL2(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                                if (plys3.getNodeName().equals("maxL3Level")) {
                                    levelStats.setMaxL3(Integer.parseInt(plys3.getFirstChild().getNodeValue()));
                                }
                            }
                            listStatsLevel.add(levelStats);
                        }
                    }
                    playerHuman.setAccount(account);
                    playerHuman.setGlobalStats(globalStats);
                    playerHuman.setListLevelStats(listStatsLevel);
                    Menu.totalNumberPlayers++;
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
        return tabScore;
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