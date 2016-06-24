package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class RegularGameStarted implements Event {

	private static final long serialVersionUID = -5228660084772635857L;

	@Override
    public void apply(ClientModel model) {
        model.getState().regularGameStarted();
    }
}
