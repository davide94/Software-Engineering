package it.polimi.ingsw.cg26.server.model.state;

import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.event.*;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

/**
 *
 */
public class MarketSell implements State {

    private GameBoard gameBoard;

    private List<Player> players;

    private int current;

    public MarketSell(List<Player> players, GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.players = players;
        current = 0;
        gameBoard.notifyObservers(new MarketStarted());
        gameBoard.notifyObservers(new PrivateUpdate(new SellTurnStarted(), getCurrentPlayer().getToken()));
    }

    @Override
    public Player getCurrentPlayer() {
        return players.get(current);
    }

    @Override
    public boolean canSell(long token) {
        return players.get(current).getToken() == token;
    }

    @Override
    public State sellFolded() {
        gameBoard.notifyObservers(new PrivateUpdate(new SellTurnEnded(), getCurrentPlayer().getToken()));

        return nextPlayer();
    }

    private State nextPlayer() {
        current++;
        if (current == players.size())
            return new MarketBuy(players, gameBoard);
        if (!getCurrentPlayer().isOnline())
            return nextPlayer();

        gameBoard.notifyObservers(new PrivateUpdate(new SellTurnStarted(), getCurrentPlayer().getToken()));
        return this;
    }
}
