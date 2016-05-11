package it.polimi.ingsw.cg26.actions.quick;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.GameLogic;

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
    public void apply(GameLogic gameLogic) {
        gameLogic.electWithAssistant(this.region, this.assistantColor);
        //gameLogic.log("Elected a " + this.assistantColor + " councillor in " + this.region +" as quick action");
    }

}
