package it.polimi.ingsw.cg26.common.change;

/**
 *
 */
public class NotYourTurn extends ChangeDecorator {

    private static final long serialVersionUID = 6264647890094906723L;

    public NotYourTurn(Change decoratedChange) {
        super(decoratedChange);
    }

}
