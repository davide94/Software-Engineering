package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;

/**
 *
 */
public class PrivateChange extends ChangeDecorator {

    private static final long serialVersionUID = 8585646987699696361L;

    private final long token;

    public PrivateChange(Change decoratedChange, long token) {
        super(decoratedChange);
        this.token = token;
    }

    @Override
    public boolean isFor(long token) {
        return this.token == token;
    }

    @Override
    public void apply(GameBoardDTO gameGameBoardDTO) {
    }
}
