package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.event.BuyTurnEnded;
import it.polimi.ingsw.cg26.common.update.event.BuyTurnStarted;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class MarketBuy implements State {

    private GameBoard gameBoard;

    private List<Player> players;

    private List<Player> shuffled;

    private int current;

    public MarketBuy(List<Player> players, GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.players = players;
        shuffled = new LinkedList<>(players);
        Collections.shuffle(shuffled);
        current = 0;
        gameBoard.notifyObservers(new PrivateUpdate(new BuyTurnStarted(), getCurrentPlayer().getToken()));
    }

    @Override
    public Player getCurrentPlayer() {
        return shuffled.get(current);
    }

    @Override
    public boolean canBuy(long token) {
        return shuffled.get(current).getToken() == token;
    }

    @Override
    public State buyFolded() {
        gameBoard.notifyObservers(new PrivateUpdate(new BuyTurnEnded(), getCurrentPlayer().getToken()));

        return nextPlayer();
    }

    private State nextPlayer() {
        current++;
        if (current == players.size()) {
            return new Regular(players, gameBoard);
        }
        if (!getCurrentPlayer().isOnline())
            return nextPlayer();

        gameBoard.notifyObservers(new PrivateUpdate(new BuyTurnStarted(), getCurrentPlayer().getToken()));
        return this;
    }
}
