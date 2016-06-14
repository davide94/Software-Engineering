package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

/**
 *
 */
public class MatchNotStarted extends State {

    public MatchNotStarted(GameBoard gameBoard) {
        super(gameBoard);
    }

    @Override
    public State startMatch(List<Player> players) {
        return new Regular(players, gameBoard);
    }
}
