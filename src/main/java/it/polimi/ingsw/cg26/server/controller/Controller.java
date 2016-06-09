package it.polimi.ingsw.cg26.server.controller;

import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.Update;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.FullStateChange;
import it.polimi.ingsw.cg26.common.update.change.LocalPlayerChange;
import it.polimi.ingsw.cg26.common.update.event.ActionFailed;
import it.polimi.ingsw.cg26.common.update.event.ActionSuccessFul;
import it.polimi.ingsw.cg26.common.update.event.Event;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.common.observer.Observer;

/**
 * 
 */
public class Controller implements Observer<Action>, Runnable {

    private final GameBoard gameBoard;

    public Controller(GameBoard gameBoard) {
        if (gameBoard == null)
            throw new NullPointerException();
        this.gameBoard = gameBoard;
    }

    @Override
    public synchronized void update(Action action) {

        if (gameBoard.getCurrentPlayer().getToken() == action.getToken()) {
            try {
                action.apply(gameBoard);

                Event e = new ActionSuccessFul();
                Update u = new PrivateUpdate(e, action.getToken());
                gameBoard.notifyObservers(u);

                gameBoard.actionPerformed();
            } catch (RuntimeException e) {
                e.printStackTrace();

                Event event = new ActionFailed(); // TODO: put Exception inside
                Update u = new PrivateUpdate(event, action.getToken());
                gameBoard.notifyObservers(u);
            }
        }

    }

    @Override
    public void run() {
        System.out.println("Partita cominciata");

        gameBoard.notifyObservers(new FullStateChange(new BasicChange(), gameBoard.getState()));
        for (PlayerDTO player : gameBoard.getFullPlayers())
            gameBoard.notifyObservers(new PrivateUpdate(new LocalPlayerChange(new BasicChange(), player), player.getToken()));

    }
}