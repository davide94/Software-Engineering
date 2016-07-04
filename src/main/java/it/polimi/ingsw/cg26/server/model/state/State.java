package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public abstract class State {

	/**
	 * The timeout of each turn
	 */
    private static final int TURN_TIMEOUT = 5 * 60 * 1000;
    
    /**
     * The game board
     */
    protected GameBoard gameBoard;

    /**
     * The timer
     */
    protected Timer timer;

    /**
     * the Constructor of the state machine of the game 
     * @param gameBoard is the game board
     */
    public State(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        timer = new Timer();
    }
    
    /**
     * Get the current player
     * @return null
     */
    public Player getCurrentPlayer() {
        return null;
    }

    /**
     * 
     * @param players is the list of players
     * @return the state startMatch
     */
    public State startMatch(List<Player> players) {
        return this;
    }

    /**
     * check if a player can perform a regular action
     * @param token of the player 
     * @return false 
     */
    public boolean canPerformRegularAction(long token) {
        return false;
    }
    
    /**
     * check if a player can sell an item
     * @param token of the player
     * @return false
     */
    public boolean canSell(long token) {
        return false;
    }

    /**
     * check if a player can buy an item
     * @param token is the token of the player
     * @return false
     */
    public boolean canBuy(long token) {
        return false;
    }

    /**
     * 
     * @return the state regularActionPerformed
     */
    public State regularActionPerformed() {
        return this;
    }
    
    /**
     * The state when all the players have finished to sell
     * @return the state sellFolded 
     */
    public State sellFolded() {
        return this;
    }

    /**
     * The state when all the players have finished to buy
     * @return the state buyFolded
     */
    public State buyFolded() {
        return this;
    }

    /**
     * Get the state of the next player
     * @return the state of the next player
     */
    public State nextPlayer() {
        return this;
    }

    /**
     * Start the timer
     */
    public void startTimer() {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameBoard.getScheduler().nextPlayer();
            }
        }, TURN_TIMEOUT);
    }
}