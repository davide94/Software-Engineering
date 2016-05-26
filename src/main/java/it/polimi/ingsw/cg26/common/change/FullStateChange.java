package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;

/**
 *
 */
public class FullStateChange extends ChangeDecorator {

    private static final long serialVersionUID = -6556639578792576624L;

    private GameBoardDTO state;
    private PlayerDTO me;

    public FullStateChange(Change decoratedChange, GameBoardDTO state) {
        super(decoratedChange);
    	this.state = state;
    }

    public GameBoardDTO getState() {
        return state;
    }

    public PlayerDTO getMe() {
        return me;
    }

    public void setMe(PlayerDTO me) {
        this.me = me;
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
