package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class RegularTurnEnded implements Event {

	private static final long serialVersionUID = 3714498163196048841L;

	@Override
    public void apply(ClientModel model) {
        model.getState().turnEnded();
    }
}
