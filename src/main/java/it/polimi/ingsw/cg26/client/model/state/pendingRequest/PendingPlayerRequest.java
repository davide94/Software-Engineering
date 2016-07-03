package it.polimi.ingsw.cg26.client.model.state.pendingRequest;

import it.polimi.ingsw.cg26.client.model.Model;

/**
 *
 */
public class PendingPlayerRequest extends PendingRequest {

    public PendingPlayerRequest(Model model, int multiplicity) {
        super(model, multiplicity);
    }

    @Override
    public int isPendingPlayerBonusRequest() {
        return multiplicity;
    }
}
