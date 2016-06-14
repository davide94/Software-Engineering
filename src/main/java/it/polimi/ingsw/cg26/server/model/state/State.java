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

    private static final int TURN_TIMEOUT = 5 * 60 * 1000;

    protected GameBoard gameBoard;

    private Timer timer = new Timer();

    private TimerTask toggleNextPlayer;

    public State(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        toggleNextPlayer = new TimerTask() {
            @Override
            public void run() {
                gameBoard.getScheduler().setState(nextPlayer());
            }
        };
    }

    public Player getCurrentPlayer() {
        return null;
    }

    public State startMatch(List<Player> players) {
        return this;
    }

    public boolean canPerformRegularAction(long token) {
        return false;
    }

    public boolean canSell(long token) {
        return false;
    }

    public boolean canBuy(long token) {
        return false;
    }

    public State regularActionPerformed() {
        return this;
    }

    public State sellFolded() {
        return this;
    }

    public State buyFolded() {
        return this;
    }

    public State nextPlayer() {
        return this;
    }

    public void startTimer() {
        toggleNextPlayer.cancel();
        toggleNextPlayer = new TimerTask() {
            @Override
            public void run() {
                gameBoard.getScheduler().setState(nextPlayer());
            }
        };
        timer.schedule(toggleNextPlayer, TURN_TIMEOUT);
    }
}