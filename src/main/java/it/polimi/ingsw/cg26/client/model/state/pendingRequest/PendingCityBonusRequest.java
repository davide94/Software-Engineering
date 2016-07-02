package it.polimi.ingsw.cg26.client.model.state.pendingRequest;

import it.polimi.ingsw.cg26.client.model.Model;

/**
 *
 */
public class PendingCityBonusRequest extends PendingRequest {

    public PendingCityBonusRequest(Model model, int multiplicity) {
        super(model, multiplicity);
    }

    @Override
    public boolean isPendingCityBonusRequest() {
        return true;
    }
}
