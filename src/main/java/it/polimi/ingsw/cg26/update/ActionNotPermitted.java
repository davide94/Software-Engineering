package it.polimi.ingsw.cg26.update;

import it.polimi.ingsw.cg26.model.GameLogic;

/**
 *
 */
public class ActionNotPermitted extends Update {

    private final String message;

    public ActionNotPermitted(String message) {
        if (message == null)
            throw new NullPointerException();
        this.message = message;
    }

    @Override
    public String toString() {
        return "ActionNotPermitted{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public void apply(GameLogic gameLogic) {

    }
}
