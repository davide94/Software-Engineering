package it.polimi.ingsw.cg26.actions.main;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.board.GameBoard;

/**
 *
 */
public class Build extends Action {

    private final String city;

    public Build(String city) {
        if (city == null)
            throw new NullPointerException();
        this.city = city;
    }

    @Override
    public void apply(GameBoard gameBoard) {
        // TODO
    }

}
