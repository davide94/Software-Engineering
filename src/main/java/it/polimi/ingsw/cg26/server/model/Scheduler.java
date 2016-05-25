package it.polimi.ingsw.cg26.server.model;

import it.polimi.ingsw.cg26.common.state.PlayerState;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Scheduler {

    /**
     * List of players
     */
    private final LinkedList<Player> players;

    /**
     * The current Player
     */
    private Player currentPlayer;

    /**
     * The Game Board
     */
    private final GameBoard gameBoard;

    /**
     * Constructs a Scheduler
     * @param gameBoard is the gameBoard
     * @throws NullPointerException if gameBoard is null
     */
    public Scheduler(GameBoard gameBoard) {
        if (gameBoard == null)
            throw new NullPointerException();
        this.players = new LinkedList<>();
        this.gameBoard = gameBoard;
    }
    
    /**
     * Returns a collection that represents the state of all the players
     * @return the state of all the players
     */
    public List<PlayerState> getPlayersState(){
    	List<PlayerState> playersState = new ArrayList<>();
    	for(Player player : this.players){
    		playersState.add(player.getState());
    	}
    	return playersState;
    }

    /**
     * Returns the current Player
     * @return the current Player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Adds a Player to the list of players
     * @param player is the player that has to be added to the list of players
     * @throws NullPointerException if player is null
     */
    public void registerPlayer(Player player) {
        if (player == null)
            throw new NullPointerException();
        this.players.add(player);
        if (this.players.size() == 1)
            this.currentPlayer = this.players.poll();
    }

    /**
     * Checks if the player can perform more actions and if not, the turn passes tu the next player.
     * Also checks if someone won.
     */
    public void actionPerformed() {
        if (currentPlayer.canPerformMainAction() || currentPlayer.canPerformQuickAction())
            return;
        // TODO controlli su terminazione partita
        players.add(currentPlayer);
        currentPlayer = players.poll();
    }
}
