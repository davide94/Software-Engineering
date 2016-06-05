package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;

/**
 *
 */
public class LocalPlayerChange extends ChangeDecorator {

    private static final long serialVersionUID = 8457704269752814158L;

    private final PlayerDTO localPlayer;

    /**
     * Construct a change of the local player
     * @param decoratedChange the change to decorate
     * @param localPlayer the local player to set
     * @throws NullPointerException if one or more arguments are null
     */
    public LocalPlayerChange(Change decoratedChange, PlayerDTO localPlayer) {
        super(decoratedChange);
        if(localPlayer == null)
        	throw new NullPointerException();
        this.localPlayer = localPlayer;
    }

    @Override
    public void apply(GameBoardDTO gameGameBoardDTO) {
        super.apply(gameGameBoardDTO);
        gameGameBoardDTO.setLocalPlayer(localPlayer);
    }
}
