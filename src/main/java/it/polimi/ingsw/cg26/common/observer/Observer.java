package it.polimi.ingsw.cg26.common.observer;

import java.rmi.RemoteException;

/**
 * 
 */
public interface Observer<C> {

    void update(C o);

}