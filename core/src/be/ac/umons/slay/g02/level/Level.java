package be.ac.umons.slay.g02.level;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import be.ac.umons.slay.g02.entities.Entity;
import be.ac.umons.slay.g02.entities.Soldier;
import be.ac.umons.slay.g02.entities.SoldierLevel;
import be.ac.umons.slay.g02.entities.StaticEntity;
import be.ac.umons.slay.g02.gui.screens.LevelSelection;
import be.ac.umons.slay.g02.players.HumanPlayer;
import be.ac.umons.slay.g02.players.Player;
import be.ac.umons.slay.g02.players.Statistics;

import static be.ac.umons.slay.g02.gui.Main.prefs;
import static be.ac.umons.slay.g02.gui.screens.Menu.player1;
import static be.ac.umons.slay.g02.gui.screens.Menu.player2;

public class Level implements Playable {

    private Tile[][] tileMap;

    /**
     * Width of the level
     */
    private int width;

    /**
     * Height of the level
     */
    private int height;

    /**
     * List of players in this level
     */
    private static Player[] players;

    /**
     * Current turn
     */
    private int turn = 0;

    /**
     * Current player
     */
    private Player currentPlayer;

    /**
     * Creates an empty level
     *
     * @param x the width of the level
     * @param y the height of the level
     */
    Level(int x, int y) {
        tileMap = new Tile[x][y];
        width = x;
        height = y;
    }

    /**
     * Gives the list of players in this level
     *
     * @return Tab of players
     */
    public static Player[] getPlayers() {
        return players;
    }

