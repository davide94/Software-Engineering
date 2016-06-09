package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.observer.Observable;
import it.polimi.ingsw.cg26.common.observer.Observer;
import it.polimi.ingsw.cg26.common.update.Update;
import it.polimi.ingsw.cg26.server.actions.Action;

/**
 * 
 */
public abstract class View extends Observable<Action> implements Observer<Update>, Runnable {

}