package it.polimi.ingsw.cg26.client.model.state.pendingRequest;

import it.polimi.ingsw.cg26.client.model.Model;

/**
 *
 */
public class PendingBPTRequest extends PendingRequest {

    public PendingBPTRequest(Model model, int multiplicity) {
        super(model, multiplicity);
    }

    @Override
    public boolean isPendingBPTBonusRequest() {
        return true;
    }
}
