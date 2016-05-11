package it.polimi.ingsw.cg26.actions.quick;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.GameLogic;

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
    public void apply(GameLogic gameLogic) {
        gameLogic.changeBPT(this.region);
        //gameLogic.log("Changed tile in " + this.region);
    }

}
