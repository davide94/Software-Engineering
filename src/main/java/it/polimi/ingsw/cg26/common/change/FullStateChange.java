package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;

/**
 *
 */
public class FullStateChange extends ChangeDecorator {

    private static final long serialVersionUID = -6556639578792576624L;

    private GameBoardDTO state;

    public FullStateChange(Change decoratedChange, GameBoardDTO state) {
        super(decoratedChange);
    	this.state = state;
    }

    public GameBoardDTO getState() {
        return state;
    }

    @Override
    public String toString() {
        return "FullStateChange{" +
                "dto=" + state +
                '}';
    }

	@Override
	public void apply(GameBoardDTO gameGameBoardDTO){}
}
