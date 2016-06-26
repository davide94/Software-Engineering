package it.polimi.ingsw.cg26.server.model;

import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.server.model.state.MatchNotStarted;
import it.polimi.ingsw.cg26.server.model.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Scheduler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Number of politic cards when the match starts
     */
    private static final int INITIAL_CARDS_NUMBER = 6;

    /**
     * List of players
     */
    private final List<Player> players;

    
    /**
     * The state of the match
     */
    private State state;

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
        this.gameBoard = gameBoard;
        state = new MatchNotStarted(gameBoard);
    }
    
    /**
     * Get the numbers of players
     * @return numbers of players
     */
    public int playersNumber() {
        return players.size();
    }

    /* ---------- ONLY FOR TESTING ---------- */
    /**
     * Get the list of players
     * @return the list of players
     */
    public List<Player> getPlayers() {
        return players;
    }
    /* -------------------------------------- */

    /**
     * Returns a collection that represents the dto of all the players
     *
     * @return the dto of all the players
     */
    public List<PlayerDTO> getPlayersState() {
        List<PlayerDTO> playersState = new ArrayList<>();
        for (Player player : players)
            playersState.add(player.getState());
        return playersState;
    }

    /**
     * Returns a collection that represents the dto of all the players
     *
     * @return the dto of all the players
     */
    public List<PlayerDTO> getPlayersFullState() {
        List<PlayerDTO> playersState = new ArrayList<>();
        for (Player player : players)
            playersState.add(player.getFullState());
        return playersState;
    }

    /**
     * Returns the current Player
     *
     * @return the current Player
     */
    public Player getCurrentPlayer() {
        return state.getCurrentPlayer();
    }

    /**
     * Adds a Player to the list of players
     * @throws NoRemainingCardsException
     */
    public Player registerPlayer(String name) throws NoRemainingCardsException {
        Player player = newPlayer(name);
        players.add(player);
        //buyTurn.add(player);
        return player;
    }
    
    /**
     * Create a new player
     * @param name of the new player
     * @return the new player
     * @throws NoRemainingCardsException if there aren't enough politic cards in the deck
     */
    private Player newPlayer(String name) throws NoRemainingCardsException {
        if (name.equals(""))
            name = "Player_" + players.size();
        long token = new BigInteger(64, new SecureRandom()).longValue();
        return new Player(token, name, gameBoard.getNobilityTrack().getFirstCell(), 10 + playersNumber(), new LinkedList<>(), new LinkedList<>());
    }
    
    /**
     * Start the match and change state
     * @throws NoRemainingCardsException if there aren't enough politic cards in the deck
     */
    public void start() throws NoRemainingCardsException {
        initPlayers();
        state = state.startMatch(new LinkedList<>(players));
    }

    /**
     * Add all the items to the players to start the match
     * @throws NoRemainingCardsException if there aren't enough politic cards in the deck
     */
    public void initPlayers() throws NoRemainingCardsException {

        for (int i = 0; i < playersNumber(); i++) {
            Player p = players.get(i);
            int delta = 10 + i - p.getCoinsNumber();
            if (delta > 0)
                p.addCoins(delta);
            else {
                try {
                    p.removeCoins(-delta);
                } catch (NotEnoughMoneyException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 0; j < i + 1; j++)
                p.addAssistant(new Assistant());
            for (int j = 0; j < INITIAL_CARDS_NUMBER; j++)
                p.addPoliticCard(gameBoard.getPoliticDeck().draw());
        }
    }

    /**
     * Remove a player from the match 
     * @param token is the token of the player that has to be removed
     */
    public void killPlayer(long token) {
        Player toBeKilled = null;
        for (Player p: players)
            if (p.getToken() == token)
                toBeKilled = p;
        if (toBeKilled != null) {
            players.remove(toBeKilled);
            //buyTurn.remove(toBeKilled);
        }
        log.info("Player " + token + " killed.");
    }

    /**
     * Set Offline the state of a player
     * @param token is the token of the player
     */
    public void deactivatePlayer(long token) {
        for (Player p: players)
            if (p.getToken() == token)
                p.setOnline(false);
    }

    /**
     * Check if a player can perform a Regular Action
     * @param token is the token of the player
     * @return true if the state of the player says that he can perform it
     */
    public boolean canPerformRegularAction(long token) {
        return state.canPerformRegularAction(token);
    }

    /**
     * Check if a player can sell an item
     * @param token is the token of the player
     * @return true if the state of the player says that he can sell, otherwise false
     */
    public boolean canSell(long token) {
        return state.canSell(token);
    }
    
    /**
     * Check if a player can buy an item
     * @param token is the token of the player
     * @return true if the state of the player says that he can buy, otherwise false
     */
    public boolean canBuy(long token) {
        return state.canBuy(token);
    }

    /**
     * Set the state of the match as regular action performed
     */
    public void regularActionPerformed() {
        state = state.regularActionPerformed();
    }

    /**
     * Set the state of the match as fold sell
     */
    public void foldSell() {
        state = state.sellFolded();
    }
    
    /**
     * Set the state of the match as fold buy
     */
    public void foldedBuy() {
        state = state.buyFolded();
    }

    /**
     * Setter of the state
     * @param state of the game
     */
    public void setState(State state) {
        this.state = state;
    }
}
