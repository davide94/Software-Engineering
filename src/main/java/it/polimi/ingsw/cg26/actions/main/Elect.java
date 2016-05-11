package it.polimi.ingsw.cg26.actions.main;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.GameLogic;

/**
 *
 */
public class Elect extends Action {

    private final String region;

    private final String assistantColor;

    public Elect(String region, String assistantColor) {
        if (region == null || assistantColor == null)
            throw new NullPointerException();
        this.region = region;
        this.assistantColor = assistantColor;
    }

    @Override
    public void apply(GameLogic gameLogic) {
        gameLogic.log("Elected a " + this.assistantColor + " councillor in " + this.region);
    }

}
