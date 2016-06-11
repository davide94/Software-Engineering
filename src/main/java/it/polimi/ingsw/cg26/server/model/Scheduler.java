package it.polimi.ingsw.cg26.server.model;

import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.event.*;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
public class Scheduler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final int INITIAL_CARDS_NUMBER = 6;

    private static final int TURN_TIMEOUT = 600000;

    /**
     * List of players
     */
    private final List<Player> players;

    private final List<Player> buyTurn;

    /**
     * The position of the current player in the players list
     */
    private int current;

    private int currentInMarket;

    private boolean market;

    private boolean sell;

    private boolean lastTurn;

    private int winner;

    private Timer timer;

    /**
     * The Game Board
     */
    private final GameBoard gameBoard;

    /**
     * Constructs a Scheduler
     *
     * @param gameBoard is the gameBoard
     * @throws NullPointerException if gameBoard is null
     */
    public Scheduler(GameBoard gameBoard) {
        if (gameBoard == null)
            throw new NullPointerException();
        this.players = new LinkedList<>();
        this.buyTurn = new LinkedList<>();
        this.current = 0;
        this.market = false;
        this.lastTurn = false;
        this.sell = false;
        this.winner = 0;
        this.timer = new Timer();
        this.gameBoard = gameBoard;
    }

    public boolean isMarket() {
        return market;
    }

    public int playersNumber() {
        return players.size();
    }

    /* ---------- ONLY FOR TESTING ---------- */

    public List<Player> getPlayers() {
        return players;
    }
    
    public void setMarket(boolean b){
    	this.market = b;
    }

    /* -------------------------------------- */

    /**
     * Returns a collection that represents the dto of all the players
     *
     * @return the dto of all the players
     */
    public List<PlayerDTO> getPlayersState() {
        return this.players.stream().map(Player::getState).collect(Collectors.toList());
    }

    /**
     * Returns a collection that represents the dto of all the players
     *
     * @return the dto of all the players
     */
    public List<PlayerDTO> getPlayersFullState() {
        List<PlayerDTO> playersState = new ArrayList<>();
        playersState.add(getCurrentPlayer().getFullState());
        for (Player player : this.players) {
            playersState.add(player.getFullState());
        }
        return playersState;
    }

    /**
     * Returns the current Player
     *
     * @return the current Player
     */
    public Player getCurrentPlayer() {
        if (players.isEmpty())
            return null;
        if (market) {
            if (sell)
                return players.get(currentInMarket);
            return buyTurn.get(currentInMarket);
        }
        return players.get(current);

    }

    /**
     * Adds a Player to the list of players
     * @throws NoRemainingCardsException
     */
    public long registerPlayer(String name) throws NoRemainingCardsException {
        Player player = null;
        player = newPlayer(name);
        players.add(player);
        buyTurn.add(player);
        return player.getToken();
    }

    private Player newPlayer(String name) throws NoRemainingCardsException {
        if (name.equals(""))
            name = "Player_" + players.size();
        long token = new BigInteger(64, new SecureRandom()).longValue();
        return new Player(token, name, gameBoard.getNobilityTrack().getFirstCell(), 10, new LinkedList<>(), new LinkedList<>());
    }

    public void initPlayers() throws NoRemainingCardsException {

        for (int i = 0; i < playersNumber(); i++) {
            Player p = players.get(i);
            p.addCoins(i);
            for (int j = 0; j < i + 1; j++)
                p.addAssistant(new Assistant());
            for (int j = 0; j < INITIAL_CARDS_NUMBER; j++)
                p.addPoliticCard(gameBoard.getPoliticDeck().draw());
            if (i == 0) {
                p.setRemainingMainActions(1);
                p.setRemainingQuickActions(1);
                p.addPoliticCard(gameBoard.getPoliticDeck().draw());
            }
        }

    }

    public void killPlayer(long token) {
        Player toBeKilled = null;
        for (Player p: players)
            if (p.getToken() == token)
                toBeKilled = p;
        if (toBeKilled != null) {
            players.remove(toBeKilled);
            buyTurn.remove(toBeKilled);
        }
    }

    public void deactivatePlayer(long token) {
        int activePlayers = 0;
        for (Player p: players) {
            if (p.isOnline())
                activePlayers++;
            if (p.getToken() == token)
                p.setOnline(false);
        }
        if (activePlayers < 2)
            endMatch();
    }

    /**
     * Checks if the player can perform more actions and if not, the turn passes tu the next player.
     * Also checks if someone won.
     */
    public void actionPerformed() {
        /*timer.cancel();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextPlayer();
            }
        }, TURN_TIMEOUT);*/

        if (market || getCurrentPlayer().canPerformMainAction() || getCurrentPlayer().canPerformQuickAction())
            return;
        nextPlayer();
    }

    private void nextPlayer() {
        if (!lastTurn) {
            int count = gameBoard.getRegions().stream().mapToInt(r -> (int) r.getCities().stream().filter(
                    c -> (c.hasEmporium(getCurrentPlayer()))).count()).reduce(0, (a, b) -> a + b);
            if (count >= 10) {
                getCurrentPlayer().addVictoryPoints(3);
                lastTurn = true;
                winner = current;
            }
        }

        gameBoard.notifyObservers(new PrivateUpdate(new TurnEnded(), getCurrentPlayer().getToken()));

        current = (current + 1) % playersNumber();
        while (!players.get(current).isOnline())
            current = (current + 1) % playersNumber();

        if (current == players.size())
            current = 0;

        if (lastTurn && current == winner) {
            endMatch();
            return;
        }

        if (!market && current == 0) {
            startMarket();
            return;
        }

        getCurrentPlayer().setRemainingMainActions(1);
        getCurrentPlayer().setRemainingQuickActions(1);
        try {
            getCurrentPlayer().addPoliticCard(gameBoard.getPoliticDeck().draw());
        } catch (NoRemainingCardsException e) {
            log.error("Player can't draw from politic deck", e);
        }

        gameBoard.notifyObservers(new PrivateUpdate(new TurnStarted(), getCurrentPlayer().getToken()));
    }

    private void startMarket() {
        market = true;
        sell = true;
        currentInMarket = 0;

        gameBoard.notifyObservers(new MarketStarted());
        gameBoard.notifyObservers(new PrivateUpdate(new SellTurnStarted(), getCurrentPlayer().getToken()));
    }

    public void foldSell() {
        if (!market || !sell)
            return;
        gameBoard.notifyObservers(new PrivateUpdate(new SellTurnEnded(), getCurrentPlayer().getToken()));

        currentInMarket++;
        if (currentInMarket == players.size()) {
            sell = false;
            currentInMarket = 0;
            Collections.shuffle(buyTurn);

            gameBoard.notifyObservers(new PrivateUpdate(new BuyTurnStarted(), getCurrentPlayer().getToken()));
        } else {
            gameBoard.notifyObservers(new PrivateUpdate(new SellTurnStarted(), getCurrentPlayer().getToken()));
        }
    }

    public void foldBuy() {
        if (!market || sell)
            return;
        gameBoard.notifyObservers(new PrivateUpdate(new BuyTurnEnded(), getCurrentPlayer().getToken()));

        currentInMarket++;
        if (currentInMarket == players.size()) {
            market = false;

            gameBoard.notifyObservers(new MarketEnded());
            gameBoard.notifyObservers(new PrivateUpdate(new TurnStarted(), getCurrentPlayer().getToken()));
        } else {
            gameBoard.notifyObservers(new PrivateUpdate(new BuyTurnStarted(), getCurrentPlayer().getToken()));
        }
    }

    private Comparator<Player> nobilityComparator = (x, y) ->
            x.getNobilityCell().getIndex() < y.getNobilityCell().getIndex() ? -1 :
                    x.getNobilityCell().getIndex() == y.getNobilityCell().getIndex() ? 0 : 1;

    private Comparator<Player> bptComparator = (x, y) ->
            x.getBPTNumber() < y.getBPTNumber() ? -1 :
                    x.getBPTNumber() == y.getBPTNumber() ? 0 : 1;

    private Comparator<Player> victoryComparator = (x, y) ->
            x.getBPTNumber() < y.getBPTNumber() ? -1 :
                    x.getBPTNumber() == y.getBPTNumber() ? 0 : 1;

    private Comparator<Player> assistantsPlusCardsComparator = (x, y) ->
            x.getAssistantsNumber() + x.getPoliticCardsNumber() < y.getAssistantsNumber() + y.getPoliticCardsNumber() ? -1 :
                    x.getAssistantsNumber() + x.getPoliticCardsNumber() == y.getAssistantsNumber() + y.getPoliticCardsNumber() ? 0 : 1;

    private void endMatch() {

        // first nobility -> 5 victory
        // second nobility -> 2 victory (only if the first is unique)
        // first bpt -> 3 (only if unique)

        Collections.sort(players, nobilityComparator);
        if (players.get(0).getNobilityCell().getIndex() != players.get(1).getNobilityCell().getIndex()) {
            players.get(0).addVictoryPoints(5);
            players.stream().skip(1)
                    .filter(p -> p.getNobilityCell().getIndex() == players.get(1).getNobilityCell().getIndex())
                    .forEach(p -> p.addVictoryPoints(2));
        } else {
            players.stream()
                    .filter(p -> p.getNobilityCell().getIndex() == players.get(0).getNobilityCell().getIndex())
                    .forEach(p -> p.addVictoryPoints(5));
        }

        Collections.sort(players, bptComparator);
        if (players.get(0).getBPTNumber() > players.get(2).getBPTNumber())
            players.get(0).addVictoryPoints(3);

        Collections.sort(players, victoryComparator);
        int a = (int)players.stream().filter(p -> p.getVictoryPoints() == players.get(0).getVictoryPoints()).count();
        if (a != 0)
            Collections.sort(players.subList(0, a), assistantsPlusCardsComparator);

        //if (assistantsPlusCardsComparator.compare(players.get(0), players.get(1)) == 0)
            // no one wins :(
        // players.gat(0) is the winner

        gameBoard.notifyObservers(new GameEnded()); // TODO: put inside final ranking
    }
}
