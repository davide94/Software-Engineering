package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;

/**
 *
 */
public class LocalPlayerChange extends ChangeDecorator {

    private static final long serialVersionUID = 8457704269752814158L;

    private final PlayerDTO localPlayer;

    public LocalPlayerChange(Change decoratedChange, PlayerDTO localPlayer) {
        super(decoratedChange);
        this.localPlayer = localPlayer;
    }

    @Override
    public void apply(GameBoardDTO gameGameBoardDTO) {
        super.apply(gameGameBoardDTO);
        gameGameBoardDTO.setLocalPlayer(localPlayer);
    }
}
