package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.server.observer.Observable;
import it.polimi.ingsw.cg26.server.observer.Observer;

/**
 * 
 */
public abstract class View extends Observable<Action> implements Observer<Change>, Runnable {

}