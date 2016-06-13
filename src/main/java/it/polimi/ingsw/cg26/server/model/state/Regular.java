package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.LocalPlayerChange;
import it.polimi.ingsw.cg26.common.update.event.RegularGameStarted;
import it.polimi.ingsw.cg26.common.update.event.RegulaTurnEnded;
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
public class Regular implements State {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private GameBoard gameBoard;

    private List<Player> players;

    private int current;

    public Regular(List<Player> players, GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.players = players;
        current = -1;
        gameBoard.notifyObservers(new RegularGameStarted());
        nextPlayer();
    }

    @Override
    public Player getCurrentPlayer() {
        return players.get(current);
    }

    @Override
    public boolean canPerformRegularAction(long token) {
        return players.get(current).getToken() == token && (getCurrentPlayer().canPerformMainAction() || getCurrentPlayer().canPerformQuickAction());
    }

    @Override
    public State regularActionPerformed() {
        if (getCurrentPlayer().canPerformMainAction() || getCurrentPlayer().canPerformQuickAction())
            return this;

        gameBoard.notifyObservers(new PrivateUpdate(new RegulaTurnEnded(), getCurrentPlayer().getToken()));
        return nextPlayer();
    }

    private State nextPlayer() {
        current++;
        if (current == players.size())
            return new MarketSell(players, gameBoard);
        if (!getCurrentPlayer().isOnline())
            return nextPlayer();

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

    @Override
    public State tenEmporiumsBuilt() {
        return new LastRound(players, current, gameBoard);
    }
}
