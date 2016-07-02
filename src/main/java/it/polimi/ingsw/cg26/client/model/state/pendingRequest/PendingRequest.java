package it.polimi.ingsw.cg26.client.model.state.pendingRequest;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.model.state.State;

import java.util.Map;

/**
 *
 */
public class PendingRequest implements State {

    protected Model model;

    protected int multiplicity;

    public PendingRequest(Model model, int multiplicity) {
        this.model = model;
        this.multiplicity = multiplicity;
    }

    @Override
    public Map<String, String> commands() {
        return null;
    }
}
