package it.polimi.ingsw.cg26.common.change;

/**
 *
 */
public class YourTurnEnds extends ChangeDecorator {

    private static final long serialVersionUID = -1691757892027613935L;

    public YourTurnEnds(Change decoratedChange) {
        super(decoratedChange);
    }
}
