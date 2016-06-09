package it.polimi.ingsw.cg26.server.controller;

import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.common.observer.Observer;
import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.Update;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.FullStateChange;
import it.polimi.ingsw.cg26.common.update.change.LocalPlayerChange;
import it.polimi.ingsw.cg26.common.update.event.*;
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

            Event e = new ActionSuccessFul();
            Update u = new PrivateUpdate(e, action.getToken());
            gameBoard.notifyObservers(u);
            log.info("Action successfully performed");
            gameBoard.actionPerformed();
        } catch (Exception e) {
            log.error("Error occurred while executing action", e);
            Event event = new ActionFailed(e); // TODO: put Exception inside
            Update u = new PrivateUpdate(event, action.getToken());
            gameBoard.notifyObservers(u);
        }
    }

    @Override
    public void run() {
        log.info("Match Started");

        gameBoard.notifyObservers(new FullStateChange(new BasicChange(), gameBoard.getState()));
        for (PlayerDTO player : gameBoard.getFullPlayers())
            gameBoard.notifyObservers(new PrivateUpdate(new LocalPlayerChange(new BasicChange(), player), player.getToken()));

        Event e = new GameStarted();
        gameBoard.notifyObservers(e);

        e =  new TurnStarted();
        Update u = new PrivateUpdate(e, gameBoard.getCurrentPlayer().getToken());
        gameBoard.notifyObservers(u);
    }
}