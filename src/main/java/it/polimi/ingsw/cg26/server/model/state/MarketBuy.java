package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.event.BuyTurnEnded;
import it.polimi.ingsw.cg26.common.update.event.BuyTurnStarted;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class MarketBuy extends State {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * The list of players
     */
    private List<Player> players;

    /**
     * The list of players in a random order for the buy market phase 
     */
    private List<Player> shuffled;

    /**
     * The number of the current player
     */
    private int current;
    
    /**
     * default constructor
     * @param players is the list of players
     * @param gameBoard is the game board
     */
    public MarketBuy(List<Player> players, GameBoard gameBoard) {
        super(gameBoard);
        this.players = players;
        shuffled = new LinkedList<>(players);
        Collections.shuffle(shuffled);
        current = -1;
        log.info("Market Buy started.");
        nextPlayer();
    }

    @Override
    public Player getCurrentPlayer() {
        return shuffled.get(current);
    }

    @Override
    public boolean canBuy(long token) {
        return getCurrentPlayer().getToken() == token;
    }

    @Override
    public State buyFolded() {
        gameBoard.notifyObservers(new PrivateUpdate(new BuyTurnEnded(), getCurrentPlayer().getToken()));

        return nextPlayer();
    }

    @Override
    public State nextPlayer() {
        current++;
        if (current == players.size())
            return new Regular(players, gameBoard);
        if (!getCurrentPlayer().isOnline())
            return nextPlayer();
        log.info("Player " + getCurrentPlayer().getToken() + " begin his Buy turn.");
        startTimer();
        gameBoard.notifyObservers(new PrivateUpdate(new BuyTurnStarted(), getCurrentPlayer().getToken()));
        return this;
    }
}
