package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;

/**
 *
 */
public class PrivateChange extends ChangeDecorator {

    private static final long serialVersionUID = 8585646987699696361L;

    private final long to;

    public PrivateChange(Change decoratedChange, long to) {
        super(decoratedChange);
        this.to = to;
    }

    @Override
    public boolean isFor(long token) {
        return this.to == token;
    }

    @Override
    public void apply(GameBoardDTO gameGameBoardDTO) {
        super.apply(gameGameBoardDTO);
    }
}