    /**
     * Loads players, initializes the turn and the current player
     *
     * @param players Tab of players
     */
    void setPlayers(Player[] players) {
        this.players = players;
        turn = 0;
        currentPlayer = players[turn];
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gives the maximum width of the level
     *
     * @return
     */
    @Override
    public int width() {
        return width;
    }

    /**
     * Gives the maximum height of the level
     *
     * @return
     */
    @Override
    public int height() {
        return height;
    }

    /**
     * Places the tile in parameter in the mentioned coordinates
     *
     * @param tile   The tile to place
     * @param coords The coordiantes to place the tile to
     */
    void set(Tile tile, Coordinate coords) {
        tileMap[coords.getX()][coords.getY()] = tile;
    }

    /**
     * Changes the entity of the tile at the mentioned coordinates
     *
     * @param entity the entity to place
     * @param coords the coordinates of the tile to place the entity on.
     */
    public void set(Entity entity, Coordinate coords) {
        tileMap[coords.getX()][coords.getY()].setEntity(entity);
    }

    /**
     * Returns the tile at the mentioned coordinates
     *
     * @param x
     * @param y
     * @return
     */
    @Override
    public Tile get(int x, int y) {
        return tileMap[x][y];
    }

    /**
     * Returns the tile at the mentioned coordinates
     *
     * @param coords the coordinates for which we want the tile
     * @return a Tile
     */
    @Override
    public Tile get(Coordinate coords) {
        return tileMap[coords.getX()][coords.getY()];
    }

    /**
     * Buys an entity
     *
     * @param entity the new entity to place
     * @param start  the origin Tile
     * @param to     the tile in which to place the entity
     * @return
     */

    @Override
    public boolean buy(Entity entity, Coordinate start, Coordinate to) {
        if (getMoves(entity, start).contains(to)) {
            boolean isBought = get(start).buy(entity, get(to));

            // When a soldier is bought, updates for stats : currentL. and currentMoneySpent
            if (isBought) {
                if (prefs.getBoolean("isPlayer1Logged") && currentPlayer.getName().equals(player1.getName()))
                    updatePlayerStatsBuy(player1, entity);

                else if (prefs.getBoolean("isPlayer2Logged") && currentPlayer.getName().equals(player2.getName()))
                    updatePlayerStatsBuy(player2, entity);
            }
            return isBought;
        }
        return false;
    }

    @Override
    public boolean nextTurn() {
        // If there is no winner
        if (hasWon() == null) {

            List<Territory> processed = new LinkedList<Territory>();

            turn = (turn + 1) % players.length;
            currentPlayer = players[turn];

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Coordinate c = new Coordinate(i, j);
                    Tile t = get(i, j);
                    Territory terr = t.getTerritory();

                    if (terr != null && !processed.contains(terr)) {
                        // Adds funds and kills soldier for all territories
                        t.getTerritory().nextTurn();
                        processed.add(terr);
                    }
                    // Spawns trees
                    if (canSpawnTree(c)) {
                        t.setEntity(StaticEntity.TREE);
                    }
                    if (t.getTerritory() != null && t.getTerritory().getOwner().equals(currentPlayer)) {
                        if (t.getEntity() != null && t.getEntity() instanceof Soldier) {
                            ((Soldier) t.getEntity()).setMoved(false);
                        }
                    }
                }
            }
            // Updates currentTurns for stats - when the player plays a new turn
            if (turn == 0) {
                if (prefs.getBoolean("isPlayer1Logged") && currentPlayer.getName().equals(player1.getName()))
                    updatePlayerStatsTurns(player1);

                else if (prefs.getBoolean("isPlayer2Logged") && currentPlayer.getName().equals(player2.getName()))
                    updatePlayerStatsTurns(player2);
            }

            return true;
        }
        // There is a winner
        else
            return false;
    }

    /**
     * Returns true if a tree should be placed at the mentioned coordinate
     *
     * @param c
     * @return true if a tree shpuld be placed
     */
    private boolean canSpawnTree(Coordinate c) {
        Tile cell = get(c);
        if (cell.getEntity() != null || cell.getType() == TileType.WATER) {
            return false;
        }
        int treeCount = 0;
        for (Coordinate nbr : c.getNeighbors()) {
            if (isInLevel(nbr) && get(nbr).getEntity() == StaticEntity.TREE) {
                treeCount++;
            }
        }
        double odds = 0.01 + ((treeCount * Math.log10(treeCount + 1)) / 10);
        double random = new Random().nextDouble();

        return random < odds && turn == 0;
    }

    /**
     * Uses to load positions in which to place a soldier freshly bought
     * Used to buy a soldier
     *
     * @param entity the new entity to place
     * @param start  the origin tile
     * @return Coordinates list
     */
    @Override
    public List<Coordinate> getMoves(Entity entity, Coordinate start) {
        /*
         *	Loads the list of cells in the duplicated territory (because it must be browsed)
         *	and adds the elements in the list at the same time
         */
        List<Coordinate> listTerr = neighbourTilesInSameTerritory(start);
        List<Coordinate> visited = new ArrayList<Coordinate>();

        for (Coordinate terr : listTerr) {
            // For all cells already in the list

            if (get(terr).getEntity() != null && get(terr).getEntity() instanceof Soldier) {
                // Places on another soldier
                int toLvl = ((Soldier) get(terr).getEntity()).getSoldierLevel().getLevel();
                int fromLvl = ((Soldier) entity).getSoldierLevel().getLevel();

                // Checks if the soldiers can merge
                if (toLvl == fromLvl && toLvl + fromLvl < 3) {
                    visited.add(terr);
                }
            } else {
                visited.add(terr);
            }

            for (Coordinate neighbour : terr.getNeighbors()) {
                // Goes through all neighbours
                Tile neigh = get(neighbour);

                if (neigh.getType() == TileType.NEUTRAL // Water is excluded
                        && !visited.contains(neighbour) // Not in the list of results yet
                        && !neigh.hasSameOwner(get(start))) { // Adds only the cells at the border of the territory

                    if (neigh.getEntity() != null) {
                        // An entity is in the cell
                        if (neigh.getEntity() instanceof Soldier) {
                            // Enemy soldier
                            if (((Soldier) entity).canAttack((Soldier) neigh.getEntity())) {
                                // Can attack
                                visited.add(neighbour);
                            }
                        }
                        if (neigh.getEntity() == StaticEntity.TREE
                                // Tree
                                || (neigh.getEntity() == StaticEntity.CAPITAL && ((Soldier) entity).getSoldierLevel().getLevel() > 0)) {

                            // Attacks a capital
                            visited.add(neighbour);
                        }
                    } else {
                        visited.add(neighbour);
                    }
                }
            }
        }
        return visited;
    }

    /**
     * Returns the list of coordinates that can be reached
     * from the start coordinate limited to n steps
     * Used to move a soldier
     *
     * @param start the starting coordinate
     * @param n     the maximum number of steps
     * @return List of coordinates that can be reached
     */
    @Override
    public List<Coordinate> getMoves(Coordinate start, int n) {
        ArrayList<Coordinate> visited = new ArrayList<Coordinate>();
        visited.add(start);
        List<ArrayList<Coordinate>> fringes = new ArrayList<ArrayList<Coordinate>>();
        ArrayList<Coordinate> startArray = new ArrayList<Coordinate>(1);
        startArray.add(start);
        fringes.add(startArray);

        for (int k = 1; k <= n; k++) {
            fringes.add(new ArrayList<Coordinate>());
            for (Coordinate c : fringes.get(k - 1)) {
                for (Coordinate neighbour : c.getNeighbors()) {
                    if (!visited.contains(neighbour) && canMove(start, neighbour)) {
                        // Not already visited and can move to

                        if (!get(neighbour).hasSameOwner(get(start))) {
                            // Not same owner so stop in this direction

                            visited.add(neighbour);
                        } else {
                            // Same owner so continue in this direction

                            visited.add(neighbour);
                            fringes.get(k).add(neighbour);
                        }
                    }
                }
            }
        }
        return visited;
    }

    /**
     * Returns a list of all purchasable entities in the territory at the mentionned coordinate
     *
     * @param p the coordinate where we want to buy the Entity
     * @return List of entities we can buy
     */
    @Override
    public List<Entity> canBuy(Coordinate p) {
        if (get(p).getTerritory() == null) {
            return null;
        }
        List<Entity> canBuyE = new ArrayList<Entity>();
        for (SoldierLevel lvl : SoldierLevel.values()) {
            Soldier s = new Soldier(lvl);
            if (get(p).getTerritory().canBuy(s)) {
                canBuyE.add(s);
            }
        }
        return canBuyE;
    }

    /**
     * Returns if it is possible to go from the starting coordinates
     * to the arrival coordinates
     *
     * @param fromC the starting coordinate
     * @param toC   the arrival coordinate
     * @return True if it is possible, false otherwise
     */
    private boolean canMove(Coordinate fromC, Coordinate toC) {
        if (fromC.equals(toC) || !get(fromC).getTerritory().getOwner().equals(currentPlayer)) {
            // Prevents movement on the starting cell
            return false;
        }
        // Gets the matching tiles
        Tile from = get(fromC);
        Tile to = get(toC);

        if (to.getType().equals(TileType.NEUTRAL) && from.getEntity() instanceof Soldier && !(((Soldier) from.getEntity()).getMoved())) {
            //Prevents movement in the water, verifies that you are trying to move a soldier and that no entity has already moved

            if (to.getTerritory() == null) {
                // Moving to a cell that does not belong to anyone
                return true;
            } else if (to.hasSameOwner(from)) {
                // Moving in the same territory

                if (to.getEntity() != null && to.getEntity() instanceof Soldier) {
                    // Moving on another soldier

                    int toLvl = ((Soldier) to.getEntity()).getSoldierLevel().getLevel();
                    int fromLvl = ((Soldier) from.getEntity()).getSoldierLevel().getLevel();

                    // Checks if the soldiers can merge
                    return toLvl == fromLvl && toLvl + fromLvl < 3;
                }
                return true;
            } else {
                // Moving in another territory

                if (to.getEntity() instanceof Soldier) {
                    // The arrival cell contains a soldier so check if it can be attacked

                    return ((Soldier) from.getEntity()).canAttack((Soldier) to.getEntity());
                } else if (to.getEntity() == StaticEntity.CAPITAL) {
                    // The arrival cell contains a capital so check if we can attack it

                    return ((Soldier) from.getEntity()).getSoldierLevel().getLevel() > 0;
                } else {
                    // Checks if a soldier is watching the arrival cell

                    // Gets the immediate neighbour from the arrival cell
                    Coordinate[] neighbors = toC.getNeighbors();
                    boolean isFound = true;

                    for (Coordinate coordinate : neighbors) {
                        // Checks all neighbours
                        Tile current = get(coordinate);

                        if (current.getType().equals(TileType.NEUTRAL)
                                && current.getEntity() != null) {
                            // To not check in the water and avoid nullPointerException

                            if (current.getEntity() instanceof Soldier
                                    && to.hasSameOwner(current)) {
                                /*
                                 *	If a soldier has been found and belongs to the same
                                 *  owner as the arrival cell's one
                                 */
                                isFound = ((Soldier) from.getEntity()).canAttack(
                                        (Soldier) current.getEntity());

                            }
                        }
                    }
                    return isFound;
                }
            }
        }
        return false;
    }

    /**
     * Moves an entity from the start coordinates to the arrival coordinates
     *
     * @param fromC the start coordinates
     * @param toC   the arrival coordinates
     */
    @Override
    public void move(Coordinate fromC, Coordinate toC) {

        // Loads the list of possible moves
        List<Coordinate> listMoves = getMoves(fromC, 4);

        /*
         *	Checks that the arrival coordinates are in the list of possible moves
         *	and checks that the coordinates are different
         */
        if (listMoves.contains(toC) && !fromC.equals(toC)) {
            Tile to = get(toC);
            Tile from = get(fromC);

            // Prevents movement in water
            if (to.getTerritory() != null) {

                if (to.hasSameOwner(from)) {
                    // Moves in own territory
                    if (to.getEntity() != StaticEntity.CAPITAL) {
                        // Prevents movement on its own capital

                        if (to.getEntity() instanceof Soldier) {
                            // Soldier fusion
                            int fromLvl = ((Soldier) from.getEntity()).getSoldierLevel().getLevel();
                            int toLvl = ((Soldier) to.getEntity()).getSoldierLevel().getLevel();
                            int newLvl = toLvl + fromLvl + 1;
                            // If "to" soldier has already moved (no need to check each other because it can move)
                            to.setEntity(new Soldier(SoldierLevel.fromLevel(newLvl),
                                    ((Soldier) to.getEntity()).getMoved()));

                            // Updates currentL. for stats - when two soldiers merge, they are deleted and another type is added
                            if (prefs.getBoolean("isPlayer1Logged") && currentPlayer.getName().equals(player1.getName()))
                                updatePlayerStatsMerge(player1, fromLvl, toLvl, newLvl);

                            else if (prefs.getBoolean("isPlayer2Logged") && currentPlayer.getName().equals(player2.getName()))
                                updatePlayerStatsMerge(player2, fromLvl, toLvl, newLvl);

                            from.setEntity(null);

                        } else {
                            // Just moves the Entity
                            moveEntity(from, to);
                        }
                    }
                } else {
                    // "to" territory is enemy
                    moveEntity(from, to);
                }
            } else {
                // "to" territory is null
                moveEntity(from, to);
            }
            splitTerritories(); //TODO find a better approach
            mergeTerritories();
        }
    }

    private void moveEntity(Tile from, Tile to) {
        // Updates currentTrees for stats - when the player cuts a tree
        if (to.contains(StaticEntity.TREE)) {
            if (prefs.getBoolean("isPlayer1Logged") && currentPlayer.getName().equals(player1.getName()))
                updatePlayerStatsTrees(player1);

            else if (prefs.getBoolean("isPlayer2Logged") && currentPlayer.getName().equals(player2.getName()))
                updatePlayerStatsTrees(player2);
        }
        if (to.getEntity() != null && to.getEntity() instanceof Soldier) {
            /*
                Updates currentLostL. for stats - when a soldier is killed by the enemy
                The stats are updated for the player who is not the current player
             */
            if (prefs.getBoolean("isPlayer1Logged") && currentPlayer.getName().equals(player2.getName()))
                updatePlayerStatsLost(player1, to.getEntity());

            else if (prefs.getBoolean("isPlayer2Logged") && currentPlayer.getName().equals(player1.getName()))
                updatePlayerStatsLost(player2, to.getEntity());
        }

        to.setTerritory(from.getTerritory());
        to.setEntity(from.getEntity());
        from.setEntity(null);

        ((Soldier) to.getEntity()).setMoved(true);
    }

    /**
     * Merges the territories of adjacent cells
     */
    public void mergeTerritories() {
        List<Coordinate> processed = new LinkedList<Coordinate>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Coordinate pos = new Coordinate(i, j);
                if (!processed.contains(pos)) {
                    mergeTerritories(pos, processed);
                }
            }
        }
    }

    /**
     * Merges the territories from a specific position
     *
     * @param pos
     * @param processed
     */
    private void mergeTerritories(Coordinate pos, List<Coordinate> processed) {
        // Source: https://codereview.stackexchange.com/questions/90108/recursively-evaluate-neighbors-in-a-two-dimensional-grid

        // Base case: We already checked this cell
        if (!processed.contains(pos)) {
            processed.add(pos);

            int x = pos.getX();
            int y = pos.getY();

            Coordinate[] neighbors = pos.getNeighbors();

            for (Coordinate nb : neighbors) {
                if (isInLevel(nb)) {
                    if (tileMap[x][y].mergeTerritories(tileMap[nb.getX()][nb.getY()])) {
                        mergeTerritories(nb, processed);
                    }
                }
            }
        }
    }

    public void splitTerritories() {
        /*
         *	Idea: We start from one cell and we keep in this territory all the cells that are
         *	adjacent to this one and in the same territory. All the others are separated in another
         *	territory and continue to split until all the cells in the territory are adjacent.
         */

        List<Territory> processedTerritories = new LinkedList<Territory>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Coordinate pos = new Coordinate(i, j);
                Tile cell = tileMap[i][j];

                if (!cell.hasTerritory())
                    continue;

                if (!processedTerritories.contains(cell.getTerritory())) { // TODO check that this works
                    List<Coordinate> neighbours = neighbourTilesInSameTerritory(pos);
                    List<Tile> tiles = new LinkedList<Tile>();  // List of all tiles in neighbourhood
                    for (Coordinate p : neighbours) {         // in the same territory
                        tiles.add(tileMap[p.getX()][p.getY()]);
                    }
                    // If the list is equal
                    if (tiles.containsAll(cell.getTerritory().getCells())) {
                        // If all the cells in the territory are in the neighbourhood,
                        // we don't have to split anything
                        // TODO useless because being checked after ?
                        continue;
                    } else {
                        // Removes all the cells that are not in the neighbourhood
                        // and creates a new territory for them
                        Territory newTerr = new Territory(cell.getTerritory().getOwner());
                        processedTerritories.add(newTerr);
                        List<Tile> tilesInTerritory = cell.getTerritory().getCells();
                        Tile[] tilesArray = new Tile[tilesInTerritory.size()];
                        tilesInTerritory.toArray(tilesArray);
                        for (Tile t : tilesArray) {
                            if (!tiles.contains(t) && t != null && t.getTerritory() != null) {
                                t.getTerritory().remove(t);
                                t.setTerritory(newTerr);
                            }
                        }
                    }
                    processedTerritories.add(cell.getTerritory());
                }
            }
        }
        if (processedTerritories.size() > 1) {
            for (Territory t : processedTerritories) {
                if (t != null) {
                    t.newCapital();
                }
            }
        }
    }

    /**
     * Lists all cells in a neighbourhood, which represents all adjacent cells
     * that are part of the same territory
     *
     * @param pos the position of one cell of the neighbourhood
     * @return a list of all the cells in the neighbourhood containing the position pos
     */
    @Override
    public List<Coordinate> neighbourTilesInSameTerritory(Coordinate pos) {
        List<Coordinate> neighbours = new LinkedList<Coordinate>();
        Tile cell = get(pos);

        if (cell.getTerritory() == null) {
            return neighbours;
        } else {
            neighbourTilesInSameTerritory(pos, cell.getTerritory(), neighbours);
            return neighbours;
        }
    }

    /**
     * Adds all the neighbours in the same territory to the known list
     *
     * @param pos       the current position
     * @param territory the territory of the neighbourhood
     * @param known     all already known cells in the neighbourhood in the same territory
     */
    private void neighbourTilesInSameTerritory(Coordinate pos, Territory territory, List<Coordinate> known) {
        Tile cell = tileMap[pos.getX()][pos.getY()];
        if (cell.getTerritory() == null) {
            return;
        }
        if (known.contains(pos)) {
            return;
        }
        if (!cell.getTerritory().equals(territory)) {
            return;
        }
        known.add(pos);

        for (Coordinate nbr : pos.getNeighbors()) {
            neighbourTilesInSameTerritory(nbr, territory, known);
        }
    }

    public int countTerritories(Player player) {
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Tile tile = tileMap[i][j];
                if (tile.hasTerritory() && tile.getTerritory().getOwner().equals(player)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Ckecks if a player has won the game
     *
     * @return a player is he has won the game, null if no one has
     */
    @Override
    public Player hasWon() {
        List<Player> inGame = new ArrayList<Player>();
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            int n = countTerritories(player);
            if (n > 0) {
                inGame.add(player);
            }
        }
        // If the list only contains one player, it is the winner
        if (inGame.size() == 1) {

            // Updates all stats for winner and loser (if logged)
            Player winner = inGame.get(0);

            if (prefs.getBoolean("isPlayer1Logged") && winner.getName().equals(player1.getName())) {

                // Number of games ++
                updatePlayerGames(player1);

                // Number of wins ++
                updatePlayerWins(player1);

                // All other stats are updated
                updatePlayerScore(player1);

            } else if (prefs.getBoolean("isPlayer2Logged") && winner.getName().equals(player2.getName())) {

                // Number of games ++
                updatePlayerGames(player2);

                // Number of wins ++
                updatePlayerWins(player2);

                // All other stats are updated
                updatePlayerScore(player2);
            }
            Player loser;
            if (winner == players[0])
                loser = players[1];
            else
                loser = players[0];

            if (prefs.getBoolean("isPlayer1Logged") && loser.getName().equals(player1.getName())) {

                // Number of games ++
                updatePlayerGames(player1);

                // Number of defeats ++
                updatePlayerDefeats(player1);

                // All other stats are updated
                updatePlayerScore(player1);

            } else if (prefs.getBoolean("isPlayer2Logged") && loser.getName().equals(player2.getName())) {

                // Number of games ++
                updatePlayerGames(player2);

                // Number of defeats ++
                updatePlayerDefeats(player2);

                // All other stats are updated
                updatePlayerScore(player2);
            }

            return winner;
        }
        // No one has won
        else
            return null;
    }

    /**
     * @param player
     */
    private void updatePlayerStatsTrees(HumanPlayer player) {
        player.getStatistics().incrementStat(player.getStatistics().getCurrentStats(), Statistics.CURRENT_TREES);
    }

    /**
     * @param player
     */
    private void updatePlayerStatsTurns(HumanPlayer player) {
        player.getStatistics().incrementStat(player.getStatistics().getCurrentStats(), Statistics.CURRENT_TURNS);
    }

    /**
     * @param player
     * @param entity
     */
    private void updatePlayerStatsLost(HumanPlayer player, Entity entity) {
        player.getStatistics().incrementStat(player.getStatistics().getCurrentStats(), "currentLost" + entity.getName());
    }

    /**
     * @param player
     * @param entity
     */
    private void updatePlayerStatsBuy(HumanPlayer player, Entity entity) {
        // Updates currentMoneySpent
        player.getStatistics().addToStat(player.getStatistics().getCurrentStats(),
                Statistics.CURRENT_MONEY_SPENT, entity.getPrice());

        // Updates currentL.
        player.getStatistics().incrementStat(player.getStatistics().getCurrentStats(), "current" + entity.getName());
    }

    /**
     * @param player
     * @param fromLvl
     * @param toLvl
     * @param newLvl
     */
    private void updatePlayerStatsMerge(HumanPlayer player, int fromLvl, int toLvl, int newLvl) {
        // Current old unit -= 1 (for the first one)
        player.getStatistics().addToStat(player.getStatistics().getCurrentStats(),
                "current" + SoldierLevel.fromLevel(fromLvl).getName(), -1);

        // Current old unit -= 1 (for the second one)
        player.getStatistics().addToStat(player.getStatistics().getCurrentStats(),
                "current" + SoldierLevel.fromLevel(toLvl).getName(), -1);

        // Current new unit += 1
        player.getStatistics().incrementStat(player.getStatistics().getCurrentStats(),
                "current" + SoldierLevel.fromLevel(newLvl).getName());
    }

    /**
     * on incrémente pour globalstats et le levelstats du niveau joué
     *
     * @param player
     */
    private void updatePlayerGames(HumanPlayer player) {
        player.getGlobalStats().incrementStat(player.getGlobalStats().getStats(), Statistics.GAMES);
        player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).incrementStat
                (player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).getStats(), Statistics.GAMES);
    }

    /**
     * on incrémente pour globalstats et le levelstats du niveau joué
     *
     * @param player
     */
    private void updatePlayerWins(HumanPlayer player) {
        player.getGlobalStats().incrementStat(player.getGlobalStats().getStats(), Statistics.WINS);
        player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).incrementStat
                (player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).getStats(), Statistics.WINS);
    }

    /**
     * on incrémente pour globalstats et le levelstats du niveau joué
     *
     * @param player
     */
    private void updatePlayerDefeats(HumanPlayer player) {
        player.getGlobalStats().incrementStat(player.getGlobalStats().getStats(), Statistics.DEFEATS);
        player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).incrementStat
                (player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).getStats(), Statistics.DEFEATS);
    }

    /**
     * on met à jour pour globalstats et le levelstats du niveau joué puis on calcule le score (pour le niveau joué)
     *
     * @param player
     */
    private void updatePlayerScore(HumanPlayer player) {
        player.getGlobalStats().updateStats();
        player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).updateStats();

        int games = player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).getStats().get(Statistics.GAMES);
        int wins = player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).getStats().get(Statistics.WINS);
        int score = player.getListLevelStats(LevelSelection.getCurrentIslandNumber()).calculateScore(games, wins);

        player.getGlobalStats().setScore(score);
    }

    /**
     * Returns true if the position c is in the level
     *
     * @param c the position to check
     * @return true if the position c is in the level
     */
    @Override
    public boolean isInLevel(Coordinate c) {
        return c.getX() > 0 && c.getX() < width && c.getY() > 0 && c.getY() < height;
    }
}