package it.polimi.ingsw.cg26.common.change;

/**
 *
 */
public class YourTurnStarts extends ChangeDecorator {

    private static final long serialVersionUID = 5738884095969265935L;

    public YourTurnStarts(Change decoratedChange) {
        super(decoratedChange);
    }
}
