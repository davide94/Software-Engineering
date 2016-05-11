package it.polimi.ingsw.cg26.actions.main;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.GameLogic;

/**
 *
 */
public class Build extends Action {

    private final String city;

    public Build(String city) {
        this.city = city;
    }

    @Override
    public void apply(GameLogic gameLogic) {
        gameLogic.log("Built in " + this.city);
    }

}
