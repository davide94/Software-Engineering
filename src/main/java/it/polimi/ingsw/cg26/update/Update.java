package it.polimi.ingsw.cg26.update;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.GameLogic;

/**
 *
 */
public abstract class Update extends Action {
    public abstract void apply(GameLogic gameLogic);
}
