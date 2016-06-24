package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.LocalPlayerChange;
import it.polimi.ingsw.cg26.common.update.event.RegularTurnEnded;
import it.polimi.ingsw.cg26.common.update.event.RegularTurnStarted;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 */
public class LastRound extends State {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private List<Player> players;

    private int current;

    private int winner;

    public LastRound(List<Player> players, int winner, GameBoard gameBoard) {
        super(gameBoard);
        this.players = players;
        this.winner = winner;

    }

    @Override
    public Player getCurrentPlayer() {
        return null;
    }

    @Override
    public boolean canPerformRegularAction(long token) {
        return players.get(current).getToken() == token;
    }

    @Override
    public State regularActionPerformed() {
        if (getCurrentPlayer().canPerformMainAction() || getCurrentPlayer().canPerformQuickAction())
            return this;

        gameBoard.notifyObservers(new PrivateUpdate(new RegularTurnEnded(), getCurrentPlayer().getToken()));
        return nextPlayer();
    }

    @Override
    public State nextPlayer() {
        current++;
        if (current == players.size())
            current = 0;
        if (current == winner)
            return new MatchEnded(players, gameBoard);
        if (!getCurrentPlayer().isOnline())
            return nextPlayer();

        startTimer();
        getCurrentPlayer().setRemainingMainActions(1);
        getCurrentPlayer().setRemainingQuickActions(1);
        try {
            getCurrentPlayer().addPoliticCard(gameBoard.getPoliticDeck().draw());
        } catch (NoRemainingCardsException e) {
            log.error("Player can't draw from politic deck", e);
        }
        gameBoard.notifyObservers(new PrivateUpdate(new LocalPlayerChange(new BasicChange(), getCurrentPlayer().getFullState()), getCurrentPlayer().getToken()));
        gameBoard.notifyObservers(new PrivateUpdate(new RegularTurnStarted(), getCurrentPlayer().getToken()));
        return this;
    }
}
