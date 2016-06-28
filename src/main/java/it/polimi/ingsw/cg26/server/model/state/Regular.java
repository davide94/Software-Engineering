package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.LocalPlayerChange;
import it.polimi.ingsw.cg26.common.update.event.RegularGameStarted;
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
public class Regular extends State {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * The list of players
     */
    private List<Player> players;

    /**
     * the number of the current player
     */
    private int current;

    /**
     * Default Constructor
     * @param players the list of players
     * @param gameBoard is the game board
     */
    public Regular(List<Player> players, GameBoard gameBoard) {
        super(gameBoard);
        this.players = players;
        current = -1;
        gameBoard.notifyObservers(new RegularGameStarted());
        log.info("Regular game started.");
        nextPlayer();
    }

    @Override
    public Player getCurrentPlayer() {
        return players.get(current);
    }

    @Override
    public boolean canPerformRegularAction(long token) {
        return getCurrentPlayer().getToken() == token && (getCurrentPlayer().canPerformMainAction() || getCurrentPlayer().canPerformQuickAction());
    }

    @Override
    public State regularActionPerformed() {
        if (getCurrentPlayer().canPerformMainAction() || getCurrentPlayer().canPerformQuickAction()) {

            //check if someone built 10 emporiums
            int count = gameBoard.getRegions().stream().mapToInt(r -> (int) r.getCities().stream().filter(
                    c -> (c.hasEmporium(getCurrentPlayer()))).count()).reduce(0, (a, b) -> a + b);
            if (count >= 10) {
                getCurrentPlayer().addVictoryPoints(3);
                return new LastRound(players, current, gameBoard);
            }
            return this;
        }
        gameBoard.notifyObservers(new PrivateUpdate(new RegularTurnEnded(), getCurrentPlayer().getToken()));
        return nextPlayer();
    }

    @Override
    public State nextPlayer() {
        current++;
        if (current == players.size())
            return new MarketSell(players, gameBoard);

        /*if (players.stream().filter(Player::isOnline).count() < 2)
            return new MatchEnded(players, gameBoard);*/

        if (!getCurrentPlayer().isOnline())
            return nextPlayer();
        log.info("Player " + getCurrentPlayer().getToken() + " begin his regular turn.");
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
