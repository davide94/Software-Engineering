package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.state.BoardState;

/**
 *
 */
public class FullStateChange extends Change {

    private static final long serialVersionUID = -6556639578792576624L;

    private BoardState state;

    public FullStateChange(BoardState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "FullStateChange{" +
                "state=" + state +
                '}';
    }
}
