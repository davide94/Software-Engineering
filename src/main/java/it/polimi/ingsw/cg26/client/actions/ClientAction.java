package it.polimi.ingsw.cg26.client.actions;


import java.io.Serializable;

/**
 *
 */
public abstract class ClientAction implements Serializable {

    public abstract void apply();
}
