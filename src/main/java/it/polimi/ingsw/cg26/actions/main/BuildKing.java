package it.polimi.ingsw.cg26.actions.main;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.GameLogic;

/**
 *
 */
public class BuildKing extends Action {

    @Override
    public void apply(GameLogic gameLogic) {
        gameLogic.log("BuildKing");
    }
}
