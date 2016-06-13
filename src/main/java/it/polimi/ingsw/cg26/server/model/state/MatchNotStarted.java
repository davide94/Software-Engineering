package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

/**
 *
 */
public class MatchNotStarted implements State {

    private GameBoard gameBoard;

    public MatchNotStarted(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public Player getCurrentPlayer() {
        return null;
    }

    @Override
    public State startMatch(List<Player> players) {
        return new Regular(players, gameBoard);
    }
}
