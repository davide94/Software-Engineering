package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.state.BoardState;

/**
 *
 */
public class FullStateChange extends ChangeDecorator {

    private static final long serialVersionUID = -6556639578792576624L;

    private BoardState state;

    public FullStateChange(Change decoratedChange, BoardState state) {
        super(decoratedChange);
    	this.state = state;
    }

    public BoardState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "FullStateChange{" +
                "state=" + state +
                '}';
    }

	@Override
	public void apply(BoardState gameBoardState){}
}
