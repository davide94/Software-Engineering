package it.polimi.ingsw.cg26.actions.quick;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.board.GameBoard;

/**
 *
 */
public class ElectAsQuickAction extends Action {

    private final String region;

    private final String assistantColor;

    public ElectAsQuickAction(String region, String assistantColor) {
        if (region == null || assistantColor == null)
            throw new NullPointerException();
        this.region = region;
        this.assistantColor = assistantColor;
    }

    @Override
    public void apply(GameBoard gameBoard) {
        // TODO
    }

}
