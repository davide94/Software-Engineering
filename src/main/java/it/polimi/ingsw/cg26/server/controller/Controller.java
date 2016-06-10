package it.polimi.ingsw.cg26.server.controller;

import it.polimi.ingsw.cg26.common.observer.Observer;
import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.event.ActionFailed;
import it.polimi.ingsw.cg26.common.update.event.ActionSuccessFul;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class Controller implements Observer<Action>, Runnable {

    private final GameBoard gameBoard;

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    public Controller(GameBoard gameBoard) {
        if (gameBoard == null)
            throw new NullPointerException();
        this.gameBoard = gameBoard;
    }

    @Override
    public synchronized void update(Action action) {
        log.info("Action received: " + action);
        if (gameBoard.getCurrentPlayer().getToken() != action.getToken()) {
            log.error("Is not the turn of the sender");
            return;
        }
        try {
            action.apply(gameBoard);
            gameBoard.notifyObservers(new PrivateUpdate(new ActionSuccessFul(), action.getToken()));
            log.info("Action successfully performed");
            gameBoard.actionPerformed();
        } catch (Exception e) {
            log.error("Error occurred while executing action", e);
            gameBoard.notifyObservers(new PrivateUpdate(new ActionFailed(e), action.getToken()));
        }
    }

    @Override
    public void run() {
        gameBoard.start();
        log.info("Match Started");
    }
}