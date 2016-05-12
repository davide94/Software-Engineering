package it.polimi.ingsw.cg26.actions.quick;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.board.GameBoard;

/**
 *
 */
public class ChangeBPT extends Action {

    private final String region;

    public ChangeBPT(String region) {
        if (region == null)
            throw new NullPointerException();
        this.region = region;
    }

    @Override
    public void apply(GameBoard gameBoard) {
        // TODO
    }

}
