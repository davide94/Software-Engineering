package it.polimi.ingsw.cg26.common.commands;


import it.polimi.ingsw.cg26.server.actions.Action;

import java.io.Serializable;

/**
 *
 */
public abstract class Command implements Serializable {

    public abstract Action generateAction();
}
