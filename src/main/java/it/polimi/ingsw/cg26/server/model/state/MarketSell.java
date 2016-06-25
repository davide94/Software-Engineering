package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.event.*;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 */
public class MarketSell extends State {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    
    /**
     * The list of players
     */
    private List<Player> players;

    
    /**
     * The number of the current player
     */
    private int current;

    
    /**
     * Default constructor
     * @param players is the list of players
     * @param gameBoard is the game board
     */
    public MarketSell(List<Player> players, GameBoard gameBoard) {
        super(gameBoard);
        this.players = players;
        current = -1;
        gameBoard.notifyObservers(new MarketStarted());
        log.info("Market Sell started.");
        nextPlayer();
    }

    @Override
    public Player getCurrentPlayer() {
        return players.get(current);
    }

    @Override
    public boolean canSell(long token) {
        return getCurrentPlayer().getToken() == token;
    }

    @Override
    public State sellFolded() {
        gameBoard.notifyObservers(new PrivateUpdate(new SellTurnEnded(), getCurrentPlayer().getToken()));

        return nextPlayer();
    }

    @Override
    public State nextPlayer() {
        current++;
        if (current == players.size())
            return new MarketBuy(players, gameBoard);
        if (!getCurrentPlayer().isOnline())
            return nextPlayer();
        log.info("Player " + getCurrentPlayer().getToken() + " begin his Sell turn.");
        startTimer();
        gameBoard.notifyObservers(new PrivateUpdate(new SellTurnStarted(), getCurrentPlayer().getToken()));
        return this;
    }
}
