package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class MatchEnded implements Event {

	private static final long serialVersionUID = -6954465562704054321L;

	@Override
    public void apply(ClientModel model) {
        model.getState().matchEnded();
    }
}
