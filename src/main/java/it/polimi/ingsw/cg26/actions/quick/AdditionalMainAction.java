package it.polimi.ingsw.cg26.actions.quick;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.GameLogic;

/**
 *
 */
public class AdditionalMainAction extends Action {

    @Override
    public void apply(GameLogic gameLogic) {
        gameLogic.log("AdditionalMainAction");
    }

}
