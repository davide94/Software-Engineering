package it.polimi.ingsw.cg26.server.model;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.LinkedList;

/**
 *
 */
public class Scheduler {

    private final LinkedList<Player> players;

    private Player currentPlayer;

    private final GameBoard gameBoard;

    public Scheduler(GameBoard gameBoard) {
        if (gameBoard == null)
            throw new NullPointerException();
        this.players = new LinkedList<>();
        this.gameBoard = gameBoard;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void registerPlayer(Player player) {
        this.players.add(player);
        if (this.players.size() == 1)
            this.currentPlayer = this.players.poll();
    }

    public void actionPerformed() {
        if (currentPlayer.canPerformMainAction() || currentPlayer.canPerformQuickAction())
            return;
        // TODO controlli su terminazione partita
        players.add(currentPlayer);
        currentPlayer = players.poll();
    }
}
